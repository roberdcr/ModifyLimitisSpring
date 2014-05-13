package es.unileon.ulebank.payments.service;

import java.util.List;

import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.domain.CreditCard;

public class SimpleCardManager implements CardManager {

    private static final long serialVersionUID = 1L;

    private List<CreditCard> cards;

    public List<CreditCard> getProducts() {
        return cards; 
    }

	public void changeBuyLimits(double diary, double monthly)
			throws IncorrectLimitException {
		if (cards != null) {
            for (CreditCard card : cards) {
                card.setBuyLimitDiary(diary);
                card.setBuyLimitMonthly(monthly);
            }
        }
	}

	public void changeCashLimits(double diary, double monthly)
			throws IncorrectLimitException {
		if (cards != null) {
            for (CreditCard card : cards) {
                card.setCashLimitDiary(diary);
                card.setCashLimitMonthly(monthly);
            }
        }
		
	}
	
    public void setProducts(List<CreditCard> cards) {
        this.cards = cards;
    }

}