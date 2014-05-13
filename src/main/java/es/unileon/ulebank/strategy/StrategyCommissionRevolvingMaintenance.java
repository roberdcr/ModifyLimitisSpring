package es.unileon.ulebank.strategy;

import es.unileon.ulebank.exceptions.CommissionException;


/**
 * @class StrategyCommissionRevolvingMaintenance
 * @author Rober dCR
 * @date 20/03/2014
 * @brief Class that obtain Commision of Maintenance in Revolving Cards
 */
public class StrategyCommissionRevolvingMaintenance implements
		StrategyCommissionRevolving {

	/**
	 * Commission establish by the employees
	 */
	private float quantity;
	
	/**
	 * Class constructor
	 * @param quantity
	 * @throws CommissionException 
	 */
	public StrategyCommissionRevolvingMaintenance(float quantity) throws CommissionException{
		if (quantity >= 0)
			this.quantity = quantity;
		else
			throw new CommissionException("Commission can't been negative.");
	}
	
	public float calculateCommission() {
		return this.quantity;
	}
}
