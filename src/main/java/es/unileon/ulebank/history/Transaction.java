package es.unileon.ulebank.history;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.handler.Handler;
import java.util.Date;

/**
 *
 * @author roobre
 */
public abstract class Transaction {

    private final Handler id;
    private final double amount;
    private final Date date;
    private Date effectiveDate;
    private final String subject;
    private final Enum<TransactionType> type;
    private DetailedInformation extraInformation;

    /**
     *
     * @param amount
     * @param date
     * @param subject
     * @param type
     */
    public Transaction(double amount, Date date, String subject, Enum<TransactionType> type) {
        this(amount, date, subject, type, new DetailedInformation(""));
    }

    /**
     *
     * @param amount
     * @param date
     * @param subject
     * @param type
     * @param info
     */
    public Transaction(double amount, Date date, String subject, Enum<TransactionType> type, DetailedInformation info) {
        this.id = TransactionHandlerProvider.getTransactionHandler();
        this.amount = amount;
        this.date = date;
        this.subject = subject;
        this.type = type;
        this.extraInformation = info;
        this.extraInformation.doFinal();
    }

    /**
     * @return the id
     */
    public Handler getId() {
        return id;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the type
     */
    public Enum<TransactionType> getType() {
        return type;
    }

    /**
     *
     * @param effectiveDate
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Transaction " + "id=" + id + ", amount=" + amount + ", date=" + date + ", effectiveDate=" + effectiveDate + ", subject=" + subject;
    }
}
