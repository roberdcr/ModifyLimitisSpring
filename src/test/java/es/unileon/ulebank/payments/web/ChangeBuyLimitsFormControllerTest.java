package es.unileon.ulebank.payments.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.DNIHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.domain.CreditCard;
import es.unileon.ulebank.payments.service.ChangeLimit;
import es.unileon.ulebank.payments.service.SimpleCardManager;
import es.unileon.ulebank.transactionManager.TransactionManager;

public class ChangeBuyLimitsFormControllerTest {

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
	public void testFormBackingObject() throws Exception{		
		ChangeBuyLimitsFormController controller = new ChangeBuyLimitsFormController();		
		controller.setProductManager(this.productManager);	
		assertEquals(controller.formBackingObject(null).getDiaryLimit(),this.productManager.getCard().getBuyLimitDiary(),0.01);
		assertEquals(controller.formBackingObject(null).getMonthlyLimit(),this.productManager.getCard().getBuyLimitMonthly(),0.01);
	}
	
	@Test
	public void testOnSubmit() throws Exception{		
		ChangeBuyLimitsFormController controller = new ChangeBuyLimitsFormController();		
		controller.setProductManager(this.productManager);	
		ChangeLimit limit = new ChangeLimit();
		limit.setDiaryLimit(150);
		limit.setMonthlyLimit(300);
		/*controller.onSubmit(limit, new BindingResult());
		assertEquals(150,this.productManager.getCard().getBuyLimitDiary(),0.01);
		assertEquals(300,this.productManager.getCard().getBuyLimitDiary(),0.01);*/
	}

}
