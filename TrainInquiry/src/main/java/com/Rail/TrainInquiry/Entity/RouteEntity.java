package com.Rail.TrainInquiry.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Route")
public class RouteEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String source;
	private String destination;
	
	@OneToMany(targetEntity = TrainEntity.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="route_id")
	private List<TrainEntity> listOfTrains;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<TrainEntity> getListOfTrains() {
		return listOfTrains;
	}

	public void setListOfTrains(List<TrainEntity> listOfTrains) {
		this.listOfTrains = listOfTrains;
	}

	

}
