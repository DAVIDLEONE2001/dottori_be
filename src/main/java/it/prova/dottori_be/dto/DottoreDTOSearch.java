package it.prova.dottori_be.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.dottori_be.model.Dottore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DottoreDTOSearch {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceDottore;
	private String codFiscalePazienteAttualmenteInVisita;
	private Boolean inVisita;
	private Boolean inServizio;

	public static DottoreDTOSearch buildDottoreDTOFromModel(Dottore dottoreModel) {

		return DottoreDTOSearch.builder().nome(dottoreModel.getNome()).cognome(dottoreModel.getCognome())
				.codiceDottore(dottoreModel.getCodiceDottore())
				.codFiscalePazienteAttualmenteInVisita(dottoreModel.getCodFiscalePazienteAttualmenteInVisita())
				.inVisita(dottoreModel.getInVisita()).inServizio(dottoreModel.getInServizio()).id(dottoreModel.getId()) .build();
	}
	public Dottore buildDottoreModel() {
	
		
		return Dottore.builder().nome(this.getNome()).cognome(this.getCognome())
				.codiceDottore(this.getCodiceDottore())
				.codFiscalePazienteAttualmenteInVisita(this.getCodFiscalePazienteAttualmenteInVisita())
				.inVisita(this.getInVisita()).inServizio(this.getInServizio()).id(this.getId()) .build();
	}
	
	public static List<DottoreDTOSearch> createDottoreDTOShowListFromModelList(List<Dottore> modelListInput
			) {
		return modelListInput.stream().map(registaEntity -> {
			DottoreDTOSearch result = DottoreDTOSearch.buildDottoreDTOFromModel(registaEntity);
			return result;
		}).collect(Collectors.toList());
	}
}
