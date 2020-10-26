package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckingAccount extends BankAccount{
	/**
	 * Static variable for interest rate
	 */
	static double interestRate = 0.0001;
	/**
	 * A Constructor that is passing to parent class
	 * @param openingBalance
	 */
	public CheckingAccount(double openingBalance){
		super(openingBalance, interestRate);
	}
	/**
	 * A constructor that is passing to parent class.
	 * @param accountNumber
	 * @param balance
	 * @param interestRate
	 * @param openedOn
	 */
	public CheckingAccount(Long accountNumber, Double balance,
			Double interestRate, Date openedOn) {
			super(accountNumber, balance, interestRate, openedOn);
	}
	/**
	 * Read from String , gets data thats being passed through, and returns new account with the information
	 * @param accountData
	 * @return
	 * @throws ParseException
	 */
	public static CheckingAccount readFromString(String accountData) throws ParseException{
		try {
			String[] temp = accountData.split(",");
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(temp[3]);
			CheckingAccount newAccount =  new CheckingAccount(Long.valueOf(temp[0]), Double.valueOf(temp[1]),Double.valueOf(temp[2]), date);
			return newAccount;
		}
		catch(Exception exception) {
			throw new NumberFormatException();
		}
	}

}