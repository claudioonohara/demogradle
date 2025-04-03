package com.example.demogradle.service;

import org.springframework.stereotype.Service;
import com.example.demogradle.entity.Pessoa;
import com.example.demogradle.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {
    
    private final PessoaRepository pessoaRepository;
    
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
    
    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pessoa not found"));
    }
}
