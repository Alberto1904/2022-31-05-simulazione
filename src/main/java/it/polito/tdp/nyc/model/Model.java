package it.polito.tdp.nyc.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	NYCDao dao;
	Graph<String,DefaultWeightedEdge> grafo;
	Map<Integer,Hotspot> idMap;
	public Model()
	{
		this.dao=new NYCDao();
		idMap= new HashMap<>();
		this.dao.getAllHotspot(idMap);
		
	}
	public List<String> getProvider()
	{
		return this.dao.getProvider();
	}
	public void creaGrafo(String provider)
	{
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(provider));
		
		List<Adiacenza> coppie=this.dao.getArchi(provider);
		for(Adiacenza a: coppie)
		{
			Graphs.addEdgeWithVertices(this.grafo,a.getS1(),a.getS2(),a.getPeso());
		}
	}
	public List<String> getCitta()
	{
		List<String> citta= new LinkedList<>(this.grafo.vertexSet());
		return citta;
	}
	public int nVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int nArchi()
	{
		return this.grafo.edgeSet().size();
	}
	public List<Adiacente> getConnessi(String citta)
	{
		List<Adiacente> result= new LinkedList<>();
		for(String c: Graphs.neighborListOf(this.grafo,citta))
		{
			DefaultWeightedEdge edge= this.grafo.getEdge(c, citta);
			double peso=this.grafo.getEdgeWeight(edge);
			Adiacente a = new Adiacente(c,peso);
			result.add(a);
		}
		Comparator<Adiacente> com= new OrdinazioneConn();
		Collections.sort(result,com);
		return result;
	}
	public boolean getGrafo()
	{
		if(this.grafo==null) 
			return false;
		return true;
	}
	
	
}
