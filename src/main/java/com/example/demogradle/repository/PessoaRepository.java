package com.example.demogradle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demogradle.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
