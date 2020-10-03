package com.Rail.TrainInquiry.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Rail.TrainInquiry.DAO.TrainInquiry_Route_Repo;
import com.Rail.TrainInquiry.DAO.TrainInquiry_Train_Repo;
import com.Rail.TrainInquiry.Entity.RouteEntity;
import com.Rail.TrainInquiry.Entity.TrainEntity;
import com.Rail.TrainInquiry.Exceptions.NoRouteExistsException;
import com.Rail.TrainInquiry.Exceptions.NoTrainsException;
import com.Rail.TrainInquiry.Exceptions.TrainAlreadyExistsException;
import com.Rail.TrainInquiry.Exceptions.TrainCannotDeletedException;
import com.Rail.TrainInquiry.Exceptions.TrainCannotUpdatedException;
import com.Rail.TrainInquiry.Model.Route;
import com.Rail.TrainInquiry.Model.Train;
import com.Rail.TrainInquiry.Validation.Validator;

@Service
@Transactional
public class TrainInquiry_Service 
{
	@Autowired
	private TrainInquiry_Route_Repo repo1;
	
	@Autowired
	private TrainInquiry_Train_Repo repo2;
	
	
	//Requirement 1
	public Integer createRoute(Route route) throws Exception
	{
		List<RouteEntity> listOfRe=repo1.findAll();
		for(RouteEntity re:listOfRe)
		{
			if(re.getSource().equalsIgnoreCase(route.getSource()) && re.getDestination().equalsIgnoreCase(route.getDestination()))
			{
				throw new Exception("Route.Already.Present");
			}
		}
		
		RouteEntity routeEntity=new ModelMapper().map(route, RouteEntity.class);
		List<TrainEntity> te=new ArrayList<TrainEntity>();
		
		System.out.println(route.getTrains().toString());
		for(Train t: route.getTrains())
		{
			System.out.println(t.getArrivalTime());
			TrainEntity tem=new TrainEntity();
			tem.setId(t.getId());
			if(!t.getArrivalTime().matches("([01]\\d|2[0123]):([012345]\\d):([012345]\\d)") || !(t.getDepartureTime().matches("([01]\\d|2[0123]):([012345]\\d):([012345]\\d)")))
			{
				throw new Exception("check arrival and departure time for the train id:"+t.getId()+" they should be in format : hh:mm:ss "
						+ "example : 12:00:00, 12:22:03, incorrect format : 2:1:4, 02:1:2");
			}
			tem.setArrivalTime(convertToDate(t.getArrivalTime()));
			tem.setDepartureTime(convertToDate(t.getDepartureTime()));
			if(tem.getArrivalTime().isAfter(tem.getDepartureTime()))
			{
				throw new Exception("Arrival Time cannot be greater than the departure time for train id: "+t.getId());
			}
			tem.setFare(t.getFare());
			tem.setTrainName(t.getTrainName());
			te.add(tem);
		}
		routeEntity.setListOfTrains(te);
		
		routeEntity=repo1.saveAndFlush(routeEntity);
		return routeEntity.getId();
	}
	
	//Requirement 2
	public Route getRoute(Integer routeId) throws NoRouteExistsException
	{
		Optional<RouteEntity> routeEntity=repo1.findById(routeId);
		if(!routeEntity.isPresent())
		{
			throw new NoRouteExistsException("RouteEntity.Doesnot.Exists");
		}
		return new ModelMapper().map(routeEntity.get(), Route.class);
	}
	
	//Requirement 3
		public List<Train> getListOfTrains(String source,String destination) throws NoTrainsException
		{
			List<TrainEntity> listoftrains=null;
			try {
				listoftrains = repo1.findTrainsForThisRoute(source, destination);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!listoftrains.isEmpty())
			{
				
				List<Train> listOfTrains=new ArrayList<Train>();
				for(TrainEntity te:listoftrains)
				{
					Train t=new ModelMapper().map(te, Train.class);
					listOfTrains.add(t);
				}
				return listOfTrains;
			}
			else
			{
				throw new NoTrainsException("NO.TRAINS.EXISTS");
			}
		}
	
	//Requirement 4
	public String updateRouteDetails(Integer routeId,String source,String destination) throws NoRouteExistsException
	{
		Optional<RouteEntity> routeEntity=repo1.findById(routeId);
		if(!routeEntity.isPresent())
		{
			throw new NoRouteExistsException("RouteEntity.Doesnot.Exists");
		}
		
		routeEntity.get().setSource(source);
		routeEntity.get().setDestination(destination);
		repo1.save(routeEntity.get());
		
		return "Route updated Successfully with source: "+source+" and destination: "+destination;
	}
	
