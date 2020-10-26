package com.meritamerica.assignment4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MeritBank {
	/**
	 * The Variables for the class
	 */
	static long accountNumber;
	static AccountHolder AccountHolders[] = new AccountHolder[0];
	static CDOffering CDOfferings[] = new CDOffering[0];
	static FraudQueue fraudQueue = new FraudQueue();
	/**
	 * A method that adds a new Account Holder to a list of Account Holders
	 * @param accountHolder
	 */
	public static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] holder = Arrays.copyOf(AccountHolders, AccountHolders.length+1);
		for(int i = 0; i<AccountHolders.length; i++) {
			holder[i] = AccountHolders[i];
		}
		holder[holder.length - 1] = accountHolder;
		AccountHolders = holder;
	}
	/**
	 * Get the Account Holders array and return the account holders
	 * @return
	 */
	public static AccountHolder[] getAccountHolders() {
		return AccountHolders;
	}
	/**
	 * Get the CD Offerings and return the offerings
	 * @return
	 */
	public static CDOffering[] getCDOfferings() {
		return CDOfferings;
	}
	/**
	 * A method that finds the Best Offering for a newly opened CD Account.
	 * We set the best to be zero and run an enhanced loop to go through the CD Offering Array and check to see which of our offerings is the best for an Account Holder.
	 * @param depositAmount
	 * @return best offering 
	 */
	public static CDOffering getBestCDOffering(double depositAmount) {
		double best = 0.0; 
		CDOffering bestOffering = null;
		if(CDOfferings == null) {
			return null;
		}
		for(CDOffering offerings :  CDOfferings) {
			if(futureValue(depositAmount, offerings.getInterestRate(), offerings.getTerm()) > best) {
				bestOffering = offerings;
				best = futureValue(depositAmount, offerings.getInterestRate(), offerings.getTerm());
			}
		}
		return bestOffering;
	}
	/**
	 * After find the best offering for Account Holder, we run through the array again to find the second best offering.
	 * Then set that as the best offering
	 * @param depositAmount
	 * @return second best offering
	 */
	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if(CDOfferings == null) {
			return null;
		}
		CDOffering bestOffering = null;
		double best = 0.0; 
		CDOffering secondBestOffering = null;
		
		for(CDOffering offerings :  CDOfferings) {
			if(futureValue(depositAmount, offerings.getInterestRate(), offerings.getTerm()) > best) {
				secondBestOffering = bestOffering;
				bestOffering = offerings;
				best = futureValue(depositAmount, offerings.getInterestRate(), offerings.getTerm());
			}
		}
		return secondBestOffering;
	}
	/**
	 * Constructor to clear the offerings offered. Sets them to null, because offerings change daily
	 */
	public static void clearCDOfferings() {
		CDOfferings = null;
	}
	/**
	 * Setter to set offerings into the Array 
	 * @param offerings
	 */
	public static void setCDOfferings(CDOffering[] offerings) {
		CDOfferings = offerings;
	}
	/**
	 * Get the next account number by taking previous account number and adding it by 1.
	 * @return Current number
	 */
	public static long getNextAccountNumber() {
		long currentAccountNumber = accountNumber;
		accountNumber += 1;
		return currentAccountNumber;
	}
	/**
	 * Set the next account number to be equal to account number.Updating the variable 
	 * @param nextAccountNumber
	 */
	public static void setNextAccountNumber(long nextAccountNumber) {
		accountNumber = nextAccountNumber;
	}
	/**
	 * Read from an external file and construct Account Holders with associated accounts
	 * @param fileName
	 * @return
	 */
	public static boolean readFromFile(String fileName) {
		CDOfferings = new CDOffering[0];
		setNextAccountNumber(0);
		AccountHolders = new AccountHolder[0];
		fraudQueue = new FraudQueue();
		Set<String> transactions = new HashSet<String>();
		try(BufferedReader nextLine = new BufferedReader(new FileReader(fileName))) {
			
			setNextAccountNumber(Long.valueOf(nextLine.readLine()));
			int numberOfCDOfferings = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i < numberOfCDOfferings; i++) {
				CDOfferings = Arrays.copyOf(CDOfferings, CDOfferings.length + 1);
				CDOfferings[CDOfferings.length - 1] = CDOffering.readFromString(nextLine.readLine());
			}
			int numberOfAccountHolders = Integer.valueOf(nextLine.readLine());
			
			for(int i = 0; i < numberOfAccountHolders; i++) {
				AccountHolder nextAccountHolder = AccountHolder.readFromString(nextLine.readLine());
				MeritBank.addAccountHolder(nextAccountHolder);	
				int numberOfCheckingAccounts = Integer.valueOf(nextLine.readLine());
				for(int c = 0; c < numberOfCheckingAccounts; c++) {
					nextAccountHolder.addCheckingAccount(CheckingAccount.readFromString(nextLine.readLine()));
					int numberOfCheckingTransactions = Integer.valueOf(nextLine.readLine());
					
					for(int ct = 0; ct < numberOfCheckingTransactions; ct++) {	
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfSavingsAccounts = Integer.valueOf(nextLine.readLine());
				
				for(int s = 0; s < numberOfSavingsAccounts; s++) {
					nextAccountHolder.addSavingsAccount(SavingsAccount.readFromString(nextLine.readLine()));
					int numberOfSavingsTransactions = Integer.valueOf(nextLine.readLine());
					for(int st = 0; st < numberOfSavingsTransactions; st++) {
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfCDAccounts = Integer.valueOf(nextLine.readLine());
				for(int cd = 0; cd < numberOfCDAccounts; cd++) {
					nextAccountHolder.addCDAccount(CDAccount.readFromString(nextLine.readLine()));
					int numberCDTransactions = Integer.valueOf(nextLine.readLine());
					for(int cdt = 0; cdt < numberCDTransactions; cdt++) {
						transactions.add(nextLine.readLine());
					}
				}
					
			}
			int numberOfFraudQueueTransactions = Integer.valueOf(nextLine.readLine());
			for(int fqt = 0; fqt < numberOfFraudQueueTransactions; fqt++) {
				fraudQueue.addTransaction(Transaction.readFromString(nextLine.readLine()));
			}
			System.out.println(transactions.size());
			for(String transaction : transactions) {
				Transaction newTran = Transaction.readFromString(transaction);
				if(newTran.getSourceAccount() == null) {
					newTran.getTargetAccount().addTransaction(newTran);
				}
				else {
					newTran.getTargetAccount().addTransaction(newTran);
					newTran.getSourceAccount().addTransaction(newTran);
				}
			}
			return true;
		}catch(Exception exception) {
			return false;
		}

	}
	/**
	 * Writing the data for an Account Holder to an external file.
	 * @param fileName
	 * @return
	 */
	public static boolean writeToFile(String fileName) {
		try (BufferedWriter nextLine = new BufferedWriter(new FileWriter(fileName))){
        	nextLine.write(String.valueOf(accountNumber));
        	nextLine.newLine();
        	nextLine.write(String.valueOf(CDOfferings.length));
        	nextLine.newLine();
        	for(int cdo = 0; cdo < CDOfferings.length; cdo++) {
        		nextLine.write(CDOfferings[cdo].writeToString());
            	nextLine.newLine();
        	}
        	nextLine.write(String.valueOf(AccountHolders.length));
        	nextLine.newLine();
        	for(int a = 0; a < AccountHolders.length; a++) {
        		nextLine.write(AccountHolders[a].writetoString());
        		nextLine.newLine();
        		nextLine.write(String.valueOf(AccountHolders[a].getCheckingAccounts().length));
        		nextLine.newLine();
        		for(int c = 0; c<AccountHolders[a].getCheckingAccounts().length; c++ ) {
        			nextLine.write(AccountHolders[a].getCheckingAccounts()[c].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(AccountHolders[a].getCheckingAccounts()[c].getTransactions().size()));
        			nextLine.newLine();
        			int ctl = AccountHolders[a].getCheckingAccounts()[c].getTransactions().size();
        			for(int ct = 0; ct < ctl; ct++) {
        				nextLine.write(AccountHolders[a].getCheckingAccounts()[c].getTransactions().get(ct).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(AccountHolders[a].getSavingsAccounts().length));
        		nextLine.newLine();
        		for(int s = 0; s<AccountHolders[a].getSavingsAccounts().length; s++ ) {
        			nextLine.write(AccountHolders[a].getSavingsAccounts()[s].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(AccountHolders[a].getSavingsAccounts()[s].getTransactions().size()));
        			nextLine.newLine();
        			int stl = AccountHolders[a].getSavingsAccounts()[s].getTransactions().size();
        			for(int st = 0; st < stl; st++) {
        				nextLine.write(AccountHolders[a].getSavingsAccounts()[s].getTransactions().get(st).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(AccountHolders[a].getCDAccounts().length));
        		nextLine.newLine();
        		for(int cd = 0; cd<AccountHolders[a].getCDAccounts().length; cd++ ) {
        			nextLine.write(AccountHolders[a].getCDAccounts()[cd].writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(AccountHolders[a].getCDAccounts()[cd].getTransactions().size()));
        			nextLine.newLine();
        			int cdtl = AccountHolders[a].getCDAccounts()[cd].getTransactions().size();
        			for(int cdt = 0; cdt < cdtl; cdt++) {
        				nextLine.write(AccountHolders[a].getCDAccounts()[cd].getTransactions().get(cdt).writeToString());
        				nextLine.newLine();
        			}
        		}
        	}
        	nextLine.write(String.valueOf(fraudQueue.getTransaction().size()));
        	nextLine.newLine();
        	for(int fq = 0; fq < fraudQueue.getTransaction().size(); fq++) {
        		nextLine.write(fraudQueue.getTransaction().get(fq).writeToString());
        		nextLine.newLine();
        	}
        	return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	public static AccountHolder[] sortAccountHolders() {
		Arrays.sort(AccountHolders);
		return AccountHolders;
	}

	public static double totalBalances() {
		double total = 0.0;
		for(AccountHolder account : AccountHolders) {
			total += account.getCombinedBalance();
		}
		return total;
		
	}
	/**
	 * A method that verifies that a transaction follows bank protocols for simple transactions 
	 * @param transaction
	 * @return
	 * @throws NegativeAmountException
	 * @throws ExceedsAvailableBalanceException
	 * @throws ExceedsFraudSuspicionLimitException
	 */
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException,
	ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException{
		BankAccount source = transaction.getSourceAccount();
		BankAccount target = transaction.getTargetAccount();
		
		if(source == null) {
			if(transaction instanceof WithdrawTransaction) {
				if(transaction.getAmount() < 0) {
					throw new NegativeAmountException("Can not withdraw a negative amount");
				}
				if(transaction.getAmount() + target.getBalance() < 0) {
					throw new ExceedsAvailableBalanceException("Insufficient Balance");
				}
				if(transaction.getAmount() < -1000) {
					fraudQueue.addTransaction(transaction);
					throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
				}
				return true;
			}
			if(transaction.getAmount() < 0) {
				throw new NegativeAmountException("Can not withdraw a negative amount");
			}
			if(transaction.getAmount() > 1000) {
				fraudQueue.addTransaction(transaction);
				throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
			}
			return true;
		}
		if(source.getBalance() < transaction.getAmount()) {
			throw new ExceedsAvailableBalanceException("Insufficient Balance");
		}
		if(transaction.getAmount() < 0) {
			throw new NegativeAmountException("Can not withdraw a negative amount");
		}
		if(transaction.getAmount() > 1000) {
			fraudQueue.addTransaction(transaction);
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		return true;
	}
	/**
	 * A method tht passes in the recursive future value method 
	 * @param presentValue
	 * @param interestRate
	 * @param term
	 * @return
	 */
	public static double futureValue(double presentValue, double interestRate, int term) {
		return recursiveFutureValue(presentValue, term, interestRate);
	}
	/**
	 * Recursive method for future value.
	 * @param amount
	 * @param years
	 * @param interestRate
	 * @return
	 */
	//Utilized the recursive method in place of math.pow()
	public static double recursiveFutureValue(double amount, int years,
			double interestRate) {
		if(years > 0) {
			double newAmount = amount + (amount * interestRate);
			return recursiveFutureValue(newAmount, years - 1, interestRate);
		}
		return amount;
	}
	/**
	 * Calls on fraud queue
	 * @return
	 */
	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	/**
	 * A method that go's through the account holder array and gets the information for each account and return it. 
	 * @param accountId
	 * @return
	 */
	public static BankAccount getBankAccount(long accountId) {
		for(AccountHolder account : AccountHolders) {
			for(int c = 0; c < account.getCheckingAccounts().length; c++) {
				if(accountId == account.getCheckingAccounts()[c].getAccountNumber()) {
					return account.getCheckingAccounts()[c];
				}
			}
			for(int s = 0; s < account.getSavingsAccounts().length; s++) {
				if(accountId == account.getSavingsAccounts()[s].getAccountNumber()) {
					return account.getSavingsAccounts()[s];
				}
			}
			for(int cda = 0; cda < account.getCDAccounts().length; cda++) {
				if(accountId == account.getCDAccounts()[cda].getAccountNumber()) {
					return account.getCDAccounts()[cda];
				}
			}
		
		}
		return null;
	}
}