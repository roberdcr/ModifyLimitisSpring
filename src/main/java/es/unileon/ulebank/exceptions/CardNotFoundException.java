package es.unileon.ulebank.exceptions;

public class CardNotFoundException extends Exception {
	
private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la clase
	 * @param message
	 */
	public CardNotFoundException(String message) {
		super(message);
	}
}
