package model.miembros;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa una membresia mensual del gimnasio.
 * Hereda de Membresia con una vigencia de 30 dias.
 * Permite renovacion automatica y tiene accesos ilimitados (999).
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Membresia
 * @see MembresiaTrimestral
 * @see MembresiaAnual
 * @see MembresiaVitalicia
 */
public class MembresiaMensual extends Membresia {

    // ==================== ATRIBUTOS ====================
    
    /** Cantidad de dias de vigencia (30 dias). */
    private int diasVigencia;
    
    /** Indica si la membresia se renueva automaticamente. */
    private boolean renovacionAutomatica;
    
    /** Costo de la renovacion de la membresia. */
    private BigDecimal costoRenovacion;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una membresia mensual.
     * La vigencia es de 30 dias, renovacion automatica desactivada por defecto,
     * accesos incluidos ilimitados (999).
     * 
     * @param idMembresia Identificador unico de la membresia
     * @param fechaInicio Fecha y hora de inicio de la membresia
     */
    public MembresiaMensual(String idMembresia, LocalDateTime fechaInicio) {
        super(idMembresia, fechaInicio, 30);
        this.diasVigencia = 30;
        this.renovacionAutomatica = false;
        this.accesosIncluidos = 999;
    }

    // ==================== METODOS SOBRESCRITOS ====================
    
    /**
     * Obtiene el tipo de membresia como texto.
     * 
     * @return "MENSUAL"
     */
    @Override
    public String getTipoMembresia() {
        return "MENSUAL";
    }

    // ==================== METODOS ESPECIFICOS ====================
    
    /**
     * Renueva la membresia mensual.
     * Si la renovacion automatica esta activada, extiende la fecha de fin
     * sumando los dias de vigencia.
     */
    public void renovar() {
        if (renovacionAutomatica) {
            this.fechaFin = this.fechaFin.plusDays(diasVigencia);
        }
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Dias de vigencia (30) */
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