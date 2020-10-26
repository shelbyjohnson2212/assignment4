package com.meritamerica.assignment4;

import java.util.Arrays;

public class AccountHolder implements Comparable<AccountHolder>{
	
	String firstName;
	String middleName;
	String lastName;
	String ssn;
	CheckingAccount[] checkingArray = new CheckingAccount[0];
	SavingsAccount[] savingsArray = new SavingsAccount[0];
	CDAccount[] cdAccountArray = new CDAccount[0];
	
	
	public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
	}
	
	

	public static AccountHolder readFromString(String accountHolderData) throws Exception{
		try {
			String[] holding = accountHolderData.split(",");
			AccountHolder newAccount =  new AccountHolder(holding[0], holding[1], holding[2], holding[3]);
			return newAccount;
		}
		catch(Exception exception) {
			throw new Exception();
		}
	}
	
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		
		CheckingAccount newAccount = new CheckingAccount(openingBalance);
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
		CheckingAccount[] holding = new CheckingAccount[checkingArray.length + 1];
		for(int i = 0; i<checkingArray.length; i++) {
			holding[i] = checkingArray[i];
		}
		holding[holding.length - 1] = newAccount;
		checkingArray = holding;
		return newAccount;
	}
	
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitException{
		if(getCheckingBalance() + getSavingsBalance() + checkingAccount.getBalance() >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		DepositTransaction transaction = new DepositTransaction(checkingAccount, checkingAccount.getBalance());
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
		
		
		CheckingAccount[] holding = new CheckingAccount[checkingArray.length + 1];
		for(int i = 0; i<checkingArray.length; i++) {
			holding[i] = checkingArray[i];
		}
		holding[holding.length - 1] = checkingAccount;
		checkingArray = holding;
		return checkingAccount;
	}
	
	
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitException{
		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		SavingsAccount newAccount = new SavingsAccount(openingBalance);
		
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		
		SavingsAccount[] holding = new SavingsAccount[savingsArray.length + 1];
		for(int i = 0; i<savingsArray.length; i++) {
			holding[i] = savingsArray[i];
		}
		holding[holding.length - 1] = newAccount;
		savingsArray = holding;
		return newAccount;
	}
	
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException, ExceedsFraudSuspicionLimitException, NegativeAmountException{
		if(getCheckingBalance() + getSavingsBalance() + savingsAccount.getBalance()>= 250000) {
			throw new ExceedsCombinedBalanceLimitException("Aggregate balance of your Checking and Savings accounts exceeds $250,000.");
		}
		DepositTransaction transaction = new DepositTransaction(savingsAccount, savingsAccount.getBalance());
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
		
		
		SavingsAccount[] holding = new SavingsAccount[savingsArray.length + 1];
		for(int i = 0; i<savingsArray.length; i++) {
			holding[i] = savingsArray[i];
		}
		holding[holding.length - 1] = savingsAccount;
		savingsArray = holding;
		return savingsAccount;

	}
	
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws NegativeAmountException, ExceedsFraudSuspicionLimitException{
		if(offering == null) {
			return null;
		}
		CDAccount newAccount = new CDAccount(offering, openingBalance);
		DepositTransaction transaction = new DepositTransaction(newAccount, openingBalance);
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		
	
		CDAccount[] holding = Arrays.copyOf(cdAccountArray, cdAccountArray.length+1);
		
		for(int i = 0; i<cdAccountArray.length; i++) {
			holding[i] = cdAccountArray[i];
		}
		holding[holding.length - 1] = newAccount;
		cdAccountArray = holding;
		return newAccount;
	}

	public CDAccount addCDAccount(CDAccount cdAccount) throws NegativeAmountException, ExceedsFraudSuspicionLimitException {
		DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());
		try{
			MeritBank.processTransaction(transaction);
		}
		catch(NegativeAmountException exception) {
			throw new NegativeAmountException("Can not deposit/withdraw a negative amount");
		}
		catch(ExceedsFraudSuspicionLimitException exception) {
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		catch(Exception exception) {
			
		}
		CDAccount[] holding = Arrays.copyOf(this.cdAccountArray,this.cdAccountArray.length+1);
		for(int i = 0; i<this.cdAccountArray.length; i++) {
			holding[i] = this.cdAccountArray[i];
		}
		holding[holding.length - 1] = cdAccount;
		this.cdAccountArray = holding;
		return cdAccount;
	}

	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}

	@Override
	public int compareTo(AccountHolder account) {
		if(this.getCombinedBalance() > account.getCombinedBalance()) {
			return 1;
		}
		return -1;
	}
	

	public String getFirstName() {
		return firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public CheckingAccount[] getCheckingAccounts() {
		return checkingArray;
	}
	
	public int getNumberOfCheckingAccounts() {
		return checkingArray.length;
	}
	
	public double getCheckingBalance() {
		int i;
		double total = 0.0;
		for(i = 0; i < checkingArray.length; i++) {
			total += checkingArray[i].getBalance();
		}
		return total;
	}
	
	public SavingsAccount[] getSavingsAccounts() {
		return savingsArray;
	}

	public int getNumberOfSavingsAccounts() {
		return savingsArray.length;
	}
	
	public double getSavingsBalance() {
		double total = 0.0;
		for(SavingsAccount balance : savingsArray) {
			total += balance.getBalance();
		}
		return total;
	}
	
	public CDAccount[] getCDAccounts() {
		return cdAccountArray;
	}

	public int getNumberOfCDAccounts() {
		return cdAccountArray.length;
	}

	public double getCDBalance() {
		double total = 0.0;
		for(CDAccount balance : cdAccountArray) {
			total += balance.getBalance();
		}
		return total;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}
	
	public String writetoString() {
		StringBuilder toString = new StringBuilder();
		toString.append(firstName).append(",");
		toString.append(middleName).append(",");
		toString.append(lastName).append(",");
		toString.append(ssn);
		return toString.toString();
	}
}