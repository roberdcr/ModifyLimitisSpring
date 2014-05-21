package es.unileon.ulebank.repository;

import es.unileon.ulebank.payments.Card;

/**
 * Interface for Database Access Object
 * @author Rober dCR
 * @date 21/05/2014
 * @brief Interface of methods which works with the objects of the database
 */
public interface CardDAO {
	
	/**
	 * Method that obtains the card of the database;
	 * @param card_id Identifier of the card which get of the BBDD
	 * @return Card of the BBDD
	 */
	public Card getCardDAO(String card_id);
	
	/**
	 * Method that saves the card modified in the BBDD
	 * @param card
	 */
	public void saveCard(Card card);
}
