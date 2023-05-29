package it.prova.dottori_be.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dottore")
public class Dottore {

	/*(nome, cognome, codiceDottore, codFiscalePazienteAttualmenteInVisita, inVisita,
inServizio)*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "codicedottore")
	private String codiceDottore;
	@Column(name = "codfiscalepazienteattualmenteinvisita")
	private String codFiscalePazienteAttualmenteInVisita;
	@Column(name = "invisita")
	@Builder.Default
	private Boolean inVisita=false;
	@Column(name = "inservizio")
	@Builder.Default
	private Boolean inServizio=false;
	
	
	
	

}
