package es.unileon.ulebank.payments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.exceptions.PaymentException;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.History;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionException;

/**
 * @author Israel
 */
public abstract class Card {
	
	/**
     * El logger de la clase
     */
    private static final Logger LOG = Logger.getLogger(Card.class.getName());
	/**
	 * String para obtener el limite de compra diario del fichero de propiedades
	 */
	private final String BUY_LIMIT_DIARY_DEFAULT = "buy_limit_diary";
	/**
	 * String para obtener el limite de compra mensual del fichero de propiedades
	 */
	private final String BUY_LIMIT_MONTHLY_DEFAULT = "buy_limit_monthly";
	/**
	 * String para obtener el limite de extraccion en cajero diario del fichero de propiedades
	 */
	private final String CASH_LIMIT_DIARY_DEFAULT = "cash_limit_diary";
	/**
	 * String para obtener el limite de extraccion en cajero mensual del fichero de propiedades
	 */
	private final String CASH_LIMIT_MONTHLY_DEFAULT = "cash_limit_monthly";
	/**
	 * String para obtener el limite minimo del fichero de propiedades
	 */
	private final String MINIMUM_LIMIT = "minimum_limit";
	/**
	 * String para obtener el numero de agnos de caducidad del fichero de propiedades
	 */
	private final String EXPIRATION_YEAR = "expiration_year";
	/**
	 * String para obtener el tamagno del codigo CVV del fichero de propiedades
	 */
	private final String CVV_SIZE = "cvv_size";
	/**
	 * String para obtener el tamagno del codigo PIN del fichero de propiedades
	 */
	private final String PIN_SIZE = "pin_size";
	
	/**
	 * Limite de compra diario por defecto
	 */
	private double buyLimitDiaryDefault;
	/**
	 * Limite de compra mensual por defecto
	 */
	private double buyLimitMonthlyDefault;
	/**
	 * Limite de extraccion en cajero diario por defecto
	 */
	private double cashLimitDiaryDefault;
	/**
	 * Limite de extraccion en cajero mensual por defecto
	 */
	private double cashLimitMonthlyDefault;
	/**
	 * Limite minimo de la tarjeta
	 */
	private double minimumLimit;
	/**
	 * Agnos de caducidad de la tarjeta
	 */
	private int expirationYear;
	/**
	 * Tamagno del codigo CVV
	 */
	private int cvvSize;
	/**
	 * Tamagno del codigo PIN
	 */
	private int pinSize;
	
	/**
	 * Identificador de la tarjeta
	 */
	private Handler cardId;
	/**
	 * Codigo PIN de la tarjeta
	 */
	private String pin;
	/**
	 * Limite de compra diario de la tarjeta
	 */
	private double buyLimitDiary;
	/**
	 * Limite de compra mensual de la tarjeta
	 */
	private double buyLimitMonthly;
	/**
	 * Limite de extraccion en cajero diario de la tarjeta
	 */
	private double cashLimitDiary;
	/**
	 * Limite de extraccion en cajero mensual de la tarjeta
	 */
	private double cashLimitMonthly;
	/**
	 * Fecha de emision de la tarjeta
	 */
	private String emissionDate;
	/**
	 * Fecha de caducidad de la tarjeta
	 */
	private String expirationDate;
	/**
	 * Tipo de tarjeta
	 */
	private CardType cardType;
	/**
	 * Codigo CVV de la tarjeta
	 */
	private String cvv;
	/**
	 * Comision de emision de la tarjeta
	 */
	private FeeStrategy commissionEmission;
	/**
	 * Comision de mantenimiento de la tarjeta
	 */
	private FeeStrategy commissionMaintenance;
	/**
	 * Comision de renovacion de la tarjeta
	 */
	private FeeStrategy commissionRenovate;
	/**
	 * Historia de las transacciones realizadas con la tarjeta
	 */
	private History<Transaction> transactionHistory;
	
