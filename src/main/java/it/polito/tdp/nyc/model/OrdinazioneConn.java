package it.polito.tdp.nyc.model;

import java.util.Comparator;

public class OrdinazioneConn implements Comparator<Adiacente>{
	public int compare(Adiacente a1,Adiacente a2)
	{
		if(a1.getPeso()>a2.getPeso())
			return 1;
		else
			return -1;
	}

}
