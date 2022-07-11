package it.servizio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.model.Persona;

@Path("/service")
public class Servizio {

	private final Map<Long, Persona> persone = new HashMap<>();

	public Servizio() {
		createPersone();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getpersone")
	public List<Persona> createPersone() {
		List<Persona> all = new ArrayList<>();
		char a = (int) 97;
		char b = (int) 90;
		for (int i = 0; i < 23; i++) {

			Persona persona = new Persona(Character.toString(a), Character.toString(b), i);
			all.add(persona);
			persone.put(persona.getEtà(), persona);
			--b;
			++a;
		}

		return all;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getsingle/{id}")
	public Persona onePersona(@PathParam(value = "id") Long id) {
		return persone.get(id);
	}

	@PUT
	@Path("/update")
	Response updatePersona() {

		Response response;
		Persona p = new Persona("a", "a", 10);
		Persona current = persone.get(p.getEtà());
		if (current != null) {
			persone.put(p.getEtà(), p);
			response = Response.ok().build();
		} else {
			System.out.print("null");
			response = Response.notModified().build();
		}
		return response;
	}

	@DELETE
	@Path("/delete/{id}")
	Response deletePersona(@PathParam(value = "id") Long id) {
		Response response;
		Persona persona = persone.get(id);

		if (persona != null) {
			persone.remove(persona.getEtà());
			response = Response.ok().build();
			System.out.print("deleted!");
		} else {
			response = Response.notModified().build();
		}
		return response;

	}

}
