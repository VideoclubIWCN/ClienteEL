package com.iwcn.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaConcreta {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	private String imdbID;
	
	private String Title;
	
	private String Plot;
	
	private String Released;
	
	private String Director;
	
	private String Actors;
	
	private String Poster;
	
	private String imdbRating;	
	
	private String Runtime;
	
	private String Video;
	
}
