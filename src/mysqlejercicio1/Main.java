/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlejercicio1;

import java.sql.*;
import java.util.Scanner;
import static mysqlejercicio1.Methods.*;

/**
 *
 * @author DAW
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String leftAlignFormat = "\t|                %-20s                   |";

        Scanner sc = new Scanner(System.in);
        boolean entryOk = false;
        int id = 0;
        String nombre = null;
        String descripcion = null;

        Categorias categoria = new Categorias(id, nombre, descripcion);

        Conexion login = new Conexion();
        Connection con = null;
        PreparedStatement stmt = null;
        int retorno = 0;

        try {
            con = login.conectar();
            System.out.println("\tConexión establecida\n\n");

            System.out.println("\t" + line(60, "-"));
            String textoAux = String.format(leftAlignFormat, "REGISTRO DE CATEGORIAS ");
            System.out.println(textoAux);
            System.out.println("\t" + line(60, "-"));

            // captura por teclado de id - num entero
            do {
                try {
                    System.out.print("\tidCategoría: ");
                    categoria.setId(Integer.parseInt(sc.nextLine()));
                    if (categoria.getId() < 1) {
                        Error error1 = new Error("Debe ser mayor que cero");
                        throw error1;
                    }
                    entryOk = true;
                } catch (NumberFormatException err) {
                    System.out.println("\tDato ingresado no válido. " + err.getMessage());
                    entryOk = false;
                } catch (Exception err) {
                    System.out.println("\tDato ingresado no válido. " + err.getMessage());
                    entryOk = false;
                }

            } while (!entryOk);
/*
            // captura por teclado de Nombre - String
            do {
                try {
                    System.out.print("\tNombre: ");
                    categoria.setNombre(sc.nextLine());
                    if (categoria.getNombre().length() < 4) {
                        Error error2 = new Error("\t\tAl menos 4 caracteres");
                        throw error2;
                    }
                    entryOk = true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    entryOk = false;
                }

            } while (!entryOk); //Categorias categoria = new 

            // captura por teclado de Description - String
            do {
                try {
                    System.out.print("\tDescripción: ");
                    categoria.setDescripcion(sc.nextLine());
                    if (categoria.getDescripcion().length() < 10) {
                        Error error1 = new Error("\t\tAl menos 10 caracteres");
                        throw error1;
                    }
                    entryOk = true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    entryOk = false;
                }

            } while (!entryOk);

            // INSERCION DE DATOS EN BASE DE DATOS
            stmt = con.prepareStatement("INSERT INTO Categorias VALUES(?,?,?)");
            stmt.setInt(1, categoria.getId());
            stmt.setString(2, categoria.getNombre());
            stmt.setString(3, categoria.getDescripcion());
            retorno = stmt.executeUpdate();
            if (retorno > 0) {
                System.out.println("\t"+retorno + " registro ejecutado");
            }
      */    
            //CONSULTA DE LOS DATOS INSERTADOS
            stmt = con.prepareStatement("SELECT * FROM Categorias WHERE idCategoria=?");
            stmt.setInt(1, categoria.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " \t" + rs.getString(3));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            //cierre de stmt 
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception err4) {
                System.out.println(err4.getMessage());
            }
            //desconexion base de datos
            try {
                login.desconectar(con);
                System.out.println("\tConexión cerrada");
            } catch (Exception err5) {
                System.out.println("\tBase de datos aun conectada. " + err5.getMessage());
            }
        }
    }

}
