package br.com.desafio.propostacredito;

import jakarta.persistence.*; 
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity 
@Table(name = "parcelas") 
@Getter 
@Setter 
public class Parcela {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    
    @ManyToOne 
    @JoinColumn(name = "proposta_id", nullable = false) 
    private Proposta proposta;

    private Integer numeroParcela;

    private BigDecimal valor;

    private LocalDate dataVencimento;

    private String status;
}