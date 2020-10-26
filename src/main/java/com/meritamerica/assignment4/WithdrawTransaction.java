package com.meritamerica.assignment4;

import java.util.Date;

public class WithdrawTransaction extends Transaction{
	/**
	 * Constructor  for the withdraw transaction object
	 */
	WithdrawTransaction(BankAccount targetAccount, double amount){
		account = null;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.date = new Date();
	}
}

      