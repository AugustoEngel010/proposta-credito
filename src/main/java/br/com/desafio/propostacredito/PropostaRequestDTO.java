package br.com.desafio.propostacredito;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PropostaRequestDTO {

    @NotBlank(message = "CPF não pode ser nulo ou vazio.")
    @CPF(message = "CPF inválido.") 
    private String cpf;


    @NotNull(message = "Valor solicitado não pode ser nulo.")
    @DecimalMin(value = "100.00", message = "O valor solicitado deve ser de no mínimo R$ 100,00.")
    private BigDecimal valorSolicitado;


    @NotNull(message = "Quantidade de parcelas não pode ser nula.")
    @Min(value = 1, message = "A quantidade de parcelas deve ser no mínimo 1.")
    @Max(value = 24, message = "A quantidade de parcelas deve ser no máximo 24.")
    private Integer quantidadeParcelas;


    @NotNull(message = "Data da solicitação não pode ser nula.")
    @PastOrPresent(message = "A data da solicitação não pode ser no futuro.")
    private LocalDate dataSolicitacao;
}