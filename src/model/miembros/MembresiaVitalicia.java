package model.miembros;

import java.time.LocalDateTime;

/**
 * Clase que representa una membresia vitalicia del gimnasio.
 * Hereda de Membresia con una vigencia extremadamente larga (100 años).
 * Tiene beneficios especiales como acceso ilimitado de por vida e invitados.
 * Sobrescribe el metodo ValidarAcceso para que siempre retorne true.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Membresia
 * @see MembresiaMensual
 * @see MembresiaTrimestral
 * @see MembresiaAnual
 */
public class MembresiaVitalicia extends Membresia {

    // ==================== ATRIBUTOS ====================
    
    /** Descripcion del beneficio vitalicio. */
    private String beneficioVitalicio;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una membresia vitalicia.
     * La vigencia es de 36,500 dias (aproximadamente 100 años),
     * accesos incluidos esencialmente ilimitados (999,999).
     * 
     * @param idMembresia Identificador unico de la membresia
     * @param fechaInicio Fecha y hora de inicio de la membresia
     */
    public MembresiaVitalicia(String idMembresia, LocalDateTime fechaInicio) {
        super(idMembresia, fechaInicio, 36500);
        this.beneficioVitalicio = "Acceso ilimitado de por vida + invitados";
        this.accesosIncluidos = 999999;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de membresia como texto.
     * 
     * @return "VITALICIA"
     */
    @Override
    public String getTipoMembresia() {
        return "VITALICIA";
    }

    /**
     * Valida si el miembro puede acceder al gimnasio.
     * La membresia vitalicia siempre es valida mientras el miembro exista.
     * 
     * @return Siempre true
     */
    @Override
    public boolean ValidarAcceso() {
        return true;
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Descripcion del beneficio vitalicio */
    public String getBeneficioVitalicio() { return beneficioVitalicio; }
    
    /** @param beneficioVitalicio Nueva descripcion del beneficio */
    public void setBeneficioVitalicio(String beneficioVitalicio) { this.beneficioVitalicio = beneficioVitalicio; }
    
}//fin de la clase