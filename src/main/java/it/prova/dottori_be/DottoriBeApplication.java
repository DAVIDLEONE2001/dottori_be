package it.prova.dottori_be;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.dottori_be.model.Dottore;
import it.prova.dottori_be.service.DottoreService;

@SpringBootApplication
public class DottoriBeApplication implements CommandLineRunner {

	@Autowired
	DottoreService dottoreService;

	public static void main(String[] args) {
		SpringApplication.run(DottoriBeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Dottore dottore1 = Dottore.builder().nome("Marco").cognome("Guido").codiceDottore("MRCGD").inServizio(true)
				.build();
		dottoreService.inserisciNuovo(dottore1);
		Dottore dottore2 = Dottore.builder().nome("Mimmo").cognome("Guido").codiceDottore("MRCGgD")
				.build();
		dottoreService.inserisciNuovo(dottore2);
		Dottore dottore3 = Dottore.builder().nome("Pietro").cognome("Guido").codiceDottore("MRCggGD")
				.build();
		dottoreService.inserisciNuovo(dottore3);

	}

}
