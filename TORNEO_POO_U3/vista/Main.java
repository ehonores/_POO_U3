package vista;

import controlador.ControladorTorneo;
import excepciones.NumeroEquiposInvalidoException;
import excepciones.SorteoInvalidoException;
import modelo.Equipo;
import modelo.Partido;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControladorTorneo controlador = new ControladorTorneo();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=== GESTIÓN DE TORNEO - FÚTBOL PROFESIONAL (MVC) ===");
            System.out.println("1. Ingresar equipos manualmente");
            System.out.println("2. Cargar equipos por defecto (16 equipos rápidos)");
            System.out.println("3. Ver equipos inscritos");
            System.out.println("4. Ejecutar sorteo recursivo de etapas");
            System.out.println("5. Guardar estado del torneo (Serialización)");
            System.out.println("6. Cargar estado del torneo (Deserialización)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        controlador.limpiarEquipos();
                        System.out.print("\n¿Cuántos equipos va a registrar? (Debe ser potencia de 2: 2, 4, 8 o 16): ");
                        int cantidad = Integer.parseInt(scanner.nextLine());
                        
                        if (cantidad != 2 && cantidad != 4 && cantidad != 8 && cantidad != 16) {
                            System.out.println("⚠️ Cantidad no válida para eliminación directa. Debe ser 2, 4, 8 o 16.");
                            break;
                        }

                        for (int i = 1; i <= cantidad; i++) {
                            System.out.print("Ingrese el nombre del equipo " + i + ": ");
                            String nombreEquipo = scanner.nextLine();
                            controlador.agregarEquipo(i, nombreEquipo);
                        }
                        System.out.println(" ¡Equipos registrados exitosamente!");
                        break;

                    case 2:
                        controlador.limpiarEquipos();
                        for (int i = 1; i <= 16; i++) {
                            controlador.agregarEquipo(i, "Equipo " + i);
                        }
                        System.out.println(" Se han cargado 16 equipos por defecto automáticamente.");
                        break;

                    case 3:
                        List<Equipo> inscritos = controlador.obtenerListaEquipos();
                        if (inscritos.isEmpty()) {
                            System.out.println("\n⚠️ No hay equipos inscritos todavía.");
                        } else {
                            System.out.println("\n--- LISTA DE EQUIPOS INSCRITOS (" + inscritos.size() + ") ---");
                            for (Equipo e : inscritos) {
                                System.out.println("- " + e);
                            }
                        }
                        break;

                    case 4:
                        try {
                            controlador.procesarSorteo();
                            System.out.println("\n ¡Sorteo recursivo completado con éxito!");
                            List<List<Partido>> fases = controlador.consultarEtapasTorneo();
                            int numeroFase = 1;
                            for (List<Partido> partidosFase : fases) {
                                System.out.println("\n--- Fase / Llave " + numeroFase++ + " ---");
                                for (Partido p : partidosFase) {
                                    System.out.println("[ Partido ] " + p);
                                }
                            }
                        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
                            System.err.println(" Error en las reglas del sorteo: " + e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            controlador.exportarDatosBinarios("torneo.ser");
                            System.out.println(" Torneo guardado exitosamente en 'torneo.ser'.");
                        } catch (IOException e) {
                            System.err.println(" Error de E/S al guardar el archivo: " + e.getMessage());
                        }
                        break;

                    case 6:
                        try {
                            controlador.importarDatosBinarios("torneo.ser");
                            System.out.println(" Torneo recuperado exitosamente desde el archivo serializado.");
                        } catch (IOException | ClassNotFoundException e) {
                            System.err.println(" Error al cargar los datos: " + e.getMessage());
                        }
                        break;

                    case 7:
                        System.out.println("Saliendo de la aplicación...");
                        break;

                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println(" Por favor, ingrese un valor numérico válido.");
            }

        } while (opcion != 7);

        scanner.close();
    }
}
