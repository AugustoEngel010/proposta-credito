package br.com.desafio.propostacredito;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ParcelaDTO {
    private Integer numeroParcela;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String status;
}