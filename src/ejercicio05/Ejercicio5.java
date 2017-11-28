package ejercicio05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Ejercicio5 {
	public static void main(String[] args) {

	}

	/**
	 * El metodo devuelve una conexion dado un string que será el nombre de la base
	 * de datos. Los nombres aceptados son <list>
	 * <li>oracle</li>
	 * <li>mysql</li>
	 * <li>sqlite</li> </list>
	 * 
	 * @param baseDatos
	 *            nombre de la base de datos
	 * @return conexion obtenida
	 */
	public static Connection getConnection(String baseDatos) {
		Connection conexion = null;
		Properties propiedades = new Properties();
		FileInputStream fis = null;
		String database = baseDatos.toLowerCase();
		try {
			fis = new FileInputStream("propiedades.properties");
			switch (database) {
			case "oracle":
				Class.forName(propiedades.getProperty("driver-" + database));
				conexion = DriverManager.getConnection(propiedades.getProperty("conexion-" + database));
				break;
			case "mysql":
				Class.forName(propiedades.getProperty("driver-" + database));
				conexion = DriverManager.getConnection(propiedades.getProperty("conexion-" + database),
						propiedades.getProperty("usuario-" + database), "");
				break;
			case "sqlite":
				Class.forName(propiedades.getProperty("driver-" + database));
				conexion = DriverManager.getConnection(propiedades.getProperty("conexion-" + database));
				break;
			default:
				System.out.println("No se puede establecer la conexión a la base de datos");
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return conexion;
	}
	
	public static void getInfoDB(String baseDatos) {
		Connection conexion=getConnection("baseDatos");
		try {
			DatabaseMetaData dbmd=conexion.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
