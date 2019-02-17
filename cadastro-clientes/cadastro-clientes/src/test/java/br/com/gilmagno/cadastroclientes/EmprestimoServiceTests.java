package br.com.gilmagno.cadastroclientes;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gilmagno.cadastroclientes.dto.DadosSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.dto.ResultadoSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.repositories.ClienteRepository;
import br.com.gilmagno.cadastroclientes.services.EmprestimoServiceImpl;

/**
 * Classe de testes unitarios dos metodos do classe EmprestimoServiceTests
 * Utilizando JUnit e Mockito
 * @author Gilmagno
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmprestimoServiceTests {
	
	@Mock
	private ClienteRepository clienteRepositoryMock;
	
	@InjectMocks
	private EmprestimoServiceImpl emprestimoService;
	
	private static Cliente clienteRiscoAMock;
	private static Cliente clienteRiscoBMock;
	private static Cliente clienteRiscoCMock;
	
	@BeforeClass
	public static void criarClienteMock() {
		clienteRiscoAMock = new Cliente();
		clienteRiscoAMock.setCodigo(1L);
		clienteRiscoAMock.setRisco("A");
		
		clienteRiscoBMock = new Cliente();
		clienteRiscoBMock.setCodigo(2L);
		clienteRiscoBMock.setRisco("B");
		
		clienteRiscoCMock = new Cliente();
		clienteRiscoCMock.setCodigo(3L);
		clienteRiscoCMock.setRisco("C");
	}
	
	@Test
	public void testarSimulacaoEmprestimoClienteRiscoA() throws ServicoException {
		DadosSimulacaoDTO dadosSimulacao = new DadosSimulacaoDTO();
		dadosSimulacao.setCodigoCliente(EmprestimoServiceTests.clienteRiscoAMock.getCodigo());
		dadosSimulacao.setMesesDuracao(12);
		dadosSimulacao.setValorSolicitado(new BigDecimal(10000));
		
		Mockito.when(clienteRepositoryMock.getOne(clienteRiscoAMock.getCodigo())).thenReturn(clienteRiscoAMock);
		
		ResultadoSimulacaoDTO resultado = emprestimoService.simular(dadosSimulacao);
		
		assertEquals(new BigDecimal(10190).compareTo(resultado.getValorTotal()), 0);
		assertEquals(new BigDecimal(849.17).setScale(2, RoundingMode.HALF_EVEN).compareTo(resultado.getValorPrestacao()), 0);
	}
	
	@Test
	public void testarSimulacaoEmprestimoClienteRiscoB() throws ServicoException {
		DadosSimulacaoDTO dadosSimulacao = new DadosSimulacaoDTO();
		dadosSimulacao.setCodigoCliente(EmprestimoServiceTests.clienteRiscoBMock.getCodigo());
		dadosSimulacao.setMesesDuracao(12);
		dadosSimulacao.setValorSolicitado(new BigDecimal(10000));
		
		Mockito.when(clienteRepositoryMock.getOne(clienteRiscoBMock.getCodigo())).thenReturn(clienteRiscoBMock);
		
		ResultadoSimulacaoDTO resultado = emprestimoService.simular(dadosSimulacao);
		
		assertEquals(new BigDecimal(10500).compareTo(resultado.getValorTotal()), 0);
		assertEquals(new BigDecimal(875).setScale(2, RoundingMode.HALF_EVEN).compareTo(resultado.getValorPrestacao()), 0);
	}
	
	@Test
	public void testarSimulacaoEmprestimoClienteRiscoC() throws ServicoException {
		DadosSimulacaoDTO dadosSimulacao = new DadosSimulacaoDTO();
		dadosSimulacao.setCodigoCliente(EmprestimoServiceTests.clienteRiscoCMock.getCodigo());
		dadosSimulacao.setMesesDuracao(12);
		dadosSimulacao.setValorSolicitado(new BigDecimal(10000));
		
		Mockito.when(clienteRepositoryMock.getOne(clienteRiscoCMock.getCodigo())).thenReturn(clienteRiscoCMock);
		
		ResultadoSimulacaoDTO resultado = emprestimoService.simular(dadosSimulacao);
		
		assertEquals(new BigDecimal(11000).compareTo(resultado.getValorTotal()), 0);
		assertEquals(new BigDecimal(916.67).setScale(2, RoundingMode.HALF_EVEN).compareTo(resultado.getValorPrestacao()), 0);
	}
}
