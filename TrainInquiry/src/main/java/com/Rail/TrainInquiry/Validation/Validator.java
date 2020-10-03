package com.Rail.TrainInquiry.Validation;

import com.Rail.TrainInquiry.Model.Train;

public class Validator 
{
	public static void validate(Train t) throws Exception
	{
		if(!validateArrivalTime(t.getArrivalTime()))
		{
			throw new Exception("Arrival.Time.Format");
		}
		if(!validateDepartureTime(t.getDepartureTime()))
		{
			throw new Exception("Departure.Time.Format");
		}
		if(!validateFare(t.getFare()))
		{
			throw new Exception("Fare should be greater than zero");
		}
	}
	public static boolean validateArrivalTime(String arrivalTime)
	{
		if(arrivalTime.matches("([01]\\d|2[0123]):([012345]\\d):([012345]\\d)"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean validateDepartureTime(String departureTime)
	{
		if(departureTime.matches("([01]\\d|2[0123]):([012345]\\d):([012345]\\d)"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean validateTrainName(String name)
	{
		if(name.matches("[[A-Za-z]+[\\s]?]+"))
		{
			return true;
		}
		return false;
	}
	public static boolean validateFare(Double fare)
	{
		return fare>0;
	}

}
