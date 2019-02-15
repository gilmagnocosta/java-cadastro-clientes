package br.com.gilmagno.cadastroclientes.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gilmagno.cadastroclientes.entities.Cliente;
import br.com.gilmagno.cadastroclientes.exceptions.ServicoException;
import br.com.gilmagno.cadastroclientes.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarTodosClientesAtivos() throws ServicoException{
		return ResponseEntity.ok().body(this.clienteService.listarClientesAtivos());
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Cliente> consultarPorCodigo(@PathVariable long id) throws ServicoException{
		Cliente cliente = clienteService.consultarPorCodigo(id);
		
		if (cliente != null) {
			return ResponseEntity.ok(cliente);	
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) throws ServicoException{
		if (cliente != null) {
			
			Cliente clienteAtualizado = clienteService.salvar(cliente);	
			
			URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteAtualizado.getCodigo()).toUri();
			
			return ResponseEntity.created(url).build();
			
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
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
			
			return ResponseEntity.ok(clienteService.salvar(clienteAlterado));
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") long id) throws ServicoException{
		Cliente clienteParaDeletar = clienteService.consultarPorCodigo(id);
		
		if (clienteParaDeletar != null) {
			clienteService.excluir(id);
			
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
}
