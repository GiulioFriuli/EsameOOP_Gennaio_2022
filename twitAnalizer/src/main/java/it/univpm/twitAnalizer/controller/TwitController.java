package it.univpm.twitAnalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.twitAnalizer.service.TwitService;

@RestController
public class TwitController {
	
	@Autowired
	TwitService ts;
	
	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public ResponseEntity<Object> getStats(){
		return new ResponseEntity<>(ts.getStats(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/control", method = RequestMethod.PUT)
	public ResponseEntity<Object> saveEveryHour(){
		ts.saveEveryHour();
		return new ResponseEntity<>("Statistics saved.", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Object> everyHour(){
		ts.everyHour();
		return new ResponseEntity<>("TESTED.", HttpStatus.OK);
	}
}
