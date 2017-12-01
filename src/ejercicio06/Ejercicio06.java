package ejercicio06;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ejercicio05.Ejercicio5;

/**
 * ESte programa permite ver la información de una base de datos, así como
 * obtener información de las columnas y claves primarias o ajenas de una de sus
 * tablas, para obtener la información usa un método creado {@link Enum} el ejercicio 5
 * 
 * @author Joaquin Alonso Perianez
 * @version 01/12/2017 11:46
 *@see #Ejercicio5
 *
 */
public class Ejercicio06 {
	/**
	 * Esta constante se usará para referenciar claves primarias en el método
	 * {@link #getClaves(int, String, String, String)}
	 */
	final static int PRIMARY_KEY = 1;
	/**
	 * Esta constante se usará para referenciar claves ajenas en el método
	 * {@link #getClaves(int, String, String, String)}
	 */
	final static int FOREIGN_KEY = 2;

	public static void main(String[] args) {
		menu();
	}

	/**
	 * Este menu constará de varias opciones <list>
	 * <li>1.-Mostrar datos para sqlite</li>
	 * <li>2.-Mostrar datos para oracle</li>
	 * <li>3.-Mostrar datos para mysql</li>
	 * <li>4.-Salir</li> </list> Por cada opcion, excepto salir, mostrará la
	 * metadata de la base de datos y una lista de las tablas, después pedira el
	 * esquema, salvo en sqlite y pedirá una tabla, mostrará las columnas de esta
	 * tabla y después pedira un calor entero que deberían ser {@value #PRIMARY_KEY}
	 * o {@value #FOREIGN_KEY} y mostrará las claves primarias o ajenas de la base
	 * de datos
	 * 
	 * @see #getColumnaTabla(String, String, String) @ see
	 * {@link #getClaves(int, String, String, String)}
	 * @see Ejercicio5
	 * 
	 */
	private static void menu() {
		int opcion = 0;
		String tabla;
		String esquema;
		Scanner scString = new Scanner(System.in);
		int claves;
		do {

			System.out.println("--MENU--");
			System.out.println("1.-Mostrar datos de sqlite");
			System.out.println("2.-Mostrar datos de oracle");
			System.out.println("3.-Mostra datos de mysql");
			System.out.println("4.-Salir");
			Scanner sc = new Scanner(System.in);
			try {
				opcion = sc.nextInt();
			} catch (NumberFormatException e) {
				System.out.println("DEBES INTRODUCIR NUMEROS");
			}
			switch (opcion) {
			case 1:
				Ejercicio5.getInfoDB("sqlite");
				System.out.println("Introduce la tabla de la que quieres obtener datos");
				tabla = scString.nextLine();
				getColumnaTabla("sqlite", null, tabla);
				System.out.println(
						"Introduce 1 si quieres mostrar claves primarias y 2 si quieres mostrar claves ajenas");
				claves = sc.nextInt();
				getClaves(claves, "sqlite", null, tabla);
				break;
			case 2:
				Ejercicio5.getInfoDB("oracle");
				System.out.println("Introduce el esquema del que quieres mostrar datos");
				esquema = scString.nextLine();
				System.out.println("Introduce la tabla de la que quieres obtener datos");
				tabla = scString.nextLine();
				getColumnaTabla("oracle", esquema, tabla);
				System.out.println(
						"Introduce 1 si quieres mostrar claves primarias y 2 si quieres mostrar claves ajenas");
				claves = sc.nextInt();
				getClaves(claves, "oracle", esquema, tabla);
				break;
			case 3:
				Ejercicio5.getInfoDB("mysql");
				System.out.println("Introduce el esquema del que quieres mostrar datos");
				esquema = scString.nextLine();
				System.out.println("Introduce la tabla de la que quieres obtener datos");
				tabla = scString.nextLine();
				getColumnaTabla("mysql", esquema, tabla);
				System.out.println(
						"Introduce 1 si quieres mostrar claves primarias y 2 si quieres mostrar claves ajenas");
				claves = sc.nextInt();
				getClaves(claves, "mysql", esquema, tabla);

				break;
			case 4:
				System.out.println("Fin del programa");

				break;
			default:
				System.out.println("Te has equivocado");

				break;

			}
		} while (opcion != 4);

	}

