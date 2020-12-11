public class App {
    public static void main(String[] args) throws Exception {
        JDBC jdbc = new JDBC();
        jdbc.abrirConexion("add","localhost","root","qwerty1234");
        //jdbc.altaAlumno("add",10, "kokito", "kokotron", 100, 20);
        jdbc.altaAsignatura(9, "AD");
        jdbc.consultaAlumnos();
        jdbc.cerrarConexion();
    }
}
