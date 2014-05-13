package es.unileon.ulebank.payments.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.service.CardManager;
import es.unileon.ulebank.payments.service.ChangeLimit;


@Controller
@RequestMapping(value="/buyLimits.htm")
public class ChangeBuyLimitsFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CardManager productManager;

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@Valid ChangeLimit changeLimit, BindingResult result) throws IncorrectLimitException
    {
        if (result.hasErrors()) {
            return "buyLimits";
        }
		
        double diaryLimit = changeLimit.getDiaryLimit();
        double monthlyLimit = changeLimit.getMonthlyLimit();
        logger.info("Increasing prices by " + monthlyLimit + "%.");

        productManager.changeBuyLimits(diaryLimit, monthlyLimit);

        return "redirect:/hello.htm";
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ChangeLimit formBackingObject(HttpServletRequest request) throws ServletException {
        ChangeLimit changeLimit = new ChangeLimit();
        changeLimit.setDiaryLimit(this.productManager.getProducts().get(0).getBuyLimitDiary()); //this.productManager.getBuyLimitDiary()
        changeLimit.setMonthlyLimit(this.productManager.getProducts().get(0).getBuyLimitMonthly()); //this.productManager.getBuyLimitMonthly()
        return changeLimit;
    }

    public void setProductManager(CardManager productManager) {
        this.productManager = productManager;
    }

    public CardManager getProductManager() {
        return productManager;
    }

}