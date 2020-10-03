package com.Rail.TrainInquiry.Model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Route 
{
	private Integer id;
	@NotEmpty(message = "Source of the Route cannot be Empty.")
	@Pattern(regexp = "[A-Za-z]+",message = "Source string should only contain characters.")
	private String source;
	@NotEmpty(message = "Destination of the Route cannot be Empty.")
	@Pattern(regexp = "[A-Za-z]+",message = "Destination string should only contain characters.")
	private String destination;
	@NotEmpty(message = "Empty list of trains not allowed.")
	private List<Train> trains;
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
	public List<Train> getTrains() {
		return trains;
	}
	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}

}
