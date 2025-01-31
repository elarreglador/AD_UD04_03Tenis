import java.util.Scanner;

import org.hibernate.SessionFactory;

import UD04_03Tenis.entity.Players;
import UD04_03Tenis.entity.Teams;


public class Main {

	public static void main(String[] args) {

		// Usamos un unico scanner para toda la app
		Scanner teclado = new Scanner(System.in);
		
		// Array con enunciado y opciones
		String[] opciones = {
				"Indica el numero de la opcion que desar y pulsa INTRO:",
				"Muestra un equipo por ID",
				"Muestra un jugador por ID",
				"Muestra los jugadores de un equipo existente",
				"Crea un nuevo equipo",
				"Crea un nuevo jugador asociado a un nuevo equipo",
				"Crea un jugador en un equipo existente",
				"Borra un jugador",
				"Borra un equipo",
				"Establece el salario de todos los jugadores de un equipo",
				"Aumenta el salario de lso mejores jugadores de un "
				+ "equipo en la temporada 07/08",
				"Salir"
		};

		boolean salir = false;
		try ( SessionFactory sf = HibernateUtil.getSessionFactory() ){
			do {
				// usamos switch para gestionar la respuesta del menu
				System.out.println();
				switch (Menu.menu(opciones, teclado)) {
					// no hay un case 0
					case 1:
						Teams.muestraEquipo(teclado, sf);
						break;
					case 2:
						Players.muestraJugador(teclado, sf);
						break;
					case 3:
						Teams.muestraJugadoresEnEquipo(teclado, sf);
						break;
					case 4:
						Teams.crearEquipo(teclado, sf);
						break;
					case 5:
						Players.crearJugadorYEquipo(teclado, sf);
						break;
					case 6:
						Players.crearJugadorEnEquipo(teclado, sf);
						break;
					case 7:
						Players.borrarJugador(teclado, sf);
						break;
					case 8:
						Teams.borrarEquipo(sf, teclado);
						break;
					case 9:
						Teams.establecerSalarioEquipo(teclado, sf);
						break;
					case 10:
						Teams.subirSalarioMejoresJugadoresEquipo(
								teclado, sf);
						break;
					case 11:
						salir = true;
						break;
				}
			} while(!salir);
		}
		
		System.out.println("Hasta la proxima!");
	}

}