	/**
	 * Crea una nueva tarjeta con los parametros indicados
	 * @param cardId
	 * @param owner
	 * @param account
	 * @param type
	 * @param buyLimitDiary
	 * @param buyLimitMonthly
	 * @param cashLimitDiary
	 * @param cashLimitMonthly
	 * @param commissionEmission
	 * @param commissionMaintenance
	 * @param commissionRenovate
	 */
	public Card(Handler cardId, CardType type, double buyLimitDiary, double buyLimitMonthly, 
			double cashLimitDiary, double cashLimitMonthly, FeeStrategy commissionEmission, 
			FeeStrategy commissionMaintenance, FeeStrategy commissionRenovate) {
		try {
			this.setDefaultCardProperties();
		} catch (FileNotFoundException e) {
			LOG.info("The file is not found.");
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}
		this.cardId = (CardHandler) cardId;
		this.cardType = type;
		this.pin = generatePinCode();
		this.buyLimitDiary = buyLimitDiary;
		this.buyLimitMonthly = buyLimitMonthly;
		this.cashLimitDiary = cashLimitDiary;
		this.cashLimitMonthly = cashLimitMonthly;
		this.emissionDate = generateEmissionDate();
		this.expirationDate = generateExpirationDate();
		this.cvv = this.generateCVV();
		this.commissionEmission = commissionEmission;
		this.commissionMaintenance = commissionMaintenance;
		this.commissionRenovate = commissionRenovate;
		this.transactionHistory = new History<Transaction>();
	}
	
	/**
	 * Inicializa los valores por defecto sacandolos del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setDefaultCardProperties() throws FileNotFoundException, IOException {
		this.setBuyLimitDiaryDefault();
		this.setBuyLimitMonthlyDefault();
		this.setCashLimitDiaryDefault();
		this.setCashLimitMonthlyDefault();
		this.setMinimumLimit();
		this.setExpirationYear();
		this.setCvvSize();
		this.setPinSize();
	}

	/**
	 * Genera el codigo pin de la tarjeta
	 * @return String
	 */
	public String generatePinCode() {
		StringBuilder result = new StringBuilder();
		//Generamos tantos numeros aleatorios como indique la constante PIN_SIZE para formar el codigo PIN
		for (int i = 0; i < pinSize; i++) {
			result.append((int) (Math.random()*10));
		}
		
		return result.toString();
	}
	
	/**
	 * Genera la fecha de emision de la tarjeta
	 * @return String
	 */
	public String generateEmissionDate() {
		//Generamos la fecha dandole el formato estandar dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(new Date());
	}
	
	/**
	 * Genera una fecha de caducidad para la tarjeta
	 * @return String
	 */
	public String generateExpirationDate() {
		//Obtenemos una instancia del calendario
		Calendar calendar = Calendar.getInstance();
		
		//Obtenemos el mes actual que sera devuelto en forma de int comenzando en 0 (Enero) por tanto debemos sumarle 1
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1); 
		//Si el mes esta entre enero y septiembre agnadimos un 0 delante al String para que tenga 2 cifras
		if (month.length() == 1) {
			month = "0" + month;
		}
		
