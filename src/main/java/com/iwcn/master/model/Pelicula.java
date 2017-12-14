package com.iwcn.master.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
	
	private String imdbID;
	
	private String Title;
	
	private String Year;
	
	private String Poster;
	
}
