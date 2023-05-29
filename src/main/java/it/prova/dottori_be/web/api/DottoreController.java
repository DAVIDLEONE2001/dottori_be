package it.prova.dottori_be.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.dottori_be.dto.DottoreDTO;
import it.prova.dottori_be.dto.DottoreDTOSearch;
import it.prova.dottori_be.dto.ImpostaInVisitaDTO;
import it.prova.dottori_be.model.Dottore;
import it.prova.dottori_be.service.DottoreService;
import it.prova.dottori_be.web.exception.DottoreNotFoundException;
import it.prova.dottori_be.web.exception.IdNotNullForInsertException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dottore")
public class DottoreController {

	@Autowired
	private DottoreService dottoreService;

	@GetMapping
	public List<DottoreDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return DottoreDTO.createDottoreDTOShowListFromModelList(dottoreService.listAllDottori());
	}

	@GetMapping("/{id}")
	public DottoreDTO findById(@PathVariable(value = "id", required = true) long id) {
		Dottore dottore = dottoreService.caricaSingoloDottore(id);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + id);

		return DottoreDTO.buildDottoreDTOFromModel(dottore);
	}
	
	@GetMapping("get/{codiceDottore}")
	public DottoreDTO findCodicve(@PathVariable(value = "codiceDottore", required = true) String codiceDottore) {
		Dottore dottore = dottoreService.findByCodice(codiceDottore);
		
		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + codiceDottore);
		
		return DottoreDTO.buildDottoreDTOFromModel(dottore);
	}

	@GetMapping("/verifica/{codiceDottore}")
	public DottoreDTO verifica(@PathVariable(value = "codiceDottore", required = true) String codiceDottore) {
		Dottore dottore = dottoreService.findByCodice(codiceDottore);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con codice: " + codiceDottore);

		return DottoreDTO.buildDottoreDTOFromModel(dottore);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DottoreDTO createNew(@Valid @RequestBody DottoreDTO dottoreInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (dottoreInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		;
		Dottore dottoreAggiornato = dottoreService.inserisciNuovo(dottoreInput.buildDottoreModel());
		return DottoreDTO.buildDottoreDTOFromModel(dottoreService.caricaSingoloDottore(dottoreAggiornato.getId()));
	}
	
	@CrossOrigin("*")
	@PostMapping("/impostaInVisita")
	public DottoreDTO impostaInVisita(@Valid @RequestBody ImpostaInVisitaDTO input) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		Dottore dottore = dottoreService.findByCodice(input.getCodiceDottore());
		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con codice: " + input.getCodiceDottore());

		dottore.setInVisita(true);
		dottore.setCodFiscalePazienteAttualmenteInVisita(input.getCodiceFiscalePaziente());
		
		Dottore dottoreAggornato = dottoreService.aggiorna(dottore);

		return DottoreDTO.buildDottoreDTOFromModel(dottoreAggornato);
	}
	@PostMapping("/terminaVisita")
	public DottoreDTO terminaVisita(@Valid @RequestBody ImpostaInVisitaDTO input) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		Dottore dottore = dottoreService.findByCodice(input.getCodiceDottore());
		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con codice: " + input.getCodiceDottore());
		
		dottore.setInServizio(false);
		dottore.setCodFiscalePazienteAttualmenteInVisita(null);
		
		Dottore dottoreAggornato = dottoreService.aggiorna(dottore);
		
		return DottoreDTO.buildDottoreDTOFromModel(dottoreAggornato);
	}

	@PutMapping("aggiorna/{codiceDottore}")
	public DottoreDTO updateCodice(@Valid @RequestBody DottoreDTO dottoreInput, @PathVariable(required = true) String codiceDottore) {
		Dottore dottore = dottoreService.findByCodice(codiceDottore);

		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con codice: " + codiceDottore);

		dottoreInput.setId(dottore.getId());
		Dottore dottoreAggiornato = dottoreService.aggiorna(dottoreInput.buildDottoreModel());
		return DottoreDTO.buildDottoreDTOFromModel(dottoreAggiornato);
	}
	
	@PutMapping("/{id}")
	public DottoreDTO update(@Valid @RequestBody DottoreDTO dottoreInput, @PathVariable(required = true) Long id) {
		Dottore dottore = dottoreService.caricaSingoloDottore(id);
		
		if (dottore == null)
			throw new DottoreNotFoundException("Dottore not found con id: " + id);
		
		dottoreInput.setId(id);
		Dottore dottoreAggiornato = dottoreService.aggiorna(dottoreInput.buildDottoreModel());
		return DottoreDTO.buildDottoreDTOFromModel(dottoreAggiornato);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		dottoreService.rimuovi(id);
	}
	@DeleteMapping("/delete/{codiceDottore}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCodice(@PathVariable(required = true) String codiceDottore) {
		
		dottoreService.rimuovi(dottoreService.findByCodice(codiceDottore).getId());
	}

//
	@PostMapping("/search")
	public ResponseEntity<Page<DottoreDTO>> searchPaginated(@RequestBody DottoreDTOSearch example,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "0") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		Page<Dottore> entityPageResults = dottoreService.findByExample(example.buildDottoreModel(), pageNo, pageSize,
				sortBy);
		return new ResponseEntity<Page<DottoreDTO>>(DottoreDTO.fromModelPageToDTOPage(entityPageResults),
				HttpStatus.OK);
	}

}
