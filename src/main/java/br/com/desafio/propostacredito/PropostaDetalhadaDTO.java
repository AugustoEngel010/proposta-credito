package br.com.desafio.propostacredito;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PropostaDetalhadaDTO {
    private Long id;
    private String cpf;
    private BigDecimal valorSolicitado;
    private int quantidadeParcelas;
    private LocalDate dataSolicitacao;
    private List<ParcelaDTO> parcelas;

    public static PropostaDetalhadaDTO fromEntity(Proposta proposta) {
        PropostaDetalhadaDTO dto = new PropostaDetalhadaDTO();
        dto.setId(proposta.getId());
        dto.setCpf(proposta.getCpf());
        dto.setValorSolicitado(proposta.getValorSolicitado());
        dto.setQuantidadeParcelas(proposta.getQuantidadeParcelas());
        dto.setDataSolicitacao(proposta.getDataSolicitacao());

        dto.setParcelas(proposta.getParcelas().stream().map(parcela -> {
            ParcelaDTO parcelaDTO = new ParcelaDTO();
            parcelaDTO.setNumeroParcela(parcela.getNumeroParcela());
            parcelaDTO.setValor(parcela.getValor());
            parcelaDTO.setDataVencimento(parcela.getDataVencimento());
            parcelaDTO.setStatus(parcela.getStatus());
            return parcelaDTO;
        }).collect(Collectors.toList()));

        return dto;
    }
}