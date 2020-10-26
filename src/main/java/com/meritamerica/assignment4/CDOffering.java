package com.meritamerica.assignment4;

public class CDOffering {
	/**
	 * Instance Variables for CD Offering 
	 */
	int term;
	double interestRate;
	/**
	 * Constructor for offering for a CD
	 * @param term
	 * @param interestRate
	 */
	public CDOffering(int term, double interestRate){
		this.term = term;
		this.interestRate = interestRate;
	}
	/**
	 * Read a String  that as the data of a CD Offering  and returning the new account with the pass through information 
	 * @param cdOfferingDataString
	 * @return
	 */
	public static CDOffering readFromString(String cdOfferingDataString){
		try {
			String[] temp = cdOfferingDataString.split(",");
			int tempTerm = Integer.valueOf(temp[0]);
			double tempInterestRate = Double.valueOf(temp[1]);
			CDOffering newAccount = new CDOffering(tempTerm, tempInterestRate);
			return newAccount;
		}
		catch(Exception exception) {
			throw new NumberFormatException();
		}
	}
	/**
	 * Getter for term 
	 * @return
	 */
	public int getTerm() {
		return term;
	}
	/**
	 * A method to write string data of CD offering and returning that data when called on. 
	 * @return
	 */
	public String writeToString() {
		StringBuilder toString = new StringBuilder();
		toString.append(term).append(",").append(interestRate);
		return toString.toString();
	}
	/**
	 * Getter for Interest Rate
	 * @return
	 */
	public double getInterestRate() {
		return interestRate;
	}

}