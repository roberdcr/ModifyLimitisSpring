package es.unileon.ulebank.payments.service;

import javax.security.auth.login.AccountNotFoundException;

import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.command.ModifyBuyLimitCommand;
import es.unileon.ulebank.command.ModifyCashLimitCommand;
import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.history.TransactionException;
import es.unileon.ulebank.payments.Card;

/**
 * Simple Card Manager Class
 * @author Rober dCR
 * @date 14/05/2014
 * @brief Simple Class which manages the card for change buy and cash limits
 */
public class SimpleCardManager implements CardManager {

	private static final long serialVersionUID = 1L;

	/**
	 * Card which modifies from changeLimits.jsp
	 */
	private Card card;

	@Override
	public Card getCard() {
		return card; 
	}

	@Override
	public void changeBuyLimits(double diary, double monthly)
			throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException {
		Command buyLimitsDiary = new ModifyBuyLimitCommand(this.card.getCardNumber(), this.card, diary, "diary");
		Command buyLimitsMonthly = new ModifyBuyLimitCommand(this.card.getCardNumber(), this.card, monthly, "monthly");
		buyLimitsMonthly.execute();
		buyLimitsDiary.execute();
	}

	@Override
	public void changeCashLimits(double diary, double monthly)
			throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException {
		Command cashLimitsDiary = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, diary, "diary");
		Command cashLimitsMonthly = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, monthly, "monthly");
		cashLimitsMonthly.execute();
		cashLimitsDiary.execute();
	}

	public void setCard(Card card) {
		this.card = card;
	}

}