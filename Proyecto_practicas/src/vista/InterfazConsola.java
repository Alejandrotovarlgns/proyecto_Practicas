package vista;

import java.util.List;
import java.util.Scanner;

import modelo.ProductoOtaku;
import modelo.ProductoDAO;
import modelo.LlmService;

public class InterfazConsola {
    private Scanner sc = new Scanner(System.in);
    private ProductoDAO productoDAO = new ProductoDAO(); // Acceso a la base de datos de productos

    // Muestra el menú principal al usuario
    public void mostrarMenuPrincipal() {
        System.out.println("\n--- AKIHABARA MARKET ---");
        System.out.println("1. Añadir producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Ver todos los productos");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Generar descripción de producto con IA");
        System.out.println("7. Sugerir categoría con IA");
        System.out.println("8. Salir");
        System.out.print("Elige una opción: ");
    }

    // Lee desde consola los datos para crear un producto nuevo
    public ProductoOtaku leerProductoNuevo() {
        System.out.println("\nIntroduce los datos del nuevo producto:");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Categoría: ");
        String categoria = sc.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());

        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    // Lee un ID desde consola para buscar un producto
    public int leerIdProducto() {
        System.out.print("\nIntroduce el ID del producto: ");
        return Integer.parseInt(sc.nextLine());
    }

    // Muestra por consola los datos de un producto
    public void mostrarProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.println("\n" + producto.toString());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Muestra una lista de productos (si hay) o mensaje si está vacía
    public void mostrarListaProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("\nListado de productos:");
            productos.forEach(p -> System.out.println(p));
        }
    }

    // Lee datos para actualizar un producto, dejando campos vacíos si no se quiere cambiar
    public ProductoOtaku leerProductoActualizado(ProductoOtaku original) {
        System.out.println("\nActualiza los datos (deja vacío para mantener el valor actual)");

        System.out.print("Nombre [" + original.getNombre() + "]: ");
        String nombre = sc.nextLine();
        if (!nombre.isEmpty()) original.setNombre(nombre);

        System.out.print("Categoría [" + original.getCategoria() + "]: ");
        String categoria = sc.nextLine();
        if (!categoria.isEmpty()) original.setCategoria(categoria);

        System.out.print("Precio [" + original.getPrecio() + "]: ");
        String precio = sc.nextLine();
        if (!precio.isEmpty()) original.setPrecio(Double.parseDouble(precio));

        System.out.print("Stock [" + original.getStock() + "]: ");
        String stock = sc.nextLine();
        if (!stock.isEmpty()) original.setStock(Integer.parseInt(stock));

        return original;
    }

    // Muestra un mensaje genérico por consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Lee una opción numérica, devuelve -1 si no es válida
    public int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // --- NUEVOS MÉTODOS CON IA ---

    // Genera una descripción del producto con IA consultando LlmService
    public void generarDescripcionIA() {
        System.out.print("Introduce el ID del producto: ");
        int id = Integer.parseInt(sc.nextLine());
        ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);

        if (producto != null) {
            String prompt = "Genera una descripción de marketing breve y atractiva para el producto otaku: " 
                            + producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
            String respuesta = LlmService.enviarPrompt(prompt);
            System.out.println("Descripción generada por IA:\n" + respuesta);
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Sugiere una categoría para un producto nuevo usando IA
    public void sugerirCategoriaIA() {
        System.out.print("Introduce el nombre del nuevo producto: ");
        String nombre = sc.nextLine();

        String prompt = "Para un producto otaku llamado '" + nombre + "', sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
        String respuesta = LlmService.enviarPrompt(prompt);
        System.out.println("Categoría sugerida por IA:\n" + respuesta);
    }
}

