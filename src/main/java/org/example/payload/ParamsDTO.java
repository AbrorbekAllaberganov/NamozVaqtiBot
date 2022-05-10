package org.example.payload;

import java.io.Serializable;

public class ParamsDTO implements Serializable {
	private int fajr;
	private int isha;

	public void setFajr(int fajr){
		this.fajr = fajr;
	}

	public int getFajr(){
		return fajr;
	}

	public void setIsha(int isha){
		this.isha = isha;
	}

	public int getIsha(){
		return isha;
	}

	@Override
 	public String toString(){
		return 
			"ParamsDTO{" + 
			"fajr = '" + fajr + '\'' + 
			",isha = '" + isha + '\'' + 
			"}";
		}
}