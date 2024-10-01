package com.example.demo.controller;

import com.example.demo.model.Endereco;
import com.example.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Busca todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> getAllEnderecos() {
        return new ResponseEntity<>(enderecoRepository.findAll(), HttpStatus.OK);
    }

    // Busca um endereço pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Long id) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enderecoOpt.get(), HttpStatus.OK);
    }

    // Cria um novo endereço
    @PostMapping
    public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) {
        Endereco novoEndereco = enderecoRepository.save(endereco);
        return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
    }

    // Atualiza um endereço existente
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Long id, @RequestBody Endereco novosDadosEndereco) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Endereco enderecoExistente = enderecoOpt.get();
        enderecoExistente.setRua(novosDadosEndereco.getRua());
        enderecoExistente.setNumero(novosDadosEndereco.getNumero());
        enderecoExistente.setBairro(novosDadosEndereco.getBairro());
        enderecoExistente.setCidade(novosDadosEndereco.getCidade());
        enderecoExistente.setEstado(novosDadosEndereco.getEstado());
        enderecoExistente.setCep(novosDadosEndereco.getCep());

        enderecoRepository.save(enderecoExistente);
        return new ResponseEntity<>(enderecoExistente, HttpStatus.OK);
    }

    // Remove um endereço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        enderecoRepository.delete(enderecoOpt.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
