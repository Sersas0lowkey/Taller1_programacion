import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Categorias Disponibles ---");
            System.out.println("[1] Computacion    [2] Oficina       [3] Música");
            System.out.println("[4] Ropa           [5] Accesorios    [6] Coleccionables");
            System.out.println("[7] Hogar          [8] Electrónica   [9] Papeleria");
            System.out.println("[10] Muebles       [0] SALIR");
            System.out.print("\nIngrese la categoria a buscar: ");

            opcion = sc.nextInt();

            if (opcion == 0) {
                System.out.println("Programa finalizado.");
                break;
            }

            String[] categorias = {
                    "Computacion", "Oficina", "Musica", "Ropa", "Accesorios",
                    "Coleccionables", "Hogar", "Electronica", "Papeleria", "Muebles"
            };

            if (opcion < 1 || opcion > categorias.length) {
                System.out.println("Error: Categoria inexistente.");
                continue;
            }

            String categoriaTexto = categorias[opcion - 1];
            ArrayList<String> lineas = new ArrayList<>();

            // Lectura del archivo (con ruta src/ por tu configuración de IntelliJ)
            try (BufferedReader br = new BufferedReader(new FileReader("src/inventario.txt"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.isBlank()) lineas.add(linea);
                }
            } catch (IOException e) {
                System.out.println("Error crítico: No se pudo abrir 'inventario.txt'");
                continue;
            }

            // LLAMADA RECURSIVA
            String resultado = buscarMasBarato(lineas, categoriaTexto, 0, null, Integer.MAX_VALUE);

            if (resultado == null) {
                System.out.println("No hay productos en la categoria: " + categoriaTexto);
            } else {
                System.out.println(">>> PRODUCTO MÁS BARATO: " + resultado);
            }
        }
        sc.close();
    }

    // MÉTODO RECURSIVO
    public static String buscarMasBarato(ArrayList<String> lineas, String categoria, int index, String mejorNombre, int mejorPrecio) {
        // Caso base: Se terminaron las líneas
        if (index >= lineas.size()) {
            return mejorNombre;
        }

        try {
            String[] partes = lineas.get(index).split(",");
            String nombre = partes[1].trim();
            String cat = partes[2].trim();
            int precio = Integer.parseInt(partes[3].trim());

            // Comparación de lógica
            if (cat.equalsIgnoreCase(categoria) && precio < mejorPrecio) {
                mejorPrecio = precio;
                mejorNombre = nombre;
            }
        } catch (Exception e) {
            // Salta líneas mal formateadas sin romper el programa
        }

        // LLAMADA RECURSIVA: Avanza al siguiente índice (index + 1)
        return buscarMasBarato(lineas, categoria, index + 1, mejorNombre, mejorPrecio);
    }
}