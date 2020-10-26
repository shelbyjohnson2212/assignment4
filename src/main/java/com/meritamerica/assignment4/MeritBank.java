package com.meritamerica.assignment4;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.io.BufferedReader;

class MeritBank {
	
	private static long nextAccountNumber = 84920570;
	private static AccountHolder AccountHoldersArray[] = new AccountHolder[0];
	private static CDOffering CDOfferingsArray[] = new CDOffering[0];

	public static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] newAccountHolderArray = new AccountHolder[AccountHoldersArray.length+1];
			for (int i = 0; i < newAccountHolderArray.length-1; i++) {
				newAccountHolderArray[i] = AccountHoldersArray[i];
			}
		AccountHoldersArray = newAccountHolderArray;
		AccountHoldersArray[AccountHoldersArray.length-1] = accountHolder;
	}
	
	public static AccountHolder[] getAccountHolders() {
		return AccountHoldersArray;
	}

	public static CDOffering[] getCDOfferings() {
		return CDOfferingsArray;
	}
	
	public static CDOffering getBestCDOffering(double depositAmount) {
		double best = 0.0; 
		CDOffering bestOffering = null;
		if(CDOfferingsArray == null) {
			return null;
		}
			for(CDOffering offering :  CDOfferingsArray) {
				if(futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > best) {
					bestOffering = offering;
					best = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
				}
			}
		return bestOffering;
	}

	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if(CDOfferingsArray == null) {
			return null;
		}
			CDOffering bestOffering = null;
			double best = 0.0; 
			CDOffering secondBestOffering = null;
		
				for(CDOffering offering :  CDOfferingsArray) {
					if(futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > best) {
						secondBestOffering = bestOffering;
						bestOffering = offering;
						best = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
					}
				}
		return secondBestOffering;
	}

	public static void clearCDOfferings() {
		CDOfferingsArray = null;
	}
	
	public static void setCDOfferings(CDOffering[] offerings) {
		CDOfferingsArray = offerings;
	}

	public static long getNextAccountNumber() {
		return nextAccountNumber;
	}

	public static double totalBalances() {
		double total = 0.0;
			for(AccountHolder accounts : AccountHoldersArray) {
				total += accounts.getCombinedBalance();
			}
		System.out.println("Total aggregate account balance: $" + total);
		return total;
		
	}

	public static double futureValue(double presentValue, double interestRate, int term) {
		double fValue = presentValue*Math.pow(1 + interestRate,  term);
		return fValue;
	}
	
	static boolean readFromFile( String fileName) {
		CDOffering offering[] = new CDOffering[0];	
		try {
			FileReader reader = new FileReader (fileName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			Long nextAccountNumber = Long.valueOf(bufferedReader.readLine());
			int holdOfferNum = Integer.valueOf(bufferedReader.readLine());
				for(int i = 0; i < holdOfferNum; i++) {
					offering = Arrays.copyOf(offering, offering.length + 1);
					offering [offering.length-1] = CDOffering.readFromString(bufferedReader.readLine());
				}
			int numOfAcctHld = Integer.valueOf(bufferedReader.readLine());
			AccountHolder [] newAccountHolders = new AccountHolder[numOfAcctHld];
			for(int i = 0; i<numOfAcctHld; i++) {				
				AccountHolder acctH = AccountHolder.readFromString(bufferedReader.readLine());
				int numOfChecking = Integer.valueOf(bufferedReader.readLine());
					for(int j = 0; j<numOfChecking; j++) {
						acctH.addCheckingAccount(CheckingAccount.readFromString(bufferedReader.readLine()));				
					}
				int numOfSavings = Integer.valueOf(bufferedReader.readLine());
					for(int k = 0; k<numOfSavings; k++) {
						acctH.addSavingsAccount(SavingsAccount.readFromString(bufferedReader.readLine()));				
					}
				int numOfCD = Integer.valueOf(bufferedReader.readLine());
					for(int m = 0; m<numOfCD; m++) {
						acctH.addCDAccount(CDAccount.readFromString(bufferedReader.readLine()));				
					}
				newAccountHolders[i] = acctH;	
			}
			setNextAccountNumber(nextAccountNumber);
			CDOfferingsArray = offering;
			AccountHoldersArray = newAccountHolders;
			reader.close();
			return true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	static boolean writeToFile( String fileName)  {
		try {
		FileWriter writer = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(String.valueOf(nextAccountNumber));
		bufferedWriter.newLine();
		bufferedWriter.write(String.valueOf(CDOfferingsArray.length));
		bufferedWriter.newLine();
			for(int i = 0; i<CDOfferingsArray.length;i++) {
				bufferedWriter.write(CDOfferingsArray[i].writeToString());
				bufferedWriter.newLine();
			}
			
		bufferedWriter.write(String.valueOf(AccountHoldersArray.length));
		bufferedWriter.newLine();
			for(int j = 0; j <AccountHoldersArray.length;j++) {
				bufferedWriter.write(AccountHoldersArray[j].writeToString());
				bufferedWriter.newLine();
				bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getCheckingAccounts().length));
				bufferedWriter.newLine();
					for(int k = 0; k < AccountHoldersArray[j].getCheckingAccounts().length;k++) {
							bufferedWriter.write(AccountHoldersArray[j].getCheckingAccounts()[k].writeToString());
							bufferedWriter.newLine();
					}
				bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getSavingsAccounts().length));
				bufferedWriter.newLine();
					for(int m = 0; m < AccountHoldersArray[j].getSavingsAccounts().length;m++) {
							bufferedWriter.write(AccountHoldersArray[j].getSavingsAccounts()[m].writeToString());
							bufferedWriter.newLine();
					}
				bufferedWriter.write(String.valueOf(AccountHoldersArray[j].getCDAccounts().length));
				bufferedWriter.newLine();
					for(int n = 0; n < AccountHoldersArray[j].getCDAccounts().length;n++) {
							bufferedWriter.write(AccountHoldersArray[j].getCDAccounts()[n].writeToString());
							bufferedWriter.newLine();
					}			
			}
			writer.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	
	}
	
	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(AccountHoldersArray);
			for(AccountHolder a : AccountHoldersArray) {
				System.out.println(a);
			}
		return AccountHoldersArray;
	}
	
	static void setNextAccountNumber( long accountNumber) {
		nextAccountNumber = accountNumber;
		
	}
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		
	}
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailbleBalanceException, ExceedsFraudSuspicionLimitException
	
	public static FraudQueue getFraudQueue() {
		
	}
	public static BankAccount getBankAccount(long accountId) {
		return null if account not found 
	}
	
	
}