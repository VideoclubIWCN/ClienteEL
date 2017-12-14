package com.iwcn.master.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nombre;
	
	private String cont;
	
	private String cont2;
	
	private String correo;
	
	private String rol; 
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<GrantedAuthority> roles;
	
	public Usuario (String nombre, String contra, String rol, String correo) {
		this.nombre = nombre;
		this.cont = contra;
		this.cont2 = contra;
		this.rol = rol;
		this.correo = correo;
	}
	
	public Usuario (String nombre, String contra, List<GrantedAuthority> roles, String correo, String rol) {
		this.nombre = nombre;
		this.cont = new BCryptPasswordEncoder().encode(contra);
		this.roles = roles;
		this.correo = correo;
		this.rol = rol;
		this.cont2 = contra;
	}
	
	public String getPasswordHash() {
        return cont;
    }
	
	public void setPass (String password) {
		this.cont = new BCryptPasswordEncoder().encode(password);
	}
    
    public List<String> getRolesString() {
    	List<String> listRoles = new ArrayList<>();
    	for (GrantedAuthority r:roles) {
    		listRoles.add(r.getAuthority());
    	}
    	return listRoles;
    }
}