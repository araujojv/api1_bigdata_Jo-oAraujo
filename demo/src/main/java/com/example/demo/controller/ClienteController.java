package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.service.ClienteService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService service;

    // Logger para registrar os erros e eventos
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    // Busca todos os clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        logger.info("Recebendo requisição para listar todos os clientes.");
        List<Cliente> clientes = service.getAllClients();
        if (clientes.isEmpty()) {
            logger.info("Nenhum cliente encontrado.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Clientes listados com sucesso.");
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Cria um cliente
    @PostMapping("/clientes")
    public ResponseEntity<String> saveClient(@Valid @RequestBody Cliente cliente) {
        logger.info("Recebendo requisição para criar um novo cliente.");
        try {
            Cliente clienteValidado = service.criaUsuario(cliente);
            clienteRepository.save(clienteValidado);
            logger.info("Cliente criado com sucesso: {}", clienteValidado.getNome());
            return new ResponseEntity<>("Cliente criado com sucesso", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Dados inválidos para cliente: ", e);
            return new ResponseEntity<>("Erro: Dados inválidos - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Erro ao salvar cliente: ", e);
            return new ResponseEntity<>("Erro ao salvar cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Atualiza um cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable("id") Long id, @Valid @RequestBody Cliente novosDados) {
        logger.info("Recebendo requisição para atualizar cliente com ID: {}", id);
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (!clienteExistente.isPresent()) {
            logger.warn("Cliente com ID {} não encontrado.", id);
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            Cliente clienteAtualizado = service.updateCliente(id, novosDados);
            logger.info("Cliente atualizado com sucesso: {}", clienteAtualizado.getNome());
            return new ResponseEntity<>("Cliente atualizado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Erro ao atualizar cliente: ", e);
            return new ResponseEntity<>("Erro ao atualizar cliente: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Remove um cliente
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable("id") Long id) {
        logger.info("Recebendo requisição para remover cliente com ID: {}", id);
        Optional<Cliente> optCliente = clienteRepository.findById(id);

        if (!optCliente.isPresent()) {
            logger.warn("Cliente com ID {} não encontrado.", id);
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            clienteRepository.delete(optCliente.get());
            logger.info("Cliente com ID {} removido com sucesso.", id);
            return new ResponseEntity<>("Cliente removido com sucesso", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Erro ao remover cliente: ", e);
            return new ResponseEntity<>("Erro ao remover cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
