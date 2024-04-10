package com.algaWorks.awpagapi.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelamentoModel {

	private Long id;
	private String nomeCliente;
	private String descricao;
	private BigDecimal valorTotal;
	private Integer parcelas;
	private OffsetDateTime dataCriacao;
}
