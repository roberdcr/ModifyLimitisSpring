package es.unileon.ulebank.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CommandHandler class
 * @author Rober dCR
 * @date 9/04/2014
 * @brief Class of the identifier for Commands
 */
public class CommandHandler implements Handler {

	private Handler id;
	private String date;
	
	/**
	 * Class constructor
	 * @param id
	 */
	public CommandHandler(Handler id){
		this.id = id;
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		this.date = dateFormat.format(new Date());
	}
	
	@Override
	public int compareTo(Handler another) {
		return this.toString().compareTo(another.toString());
	}
	
	/**
	 * Getter id
	 * @return id
	 */
	public Handler getId(){
		return this.id;
	}

	/**
	 * Getter date
	 * @return String
	 */
	public String getDate(){
		return this.date.toString();
	}
	
	/**
	 * Devuelve en una cadena de strings el id y la fecha
	 */
	public String toString() {
		return this.id.toString() + " " + date.toString();
	}
}
