
package it.univpm.twitAnalizer.model;

public class TwitModel {
	// should be usefull
		private String placeId;
		private DateModel created;
		
		public TwitModel(String placeId, DateModel created) {
			this.placeId = placeId;
			this.created = created;
		}
		
		public String getPlaceId() {
			return placeId;
		}
		
		public DateModel getCreated() {
			return created;
		}
		
}