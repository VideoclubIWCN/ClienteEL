package com.iwcn.master.services.interfaces;

import java.util.List;

import com.iwcn.master.model.Pelicula;
import com.iwcn.master.model.PeliculaConcreta;

public interface Data {
	
	public List<Pelicula> callListaPeliculasOMDB (String optio);
	
	public PeliculaConcreta callPeliculaConcretaOMDB (String optio);
	
    public void callNuevo (PeliculaConcreta pi);
    
    public PeliculaConcreta[] callLista ();
    
    public PeliculaConcreta[] callBusquedaPeliculas (String optio) ;
	
	public PeliculaConcreta callPeliculaConcreta (String optio);
	
	public void callBorrar (String optio);
	
	public void callModificar (PeliculaConcreta pi);
	
}
