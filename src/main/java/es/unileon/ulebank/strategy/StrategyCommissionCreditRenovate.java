package es.unileon.ulebank.strategy;

import es.unileon.ulebank.exceptions.CommissionException;

/**
 * @class StrategyCommissionCreditRenovate
 * @author Rober dCR
 * @date 19/03/2014
 * @brief Class that obtain Commision of Renovate (substitution, theft or damage) in Debit Cards
 */
public class StrategyCommissionCreditRenovate implements StrategyCommissionCredit {

	/**
	 * Commission establish by the employee
	 */
	private float quantity; 

	/**
	 * Class constructor
	 * @param quantity
	 * @throws CommissionException 
	 */
	public StrategyCommissionCreditRenovate(float quantity) throws CommissionException{
		if (quantity >= 0)
			this.quantity = quantity;
		else
			throw new CommissionException("Commission can't been negative.");
	}

	public float calculateCommission() {
		return this.quantity;
	}

}
