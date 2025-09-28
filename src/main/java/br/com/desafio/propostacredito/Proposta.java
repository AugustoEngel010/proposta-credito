package br.com.desafio.propostacredito;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propostas")
@Getter
@Setter
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;
    private BigDecimal valorSolicitado;
    private int quantidadeParcelas;
    private LocalDate dataSolicitacao;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Parcela> parcelas = new ArrayList<>();
}