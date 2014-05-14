package es.unileon.ulebank.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.MalformedHandlerException;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.History;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionException;
import es.unileon.ulebank.history.TransactionType;
import es.unileon.ulebank.payments.Card;
import org.apache.log4j.Logger;

/**
 *
 * @author runix
 */
public class Account {

    /**
     * The logger of the class
     */
    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    /**
     * The default liquidation frecuency
     */
    private static final int DEFAULT_LIQUIDATION_FREQUENCY = 6;
    /**
     * The account identifier
     */
    private final Handler id;

    /**
     * The account balance
     */
    private double balance;
    /**
     * The account titulars
     */
    private final List<Client> titulars;
    /**
     * The account authorizeds
     */
    private final List<Client> authorizeds;
    /**
     * The history of the account
     */
    private final History history;
    /**
     * The last liquidation
     */
    private Date lastLiquidation;
    /**
     * The liquidation frequency in months
     */
    private int liquidationFrecuency;

    private final List<LiquidationStrategy> liquidationStrategies;

    /**
     * The max account's overdraft ( in positive )
     */
    private float maxOverdraft;
    
    /**
     * Cards list
     */
    
    private ArrayList<Card> cards;

    /**
     * Create a new account
     *
     * @param office (The office of the account)
     *
     * @param bank ( The bank of the office )
     *
     * @param accountnumber (the accountNumber)
     *
     * @throws es.unileon.ulebank.handler.MalformedHandlerException
     *
     */
    public Account(Office office, Bank bank, String accountnumber) throws MalformedHandlerException {
        this.id = new AccountHandler(office.getIdOffice(), bank.getID(), accountnumber);
        this.history = new History<>();
        this.balance = 0.0d;
        this.titulars = new ArrayList<>();
        this.authorizeds = new ArrayList<>();
        this.lastLiquidation = new Date(System.currentTimeMillis());
        this.liquidationFrecuency = DEFAULT_LIQUIDATION_FREQUENCY;
        this.liquidationStrategies = new ArrayList<>();
        this.maxOverdraft = 0;
        this.cards = new ArrayList<Card>();
        LOG.info("Create a new account with number " + accountnumber + " office " + office.getIdOffice().toString() + " bank " + bank.getID());
    }

    /**
     * Set the liquidation frecuency in months
     *
     * ( Default {
     *
     * @see DEFAULT_LIQUIDATION_FREQUENCY} )
     *
     * @param liquidationFrecuency ( new liquidation frecuency )
     *
     * @return (true if success, false if the param is negative or zero)
     */
    public boolean setLiquidationFrecuency(int liquidationFrecuency) {
        if (liquidationFrecuency >= 1) {
            LOG.info("Change liquidation frecuency to " + liquidationFrecuency);
            this.liquidationFrecuency = liquidationFrecuency;
            return true;
        }
        return false;
    }

    /**
     * Set the max account's overdraft
     *
     * @param maxOverdraft ( the account's overdraft ( in positive ))
     * @return ( true if succes, false otherwise)
     */
    public boolean setMaxOverdraft(float maxOverdraft) {
        if (maxOverdraft >= 0) {
            LOG.info("Change max overdraft to " + maxOverdraft + "\n");
            this.maxOverdraft = maxOverdraft;
            return true;
        }
        return false;
    }

    /**
     *
     * Add a new titular. The client cannot be repeated, that is, two titulars
     * cannot have the same id, because its id is unique. If we try to add a
     * person that is already added the method return false.
     *
     * @param client ( client to add)
     *
     * @return ( true if success, else false )
     */
    public boolean addTitular(Client client) {
        boolean found = false;
        int i = 0;
        while (i < this.titulars.size() && !found) {
            if (this.titulars.get(i++).getId().compareTo(client.getId()) == 0) {
                found = true;
            } 
        }
        if (!found) {
            LOG.info(("Add new titular " + client.getId()));
            this.titulars.add(client);
        } else {
            LOG.error("Cannot add the titular " + client.getId().toString() + " , the titular already exists");
        }
        return !found;
    }

