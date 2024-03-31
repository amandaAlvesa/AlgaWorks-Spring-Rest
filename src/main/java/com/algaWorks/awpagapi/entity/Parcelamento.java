package com.algaWorks.awpagapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.algaWorks.awpagapi.validation.ValidationGroups;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of="id")
@Entity
public class Parcelamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotBlank
	@Size(max = 20)
	private String descricao;
	
	@NotNull
	@Positive
	private BigDecimal valorTotal;
	
	@NotNull
	@Positive
	@Max(12)
	private Integer quantidadeParcelados;
	
	private LocalDateTime dataCriacao;
}