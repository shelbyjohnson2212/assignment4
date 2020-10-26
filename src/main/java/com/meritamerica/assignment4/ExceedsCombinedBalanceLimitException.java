package com.meritamerica.assignment4;

public class ExceedsCombinedBalanceLimitException extends Exception {
	/**
	 * Static variable that cannot be  changed or call on from outside the class
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *A constructor for this exception
	 * @param errorMessage
	 */
	ExceedsCombinedBalanceLimitException(String errorMessage){
		super(errorMessage);
	}
}
