package com.Rail.TrainInquiry.Exceptions;

public class NoRouteExistsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRouteExistsException() {
		// TODO Auto-generated constructor stub
	}
	
	public NoRouteExistsException(String msg)
	{
		super(msg);
	}

}
