
package it.univpm.twitAnalizer.model;

public class TwitModel {
	// should be usefull
		private String placeId;
		private String name;
		private DateModel created;
		
		public TwitModel(String placeId, String name, DateModel created) {
			this.placeId = placeId;
			this.name = name;
			this.created = created;
		}
		
		public String getPlaceId() {
			return placeId;
		}
		
		public String getName() {
			return name;
		}
		
		public DateModel getCreated() {
			return created;
		}		
}
//added on main branch