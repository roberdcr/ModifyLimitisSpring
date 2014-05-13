package es.unileon.ulebank.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unileon.ulebank.exceptions.MalformedHandlerException;

public class ShareHandler implements Handler {

	private String ticker;
	private String company;
	private String stockMarket;

	public ShareHandler(String ticker, String company, String stockMarket)
			throws MalformedHandlerException {
		StringBuilder errors = new StringBuilder();

		errors.append("");

		Pattern patternTicker = Pattern.compile("[.]");
		Matcher matcherTicker = patternTicker.matcher(ticker);

		if (!(ticker.length() == 3) && !(ticker.length() == 4)) {
			errors.append("The tocker must be at least 3 or 4 characters.\n");
		}
		
		if ((company.substring(0, 3).toUpperCase().compareTo(ticker.toUpperCase()) != 0)
				&& company.substring(0, 4).toUpperCase().compareTo(ticker.toUpperCase()) != 0) {
			errors.append("Ticker is malformed.\n");
		}

		if (matcherTicker.find()) {
			errors.append("Ticker must be a character.\n");

		}

		if (errors.length() > 0) {
			throw new MalformedHandlerException(errors.toString());
		}

		this.ticker = ticker.toUpperCase();
		this.company = company;
		this.stockMarket = stockMarket;
	}

	public String toString() {
		return this.ticker + " " + this.company + " " + this.stockMarket;
	}

	public int compareTo(Handler other) {
		return this.toString().compareTo(other.toString());
	}

}
