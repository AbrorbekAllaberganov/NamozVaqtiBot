package org.example.payload;

import java.util.List;
import java.io.Serializable;

public class ResponseDTO implements Serializable {
	private int code;
	private String status;
	private List<DataDTO> data;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setData(List<DataDTO> data){
		this.data = data;
	}

	public List<DataDTO> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDTO{" + 
			"code = '" + code + '\'' + 
			",status = '" + status + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}