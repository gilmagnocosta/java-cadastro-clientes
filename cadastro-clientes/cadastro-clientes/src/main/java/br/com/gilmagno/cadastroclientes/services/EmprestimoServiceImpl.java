package br.com.gilmagno.cadastroclientes.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.gilmagno.cadastroclientes.dto.DadosSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.dto.ResultadoSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;

/**
 * Classe de regras de negócio para emprestimos do cliente
 * @author Gilmagno
 *
 */
@Service
public class EmprestimoServiceImpl implements EmprestimoService {

	/**
	 * Realiza uma simulação de emprestimo de acordo com dados informados do cliente
	 * Já que não foi informado se a taxa de juros seria mensal ou anual ou se seria juros compostos ou nao, 
	 * foi aplicada a taxa em cima do valor da simulação e mostrado a parcela mensal.
	 */
	@Override
	public ResultadoSimulacaoDTO simular(DadosSimulacaoDTO dadosSimulacao) throws ServicoException {
		BigDecimal valorTotalComJuros = new BigDecimal(0);
		BigDecimal valorParcela = new BigDecimal(0);
		ResultadoSimulacaoDTO resultadoSimulacao = new ResultadoSimulacaoDTO();
		
		try {
			valorTotalComJuros = dadosSimulacao.getValorSolicitado()
					.add(dadosSimulacao.getValorSolicitado()
							.multiply(dadosSimulacao.getTaxaJuros())
							.divide(new BigDecimal(100)));
			
			valorParcela = valorTotalComJuros
					.divide(new BigDecimal(dadosSimulacao.getMesesDuracao()), 2, RoundingMode.HALF_UP);
			
			resultadoSimulacao.setDadosSimulacao(dadosSimulacao);
			resultadoSimulacao.setValorPrestacao(valorParcela);
			resultadoSimulacao.setValorTotal(valorTotalComJuros);
			
			return resultadoSimulacao;
			
		}catch (Exception e) {
			throw new ServicoException("Ocorreu um erro no calculo de simulação de emprestimo do cliente", e);
		}
	}

}
