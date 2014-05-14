package es.unileon.ulebank.command;


import javax.security.auth.login.AccountNotFoundException;

import org.apache.log4j.Logger;

import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;

/**
 * @author Israel
 */
public class ModifyCashLimitCommand implements Command {
	/**
	 * Logger de la clase
	 */
	private static final Logger LOG = Logger.getLogger(ModifyCashLimitCommand.class.getName());
	/**
	 * Identificador del comando
	 */
	private Handler id;
	/**
	 * Objeto tarjeta cuyos limites se van a modificar
	 */
	private Card card;
	/**
	 * Nueva cantidad a modificar
	 */
	private double newAmount;
	/**
	 * Cantidad antes de la modificacion
	 */
	private double oldAmount;
	/**
	 * Tipo de limite a modificar (diario o mensual)
	 */
	private String type;
	
	/**
	 * Constructor de la clase
	 * @param cardId
	 * @param office
	 * @param dni
	 * @param accountHandler
	 * @param amount
	 * @param type
	 * @throws AccountNotFoundException 
	 */
	public ModifyCashLimitCommand(Handler cardId, Card card, double amount, String type) throws AccountNotFoundException {
		try {
			this.id = new CommandHandler(cardId);
			this.card = card;
			this.newAmount = amount;
			this.type = type;
		} catch (NullPointerException e) {
			LOG.info(e.getMessage());
		}
	}
	
	/**
	 * Realiza la modificacion del limite de extraccion en cajero ya sea diario o mensual
	 * @throws CardNotFoundException 
	 * @throws IncorrectLimitException 
	 */
	@Override
	public void execute() throws CardNotFoundException, IncorrectLimitException {
		//Buscamos la tarjeta con el identificador de la misma en la lista de tarjetas de la cuenta
		try {			
			//Si el limite a modificar es diario
			if (type.equalsIgnoreCase("diary")) {
					//Guardamos la cantidad anterior para poder deshacer la operacion
					this.oldAmount = this.card.getCashLimitDiary();
					//Cambiamos el limite por el indicado
					this.card.setCashLimitDiary(newAmount);
				//Si el limite a modificar es mensual
			} else if (type.equalsIgnoreCase("monthly")) {
					//Guardamos la cantidad anterior para poder deshacer la operacion
					this.oldAmount = this.card.getCashLimitMonthly();
					//Cambiamos el limite por el indicado
					this.card.setCashLimitMonthly(newAmount);
				//Si no se indica el tipo de limite a modificar adecuadamente no va a realizar la operacion
			} else {
				LOG.info("Limit type not defined");
			}
		} catch (NullPointerException e) {
			LOG.info(e.getMessage());
		} catch (IncorrectLimitException e) {
			LOG.info(e.getMessage());
			throw new IncorrectLimitException("Diary limit must been lower tha monthly limit");
		}	
	}

	/**
	 * Deshace la modificacion del limite de compra dejandolo como estaba
	 */
	@Override
	public void undo() {
		//Si el tipo es diario
		if (type.equalsIgnoreCase("diary")) {
			try {
				//Recuperamos el limite anterior
				this.card.setCashLimitDiary(oldAmount);
			} catch (IncorrectLimitException e) {
				LOG.info(e.getMessage());
			}
			//Si el tipo es mensual
		} else if (type.equalsIgnoreCase("monthly")) {
			try {
				//Recuperamos el limite anterior
				this.card.setCashLimitMonthly(oldAmount);
			} catch (IncorrectLimitException e) {
				LOG.info(e.getMessage());
			}
			//Si no se indica el tipo de limite a modificar adecuadamente no va a realizar la operacion
		} else {
			LOG.info("Limit type not defined");
		}
	}

	/**
	 * Rehace la modificacion del limite de compra despues de haberlo deshecho
	 */
	@Override
	public void redo() {
		//Si el tipo es diario
		if (type.equalsIgnoreCase("diary")) {
			try {
				//Volvemos a cambiar el limite por el que lo habiamos cambiado anteriormente
				this.card.setCashLimitDiary(newAmount);
			} catch (IncorrectLimitException e) {
				LOG.info(e.getMessage());
			}
			//Si el tipo es mensual
		} else if (type.equalsIgnoreCase("monthly")) {
			try {
				//Volvemos a cambiar el limite por el que lo habiamos cambiado anteriormente
				this.card.setCashLimitMonthly(newAmount);
			} catch (IncorrectLimitException e) {
				LOG.info(e.getMessage());
			}
			//Si no se indica el tipo de limite a modificar adecuadamente no va a realizar la operacion
		} else {
			LOG.info("Limit type not defined");
		}
	}
	
	/**
	 * Devuelve el identificador del comando
	 */
	@Override
	public Handler getId() {
		return this.id;
	}
}
