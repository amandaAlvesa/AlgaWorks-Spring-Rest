package com.algaWorks.awpagapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaWorks.awpagapi.entity.Parcelamento;
import com.algaWorks.awpagapi.exception.NegocioException;
import com.algaWorks.awpagapi.model.ParcelamentoModel;
import com.algaWorks.awpagapi.repository.ParcelamentoRepository;
import com.algaWorks.awpagapi.service.ParcelamentoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {

	private final ParcelamentoRepository repository;
	private final ParcelamentoService service;
	
	@GetMapping
	public List<Parcelamento> listar(){
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ParcelamentoModel> buscar(@PathVariable Long id) {

		return repository.findById(id).map(parcelamento -> {
			var parcelamentoModel = new ParcelamentoModel();
			parcelamentoModel.setId(parcelamento.getId());
			parcelamentoModel.setNomeCliente(parcelamento.getCliente().getNome());
			parcelamentoModel.setDescricao(parcelamento.getDescricao());
			parcelamentoModel.setParcelas(parcelamento.getQuantidadeParcelados());
			parcelamentoModel.setValorTotal(parcelamento.getValorTotal());
			parcelamentoModel.setDataCriacao(parcelamento.getDataCriacao());
			
			return ResponseEntity.ok(parcelamentoModel);
		}).orElse(ResponseEntity.notFound().build());
		
//		Optional<Parcelamento> parcelamento = repository.findById(id);
//		
//		if(parcelamento.isEmpty()) {
//			return ResponseEntity.badRequest().build();
//		}
//		
//		return ResponseEntity.ok(parcelamento.get());
	}
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public Parcelamento criar(@Valid @RequestBody Parcelamento parcelamento) {
		return service.salvar(parcelamento);
	}
}