		//Obtenemos el agno actual y cortamos el substring para coger unicamente las dos ultimas cifras
		String year = Integer.toString(expirationYear + calendar.get(Calendar.YEAR)).substring(2);
		//Devolvemos el String con el formato MM/yy
		return month + "/" + year;
	}
	
	/**
	 * Genera el codigo de validacion CVV
	 * @return String
	 */
	public String generateCVV() {
		StringBuilder result = new StringBuilder();
		//Generamos tantos numeros aleatorios como indique la constante CVV_SIZE para formar el codigo CVV
		for (int i = 0; i < cvvSize; i++) {
			result.append((int) (Math.random()*10));
		}
		
		return result.toString();
	}
	
	/**
	 * Devuelve el identificador de la tarjeta
	 * @return String
	 */
	public String getCardId() {
		return this.cardId.toString();
	}
	
	/**
	 * Devuelve el codigo PIN de la tarjeta
	 * @return String
	 */
	public String getPin() {
		return this.pin;
	}
	
	/**
	 * Cambia el codigo PIN de la tarjeta por el que recibe
	 * @param pin
	 * @throws IOException 
	 */
	public void setPin(String pin) throws IOException {
		//Comprobamos que el String recibido contiene solo numeros
		if (checkStringNumber(pin)) {
			//Si el pin tiene el tamagno adecuado lo cambiamos
			if (pin.length() == pinSize) {
				this.pin = pin;
			//Sino lanzamos una excepcion
			} else {
				throw new IOException("Incorrect length");
			}
		//Sino lanzamos una excepcion ya que solo puede haber numeros
		} else {
			throw new IOException("The pin must only contain numbers");
		}
	}
	
	/**
	 * Comprueba que el pin sea correcto
	 * @param pin
	 * @return boolean
	 */
	public boolean checkPin(String pin) {
		//Si el pin coincide devuelve true
		if (pin.equals(this.pin)) {
			return true;
		//Sino devuelve false
		} else {
			return false;
		}
	}
	
	/**
	 * Devuelve el limite de la tarjeta diario para compras
	 * @return double
	 */
	public double getBuyLimitDiary() {
		return this.buyLimitDiary;
	}
	
	/**
	 * Cambia el linmite de la tarjeta diario para compras
	 * @param newAmount
	 * @throws IncorrectLimitException 
	 */
	public void setBuyLimitDiary(double newAmount) throws IncorrectLimitException {
		//Si el limite mensual es mayor que el limite diario a cambiar y este ultimo es mayor 
		//o igual que el limite minimo, cambiamos el limite
		if (this.buyLimitMonthly > newAmount && newAmount >= minimumLimit) {
			this.buyLimitDiary = newAmount;
		//en caso contrario lanzamos una excepcion
		} else {
			throw new IncorrectLimitException("The limit is bigger than current monthly limit or is too small.");
		}
	}
	
	/**
	 * Comprueba que el precio no exceda el limite de la tarjeta
	 * @param price
	 * @return boolean
	 */
	public boolean checkBuyLimitDiary(double price) {
		//Si el precio es mayor que el limite de compra diario devuelve false
		if (price > buyLimitDiary) {
			return false;
		//sino devuelve true
		} else {
			return true;
		}
	}
	
	/**
	 * Devuelve el limite de la tarjeta mensual para compras
	 * @return double
	 */
	public double getCashLimitMonthly() {
		return cashLimitMonthly;
	}
	
	/**
	 * Devuelve el limite de compra mensual
	 * @return double
	 */
	public double getBuyLimitMonthly() {
		return buyLimitMonthly;
	}

	/**
	 * Cambia el linmite de la tarjeta mensual para compras
	 * @param buyLimit
	 * @throws IncorrectLimitException 
	 */
	public void setBuyLimitMonthly(double newAmount) throws IncorrectLimitException {
		//Si el limite recibido es mayor o igual que el limite diario lo cambiamos
		if (newAmount >= this.buyLimitDiary) {
			this.buyLimitMonthly = newAmount;
		//sino se lanza una excepcion
		} else {
			throw new IncorrectLimitException("Monthly limit must be greater than diary limit");
		}
	}

	/**
	 * Devuelve el limite de la tarjeta para extracciones en cajeros
	 * @return double
	 */
	public double getCashLimitDiary() {
		return this.cashLimitDiary;
	}
	
	/**
	 * Cambia el limite de la tarjeta para extracciones en cajeros
	 * @throws IncorrectLimitException 
	 */
	public void setCashLimitDiary(double newAmount) throws IncorrectLimitException {
		//Si el limite mensual es mayor que el limite diario a cambiar y este ultimo es mayor 
		//o igual que el limite minimo, cambiamos el limite
		if (this.cashLimitMonthly >= newAmount && newAmount >= minimumLimit) {
			this.cashLimitDiary = newAmount;
		//sino lanzamos una excepcion
		} else {
			throw new IncorrectLimitException("The limit is bigger than current monthly limit or is too small.");
		}
	}
	
	/**
	 * Comprueba que la cantidad solicitada para extraer en cajero no exceda el limite de la tarjeta
	 * @param cash
	 * @return boolean
	 */
	public boolean checkCashLimitDiary(double cash) {
		//Si la cantidad solicitada para extraer es mayor que la cantidad maxima diaria devuelve false
		if (cash > this.cashLimitDiary) {
			return false;
		//sino devuelve true
		} else {
			return true;
		}
	}

	/**
	 * Cambia la cantidad maxima para extraer en un cajero al mes
	 * @param newAmount
	 * @throws IncorrectLimitException 
	 */
	public void setCashLimitMonthly(double newAmount) throws IncorrectLimitException {
		//Si el limite recibido es mayor o igual que el limite diario lo cambiamos
		if (newAmount >= this.cashLimitDiary) {
			this.cashLimitMonthly = newAmount;
		//en caso contrario se lanza una excepcion
		} else {
			throw new IncorrectLimitException("Monthly limit must be greater than diary limit");
		}
	}
	
	/**
	 * Devuelve la fecha de emision de la tarjeta
	 * @return String
	 */
	public String getEmissionDate() {
		return emissionDate;
	}

	/**
	 * Devuelve la fecha de caducidad de la tarjeta
	 * @return String
	 */
	public String getExpirationDate() {
		return this.expirationDate;
	}
	
	/**
	 * Cambia la fecha de caducidad por una nueva
	 * @param expirationDate
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Devuelve el tipo de tarjeta
	 * @return CardType
	 */
	public CardType getCardType() {
		return this.cardType;
	}

	/**
	 * Devuelve el codigo de validacion CVV
	 * @return String
	 */
	public String getCvv() {
		return this.cvv;
	}

	/**
	 * Cambia el CVV por el nuevo que ha recibido
	 * @param cvv
	 * @throws IOException 
	 */
	public void setCvv(String cvv) throws IOException {
		//Comprueba que el String contenga solo numeros
		if (checkStringNumber(cvv)) {
			//Si el tamagno del cvv es correcto se cambia
			if (cvv.length() == cvvSize) {
				this.cvv = cvv;
			//sino se lanza una excepcion
			} else {
				throw new IOException("Incorrect length");
			}
		//sino se lanza una excepcion
		} else {
			throw new IOException("The cvv must only contains numbers");
		}
	}

	/**
	 * Devuelve el numero de la tarjeta
	 * @return Handler
	 */
	public Handler getCardNumber() {
		return cardId;
	}

	/**
	 * Devuelve el limite diario de compra por defecto
	 * @return double
	 */
	public double getBuyLimitDiaryDefault() {
		return buyLimitDiaryDefault;
	}
	
	/**
	 * Cambia el valor del limite de compra diario leyendolo del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setBuyLimitDiaryDefault() throws FileNotFoundException, IOException {
		Properties ageProperty = new Properties();
		ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.buyLimitDiaryDefault = Double.parseDouble(ageProperty.getProperty(this.BUY_LIMIT_DIARY_DEFAULT));
	}

	/**
	 * Devuelve el limite mensual de compra por defecto
	 * @return double
	 */
	public double getBuyLimitMonthlyDefault() {
		return buyLimitMonthlyDefault;
	}
	
	/**
	 * Cambia el valor del limite de compra mensual leyendolo del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setBuyLimitMonthlyDefault() throws FileNotFoundException, IOException {
		Properties ageProperty = new Properties();
		ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.buyLimitMonthlyDefault = Double.parseDouble(ageProperty.getProperty(this.BUY_LIMIT_MONTHLY_DEFAULT));
	}

	/**
	 * Devuelve el limite diario de extraccion en cajero por defecto
	 * @return double
	 */
	public double getCashLimitDiaryDefault() {
		return cashLimitDiaryDefault;
	}
	
	/**
	 * Cambia el valor del limite de extraccion en cajero diario leyendolo del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setCashLimitDiaryDefault() throws FileNotFoundException, IOException {
		Properties ageProperty = new Properties();
		ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.cashLimitDiaryDefault = Double.parseDouble(ageProperty.getProperty(this.CASH_LIMIT_DIARY_DEFAULT));
	}

	/**
	 * Devuelve el limite mensual de extraccion en cajero por defecto
	 * @return double
	 */
	public double getCashLimitMonthlyDefault() {
		return cashLimitMonthlyDefault;
	}
	
	/**
	 * Cambia el valor del limite de extraccion en cajero mensual leyendolo del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setCashLimitMonthlyDefault() throws FileNotFoundException, IOException {
		Properties ageProperty = new Properties();
		ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.cashLimitMonthlyDefault = Double.parseDouble(ageProperty.getProperty(this.CASH_LIMIT_MONTHLY_DEFAULT));
	}

	/**
	 * Devuelve la comision de emision de la tarjeta
	 * @return float
	 */
	public double getCommissionEmission(double value) {
		return commissionEmission.getFee(value);
	}

	/**
	 * Cambia la comision de emision por la que recibe
	 * @param commissionEmission
	 */
	public void setCommissionEmission(FeeStrategy commissionEmission) {
		this.commissionEmission = commissionEmission;
	}

	/**
	 * Devuelve la comisionde mantenimiento de la tarjeta
	 * @return float
	 */
	public double getCommissionMaintenance(double value) {
		return commissionMaintenance.getFee(value);
	}

	/**
	 * Cambia la comision de mantenimiento por la que se indica
	 * @param commissionMaintenance
	 */
	public void setCommissionMaintenance(FeeStrategy commissionMaintenance) {
		this.commissionMaintenance = commissionMaintenance;
	}

	/**
	 * Devuelve la comision de renovacion de la tarjeta
	 * @return float
	 */
	public double getCommissionRenovate(double value) {
		return commissionRenovate.getFee(value);
	}

	/**
	 * Cambia la comision de renovacion por la que se recibe
	 * @param commissionRenovate
	 */
	public void setCommissionRenovate(FeeStrategy commissionRenovate) {
		this.commissionRenovate = commissionRenovate;
	}
	
	/**
	 * Cambia el limite minimo de la tarjeta leyendolo del fichero de propiedades
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void setMinimumLimit() throws FileNotFoundException, IOException {
		Properties minimumLimitProperty = new Properties();
		minimumLimitProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.minimumLimit = Double.parseDouble(minimumLimitProperty.getProperty(this.MINIMUM_LIMIT));
	}

	/**
	 * Cambia el numero de agnos de caducidad de la tarjeta sacandolos del fichero de propiedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setExpirationYear() throws FileNotFoundException, IOException {
		Properties expirationYearProperty = new Properties();
		expirationYearProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.expirationYear = Integer.parseInt(expirationYearProperty.getProperty(this.EXPIRATION_YEAR));
	}

	/**
	 * Lee del fichero de propiedades el tamagno del codigo CVV y lo almacena
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void setCvvSize() throws FileNotFoundException, IOException {
		Properties cvvProperty = new Properties();
		cvvProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.cvvSize = Integer.parseInt(cvvProperty.getProperty(this.CVV_SIZE));
	}

	/**
	 * Lee del fichero de propiedades el tamagno del codigo PIN y lo guarda
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setPinSize() throws FileNotFoundException, IOException {
		Properties pinProperty = new Properties();
		pinProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
		
		/**Obtenemos los parametros definidos en el archivo*/
		this.pinSize = Integer.parseInt(pinProperty.getProperty(this.PIN_SIZE));
	}

	/**
	 * Comprueba que el String recibido sea solo numerico
	 * @param string
	 * @return boolean
	 */
	private boolean checkStringNumber(String string) {
		//Crea un patron para indicar que solo debe contener numeros
		Pattern pattern = Pattern.compile("^[0-9]*$");
		//Comprueba que el String recibido cumple el patron
		Matcher matcher = pattern.matcher(string);
		
		//Si se cumple el patron devuelve true
		if (matcher.find()) {
			return true;
		//sino devuelve false
		} else {
			return false;
		}
	}

	/**
	 * Method that adds new transaction in the list
	 * @param transaction
	 * @throws TransactionException 
	 */
	public void addTransaction(Transaction transaction) throws TransactionException{
		//Si devuelve false la transaccion ya esta incluida
		if (!this.transactionHistory.add(transaction))
			throw new TransactionException("Transacion already exists.");
	}
	
	/**
	 * Method that makes the payment
	 * @param receiverAccount Account which receives the money from the card
	 * @param quantity Amount of the payment
	 * @param payConcept Concept of the payment
	 * @throws PaymentException 
	 * @throws TransactionException 
	 */
	public void makeTransaction(Account receiverAccount, double quantity, String payConcept) throws PaymentException, TransactionException {
		
	}

}
