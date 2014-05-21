package es.unileon.ulebank.payments.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.taskList.TaskList;
import es.unileon.ulebank.history.CardTransaction;
import es.unileon.ulebank.history.TransactionException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Israel, Rober dCR
 * Clase que representa la tarjeta de credito
 */
public class CreditCard extends Card implements Serializable{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Cuenta a la que esta asociada la tarjeta
	 */
	private Account account;
	/**
	 * Duegno de la tarjeta
	 */
	private Client owner;
	/**
	 * Lista de transacciones de la tarjeta
	 */
	private List<CardTransaction> transactionList;
	/**
	 * Lista de tareas a realizar
	 */
	private TaskList transactionTask;
	
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
	 * @throws CommissionException
	 * @throws InvalidFeeException 
	 */
	public CreditCard(Handler cardId, Client owner, Account account, double buyLimitDiary, double buyLimitMonthly, 
			double cashLimitDiary, double cashLimitMonthly, double commissionEmission, 
			double commissionMaintenance, double commissionRenovate) throws CommissionException, InvalidFeeException {
		super(cardId, CardType.CREDIT, buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly,
				new LinearFee(0,commissionEmission),
				new LinearFee(0,commissionMaintenance),
				new LinearFee(0,commissionRenovate));
		this.account = account;
		this.owner = owner;
		this.transactionList = new ArrayList<CardTransaction>();
	}
	
	/**
	 * Method that makes the payment
	 * @param receiverAccount Account which receives the money from the card
	 * @param quantity Amount of the payment
	 * @param payConcept Concept of the payment
	 * @throws PaymentException 
	 * @throws TransactionException 
	 */
	@Override
	public void makeTransaction(Account receiverAccount, double quantity, String payConcept) throws PaymentException, TransactionException {
		//TODO - Actualizar con las nuevas transacciones
		//Agyadimos la transaccion a la lista
		this.transactionList.add(new CardTransaction(quantity, new Date(), payConcept, this.account));
		//LLegada la fecha hay que descontar el dinero de la cuenta
		//Pagar los importes a la cuenta
	}
	
	/**
	 * Devuelve el duegno de la tarjeta
	 * @return Client
	 */
	public Client getOwner() {
		return owner;
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
}
