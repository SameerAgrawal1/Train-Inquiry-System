package com.Rail.TrainInquiry.Exceptions;

public class TrainAlreadyExistsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrainAlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}
	
	public TrainAlreadyExistsException(String msg)
	{
		super(msg);
	}

}
