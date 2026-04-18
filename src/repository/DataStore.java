package repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.clases.ClaseCrossfit;
import model.clases.ClaseGrupal;
import model.clases.ClasePilates;
import model.clases.ClaseSpinning;
import model.clases.ClaseYoga;
import model.miembros.Membresia;
import model.miembros.MembresiaAnual;
import model.miembros.MembresiaMensual;
import model.miembros.MembresiaTrimestral;
import model.miembros.Miembro;
import model.operaciones.Estadistica;
import model.operaciones.Notificacion;
import model.operaciones.RegistroVisita;

/**
 * Clase singleton que actua como almacenamiento central de datos del sistema de gimnasio.
 * Simula una base de datos en memoria gestionando miembros, membresias, clases,
 * visitas, notificaciones y estadisticas. Contiene datos de prueba precargados.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Miembro
 * @see Membresia
 * @see ClaseGrupal
 * @see RegistroVisita
 */
public class DataStore {

    // ==================== ATRIBUTOS ====================
    
    /** Instancia unica del singleton DataStore. */
    private static DataStore instance;
    
    /** Mapa de miembros indexados por identificador. */
    private Map<String, Miembro> miembros;
    
    /** Mapa de membresias indexadas por identificador. */
    private Map<String, Membresia> membresias;
    
    /** Mapa de clases grupales indexadas por identificador. */
    private Map<String, ClaseGrupal> clases;
    
    /** Lista de registros de visita. */
    private List<RegistroVisita> visitas;
    
    /** Gestor de notificaciones del sistema. */
    private Notificacion notificador;
    
    /** Gestor de estadisticas del sistema. */
    private Estadistica estadisticas;

    /** Contador para generar IDs de miembros. */
    private int contadorMiembros = 1000;
    
    /** Contador para generar IDs de visitas. */
    private int contadorVisitas = 1;

    // ==================== CONSTRUCTOR PRIVADO ====================
    
    /**
     * Constructor privado para implementar el patron singleton.
     * Inicializa las estructuras de datos y carga los datos de prueba.
     */
    private DataStore() {
        this.miembros = new HashMap<>();
        this.membresias = new HashMap<>();
        this.clases = new HashMap<>();
        this.visitas = new ArrayList<>();
        this.notificador = new Notificacion();
        this.estadisticas = new Estadistica();
        
        inicializarDatosPrueba();
    }

    // ==================== METODOS SINGLETON ====================
    
    /**
     * Obtiene la instancia unica del DataStore.
     * Si no existe, la crea por primera vez.
     * 
     * @return Instancia unica del DataStore
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ==================== INICIALIZACION DE DATOS ====================
    
    /**
     * Inicializa datos de prueba para demostracion.
     * Crea:
     * - 3 miembros (Juan, Maria, Carlos)
     * - 3 membresias (Mensual, Anual, Trimestral)
     * - 4 clases (Yoga, Spinning, Crossfit, Pilates)
     * - 4 visitas de prueba
     */
    private void inicializarDatosPrueba() {
        // Crear miembros
        Miembro m1 = new Miembro("M001", "Juan Perez", "12345678");
        m1.setEmailContacto("juan@email.com");
        m1.setTelefonoContacto("5512345678");
        m1.setObjetivoPrincipal("Perder peso");
        miembros.put(m1.getIdMiembro(), m1);

        Miembro m2 = new Miembro("M002", "Maria Garcia", "87654321");
        m2.setEmailContacto("maria@email.com");
        m2.setTelefonoContacto("5587654321");
        m2.setObjetivoPrincipal("Ganar musculo");
        miembros.put(m2.getIdMiembro(), m2);

        Miembro m3 = new Miembro("M003", "Carlos Lopez", "11223344");
        m3.setEmailContacto("carlos@email.com");
        m3.setTelefonoContacto("5511223344");
        m3.setObjetivoPrincipal("Tonificar");
        miembros.put(m3.getIdMiembro(), m3);

        // Crear membresias
        MembresiaMensual memMensual = new MembresiaMensual("MEM001", LocalDateTime.now());
        memMensual.setPrecioPagado(new BigDecimal("800"));
        memMensual.setMetodoPago("TARJETA");
        membresias.put(memMensual.getIdMembresia(), memMensual);
        m1.asignarMembresia(memMensual);

        MembresiaAnual memAnual = new MembresiaAnual("MEM002", LocalDateTime.now().minusMonths(2));
        memAnual.setPrecioPagado(new BigDecimal("6000"));
        memAnual.setMetodoPago("EFECTIVO");
        memAnual.setRenovacionAutomatica(true);
        membresias.put(memAnual.getIdMembresia(), memAnual);
        m2.asignarMembresia(memAnual);

        MembresiaTrimestral memTrimestral = new MembresiaTrimestral("MEM003", LocalDateTime.now());
        memTrimestral.setPrecioPagado(new BigDecimal("2000"));
        memTrimestral.setMetodoPago("TRANSFERENCIA");
        membresias.put(memTrimestral.getIdMembresia(), memTrimestral);
        m3.asignarMembresia(memTrimestral);

        // Crear clases
        ClaseYoga yoga = new ClaseYoga("CLS001", "Yoga Energetico", 
            LocalDateTime.now().withHour(10).withMinute(0));
        yoga.setInstructorAsignado("Ana Martinez");
        yoga.setSalonUbicacion("Salon A");
        clases.put(yoga.getIdClase(), yoga);

        ClaseSpinning spinning = new ClaseSpinning("CLS002", "Spinning Power", 
            LocalDateTime.now().withHour(18).withMinute(0));
        spinning.setInstructorAsignado("Pedro Ramirez");
        spinning.setSalonUbicacion("Salon B");
        clases.put(spinning.getIdClase(), spinning);

        ClaseCrossfit crossfit = new ClaseCrossfit("CLS003", "Crossfit Intenso", 
            LocalDateTime.now().withHour(19).withMinute(30));
        crossfit.setInstructorAsignado("Roberto Sanchez");
        crossfit.setSalonUbicacion("Salon C");
        clases.put(crossfit.getIdClase(), crossfit);

        ClasePilates pilates = new ClasePilates("CLS004", "Pilates Suave", 
            LocalDateTime.now().withHour(9).withMinute(0));
        pilates.setInstructorAsignado("Laura Torres");
        pilates.setSalonUbicacion("Salon A");
        clases.put(pilates.getIdClase(), pilates);

        // Registrar algunas visitas de prueba
        RegistrarVisitaPrueba(m1, "GIMNASIO");
        RegistrarVisitaPrueba(m2, "GIMNASIO");
        RegistrarVisitaPrueba(m3, "CLASE", yoga);
        RegistrarVisitaPrueba(m1, "CLASE", spinning);
    }

