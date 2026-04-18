package service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.clases.ClaseGrupal;
import model.miembros.Miembro;
import model.operaciones.Estadistica;
import model.operaciones.RegistroVisita;
import repository.DataStore;

/**
 * Clase de servicio que gestiona el control de acceso al gimnasio.
 * Proporciona funcionalidades para registrar ingresos y salidas de miembros,
 * ver visitas activas y generar reportes estadisticos de uso.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see RegistroVisita
 * @see Miembro
 * @see ClaseGrupal
 * @see DataStore
 */
public class AccesoService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;
    
    /** Contador para generar IDs de visita. */
    private int contadorVisitas = 1000;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de acceso.
     * Obtiene la instancia unica del DataStore.
     */
    public AccesoService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE REGISTRO ====================
    
    /**
     * Registra el ingreso de un miembro al gimnasio.
     * Valida que el miembro tenga membresia activa y que sea valida.
     * Permite seleccionar entre acceso a gimnasio libre o a clase especifica.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @param miembro Miembro que registra ingreso
     */
    public void registrarIngreso(Scanner scanner, Miembro miembro) {
        System.out.println("\n=== REGISTRO DE INGRESO ===");
        
        if (miembro.getMembresiaActiva() == null) {
            System.out.println("El miembro no tiene membresia activa");
            return;
        }
        
        if (!miembro.getMembresiaActiva().ValidarAcceso()) {
            System.out.println("Membresia no valida para acceso");
            return;
        }
        
        System.out.println("Tipo de acceso:");
        System.out.println("1. Gimnasio");
        System.out.println("2. Clase");
        System.out.print("Seleccione: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        String idVisita = "V" + (contadorVisitas++);
        RegistroVisita visita;
        
        if (tipo == 1) {
            visita = new RegistroVisita(idVisita, miembro, "GIMNASIO");
            System.out.println("Acceso a gimnasio registrado");
        } else {
            System.out.print("ID de la clase: ");
            String idClase = scanner.nextLine();
            ClaseGrupal clase = dataStore.getClase(idClase);
            
            if (clase == null) {
                System.out.println("Clase no encontrada");
                return;
            }
            
            visita = new RegistroVisita(idVisita, miembro, "CLASE");
            visita.setClase(clase);
            System.out.println("Acceso a clase registrado");
        }
        
        miembro.getMembresiaActiva().RegistrarAcceso();
        
        dataStore.addVisita(visita);
        System.out.println("Ingreso registrado: " + LocalDateTime.now());
    }

    /**
     * Registra la salida de un miembro.
     * Busca una visita activa (sin hora de salida) por su ID.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     */
    public void registrarSalida(Scanner scanner) {
        System.out.print("ID de la visita: ");
        String idVisita = scanner.nextLine();
        
        RegistroVisita visita = dataStore.getVisitas().stream()
                .filter(v -> v.getIdVisita().equals(idVisita) && v.getFechaHoraSalida() == null)
                .findFirst()
                .orElse(null);
        
        if (visita == null) {
            System.out.println("Visita no encontrada o ya finalizada");
            return;
        }
        
        visita.RegistrarSalida();
        System.out.println("Salida registrada. Tiempo: " + 
                          visita.CalcularTiempoPermanencia() + " minutos");
    }

    // ==================== METODOS DE CONSULTA ====================
    
    /**
     * Muestra todas las visitas activas (sin salida registrada).
     * Incluye ID, nombre del miembro, tipo de acceso, hora de ingreso y tiempo actual.
     */
    public void verVisitasActivas() {
        System.out.println("\n=== VISITAS ACTIVAS ===");
        
        List<RegistroVisita> activas = dataStore.getVisitas().stream()
                .filter(v -> v.getFechaHoraSalida() == null)
                .toList();
        
        if (activas.isEmpty()) {
            System.out.println("No hay visitas activas");
            return;
        }
        
        for (RegistroVisita v : activas) {
            System.out.println(v.getIdVisita() + " | " + 
                             v.getMiembro().getNombreCompleto() + " | " +
                             v.getTipoAcceso() + " | " +
                             "Ingreso: " + v.getFechaHoraIngreso().toLocalTime() + " | " +
                             "Tiempo: " + v.CalcularTiempoPermanencia() + " min");
        }
    }

    // ==================== METODOS DE REPORTES ====================
    
    /**
     * Genera y muestra un reporte estadistico completo.
     * Incluye reporte diario (total visitas, ocupacion promedio, horas pico)
     * y estadisticas por tipo de membresia y por tipo de clase.
     */
    public void generarReporteEstadistico() {
        Estadistica est = dataStore.getEstadisticas();
        List<RegistroVisita> visitas = dataStore.getVisitas();
        
        est.CalcularOcupacionPromedio(visitas);
        est.AnalizarHorasPico(visitas);
        
        System.out.println(est.GenerarReporteDiario(visitas));
        
        System.out.println("\n ESTADISTICAS POR MEMBRESIA:");
        est.generarEstadisticasPorMembresia(visitas).forEach((tipo, count) -> 
            System.out.println("  " + tipo + ": " + count + " visitas")
        );
        
        System.out.println("\n ESTADISTICAS POR CLASE:");
        est.generarEstadisticasPorClase(visitas).forEach((tipo, count) -> 
            System.out.println("  " + tipo + ": " + count + " visitas")
        );
    }
    
}//fin de la clase