package br.com.gilmagno.cadastroclientes.exceptions;

public class ServicoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicoException(String msg) {
		super(msg);
	}
	
	public ServicoException(String msg, Throwable excecao) {
		super(msg, excecao);
	}
}
