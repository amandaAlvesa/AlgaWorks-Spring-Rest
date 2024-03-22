package com.algaWorks.awpagapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaWorks.awpagapi.entity.Parcelamento;

@Repository
public interface ParcelamentoRepository extends JpaRepository<Parcelamento, Long> {

}
