package ejercicio03;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

/**
 * ESte programa hará una consulta select a la tabla empleados de la base de
 * datos mysql acadt situada en localhost
 * 
 * @author Joaquin Alonso Perianez
 *
 */
public class Principal {

	public static void main(String[] args) {
		select("apellido", "oficio", "salario", 10);
	}

	/**
	 * Realiza la sigiente consulta:
	 * <pre>
	 * SELECT campo, campo2, campo3 FROM empleados where dept_no=num_dpto
	 * </pre>
	 * <pre>
	 * campo1 y campo2 deberán ser campos de la base de datos que devuelvan una cadena 
	 * y campo3 deberá ser un campo que devuelva un entero
	 * </pre> 
	 * @param campo1
	 *            primer campo de la consulta
	 * @param campo2
	 *            segundo campo de la consulta
	 * @param campo3
	 *            tercer campo de la consulta
	 * @param num_dpto
	 *            el numero de departamento a buscar
	 */
	private static void select(String campo1, String campo2, String campo3, int num_dpto) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt", "root", "");
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT " + campo1 + ", " + campo2 + ", " + campo3 + " FROM empleados where dept_no=" + num_dpto);
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
