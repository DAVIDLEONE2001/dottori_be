package it.prova.dottori_be.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.dottori_be.model.Dottore;

public interface DottoreService {

	public List<Dottore> listAllDottori();

	public Dottore caricaSingoloDottore(Long id);

	public Dottore aggiorna(Dottore ottoreInstance);

	public Dottore inserisciNuovo(Dottore dottoreInstance);
	
	public Dottore findByCodice(String codiceDottore);

	public void rimuovi(Long idToRemove);
	
	Page<Dottore> findByExample(Dottore example, Integer pageNo, Integer pageSize, String sortBy);


	
}
