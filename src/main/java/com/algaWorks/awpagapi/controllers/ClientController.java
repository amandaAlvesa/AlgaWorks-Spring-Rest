package com.algaWorks.awpagapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaWorks.awpagapi.entity.Cliente;
import com.algaWorks.awpagapi.exception.NegocioException;
import com.algaWorks.awpagapi.repository.ClienteRepositorio;
import com.algaWorks.awpagapi.service.CadastroClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/clientes")
@AllArgsConstructor
@RestController
public class ClientController {

	private final CadastroClienteService service;
	private final ClienteRepositorio repository;
	
	@GetMapping()
	public List<Cliente> clientes(){
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarId(@PathVariable Long id) {
		Optional<Cliente> cliente =repository.findById(id);	

		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cliente criarCliente(@Valid @RequestBody Cliente clienteNovo) {
		return service.salvar(clienteNovo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> modificar(@PathVariable Long id, @Valid @RequestBody Cliente mudancaCliente) {
		if(!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		mudancaCliente.setId(id);
		mudancaCliente  = service.salvar(mudancaCliente);
		return ResponseEntity.ok(mudancaCliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long id){
		if(!repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Cliente> cliente =repository.findById(id);	

		service.excluir(id);
		
		return ResponseEntity.ok().body(cliente.get());
	}
	
}
