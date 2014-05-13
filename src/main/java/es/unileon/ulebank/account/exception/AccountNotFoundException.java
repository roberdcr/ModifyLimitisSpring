package es.unileon.ulebank.account.exception;

public class AccountNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String msg) {
		super(msg);
	}
}
