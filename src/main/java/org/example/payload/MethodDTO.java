package org.example.payload;

import java.io.Serializable;

public class MethodDTO implements Serializable {
	private int id;
	private String name;
	private ParamsDTO params;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setParams(ParamsDTO params){
		this.params = params;
	}

	public ParamsDTO getParams(){
		return params;
	}

	@Override
 	public String toString(){
		return 
			"MethodDTO{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",params = '" + params + '\'' + 
			"}";
		}
}