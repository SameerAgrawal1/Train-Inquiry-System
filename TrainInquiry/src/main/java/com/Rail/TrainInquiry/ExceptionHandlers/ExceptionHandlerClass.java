package com.Rail.TrainInquiry.ExceptionHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Rail.TrainInquiry.ErrorMessageResponse.ErrorMessage;
import com.Rail.TrainInquiry.Exceptions.NoRouteExistsException;
import com.Rail.TrainInquiry.Exceptions.NoTrainsException;
import com.Rail.TrainInquiry.Exceptions.TrainAlreadyExistsException;
import com.Rail.TrainInquiry.Exceptions.TrainCannotDeletedException;
import com.Rail.TrainInquiry.Exceptions.TrainCannotUpdatedException;

import io.swagger.annotations.Api;

@RestControllerAdvice
@Api(value = "Exception handler, Handles the exceptions occurred globally for the application")
public class ExceptionHandlerClass 
{
	@Autowired
	private Environment env;
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handlerException1(Exception e)
	{
		if(e.getMessage().equalsIgnoreCase("Arrival.Time.Format")||e.getMessage().equalsIgnoreCase("Departure.Time.Format")||e.getMessage().equalsIgnoreCase("Route.Already.Present") || e.getMessage().equalsIgnoreCase("Times.Equal") || e.getMessage().equalsIgnoreCase("Arrival.After.Departure") || e.getMessage().equalsIgnoreCase("NO.TRAINS.EXISTS"))
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(env.getProperty(e.getMessage()));
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
		else
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ExceptionHandler(NoRouteExistsException.class)
	public ResponseEntity<ErrorMessage> handlerNoRouteExistsException2(NoRouteExistsException e)
	{
		if(e.getMessage().equalsIgnoreCase("RouteEntity.Doesnot.Exists"))
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(env.getProperty(e.getMessage()));
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
		else
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ExceptionHandler(NoTrainsException.class)
	public ResponseEntity<ErrorMessage> handlerNoTrainsException3(NoTrainsException e)
	{
		if(e.getMessage().equalsIgnoreCase("Train.NotExists.UpdateFare") || e.getMessage().equalsIgnoreCase("NO.Trains.Exists.Todelete") || e.getMessage().equalsIgnoreCase("NO.Trains.Exists.ToUpdate"))
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(env.getProperty(e.getMessage()));
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
		else
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(TrainAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> handlerTrainAlreadyExistsException4(TrainAlreadyExistsException e)
	{
		if(e.getMessage().equalsIgnoreCase("Train.Already.Exists"))
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(env.getProperty(e.getMessage()));
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
		else
		{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(TrainCannotUpdatedException.class)
	public ResponseEntity<ErrorMessage> handlerTrainCannotUpdatedException5(TrainCannotUpdatedException e)
	{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TrainCannotDeletedException.class)
	public ResponseEntity<ErrorMessage> handlerTrainCannotDeletedException6(TrainCannotDeletedException e)
	{
			ErrorMessage erm=new ErrorMessage();
			erm.setErrorCode(HttpStatus.BAD_REQUEST.value());
			erm.setErrorMessage(e.getMessage());
			return new ResponseEntity<ErrorMessage>(erm, HttpStatus.BAD_REQUEST);
	}
}
