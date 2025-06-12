package controlador; // Define el paquete al que pertenece esta clase (controlador)

// Importaciones necesarias de clases de otros paquetes del proyecto
import modelo.ProductoDAO;
import modelo.SetupDatos;
import modelo.ProductoOtaku;
import vista.InterfazConsola;

import java.util.List; // Importa la clase List de Java para trabajar con listas

public class MainApp {
    public static void main(String[] args) {
        // Crea una instancia de la vista para interacción por consola
        InterfazConsola vista = new InterfazConsola();

        // Crea una instancia del DAO que gestiona operaciones con productos
        ProductoDAO dao = new ProductoDAO();

        // Carga datos iniciales usando una clase auxiliar (como productos de prueba)
        SetupDatos.cargarDatosIniciales();

        // Variable para controlar el bucle del menú
        boolean salir = false;

        // Bucle principal del menú hasta que el usuario decida salir
        while (!salir) {
            // Muestra el menú principal en consola
            vista.mostrarMenuPrincipal();

            // Lee la opción elegida por el usuario
            int opcion = vista.leerOpcion();

            // Evalúa la opción mediante un switch
            switch (opcion) {
                case 1: // Añadir producto
                    // Solicita al usuario los datos del nuevo producto
                    ProductoOtaku nuevo = vista.leerProductoNuevo();
                    // Añade el nuevo producto a la base de datos (o lista)
                    dao.agregarProducto(nuevo);
                    // Muestra mensaje de confirmación
                    vista.mostrarMensaje("Producto añadido con éxito.");
                    break;

                case 2: // Consultar por ID
                    // Solicita un ID al usuario
                    int idBuscar = vista.leerIdProducto();
                    // Busca el producto por ID
                    ProductoOtaku producto = dao.obtenerProductoPorId(idBuscar);
                    // Muestra los datos del producto encontrado (o nulo)
                    vista.mostrarProducto(producto);
                    break;

                case 3: // Mostrar todos
                    // Obtiene la lista completa de productos
                    List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
                    // Muestra todos los productos por consola
                    vista.mostrarListaProductos(productos);
                    break;

                case 4: // Actualizar
                    // Solicita el ID del producto a actualizar
                    int idActualizar = vista.leerIdProducto();
                    // Busca el producto existente
                    ProductoOtaku existente = dao.obtenerProductoPorId(idActualizar);
                    if (existente != null) {
                        // Solicita al usuario los nuevos datos del producto
                        ProductoOtaku actualizado = vista.leerProductoActualizado(existente);
                        // Intenta actualizar el producto
                        if (dao.actualizarProducto(actualizado)) {
                            vista.mostrarMensaje("Producto actualizado correctamente.");
                        } else {
                            vista.mostrarMensaje("Error al actualizar el producto.");
                        }
                    } else {
                        // El producto con ese ID no existe
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;

                case 5: // Eliminar
                    // Solicita el ID del producto a eliminar
                    int idEliminar = vista.leerIdProducto();
                    // Intenta eliminarlo
                    if (dao.eliminarProducto(idEliminar)) {
                        vista.mostrarMensaje("Producto eliminado con éxito.");
                    } else {
                        vista.mostrarMensaje("Error al eliminar el producto.");
                    }
                    break;

                case 6: // ✅ Generar descripción con IA
                    // Llama a la función que genera descripción con inteligencia artificial
                    vista.generarDescripcionIA();
                    break;

                case 7: // ✅ Sugerir categoría con IA
                    // Llama a la función que sugiere una categoría con inteligencia artificial
                    vista.sugerirCategoriaIA();
                    break;

                case 8: // Salir
                    // Cambia la condición para salir del bucle
                    salir = true;
                    // Muestra mensaje de despedida
                    vista.mostrarMensaje("¡Gracias por usar Akihabara Market!");
                    break;

                default:
                    // Caso por defecto si se introduce una opción no válida
                    vista.mostrarMensaje("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}

