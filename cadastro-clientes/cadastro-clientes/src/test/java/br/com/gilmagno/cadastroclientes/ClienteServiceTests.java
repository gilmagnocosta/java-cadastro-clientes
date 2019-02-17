package br.com.gilmagno.cadastroclientes;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.repositories.ClienteRepository;
import br.com.gilmagno.cadastroclientes.services.ClienteServiceImpl;

/**
 * Classe de testes unitarios dos metodos do classe ClienteServiceTests
 * Utilizando JUnit e Mockito
 * @author Gilmagno
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteServiceTests {
	
	@Mock
	private ClienteRepository clienteRepositoryMock;
	
	@InjectMocks
	private ClienteServiceImpl clienteService;
	
	@Test
	public void testarListarTodosOsClientes() throws ServicoException {
		List<Cliente> listaEsperada = new ArrayList<Cliente>();
		listaEsperada.add(new Cliente());
		listaEsperada.add(new Cliente());
		listaEsperada.add(new Cliente());
		
		Mockito.when(clienteRepositoryMock.findAll()).thenReturn(listaEsperada);
		List<Cliente> clientes = this.clienteService.listarClientes();
		assertTrue(clientes.size() == 3);
	}
	
	@Test
	public void testarConsultarClientePorCodigo() throws ServicoException {
		long codigoCliente = 1;
		Cliente clienteEsperado = new Cliente();
		clienteEsperado.setCodigo(codigoCliente);
		clienteEsperado.setNome("Teste");
				
		Mockito.when(clienteRepositoryMock.getOne(codigoCliente)).thenReturn(clienteEsperado);
		Cliente cliente = this.clienteService.consultarPorCodigo(1L);
		assertTrue(cliente.getCodigo() == codigoCliente);
		assertTrue(cliente.getNome() == "Teste");
	}
	
	@Test
	public void testarInserirNovoCliente() throws ServicoException {
		Cliente clienteEsperado = new Cliente();
		clienteEsperado.setNome("Teste");
		clienteEsperado.setAtualmenteEmpregado(false);
		clienteEsperado.setEndereco("Rua teste");
		clienteEsperado.setRendimentoMensal(new BigDecimal("2000"));
		clienteEsperado.setValorTotalDividas(new BigDecimal("500"));
		clienteEsperado.setValorTotalPatrimonio(new BigDecimal("20000"));
		clienteEsperado.setRisco("A");
		clienteEsperado.setTipoCliente(1);
				
		Mockito.when(clienteRepositoryMock.save(clienteEsperado)).thenReturn(clienteEsperado);
		
		Cliente cliente = this.clienteService.salvar(clienteEsperado);
		assertTrue(cliente != null);
		assertTrue(cliente.getNome() == "Teste");
	}
	
	@Test
	public void testarAtualizarCliente() throws ServicoException {
		Cliente clienteOriginal = new Cliente();
		clienteOriginal.setCodigo(1L);
		clienteOriginal.setNome("Teste");
		clienteOriginal.setAtualmenteEmpregado(false);
		clienteOriginal.setEndereco("Rua teste");
		clienteOriginal.setRendimentoMensal(new BigDecimal("2000"));
		clienteOriginal.setValorTotalDividas(new BigDecimal("500"));
		clienteOriginal.setValorTotalPatrimonio(new BigDecimal("20000"));
		clienteOriginal.setRisco("A");
		clienteOriginal.setTipoCliente(1);
		
		Cliente clienteEsperado = new Cliente();
		clienteEsperado.setCodigo(1L);
		clienteEsperado.setNome("Teste Alterado");
		clienteEsperado.setAtualmenteEmpregado(false);
		clienteEsperado.setEndereco("Rua teste");
		clienteEsperado.setRendimentoMensal(new BigDecimal("2000"));
		clienteEsperado.setValorTotalDividas(new BigDecimal("500"));
		clienteEsperado.setValorTotalPatrimonio(new BigDecimal("20000"));
		clienteEsperado.setRisco("A");
		clienteEsperado.setTipoCliente(2);
				
		Mockito.when(clienteRepositoryMock.save(clienteOriginal)).thenReturn(clienteEsperado);
		
		Cliente cliente = this.clienteService.salvar(clienteOriginal);
		assertTrue(cliente.getCodigo() == 1L);
		assertTrue(cliente.getNome() == "Teste Alterado");
		assertTrue(cliente.getTipoCliente() == 2);
	}
}
