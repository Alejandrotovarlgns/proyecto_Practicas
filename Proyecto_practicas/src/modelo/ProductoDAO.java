package modelo;

import java.sql.*;         // Importa clases para manejar conexión y sentencias SQL
import java.util.*;        // Importa utilidades como listas

public class ProductoDAO {

    // Método para agregar un nuevo producto a la base de datos
    public void agregarProducto(ProductoOtaku producto) {
        // Sentencia SQL con parámetros para insertar un producto
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar valores al PreparedStatement desde el objeto producto
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            // Ejecutar la inserción en la base de datos
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en caso de fallo
        }
    }

    // Método para obtener un producto por su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Asignar el ID al parámetro
            ResultSet rs = stmt.executeQuery();  // Ejecutar consulta

            // Si se encuentra el producto, se crea y retorna el objeto ProductoOtaku
            if (rs.next()) {
                return new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Retorna null si no encuentra el producto
    }

    // Método para obtener la lista de todos los productos de la base de datos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorrer el ResultSet y agregar cada producto a la lista
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos; // Devuelve la lista con todos los productos
    }

    // Método para actualizar los datos de un producto existente
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar nuevos valores al producto en la sentencia SQL
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            // Ejecutar actualización y retornar true si afectó alguna fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo error o no se actualizó nada
    }

    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Asignar el ID a eliminar
            // Ejecutar eliminación y retornar true si se borró al menos un registro
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si no se pudo eliminar o hubo error
    }

    // Método para buscar productos cuyo nombre contenga una cadena dada
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Usar comodines % para búsqueda parcial
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            // Añadir a la lista todos los productos que cumplan la condición
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos; // Retorna la lista con los productos encontrados
    }
}
