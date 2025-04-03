package com.example.demogradle.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.demogradle.entity.FotoPessoa;
import com.example.demogradle.entity.Pessoa;
import com.example.demogradle.repository.FotoPessoaRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final MinioClient minioClient;
    private final FotoPessoaRepository fotoPessoaRepository;
    
    @Value("${minio.bucket}")
    private String bucket;
    
    @Value("${minio.endpoint}")
    private String endpoint;

    public String generateTemporaryUrl(String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(objectName)
                .expiry(5, TimeUnit.MINUTES)
                .build()
        );
    }

    @Transactional
    public FotoPessoa uploadFoto(MultipartFile file, Pessoa pessoa) throws Exception {
        String objectName = UUID.randomUUID().toString();
        
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
        );

        FotoPessoa foto = new FotoPessoa();
        foto.setPessoa(pessoa);
        foto.setFileName(file.getOriginalFilename());
        foto.setContentType(file.getContentType());
        foto.setMinioObjectName(objectName);
        foto.setFileSize(file.getSize());
        foto.setUrl(endpoint + "/" + bucket + "/" + objectName);
        foto.setTemporaryUrl(generateTemporaryUrl(objectName));
        
        return fotoPessoaRepository.save(foto);
    }
}
