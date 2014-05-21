package es.unileon.ulebank.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.DNIHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.domain.CreditCard;
import es.unileon.ulebank.transactionManager.TransactionManager;

public class JPACardDaoTests {
	
	private ApplicationContext context;
    private CardDAO cardDao;

    @Before
    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext("classpath:test-context.xml");
        cardDao = (CardDAO) context.getBean("cardDao");
    }
    
    @Test
    public void testGetProductList() {
    	String card_id = "1234011234567892";
        Card card = cardDao.getCardDAO(card_id);
        assertEquals(card.getCardId(), card_id);	   
    }

    @Test
    public void testSaveProduct() throws InvalidFeeException, CommissionException {
    	TransactionManager manager = new TransactionManager();
		Bank bank = new Bank(manager, new GenericHandler("1234"));
		Office office = new Office(new GenericHandler("1234"), bank);
		CardHandler handler = new CardHandler(new BankHandler("1234"), "01", "123456789");
		Client client = new Client(new DNIHandler("71451559N"), 27);
		Account account = new Account(office, bank, "0000000000");
		FeeStrategy commissionEmission = new LinearFee(0, 25);
		FeeStrategy commissionMaintenance = new LinearFee(0, 0);
		FeeStrategy commissionRenovate = new LinearFee(0, 0);
		Card testCard = new CreditCard(handler, client, account, 400.0, 1000.0, 400.0, 1000.0, commissionEmission.getFee(0), commissionMaintenance.getFee(0), commissionRenovate.getFee(0));

        cardDao.saveCard(testCard);

        Card testCardCopy = cardDao.getCardDAO(testCard.getCardId());
        
        assertEquals(testCardCopy.getCardId(), testCard.getCardId());
    }

}
