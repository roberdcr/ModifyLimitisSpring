package es.unileon.ulebank.payments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import es.unileon.ulebank.exceptions.SecurityCardException;

/**
 * SecurityCard Class
 * @author Rober dCR
 * @date 26/03/2014
 * @brief Class about the security matrix for the card which allows transactions in TPVs 
 * Based on "CajaEspanya" SecurityCard
 */
public class SecurityCard {

	/**
	 * Global variable for search the row dimension in card.properties
	 */
	private final String DIMENSION_ROW = "row";
	/**
	 * Global variable for search the column dimension in card.properties
	 */
	private final String DIMENSION_COLUMNS = "columns";
	/**
	 * Value that indicates if the SecurityCard is given to the owner
	 */
	private boolean activate;
	/**
	 * Number of the rows in the matrix
	 */
	private int row;
	/**
	 * Number of the columns in the matrix
	 */
	private int columns;
	/**
	 * Matrix which store the coordinates of the security card
	 */
	private Integer[][] coordinates;
	/**
	 * Card which owns this security card
	 */
	private Card associatedCard;
	
	/**
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @brief Security Card constructor
	 */
	public SecurityCard(Card card) throws NumberFormatException, IOException{
		this.setDefaultRow();
		this.setDefaultColumns();
		this.coordinates = new Integer[row][columns];
		this.createCoordinates(this.coordinates);
		this.associatedCard = card;
		this.activate = false;
	}
	
	/**
	 * @brief Method fill the matrix of coordinates randomly
	 * @param coordinates
	 */
	private void createCoordinates(Integer[][] coordinates){
		Random randomGenerator = new Random();
		
		for (int i = 0; i < this.row; i++ ){
			for (int j = 0; j < this.columns; j++){		
				this.coordinates[i][j] = randomGenerator.nextInt(100);
			}
		}
	}
	
	/**
	 * @brief Method get the coordinate of a row and column specified
	 * @param row of the matrix
	 * @param column column of the matrix
	 * @return coordinate store in the matrix
	 * @throws SecurityCardException 
	 */
	private Integer getCoordinate(int row, int column) throws SecurityCardException{
		if ( ( (row >= 0) && (row < this.row) ) && ( (column >= 0) && (column < this.columns) ) )
			return this.coordinates[row][column];
		else
			throw new SecurityCardException("Index out of range");
	}
	
	/**
	 * @brief Method that probe if the coordinate to check is really in the coordinates indicated
	 * @param row of the matrix
	 * @param column of the matrix
	 * @param coordinate to check
	 * @return true if coordinate is correct / false another case
	 * @throws SecurityCardException 
	 */
	public boolean checkCoordinates(int row, int column, int coordinate) throws SecurityCardException{
		return this.getCoordinate(row, column).equals(coordinate);
	}
	
	/**
	 * Method that deliver to the owner the security card coordinates only one time
	 * if cardPin is correct
	 * @param cardPin
	 * @return Array of Integers
	 * @throws SecurityCardException 
	 */
	public Integer[][] deliverSecurityCard(String cardPin) throws SecurityCardException{
		if (this.activate == true)
			throw new SecurityCardException("This Security Card has activated yet");
		else if(!this.associatedCard.checkPin(cardPin))
			throw new SecurityCardException("Invalid pin or this Security Card has activated yet");
		else {
			this.activate = true;
			return this.coordinates;
		}
	}
	
	/**
	 * Getter of the associated Card
	 * @return associatedCard
	 */
	public Card getAssociatedCard(){
		return this.associatedCard;
	}
	
	/**
	 * Method that establish the number of the rows specified in card.properties
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private void setDefaultRow() throws NumberFormatException, IOException{

		try {
			Properties commissionProperty = new Properties();
			commissionProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));

			/**Obtenemos los parametros definidos en el archivo*/
			this.row = Integer.parseInt(commissionProperty.getProperty(this.DIMENSION_ROW));
		}
		catch(FileNotFoundException e){
			throw new FileNotFoundException("The file card.properties is not found.");
		}catch (IOException e2) {
			throw new IOException("Fail to try open or close file card.properties");
		}

	}
	
	/**
	 * Method that establish the number of the columns specified in card.properties 
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private void setDefaultColumns() throws NumberFormatException, IOException{

		try {
			Properties commissionProperty = new Properties();
			commissionProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));

			/*Obtenemos los parametros definidos en el archivo*/
			this.columns = Integer.parseInt(commissionProperty.getProperty(this.DIMENSION_COLUMNS));
		}
		catch(FileNotFoundException e){
			throw new FileNotFoundException("The file card.properties is not found.");
		}catch (IOException e2) {
			throw new IOException("Fail to try open or close file card.properties");
		}

	}
		
}
