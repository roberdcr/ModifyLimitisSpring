package es.unileon.ulebank.command;

import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.exceptions.TransferException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.TransactionException;

/**
 * @author Israel
 */
public interface Command {
	/**
	 * Realiza la ejecucion del comando
	 * @throws InvalidFeeException 
	 * @throws TransactionException 
	 * @throws PaymentException 
	 * @throws CardNotFoundException 
	 * @throws IncorrectLimitException 
	 */
	public void execute() throws PaymentException, TransactionException, CardNotFoundException, IncorrectLimitException;
	/**
	 * Deshace los cambios realizados
	 * @throws TransferException 
	 * @throws es.unileon.ulebank.history.TransactionException 
	 */
	public void undo() throws TransferException;
	/**
	 * Rehace los cambios deshechos
	 * @throws TransactionException 
	 * @throws PaymentException 
	 */
	public void redo() throws PaymentException, TransactionException;
	/**
	 * Devuelve el identificador del comando
	 * @return
	 */
	public Handler getId();
}