    /**
     *
     * Delete a titular. If the titular hadn't been added, the method return
     * false.
     *
     * @see es.unileon.ulebank.handler.Handler}.
     *
     * @param id ( The client id )
     *
     * @return ( true if success, false otherwise )
     */
    public boolean deleteTitular(Handler id) {
        boolean found = false;
        int i = 0;
        while (i < this.titulars.size() && !found) {
            if (this.titulars.get(i++).getId().compareTo(id) == 0) {
                LOG.info("Delete " + id.toString() + " titular");
                this.titulars.remove(i);
                found = true;
            }
        }
        if (!found) {
            LOG.error("Cannot remove the titular " + id.toString() + " because it doesn't exist");
        }
        return found;
    }

    /**
     *
     * Add a new authorized. The authorized cannot be repeated, that is, two
     * titulars cannot have the same id, because its id is unique.If we try to
     * add a person that is already added the method return false.
     *
     *
     * @param authorized ( authorized to add)
     *
     * @return ( true if success, else false )
     */
    public boolean addAuthorized(Client authorized) {
        boolean found = false;
        int i = 0;
        while (i < this.authorizeds.size() && !found) {
            if (this.authorizeds.get(i++).getId().compareTo(authorized.getId()) == 0) {
                found = true;
            }
        }
        if (!found) {
            LOG.info(("Add new authorized " + authorized.getId()));
            this.authorizeds.add(authorized);
        } else {
            LOG.error("Cannot add the authorized " + authorized.getId().toString() + " , the authorized already exists");
        }
        return !found;
    }

    /**
     *
     * Delete a authorized. If the authorized hadn't been added, the method
     * return false.
     *
     * @see es.unileon.ulebank.handler.Handler}.
     *
     * @param id ( The authorized id )
     *
     * @return ( true if success, else false )
     */
    public boolean deleteAuthorized(Handler id) {
        boolean found = false;
        int i = 0;
        while (i < this.authorizeds.size() && !found) {
            if (this.authorizeds.get(i++).getId().compareTo(id) == 0) {
                LOG.info("Delete " + id.toString() + " authorized");
                this.authorizeds.remove(i);
                found = true;
            }
        }
        if (!found) {
            LOG.error("Cannot remove the authorized " + id.toString() + " because it doesn't exist");
        }
        return found;
    }

    /**
     * Get the account titulars
     *
     * @return ( The titulars )
     */
    public List<Client> getTitulars() {
        return new ArrayList<>(this.titulars);
    }

    /**
     * Get the authorizeds
     *
     * @return ( the authorizeds )
     */
    public List<Client> getAuthorizeds() {
        return new ArrayList<>(this.authorizeds);
    }

    /**
     * Get the account's balance
     *
     * @return (the balance)
     *
     * @author runix
     */
    public final double getBalance() {
        return this.balance;
    }
    
    /**
     * Set balance value
     * @param balance
     */
    public void setBalance(double balance) {
		this.balance = balance;
	}

    /**
     * Get the max account's overdraft
     *
     * @return (the account's overdraft )
     */
    public final double getMaxOverdraft() {
        return this.maxOverdraft;
    }

    /**
     * Withdraw money from the account.
     *
     * @param transaction ( transaction to do )
     *
     * @throws TransactionException
     * es.unileon.ulebank.account.exception.TransactionException (if there are
     * some null fields in the transaction)
     */
    public synchronized void doWithdrawal(Transaction transaction) throws TransactionException {
        StringBuilder err = new StringBuilder();
        if (transaction.getSubject() == null) {
            err.append("The subject cannot be null \n");
        } else {
            if (transaction.getSubject().length() == 0) {
                err.append("Transaction length cannot be 0 \n");
            }
        }

        if (transaction.getType() != TransactionType.CHARGE && transaction.getType() != TransactionType.SALARY) {
            err = err.append("Withdrawal operations must be ").append(TransactionType.CHARGE).append(" type\n");
        }

        if (transaction.getId() == null) {
            err.append(("The id cannot be null \n"));
        } else {
            if (transaction.getId().toString().length() == 0) {
                err.append(("The id size cannot be 0 \n"));
            }
        }
        if (transaction.getDate() == null) {
            err.append("The date cannot be null \n");
        }

        if (transaction.getAmount() < 0) {
            err.append("Fail, the amount of money cannot be less than zero\n");
        }

        if (transaction.getEffectiveDate() == null) {
            err.append("The effective date cannot be null \n");
        }

        if (balance - transaction.getAmount() < -this.maxOverdraft) {
            err.append("Cannot withdrawal money, max overdraft reached. Max overdraft = ").append(this.maxOverdraft).append("\n");
        }

        if (err.length() > 0) {
            LOG.error(err.toString());
            throw new TransactionException(err.toString());
        }
        boolean success = this.history.add(transaction);
        if (success) {
            this.balance -= transaction.getAmount();
        } else {
            String error = "Cannot store the transaction\n";
            LOG.error(error);
            throw new TransactionException(error);
        }

    }

