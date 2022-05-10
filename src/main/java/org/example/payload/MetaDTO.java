package org.example.payload;

import java.io.Serializable;

public class MetaDTO implements Serializable {
	private Object latitude;
	private Object longitude;
	private String timezone;
	private MethodDTO method;

	public void setLatitude(Object latitude){
		this.latitude = latitude;
	}

	public Object getLatitude(){
		return latitude;
	}

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setMethod(MethodDTO method){
		this.method = method;
	}

	public MethodDTO getMethod(){
		return method;
	}

	@Override
 	public String toString(){
		return 
			"MetaDTO{" + 
			"latitude = '" + latitude + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",timezone = '" + timezone + '\'' + 
			",method = '" + method + '\'' + 
			"}";
		}
}