import java.util.Scanner;

// Menu v.20250126

public class Menu {

	// EJEMPLO DE USO
	public static void main(String[] args) {
		// Usamos un unico scanner para toda la app
		Scanner teclado = new Scanner(System.in);
		// Array con enunciado y opciones
		String[] opciones = {
				"Cabecera del enunciado:",
				"Opcion A",
				"Opcion B",
				"Opcion C"
		};
		// menu() nos devuelve un int con la opcion seleccionada
		System.out.println("Has seleccionado la opcion: " + Menu.menu(opciones, teclado));

		// guardamos la seleccion en una variable
		@SuppressWarnings("unused")
		int seleccion = Menu.menu(opciones, teclado);

		// usamos switch para gestionar la respuesta del menu
		switch (Menu.menu(opciones, teclado)) {
			// no hay un case 0
			case 1:
				System.out.println("opcion uno");
				break;
			case 2:
				System.out.println("opcion dos");
				break;
			case 3:
				System.out.println("opcion tres");
				break;
		}

	}

	// MUESTRA UN MENU CON LOS STRINGS DEL PARAMETRO
	public static int menu(String[] opciones, Scanner teclado) {
		// Usamos un único Scanner en toda la aplicacion
		// opciones[0] es la cabecera

		int retorno = -1;

		while (true) {

			System.out.println(opciones[0] + "\n");
			for (int i = 1; i < opciones.length; i++) {
				System.out.printf("   %d) %s%n", i, opciones[i]);
			}

			try {
				System.out.print("\n: ");
				retorno = Integer.parseInt(teclado.nextLine());

				if (retorno >= 1 && retorno < opciones.length) {
					break; // Opción válida
				} else {
					System.out.println("Opción no válida. Inténtalo de nuevo.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada no válida. Inténtalo de nuevo.");
			}

		}
		return retorno;
	}

}
