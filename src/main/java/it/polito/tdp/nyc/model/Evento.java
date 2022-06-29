package it.polito.tdp.nyc.model;

public class Evento implements Comparable<Evento>{
	
	
	enum tipoEvento{
		REVISIONE,
		CAMBIO_QUARTIERE
	}
	tipoEvento tipo;
	int tempo;
	String quartiere;
	String hotspot;
	int tecnico;
	public Evento(tipoEvento tipo, int tempo, String quartiere, String hotspot, int tecnico) {
		super();
		this.tipo = tipo;
		this.tempo = tempo;
		this.quartiere = quartiere;
		this.hotspot = hotspot;
		this.tecnico = tecnico;
	}
	public int getTecnico() {
		return tecnico;
	}
	public void setTecnico(int tecnico) {
		this.tecnico = tecnico;
	}
	public tipoEvento getTipo() {
		return tipo;
	}
	public void setTipo(tipoEvento tipo) {
		this.tipo = tipo;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public String getQuartiere() {
		return quartiere;
	}
	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}
	public String getHotspot() {
		return hotspot;
	}
	public void setHotspot(String hotspot) {
		this.hotspot = hotspot;
	}

	public int  compareTo(Evento other)
	{
		return this.tempo-other.tempo;
	}
	

}
