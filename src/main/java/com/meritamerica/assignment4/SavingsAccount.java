package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends BankAccount{
	/**
	 * Static variable for interest rate
	 */
	static double interestRate = 0.01;
	/**
	 * A Constructor that is passing to parent class
	 * @param openingBalance
	 */
	public SavingsAccount(double openingBalance){
		super(openingBalance, interestRate);
	}
	/**
	 * A constructor that is passing to parent class.
	 * @param accountNumber
	 * @param balance
	 * @param interestRate
	 * @param openedOn
	 */
	public SavingsAccount(Long accountNumber, Double balance,
		Double interestRate, Date openedOn) {
		super(accountNumber, balance, interestRate, openedOn);
	}
	/**
	 * Read from String , gets data thats being passed through, and returns new account with the information
	 * @param accountData
	 * @return
	 * @throws ParseException
	 */
	public static SavingsAccount readFromString(String accountData) throws ParseException{
		try {
			String[] holding = accountData.split(",");
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(holding[3]);
			SavingsAccount newAccount =  new SavingsAccount(Long.valueOf(holding[0]), Double.valueOf(holding[1]),Double.valueOf(holding[2]), date);
			return newAccount;
		}
		catch(Exception exception) {
			throw new NumberFormatException();
		}
	}

}