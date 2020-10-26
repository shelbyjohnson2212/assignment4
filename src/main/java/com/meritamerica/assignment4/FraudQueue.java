package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.List;

class FraudQueue {
	 
private List<Transaction> transactions = new ArrayList<Transaction>();
	/**
	 * A Constructor for Fraud Queue 
	 */
	FraudQueue(){
		
	}
	/**
	 * Method to add transaction to Fraud Queue
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	/**
	 * Return the transactions to the Fraud Queue
	 * @return
	 */
	public List<Transaction> getTransaction() {
		return transactions;
	}
	 
	 
	 
}