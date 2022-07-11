# Jersey - crud operations

Applicazione per capire il funzionamento delle operazioni crud utilizzando 
il framework Jersey per gestire i servizi, le annotazioni JAX-RS per 
creare risorse e le annotazioni JAXB per mappare le nostre classi POJO. 
Infine testiamo il tutto con postman.

Struttura: 

- Pom.xml
- web.xml
- Model.java
- Servizio.java

Il pom ci permette di avere tutte le dipendenze necessarie, il file 
web.xml ci permette di mappare la classe che contiene il servizio con un 
determinato percorso e classe. 
La classe model contiene il modello e il mapping JAXB. Infine la classe 
servizio la quale, con le annotazioni JAX-RS, gestisce tutti i servizi 
collegabili ai verbi HTTP.

---

Pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>it.test</groupId>
	<artifactId>jaxrs-first</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<properties>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-server</artifactId>
			<version>1.17.1</version>
		</dependency>
		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-core</artifactId>
			<version>1.17.1</version>
		</dependency>
		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-servlet</artifactId>
			<version>1.17.1</version>
		</dependency>

		<dependency>
			<groupId>javax.xml.bind</groupId>
			<artifactId>jaxb-api</artifactId>
			<version>2.3.1</version>
		</dependency>

		<dependency>
			<groupId>org.glassfish.jaxb</groupId>
			<artifactId>jaxb-runtime</artifactId>
			<version>2.3.1</version>
		</dependency>

		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-bundle</artifactId>
			<version>1.18</version>
		</dependency>

		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-json</artifactId>
			<version>1.18.1</version>
		</dependency>
		<dependency>
			<groupId>com.owlike</groupId>
			<artifactId>genson</artifactId>
			<version>0.99</version>
		</dependency>

		<dependency>
			<groupId>com.sun.jersey</groupId>
			<artifactId>jersey-json</artifactId>
			<version>1.18.1</version>
		</dependency>
		<dependency>
			<groupId>com.owlike</groupId>
			<artifactId>genson</artifactId>
			<version>0.99</version>
		</dependency>

	</dependencies>

	<build>
		<plugins>
			<plugin>
				
<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>3.3.1</version>
			</plugin>
		</plugins>
	</build>

</project>

```

---

web.xml

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
	<display-name>Archetype Created Web Application</display-name>
	<servlet>
		<servlet-name>Jersey Web Application</servlet-name>
		<servlet-class> 
com.sun.jersey.spi.container.servlet.ServletContainer </servlet-class>
		<init-param>
			<param-name> 
com.sun.jersey.config.property.packages </param-name>
			<param-value>it.servizio</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>Jersey Web Application</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>
</web-app>
```

Lo schema web.xml ci permette di mappare un url con una determinata 
servlet. In questo caso noi colleghiamo la classe che contiene i servizi 
con le annotazioni JAX-RS con il servlet di Jersey.

Per poter collegare la nostra classe dobbiamo solo inserire il nome del 
package in cui si trova, in questo caso ‘it.servizio’.

