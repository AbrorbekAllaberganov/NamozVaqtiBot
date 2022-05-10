package org.example;

import com.google.gson.Gson;
import org.example.payload.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyBot extends TelegramLongPollingBot {
    static List<User> users = new ArrayList<>();

    @Override
    public String getBotUsername() {
        return "USERNAME";
    }

    @Override
    public String getBotToken() {
        return "TOKEN";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage() != null) {
            Message message = update.getMessage();
            String chat_id = String.valueOf(message.getChatId());
            User user = findUser(chat_id);

            if (message.hasText()) {
                String text = message.getText();

                if (text.equals("/start")) {
                    sendMessage(chat_id, "Botga xush kelibsiz\nNamoz vaqtlarini bilishni xohlaysizmi ?", home());
                    user.setSTEP("HOME");
                } else if (text.equals("Bugun")) {
                    sendMessage(chat_id, "Manzilingizni yuboring", getLocation());
                    user.setSTEP("LOCATION");
                } else if (text.equals("Kun bo'yicha")) {
                    sendMessage(chat_id, "Sanani quyidagi ko'rinishda yuboring\n30-07-2022", null);
                    user.setSTEP("DATE");
                } else if (user.getSTEP().equals("DATE")) {
                    sendMessage(chat_id, "Manzilingizni yuboring", getLocation());
                    user.setDate(text);
                }
            } else if (message.hasLocation()) {
                Location location = message.getLocation();
                Double lat = location.getLatitude();
                Double lon = location.getLongitude();
                Date date;
                if (user.getSTEP().equals("LOCATION"))
                     date = new Date();
                else {
                    SimpleDateFormat spf=new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        date=spf.parse(user.getDate());
                    } catch (ParseException e) {
                        sendMessage(chat_id,"Sana xato yuborildi",null);
                        throw new RuntimeException(e);
                    }
                }

                ResponseDTO responseDTO = getResponse(date,lat,lon);
                List<DataDTO> dataDTO=responseDTO.getData();
                Calendar calendar=new GregorianCalendar();
                calendar.setTime(date);
                TimingsDTO timingsDTO=dataDTO.get(calendar.get(Calendar.DAY_OF_MONTH)).getTimings();

                String text = "⏰ Bomdod vaqti: " +
                        timingsDTO.getFajr() + " - " + timingsDTO.getSunrise() +
                        "\n⏰ Peshin: " + timingsDTO.getDhuhr() + " - " + timingsDTO.getAsr() +
                        "\n⏰ Asr: " + timingsDTO.getAsr() + " - " + timingsDTO.getMaghrib() +
                        "\n⏰ Shom: " + timingsDTO.getMaghrib() + " - " + timingsDTO.getIsha() +
                        "\n⏰ Xufton: " + timingsDTO.getIsha();

                sendMessage(chat_id, text,home());

            }

        }
    }

    public void sendMessage(String chat_id, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chat_id);
        sendMessage.setReplyMarkup(Objects.requireNonNullElseGet(markup, () -> new ReplyKeyboardRemove(true)));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public ReplyKeyboardMarkup home() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setText("Bugun");
        row.add(button);

        button = new KeyboardButton();
        button.setText("Kun bo'yicha");
        row.add(button);

        rows.add(row);
        markup.setKeyboard(rows);

        return markup;
    }

    public ReplyKeyboardMarkup getLocation(){
        ReplyKeyboardMarkup markup=new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow>rows=new ArrayList<>();
        KeyboardRow row=new KeyboardRow();

        KeyboardButton button=new KeyboardButton();
        button.setText("Manzilimni yuborish");
        button.setRequestLocation(true);

        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);

        return markup;
    }

    public User findUser(String chat_id) {
        for (User user : users) {
            if (user.getChat_id().equals(chat_id))
                return user;
        }
        User user = new User(chat_id, "START");
        users.add(user);
        return user;
    }

    public ResponseDTO getResponse(Date date, Double lat, Double lon) {
        URL url = null;
        Gson gson = new Gson();
        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);


        try {
            url = new URL("http://api.aladhan.com/v1/calendar?latitude=" + lat + "&longitude=" + lon + "&method=2&month=" + (calendar.get(Calendar.MONTH)+1) + "&year=" + calendar.get(Calendar.YEAR));
            urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            ResponseDTO responseDTO = gson.fromJson(bufferedReader, ResponseDTO.class);
            if (responseDTO != null)
                return responseDTO;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}

