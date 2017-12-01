package ejercicio05;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Muestra la información de la base de datos sqlite y la base de datos oracle
 * 
 * @author Joaquin Alonso Perianez
 *
 */
public class Ejercicio5 {
	public static void main(String[] args) {
		System.out.println("====================");
		System.out.println("SQLITE");
		System.out.println("");
		getInfoDB("sqlite");
		System.out.println("====================");
		System.out.println("ORACLE");
		System.out.println(" ");
		getInfoDB("oracle");
	}

	/**
	 * El metodo devuelve una conexion dado un string que será el nombre de la base
	 * de datos. Los nombres aceptados son <list>
	 * <li>oracle</li>
	 * <li>mysql</li>
	 * <li>sqlite</li> </list> <b>Si da error en alguno ojo a los getConnection
	 * </b><br>
	 * </br>
	 * 
	 * @param baseDatos
	 *            nombre de la base de datos
	 * @return conexion obtenida
	 */
	public static Connection getConnection(String baseDatos) {
		Connection conexion = null;

		String database = baseDatos.toLowerCase();
		try {

			switch (database) {
			case "oracle":
				Class.forName("oracle.jdbc.OracleDriver");
				conexion = DriverManager.getConnection("jdbc:oracle:thin:acadt/12345@//localhost/XE");
				break;
			case "mysql":
				Class.forName("com.mysql.jdbc.Driver");
				conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");

				break;
			case "sqlite":
				Class.forName("org.sqlite.JDBC");
				conexion = DriverManager.getConnection("jdbc:sqlite:src/ejercicio01/ejercicio1.db");
				break;
			default:
				System.out.println("No se puede establecer la conexión a la base de datos");
				break;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("clase no encontrada");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error sql");
			e.printStackTrace();
		}
		return conexion;
	}

	/**
	 * Dado el nombre de un sistema gestor de bases de datos, llama al metodo
	 * {@link #getConnection(String)} que obtendra la conexion correcta Después
	 * obtiene y muestra la información de la base de datos y las tablas que tiene
	 * Si da error para oracle cambiar en getTables o en el getConnection Si da
	 * error para sqlite comprobar que la ruta este bien en el
	 * {@link #getConnection(String)}
	 * 
	 * @param baseDatos
	 *            nombre del sistema gestor de bases de datos
	 * @see #getConnection(String)
	 * 
	 */
	public static void getInfoDB(String baseDatos) {
		Connection conexion = getConnection(baseDatos);
		try {
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet rs = null;
			String nombre = dbmd.getDatabaseProductName();
			String driver = dbmd.getDriverName();
			String url = dbmd.getURL();
			String usuario = dbmd.getUserName();

			System.out.println("INFORMACION DE LA BASE DE DATOS");
			System.out.println("================================");
			System.out.println("Nombre : " + nombre);
			System.out.println("Drriver : " + driver);
			System.out.println("URL : " + url);
			System.out.println("Usuario: " + usuario);
			System.out.println("\nTABLAS DE LA BASE DE DATOS");
			System.out.println("\t================");
			if (baseDatos.equalsIgnoreCase("sqlite")) {
				rs = dbmd.getTables(null, null, null, null);
			} else {
				rs = dbmd.getTables(null, "ACADT", null, null);
			}
			boolean hay = false;
			while (rs.next()) {
				String catalogo = rs.getString(1);
				String esquema = rs.getString(2);
				String tabla = rs.getString(3);
				String tipo = rs.getString(4);
				System.out.println(tipo + " Catalogo: " + catalogo + " Esquema: " + esquema + " Tabla: " + tabla);
				hay = true;
			}
			if (!hay) {
				System.out.println("NO SE HAN ENCONTRADO TABLAS PARA LA BASE DE DATOS");
				// Si muestra este mensaje hay que cambiar en el getTables o en el driver
				// manager el nombre de la base de datos
				// En ese caso controlar mayusculas y minusculas
			}
			conexion.close();

		} catch (SQLException e) {
			System.out.println("\t\tERROR DE SQL");
			e.printStackTrace();
		}
	}
}
