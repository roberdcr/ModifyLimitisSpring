package es.unileon.ulebank.payments.service;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.DNIHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.history.TransactionException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.domain.CreditCard;
import es.unileon.ulebank.transactionManager.TransactionManager;


public class SimpleCardManagerTests {

    private SimpleCardManager productManager;
    
    Card testCard;
	CardHandler handler;
	private Office office;
	private Bank bank;
	private TransactionManager manager;
    private String accountNumber = "0000000000";
    
    @Before
    public void setUp() throws Exception {
        productManager = new SimpleCardManager();
        this.manager = new TransactionManager();
        this.bank = new Bank(manager, new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
		handler = new CardHandler(new BankHandler("1234"), "01", "123456789");
		Client client = new Client(new DNIHandler("71451559N"), 27);
		Account account = new Account(office, bank, accountNumber);
		FeeStrategy commissionEmission = new LinearFee(0, 25);
		FeeStrategy commissionMaintenance = new LinearFee(0, 0);
		FeeStrategy commissionRenovate = new LinearFee(0, 0);
		testCard = new CreditCard(handler, client, account, 400.0, 1000.0, 400.0, 1000.0, commissionEmission.getFee(0), commissionMaintenance.getFee(0), commissionRenovate.getFee(0));
		this.productManager.setCard(this.testCard);
	}

    @Test
    public void testGetProductsWithNoProducts() {
        productManager = new SimpleCardManager();
        assertNull(productManager.getCard());
    }

    @Test
    public void testGetCard() {
        assertNotNull(productManager.getCard());        
        assertEquals(productManager.getCard(), this.testCard);
    }

    @Test(expected = NullPointerException.class)
    public void testChangeBuyLimitsWithNullCard() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	productManager = new SimpleCardManager();
    	productManager.changeBuyLimits(100.00, 1000.00);
    }

    @Test(expected = IncorrectLimitException.class)
    public void testChangeBuyLimitsWithIncorrectLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeBuyLimits(300, 100);       
    }
    
    @Test
    public void testChangeBuyLimitsWithCorrectLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeBuyLimits(300, 1000);
    	assertEquals(this.testCard.getBuyLimitDiary(),300,0.01);
    	assertEquals(this.testCard.getBuyLimitMonthly(),1000,0.01);
    }
    
    @Test(expected = IncorrectLimitException.class)
    public void testChangeBuyLimitsWithEqualstLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeBuyLimits(1000, 1000);
    	assertEquals(this.testCard.getBuyLimitDiary(),1000,0.01);
    	assertEquals(this.testCard.getBuyLimitMonthly(),1000,0.01);
    }
    
    @Test(expected = NullPointerException.class)
    public void testChangeCashLimitsWithNullCard() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	productManager = new SimpleCardManager();
    	productManager.changeCashLimits(100.00, 1000.00);
    }
    
    @Test(expected = IncorrectLimitException.class)
    public void testChangeCahsLimitsWithIncorrectLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeCashLimits(300, 100);       
    }
    
    @Test
    public void testChangeCashLimitsWithCorrectLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeCashLimits(300, 1000);
    	assertEquals(this.testCard.getCashLimitDiary(),300,0.01);
    	assertEquals(this.testCard.getCashLimitMonthly(),1000,0.01);
    }
    
    @Test(expected = IncorrectLimitException.class)
    public void testChangeCashLimitsWithEqualstLimits() throws AccountNotFoundException, IncorrectLimitException, PaymentException, TransactionException, CardNotFoundException {
    	this.productManager.changeBuyLimits(1000, 1000);
    	assertEquals(this.testCard.getBuyLimitDiary(),1000,0.01);
    	assertEquals(this.testCard.getBuyLimitMonthly(),1000,0.01);
    }
}