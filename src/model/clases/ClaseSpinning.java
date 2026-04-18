package model.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una clase de Spinning en el gimnasio.
 * Hereda de ClaseGrupal con nivel de intensidad ALTO.
 * Material necesario: Bicicleta, Toalla, Agua.
 * Musica tipo: ELECTRONICA.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ClaseGrupal
 * @see ClaseYoga
 * @see ClaseCrossfit
 * @see ClasePilates
 */
public class ClaseSpinning extends ClaseGrupal {

    // ==================== ATRIBUTOS ====================
    
    /** Nivel de intensidad de la clase (ALTO). */
    private String nivelIntensidad;
    
    /** Lista de materiales necesarios para la clase. */
    private List<String> materialNecesario;
    
    /** Tipo de musica que se reproduce durante la clase. */
    private String musicaTipo;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una clase de Spinning.
     * Nivel de intensidad: ALTO.
     * Musica tipo: ELECTRONICA.
     * Material necesario: Bicicleta, Toalla, Agua.
     * 
     * @param idClase Identificador unico de la clase
     * @param nombreClase Nombre de la clase
     * @param horarioInicio Fecha y hora de inicio de la clase
     */
    public ClaseSpinning(String idClase, String nombreClase, LocalDateTime horarioInicio) {
        super(idClase, nombreClase, horarioInicio);
        this.nivelIntensidad = "ALTO";
        this.musicaTipo = "ELECTRONICA";
        this.materialNecesario = new ArrayList<>();
        this.materialNecesario.add("Bicicleta");
        this.materialNecesario.add("Toalla");
        this.materialNecesario.add("Agua");
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de clase como texto.
     * 
     * @return "SPINNING"
     */
    @Override
    public String getTipoClase() {
        return "SPINNING";
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Nivel de intensidad de la clase */
    public String getNivelIntensidad() { return nivelIntensidad; }
    
    /** @param nivelIntensidad Nuevo nivel de intensidad */
    public void setNivelIntensidad(String nivelIntensidad) { this.nivelIntensidad = nivelIntensidad; }

    /** @return Lista de materiales necesarios */
    public List<String> getMaterialNecesario() { return materialNecesario; }
    
    /** @param materialNecesario Nueva lista de materiales */
    public void setMaterialNecesario(List<String> materialNecesario) { this.materialNecesario = materialNecesario; }

    /** @return Tipo de musica de la clase */
    public String getMusicaTipo() { return musicaTipo; }
    
    /** @param musicaTipo Nuevo tipo de musica */
    public void setMusicaTipo(String musicaTipo) { this.musicaTipo = musicaTipo; }
    
}//fin de la clase