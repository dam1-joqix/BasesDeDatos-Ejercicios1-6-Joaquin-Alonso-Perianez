package ejercicio06;

import java.sql.Connection;

public class Ejercicio06 {
	public static void main(String[] args) {
		
	}
	/**
	 *Muestra las columnas que tiene una tabla de una base de datos
	 *Obtendra la conexion con el metodo getConnection
	 * @param bd
	 * @param esquema
	 * @param tabla
	 */
	public static void getColumnaTabla(String bd, String esquema, String tabla) {
		Connection conextion= ejercicio05.Ejercicio5.getConnection(bd);
	}
	public static void getClaves(int tipo,String bd,String esquema,String tabla) {
		
	}
}
	
