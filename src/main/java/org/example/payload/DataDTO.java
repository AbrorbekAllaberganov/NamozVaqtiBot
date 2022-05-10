package org.example.payload;

import java.io.Serializable;

public class DataDTO implements Serializable {
	private TimingsDTO timings;
	private MetaDTO meta;

	public void setTimings(TimingsDTO timings){
		this.timings = timings;
	}

	public TimingsDTO getTimings(){
		return timings;
	}

	public void setMeta(MetaDTO meta){
		this.meta = meta;
	}

	public MetaDTO getMeta(){
		return meta;
	}

	@Override
 	public String toString(){
		return 
			"DataDTO{" + 
			"timings = '" + timings + '\'' + 
			",meta = '" + meta + '\'' + 
			"}";
		}
}