package ejercicio04;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Principal {

	public static void main(String[] args) {
		System.out.println("Empleado con mayor salario ");
		select("apellido","salario");
	}

	private static void select(String campo1, String campo2) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/acadt","root","");
			Statement st= conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+campo1+", "+campo2+" FROM empleados WHERE salario=( SELECT max(salario) FROM empleados )");
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getInt(2));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}