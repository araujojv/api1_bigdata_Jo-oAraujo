package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criaUsuario(Cliente cliente) throws Exception {
        // Validação do nome
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new Exception("O nome do cliente não pode ser vazio");
        }

        // Validações adicionais podem ser colocadas aqui

        // Salvando o cliente no banco de dados
        return clienteRepository.save(cliente);
    }

    public List<Cliente> getAllClients() {
        return clienteRepository.findAll();
    }

    public Cliente updateCliente(Long id, Cliente novosDados) throws Exception {
        // Atualiza o cliente com as novas informações
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        clienteExistente.setNome(novosDados.getNome());
        clienteExistente.setEmail(novosDados.getEmail());
        clienteExistente.setCpf(novosDados.getCpf());
        clienteExistente.setDataNascimento(novosDados.getDataNascimento());
        clienteExistente.setTelefone(novosDados.getTelefone());

        // Salva o cliente atualizado no banco de dados
        return clienteRepository.save(clienteExistente);
    }
}
