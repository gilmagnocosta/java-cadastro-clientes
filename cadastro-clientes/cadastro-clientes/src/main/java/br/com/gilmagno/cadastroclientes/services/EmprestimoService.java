package br.com.gilmagno.cadastroclientes.services;

import br.com.gilmagno.cadastroclientes.dto.DadosSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.dto.ResultadoSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;

/**
 * Interface com ações especificas para emprestimo do cliente
 * @author Gilmagno
 *
 */
public interface EmprestimoService {
	
	/**
	 * Realiza uma simulação de emprestimo de acordo com dados informados do cliente
	 * @param dadosSimulacao
	 * @return
	 * @throws ServicoException
	 */
	ResultadoSimulacaoDTO simular(DadosSimulacaoDTO dadosSimulacao) throws ServicoException;
	
}
