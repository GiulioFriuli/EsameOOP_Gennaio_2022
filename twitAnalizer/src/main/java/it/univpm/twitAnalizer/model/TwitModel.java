
package it.univpm.twitAnalizer.model;

/*
 * this class describes tweet's properties 
 * @author Nicholas Bradach
 * @author Andrea Colonnini
 */

public class TwitModel {
	// should be usefull
		private String placeId;
		private String name;
		private DateModel created;
		
		/*
		 * Object's builder
		 * @param placeId is tweet's location code
		 * @param name is the name of the city
		 * @param created is the tweet's creation date
		 */
		public TwitModel(String placeId, String name, DateModel created) {
			this.placeId = placeId;
			this.name = name;
			this.created = created;
		}
		
		/*
		 * placeId getter
		 * @return placeId
		 */
		public String getPlaceId() {
			return placeId;
		}
		
		/*
		 * name getter
		 * @return name
		 */
		public String getName() {
			return name;
		}
		
		/*
		 * create getter
		 * @return created
		 */
		public DateModel getCreated() {
			return created;
		}		
}