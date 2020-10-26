package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class CDAccount extends BankAccount {
	
	CDOffering offering;
	private int term;
	Date date;
	
	public CDAccount(CDOffering offering, double openBalance) {
		super(openBalance,offering.getInterestRate());
		this.offering = offering;
		this.term = offering.getTerm();
	}
	
	public CDAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn, int term) {
		super(accountNumber, balance,interestRate,accountOpenedOn);
		this.term = term;
	}
	
	public int getTerm() {
		return this.term;
	}
	
	public Date getStartDate(){
		return date;
	}
	
	public double futureValue() {
		return super.futureValue(term);
	}
	
	@Override
	public boolean withdraw(double amount) {
        return false;
    }
    
    public boolean deposit(double amount) {
    	return false;
    }
    
    public static CDAccount readFromString(String accountData)throws ParseException, NumberFormatException {
    	String [] holding = accountData.split(",");
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    	//[0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which is SimpleDate, [4] is term
    	long accountNumber = Long.parseLong(holding[0]);
    	double balance = Double.parseDouble(holding[1]);
    	double interestRate = Double.parseDouble(holding[2]);
    	Date accountOpenedOn = date.parse(holding[3]);
    	int term = Integer.parseInt(holding[4]);
    	CDAccount newCDAccount = new CDAccount(accountNumber,balance,interestRate,accountOpenedOn,term);
    	return newCDAccount;
    }
    
    public String writeToString() {
    	StringBuilder override = new StringBuilder();
    	override.append(writeToString()).append(",");
    	override.append(term);
    	return override.toString();
    }

}