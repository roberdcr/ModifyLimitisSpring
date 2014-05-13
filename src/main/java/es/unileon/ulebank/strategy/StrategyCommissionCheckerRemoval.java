package es.unileon.ulebank.strategy;

import es.unileon.ulebank.exceptions.CommissionException;


/**
 * @class StrategyCommissionCheckerRemoval
 * @author Rober dCR
 * @date 20/03/2014
 * @brief Class that obtain Commision of Removal in Checker
 */
public class StrategyCommissionCheckerRemoval implements StrategyCommission {

	/**
	 * Commission establish by the employee
	 */
	private float commission;  
	
	public StrategyCommissionCheckerRemoval(float commission) throws CommissionException{
		if (commission >= 0)
			this.commission = commission;
		else
			throw new CommissionException("Commission can't been negative.");
	}
	
	public float calculateCommission() {
		return this.commission;
	}

}
