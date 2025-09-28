package br.com.desafio.propostacredito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final ParcelaRepository parcelaRepository;

    @Autowired
    public PropostaService(PropostaRepository propostaRepository, ParcelaRepository parcelaRepository) {
        this.propostaRepository = propostaRepository;
        this.parcelaRepository = parcelaRepository;
    }

    @Transactional 
    public Long criarProposta(PropostaRequestDTO dto) {

        Proposta proposta = new Proposta();
        proposta.setCpf(dto.getCpf());
        proposta.setValorSolicitado(dto.getValorSolicitado());
        proposta.setQuantidadeParcelas(dto.getQuantidadeParcelas());
        proposta.setDataSolicitacao(dto.getDataSolicitacao());

        List<Parcela> parcelasGeradas = gerarParcelas(proposta);
        proposta.setParcelas(parcelasGeradas);

        Proposta propostaSalva = propostaRepository.save(proposta);

        return propostaSalva.getId();
    }

    private List<Parcela> gerarParcelas(Proposta proposta) {
        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal valorParcela = proposta.getValorSolicitado()
                .divide(BigDecimal.valueOf(proposta.getQuantidadeParcelas()), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= proposta.getQuantidadeParcelas(); i++) {
            Parcela p = new Parcela();
            p.setProposta(proposta);
            p.setNumeroParcela(i);
            p.setValor(valorParcela);
            p.setDataVencimento(proposta.getDataSolicitacao().plusMonths(i));
            p.setStatus("Em Aberto");
            parcelas.add(p);
        }
        return parcelas;
    }

    public PropostaDetalhadaDTO buscarPorId(Long id) {

        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada com o ID: " + id));

        return PropostaDetalhadaDTO.fromEntity(proposta);
    }

    public Page<PropostaDetalhadaDTO> listarTodas(Pageable pageable) {

        Page<Proposta> propostasPaginadas = propostaRepository.findAll(pageable);

        return propostasPaginadas.map(PropostaDetalhadaDTO::fromEntity);
    }

    @Transactional 
    public ParcelaDTO pagarParcela(Long idProposta, Integer numeroParcela) {

        Proposta proposta = propostaRepository.findById(idProposta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada com o ID: " + idProposta));

        Optional<Parcela> parcelaOpt = proposta.getParcelas().stream()
                .filter(p -> p.getNumeroParcela().equals(numeroParcela))
                .findFirst();

        if (parcelaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcela número " + numeroParcela + " não encontrada para esta proposta.");
        }

        Parcela parcela = parcelaOpt.get();

        if ("Paga".equals(parcela.getStatus())) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta parcela já foi paga.");
        }

        parcela.setStatus("Paga");

        ParcelaDTO parcelaDTO = new ParcelaDTO();
        parcelaDTO.setNumeroParcela(parcela.getNumeroParcela());
        parcelaDTO.setValor(parcela.getValor());
        parcelaDTO.setDataVencimento(parcela.getDataVencimento());
        parcelaDTO.setStatus(parcela.getStatus());

        return parcelaDTO;
    }
}
