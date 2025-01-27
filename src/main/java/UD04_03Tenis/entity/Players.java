package UD04_03Tenis.entity;
// Generated 26 ene 2025 16:50:50 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Players generated by hbm2java
 */
@Entity
@Table(name = "players", catalog = "NBA")
public class Players implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "code", unique = true, nullable = false)
	private int code;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Team_name")
	private Teams teams;
	@Column(name = "Name", length = 30)
	private String name;
	@Column(name = "Origin", length = 20)
	private String origin;
	@Column(name = "Height", length = 4)
	private String height;
	@Column(name = "Weight")
	private Integer weight;
	@Column(name = "Position", length = 5)
	private String position;
	@Column(name = "Salary")
	private Integer salary;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "players")
	private Set<Stats> stats = new HashSet<Stats>(0);

	public Players() {}

	public Players(int code) {
		this.code = code;}
	
	public Players(int code, Teams teams, String name, String origin, 
			String height, Integer weight, String position,
			Integer salary) {
		this.code = code;
		this.teams = teams;
		this.name = name;
		this.origin = origin;
		this.height = height;
		this.weight = weight;
		this.position = position;
		this.salary = salary;}

	public Players(int code, Teams teams, String name, String origin, 
			String height, Integer weight, String position,
			Integer salary, Set<Stats> stats) {
		this.code = code;
		this.teams = teams;
		this.name = name;
		this.origin = origin;
		this.height = height;
		this.weight = weight;
		this.position = position;
		this.salary = salary;
		this.stats = stats;}

	public int getCode() {
		return this.code;}

	public void setCode(int code) {
		this.code = code;}

	public Teams getTeams() {
		return this.teams;}

	public void setTeams(Teams teams) {
		this.teams = teams;}

	public String getName() {
		return this.name;}

	public void setName(String name) {
		this.name = name;}

	public String getOrigin() {
		return this.origin;}

	public void setOrigin(String origin) {
		this.origin = origin;}

	public String getHeight() {
		return this.height;}

	public void setHeight(String height) {
		this.height = height;}

	public Integer getWeight() {
		return this.weight;}

	public void setWeight(Integer weight) {
		this.weight = weight;}

	public String getPosition() {
		return this.position;}

	public void setPosition(String position) {
		this.position = position;}

	public Integer getSalary() {
		return this.salary;}

	public void setSalary(Integer salary) {
		this.salary = salary;}

	public Set<Stats> getStats() {
		return this.stats;}

	public void setStatses(Set<Stats> stats) {
		this.stats = stats;}
	
	@Override
	public String toString() {
		return " ID = " + this.getCode() +
				"\n nombre = " + this.getName() + 
				"\n equipo = " + this.getTeams().getName() + 
				"\n posicion = " + this.getPosition() + 
				"\n origen = " + this.getOrigin() + 
				"\n altura (pies - pulgadas) = " + this.getHeight() + 
				"\n peso (lbs) = " + this.getWeight() + 
				"\n salario = " + this.getSalary();
		}
	
	public static boolean existe(SessionFactory sf, Integer code) {
	    // Verifica si existe un jugador
	    try (Session session = sf.openSession()) {
	        // Realizamos la consulta para verificar si 
	    	// existe un jugador con ese nombre
	    	Query<Long> query = session.createQuery(
	                "SELECT COUNT(P) FROM Players P WHERE P.code = :code", Long.class);
            query.setParameter("code", code);

            // Devuelve el número de coincidencias
            Long count = query.uniqueResult(); 
            // Existe si el conteo es mayor que 0
            return count != null && count > 0; 
	    } catch (Exception e) {
	        System.err.println("Error al verificar la existencia del equipo: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static void muestraJugador(Scanner teclado, SessionFactory sf) {
		/*
		 Preguntará por la ID de un jugador y mostrará la información por
		 pantalla. Si no existe el jugador, se deberá mostrar un mensaje.
		 */
		System.out.println("MUESTRA UN JUGADOR POR ID");
		System.out.print("Introduce ID de jugador: ");
		Integer num = teclado.nextInt();
		teclado.nextLine(); // evita entradas fantasma
		
		try ( Session session = sf.openSession() ){
			Query<Players> query = session.createQuery(
					"FROM Players P " +
					"WHERE P.code = :num", Players.class);
			query.setParameter("num", num);
			List<Players> jugadores = query.list();
			
			if (!jugadores.isEmpty()) {
				Players jugador = jugadores.get(0);
				System.out.println("\n" + jugador + "\n");
			} else {
				System.out.println("No existe el jugador " + num);
			}
			
		} catch (Exception e) {
            System.err.println("Error al ejecutar la consulta: " + 
            		e.getMessage());
            e.printStackTrace();
        }
	}
	
	private static Players guardaJugador(SessionFactory sf, 
			int code, Teams equipo, String name, String origin, 
			String height, Integer weight, String position,
			Integer salary) {
		// Guarda jugador en la BD
				
		if ( Teams.existe(sf, name) ) { 
			System.out.println("El jugador codigo " + code +
					" ya existia, no se crea el jugador");
			return null;
		}
		
		Players jugador = new Players(code, equipo, name, origin, 
				height, weight, position, salary);

		Transaction tx = null;
		try ( Session session = sf.openSession() ){
			tx = session.beginTransaction();
			session.persist(jugador);
			tx.commit();
			System.out.println("\nJugador guardado en BD.\n");
			return jugador;
		} catch (Exception e) {
			System.out.println("Excepcion nuevo(): " + e);
		}
		return null;
	}
	
	public static void crearJugadorYEquipo(Scanner teclado, SessionFactory sf) {
		// pregunta por los datos necesarios para crear el jugador y el
		// equipo y creará un objeto persistente en la base de datos
		
		System.out.println("CREA UN NUEVO JUGADOR ASOCIADO A UN NUEVO"
				+ "EQUIPO");
		System.out.print("Introduce codigo (ID) de jugador: ");
		int code = teclado.nextInt();
		teclado.nextLine();
		System.out.print("Introduce origen de jugador: ");
		String origin = teclado.nextLine();
		System.out.print("Introduce posicion de jugador: ");
		String position = teclado.nextLine();
		System.out.print("Introduce salario de jugador: ");
		Integer salary = teclado.nextInt();
		teclado.nextLine();
		System.out.print("Introduce altura de jugador (pies-pulgadas): ");
		String height = teclado.nextLine();
		System.out.print("Introduce nombre de jugador: ");
		String name = teclado.nextLine();
		System.out.print("Introduce peso de jugador: ");
		Integer weight = teclado.nextInt();
		teclado.nextLine();
		
		// crea el equipo y me devuelve el objeto creado
		Teams equipo = Teams.crearEquipo(teclado, sf);
				
		Players.guardaJugador(sf, code, equipo, name, origin, 
				height, weight, position, salary);	
	}
}
