/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history;

import es.unileon.ulebank.account.DetailedInformation;
import java.util.Date;

/**
 * Generic transaction
 *
 * @author runix
 */
public class GenericTransaction extends Transaction {

    /**
     * Create a new generic transaction
     *
     * @param amount ( Transaction amount )
     * @param date ( Transaction date )
     * @param subject ( Transaction subject )
     * @param type (Transaction type)
     */
    public GenericTransaction(double amount, Date date, String subject, Enum<TransactionType> type) {
        super(amount, date, subject, type);
    }

    /**
     * Create a new generic transaction
     *
     * @param amount ( Transaction amount )
     * @param date ( Transaction date )
     * @param subject ( Transaction subject )
     * @param type (Transaction type)
     */
    public GenericTransaction(double amount, Date date, String subject, Enum<TransactionType> type, DetailedInformation extraInfo) {
        super(amount, date, subject, type, extraInfo);
    }
}