	/**
	 * Muestra las columnas que tiene una tabla de una base de datos Obtendra la
	 * conexion con el metodo getConnection
	 * 
	 * 
	 * @param bd
	 *            sistema gestor de bases de datos usado para crear la base de datos
	 * @param esquema
	 *            esquema de la base de datos
	 * @param tabla
	 *            tabla de la que queremos obtener información
	 */
	public static void getColumnaTabla(String bd, String esquema, String tabla) {
		Connection conection = ejercicio05.Ejercicio5.getConnection(bd);
		try {
			DatabaseMetaData dbmd = conection.getMetaData();
			ResultSet columnas = null;
			columnas = dbmd.getColumns(null, esquema, tabla, null);
			System.out.println("Estas son las columnas para la tabla " + tabla);
			System.out.println("------------------------------------------");
			while (columnas.next()) {
				String nombreCol = columnas.getString("COLUMN_NAME");
				String tipoCol = columnas.getString("TYPE_NAME");
				String tamanyoCol = columnas.getString("COLUMN_SIZE");
				String nula = columnas.getString("IS_NULLABLE");
				System.out.println("Columna: " + nombreCol + ", Tipo: " + tipoCol + ", Tamaño: " + tamanyoCol
						+ ", ¿Puede ser Nula? " + nula);
			}
			conection.close();
		} catch (SQLException e) {
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene claves primarias o ajenas de una tabla de una base de datos El primer
	 * parámetro indica el tipo 1 para primarias y 2 para ajenas
	 * 
	 * @param tipo
	 *            tipo de claves a buscar {@link #PRIMARY_KEY} {@value #PRIMARY_KEY}
	 *            para claves primarias y {@link #FOREIGN_KEY} {@value #FOREIGN_KEY}
	 *            para claves ajenas
	 * @param bd
	 *            la base de datos
	 * @param esquema
	 *            el esquema a buscar
	 * @param tabla
	 *            la tabla en la que buscaremos las claves
	 * @see #PRIMARY_KEY
	 * @see #FOREIGN_KEY
	 */
	public static void getClaves(int tipo, String bd, String esquema, String tabla) {
		Connection conection = ejercicio05.Ejercicio5.getConnection(bd);
		try {
			DatabaseMetaData dbmd = conection.getMetaData();
			ResultSet rs = null;
			switch (tipo) {
			case PRIMARY_KEY:
				System.out.println("Estas son las claves primarias para la tabla " + tabla);
				rs = dbmd.getPrimaryKeys(null, esquema, tabla);
				while (rs.next()) {
					System.out.println("Clave primaria " + rs.getString("COLUMN_NAME"));
				}
				break;
			case FOREIGN_KEY:
				System.out.println("Estas son las claves ajenas de la tabla " + tabla);
				rs = dbmd.getExportedKeys(null, esquema, tabla);
				while (rs.next()) {
					String nombreFK = rs.getString("FKCOLUMN_NAME");
					String nombrePK = rs.getString("PKCOLUMN_NAME");
					String nombreTablaPK = rs.getString("PKTABLE_NAME");
					String nombreTablaFK = rs.getString("FKTABLE_NAME");
					System.out.println("Tabla PK: " + nombreTablaPK + ", CLave primaria: " + nombrePK);
					System.out.println("Tabla FK: " + nombreTablaFK + ", CLave ajena: " + nombreFK);
				}
				break;
			default:
				System.out.println("Valor de tipo equivocado, solo se admiten dos valores:\n" + "1 para Primary Key"
						+ "\n2 para Foreign Key");
				break;
			}
			conection.close();

		} catch (SQLException e) {
			System.out.println("ERROR de SQL");
			e.printStackTrace();
		}
	}
}
