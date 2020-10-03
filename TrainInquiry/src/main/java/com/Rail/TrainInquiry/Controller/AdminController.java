package com.Rail.TrainInquiry.Controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Rail.TrainInquiry.Exceptions.NoRouteExistsException;
import com.Rail.TrainInquiry.Exceptions.NoTrainsException;
import com.Rail.TrainInquiry.Exceptions.TrainAlreadyExistsException;
import com.Rail.TrainInquiry.Exceptions.TrainCannotDeletedException;
import com.Rail.TrainInquiry.Model.Route;
import com.Rail.TrainInquiry.Model.Train;
import com.Rail.TrainInquiry.Service.TrainInquiry_Service;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/routes")
@Api(value="Admin Controller, Controls all the operations of the Application")
public class AdminController 
{
	@Autowired
	private TrainInquiry_Service service;
	
	//Requirement 1
	@PostMapping("/routes")
	public ResponseEntity<String> createRoute(@RequestBody Route route,Errors error) throws Exception
	{
		String response="";
		if(error.hasErrors())
		{
			response=error.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" , "));
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		Integer res=service.createRoute(route);
		return new ResponseEntity<String>("Route added successfully : "+res,HttpStatus.OK);
	}
	
	//Requirement 2
	
	@GetMapping(value="/{routeId}")
	public ResponseEntity<Route> getRouteDetails(@PathVariable Integer routeId) throws NoRouteExistsException,Exception
	{
		if(routeId<100 && routeId>999)
		{
			throw new Exception("Route Id should be 3 digit number");
		}
		return new ResponseEntity<Route>(service.getRoute(routeId),HttpStatus.ACCEPTED);
	}
	
	//Requirement 3
	@GetMapping(value="/trains")
	public ResponseEntity<List<Train>> viewTrainsBySourceandDestination(@RequestParam String source,@RequestParam String destination) throws Exception
	{
		if(source!=null && source.chars().allMatch(Character::isLetter) && destination!=null && destination.matches("[A-Za-z]+"))
		{
			List<Train> trains=service.getListOfTrains(source, destination);
			return new ResponseEntity<List<Train>>(trains, HttpStatus.ACCEPTED);
		}
		else
		{
			throw new Exception("Source and Destination strings should only consists of Alphabets. Not Even space");
		}
	}
	
	//Requirement 4
	@PutMapping(value="/{query}/route/{routeId}")
	public String updateRouteDetails(@MatrixVariable(pathVar="query") Map<String,List<String>>mp,@PathVariable Integer routeId) throws NoRouteExistsException
	{
		String source=mp.get("v").get(0);
		String destination=mp.get("v").get(1);
		return service.updateRouteDetails(routeId, source, destination);
	}
	
	//Requirement 5
	
	@DeleteMapping(value="/{routeId}/{trainId}")
	public ResponseEntity<String> deleteTrainFromListofTrains(@PathVariable Integer routeId,@PathVariable Integer trainId) throws NoRouteExistsException, NoTrainsException, TrainCannotDeletedException
	{
		String response=service.deleteTrainInaParticularRoute(routeId, trainId);
		return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
	}
	
	//Requirement 6
	@PutMapping("/{routeId}/updateOrAdd/{UA}")
	public ResponseEntity<String> updateOrAddTrainDetailsOfRoute(@PathVariable Integer routeId,@PathVariable("UA")Boolean UA,@RequestBody Train train) throws Exception
	{
		String response=service.updateDetailsOfRoute(routeId, train,UA);
		return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
	}
	
	//Requirement 7
	@PostMapping("/trains")
	public ResponseEntity<String> createTrainDetails(@RequestBody Train train) throws TrainAlreadyExistsException,Exception
	{
		String inat=train.getArrivalTime();
		String indt=train.getDepartureTime();
		
		LocalTime lat;
		try 
		{
			lat = LocalTime.parse(inat, DateTimeFormatter.ofPattern("HH:mm:ss"));
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block

			//System.out.println("lat error");
			throw new Exception("Arrival.Time.Format");
		}
		
		
		LocalTime ldt = null;
		try {
			ldt = LocalTime.parse(indt, DateTimeFormatter.ofPattern("HH:mm:ss"));
		} catch (Exception e)
		{
			//System.out.println("ldt error");
			throw new Exception("Departure.Time.Format");
			// TODO Auto-generated catch block
			
		}
		//System.out.println(lat+" "+ldt);
		if(lat.isAfter(ldt) || ldt.isBefore(lat))
		{
			throw new Exception("Arrival.After.Departure");
		}
		if(lat.equals(ldt))
		{
			throw new Exception("Times.Equal");
		}
		
		String response=service.createTrainDetails(train);
		return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
	}
	
	//Requirement 8
	@PutMapping("/{trainId}/{fare}")
	public ResponseEntity<String> updateFareOfTrain(@PathVariable Integer trainId,@PathVariable Double fare) throws NoTrainsException 
	{
		System.out.println("here is cak");
		String response="";
		try {
			response = service.updateTrainDetailsWithFare(trainId, fare);
		} catch (NoTrainsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
	}
	
	
}
