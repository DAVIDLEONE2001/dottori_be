package it.prova.dottori_be.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpostaInVisitaDTO {

	private String codiceDottore;
	private String codiceFiscalePaziente;

}
