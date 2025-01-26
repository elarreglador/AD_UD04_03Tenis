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
						System.out.println("opcion 4");
						break;
					case 5:
						System.out.println("opcion 5");
						break;
					case 6:
						System.out.println("opcion 6");
						break;
					case 7:
						System.out.println("opcion 7");
						break;
					case 8:
						System.out.println("opcion 8");
						break;
					case 9:
						System.out.println("opcion 9");
						break;
					case 10:
						System.out.println("opcion 10");
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
