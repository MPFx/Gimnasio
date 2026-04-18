package model.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa una clase grupal del gimnasio.
 * Contiene los atributos y comportamientos comunes a todos los tipos de clases
 * (Yoga, Spinning, Crossfit, Pilates). Gestiona horarios, cupos, inscripciones
 * y lista de miembros inscritos.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ClaseYoga
 * @see ClaseSpinning
 * @see ClaseCrossfit
 * @see ClasePilates
 */
public abstract class ClaseGrupal {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la clase. */
    protected String idClase;
    
    /** Nombre de la clase. */
    protected String nombreClase;
    
    /** Fecha y hora de inicio de la clase. */
    protected LocalDateTime horarioInicio;
    
    /** Duracion de la clase en minutos (por defecto 60). */
    protected int duracionMinutos;
    
    /** Capacidad maxima de miembros por clase (por defecto 20). */
    protected int cupoMaximo;
    
    /** Cantidad actual de miembros inscritos. */
    protected int inscritosActuales;
    
    /** Nombre del instructor asignado a la clase. */
    protected String instructorAsignado;
    
    /** Ubicacion del salon donde se dicta la clase. */
    protected String salonUbicacion;
    
    /** Lista de identificadores de miembros inscritos. */
    protected List<String> miembrosInscritos;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva clase grupal.
     * Inicializa la duracion en 60 minutos, cupo maximo en 20,
     * inscritosActuales en 0 y lista de inscritos vacia.
     * 
     * @param idClase Identificador unico de la clase
     * @param nombreClase Nombre de la clase
     * @param horarioInicio Fecha y hora de inicio de la clase
     */
    public ClaseGrupal(String idClase, String nombreClase, LocalDateTime horarioInicio) {
        this.idClase = idClase;
        this.nombreClase = nombreClase;
        this.horarioInicio = horarioInicio;
        this.duracionMinutos = 60;
        this.cupoMaximo = 20;
        this.inscritosActuales = 0;
        this.miembrosInscritos = new ArrayList<>();
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula la hora de finalizacion de la clase.
     * Suma la duracion en minutos a la hora de inicio.
     * 
     * @return Fecha y hora de finalizacion
     */
    public LocalDateTime CalcularHoraFin() {
        return horarioInicio.plusMinutes(duracionMinutos);
    }

    // ==================== METODOS DE INSCRIPCION ====================
    
    /**
     * Verifica si hay cupo disponible en la clase.
     * 
     * @return true si inscritosActuales es menor a cupoMaximo
     */
    public boolean HayCupo() {
        return inscritosActuales < cupoMaximo;
    }

    /**
     * Inscribe un miembro en la clase.
     * Solo si hay cupo disponible y el miembro no esta ya inscrito.
     * 
     * @param idMiembro Identificador del miembro a inscribir
     * @return true si la inscripcion fue exitosa, false en caso contrario
     */
    public boolean InscribirMiembro(String idMiembro) {
        if (HayCupo() && !miembrosInscritos.contains(idMiembro)) {
            miembrosInscritos.add(idMiembro);
            inscritosActuales++;
            return true;
        }
        return false;
    }

    /**
     * Cancela la inscripcion de un miembro en la clase.
     * 
     * @param idMiembro Identificador del miembro a cancelar
     */
    public void CancelarInscripcion(String idMiembro) {
        if (miembrosInscritos.remove(idMiembro)) {
            inscritosActuales--;
        }
    }

    // ==================== METODO ABSTRACTO ====================
    
    /**
     * Obtiene el tipo de clase como texto.
     * 
     * @return Tipo de clase (YOGA, SPINNING, CROSSFIT, PILATES)
     */
    public abstract String getTipoClase();

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la clase */
    public String getIdClase() { return idClase; }
    
    /** @param idClase Nuevo identificador de la clase */
    public void setIdClase(String idClase) { this.idClase = idClase; }

    /** @return Nombre de la clase */
    public String getNombreClase() { return nombreClase; }
    
    /** @param nombreClase Nuevo nombre de la clase */
    public void setNombreClase(String nombreClase) { this.nombreClase = nombreClase; }

    /** @return Fecha y hora de inicio */
    public LocalDateTime getHorarioInicio() { return horarioInicio; }
    
    /** @param horarioInicio Nueva fecha y hora de inicio */
    public void setHorarioInicio(LocalDateTime horarioInicio) { this.horarioInicio = horarioInicio; }

    /** @return Duracion en minutos */
    public int getDuracionMinutos() { return duracionMinutos; }
    
    /** @param duracionMinutos Nueva duracion en minutos */
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    /** @return Cupo maximo de la clase */
    public int getCupoMaximo() { return cupoMaximo; }
    
    /** @param cupoMaximo Nuevo cupo maximo */
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    /** @return Cantidad actual de inscritos */
    public int getInscritosActuales() { return inscritosActuales; }
    
    /** @param inscritosActuales Nueva cantidad de inscritos */
    public void setInscritosActuales(int inscritosActuales) { this.inscritosActuales = inscritosActuales; }

    /** @return Nombre del instructor asignado */
    public String getInstructorAsignado() { return instructorAsignado; }
    
    /** @param instructorAsignado Nuevo instructor asignado */
    public void setInstructorAsignado(String instructorAsignado) { this.instructorAsignado = instructorAsignado; }

    /** @return Ubicacion del salon */
    public String getSalonUbicacion() { return salonUbicacion; }
    
    /** @param salonUbicacion Nueva ubicacion del salon */
    public void setSalonUbicacion(String salonUbicacion) { this.salonUbicacion = salonUbicacion; }

    /** @return Lista de identificadores de miembros inscritos */
    public List<String> getMiembrosInscritos() { return miembrosInscritos; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la clase.
     * Formato: "nombreClase - horaInicio (inscritos/cupoMaximo)"
     * 
     * @return Cadena con la informacion principal de la clase
     */
    @Override
    public String toString() {
        return nombreClase + " - " + horarioInicio.toLocalTime() + 
               " (" + inscritosActuales + "/" + cupoMaximo + ")";
    }
    
}//fin de la clase