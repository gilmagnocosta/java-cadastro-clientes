package br.com.gilmagno.cadastroclientes.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gilmagno.cadastroclientes.dto.DadosSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.dto.ResultadoSimulacaoDTO;
import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.services.ClienteService;
import br.com.gilmagno.cadastroclientes.services.EmprestimoService;

@CrossOrigin
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	private final EmprestimoService emprestimoService;
	
	@Autowired
	public ClienteController(ClienteService clienteService, EmprestimoService emprestimoService) {
		this.clienteService = clienteService;
		this.emprestimoService = emprestimoService;
	}
	
	/**
	 * Action para consultar todos os clientes
	 * @return
	 * @throws ServicoException
	 */
	@GetMapping
	public ResponseEntity<List<Cliente>> listarTodosClientes() throws ServicoException{
		return ResponseEntity.ok()
				//.header("Access-Control-Allow-Origin", "*")
			    .body(this.clienteService.listarClientes());
	}

	/**
	 * Action para consultar um cliente pelo seu codigo
	 * @param id
	 * @return
	 * @throws ServicoException
	 */
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Cliente> consultarPorCodigo(@PathVariable long id) throws ServicoException{
		Cliente cliente = clienteService.consultarPorCodigo(id);
		
		if (cliente != null) {
			return ResponseEntity.ok()
					//.header("Access-Control-Allow-Origin", "*")
					//.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				    //.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
					.body(cliente);	
		}else {
			return ResponseEntity.notFound()
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}

	/**
	 * Action para inserção de novo cliente
	 * @param cliente
	 * @return
	 * @throws ServicoException
	 */
	@PostMapping
	public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) throws ServicoException{
		if (cliente != null) {
			
			Cliente clienteAtualizado = clienteService.salvar(cliente);	
			
			URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteAtualizado.getCodigo()).toUri();
			
			return ResponseEntity.created(url)
					.header("Access-Control-Allow-Origin", "*")
					.build();
			
		}else {
			return ResponseEntity.badRequest()
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * Action para atualizacao do cliente determinado nos parametros
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable("id") long id, @RequestBody Cliente cliente) throws ServicoException{
		
		Cliente clienteAlterado = clienteService.consultarPorCodigo(id);
		
		if (clienteAlterado != null) {
			clienteAlterado.setAtualmenteEmpregado(cliente.getAtualmenteEmpregado());
			clienteAlterado.setEndereco(cliente.getEndereco());
			clienteAlterado.setNome(cliente.getNome());
			clienteAlterado.setRendimentoMensal(cliente.getRendimentoMensal());
			clienteAlterado.setRisco(cliente.getRisco());
			clienteAlterado.setTipoCliente(cliente.getTipoCliente());
			clienteAlterado.setValorTotalDividas(cliente.getValorTotalDividas());
			clienteAlterado.setValorTotalPatrimonio(cliente.getValorTotalPatrimonio());
			
			return ResponseEntity.ok()
					//.header("Access-Control-Allow-Origin", "*")
					.body(clienteService.salvar(clienteAlterado));
		}else {
			return ResponseEntity.badRequest()
					//.header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * Action para deleção do cliente
	 * @param id
	 * @return
	 * @throws ServicoException
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") long id) throws ServicoException{
		Cliente clienteParaDeletar = clienteService.consultarPorCodigo(id);
		
		if (clienteParaDeletar != null) {
			clienteService.excluir(id);
			
			return ResponseEntity.ok()
					//.header("Access-Control-Allow-Origin", "*")
					.build();
		}else {
			return ResponseEntity.badRequest()
					//.header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	/**
	 * Action para realizar simulação de emprestimo.
	 * Deve ser passado como parametro um objeto DadosSimulacaoDTO contendo o codigo do cliente para obtencao da taxa de juros aplicada para ele,
	 * bem como os dados de simulacao (valor solicitado e meses de duração) 
	 * @param dadosSimulacao
	 * @return
	 * @throws ServicoException
	 */
	@PostMapping(path = {"/simular-emprestimo/{id}"})
	public ResponseEntity<ResultadoSimulacaoDTO> simularEmprestimo(@RequestBody DadosSimulacaoDTO dadosSimulacao) throws ServicoException{
		ResultadoSimulacaoDTO resultado = emprestimoService.simular(dadosSimulacao);
		return ResponseEntity.ok().body(resultado);	
		
	}
}
