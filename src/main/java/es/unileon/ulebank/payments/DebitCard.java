package es.unileon.ulebank.payments;

import java.io.IOException;
import java.util.Date;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.CardTransaction;
import es.unileon.ulebank.history.TransactionType;
import es.unileon.ulebank.strategy.StrategyCommissionDebitEmission;
import es.unileon.ulebank.strategy.StrategyCommissionDebitMaintenance;
import es.unileon.ulebank.strategy.StrategyCommissionDebitRenovate;

/**
 * @author Israel, Rober dCR
 * Clase que representa una tarjeta de Debito
 */
public class DebitCard extends Card {
	
	/**
	 * Account associated to the Card
	 */
	private Account account;
	/**
	 * Card owner
	 */
	private Client owner;
	
	/**
	 * Constructor de la clase
	 * @param cardId
	 * @param owner
	 * @param account
	 * @param buyLimitDiary
	 * @param buyLimitMonthly
	 * @param cashLimitDiary
	 * @param cashLimitMonthly
	 * @param commissionEmission
	 * @param commissionMaintenance
	 * @param commissionRenovate
	 * @throws NumberFormatException
	 * @throws CommissionException
	 * @throws IOException
	 */
	public DebitCard(Handler cardId, Client owner, Account account,
			double buyLimitDiary, double buyLimitMonthly, double cashLimitDiary, double cashLimitMonthly,
			float commissionEmission, float commissionMaintenance, float commissionRenovate) throws NumberFormatException, CommissionException, IOException {
		super(cardId, CardType.DEBIT, buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly,
				new StrategyCommissionDebitEmission(commissionEmission), 
				new StrategyCommissionDebitMaintenance(owner, commissionMaintenance), 
				new StrategyCommissionDebitRenovate(commissionRenovate));
		this.account = account;
		this.owner = owner;
	}
	
	/**
	 * Method that makes the payment
	 * @param receiverAccount Account which receives the money from the card
	 * @param quantity Amount of the payment
	 * @param payConcept Concept of the payment
	 * @throws PaymentException
	 * @throws TransactionException 
	 */
	public void makeTransaction(Account receiverAccount, double quantity, String payConcept) throws PaymentException, TransactionException{

		try{
			//Discount the quantity from sender account
			this.account.doWithdrawal(new CardTransaction(quantity, new Date(), payConcept, TransactionType.PAYMENT, this.account, receiverAccount));
			//Add the money to receiver account
			receiverAccount.doDeposit(new CardTransaction(quantity, new Date(), payConcept, TransactionType.PAYMENT, this.account, receiverAccount));
		}catch(TransactionException e){
			e.printStackTrace();
			throw new PaymentException("Denegate Transaction");
		}
		
	}

	/**
	 * Devuelve el duegno de la tarjeta
	 * @return
	 */
	public Client getOwner() {
		return owner;
	}
}
