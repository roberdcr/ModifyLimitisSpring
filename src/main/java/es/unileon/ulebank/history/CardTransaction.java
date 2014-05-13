package es.unileon.ulebank.history;

import java.util.Date;

import es.unileon.ulebank.account.Account;

/**
 * Transaction for the Card
 * @author Rober dCR
 * @date 8/05/2014
 */
public class CardTransaction extends GenericTransaction{

	/**
	 * Account which receives the amount in the transaction
	 */
	private Account receiverAccount;
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
	 */
	public CardTransaction(double amount, Date date, String subject,
			Enum<TransactionType> type, Account senderAccount, Account receiverAccount) {
		super(amount, date, subject, type);

		this.receiverAccount = receiverAccount;
		this.senderAccount = senderAccount;
	}

	/**
	 * Getter ReceiverAccount
	 * @return ReceiverAccount
	 */
	public Account getReceiverAccount() {
		return receiverAccount;
	}

	/**
	 * Getter SederAccount
	 * @return SederAccount
	 */
	public Account getSederAccount() {
		return senderAccount;
	}

}
