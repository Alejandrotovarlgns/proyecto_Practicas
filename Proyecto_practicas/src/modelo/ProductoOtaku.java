package modelo;

public class ProductoOtaku {
    // Atributos privados que representan las propiedades del producto
    private int id;             // Identificador único del producto
    private String nombre;      // Nombre del producto
    private String categoria;   // Categoría a la que pertenece el producto
    private double precio;      // Precio del producto
    private int stock;          // Cantidad disponible en stock

    // Constructor vacío (por defecto), necesario para algunos frameworks o usos
    public ProductoOtaku() {}

    // Constructor completo con todos los atributos, útil para cargar productos ya existentes
    public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Constructor sin id, útil para crear productos nuevos antes de asignarles un id en BD
    public ProductoOtaku(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters para acceder y modificar los atributos privados

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getCategoria() { 
        return categoria; 
    }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
    }

    public double getPrecio() { 
        return precio; 
    }
    public void setPrecio(double precio) { 
        this.precio = precio; 
    }

    public int getStock() { 
        return stock; 
    }
    public void setStock(int stock) { 
        this.stock = stock; 
    }

    // Método para representar el producto como texto legible, útil para depuración o mostrar datos
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + categoria + ") - " + precio + " € - Stock: " + stock;
    }
}
