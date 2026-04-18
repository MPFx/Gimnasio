package model.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una clase de Yoga en el gimnasio.
 * Hereda de ClaseGrupal con nivel de intensidad MEDIO.
 * Material necesario: Mat, Bloque, Cinta.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ClaseGrupal
 * @see ClaseSpinning
 * @see ClaseCrossfit
 * @see ClasePilates
 */
public class ClaseYoga extends ClaseGrupal {

    // ==================== ATRIBUTOS ====================
    
    /** Nivel de intensidad de la clase (MEDIO). */
    private String nivelIntensidad;
    
    /** Lista de materiales necesarios para la clase. */
    private List<String> materialNecesario;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una clase de Yoga.
     * Nivel de intensidad: MEDIO.
     * Material necesario: Mat, Bloque, Cinta.
     * 
     * @param idClase Identificador unico de la clase
     * @param nombreClase Nombre de la clase
     * @param horarioInicio Fecha y hora de inicio de la clase
     */
    public ClaseYoga(String idClase, String nombreClase, LocalDateTime horarioInicio) {
        super(idClase, nombreClase, horarioInicio);
        this.nivelIntensidad = "MEDIO";
        this.materialNecesario = new ArrayList<>();
        this.materialNecesario.add("Mat");
        this.materialNecesario.add("Bloque");
        this.materialNecesario.add("Cinta");
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de clase como texto.
     * 
     * @return "YOGA"
     */
    @Override
    public String getTipoClase() {
        return "YOGA";
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
    
}//fin de la clase