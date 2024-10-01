package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo rua é obrigatório")
    @Size(min = 3, max = 255, message = "Campo rua deve ter no minimo 3 caracteres e no maximo 255")
    @Column
    private String rua;

    @NotBlank(message = "Campo número é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Formato inválido para o número")
    private String numero;

    @NotBlank(message = "Campo bairro é obrigatório")
    @Size(min = 3, max = 100, message = "Campo bairro deve ter no minimo 3 caracteres e no maximo 100")
    @Column
    private String bairro;

    @NotBlank(message = "Campo cidade é obrigatório")
    @Size(min = 3, max = 100, message = "Campo cidade deve ter no minimo 3 caracteres e no maximo 100")
    @Column
    private String cidade;

    @NotBlank(message = "Campo estado é obrigatório")
    @Column
    private String estado;

    @NotNull(message = "Campo CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "O CEP deve ter 8 dígitos, sem ponto ou traço")
    @Column
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
