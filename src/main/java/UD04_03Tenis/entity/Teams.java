package UD04_03Tenis.entity;
// Generated 26 ene 2025 12:30:14 by Hibernate Tools 4.3.6.Final

import java.util.Arrays;
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
import org.hibernate.Transaction;
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
	
	public Teams(String name, String city, String conference, 
			String division) {
		this.name = name;
		this.city = city;
		this.conference = conference;
		this.division = division;}

	
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
	        // Realizamos la consulta para verificar si 
	    	// existe un equipo con ese nombre
	    	Query<Long> query = session.createQuery(
	                "SELECT COUNT(T) "
	                + "FROM Teams T "
	                + "WHERE T.name = :name", Long.class);
	            query.setParameter("name", name);
	            // Devuelve el número de coincidencias
	            Long count = query.uniqueResult(); 
	            // Existe si el conteo es mayor que 0
	            return count != null && count > 0; 
	    } catch (Exception e) {
	        System.err.println("Error al verificar la existencia del "
	        		+ "equipo: " + e.getMessage());
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

	public static Teams crearEquipo(Scanner teclado, SessionFactory sf) {
		// Pregunta los datos necesarios para crear equipo y crear 
		// objeto persistente en la base de datos.
		// El retorno sera null si no se crea el equipo
		
		System.out.println("CREA UN NUEVO EQUIPO");
		System.out.print("Introduce nombre (ID) de equipo: ");
		String name = teclado.nextLine();
		System.out.print("Introduce division del equipo: ");
		String division = teclado.nextLine();
		System.out.print("Introduce ciudad de equipo: ");
		String city = teclado.nextLine();
		System.out.print("Introduce conference de equipo: ");
		String conference = teclado.nextLine();
		
		Teams equipo = guardaEquipo(sf, name, division, city, conference);
		return equipo;
	}
	
	private static Teams guardaEquipo(SessionFactory sf, String name, 
			String division, String city, String conference) {
		// Guarda equipo en la BD
				
		if ( Teams.existe(sf, name) ) { 
			System.out.println("El equipo " + name +
					" ya existia, no se crea el equipo");
			return null;
		}
		
		Teams equipo = new Teams(name, city, conference, division);

		Transaction tx = null;
		try ( Session session = sf.openSession() ){
			tx = session.beginTransaction();
			session.persist(equipo);
			tx.commit();
			System.out.println("\nEquipo guardado en BD.\n");
			return equipo;
		} catch (Exception e) {
			System.out.println("Excepcion nuevo(): " + e);
		}
		return null;
	}
	
	public static void borrarEquipo(SessionFactory sf, Scanner teclado) {
		// pregunta por el ID de un equipo y lo borra de la base de datos. 
		// Si no existe ningún equipo con ese ID, mostrara un mensaje.
		// Si existen jugadores asociados a ese equipo, este no se 
		// puede borrar hasta que se hayan borrado los jugadores, 
		// se deberá mostrar un mensaje para confirmar la eliminación 
		// de los jugadores.
		
	    System.out.println("BORRA UN EQUIPO");
	    System.out.print("Introduce nombre (ID) de equipo: ");
	    String teamName = teclado.nextLine();

	    if (!Teams.existe(sf, teamName)) {
	        System.out.println("El equipo " + teamName + " NO existe");
	        return;
	    }

	    Session session = null;
	    Transaction tx = null;

	    try {
	        session = sf.openSession();
	        tx = session.beginTransaction();

	        // Verificar si hay jugadores asociados al equipo
	        Query<Long> query = session.createQuery(
	                "SELECT COUNT(P) "
	                + "FROM Players P "
	                + "WHERE P.teams.name = :teamName", Long.class);
	        query.setParameter("teamName", teamName);
	        Long numPlayers = query.uniqueResult();

	        if (numPlayers > 0) {
	            boolean continuar = false;
	            do {
	                System.out.print("\nSe borrarán también "
	                		+ "los resultados de partidos, estadisticas, "
	                		+ "y los jugadores de este equipo. "
	                		+ "Continuar (s/n): ");
	                String opcion = teclado.nextLine();
	                if (opcion.equalsIgnoreCase("n")) {
	                    System.out.println("Operación cancelada.");
	                    return;
	                } else if (opcion.equalsIgnoreCase("s")) {
	                    continuar = true;
	                }
	            } while (!continuar);
	        }
	        
	        // Borrrar registros en Matches asociados al equipo
	        Query<?> deleteMatchesQuery1 = session.createQuery(
	        		"DELETE "
	        		+ "FROM Matches M "
	        		+ "WHERE M.teamsByVisitorTeam.name = :teamName");
	        deleteMatchesQuery1.setParameter("teamName", teamName);
	        int MatchesEliminados = deleteMatchesQuery1.executeUpdate();
	        
	        Query<?> deleteMatchesQuery2 = session.createQuery(
	        		"DELETE "
	        		+ "FROM Matches M "
	        		+ "WHERE M.teamsByLocalTeam.name = :teamName");
	        deleteMatchesQuery2.setParameter("teamName", teamName);
	        MatchesEliminados = 
	        		MatchesEliminados + deleteMatchesQuery2.executeUpdate();

	        // Borrar registros en Stats asociados a los jugadores del equipo
	        Query<?> deleteStatsQuery = session.createQuery(
	        		"DELETE "
	        		+ "FROM Stats S "
	        		+ "WHERE S.players.code IN (" 
	        	    + "	  SELECT P.code "
	        	    + "	  FROM Players P "
	        	    + "   WHERE P.teams.name = :teamName"
	        	    + ")");
	        deleteStatsQuery.setParameter("teamName", teamName);
	        int statsEliminados = deleteStatsQuery.executeUpdate();

	        // Borrar jugadores asociados al equipo
	        Query<?> deletePlayersQuery = session.createQuery(
	                "DELETE "
	                + "FROM Players P "
	                + "WHERE P.teams.name = :teamName");
	        deletePlayersQuery.setParameter("teamName", teamName);
	        int jugadoresEliminados = deletePlayersQuery.executeUpdate();

	        // Borrar el equipo
	        Query<?> deleteTeamQuery = session.createQuery(
	                "DELETE "
	                + "FROM Teams T "
	                + "WHERE T.name = :teamName");
	        deleteTeamQuery.setParameter("teamName", teamName);
	        int equiposEliminados = deleteTeamQuery.executeUpdate();
	        
	        tx.commit();

	        System.out.println("Match eliminados: " + MatchesEliminados);
	        System.out.println("Stats eliminados: " + statsEliminados);
	        System.out.println("Jugadores eliminados: " + jugadoresEliminados);
	        System.out.println("Equipos eliminados: " + equiposEliminados);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Excepción borrarEquipo(): " + e.getMessage());
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	}
	
	public static void establecerSalarioEquipo(
			Scanner teclado, SessionFactory sf) {
		// pregunta por el ID de un equipo y modificará el salario de los 
		// jugadores de éste a la cantidad que se establezca por pantalla. 
		// Si no existe ningún equipo con esa ID se deberá mostrar un 
		// mensaje por pantalla.
		
		System.out.println("ESTABLECE EL SALARIO DE TODOS LOS JUGADORES"
				+ "DE UN EQUIPO");
		System.out.print("Introduce nombre (ID) de equipo: ");
		String name = teclado.nextLine();
		
		if (!Teams.existe(sf, name) ) {
			System.out.println("El equipo " + name + " NO existe.");
			return;
		}
		
		Transaction tx = null;
		try ( Session session = sf.openSession() ){
			tx = session.beginTransaction();
			
			Query<Players> query = session.createQuery(
					"FROM Players P "
					+ "WHERE P.teams.name = :team", Players.class);
			query.setParameter("team", name);
			List<Players> jugadores = query.list();
			
			System.out.println();
			int salario;
			for (Players jugador : jugadores) {
				System.out.print("Nuevo salario para " + jugador.getName() + ": ");
				salario = teclado.nextInt();
				teclado.nextLine();
				jugador.setSalary(salario);
				System.out.println(
						"Actualizado salario de " + jugador.getName());
			}
			tx.commit();
			
		} catch (Exception e) {
			System.out.println("Excepcion: "+ e);
		}
	}
	
	public static void subirSalarioMejoresJugadoresEquipo(Scanner teclado, 
			SessionFactory sf) {
		// pregunta por la ID de un equipo, por el porcentaje de aumento 
		// que se va aplicar, y cual es la estadística a tener en cuenta. 
		// El salario de los jugadores con los máximos en esa estadística 
		// se debe incrementar. 
		// Si no existe ningún equipo con esa ID se mostrara un mensaje.
		
	    System.out.println("SUBIR SALARIO A LOS MEJORES JUGADORES "
	    		+ "DE UN EQUIPO");
	    
	    Transaction tx = null;
	    try (Session session = sf.openSession() ) {
	        tx = session.beginTransaction();
		    System.out.print("Introduce nombre (ID) de equipo: ");
		    String teamName = teclado.nextLine();
	
		    if (!Teams.existe(sf, teamName)) {
		        System.out.println("El equipo " + teamName + 
		        		" NO existe.");
		        return;
		    }
	
		    System.out.print("Introduce el porcentaje de "
		    		+ "aumento (sin %): ");
		    double porcentajeAumento = teclado.nextInt();
		    teclado.nextLine();
		    porcentajeAumento = porcentajeAumento / 100;
	
		    List<String> estadisticasValidas = Arrays.asList(
		    		"pointsPerMatch", "assistancesPerMatch", 
		    		"blocksPerMatch", "reboundPerMatch");
		    
		    // Pide estadistica y verifica
		    boolean continuar = false;
		    String estadistica;
		    do {
			    System.out.print("Introduce la estadística a considerar \n"
			    		+ "(pointsPerMatch, assistancesPerMatch, "
			    		+ "blocksPerMatch, reboundPerMatch): \n");
			    estadistica = teclado.nextLine();
		
			    if (!estadisticasValidas.contains(estadistica)) {
			        System.out.println("Estadística no válida. Usa: "
			        		+ "pointsPerMatch, assistancesPerMatch, "
			        		+ "blocksPerMatch o reboundPerMatch");
			    } else {
			    	continuar = !continuar;
			    }
		    } while (!continuar);
	
	        // Obtener el máximo valor de la estadística en el equipo
		    System.out.println("Revisando " + estadistica + " ...");
	        String maxQueryStr = 
	        		"SELECT MAX(S." + estadistica + ") "
	        		+ "FROM Stats S "
	        		+ "WHERE S.players.teams.name = :teamName";
	        Query<Float> maxQuery = session.createQuery(maxQueryStr, 
	        		Float.class);
	        maxQuery.setParameter("teamName", teamName);
	        Float maxValor = maxQuery.uniqueResult();
	        System.out.println("La maxima estadistica es " + maxValor);

	        if (maxValor == null) {
	            System.out.println(
	            		"No hay estadísticas para los " + teamName);
	            return;
	        }

	        // Obtener los jugadores con el máximo valor en la estadística
	        System.out.println("Obteniendo jugadores de máximo valor...");
	        String playerQueryStr = 
	        		"SELECT S.players "
	        		+ "FROM Stats S "
	        		+ "WHERE ABS("
	        		+ "   S." + estadistica + " - :maxValor) < 0.0001 "
	        		+ "AND S.players.teams.name = :teamName ";
    		// WHERE S." + estadistica + " = :maxValor no encuentra al
	        // jugador por ajuste de valor en valores decimales


	        Query<Players> playerQuery = session.createQuery(
	        		playerQueryStr, Players.class);
	        playerQuery.setParameter("teamName", teamName);
	        playerQuery.setParameter("maxValor", maxValor);
	        List<Players> mejoresJugadores = playerQuery.list();

	        System.out.println(mejoresJugadores.size() + 
	        		" jugadores afectados:");
            for (Players jugador : mejoresJugadores) {
                double nuevoSalario = 
                		jugador.getSalary() * (1 + porcentajeAumento);
                jugador.setSalary((int) nuevoSalario);
                session.update(jugador);
                System.out.println(
                		"Nuevo salario para " + jugador.getName() 
                		+ ": " + (int) nuevoSalario);
            }
	        tx.commit();
	        
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Excepción: " + e.getMessage() );
	    }
	}
		    
}

