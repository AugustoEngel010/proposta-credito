package br.com.desafio.propostacredito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest 
@ActiveProfiles("test")
class PropostaServiceIntegrationTest {

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private PropostaRepository propostaRepository;

    @Test 
    @DisplayName("Deve criar proposta e suas parcelas com sucesso no banco de dados")
    void deveCriarPropostaEParcelasComSucesso() {

        PropostaRequestDTO dto = new PropostaRequestDTO();
        dto.setCpf("01234567890"); 
        dto.setValorSolicitado(new BigDecimal("1200.00"));
        dto.setQuantidadeParcelas(12);
        dto.setDataSolicitacao(LocalDate.now());

        Long propostaId = propostaService.criarProposta(dto);

        assertNotNull(propostaId); 

        Optional<Proposta> propostaSalvaOpt = propostaRepository.findById(propostaId);

        assertTrue(propostaSalvaOpt.isPresent());

        Proposta propostaSalva = propostaSalvaOpt.get();

        assertEquals("01234567890", propostaSalva.getCpf());
        assertEquals(0, new BigDecimal("1200.00").compareTo(propostaSalva.getValorSolicitado()));

        assertNotNull(propostaSalva.getParcelas());
        assertEquals(12, propostaSalva.getParcelas().size()); 

        Parcela primeiraParcela = propostaSalva.getParcelas().get(0);
        assertEquals("Em Aberto", primeiraParcela.getStatus());
        assertEquals(1, primeiraParcela.getNumeroParcela());
    }
}