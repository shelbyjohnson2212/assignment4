package com.meritamerica.assignment4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BankAccount {
	/**
	 * Instance Variables for our class
	 */
	double balance;
	double interestRate;
	Date accoutOpenedOn;
	long accountNumber;
	List<Transaction> transactions = new ArrayList<Transaction>();
	/**
	 * Constructor to help Checking and Savings Accounts
	 * @param balance
	 * @param interestRate
	 */
	public BankAccount(double balance, double interestRate){
		this.balance = balance;
		this.interestRate = interestRate;
		accoutOpenedOn = new Date();
		accountNumber = MeritBank.getNextAccountNumber();
	}
	/**
	 * Used to cover all basis
	 * @param balance
	 * @param interestRate
	 * @param accountOpenedOn
	 */
	public BankAccount(double balance, double interestRate, Date accountOpenedOn){
		this.balance = balance;
		this.interestRate = interestRate;
		accoutOpenedOn = accountOpenedOn;
		accountNumber = MeritBank.getNextAccountNumber();
	}
	/**
	 * Used to cover all basis
	 * @param accountNumber
	 * @param balance
	 * @param interestRate
	 * @param accountOpenedOn
	 */
	public BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn){
		this.balance = balance;
		this.interestRate = interestRate;
		accoutOpenedOn = accountOpenedOn;
		this.accountNumber = accountNumber;
	}
	/**
	 * Getters for the instance variables 
	 * @return instance variables 
	 */
	public long getAccountNumber() {
		return accountNumber;
	}
	
	public double getBalance() {
		return balance;
		
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public Date getOpenedOn(){
		return accoutOpenedOn;
	}
	/**
	 * A method to withdraw an amount,able to process request as longs as amount to be withdrawn is greater than zero
	 * and less than what is available 
	 * @param amount
	 * @return
	 */
	public boolean withdraw(double amount) {
		if(amount > 0 && amount < balance) {
			balance -= amount;
			return true;
		}
		return false;
	}
	/**
	 * A method to deposit an amount, able to process request if amount is greater than zero 
	 * @param amount
	 * @return
	 */
	public boolean deposit (double amount) {
		if(amount > 0) {
			balance += amount;
			return true;
		}
		return false;
	}
	/**
	 * A method to calculate the future value for the accounts 
	 * @param years
	 * @return
	 */
	public double futureValue(int years) {
		return balance * Math.pow(1 + interestRate, years);
	}
	/**
	 *  Method for  writing information for a Bank Account into a String
	 * @return String information
	 */
	public String writeToString() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder toString = new StringBuilder();
		toString.append(accountNumber).append(",");
		toString.append(balance).append(",");
		toString.append(interestRate).append(",");
		toString.append(format.format(accoutOpenedOn));
		return toString.toString();
	}
	/**
	 * Adds a Transaction to a list for Fraud Queue 
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	public List<Transaction> getTransactions(){
		return transactions;
	}

}