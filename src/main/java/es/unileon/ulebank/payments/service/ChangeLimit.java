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

    public void setDiaryLimit(int i) {
        diaryLimit = i;
        logger.info("Percentage set to " + i);
    }

    public int getDiaryLimit() {
        return diaryLimit;
    }
    
    public void setMonthlyLimit(int i) {
        monthlyLimit = i;
        logger.info("Percentage set to " + i);
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }
}
