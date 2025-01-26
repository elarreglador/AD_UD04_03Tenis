package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


@Entity
@Table(name = "teams", catalog = "NBA")
public class Teams implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Name", unique = true, nullable = false, length = 20)
	private String name;
	@Column(name = "City", length = 20)
	private String city;
	@Column(name = "Conference", length = 4)
	private String conference;
	@Column(name = "Division", length = 9)
	private String division;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teamsByVisitorTeam")
	private Set<Matches> matchesesForVisitorTeam = new HashSet<Matches>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teamsByLocalTeam")
	private Set<Matches> matchesesForLocalTeam = new HashSet<Matches>(0);

	public Teams() {}

	public Teams(String name) {
		this.name = name;}

	public Teams(String name, String city, String conference, 
			String division, Set<Matches> matchesesForVisitorTeam,
			Set<Matches> matchesesForLocalTeam) {
		this.name = name;
		this.city = city;
		this.conference = conference;
		this.division = division;
		this.matchesesForVisitorTeam = matchesesForVisitorTeam;
		this.matchesesForLocalTeam = matchesesForLocalTeam;}

	
	public String getName() {
		return this.name;}

	public void setName(String name) {
		this.name = name;}

	public String getCity() {
		return this.city;}

	public void setCity(String city) {
		this.city = city;}

	public String getConference() {
		return this.conference;}

	public void setConference(String conference) {
		this.conference = conference;}

	public String getDivision() {
		return this.division;}

	public void setDivision(String division) {
		this.division = division;}

	public Set<Matches> getMatchesesForVisitorTeam() {
		return this.matchesesForVisitorTeam;}

	public void setMatchesesForVisitorTeam(
			Set<Matches> matchesesForVisitorTeam) {
		this.matchesesForVisitorTeam = matchesesForVisitorTeam;}

	public Set<Matches> getMatchesesForLocalTeam() {
		return this.matchesesForLocalTeam;}

	public void setMatchesesForLocalTeam(
			Set<Matches> matchesesForLocalTeam) {
		this.matchesesForLocalTeam = matchesesForLocalTeam;}
	
	@Override
	public String toString() {
		return " name = " + this.name + 
				"\n division = " + this.division +
				"\n city = " + this.getCity() + 
				"\n conference = " + this.getConference();
	}
	
	public static boolean existe(SessionFactory sf, String name) {
	    // Verifica si existe un equipo
	    try (Session session = sf.openSession()) {
	        // Realizamos la consulta para verificar si existe un equipo con ese nombre
	    	Query<Long> query = session.createQuery(
	                "SELECT COUNT(T) FROM Teams T WHERE T.name = :name", Long.class);
	            query.setParameter("name", name);

	            Long count = query.uniqueResult(); // Devuelve el número de coincidencias
	            return count != null && count > 0; // Existe si el conteo es mayor que 0
	    } catch (Exception e) {
	        System.err.println("Error al verificar la existencia del equipo: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public static void muestraEquipo(Scanner teclado, SessionFactory sf) {
		/*
		 Preguntará por una ID y devolverá el equipo con la ID 
		 correspondiente por consola. Si no existe ningún equipo con 
		 esa ID, se deberá mostrar un mensaje.
		 */
		System.out.println("MUESTRA UN EQUIPO POR ID");
		System.out.print("Introduce nombre (ID) de equipo: ");
		String name = teclado.nextLine();
				
		try ( Session session = sf.openSession() ){
			// Busqueda por aproximacion del nombre
			// LIKE: name debe estar rodeado del caracter %
			Query<Teams> query = session.createQuery(
					"FROM Teams T " +
					"WHERE T.name LIKE :name", Teams.class);
			query.setParameter("name", "%" + name + "%");
			List<Teams> equipos = query.list();
			
			if (!equipos.isEmpty()) {
				Teams equipo = equipos.get(0);
				System.out.println("\n" + equipo + "\n");
			} else {
				System.out.println("No existe el equipo " + name);
				System.out.println();
			}
			
		} catch (Exception e) {
            System.err.println("muestraEquipo() Exception: " + e);
        }
	}
	
	public static void muestraJugadoresEnEquipo(
			Scanner teclado, SessionFactory sf) {
		// Pregunta por la ID de un equipo y mostrará a los jugadores 
		// asociados a este. Si no existe un equipo con esa ID, se 
		// deberá mostrar un mensaje.
		
		System.out.println("MUESTRA LOS JUGADORES DE UN EQUIPO EXISTENTE");
		System.out.print("Introduce nombre (ID) de equipo: ");
		String name = teclado.nextLine();
		
		if (Teams.existe(sf, name)) {
			try ( Session session = sf.openSession() ){
				Query<Players> query = session.createQuery(
						"FROM Players P "
						+ "WHERE P.teams.name = :team", Players.class);
				query.setParameter("team", name);
				List<Players> jugadores = query.list();
				
				System.out.println();
				for (Players jugador : jugadores) {
					System.out.println(jugador.getName());
				}
				System.out.println();
				
			} catch (Exception e) {
				System.out.println("Excepcion: "+ e);
			}
		} else {
			System.out.println("El equipo " + name + " no existe");
			System.out.println();
		}
	}

}

