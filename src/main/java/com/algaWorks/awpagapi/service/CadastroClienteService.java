package com.algaWorks.awpagapi.service;

import org.springframework.stereotype.Service;

import com.algaWorks.awpagapi.entity.Cliente;
import com.algaWorks.awpagapi.exception.NegocioException;
import com.algaWorks.awpagapi.repository.ClienteRepositorio;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroClienteService {

	private final ClienteRepositorio repository;
	
	public Cliente buscar(Long clienteId) {
        return repository.findById(clienteId)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
    }
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		
		boolean emailEmUso = repository.findByEmail(cliente.getEmail()).filter(c -> !c.equals(cliente)).isPresent();
		
		if(emailEmUso) {
		  throw new NegocioException("Email já existente");
		}
		
		return repository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long id) {
		repository.deleteById(id);
	}
}
