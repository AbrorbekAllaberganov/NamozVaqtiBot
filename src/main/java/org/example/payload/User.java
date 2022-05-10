package org.example.payload;

public class User {
    private String chat_id;
    private String STEP;

    private String date;

    public User() {
    }

    public User(String chat_id, String STEP) {
        this.chat_id = chat_id;
        this.STEP = STEP;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getSTEP() {
        return STEP;
    }

    public void setSTEP(String STEP) {
        this.STEP = STEP;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
