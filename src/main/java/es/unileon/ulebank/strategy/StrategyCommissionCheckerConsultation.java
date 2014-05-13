package es.unileon.ulebank.strategy;

import es.unileon.ulebank.exceptions.CommissionException;


/**
 * @class StrategyCommissionCheckerConsultation
 * @author Rober dCR
 * @date 20/03/2014
 * @brief Class that obtain Commision of Consultation in Checker
 */
public class StrategyCommissionCheckerConsultation implements
		StrategyCommissionChecker {

	private float commission; //Commission establish by the employee
	
	public StrategyCommissionCheckerConsultation(float commission) throws CommissionException{
		if (commission >= 0)
			this.commission = commission;
		else
			throw new CommissionException("Commission can't been negative.");
	}
	
	public float calculateCommission() {
		return this.commission;
	}




}
