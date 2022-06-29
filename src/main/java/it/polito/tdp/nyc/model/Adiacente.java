package it.polito.tdp.nyc.model;

public class Adiacente {
String citta;
double peso;
public String getCitta() {
	return citta;
}
public void setCitta(String citta) {
	this.citta = citta;
}
public double getPeso() {
	return peso;
}
public void setPeso(double peso) {
	this.peso = peso;
}
public Adiacente(String citta, double peso) {
	super();
	this.citta = citta;
	this.peso = peso;
}
@Override
public String toString() {
	return "Adiacente [citta=" + citta + ", peso=" + peso + "]";
}

}
