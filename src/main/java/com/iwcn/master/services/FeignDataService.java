package com.iwcn.master.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iwcn.master.model.Pelicula;
import com.iwcn.master.model.PeliculaConcreta;
import com.iwcn.master.services.interfaces.Data;
import com.iwcn.master.services.interfaces.IPeliculaFeign;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Service
public class FeignDataService implements Data{
	
	@Override
	public List<Pelicula> callListaPeliculasOMDB (String optio) {
		if (optio.length() > 0) {
			IPeliculaFeign pelis = Feign.builder()
					  .client(new OkHttpClient())
					  .encoder(new JacksonEncoder())
					  .decoder(new JacksonDecoder())
					  .target(IPeliculaFeign.class, "http://localhost:8081/peliculas/" + optio);
			return pelis.getListaPeliculasOMDB();
		}
		
		return null;
	}
	
	@Override
	public PeliculaConcreta callPeliculaConcretaOMDB (String optio) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8081/pelicula/" + optio);
		
		return peli.getPeliculaOMDB();
	}
	
	@Override
	public PeliculaConcreta[] callLista () {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/list");
		
		return peli.getListaPeliculas();
	}
	
	@Override
	public PeliculaConcreta[] callBusquedaPeliculas (String optio) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/busqueda3/?optio=" + optio);
		
		return peli.getListaPeliculas();
	}
//	
	@Override
	public void callNuevo (PeliculaConcreta pi) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/fin");
		
		peli.addPelicula(pi);
	}
	
	@Override
	public PeliculaConcreta callPeliculaConcreta (String optio) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/peliculaBD/" + optio);
		
		return peli.getPelicula();
	}
	
	@Override
	public void callBorrar (String optio) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/peliculaBD/borrar/" + optio);
		
		peli.deletePelicula();
	}
	
	@Override
	public void callModificar (PeliculaConcreta pi) {
		IPeliculaFeign peli = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new JacksonEncoder())
				  .decoder(new JacksonDecoder())
				  .target(IPeliculaFeign.class, "http://localhost:8080/principal/peliculaBD/modificar");

		peli.modPelicula(pi);
	}	
	
}