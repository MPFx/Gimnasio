package service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.clases.ClaseCrossfit;
import model.clases.ClaseGrupal;
import model.clases.ClasePilates;
import model.clases.ClaseSpinning;
import model.clases.ClaseYoga;
import model.miembros.Miembro;
import repository.DataStore;

/**
 * Clase de servicio que gestiona las operaciones relacionadas con clases grupales.
 * Proporciona funcionalidades para crear clases, listarlas, inscribir miembros
 * y cancelar inscripciones.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ClaseGrupal
 * @see Miembro
 * @see DataStore
 */
public class ClaseService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de clases.
     * Obtiene la instancia unica del DataStore.
     */
    public ClaseService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE CREACION ====================
    
    /**
     * Crea una nueva clase grupal.
     * Permite seleccionar el tipo de clase (Yoga, Spinning, Crossfit, Pilates)
     * y configurar nombre, horario, instructor, salon, cupo y duracion.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     */
    public void crearClase(Scanner scanner) {
        System.out.println("\n=== CREAR NUEVA CLASE ===");
        
        System.out.println("Tipo de clase:");
        System.out.println("1. Yoga");
        System.out.println("2. Spinning");
        System.out.println("3. Crossfit");
        System.out.println("4. Pilates");
        System.out.print("Seleccione: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Nombre de la clase: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Hora (HH:MM): ");
        String horaStr = scanner.nextLine();
        
        LocalDateTime horario = LocalDateTime.parse(fechaStr + "T" + horaStr + ":00");
        
        String idClase = "CLS" + System.currentTimeMillis();
        ClaseGrupal clase = null;
        
        switch (tipo) {
            case 1:
                clase = new ClaseYoga(idClase, nombre, horario);
                System.out.print("Nivel intensidad (BAJO/MEDIO/ALTO): ");
                ((ClaseYoga) clase).setNivelIntensidad(scanner.nextLine());
                break;
            case 2:
                clase = new ClaseSpinning(idClase, nombre, horario);
                System.out.print("Nivel intensidad: ");
                ((ClaseSpinning) clase).setNivelIntensidad(scanner.nextLine());
                System.out.print("Tipo musica: ");
                ((ClaseSpinning) clase).setMusicaTipo(scanner.nextLine());
                break;
            case 3:
                clase = new ClaseCrossfit(idClase, nombre, horario);
                System.out.print("Nivel intensidad: ");
                ((ClaseCrossfit) clase).setNivelIntensidad(scanner.nextLine());
                break;
            case 4:
                clase = new ClasePilates(idClase, nombre, horario);
                System.out.print("Nivel intensidad: ");
                ((ClasePilates) clase).setNivelIntensidad(scanner.nextLine());
                break;
        }
        
        if (clase != null) {
            System.out.print("Instructor: ");
            clase.setInstructorAsignado(scanner.nextLine());
            
            System.out.print("Salon: ");
            clase.setSalonUbicacion(scanner.nextLine());
            
            System.out.print("Cupo maximo: ");
            clase.setCupoMaximo(Integer.parseInt(scanner.nextLine()));
            
            System.out.print("Duracion (minutos): ");
            clase.setDuracionMinutos(Integer.parseInt(scanner.nextLine()));
            
            dataStore.addClase(clase);
            System.out.println("Clase creada exitosamente");
        }
    }

    // ==================== METODOS DE LISTADO ====================
    
    /**
     * Lista todas las clases disponibles en el sistema.
     * Muestra ID, nombre, tipo, horario, instructor, salon y cupo.
     */
    public void listarClases() {
        System.out.println("\n=== CLASES DISPONIBLES ===");
        List<ClaseGrupal> clases = dataStore.getAllClases();
        
        if (clases.isEmpty()) {
            System.out.println("No hay clases programadas");
            return;
        }
        
        for (ClaseGrupal c : clases) {
            System.out.println(c.getIdClase() + " | " + c.getNombreClase() + 
                             " (" + c.getTipoClase() + ")");
            System.out.println("   Horario: " + c.getHorarioInicio().toLocalDate() + 
                             " " + c.getHorarioInicio().toLocalTime() + 
                             " - " + c.CalcularHoraFin().toLocalTime());
            System.out.println("   Instructor: " + c.getInstructorAsignado());
            System.out.println("   Salon: " + c.getSalonUbicacion());
            System.out.println("   Cupo: " + c.getInscritosActuales() + "/" + c.getCupoMaximo());
            System.out.println();
        }
    }

    // ==================== METODOS DE INSCRIPCION ====================
    
    /**
     * Inscribe a un miembro en una clase grupal.
     * Verifica cupo disponible y que el miembro no este ya inscrito.
     * Envia una notificacion de recordatorio al inscribirse.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @param miembro Miembro a inscribir
     */
    public void inscribirMiembro(Scanner scanner, Miembro miembro) {
        System.out.println("\n=== INSCRIBIR A CLASE ===");
        listarClases();
        
        System.out.print("ID de la clase: ");
        String idClase = scanner.nextLine();
        
        ClaseGrupal clase = dataStore.getClase(idClase);
        if (clase == null) {
            System.out.println("Clase no encontrada");
            return;
        }
        
        if (clase.InscribirMiembro(miembro.getIdMiembro())) {
            System.out.println("Inscripcion exitosa a " + clase.getNombreClase());
            
            dataStore.getNotificador().enviarRecordatorioClase(
                miembro, clase.getNombreClase(), clase.getHorarioInicio()
            );
        } else {
            System.out.println("No se pudo inscribir. Clase llena o ya inscrito.");
        }
    }

    /**
     * Cancela la inscripcion de un miembro en una clase grupal.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @param miembro Miembro que cancela la inscripcion
     */
    public void cancelarInscripcion(Scanner scanner, Miembro miembro) {
        System.out.print("ID de la clase: ");
        String idClase = scanner.nextLine();
        
        ClaseGrupal clase = dataStore.getClase(idClase);
        if (clase == null) {
            System.out.println("Clase no encontrada");
            return;
        }
        
        clase.CancelarInscripcion(miembro.getIdMiembro());
        System.out.println("Inscripcion cancelada");
    }
    
}//fin de la clase