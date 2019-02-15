package br.com.gilmagno.cadastroclientes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.enums.TipoClienteEnum;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.repositories.ClienteRepository;

/**
 * Classe de regras de negócio do objeto Cliente
 * @author Gilmagno
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	
	/**
	 * Construtor padrão para injeção de dependencia do repositorio de cliente.
	 * Aqui utilizo padrão de Constructor Dependency Injection por ser mais recomendado arquiteturalmente
	 * @param clienteRepository
	 */
	@Autowired
	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	/**
	 * Consulta um cliente por codigo
	 */
	@Override
	public Cliente consultarPorCodigo(Long codigo) throws ServicoException {
		try {
			return clienteRepository.getOne(codigo);	
		}catch (Exception e) {
			throw new ServicoException("Erro ao consultar o cliente", e);
		}
	}
	
	/**
	 * Listar todos os clientes ativos
	 */
	@Override
	public List<Cliente> listarClientesAtivos() throws ServicoException {
		try {
			Cliente cliente = new Cliente();
			cliente.setAtivo(true);
			Example<Cliente> filtro = Example.of(cliente);
			
			return clienteRepository.findAll(filtro);
		}catch (Exception e) {
			throw new ServicoException("Erro ao listar os cliente", e);
		}
	}

	/**
	 * Salva os dados de um determinado cliente
	 */
	@Override
	public Cliente salvar(Cliente cliente) throws ServicoException {
		try {
			validarTipoCliente(cliente);
			return clienteRepository.save(cliente);
		}catch (Exception e) {
			throw new ServicoException(e.getMessage(), e);
		}
	}
	
	/**
	 * Realiza a exclusão (exclusão logica) de um cliente
	 */
	@Override
	public void excluir(Long codigo) throws ServicoException {
		try {
			clienteRepository.deleteById(codigo);
		}catch (Exception e) {
			throw new ServicoException("Erro ao excluir o cliente!", e);
		}
		
	}
	
	/**
	 * Valida se as informações dependentes do tipo do cliente foram informadas 
	 * @param cliente
	 * @throws Exception
	 */
	private void validarTipoCliente(Cliente cliente) throws Exception {
		if (cliente.getTipoCliente() == TipoClienteEnum.Comum.getValor() && cliente.getAtualmenteEmpregado() == null) {
			throw new Exception("Informe se o cliente está atualmente empregado!");
		}else if (cliente.getTipoCliente() == TipoClienteEnum.Especial.getValor() && cliente.getAtualmenteEmpregado() == null) {
			throw new Exception("Informe o valor total do patrimonio do cliente!");
		}else if (cliente.getTipoCliente() == TipoClienteEnum.Potencial.getValor() && cliente.getAtualmenteEmpregado() == null) {
			throw new Exception("Informe o valor total de dividas atuais do cliente!");
		}
	}
}