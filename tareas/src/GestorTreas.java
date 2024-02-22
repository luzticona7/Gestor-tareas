import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GestorTreas {
    private ArrayList<Tarea> tareas;

    public GestorTreas() {
        this.tareas = new ArrayList<>();
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public void eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            this.tareas.remove(indice);
        } else {
            System.out.println("Tarea no Encontrada");
        }
    }

    public void marcarComoCompletada(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            Tarea tarea = this.tareas.get(indice);
            tarea.marcarComoCompletada();
        } else {
            System.out.println("Tarea no Encontrada");
        }
    }

    public void mostrarTareas() {
    	System.out.println("|-----------------------------------------------|");
        System.out.println("         Lista de tareas:         ");
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
        	System.out.println("|-------------------------------------------|");
            System.out.println(i + ". " + tarea.getDescripcion() + " : Completada: " + tarea.isCompletada());
        	System.out.println("|-------------------------------------------|");
        }
    }

    public void generarReporte(String estado, String archivo) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write("Reporte de tareas " + estado + ":\n");
            boolean encontrado = false;
            for (Tarea tarea : tareas) {
                if (estado.equals("en_curso") && !tarea.isCompletada()) {
                    writer.write("- " + tarea.getDescripcion() + "\n");
                    encontrado = true;
                } else if (estado.equals("completadas") && tarea.isCompletada()) {
                    writer.write("- " + tarea.getDescripcion() + "\n");
                }
            }
            if (!encontrado && estado.equals("en_curso")) {
                writer.write("No hay tareas en curso\n");
            }
            System.out.println("Reporte generado: " + archivo);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }    
}