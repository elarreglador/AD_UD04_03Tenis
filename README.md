# ACCESO A DATOS: ACTIVIDAD 4.2 – CRUD CON OBJETOS DE HIBERNATE

## 1. CREAR BASE DE DATOS

Utiliza el fichero nba.sql para crear la base de datos en MySQL. 

```bash
elarreglador@G9:~$ mysql -u miUsuario -p
Enter password: *******
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1012
Server version: 8.0.40-0ubuntu0.22.04.1 (Ubuntu)

Copyright (c) 2000, 2024, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> SOURCE /home/elarreglador/Escritorio/CFS DAM/2024-2025 2CFS DAM/2CFS AD/SEGUNDO PARCIAL/eclipse-workspace2023-09/UD04_03Tenis/nba.sql
Query OK, 1 row affected (0,02 sec)

Database changed
Query OK, 0 rows affected (0,07 sec)

Query OK, 1 row affected (0,02 sec)

[...]

Query OK, 1 row affected (0,02 sec)

Query OK, 1 row affected (0,01 sec)

mysql>
```


Tomate unos minutos para
entender el esquema.

<table>
  <tr>
    <td><img src="https://github.com/elarreglador/AD_UD04_03Tenis/blob/main/BD.png" alt="Esquema de base de datos" title="HomeScreen"></td>
  </tr>
</table>

## 2. MAPEA LA DB USANDO HIBERNATE

Configura un nuevo Proyecto y utiliza Hibernate para mapear la base de datos.

## 3. IMPLEMENTA EL PROGRAMA

Crea un programa con el siguiente menú:

1. Muestra un equipo por ID

2. Muestra un jugador por ID

3. Muestra los jugadores de un equipo existente

4. Crea un nuevo equipo

5. Crea un nuevo jugador asociado a un nuevo equipo

6. Crea un jugador en un equipo existente

7. Borra un jugador

8. Borra un equipo

9. Establece el salario de todos los jugadores de un equipo

10. Aumenta el salario de los mejores jugadores de un equipo en la temporada 07/08

11. Salir

## 1) Muestra un equipo por ID

Implementa el método muestraEquipo(). Preguntará por una ID y devolverá el equipo con la ID correspondiente por consola. Si no existe ningún equipo con esa ID, se deberá mostrar un mensaje.

```java
public static void muestraEquipo(Scanner teclado, SessionFactory sf) {
	/*
	 Preguntará por una ID y devolverá el equipo con la ID 
	 correspondiente por consola. Si no existe ningún equipo con 
	 esa ID, se deberá mostrar un mensaje.
	 */
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
		}
		
	} catch (Exception e) {
        System.err.println("Error al ejecutar la consulta: " + 
        		e.getMessage());
        e.printStackTrace();
    }
}
```

## 2) Muestra un jugador por ID

Implementa el método muestraJugador().Este preguntará por la ID de un jugador y mostrará la información por pantalla. Si no existe el jugador, se deberá mostrar un mensaje.

```java
public static void muestraJugador(Scanner teclado, SessionFactory sf) {
	/*
	 Preguntará por la ID de un jugador y mostrará la información por
	 pantalla. Si no existe el jugador, se deberá mostrar un mensaje.
	 */
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
```

## 3) Muestra los jugadores de un equipo existente

Implementa el método muestraJugadoresEnEquipo(). Preguntará por la ID de un equipo y mostrará a los jugadores asociados a este. Si no existe un equipo con esa ID, se deberá mostrar un mensaje.

```java
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
```

## 4) Crea un nuevo equipo

Implementa el método crearEquipo().Este preguntará los datos necesarios para crear el equipo y crear un objeto persistente en la base de datos.

```bash
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

private static Teams guardaEquipo(SessionFactory sf, String name, String division, 
		String city, String conference) {
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
```

## 5) Crea un nuevo jugador asociado a un nuevo equipo

Implementa el método crearJugadorYEquipo(). Este preguntará por los datos necesarios para crear el jugador y el equipo y creará un objeto persistente en la base de datos.

