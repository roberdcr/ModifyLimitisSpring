package es.unileon.ulebank.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.unileon.ulebank.payments.Card;

/**
 * Class of JPACardDao
 * @author Rober dCR
 * @date 21/05/2014
 * @brief Class for work with the database "Payments" 
 */
@Repository(value = "cardtDao")
public class JPACardDAO implements CardDAO {

	/**
	 * Entity for manage the data of BBDD
	 */
	private EntityManager em = null;

	/**
	 * Sets the entity manager.
	 * @param em
	 */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
        
    @Transactional(readOnly = true)
	@Override
	public Card getCardDAO(String card_id) {
    	return (Card) em.createQuery("select c from Card c where id =" + card_id).getSingleResult();
	}

    @Transactional(readOnly = false)
    @Override
    public void saveCard(Card card) {
        em.merge(card);
    }
    
}
