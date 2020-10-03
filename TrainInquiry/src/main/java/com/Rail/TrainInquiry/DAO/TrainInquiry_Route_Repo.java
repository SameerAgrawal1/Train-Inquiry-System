package com.Rail.TrainInquiry.DAO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Rail.TrainInquiry.Entity.RouteEntity;
import com.Rail.TrainInquiry.Entity.TrainEntity;

public interface TrainInquiry_Route_Repo extends JpaRepository<RouteEntity, Integer>
{
	@Query("SELECT R.listOfTrains FROM RouteEntity R WHERE R.source=?1 AND R.destination=?2")
	public List<TrainEntity> findTrainsForThisRoute(String source,String destination);
}
