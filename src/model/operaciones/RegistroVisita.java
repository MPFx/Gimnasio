package model.operaciones;

import java.time.Duration;
import java.time.LocalDateTime;
import model.clases.ClaseGrupal;
import model.miembros.Membresia;
import model.miembros.Miembro;

/**
 * Clase que representa el registro de una visita de un miembro al gimnasio.
 * Contiene informacion sobre ingreso, salida, tipo de acceso (libre o clase),
 * y referencias al miembro, su membresia y la clase a la que asistio (si aplica).
 * Permite calcular el tiempo de permanencia y determinar si fue visita a clase.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Miembro
 * @see Membresia
 * @see ClaseGrupal
 */
public class RegistroVisita {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la visita. */
    private String idVisita;
    
    /** Fecha y hora de ingreso del miembro. */
    private LocalDateTime fechaHoraIngreso;
    
    /** Fecha y hora de salida del miembro (puede ser null si aun no sale). */
    private LocalDateTime fechaHoraSalida;
    
    /** Tipo de acceso (LIBRE, CLASE). */
    private String tipoAcceso;
    
    /** Identificador del miembro que realiza la visita. */
    private String idMiembro;
    
    /** Identificador de la membresia utilizada para el acceso. */
    private String idMembresia;
    
    /** Identificador de la clase a la que asiste (si tipoAcceso es CLASE). */
    private String idClase;

    /** Referencia al objeto Miembro. */
    private Miembro miembro;
    
    /** Referencia a la Membresia utilizada. */
    private Membresia membresia;
    
    /** Referencia a la ClaseGrupal (si aplica). */
    private ClaseGrupal clase;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un registro de visita de acceso libre.
     * Registra automaticamente la fecha y hora de ingreso actual.
     * Si el miembro tiene una membresia activa, la asigna al registro.
     * 
     * @param idVisita Identificador unico de la visita
     * @param miembro Miembro que realiza la visita
     * @param tipoAcceso Tipo de acceso (LIBRE o CLASE)
     */
    public RegistroVisita(String idVisita, Miembro miembro, String tipoAcceso) {
        this.idVisita = idVisita;
        this.miembro = miembro;
        this.idMiembro = miembro.getIdMiembro();
        this.tipoAcceso = tipoAcceso;
        this.fechaHoraIngreso = LocalDateTime.now();
        
        if (miembro.getMembresiaActiva() != null) {
            this.membresia = miembro.getMembresiaActiva();
            this.idMembresia = membresia.getIdMembresia();
        }
    }

    // ==================== METODOS DE REGISTRO ====================
    
    /**
     * Registra la salida del miembro.
     * Establece la fecha y hora de salida al momento actual.
     */
    public void RegistrarSalida() {
        this.fechaHoraSalida = LocalDateTime.now();
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula el tiempo de permanencia del miembro en el gimnasio.
     * Si aun no ha salido, calcula desde el ingreso hasta el momento actual.
     * 
     * @return Tiempo de permanencia en minutos
     */
    public long CalcularTiempoPermanencia() {
        if (fechaHoraSalida == null) {
            return Duration.between(fechaHoraIngreso, LocalDateTime.now()).toMinutes();
        }
        return Duration.between(fechaHoraIngreso, fechaHoraSalida).toMinutes();
    }

    /**
     * Verifica si la visita corresponde a una clase grupal.
     * 
     * @return true si el tipoAcceso es "CLASE" y hay una clase asignada
     */
    public boolean EsVisitaClase() {
        return "CLASE".equals(tipoAcceso) && clase != null;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la visita */
    public String getIdVisita() { return idVisita; }
    
    /** @param idVisita Nuevo identificador de visita */
    public void setIdVisita(String idVisita) { this.idVisita = idVisita; }

    /** @return Fecha y hora de ingreso */
    public LocalDateTime getFechaHoraIngreso() { return fechaHoraIngreso; }
    
    /** @param fechaHoraIngreso Nueva fecha y hora de ingreso */
    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) { this.fechaHoraIngreso = fechaHoraIngreso; }

    /** @return Fecha y hora de salida (puede ser null) */
    public LocalDateTime getFechaHoraSalida() { return fechaHoraSalida; }
    
    /** @param fechaHoraSalida Nueva fecha y hora de salida */
    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) { this.fechaHoraSalida = fechaHoraSalida; }

    /** @return Tipo de acceso (LIBRE, CLASE) */
    public String getTipoAcceso() { return tipoAcceso; }
    
    /** @param tipoAcceso Nuevo tipo de acceso */
    public void setTipoAcceso(String tipoAcceso) { this.tipoAcceso = tipoAcceso; }

    /** @return Identificador del miembro */
    public String getIdMiembro() { return idMiembro; }
    
    /** @param idMiembro Nuevo identificador del miembro */
    public void setIdMiembro(String idMiembro) { this.idMiembro = idMiembro; }

    /** @return Identificador de la membresia */
    public String getIdMembresia() { return idMembresia; }
    
    /** @param idMembresia Nuevo identificador de membresia */
    public void setIdMembresia(String idMembresia) { this.idMembresia = idMembresia; }

    /** @return Identificador de la clase */
    public String getIdClase() { return idClase; }
    
    /**
     * Establece el identificador de la clase.
     * Automaticamente cambia el tipo de acceso a "CLASE".
     * 
     * @param idClase Nuevo identificador de clase
     */
    public void setIdClase(String idClase) { 
        this.idClase = idClase;
        this.tipoAcceso = "CLASE";
    }

    /** @return Referencia al miembro */
    public Miembro getMiembro() { return miembro; }
    
    /** @param miembro Nueva referencia al miembro */
    public void setMiembro(Miembro miembro) { this.miembro = miembro; }

    /** @return Referencia a la membresia */
    public Membresia getMembresia() { return membresia; }
    
    /** @param membresia Nueva referencia a la membresia */
    public void setMembresia(Membresia membresia) { this.membresia = membresia; }

    /** @return Referencia a la clase */
    public ClaseGrupal getClase() { return clase; }
    
    /**
     * Establece la referencia a la clase.
     * Automaticamente actualiza el idClase.
     * 
     * @param clase Nueva referencia a la clase
     */
    public void setClase(ClaseGrupal clase) { 
        this.clase = clase;
        this.idClase = clase.getIdClase();
    }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la visita.
     * Formato: "Visita: nombre | Ingreso: hora | Tipo: tipoAcceso"
     * 
     * @return Cadena con la informacion principal de la visita
     */
    @Override
    public String toString() {
        return "Visita: " + miembro.getNombreCompleto() + 
               " | Ingreso: " + fechaHoraIngreso.toLocalTime() +
               " | Tipo: " + tipoAcceso;
    }
    
}//fin de la clase