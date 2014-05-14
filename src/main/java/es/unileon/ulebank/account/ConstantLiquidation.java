/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionException;
import es.unileon.ulebank.time.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author runix
 */
public class ConstantLiquidation implements LiquidationStrategy {

    /**
     *
     */
    private String subject;
    /**
     *
     */
    private Handler id;
    /**
     *
     */
    private double amount;

    /**
     *
     * @param subject
     * @param id
     * @param amount
     */
    public ConstantLiquidation(String subject, Handler id, double amount) {
        this.subject = subject;
        this.id = id;
        this.amount = amount;
    }

    @Override
    public Handler getID() {
        return this.id;
    }

    @Override
    public Transaction doLiquidation(Iterator<Transaction> transactions, Date min, Date max) {
        Transaction t = null;
        try {
            long actualTime = Time.getInstance().getTime();
            t = new GenericTransaction(this.amount, new Date(actualTime), subject);
            t.setEffectiveDate(new Date(actualTime));
        } catch (TransactionException ex) {
            Logger.getLogger(ConstantLiquidation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

}
