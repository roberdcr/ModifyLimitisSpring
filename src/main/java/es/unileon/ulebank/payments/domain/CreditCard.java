package es.unileon.ulebank.payments.domain;

import java.io.Serializable;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.strategy.StrategyCommission;

/**
 * CreditCard Class
 * @author Rober dCR
 * @date 13/05/2014
 * @brief Class of credit class adapted for persistance in database
 */
public class CreditCard extends Card implements Serializable  {

	/**
	 * Account associated to card
	 */
	private Account account;
	/**
	 * Owner of the card
	 */
	private Client owner;
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param cardId
	 * @param owner
	 * @param account
	 * @param type
	 * @param buyLimitDiary
	 * @param buyLimitMonthly
	 * @param cashLimitDiary
	 * @param cashLimitMonthly
	 * @param commissionEmission
	 * @param commissionMaintenance
	 * @param commissionRenovate
	 */
    public CreditCard(Handler cardId, Client owner, Account account, CardType type, double buyLimitDiary,
			double buyLimitMonthly, double cashLimitDiary,
			double cashLimitMonthly, StrategyCommission commissionEmission,
			StrategyCommission commissionMaintenance,
			StrategyCommission commissionRenovate) {
		super(cardId, type, buyLimitDiary, buyLimitMonthly, cashLimitDiary,
				cashLimitMonthly, commissionEmission, commissionMaintenance,
				commissionRenovate);
		this.owner = owner;
		this.account = account;
	}
    
    /**
     * Method that return the card data
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Nº Tarjeta: " + this.getCardNumber() + ";");
        buffer.append("Tipo: " + this.getCardType() + ";");
        buffer.append("Limite diario: " + this.getBuyLimitDiary() + ";");
        buffer.append("Limite mensual: " + this.getBuyLimitMonthly() + ";");
        buffer.append("Limite diario efectivo: " + this.getCashLimitDiary() + ";");
        buffer.append("Limite mensual efectivo: " + this.getCashLimitMonthly() + ";");
        return buffer.toString();
    }

    /**
     * Method that get the account associated to card
     * @return the account
     */
	public Account getAccount() {
		return account;
	}

	/**
	 * Method that get the owner associated to card
	 * @return the owner of the card
	 */
	public Client getOwner() {
		return owner;
	}

}