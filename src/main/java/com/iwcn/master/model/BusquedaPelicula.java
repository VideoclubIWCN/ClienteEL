package com.iwcn.master.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusquedaPelicula {
	
	private Pelicula[] search;
	
	private int totalResults;
	
	private String response;
	
}
