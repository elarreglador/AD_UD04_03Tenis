import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory instance = null;
	
	
	// Constructor
	private HibernateUtil() {}
	
	
	// Devuelve instancia singleton de la sesion de conexion a la BD
	public static SessionFactory getSessionFactory() {
		if (instance == null) {
			instance = new Configuration().configure().buildSessionFactory();
		}
		return instance;
	}
	
	
	public static boolean verificarConexion(SessionFactory sessionFactory) {
        // Intentamos abrir una sesión
        try (Session session = sessionFactory.openSession()) {
            // Ejecutamos la consulta SELECT 1 para verificar la conexión
            session.createNativeQuery("SELECT 1").getSingleResult();
            return true; // Conexión exitosa
        } catch (Exception e) {
            System.out.println("Error al intentar conectarse a la base de datos: " + e.getMessage());
            return false; // Conexión fallida
        } finally {
            sessionFactory.close();
        }
    }

	
	
	
	// Main para verificar funcionamiento
	public static void main(String[] args) {
		
		// Crea una instancia llamando a la funcion 
		SessionFactory sf = HibernateUtil.getSessionFactory();

		// Verifica conexion
		System.out.println("Conexion disponible: " + HibernateUtil.verificarConexion(sf));
		
	}
	
	
	


}
