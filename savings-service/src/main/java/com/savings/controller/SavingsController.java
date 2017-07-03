package com.savings.controller;

import javax.ws.rs.POST;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.savings.model.ChequingBody;
import com.savings.model.Deposit;
import com.savings.model.DepositBody;
import com.savings.model.SavingsRecord;
import com.savings.model.Withdrawal;
import com.savings.model.Savings;

@RestController
@RequestMapping("/savings")
public class SavingsController {

	Savings mySavings;

	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
	@POST
	public Deposit depositScheduled(@RequestBody DepositBody body) {

		if (mySavings == null) {
			mySavings = new Savings();
		}
		Deposit deposit = new Deposit();
		deposit.isScheduled = true;
		deposit.amount = body.amount;
		deposit.date = body.month + "/" + body.year;
		mySavings.scheduledDeposit.put(body.month, deposit);
		return deposit;
	}

	@RequestMapping("/test")
	public String test() {
		return "Health Check!Server is running";
	}

	@RequestMapping(value = "/additionaldeposit")
	@POST
	public Deposit depositAdditional(@RequestBody DepositBody body) {

		if (mySavings == null) {
			mySavings = new Savings();
		}
		Deposit deposit = new Deposit();
		deposit.isScheduled = false;
		deposit.amount = body.amount;
		deposit.date = body.month + "/" + body.year;
		mySavings.additionalDeposit.put(body.month, deposit);
		if (mySavings.scheduledDeposit.get(body.month) != null) {
			Deposit updated = new Deposit();
			updated.amount = mySavings.scheduledDeposit.get(body.month).amount + body.amount;
			updated.date = deposit.date;
			updated.isScheduled = false;
			return updated;
		}
		return deposit;
	}

	@RequestMapping("/savings")
	public Savings getSavings() {

		return mySavings;
	}

	@RequestMapping(value = "/saveallrecord")
	@POST
	public Savings saveAllSavingsRecord(@RequestBody SavingsRecord savings) {
		mySavings = new Savings();
		for (Integer i = 0; i < savings.data.size(); i++) {
			Deposit scheduledDeposit = new Deposit();
			scheduledDeposit.amount = savings.data.get(i).scheduledDeposit;
			scheduledDeposit.date = savings.data.get(i).month + "/" + savings.data.get(i).year;
			scheduledDeposit.isScheduled = true;
			mySavings.scheduledDeposit.put(i, scheduledDeposit);

			Deposit additionalDeposit = new Deposit();
			additionalDeposit.amount = savings.data.get(i).additionalDeposit;
			additionalDeposit.date = savings.data.get(i).month + "/" + savings.data.get(i).year;
			additionalDeposit.isScheduled = false;
			mySavings.additionalDeposit.put(i, additionalDeposit);
			
			Withdrawal withdrawal = new Withdrawal();
			withdrawal.amount = savings.data.get(i).withDrawal;
			withdrawal.date = savings.data.get(i).month + "/" + savings.data.get(i).year;
			mySavings.withDrawal.put(i, withdrawal);
			
			
			Deposit totalDeposit = new Deposit();
			totalDeposit.amount = savings.data.get(i).scheduledDeposit 
					+ savings.data.get(i).additionalDeposit - savings.data.get(i).withDrawal;
			totalDeposit.date = savings.data.get(i).month + "/" + savings.data.get(i).year;
			totalDeposit.isScheduled = false;
			mySavings.totalDeposit.put(i, totalDeposit);
			
			

		}
		
		return mySavings;
	}

	/*
	 * @param
	 * 
	 * @DONT CHANGE THIS ONE- PLEASE INGNORE. THIS API IS FOR MY TRIAL AND ERROR
	 */

	@RequestMapping("/getChequingCalculations")
	@POST
	public Deposit getChequingCalculations(@RequestBody ChequingBody body) {
		return null;

	}
}
