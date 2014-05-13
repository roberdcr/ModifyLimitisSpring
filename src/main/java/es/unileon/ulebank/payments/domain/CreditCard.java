package es.unileon.ulebank.payments.domain;

import java.io.Serializable;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.strategy.StrategyCommission;

public class CreditCard extends Card implements Serializable  {

	private Account account;
	private Client owner;
	private static final long serialVersionUID = 1L;
	
    public CreditCard(Handler cardId, Client owner, CardType type, double buyLimitDiary,
			double buyLimitMonthly, double cashLimitDiary,
			double cashLimitMonthly, StrategyCommission commissionEmission,
			StrategyCommission commissionMaintenance,
			StrategyCommission commissionRenovate) {
		super(cardId, type, buyLimitDiary, buyLimitMonthly, cashLimitDiary,
				cashLimitMonthly, commissionEmission, commissionMaintenance,
				commissionRenovate);
		this.owner = owner;
	}
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Nº Tarjeta: " + this.getCardNumber() + ";");
        buffer.append("Tipo: " + this.getCardType() + ";");
        buffer.append("Limite diario: " + this.getBuyLimitDiary() + ";");
        buffer.append("Limite mensual: " + this.getBuyLimitMonthly() + ";");
        buffer.append("Limite diario efectivo: " + this.getCashLimitDiary() + ";");
        buffer.append("Limite mensual efectivo: " + this.getCashLimitMonthly() + ";");
        return buffer.toString();
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}
}