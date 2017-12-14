package com.iwcn.master.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iwcn.master.configuration.DatabaseLoader;
import com.iwcn.master.model.Basica;
import com.iwcn.master.model.Peli;
import com.iwcn.master.model.Pelicula;
import com.iwcn.master.model.PeliculaConcreta;
import com.iwcn.master.model.Usuario;
import com.iwcn.master.services.FeignDataService;

@Controller
public class AppControllerCliente implements ErrorController{
	
	@Autowired
    private FeignDataService feignClient;

	@Autowired
	private DatabaseLoader dbLoader;
	
	
	//DENEGADO Y ERRORES
	
	// Vista que muestra el acceso denegado
	@RequestMapping("/denied")
    public ModelAndView accessDeniedPage() {
		return new ModelAndView("denied_template");
    }
	
    // Vista que muestra si no se puede acceder a la pagina
	@RequestMapping("/error")
    public ModelAndView accessErrorPage() {
		return new ModelAndView("error_template");
    }
	
	// Controlar el error
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	
	//PRINCIPALES E INICIO DE SESION
	
	// Vista principal para poder iniciar sesion
	@RequestMapping("/")
	public ModelAndView nuevoPrincipal() {
		return new ModelAndView("nuevoPrincipal_template");
	}
	
	// Vista principal una vez se ha iniciado sesion
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RequestMapping("/principal")
	public ModelAndView principal() {
		return new ModelAndView("principal_template");
	}
		
	// Vista para iniciar sesion con control de la sesion de usuario
	@RequestMapping("/login")
	public ModelAndView login(SecurityContextHolderAwareRequestWrapper request) {
		ModelAndView model;
		if ( (request.isUserInRole("ROLE_ADMIN")) || (request.isUserInRole("ROLE_USER")) )
			model = new ModelAndView("principal_template");
		else
			model = new ModelAndView("login_template");
		return model;
	}
	
	// Vista para finalizar la sesion de usuario
	@RequestMapping("/salir")
	public ModelAndView logout() {
		return new ModelAndView("logout_template");
	}
	
	
	//USUARIOS 
	
	// Vista que muestra el formulario para añadir nuevos usuarios
	@Secured( "ROLE_ADMIN" )
	@GetMapping("/principal/usuario")
    public ModelAndView nuevoUsuario(Usuario u) {
		return new ModelAndView("newUser_template");
    }
	
	// Vista que añade el usuario y muestra la lista
	@Secured( "ROLE_ADMIN" )
	@PostMapping("/principal/finUsuario")
    public ModelAndView nuevoUsuario2(@Valid Usuario u) {
		dbLoader.addUsuario(u);
		return new ModelAndView("listUser_template").addObject("users", dbLoader.getOnlyUsers())
				.addObject("admins",dbLoader.getOnlyAdmins());
    }
	
	// Vista que muestra la lista de usuarios
	@Secured("ROLE_ADMIN")
	@GetMapping("/principal/listUser")
    public ModelAndView listaUsuarios() {
		return new ModelAndView("listUser_template").addObject("users", dbLoader.getOnlyUsers())
				.addObject("admins",dbLoader.getOnlyAdmins());
    }
	
	// Vista del usuario en si
	@Secured("ROLE_ADMIN")
    @GetMapping("/principal/usuario/{optio}")
    public ModelAndView mostrarUsuario(@PathVariable String optio)  {
		int index = Integer.parseInt(optio);
    	Usuario u = dbLoader.getUsuario(index);
    	return new ModelAndView("usuario_template").addObject("nombre", u.getNombre()).addObject("correo", u.getCorreo()).addObject("listaRoles", u.getRol());
    }
	
	// Vista que permite borrar usuarios si no son los iniciales o el que se encuentra en sesion
	@Secured( "ROLE_ADMIN" )
    @RequestMapping("/principal/usuario/borrar/{optio}")
    public ModelAndView borrarUsuario(@PathVariable String optio) {
		int index = Integer.parseInt(optio);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    	Usuario u = dbLoader.getUsuario(index);
		if ( (index > 2) && !(principal.equals(u.getNombre())) ) {
			dbLoader.deleteUsuario(index);
			return new ModelAndView("listUser_template").addObject("users", dbLoader.getOnlyUsers())
	    			.addObject("admins",dbLoader.getOnlyAdmins());
		}
		return new ModelAndView("noModificarBorrar_template").addObject("nombre", u.getNombre());
    }
    
    // Vista que contiene el formulario para editar el usuario si no son los iniciales o el que se encuentra en sesion
    @Secured( "ROLE_ADMIN" )
    @GetMapping("/principal/usuario/editar/{optio}")
    public ModelAndView editarUsuario(@PathVariable String optio, Model model) {
    	int index = Integer.parseInt(optio);
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    	Usuario u = dbLoader.getUsuario(index);
    	if ( (index > 2) && !(principal.equals(u.getNombre())) ){
	    	u.setCont(u.getCont2());
	    	model.addAttribute(u);
	    	return new ModelAndView("editarUsuario_template").addObject("nombre", u.getNombre());
    	}
    	return new ModelAndView("noModificarBorrar_template").addObject("nombre", u.getNombre());
    }  
    
