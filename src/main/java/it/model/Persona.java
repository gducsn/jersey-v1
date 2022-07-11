package it.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cognome;
	private long età;

	public Persona() {
	};

	public Persona(String nome, String cognome, int età) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.età = età;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String congnome) {
		this.cognome = congnome;
	}

	@XmlElement
	public long getEtà() {
		return età;
	}

	public void setEtà(int età) {
		this.età = età;
	}

}
