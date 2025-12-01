package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "clientes_granja")
@Data
public class ClienteGranja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente/empresa é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "O nome deve conter apenas letras e espaços válidos.")
    private String nome;

    @NotBlank(message = "O CPF ou CNPJ é obrigatório.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$",
            message = "O CPF deve ter 11 dígitos ou o CNPJ deve ter 14 dígitos (apenas números).")
    private String cpf;

    @NotBlank(message = "O tipo de produto é obrigatório.")
    private String tipoProduto;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade mínima é 1.")
    private Integer quantidadeOvos; // O Back-end espera um Integer

    // Campos de Endereço
    @NotBlank(message = "O logradouro é obrigatório.")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "O logradouro deve conter apenas letras e espaços válidos.")
    private String logradouro;

    @NotBlank(message = "O número é obrigatório.")
    @Pattern(regexp = "^[0-9]+$", message = "O número deve conter apenas dígitos numéricos.")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório.")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "O bairro deve conter apenas letras e espaços válidos.")
    private String bairro;

    private String anotacoes;
}