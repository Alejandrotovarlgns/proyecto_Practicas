package modelo; // Define el paquete al que pertenece esta clase (modelo)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; // Importaciones necesarias para manejar conexiones SQL

public class DatabaseConnection {

    // Constante con la URL de conexión a la base de datos MySQL (nombre de la base de datos: akihabara_db)
    private static final String URL = "jdbc:mysql://localhost:3306/akihabara_db";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario de la base de datos
    private static final String PASSWORD = "root123"; // ⚠️ Importante mantener segura esta información

    // Método estático que establece y devuelve una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        // Usa el DriverManager para obtener la conexión con los parámetros definidos
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
