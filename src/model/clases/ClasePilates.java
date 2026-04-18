package model.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una clase de Pilates en el gimnasio.
 * Hereda de ClaseGrupal con nivel de intensidad BAJO.
 * Material necesario: Mat, Pelota, Banda elastica.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see ClaseGrupal
 * @see ClaseYoga
 * @see ClaseSpinning
 * @see ClaseCrossfit
 */
public class ClasePilates extends ClaseGrupal {

    // ==================== ATRIBUTOS ====================
    
    /** Nivel de intensidad de la clase (BAJO). */
    private String nivelIntensidad;
    
    /** Lista de materiales necesarios para la clase. */
    private List<String> materialNecesario;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una clase de Pilates.
     * Nivel de intensidad: BAJO.
     * Material necesario: Mat, Pelota, Banda elastica.
     * 
     * @param idClase Identificador unico de la clase
     * @param nombreClase Nombre de la clase
     * @param horarioInicio Fecha y hora de inicio de la clase
     */
    public ClasePilates(String idClase, String nombreClase, LocalDateTime horarioInicio) {
        super(idClase, nombreClase, horarioInicio);
        this.nivelIntensidad = "BAJO";
        this.materialNecesario = new ArrayList<>();
        this.materialNecesario.add("Mat");
        this.materialNecesario.add("Pelota");
        this.materialNecesario.add("Banda elastica");
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de clase como texto.
     * 
     * @return "PILATES"
     */
    @Override
    public String getTipoClase() {
        return "PILATES";
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