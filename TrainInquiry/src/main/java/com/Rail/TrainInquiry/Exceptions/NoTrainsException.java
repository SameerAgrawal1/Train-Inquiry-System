package com.Rail.TrainInquiry.Exceptions;

public class NoTrainsException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTrainsException() {
		// TODO Auto-generated constructor stub
	}
	
	public NoTrainsException(String msg)
	{
		super(msg);
	}

}