    // Vista que edita el usuario y muestra la lista
    @Secured( "ROLE_ADMIN" )
    @PostMapping("/principal/usuario/modificar")
    public ModelAndView modificarUsuario(@Valid Usuario u) {
    	dbLoader.setUsuario(u);
    	return new ModelAndView("listUser_template").addObject("users", dbLoader.getOnlyUsers())
				.addObject("admins",dbLoader.getOnlyAdmins());
    }
	    
	
    //PELICULAS
    
    // Vista para buscar peliculas en OMDB
    @Secured( "ROLE_ADMIN" )
	@GetMapping("/principal/busqueda")
    public ModelAndView busquedaPelicula() {
    	return new ModelAndView("busqueda_template");
    }
    
    // Vista con las peliculas encontradas en OMDB
    @Secured( "ROLE_ADMIN" )
	@GetMapping("/principal/busqueda2")
    public ModelAndView busquedaPelicula2(@RequestParam String optio) {
    	List<Pelicula> lista = feignClient.callListaPeliculasOMDB(optio);
    	List<Peli> peliculas = new ArrayList<>();
    	for (Pelicula p : lista) {
    		if (!p.getPoster().equals("N/A"))
    			peliculas.add(new Peli(p.getImdbID(), p.getTitle(), p.getYear(), p.getPoster(), true));
    		else
    			peliculas.add(new Peli(p.getImdbID(), p.getTitle(), p.getYear(), p.getPoster(), false));
    	}
    	return new ModelAndView("busqueda2_template").addObject("busqueda", peliculas);
    }
    
    // Vista con las peliculas encontradas en mi BBDD
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/principal/busqueda3")
    public ModelAndView busquedaPelicula3(@RequestParam String optio) {
    	PeliculaConcreta[] lista = feignClient.callBusquedaPeliculas(optio);
    	List<Peli> peliculas = new ArrayList<>();
    	for (PeliculaConcreta p : lista) {
    		if (!p.getPoster().equals("N/A"))
    			peliculas.add(new Peli(p.getImdbID(), p.getTitle(), null, p.getPoster(), true));
    		else
    			peliculas.add(new Peli(p.getImdbID(), p.getTitle(), null, p.getPoster(), false));
    	}
    	return new ModelAndView("busqueda3_template").addObject("busqueda", peliculas);
    }
    
    // Vista con los usuarios para buscarlos
    @Secured( "ROLE_ADMIN"  )
	@GetMapping("/principal/busqueda4")
    public ModelAndView busquedaPelicula4(@RequestParam String optio) {
    	return new ModelAndView("busqueda4_template").addObject("busqueda", dbLoader.getUsuarioByNombre(optio));
    }
  		
	// Vista que muestra que la pelicula se ha añadido
    @Secured( "ROLE_ADMIN" )
    @PostMapping("/principal/peliculaBD/anadir")
    public ModelAndView anadirPelicula(@Valid PeliculaConcreta pi) {
    	PeliculaConcreta[] lista = feignClient.callLista();
    	
    	String[] tituloPeliculaCortado = pi.getTitle().split(" ");
		String tituloPelicula = new String();
		for (String tPC : tituloPeliculaCortado)
			tituloPelicula += tPC;
		
    	for (PeliculaConcreta pc : lista) {
			
			String[] tituloPeliculaListaCortado = pc.getTitle().split(" ");
			String tituloPeliculaLista = new String();
			for (String tPC : tituloPeliculaListaCortado)
				tituloPeliculaLista += tPC;

			if ( tituloPelicula.toLowerCase().equals(tituloPeliculaLista.toLowerCase()) ) {
				return new ModelAndView("noAnadir_template").addObject("nombre", pi.getTitle());
			}
		}
    	feignClient.callNuevo(pi);
    	lista = feignClient.callLista();
    	return new ModelAndView("list_template").addObject("pelis", lista);
    }

    // Vista para mostrar detalles de las peliculas en OMDB
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/principal/pelicula/{optio}")
    public ModelAndView mostrarPeliculaOMDB(@PathVariable String optio)  {
    	PeliculaConcreta p = feignClient.callPeliculaConcretaOMDB(optio);
    	boolean imagen = false;
    	if(!p.getPoster().equals("N/A"))
    		imagen = true;
    	return new ModelAndView ("pelicula_template").addObject("title", p.getTitle()).addObject("plot", p.getPlot()).addObject("released", p.getReleased())
		.addObject("director", p.getDirector()).addObject("actors", p.getActors()).addObject("poster", p.getPoster()).addObject("imdbRating", p.getImdbRating())
		.addObject("runtime", p.getRuntime()).addObject("imagen", imagen);
    }
    
    // Vista que contiene el formulario para añadir peliculas
 	@Secured( "ROLE_ADMIN" )
 	@GetMapping("/principal/new")
    public ModelAndView nuevaPelicula(Basica b) {
     	return new ModelAndView("newPelicula_template");
     }
 	
