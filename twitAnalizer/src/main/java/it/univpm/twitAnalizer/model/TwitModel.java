package it.univpm.twitAnalizer.model;

import java.util.Date;

public class TwitModel {
	private String placeId;
	private Date created;
	
	public String getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
}
