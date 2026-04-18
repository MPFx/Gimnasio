package service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.miembros.Membresia;
import model.miembros.MembresiaAnual;
import model.miembros.MembresiaMensual;
import model.miembros.MembresiaTrimestral;
import model.miembros.MembresiaVitalicia;
import model.miembros.Miembro;
import repository.DataStore;

/**
 * Clase de servicio que gestiona las operaciones relacionadas con miembros del gimnasio.
 * Proporciona funcionalidades para registrar miembros, listarlos, asignar membresias
 * y consultar informacion detallada de un miembro.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Miembro
 * @see Membresia
 * @see DataStore
 */
public class MiembroService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de miembros.
     * Obtiene la instancia unica del DataStore.
     */
    public MiembroService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE REGISTRO ====================
    
    /**
     * Registra un nuevo miembro en el sistema.
     * Solicita nombre, documento, telefono, email y objetivo principal.
     * Valida que el documento no este ya registrado.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     */
    public void registrarMiembro(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE NUEVO MIEMBRO ===");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Documento identidad: ");
        String documento = scanner.nextLine();
        
        if (dataStore.getMiembroByDocumento(documento) != null) {
            System.out.println("Ya existe un miembro con ese documento");
            return;
        }
        
        String id = dataStore.generarIdMiembro();
        Miembro nuevo = new Miembro(id, nombre, documento);
        
        System.out.print("Telefono: ");
        nuevo.setTelefonoContacto(scanner.nextLine());
        
        System.out.print("Email: ");
        nuevo.setEmailContacto(scanner.nextLine());
        
        System.out.print("Objetivo principal: ");
        nuevo.setObjetivoPrincipal(scanner.nextLine());
        
        dataStore.addMiembro(nuevo);
        System.out.println("Miembro registrado exitosamente. ID: " + id);
    }

    // ==================== METODOS DE LISTADO ====================
    
    /**
     * Lista todos los miembros registrados en el sistema.
     * Muestra ID, nombre, documento, estado activo y membresia actual.
     */
    public void listarMiembros() {
        System.out.println("\n=== LISTADO DE MIEMBROS ===");
        List<Miembro> miembros = dataStore.getAllMiembros();
        
        if (miembros.isEmpty()) {
            System.out.println("No hay miembros registrados");
            return;
        }
        
        for (Miembro m : miembros) {
            System.out.println(m.getIdMiembro() + " | " + m.getNombreCompleto() + 
                             " | " + m.getDocumentoIdentidad() +
                             " | Activo: " + m.isEstaActivo());
            if (m.getMembresiaActiva() != null) {
                System.out.println("   Membresia: " + m.getMembresiaActiva().getTipoMembresia() +
                                 " | Vigente hasta: " + m.getMembresiaActiva().getFechaFin().toLocalDate());
            }
        }
    }

    // ==================== METODOS DE MEMBRESIA ====================
    
    /**
     * Asigna una membresia a un miembro existente.
     * Permite seleccionar entre Mensual, Trimestral, Anual o Vitalicia.
     * Solicita metodo de pago y registra la membresia en el sistema.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     */
    public void asignarMembresia(Scanner scanner) {
        System.out.println("\n=== ASIGNAR MEMBRESIA ===");
        
        System.out.print("ID del miembro: ");
        String idMiembro = scanner.nextLine();
        Miembro miembro = dataStore.getMiembro(idMiembro);
        
        if (miembro == null) {
            System.out.println("Miembro no encontrado");
            return;
        }
        
        System.out.println("Tipos de membresia:");
        System.out.println("1. Mensual ($800)");
        System.out.println("2. Trimestral ($2000)");
        System.out.println("3. Anual ($6000)");
        System.out.println("4. Vitalicia ($15000)");
        System.out.print("Seleccione tipo: ");
        
        int tipo = Integer.parseInt(scanner.nextLine());
        String idMembresia = "MEM" + System.currentTimeMillis();
        Membresia membresia = null;
        
        switch (tipo) {
            case 1:
                membresia = new MembresiaMensual(idMembresia, LocalDateTime.now());
                membresia.setPrecioPagado(new BigDecimal("800"));
                break;
            case 2:
                membresia = new MembresiaTrimestral(idMembresia, LocalDateTime.now());
                membresia.setPrecioPagado(new BigDecimal("2000"));
                break;
            case 3:
                membresia = new MembresiaAnual(idMembresia, LocalDateTime.now());
                membresia.setPrecioPagado(new BigDecimal("6000"));
                break;
            case 4:
                membresia = new MembresiaVitalicia(idMembresia, LocalDateTime.now());
                membresia.setPrecioPagado(new BigDecimal("15000"));
                break;
            default:
                System.out.println("Opcion no valida");
                return;
        }
        
        System.out.print("Metodo de pago: ");
        membresia.setMetodoPago(scanner.nextLine());
        
        dataStore.addMembresia(membresia);
        miembro.asignarMembresia(membresia);
        
        System.out.println("Membresia asignada exitosamente");
    }

    // ==================== METODOS DE CONSULTA ====================
    
    /**
     * Consulta y muestra la informacion detallada de un miembro especifico.
     * Incluye datos personales, edad, objetivo y detalles de la membresia activa.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     */
    public void consultarMiembro(Scanner scanner) {
        System.out.print("ID del miembro: ");
        String id = scanner.nextLine();
        
        Miembro m = dataStore.getMiembro(id);
        if (m == null) {
            System.out.println("Miembro no encontrado");
            return;
        }
        
        System.out.println("\n=== DATOS DEL MIEMBRO ===");
        System.out.println("ID: " + m.getIdMiembro());
        System.out.println("Nombre: " + m.getNombreCompleto());
        System.out.println("Documento: " + m.getDocumentoIdentidad());
        System.out.println("Telefono: " + m.getTelefonoContacto());
        System.out.println("Email: " + m.getEmailContacto());
        System.out.println("Edad: " + m.CalcularEdad() + " años");
        System.out.println("Objetivo: " + m.getObjetivoPrincipal());
        System.out.println("Estado: " + (m.isEstaActivo() ? "ACTIVO" : "INACTIVO"));
        
        if (m.getMembresiaActiva() != null) {
            Membresia mem = m.getMembresiaActiva();
            System.out.println("\nMEMBRESIA ACTIVA:");
            System.out.println("Tipo: " + mem.getTipoMembresia());
            System.out.println("Inicio: " + mem.getFechaInicio().toLocalDate());
            System.out.println("Fin: " + mem.getFechaFin().toLocalDate());
            System.out.println("Dias restantes: " + mem.CalcularDiasRestantes());
            System.out.println("Accesos: " + mem.getAccesosRealizados() + "/" + mem.getAccesosIncluidos());
        }
    }
    
}//fin de la clase