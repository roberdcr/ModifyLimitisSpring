package es.unileon.ulebank.payments.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.unileon.ulebank.payments.service.CardManager;

/**
 * Class Controller of changeLimits.jsp
 * @author Rober dCR
 * @date 10/05/2014
 * @brief Controller for resolve the petitions of changeLimits.jsp
 */
@Controller
public class InventoryController {

	/**
	 * Variable which store the information in the log
	 */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Manager of the card
     */
    @Autowired
    private CardManager cardManager;
    
    /**
     * Method that create the Model and the View of changeLimits.jsp
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/changeLimits.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String now = (new Date()).toString();
        logger.info("Returning changeLimits view with " + now + "and the card selected.");

        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("now", now);
        myModel.put("products", this.cardManager.getCard());

        return new ModelAndView("changeLimits", "model", myModel);
    }

    /**
     * Setter of the cardManager
     * @param cardManager
     */
    public void setProductManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }
}