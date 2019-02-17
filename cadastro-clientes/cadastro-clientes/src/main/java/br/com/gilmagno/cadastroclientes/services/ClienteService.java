package br.com.gilmagno.cadastroclientes.services;

import java.util.List;

import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;

/**
 * Interface com ações especificas do objeto Cliente
 * @author Gilmagno
 *
 */
public interface ClienteService {

	/**
	 * Consulta um cliente por codigo
	 * @param codigo
	 * @return
	 * @throws ServicoException
	 */
	Cliente consultarPorCodigo(Long codigo) throws ServicoException;
	
	/**
	 * Listar todos os clientes
	 * @return
	 * @throws ServicoException
	 */
	List<Cliente> listarClientes() throws ServicoException;
	
	/**
	 * Salva os dados de um determinado cliente
	 * @param cliente
	 * @return
	 * @throws ServicoException
	 */
	Cliente salvar(Cliente cliente) throws ServicoException;
	
	/**
	 * Realiza a exclusão (exclusão logica) de um cliente
	 * @param codigo
	 * @throws ServicoException
	 */
	void excluir(Long codigo) throws ServicoException;
}
