package com.example.demogradle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demogradle.entity.FotoPessoa;
import com.example.demogradle.service.FotoService;
import com.example.demogradle.service.PessoaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoas/{pessoaId}/fotos")
@RequiredArgsConstructor
public class FotoPessoaController {

    private final FotoService fotoService;
    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<FotoPessoa> uploadFoto(
            @PathVariable Long pessoaId,
            @RequestParam("file") MultipartFile file) throws Exception {
        
        var pessoa = pessoaService.findById(pessoaId);
        var foto = fotoService.uploadFoto(file, pessoa);
        return ResponseEntity.ok(foto);
    }
}
