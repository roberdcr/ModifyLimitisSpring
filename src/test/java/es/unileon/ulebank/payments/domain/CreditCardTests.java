package es.unileon.ulebank.payments.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;




import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.DNIHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.transactionManager.TransactionManager;


public class CreditCardTests {

    private CreditCard card;
	private CardHandler handler;
	private Office office;
	private Bank bank;
	private TransactionManager manager;
    private String accountNumber = "0000000000";

    @Before
    public void setUp() throws Exception {
    	this.manager = new TransactionManager();
        this.bank = new Bank(manager, new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
		handler = new CardHandler(new BankHandler("1234"), "01", "123456789");
		Client client = new Client(new DNIHandler("71451559N"), 27);
		Account account = new Account(office, bank, accountNumber);
		
		//this.card = new CreditCard(handler, client, account, 0, 0, 0, 0, commissionRenovate, commissionRenovate, commissionRenovate);
    }

    @Test
    public void testSetAndGetDescription() {
        /*String testDescription = "aDescription";
        assertNull(card.getDescription());
        card.setDescription(testDescription);
        assertEquals(testDescription, card.getDescription());*/
    }

    @Test
    public void testSetAndGetPrice() {
       /* double testPrice = 100.00;
        assertEquals(0, 0, 0);    
        card.setPrice(testPrice);
        assertEquals(testPrice, card.getPrice(), 0);*/
    }

}