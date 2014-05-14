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

public class SimpleCardManager implements CardManager {

	private static final long serialVersionUID = 1L;

	private Card card;

	public Card getProducts() {
		return card; 
	}

	public void changeBuyLimits(double diary, double monthly)
			throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException {
		Command buyLimitsDiary = new ModifyBuyLimitCommand(this.card.getCardNumber(), this.card, diary, "diary");
		Command buyLimitsMonthly = new ModifyBuyLimitCommand(this.card.getCardNumber(), this.card, monthly, "monthly");
		buyLimitsDiary.execute();
		buyLimitsMonthly.execute();
	}

	public void changeCashLimits(double diary, double monthly)
			throws IncorrectLimitException, AccountNotFoundException, PaymentException, TransactionException, CardNotFoundException {
		Command cashLimitsDiary = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, diary, "diary");
		Command cashLimitsMonthly = new ModifyCashLimitCommand(this.card.getCardNumber(), this.card, monthly, "monthly");
		cashLimitsDiary.execute();
		cashLimitsMonthly.execute();
	}

	public void setProducts(Card card) {
		this.card = card;
	}

}