    /**
     * Deposit money in the account
     *
     * @param transaction ( transaction to do )
     *
     * @throws TransactionException (if there are some null fields in the
     * transaction)
     */
    public synchronized void doDeposit(Transaction transaction) throws TransactionException {
        StringBuilder err = new StringBuilder();
        if (transaction.getSubject() == null) {
            err.append("The subject cannot be null \n");
        } else {
            if (transaction.getSubject().length() == 0) {
                err.append("Transaction length cannot be 0 \n");
            }
        }

        if (transaction.getType() != TransactionType.PAYMENT && transaction.getType() != TransactionType.PAYROLL) {
            err = err.append("Deposit operations must be").append(TransactionType.PAYMENT).append(" type\n");
        }

        if (transaction.getId() == null) {
            err.append(("The id cannot be null \n"));
        } else {
            if (transaction.getId().toString().length() == 0) {
                err.append(("The id size cannot be 0 \n"));
            }
        }

        if (transaction.getDate() == null) {
            err.append("The date cannot be null \n");
        }

        if (transaction.getAmount() < 0) {
            err.append("Fail, the amount of money cannot be less than zero\n");
        }

        if (transaction.getEffectiveDate() == null) {
            err.append("The effective date cannot be null \n");
        }
        if (err.length() > 0) {
            LOG.error(err.toString());
            throw new TransactionException(err.toString());
        }

        boolean success = this.history.add(transaction);
        if (success) {
            this.balance += transaction.getAmount();
        } else {
            String error = "Cannot store the transaction\n";
            LOG.error(error);
            throw new TransactionException(error);
        }
    }

    /**
     *
     * @param strategy
     * @return
     */
    public boolean addLiquidationStrategy(LiquidationStrategy strategy) {
        int i = 0;
        boolean found = false;
        while (i < this.liquidationStrategies.size() && !found) {
            if (this.liquidationStrategies.get(i++).getID().compareTo(strategy.getID()) == 0) {
                found = true;
            }
        }
        if (!found) {
            this.liquidationStrategies.add(strategy);
        }
        return !found;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean deleteLiquidationStrategy(Handler id) {
        int i = 0;
        boolean found = false;
        while (i < this.liquidationStrategies.size() && !found) {
            if (this.liquidationStrategies.get(i++).getID().compareTo(id) == 0) {
                this.liquidationStrategies.remove(i);
                found = true;
            }
        }
        return found;
    }

    /**
     *
     * @param office
     */
    public void doLiquidation(Office office) {
        StringBuilder err = new StringBuilder();
        try {
            AccountHandler ah = new AccountHandler(this.id);
            if (office.getIdOffice().compareTo(ah) == 0) {

            } else {
                err.append("Wrong office\n");
            }
        } catch (MalformedHandlerException e) {
            err.append("Error while parsing handler\n");
        }
    }

    /**
     * Get the account transactions
     *
     * @return (The transactions)
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * Get the account ID
     *
     * @return (the account id)
     * @author runix
     */
    public final Handler getID() {
        return this.id;
    }
    
	public void addCard(Card card) {
		this.cards.add(card);
	}

	public boolean removeCard(CardHandler cardId) {
		Card card = searchCard(cardId);
		return this.cards.remove(card);
	}
	
	public Card searchCard(CardHandler cardId) {
		Iterator<Card> iterator = cards.iterator();
		Card card = null;
		
		if (cards.isEmpty()) {
			throw new NullPointerException("Card list is empty.");
		}
		
		while (iterator.hasNext()) {
			card = iterator.next();
			
			if (card.getCardNumber().compareTo(cardId) == 0) {
				break;
			}
		}
		
		return card;
	}
	
	public int getCardAmount() {
		return this.cards.size();
	}
}
