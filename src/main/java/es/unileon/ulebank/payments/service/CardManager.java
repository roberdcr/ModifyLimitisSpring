package es.unileon.ulebank.payments.service;

import java.io.Serializable;

import es.unileon.ulebank.account.exception.AccountNotFoundException;
import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.payments.Card;

/**
 * Card Manager Interface
 * @author Rober dCR
 * @date 10/05/2014
 * @brief Interface for the card managers
 */
public interface CardManager extends Serializable {

	/**
	 * Method that changes the buy limits using the command with the limits from buyLimits.jsp
	 * @param diary
	 * @param monthly
	 * @throws IncorrectLimitException
	 * @throws AccountNotFoundException
	 * @throws PaymentException
	 * @throws TransactionException
	 * @throws CardNotFoundException
	 */
    public void changeBuyLimits(double diary, double monthly) throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException;
    
    /**
     * Method that changes the cash limits using the command with the limits from cashLimits.jsp
     * @param diary
     * @param monthly
     * @throws IncorrectLimitException
     * @throws AccountNotFoundException
     * @throws PaymentException
     * @throws TransactionException
     * @throws CardNotFoundException
     */
    public void changeCashLimits(double diary, double monthly) throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException;
    
    /**
     * Method that returns the card of the management
     * @return
     */
    public Card getCard();

}