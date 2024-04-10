package com.algaWorks.awpagapi.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaWorks.awpagapi.entity.Cliente;
import com.algaWorks.awpagapi.entity.Parcelamento;
import com.algaWorks.awpagapi.exception.NegocioException;
import com.algaWorks.awpagapi.repository.ParcelamentoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ParcelamentoService {

	private final ParcelamentoRepository repository;
	private final CadastroClienteService clienteService;
	
	@Transactional
	public Parcelamento salvar(Parcelamento parcelamento) {
		if(parcelamento.getId() != null) {
			throw new NegocioException("parcelamento a ser criado não deve possuir um código");
		}
		
		Cliente cliente = clienteService.buscar(parcelamento.getCliente().getId());
		
		parcelamento.setCliente(cliente);
		parcelamento.setDataCriacao(OffsetDateTime.now());
		
		return repository.save(parcelamento);
	}
}
