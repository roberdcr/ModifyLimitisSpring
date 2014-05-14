package es.unileon.ulebank.history;

import java.util.Date;

import es.unileon.ulebank.account.Account;

/**
 * Transaction for the Card
 * @author Rober dCR
 * @date 8/05/2014
 * @brief Class that allows all monetary transactions with cards
 */
public class CardTransaction extends Transaction{

	/**
	 * Account which emits the amount in the transaction
	 */
	private Account senderAccount;
	
	/**
	 * Class constructor
	 * @param amount
	 * @param date
	 * @param subject
	 * @param type
	 * @param senderAccount
	 * @param receiverAccount
	 * @throws TransactionException 
	 */
	public CardTransaction(double amount, Date date, String subject,
			Account senderAccount) throws TransactionException {
		super(amount, date, subject);

		this.senderAccount = senderAccount;
	}

	/**
	 * Getter SederAccount
	 * @return SederAccount
	 */
	public Account getSederAccount() {
		return senderAccount;
	}

}
