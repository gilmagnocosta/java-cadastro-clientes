package br.com.gilmagno.cadastroclientes.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe para retorno de resultado de simulação de emprestimo
 * Já que não foi informado se a taxa de juros seria mensal ou anual ou se seria juros compostos ou nao, 
 * foi aplicada a taxa em cima do valor da simulação e mostrado a parcela mensal.
 * 
 * @author Gilmagno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoSimulacaoDTO {
	
	/**
	 * Dados iniciais da simulação
	 */
	private DadosSimulacaoDTO dadosSimulacao;
	
	/**
	 * Valor atualizado da prestação com a taxa de juros 
	 */
	private BigDecimal valorPrestacao;
	
	/**
	 * Valor total final da simulação de emprestimo
	 */
	private BigDecimal valorTotal;
	
}
