package br.com.gilmagno.cadastroclientes.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DadosSimulacaoDTO {
	
	/**
	 * Codigo do cliente
	 */
	private Long codigoCliente;
	
	/**
	 * Valor solicitado para simular o emprestimo.
	 */
	private BigDecimal valorSolicitado;
	
	/**
	 * Quantidade de meses de duração do emprestimo a ser simulado
	 */
	private Integer mesesDuracao;
}