```bash
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
```

## 6) Crea un jugador en un equipo existente

Implementa el método crearJugadorEnEquipo(). Este preguntara por los datos necesarios para crear un jugador y la ID de un equipo. El objeto creado se hará persistente en la base de datos y si no existe ningún equipo con la ID introducida se deberá mostrar un mensaje.

```bash
public static void crearJugadorEnEquipo(Scanner teclado, 
		SessionFactory sf) {
	
	// pregunta por los datos necesarios para crear un jugador y la 
	// ID de un equipo. El objeto creado se hará persistente en la 
	// base de datos y si no existe ningún equipo con la ID 
	// introducida se deberá mostrar un mensaje.
	
	System.out.println("CREA UN JUGADOR EN UN EQUIPO EXISTENTE");
	System.out.print("Introduce nombre (ID) de EQUIPO: ");
	String nombreEquipo = teclado.nextLine();
	
	if ( !Teams.existe(sf, nombreEquipo) ) {
		System.out.println("El equipo " + nombreEquipo +
				" no existe.");
		return;
	}
	
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
	
	Transaction tx = null;
	try ( Session session = sf.openSession() ){
		tx = session.beginTransaction();
		// Obtiene el obj equipo
		Query<Teams> query = session.createQuery(
				"FROM Teams T " +
				"WHERE T.name = :nombreEquipo", Teams.class);
		query.setParameter("nombreEquipo", nombreEquipo);
		Teams equipo = query.uniqueResult();
		
		// Crear el nuevo jugador y guardar en BD
        Players jugador = new Players(code, equipo, name, 
        		origin, height, weight, position, salary);
        session.persist(jugador); 
        tx.commit();
	}
}
```

## 7) Borra un jugador

Implementa el método borrarJugador(). Este preguntara por una ID para seleccionar el jugador indicado y borrarlo de la base de datos. SI no existe ningún jugador con esa ID, se deberá mostrar un mensaje.

## 8) Borra un equipo

Implementa el método borrarEquipo(). Este preguntará por el ID de un equipo y lo borrará de la base de datos. Si no existe ningún equipo con ese ID, un mensaje deberá ser mostrado. Si existen jugadores asociados a ese equipo, este no se puede borrar hasta que se hayan borrado los jugadores, se deberá mostrar un mensaje para confirmar la eliminación de los jugadores.

## 9) Establece el salario de todos los jugadores de un equipo

Implementa el método establecerSalarioEquipo(). Este preguntará por el ID de un equipo y modificará el salario de los jugadores de éste a la cantidad que se establezca por pantalla. Si no existe ningún equipo con esa ID se deberá mostrar un mensaje por pantalla.

## 10) Aumenta el salario de los mejores jugadores de un equipo en la temporada 07/08

Implementa el método subirSalarioMejoresJugadoresEquipo(). Este preguntara por la ID de un equipo, por el porcentaje del aumento que se va aplicar, y cual es la estadística a tener en cuenta. El salario de los jugadores con los máximos en esa estadística se debe incrementar. Si no existe ningún equipo con esa ID se deberá mostrar un mensaje por pantalla.

Por ejemplo, si la ID del equipo es ‘Timberwolves’, el porcentaje de aumento es un 10% y la estadística seleccionada es ‘points per match’, el nuevo salario del jugador 7 – Al Jefferson pasará a ser 110000.

## 11) Salir

Cierra la app.

### Nota

Cuando implementes las funciones intenta crear funciones auxiliares para reutilizar código en común. Por ejemplo cuando borramos un equipo, la funcionalidad de borrar los jugadores se puede reutilizar. 

Recuerda que la creación de una SessionFactory consume recursos, créala solamente una vez. Las funciones no deben recibir Session como argumento, cada función debe abrir su sesión, hacer un commit de las transacciones y cerrar la sesión.

### Nota del alumno

La base de datos es de jugadores de la NBA, el repositorio se llama UD04_03Tenis, 🔥yo solo quiero ver el mundo arder🔥
