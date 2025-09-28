package br.com.desafio.propostacredito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // Lombok: Cria um construtor com todos os campos (no caso, só o idProposta)
public class PropostaResponseDTO {

    private Long idProposta;

}