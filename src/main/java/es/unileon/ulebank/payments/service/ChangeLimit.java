package es.unileon.ulebank.payments.service;

import javax.validation.constraints.Min;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChangeLimit {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Min(100)
    private int diaryLimit;
    
    @Min(300)
    private int monthlyLimit;

    public void setDiaryLimit(double i) {
        diaryLimit = (int) i;
        logger.info("Percentage set to " + i);
    }

    public double getDiaryLimit() {
        return diaryLimit;
    }
    
    public void setMonthlyLimit(double i) {
        monthlyLimit = (int) i;
        logger.info("Percentage set to " + i);
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }
}