	//Requirement 5
	public String deleteTrainInaParticularRoute(Integer routeId,Integer trainId) throws NoRouteExistsException, NoTrainsException, TrainCannotDeletedException
	{
		Optional<RouteEntity> routeEntity=repo1.findById(routeId);
		if(!routeEntity.isPresent())
		{
			throw new NoRouteExistsException("RouteEntity.Doesnot.Exists");
		}
		if(routeEntity.get().getListOfTrains().isEmpty())
		{
			throw new NoTrainsException("NO.Trains.Exists.Todelete");
		}
		
		for(TrainEntity t:routeEntity.get().getListOfTrains())
		{
			if(t.getId().intValue()==trainId.intValue())
			{
				routeEntity.get().getListOfTrains().remove(t);
				repo1.save(routeEntity.get());
				repo2.deleteById(trainId);
				return "Train with train id "+t.getId()+" deleted succesfully"; 
			}
		}
		throw new TrainCannotDeletedException("Train with trainId : "+trainId+" cannot be deleted as its not present");
		
	}
	
	public LocalTime convertToDate(String ldt)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime dateTime = LocalTime.parse(ldt, formatter);
		return dateTime;
	}
	
	//Requirement 6
	public String updateDetailsOfRoute(Integer routeId,Train train,boolean ua) throws Exception
	{
		Optional<RouteEntity> routeEntity=repo1.findById(routeId);
		if(!routeEntity.isPresent())
		{
			throw new NoRouteExistsException("RouteEntity.Doesnot.Exists");
		}
		
		
		if(ua)
		{
			if(routeEntity.get().getListOfTrains().isEmpty())
			{
				throw new NoTrainsException("NO.Trains.Exists.ToUpdate");
			}
			for(TrainEntity t:routeEntity.get().getListOfTrains())
			{
				if(t.getId().intValue()==train.getId().intValue())
				{
					t.setTrainName(train.getTrainName());
					t.setFare(train.getFare());
					t.setDepartureTime(convertToDate(train.getDepartureTime()));
					t.setArrivalTime(convertToDate(train.getArrivalTime()));
					repo1.save(routeEntity.get());
					return "Train with train id "+t.getId()+" Updated succesfully"; 
				}
			}
			throw new TrainCannotUpdatedException("Train with train id "+train.getId()+" cannot be Updated . not present in Database");
		}
		else
		{
			//System.out.println(train.getFare());
			Validator.validate(train);
			TrainEntity tp=new TrainEntity();
			tp.setId(train.getId());
			tp.setArrivalTime(convertToDate(train.getArrivalTime()));
			tp.setDepartureTime(convertToDate(train.getDepartureTime()));
			if(tp.getArrivalTime().isAfter(tp.getDepartureTime()))
			{
				throw new Exception("Arrival.After.Departure");
			}
			
			tp.setFare(train.getFare());
			tp.setTrainName(train.getTrainName());
			//System.out.println("ggg");
			for(TrainEntity re:routeEntity.get().getListOfTrains())
			{
				if(re.getId().intValue()==tp.getId().intValue())
				{
					throw new TrainAlreadyExistsException("Train.Already.Exists");
				}
			}
			System.out.println("ggg");
			
			routeEntity.get().getListOfTrains().add(tp);
			repo1.save(routeEntity.get());
			return "Train with Train id: "+train.getId()+" added successfully to route id."+routeId;
			
		}
		
	}
	
	
	
	//Requirement 7
	public String createTrainDetails(Train train) throws TrainAlreadyExistsException
	{
			Optional<TrainEntity> t=repo2.findById(train.getId());
			
			if(t.isPresent())
			{
				throw new TrainAlreadyExistsException("Train.Already.Exists");
			}
		
			TrainEntity te=new TrainEntity();
			te.setId(train.getId());
			te.setTrainName(train.getTrainName());
			te.setFare(train.getFare());
			te.setArrivalTime(convertToDate(train.getArrivalTime()));
			te.setDepartureTime(convertToDate(train.getDepartureTime()));
			
		//TrainEntity te=new ModelMapper().map(train, TrainEntity.class);
		/*
		 * if(te==null) { System.out.println("Ye bkl"); }
		 */
		
			repo2.saveAndFlush(te);
		
			// TODO Auto-generated catch block
			
		
		return "Train added successfully with trainId: "+te.getId();
	}
	
	//Requirement 8
	public String updateTrainDetailsWithFare(Integer trainId,Double fare) throws NoTrainsException 
	{
		System.out.println("here is ck");
		Optional<TrainEntity> t=repo2.findById(trainId);
		if(!t.isPresent())
		{
			System.out.println("here is c");
			throw new NoTrainsException("Train.NotExists.UpdateFare");
		}
		System.out.println("here is ck");
		t.get().setFare(fare);
		repo2.save(t.get());
		return "Train with train Id: "+trainId+" updated successfully with fare "+fare;
	}

	
}
