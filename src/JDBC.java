import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    private Connection conexion;

    public void abrirConexion(String bd, String servidor, String usuario, String password) {
        try {
            String url = String.format("jdbc:mariadb://%s:33066/%s", servidor, bd);
            this.conexion = DriverManager.getConnection(url, usuario, password);// Establecemos la conexión con la BD
            if (this.conexion != null)
                System.out.println("Conectado a la base de datos " + bd + " en " + servidor);
            else
                System.out.println("No se ha conectado a la base de datos " + bd + " en " + servidor);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getLocalizedMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código error: " + e.getErrorCode());
        }
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getLocalizedMessage());
        }
    }

    public void consultaAlumnos2(String bd) throws SQLException {
        String query = "select * from aulas"; // Consulta a ejecutar
        Statement stmt = this.conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query); // Se ejecuta la consulta
        while (rs.next()) { // Mientras queden filas en rs (el método next devuelve true) recorremos las
                            // filas
            System.out.println(rs.getInt(1) + "\t" + // Se obtiene datos en función del número de columna
                    rs.getString("nombreAula") + "\t" + rs.getInt("puestos")); // o de su nombre
        }
        stmt.close(); // Se cierra el Statement
    }

    // Ejercicio 1
    public void consultaAlumnos(String nombre) {
        String query = "select * from alumnos WHERE nombre LIKE '%" + nombre + "%'";
        try (Statement stmt = this.conexion.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            int cont = 0;
            while (rs.next()) {
                System.out.printf("%-2d\t%-10s\t%-10s\t%-4d\t%-4d\n", rs.getInt("codigo"), rs.getString("nombre"),
                        rs.getString("apellidos"), rs.getInt("altura"), rs.getInt("aula"));
                cont++;
            }
            stmt.close();
            System.out.println("Número de resultados: " + cont);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    // Ejercicio 2
    public void altaAlumno(int codigo, String nombre, String apellidos, int altura, int aula) {
        try (Statement sta = this.conexion.createStatement()) {
            int filasAfectadas = sta.executeUpdate(String.format("INSERT INTO alumnos VALUES(%d, '%s', '%s', %d, %d)",
                    codigo, nombre, apellidos, altura, aula));
            System.out.println("Filas insertadas: " + filasAfectadas);
            sta.close();
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    public void altaAsignatura(int codigo, String nombre) {
        try (Statement sta = this.conexion.createStatement()) {
            int filasAfectadas = sta
                    .executeUpdate(String.format("INSERT INTO asignaturas VALUES(%d, '%s')", codigo, nombre));
            System.out.println("Filas insertadas: " + filasAfectadas);
            sta.close();
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    // Ejercicio 3
    public void bajaAlumno(int codigo) {
        try (Statement sta = this.conexion.createStatement()) {
            int filasAfectadas = sta.executeUpdate("DELETE FROM alumnos WHERE codigo = " + codigo);
            System.out.println("Filas afectadas: " + filasAfectadas);
            sta.close();
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    public void bajaAsignatura(int codigo) {
        try (Statement sta = this.conexion.createStatement()) {
            int filasAfectadas = sta.executeUpdate("DELETE FROM asignaturas WHERE cod = " + codigo);
            System.out.println("Filas afectadas: " + filasAfectadas);
            sta.close();
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    // Ejercicio 4
      public void aulaConAlumnos() {
        String query = "SELECT * FROM aulas JOIN alumnos";
        try (Statement stmt = this.conexion.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            int cont = 0;
            while (rs.next()) {
                System.out.printf("%-2d\t%-10s\t%-2d,\t%-2d\t%-10s\t%-10s\t%-2d\t%-2d", rs.getInt("numero"),
                        rs.getString("nombreAula"), rs.getInt("puestos"), rs.getInt("codigo"), rs.getString("nombre"),
                        rs.getString("apellidos"), rs.getInt("altura"), rs.getInt("aula"));
                cont++;
            }
            stmt.close();
            System.out.println("Número de resultados: " + cont);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    public void asignaturasSinAlumnos() {
        String query = "SELECT nombre FROM asignaturas ";
        try (Statement stmt = this.conexion.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            int cont = 0;
            System.out.println("Nombre de las asignaturas:");
            while (rs.next()) {
                System.out.printf("%-10s\n", rs.getString("nombre"));
                cont++;
            }
            stmt.close();
            System.out.println("Número de resultados: " + cont);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

}
