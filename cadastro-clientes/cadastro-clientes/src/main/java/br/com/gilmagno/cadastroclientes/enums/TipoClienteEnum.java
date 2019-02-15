package br.com.gilmagno.cadastroclientes.enums;

import lombok.Getter;

/**
 * Enum para definição de tipos de clientes
 * @author Gilmagno
 *
 */
@Getter
public enum TipoClienteEnum {
	Comum(1),
	Especial(2),
	Potencial(3);
	
	private final int valor;
	
	private TipoClienteEnum(int valor) {
		this.valor = valor;
	}
}
