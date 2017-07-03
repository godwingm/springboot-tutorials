package com.savings.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Savings{

	public HashMap<Integer, Deposit> scheduledDeposit;
	public HashMap<Integer, Deposit> additionalDeposit;
	public HashMap<Integer, Withdrawal> withDrawal;
	public HashMap<Integer, Deposit> totalDeposit;
	
	public Savings() {
		scheduledDeposit = new HashMap<>();
		additionalDeposit = new HashMap<>();
		withDrawal = new HashMap<>();
		totalDeposit = new HashMap<>();
	}
}
