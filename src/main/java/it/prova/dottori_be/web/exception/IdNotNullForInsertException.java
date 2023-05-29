package it.prova.dottori_be.web.exception;

public class IdNotNullForInsertException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IdNotNullForInsertException(String message) {
		super(message);
	}
}
