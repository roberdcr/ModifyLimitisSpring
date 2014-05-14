package es.unileon.ulebank.payments.service;

import es.unileon.ulebank.account.exception.AccountNotFoundException;
import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.command.ModifyBuyLimitCommand;
import es.unileon.ulebank.command.ModifyCashLimitCommand;
import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.payments.Card;

/**
 * Simple Card Manager Class
 * @author Rober dCR
 * @date 14/05/2014
 * @brief Class which manages the card
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
		buyLimitsDiary.execute();
		buyLimitsMonthly.execute();
	}

	@Override
	public void changeCashLimits(double diary, double monthly)
			throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException {
		Command cashLimitsDiary = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, diary, "diary");
		Command cashLimitsMonthly = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, monthly, "monthly");
		cashLimitsDiary.execute();
		cashLimitsMonthly.execute();
	}

	public void setCard(Card card) {
		this.card = card;
	}

}