    /**
     * Registra una visita de prueba de tipo GIMNASIO.
     * 
     * @param m Miembro que realiza la visita
     * @param tipo Tipo de acceso (GIMNASIO o CLASE)
     */
    private void RegistrarVisitaPrueba(Miembro m, String tipo) {
        RegistroVisita v = new RegistroVisita("V" + (contadorVisitas++), m, tipo);
        v.setFechaHoraIngreso(LocalDateTime.now().minusHours(2));
        v.setFechaHoraSalida(LocalDateTime.now().minusHours(1));
        visitas.add(v);
    }

    /**
     * Registra una visita de prueba de tipo CLASE.
     * 
     * @param m Miembro que realiza la visita
     * @param tipo Tipo de acceso (CLASE)
     * @param clase Clase a la que asiste
     */
    private void RegistrarVisitaPrueba(Miembro m, String tipo, ClaseGrupal clase) {
        RegistroVisita v = new RegistroVisita("V" + (contadorVisitas++), m, tipo);
        v.setClase(clase);
        v.setFechaHoraIngreso(LocalDateTime.now().minusHours(3));
        v.setFechaHoraSalida(LocalDateTime.now().minusHours(2));
        visitas.add(v);
    }

    // ==================== METODOS CRUD PARA MIEMBROS ====================
    
    /** @return Lista de todos los miembros */
    public List<Miembro> getAllMiembros() { return new ArrayList<>(miembros.values()); }
    
    /** @param id Identificador del miembro
     *  @return Miembro encontrado o null si no existe */
    public Miembro getMiembro(String id) { return miembros.get(id); }
    
    /**
     * Busca un miembro por su numero de documento de identidad.
     * 
     * @param documento Documento de identidad del miembro
     * @return Miembro encontrado o null si no existe
     */
    public Miembro getMiembroByDocumento(String documento) {
        return miembros.values().stream()
                .filter(m -> m.getDocumentoIdentidad().equals(documento))
                .findFirst()
                .orElse(null);
    }
    
    /** @param miembro Miembro a agregar */
    public void addMiembro(Miembro miembro) { 
        miembros.put(miembro.getIdMiembro(), miembro); 
    }

    // ==================== METODOS CRUD PARA MEMBRESIAS ====================
    
    /** @return Lista de todas las membresias */
    public List<Membresia> getAllMembresias() { return new ArrayList<>(membresias.values()); }
    
    /** @param id Identificador de la membresia
     *  @return Membresia encontrada o null si no existe */
    public Membresia getMembresia(String id) { return membresias.get(id); }
    
    /** @param membresia Membresia a agregar */
    public void addMembresia(Membresia membresia) { 
        membresias.put(membresia.getIdMembresia(), membresia); 
    }

    // ==================== METODOS CRUD PARA CLASES ====================
    
    /** @return Lista de todas las clases grupales */
    public List<ClaseGrupal> getAllClases() { return new ArrayList<>(clases.values()); }
    
    /** @param id Identificador de la clase
     *  @return Clase encontrada o null si no existe */
    public ClaseGrupal getClase(String id) { return clases.get(id); }
    
    /** @param clase Clase a agregar */
    public void addClase(ClaseGrupal clase) { 
        clases.put(clase.getIdClase(), clase); 
    }

    // ==================== METODOS CRUD PARA VISITAS ====================
    
    /** @return Lista de todos los registros de visita */
    public List<RegistroVisita> getVisitas() { return visitas; }
    
    /** @param visita Registro de visita a agregar */
    public void addVisita(RegistroVisita visita) { 
        visitas.add(visita); 
    }

    // ==================== METODOS DE UTILIDAD ====================
    
    /** @return Gestor de notificaciones */
    public Notificacion getNotificador() { return notificador; }
    
    /** @return Gestor de estadisticas */
    public Estadistica getEstadisticas() { return estadisticas; }

    /**
     * Genera un nuevo identificador unico para un miembro.
     * Formato: "M" + numero incremental (empieza en 1000).
     * 
     * @return Nuevo identificador de miembro
     */
    public String generarIdMiembro() {
        return "M" + (contadorMiembros++);
    }
    
}//fin de la clase