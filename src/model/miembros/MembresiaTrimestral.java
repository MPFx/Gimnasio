package model.miembros;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa una membresia trimestral del gimnasio.
 * Hereda de Membresia con una vigencia de 90 dias.
 * Permite renovacion automatica y tiene accesos ilimitados (999).
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Membresia
 * @see MembresiaMensual
 * @see MembresiaAnual
 * @see MembresiaVitalicia
 */
public class MembresiaTrimestral extends Membresia {

    // ==================== ATRIBUTOS ====================
    
    /** Cantidad de dias de vigencia (90 dias). */
    private int diasVigencia;
    
    /** Indica si la membresia se renueva automaticamente. */
    private boolean renovacionAutomatica;
    
    /** Costo de la renovacion de la membresia. */
    private BigDecimal costoRenovacion;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una membresia trimestral.
     * La vigencia es de 90 dias, renovacion automatica desactivada por defecto,
     * accesos incluidos ilimitados (999).
     * 
     * @param idMembresia Identificador unico de la membresia
     * @param fechaInicio Fecha y hora de inicio de la membresia
     */
    public MembresiaTrimestral(String idMembresia, LocalDateTime fechaInicio) {
        super(idMembresia, fechaInicio, 90);
        this.diasVigencia = 90;
        this.renovacionAutomatica = false;
        this.accesosIncluidos = 999;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de membresia como texto.
     * 
     * @return "TRIMESTRAL"
     */
    @Override
    public String getTipoMembresia() {
        return "TRIMESTRAL";
    }

    // ==================== METODOS ESPECIFICOS ====================
    
    /**
     * Renueva la membresia trimestral.
     * Si la renovacion automatica esta activada, extiende la fecha de fin
     * sumando los dias de vigencia.
     */
    public void renovar() {
        if (renovacionAutomatica) {
            this.fechaFin = this.fechaFin.plusDays(diasVigencia);
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Dias de vigencia (90) */
    public int getDiasVigencia() { return diasVigencia; }
    
    /** @param diasVigencia Nueva cantidad de dias de vigencia */
    public void setDiasVigencia(int diasVigencia) { this.diasVigencia = diasVigencia; }

    /** @return true si la renovacion es automatica */
    public boolean isRenovacionAutomatica() { return renovacionAutomatica; }
    
    /** @param renovacionAutomatica Nueva condicion de renovacion automatica */
    public void setRenovacionAutomatica(boolean renovacionAutomatica) { this.renovacionAutomatica = renovacionAutomatica; }

    /** @return Costo de la renovacion */
    public BigDecimal getCostoRenovacion() { return costoRenovacion; }
    
    /** @param costoRenovacion Nuevo costo de renovacion */
    public void setCostoRenovacion(BigDecimal costoRenovacion) { this.costoRenovacion = costoRenovacion; }
    
}//fin de la clase