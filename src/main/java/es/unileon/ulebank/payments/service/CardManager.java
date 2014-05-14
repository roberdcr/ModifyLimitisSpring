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

    public void changeBuyLimits(double diary, double monthly) throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException;
    public void changeCashLimits(double diary, double monthly) throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException;
    
    public Card getProducts();

}