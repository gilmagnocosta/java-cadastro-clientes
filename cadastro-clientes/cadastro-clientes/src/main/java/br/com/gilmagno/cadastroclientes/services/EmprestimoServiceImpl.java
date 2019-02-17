package br.com.gilmagno.cadastroclientes.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gilmagno.cadastroclientes.dto.DadosSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.dto.ResultadoSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.repositories.ClienteRepository;

/**
 * Classe de regras de negócio para emprestimos do cliente
 * @author Gilmagno
 *
 */
@Service
public class EmprestimoServiceImpl implements EmprestimoService {

	private final ClienteRepository clienteRepository;
	
	@Autowired
	public EmprestimoServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	/**
	 * Realiza uma simulação de emprestimo de acordo com dados informados do cliente
	 * Já que não foi informado se a taxa de juros seria mensal ou anual ou se seria juros compostos ou nao, 
	 * foi aplicada a taxa em cima do valor da simulação e mostrado a parcela mensal.
	 */
	@Override
	public ResultadoSimulacaoDTO simular(DadosSimulacaoDTO dadosSimulacao) throws ServicoException {
		BigDecimal taxaJuros = new BigDecimal(0);
		BigDecimal valorTotalComJuros = new BigDecimal(0);
		BigDecimal valorParcela = new BigDecimal(0);
		ResultadoSimulacaoDTO resultadoSimulacao = new ResultadoSimulacaoDTO();
		
		try {
			
			taxaJuros = obterTaxaJuros(dadosSimulacao.getCodigoCliente());
			
			valorTotalComJuros = dadosSimulacao.getValorSolicitado()
					.add(dadosSimulacao.getValorSolicitado()
							.multiply(taxaJuros)
							.divide(new BigDecimal(100)));
			
			valorParcela = valorTotalComJuros
					.divide(new BigDecimal(dadosSimulacao.getMesesDuracao()), 2, RoundingMode.HALF_EVEN);
			
			resultadoSimulacao.setDadosSimulacao(dadosSimulacao);
			resultadoSimulacao.setValorPrestacao(valorParcela);
			resultadoSimulacao.setValorTotal(valorTotalComJuros.setScale(2, RoundingMode.HALF_EVEN));
			
			return resultadoSimulacao;
			
		}catch (Exception e) {
			throw new ServicoException("Ocorreu um erro no calculo de simulação de emprestimo do cliente", e);
		}
	}

	/**
	 * Obter taxa de juros que deve ser aplicada ao emprestimo do cliente
	 */
	private BigDecimal obterTaxaJuros(Long codigoCliente) throws ServicoException {
		try {
			BigDecimal taxa = new BigDecimal(0);
			
			Cliente cliente = clienteRepository.getOne(codigoCliente);
			
			if (cliente != null) {
				switch (cliente.getRisco()) {
				case "A":
					taxa = new BigDecimal(1.9);
					break;
				case "B":
					taxa = new BigDecimal(5);
					break;
				case "C":
					taxa = new BigDecimal(10);
					break;
				default:
					break;
				}
			}
			
			return taxa;
			
		}catch (Exception e) {
			throw new ServicoException("Erro ao consultar o cliente", e);
		}
	}	
}