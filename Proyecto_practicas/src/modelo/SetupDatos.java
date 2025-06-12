package modelo;

import java.util.List;

public class SetupDatos {
    // Instancia estática de ProductoDAO para acceder a los métodos de acceso a datos
    private static ProductoDAO productoDAO = new ProductoDAO(); // ✅ inicializado aquí

    // Método estático para cargar datos iniciales en la base de datos
    public static void cargarDatosIniciales() {
        // Obtener la lista de productos actuales en la base de datos
        List<ProductoOtaku> productos = productoDAO.obtenerTodosLosProductos();
        
        // Si la lista está vacía, significa que no hay productos cargados
        if (productos.isEmpty()) {
            // Añadir varios productos predeterminados para poblar la base de datos inicialmente
            productoDAO.agregarProducto(new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8));
            productoDAO.agregarProducto(new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20));
            productoDAO.agregarProducto(new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15));
            
            // Mensaje informativo indicando que los datos se han cargado
            System.out.println("Datos iniciales cargados.");
        } else {
            // Si ya hay productos en la base, indicar que no se necesita cargar datos
            System.out.println("La base de datos ya contiene productos.");
        }
    }
}
