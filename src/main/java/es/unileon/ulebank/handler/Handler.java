package es.unileon.ulebank.handler;
/**
 * @author Israel
 *  Interface para implementar el patron Handler permitiendo tener identificadores unicos
 */
public interface Handler {
	/**
	 * Compara el Handler con otro y devuelve un entero en funcion de la similitud
	 * @param another
	 * @return
	 */
	public int compareTo(Handler another);
	/**
	 * Devuelve el Handler en forma de String
	 * @return
	 */
	public String toString();
}
