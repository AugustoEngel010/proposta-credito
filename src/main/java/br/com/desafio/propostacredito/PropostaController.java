package br.com.desafio.propostacredito;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    @Autowired
    public PropostaController(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criarProposta(@Valid @RequestBody PropostaRequestDTO requestDTO) {

        Long propostaId = propostaService.criarProposta(requestDTO);

        PropostaResponseDTO response = new PropostaResponseDTO(propostaId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDetalhadaDTO> buscarPropostaPorId(@PathVariable Long id) {

        PropostaDetalhadaDTO propostaDTO = propostaService.buscarPorId(id);
        return ResponseEntity.ok(propostaDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PropostaDetalhadaDTO>> listarPropostas(Pageable pageable) {

        Page<PropostaDetalhadaDTO> propostas = propostaService.listarTodas(pageable);
        return ResponseEntity.ok(propostas);
    }

    @PostMapping("/{idProposta}/parcelas/{numeroParcela}/pagar")
    public ResponseEntity<ParcelaDTO> pagarParcela(
            @PathVariable Long idProposta,
            @PathVariable Integer numeroParcela) {

        ParcelaDTO parcelaPagaDTO = propostaService.pagarParcela(idProposta, numeroParcela);

        return ResponseEntity.ok(parcelaPagaDTO);
    }
}
