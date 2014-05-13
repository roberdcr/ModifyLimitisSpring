package es.unileon.ulebank.payments.service;

import java.io.Serializable;
import java.util.List;

import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.domain.CreditCard;

public interface CardManager extends Serializable {

    public void changeBuyLimits(double diary, double monthly) throws IncorrectLimitException;
    public void changeCashLimits(double diary, double monthly) throws IncorrectLimitException;
    
    public List<CreditCard> getProducts();

}