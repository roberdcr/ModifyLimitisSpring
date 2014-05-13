/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import es.unileon.ulebank.handler.EnterpriseHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.exceptions.MalformedHandlerException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that implements the data of an enterprise
 * 
 * @author Gonzalo Nicol��s Barreales
 */
public class Enterprise extends Client{

    /**
     * 
     */
    private ArrayList<Person> authorizedPersons;
    
    /**
     * 
     */
    private String enterpriseName;
    
    /**
     * 
     */
    private Address address;
    
    /**
     * 
     * @param cifLetter
     * @param cifNumber 
     */
    public Enterprise(char cifLetter, int cifNumber, char cifControl) throws MalformedHandlerException {
        super(new EnterpriseHandler(cifLetter, cifNumber, cifControl));
        authorizedPersons = new ArrayList<Person>();
    }
    
    /**
     * 
     * @param cifLetter
     * @param cifNumber
     * @param enterpriseName
     * @param address 
     */
    public Enterprise(char cifLetter, int cifNumber, char cifControl, String enterpriseName, Address address) throws MalformedHandlerException {
        super(new EnterpriseHandler(cifLetter, cifNumber, cifControl));
        authorizedPersons = new ArrayList<Person>();
        this.enterpriseName=enterpriseName;
        this.address = address;
    }
    
    /**
     * 
     * @param person 
     */
    public void addAuthorizedPerson(Person person){
        if(!existsAuthorizedPerson(person.getId()))
            authorizedPersons.add(person);
    }
    
    /**
     * 
     * @param personHandler
     * @return 
     */
    public Person removeAuthorizedPerson(Handler personHandler){
        Person result=null;
        Iterator<Person> iterator = authorizedPersons.iterator();
        while(iterator.hasNext()){
            Person person = iterator.next();
            if(person.getId().compareTo(personHandler)==0){
                result=person;
                authorizedPersons.remove(person);
            }
        }
        return result;
    }
    
    /**
     * 
     * @param personHandler
     * @return 
     */
    public boolean existsAuthorizedPerson(Handler personHandler){
        boolean result=false;
        Iterator<Person> iterator = authorizedPersons.iterator();
        while(iterator.hasNext()){
            Person person = iterator.next();
            if(person.getId().compareTo(personHandler)==0)
                result=true;
        }
        return result;
    }
    
    /**
     * 
     * @return 
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }
    
    /**
     * 
     * @param enterpriseName 
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    /**
     * 
     * @return 
     */
    public Address getAddress() {
        return address;
    }
    /**
     * 
     * @param address 
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    
    
}
