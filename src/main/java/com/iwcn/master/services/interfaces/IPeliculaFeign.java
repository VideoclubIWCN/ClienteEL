package com.iwcn.master.services.interfaces;


import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.iwcn.master.model.Pelicula;
import com.iwcn.master.model.PeliculaConcreta;

import feign.Headers;
import feign.RequestLine;

public interface IPeliculaFeign {
	
	@RequestLine("GET")
	List<Pelicula> getListaPeliculasOMDB();
	
	@RequestLine("GET")
	PeliculaConcreta getPeliculaOMDB();
	
	@RequestLine("POST")
    @Headers("Content-Type: application/json")
    void addPelicula(@RequestBody PeliculaConcreta pi);
	
	@RequestLine("GET")
	PeliculaConcreta[] getListaPeliculas();
	
	@RequestLine("GET")
	Pelicula[] getListaPeliculasBusqueda();
	
	@RequestLine("GET")
	PeliculaConcreta getPelicula();
	
	@RequestLine("DELETE")
    void deletePelicula();
	
	@RequestLine("POST")
	@Headers("Content-Type: application/json")
    void modPelicula(@RequestBody PeliculaConcreta pi);
}
