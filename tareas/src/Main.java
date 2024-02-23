import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String archivoTareas = "tareas.txt";
        GestorTreas gestor = new GestorTreas();
        ArrayList<Tarea> tareas = cargarTareas(archivoTareas);
        gestor.setTareas(tareas);
        Scanner scanner = new Scanner(System.in);
        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("---------------------------------------------");
            System.out.println("                    Menú:                    |");
            System.out.println("1. Agregar nueva tarea                       |");
            System.out.println("2. Marcar tarea como completada              |");
            System.out.println("3. Eliminar tarea                            |");
            System.out.println("4. Generar reporte de tareas en curso        |");
            System.out.println("5. Generar reporte de tareas completadas     |");
            System.out.println("6. Generar reporte de tareas ingresadas      |");
            System.out.println("7. Salir                                     |");
            System.out.println("---------------------------------------------|");
            System.out.print("Seleccione una opción:   ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la nueva tarea: ");
                    String descripcion = scanner.nextLine();
                    gestor.agregarTarea(new Tarea(descripcion));
                    break;
                case 2:
                    gestor.mostrarTareas();
                    System.out.print("Ingrese el número de tarea a marcar como completada: ");
                    int indiceCompletada = scanner.nextInt();
                    gestor.marcarComoCompletada(indiceCompletada);
                    break;
                case 3:
                    gestor.mostrarTareas();
                    System.out.print("Ingrese el número de la tarea a eliminar: ");
                    int indiceEliminar = scanner.nextInt();
                    gestor.eliminarTarea(indiceEliminar);
                    break;
                case 4:
                    gestor.generarReporte("en_curso", "reporte_en_curso.txt");
                    break;
                case 5:
                    gestor.generarReporte("completadas", "reporte_completadas.txt");
                    break;
                case 6:
                    System.out.println("La lista de tareas general es: ");
                    gestor.reportTarea();
                    break;
                case 7:
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

        guardarTareas(archivoTareas, gestor.getTareas());

        scanner.close();
    }

    public static ArrayList<Tarea> cargarTareas(String archivo) {
        ArrayList<Tarea> tareas = new ArrayList<>();
        try {
            File file = new File(archivo);
            if (!file.exists()) {
                    if (file.createNewFile()) {
                        System.out.println("Archivo creado exitosamente.");
                    } else {
                        System.out.println("Error al crear el archivo.");
                    }
                    return tareas;
                }

                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(";");
                    String descripcion = datos[0];
                    boolean completada = Boolean.parseBoolean(datos[1]);
                    Tarea tarea = new Tarea(descripcion);
                    if (completada) {
                        tarea.marcarComoCompletada();
                    }
                    tareas.add(tarea);
                }
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tareas;
    }

    public static void guardarTareas(String archivo, ArrayList<Tarea> tareas) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (Tarea tarea : tareas) {
                writer.write(tarea.getDescripcion() + ";" + tarea.isCompletada() + "\n");
            }
            System.out.println("Tareas guardadas en: " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

}