In questo caso possiamo accedere al servlet tramite l’url ‘/rest/*’.

---

Model.java

```java
package it.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cognome;
	private long età;

	public Persona() {
	};

	public Persona(String nome, String cognome, int età) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.età = età;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String congnome) {
		this.cognome = congnome;
	}

	@XmlElement
	public long getEtà() {
		return età;
	}

	public void setEtà(int età) {
		this.età = età;
	}

}
```

Con le annotazioni JAXB possiamo mappare il nostro POJO in modo da fare 
marshall o unmarshall.

Il 
[marshalling](https://docs.oracle.com/javase/8/docs/technotes/guides/xml/jaxb/index.html) 
è il processo di scrittura di oggetti Java in un file XML. Unmarshalling è 
il processo di conversione del contenuto XML in oggetti Java.

XmlRootElement → mappa la classe come elemento XML.

XmlElement → mappa la proprietà della classe come elemento XML, (va 
posizione su i getter).

---

Servizio.java

```java
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

			Persona persona = new 
Persona(Character.toString(a), Character.toString(b), i);
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
```

La classe che contiene le annotazioni JAX-RS e i metodi da collegare con i 
verbi HTTP.

```java
@Path("/service")
public class Servizio {

	private final Map<Long, Persona> persone = new HashMap<>();

```

La prima cosa da fare è definire il path, cioè l’indirizzo a cui è 
raggiungibile la nostra classe.
Se prima abbiamo definito l’ingresso del nostro servlet, con questo 
aggiungiamo la nostra classe.
Il risultato finale è: 

http://localhost:8080/jaxrs-first/rest/service/

la prima parte è il nome del nostro progetto → jaxrs-first

la seconda è il nostro servlet → rest

la terza è la nostra classe → service 

Istanziamo una nuova lista che contiene oggetti con chiavi e valori 
collegati.

Come chiave utilizziamo un tipo di tipo ‘long’, il quale sarà l’id del 
nostro model.
Come valore utilizziamo l’istanza del nostro model.

Quando vorremo ritornare una determinata istanza la chiameremo tramite il 
suo id.

```java
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

			Persona persona = new 
Persona(Character.toString(a), Character.toString(b), i);
			all.add(persona);
			persone.put(persona.getEtà(), persona);
			--b;
			++a;
		}
		return all;
}
```

Per poter avere sempre una lista di utenti ad ogni avvio, non avendo un 
DB, la creiamo aggiungendo al costruttore della classe il metodo 
‘createPersone’.

Il metodo contiene una lista che raccoglie tutte le persone. Per poter 
creare tante persone quante le lettere dell’alfabeto, senza doverle 
scrivere, ho avviato un ciclo for. Ho utilizzato caratteri per 
diversificare le persone. Siccome i caratteri sono numeri posso cambiarli 
ad ogni ciclo aumentando di 1 il loro valore così da avere: a, +1, b, +1, 
c…

Infine aggiungo ad ogni ciclo la persona istanziata.

Le 
[annotazioni](https://docs.oracle.com/cd/E19798-01/821-1841/6nmq2cp1v/index.html) 
sopra il metodo.

@GET → ogni volta che ci sarà una chiamata di tipo GET verrà chiamato 
questo metodo.

@Produces(MediaType.APPLICATION_JSON) → definisce quale sarà il tipo di 
formato in uscita da questa chiamata. In questo caso il risultato sarà 
JSON.

@Path("/getpersone") → la chiamata sarà di tipo GET ma come lo 
chiameremo? Semplicemente definendo il percorso. Quindi:

http://localhost:8080/jaxrs-first/rest/service/getpersone

Ci ritornerà tutte le persone, cioè verrà chiamato il nostro metodo. 
Questa annotazione sarà presente ad ogni metodo.

```java
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getsingle/{id}")
	public Persona onePersona(@PathParam(value = "id") Long id) {
		return persone.get(id);
	}

```

Questo metodo ha due annotazioni simili al precedente, stesso verbo e 
stesso risultato.

Il path è leggermente diverso in quanto dinamico. Possiamo scrivere nel 
link, al posto del ‘id’, qualsiasi numero. Con l’id recuperiamo 
dinamicamente la persona che ci interessa dalla lista.

http://localhost:8080/jaxrs-first/rest/service/getsingle/10

Ritornerà la persona con id 10.

```java
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
```

Il verbo in questo caso cambia, è PUT. Il path è statico.

Il metodo ritorna una Response. Creiamo un nuovo utente e recuperiamo 
l’utente dalla lista con lo stesso ID (getEtà). Se quella recuperata dalla 
lista  è presente possiamo sostituire il corrente con la persona creata al 
momento. L’id ci permette di identificarli e aggiornarli.

Una volta fatto possiamo dare ok alla response e costruirla.

Altrimenti il nostro utente non è modificato e utilizzeremo il metodo 
‘notModified’ con build.

```java
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
```

Infine, con il verbo DELETE possiamo eliminare una persona della Lista. 
Come la ricerca utilizziamo un link dinamico per eliminare una qualsiasi 
persona.

Il processo del delete è come quello dell’update. Ricerchiamo la persona 
tramite il suo id (getEtà) e la eliminiamo dalla lista.
