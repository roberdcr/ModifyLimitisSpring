package es.unileon.ulebank.exceptions;

/**
 * @author Israel
 * Esta excepcion se lanza cuando el limite que se introduce para una tarjeta es inferior 
 * al limite minimo de la misma
 */
public class IncorrectLimitException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public IncorrectLimitException(String message) {
		super(message);
	}
}
