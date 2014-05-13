package es.unileon.ulebank.strategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CommissionException;

/**
 * @class StrategyCommissionDebitMaintenance
 * @author Rober dCR
 * @date 19/03/2014
 * @brief Class that obtain Commision of Maintenance in Debit Cards
 */
public class StrategyCommissionDebitMaintenance implements StrategyCommissionDebit {

	/**
	 * Card owner
	 */
	private Client owner;
	
	/**
	 * Commission establish by the employee
	 */
	private float quantity;
	
	/**
	 * Maximum age to differentiate commissions
	 */
	private int maximum_age;
	
	/**
	 * Quantity of the default commission
	 */
	private float default_commission;
	
	/**
	 * String for obtain the maximum age
	 */
	private final String AGE = "debit_age";
	
	/**
	 * String for obtain the default commission
	 */
	private final String COMMISSION = "debit_maintenance";
	
	/**
	 * Class constructor
	 * @param owner
	 * @param quantity
	 * @throws CommissionException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public StrategyCommissionDebitMaintenance(Client owner, float quantity) throws CommissionException, NumberFormatException, IOException{
		this.owner = owner;
		this.setDefaultCommission();
		this.setMaximumAge();
		
		if (quantity >= 0)
			this.quantity = quantity;
		else
			throw new CommissionException("Commission can't been negative.");
	}

	public float calculateCommission() {
		if (this.owner.getAge() > this.maximum_age)
			return this.default_commission;
		else
			return this.quantity;
	}

	/**
	 * Setter of maximum_age
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void setMaximumAge() throws NumberFormatException, IOException{

		try {
			Properties ageProperty = new Properties();
			ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
			
			/**Obtenemos los parametros definidos en el archivo*/
			this.maximum_age = Integer.parseInt(ageProperty.getProperty(this.AGE));
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Setter of default_commission
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void setDefaultCommission() throws NumberFormatException, IOException{
		
		try {
			Properties commissionProperty = new Properties();
			commissionProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
			
			/**Obtenemos los parametros definidos en el archivo*/
			this.default_commission = Float.parseFloat(commissionProperty.getProperty(this.COMMISSION));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e2) {
			e2.printStackTrace();
		}
		
	}
	
}
