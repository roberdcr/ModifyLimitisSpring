package es.unileon.ulebank.payments.service;

import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.Card;

public class SimpleCardManager implements CardManager {

	private static final long serialVersionUID = 1L;

	private Card card;

	public Card getProducts() {
		return card; 
	}

	public void changeBuyLimits(double diary, double monthly)
			throws IncorrectLimitException {
		card.setBuyLimitDiary(diary);
		card.setBuyLimitMonthly(monthly);
	}

	public void changeCashLimits(double diary, double monthly)
			throws IncorrectLimitException {
		card.setCashLimitDiary(diary);
		card.setCashLimitMonthly(monthly);
	}

	public void setProducts(Card card) {
		this.card = card;
	}

}