 	// Vista que muestra que la pelicula se ha añadido si el nombre es exacto
	@Secured( "ROLE_ADMIN" )
	@PostMapping("/principal/fin")
	public ModelAndView nuevaPelicula2(@Valid Basica b) {
		
		String[] tituloPeliculaCortado = b.getTitle().split(" ");
		String tituloPelicula = new String();
		for (String tPC : tituloPeliculaCortado)
			tituloPelicula += tPC;

		PeliculaConcreta[] lista = feignClient.callLista();
		
		for (PeliculaConcreta pc : lista) {
			
			String[] tituloPeliculaListaCortado = pc.getTitle().split(" ");
			String tituloPeliculaLista = new String();
			for (String tPC : tituloPeliculaListaCortado)
				tituloPeliculaLista += tPC;

			if ( tituloPelicula.toLowerCase().equals(tituloPeliculaLista.toLowerCase()) ) {
				return new ModelAndView("noAnadir_template").addObject("nombre", b.getTitle());
			}
		}
		// Comprobar que la pelicula existe en OMDB
		List<Pelicula> p = feignClient.callListaPeliculasOMDB(b.getTitle());
		if (p.size() != 0) {
			PeliculaConcreta miPeli = new PeliculaConcreta();
			for (Pelicula posicion : p) {
				
				String[] tituloPeliculaOMDBCortado = posicion.getTitle().split(" ");
				String tituloPeliculaOMDB = new String();
				for (String tPC : tituloPeliculaOMDBCortado)
					tituloPeliculaOMDB += tPC;
				
				if ( tituloPeliculaOMDB.toLowerCase().equals(tituloPelicula.toLowerCase()) ) {
					miPeli = feignClient.callPeliculaConcretaOMDB(posicion.getImdbID());
					miPeli.setVideo(b.getVideo());
				}
			}
			feignClient.callNuevo(miPeli);
		}
		lista = feignClient.callLista();
		return new ModelAndView("list_template").addObject("pelis", lista);		
	}

	// Vista para añadir la pelicula desde la vista de OMDB
	@Secured( "ROLE_ADMIN" )
	@GetMapping("/principal/finOMDB/{optio}")
	public ModelAndView nuevaPelicula3(@PathVariable String optio, Model model) {
		PeliculaConcreta pi = feignClient.callPeliculaConcretaOMDB(optio);
		model.addAttribute(pi);
		return new ModelAndView("anadir_template").addObject("nombre", pi.getTitle());
	} 
	
	// Vista que contiene la lista de peliculas
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/principal/list")
    public ModelAndView listaPeliculas() {
		PeliculaConcreta[] lista = feignClient.callLista();
		return new ModelAndView("list_template").addObject("pelis", lista);
    }

    // Vista de la pelicula en si almacenada en la base de datos
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/principal/peliculaBD/{optio}")
    public ModelAndView mostrarPelicula(@PathVariable String optio)  {
    	PeliculaConcreta p = feignClient.callPeliculaConcreta(optio);
    	boolean imagen = false;
    	if(!p.getPoster().equals("N/A"))
    		imagen = true;
    	return new ModelAndView("pelicula2_template").addObject("title", p.getTitle()).addObject("plot", p.getPlot()).addObject("released", p.getReleased())
    			.addObject("director", p.getDirector()).addObject("actors", p.getActors()).addObject("poster", p.getPoster()).addObject("imdbRating", p.getImdbRating())
    			.addObject("runtime", p.getRuntime()).addObject("video", p.getVideo()).addObject("imagen", imagen);
    }
    
    // Vista que permite borrar peliculas
	@Secured( "ROLE_ADMIN" )
    @RequestMapping("/principal/peliculaBD/borrar/{optio}")
    public ModelAndView borrarPelicula(@PathVariable String optio) {
    	feignClient.callBorrar(optio);
    	PeliculaConcreta[] lista = feignClient.callLista();
    	return new ModelAndView("list_template").addObject("pelis", lista);
    }
    
    // Vista que contiene el formulario para editar la pelicula
	@Secured( "ROLE_ADMIN" )
    @GetMapping("/principal/peliculaBD/editar/{optio}")
    public ModelAndView editarPelicula(@PathVariable String optio, Model model) {
    	PeliculaConcreta p = feignClient.callPeliculaConcreta(optio);
    	model.addAttribute(p);
    	return new ModelAndView("editar_template").addObject("nombre", p.getTitle());
    }  
    
    // Vista que muestra que la pelicula se ha editado
    @Secured( "ROLE_ADMIN" )
    @PostMapping("/principal/peliculaBD/modificar")
    public ModelAndView modificarPelicula(@Valid PeliculaConcreta p) {
    	feignClient.callModificar(p);
    	PeliculaConcreta[] lista = feignClient.callLista();
    	return new ModelAndView("list_template").addObject("pelis", lista);
    }

    
}
