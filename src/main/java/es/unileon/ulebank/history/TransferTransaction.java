package es.unileon.ulebank.history;

import java.util.Date;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.TransferException;

/**
 * Transaction for the Transfer
 * @author Rober dCR
 * @date 8/05/2014
 */
public class TransferTransaction extends GenericTransaction{
	
	/**
	 * Account from transfer the money
	 */
	private Account senderAccount;
	/**
	 * Account which receives the money
	 */
	private Account receiverAccount;

	public TransferTransaction(double amount, Date date, String subject,
			Enum<TransactionType> type, Account senderAccount, Account receiverAccount) throws TransferException {
		super(amount, date, subject, type);
		
		if (!senderAccount.equals(receiverAccount)){
			this.senderAccount = senderAccount;
			this.receiverAccount = receiverAccount;
		}
		else
			throw new TransferException("Sender Account number and Receiver Account number are the same.");
	}

	/**
	 * Getter Sender Account
	 * @return
	 */
	public Account getSenderAccount() {
		return senderAccount;
	}

	/**
	 * Getter Receiver Account
	 * @return
	 */
	public Account getReceiverAccount() {
		return receiverAccount;
	}

}
