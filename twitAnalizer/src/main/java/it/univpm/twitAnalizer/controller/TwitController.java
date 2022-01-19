package it.univpm.twitAnalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.twitAnalizer.service.TwitService;
/*
 * @author Nicholas Bradach
 * @author Andrea Colonnini
 * 
 */
@RestController
public class TwitController {
	
	@Autowired
	TwitService ts;
	
	/* 
	 * @return all every place known/successfully found
	 */
	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public ResponseEntity<Object> getStats(){
		return new ResponseEntity<>(ts.getStats(), HttpStatus.OK);
	}
	/* 
	 * @param name of the city
	 * @return only the searched city
	 */
	@RequestMapping(value = "/control/{name}", method = RequestMethod.GET)
	public ResponseEntity<Object> 
	searchName(@PathVariable("name") String name){
		return new ResponseEntity<>(ts.searchName(name), HttpStatus.OK);
	}
	/*
	 * Searches, analyzes and saves the tweets
	 */
	@RequestMapping(value = "/control", method = RequestMethod.PUT)
	public ResponseEntity<Object> saveEveryHour(){
		ts.saveEveryHour();
		return new ResponseEntity<>("Statistics are being saved.", HttpStatus.OK);
	}
}
