package it.prova.dottori_be.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
public class DottoreDTO {

	private Long id;
	@NotBlank(message = "{nome.notblank}")
	private String nome;
	@NotBlank(message = "{cognome.notblank}")
	private String cognome;
	@NotBlank(message = "{codice.notblank}")
	private String codiceDottore;
	private String codFiscalePazienteAttualmenteInVisita;
	private Boolean inVisita;
	private Boolean inServizio;

	public static DottoreDTO buildDottoreDTOFromModel(Dottore dottoreModel) {

		return DottoreDTO.builder().nome(dottoreModel.getNome()).cognome(dottoreModel.getCognome())
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
	
	public static List<DottoreDTO> createDottoreDTOShowListFromModelList(List<Dottore> modelListInput
			) {
		return modelListInput.stream().map(registaEntity -> {
			DottoreDTO result = DottoreDTO.buildDottoreDTOFromModel(registaEntity);
			return result;
		}).collect(Collectors.toList());
	}
	
	public static Page<DottoreDTO> fromModelPageToDTOPage(Page<Dottore> input) {
		if (input.getPageable().isPaged()) {
			return new PageImpl<DottoreDTO>(createDottoreDTOShowListFromModelList(input.getContent()),
					PageRequest.of(input.getPageable().getPageNumber(), input.getPageable().getPageSize(),
							input.getPageable().getSort()),
					input.getTotalElements());
		}else
			return new PageImpl<DottoreDTO>(createDottoreDTOShowListFromModelList(input.getContent()),
					PageRequest.of(0, 1,
							input.getPageable().getSort()),
					input.getTotalElements());
	}
}
