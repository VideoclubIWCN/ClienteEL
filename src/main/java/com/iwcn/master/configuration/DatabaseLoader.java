package com.iwcn.master.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.iwcn.master.model.Usuario;
import com.iwcn.master.services.interfaces.UsuarioRepository;

public class DatabaseLoader {
	
	@Autowired
	private UsuarioRepository userRepository;
	
//	@PostConstruct
//	private void initDatabase() {
//		GrantedAuthority[] usuarioRoles = { new SimpleGrantedAuthority("ROLE_USER")};
//		userRepository.save(new Usuario("user", "user1", Arrays.asList(usuarioRoles), "user@ejemplo.com", "ROLE_USER"));
//	
//		GrantedAuthority[] administradorRoles = { new SimpleGrantedAuthority("ROLE_ADMIN")};
//		userRepository.save(new Usuario("root", "root1", Arrays.asList(administradorRoles), "admin@ejemplo.com", "ROLE_ADMIN"));
//
//	}
	
	public Usuario addUsuario (Usuario u) {
		Usuario u2;
		if (u.getRol().equals("ROLE_USER")) {
			GrantedAuthority[] usuarioRoles = { new SimpleGrantedAuthority("ROLE_USER")};
			u2 = userRepository.save(new Usuario(u.getNombre(), u.getCont(), Arrays.asList(usuarioRoles), u.getCorreo(), u.getRol()));
			return u2;
		}
		else if (u.getRol().equals("ROLE_ADMIN")) {
			GrantedAuthority[] administradorRoles = { new SimpleGrantedAuthority("ROLE_ADMIN")};
			u2 = userRepository.save(new Usuario (u.getNombre(), u.getCont(), Arrays.asList(administradorRoles), u.getCorreo(), u.getRol()));
			return u2;
		}
		return null;
	}
	
	public ArrayList<Usuario> getUsuarios () {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		for (Usuario p : userRepository.findAll()) {
			usuarios.add(p);
		}
		return usuarios;
	}
	
	public List<Usuario> getOnlyUsers(){
		ArrayList<Usuario> usuarios = new ArrayList<>();
		for (Usuario p : userRepository.findAll()) {
			if(!p.getRoles().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				usuarios.add(p);
			}
		}
		return usuarios;	
	}
	
	public List<Usuario> getOnlyAdmins(){
		ArrayList<Usuario> usuarios = new ArrayList<>();
		for (Usuario p : userRepository.findAll()) {
			if(p.getRoles().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				usuarios.add(p);
			}
		}
		return usuarios;
	}
	
	public Usuario getUsuario (int index) {
		return userRepository.findById(index);
	}
	
	public Usuario getUsuarioByNombre (String optio) {
		return userRepository.findByNombre(optio);
	}
	
	public void deleteUsuario (int index) {
		Usuario u = userRepository.findById(index);
		userRepository.delete(u);		
	}

	public void setUsuario (Usuario u) {
		if (u.getRol().equals("ROLE_USER")) {
			GrantedAuthority[] usuarioRoles = { new SimpleGrantedAuthority("ROLE_USER")};
			u.setRoles(Arrays.asList(usuarioRoles));
			u.setPass(u.getCont());
			userRepository.save(u);
		}
		else if (u.getRol().equals("ROLE_ADMIN")) {
			GrantedAuthority[] administradorRoles = { new SimpleGrantedAuthority("ROLE_ADMIN")};
			u.setRoles(Arrays.asList(administradorRoles));
			u.setPass(u.getCont());
			userRepository.save(u);
		}
	}
	
}