package es.unileon.ulebank.strategy;

import es.unileon.ulebank.exceptions.CommissionException;


/**
 * @class StrategyCommissionCreditMaintenance
 * @author Rober dCR
 * @date 19/03/2014
 * @brief Class that obtain Commision of Maintenance in Credit Cards
 */
public class StrategyCommissionCreditMaintenance implements StrategyCommissionCredit {


	/**
	 * Commission establish by the employee 
	 */
	private float quantity;
	
	/**
	 * Class constructor
	 * @param quantity
	 * @throws CommissionException 
	 */
	public StrategyCommissionCreditMaintenance(float quantity) throws CommissionException{
		if (quantity >= 0)
			this.quantity = quantity;
		else
			throw new CommissionException("Commission can't been negative.");
	}

	public float calculateCommission() {
		return this.quantity;
	}


}
