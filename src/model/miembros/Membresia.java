package model.miembros;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Clase abstracta que representa una membresia del gimnasio.
 * Contiene los atributos y comportamientos comunes a todos los tipos de membresia
 * (Mensual, Trimestral, Anual, Vitalicia). Gestiona fechas de inicio y fin,
 * validacion de acceso, registro de accesos y calculo de dias restantes.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see MembresiaMensual
 * @see MembresiaTrimestral
 * @see MembresiaAnual
 * @see MembresiaVitalicia
 */
public abstract class Membresia {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico de la membresia. */
    protected String idMembresia;
    
    /** Fecha y hora de inicio de la membresia. */
    protected LocalDateTime fechaInicio;
    
    /** Fecha y hora de finalizacion de la membresia. */
    protected LocalDateTime fechaFin;
    
    /** Precio pagado por la membresia. */
    protected BigDecimal precioPagado;
    
    /** Metodo de pago utilizado (Efectivo, Tarjeta, Transferencia). */
    protected String metodoPago;
    
    /** Cantidad de dias restantes de vigencia. */
    protected int diasRestantes;
    
    /** Cantidad de accesos incluidos en la membresia (0 = ilimitados). */
    protected int accesosIncluidos;
    
    /** Cantidad de accesos ya realizados. */
    protected int accesosRealizados;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear una nueva membresia.
     * Inicializa la fecha de fin sumando los dias de vigencia a la fecha de inicio.
     * Los accesos realizados comienzan en cero.
     * 
     * @param idMembresia Identificador unico de la membresia
     * @param fechaInicio Fecha y hora de inicio
     * @param diasVigencia Cantidad de dias de vigencia
     */
    public Membresia(String idMembresia, LocalDateTime fechaInicio, int diasVigencia) {
        this.idMembresia = idMembresia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaInicio.plusDays(diasVigencia);
        this.accesosRealizados = 0;
    }

    // ==================== METODOS DE VALIDACION ====================
    
    /**
     * Calcula los dias restantes de vigencia de la membresia.
     * Si la fecha actual es posterior a la fecha de fin, retorna 0.
     * 
     * @return Cantidad de dias restantes
     */
    public int CalcularDiasRestantes() {
        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.isAfter(fechaFin)) {
            this.diasRestantes = 0;
        } else {
            this.diasRestantes = (int) ChronoUnit.DAYS.between(ahora, fechaFin);
        }
        return this.diasRestantes;
    }

    /**
     * Valida si el miembro puede acceder al gimnasio con esta membresia.
     * Condiciones:
     * - La fecha actual no debe ser posterior a la fecha de fin
     * - Si hay limite de accesos, no deben haberse superado
     * 
     * @return true si el acceso esta permitido, false en caso contrario
     */
    public boolean ValidarAcceso() {
        if (LocalDateTime.now().isAfter(fechaFin)) {
            return false;
        }
        if (accesosIncluidos > 0 && accesosRealizados >= accesosIncluidos) {
            return false;
        }
        return true;
    }

    /**
     * Registra un acceso del miembro al gimnasio.
     * Solo incrementa el contador si el acceso es valido.
     */
    public void RegistrarAcceso() {
        if (ValidarAcceso()) {
            this.accesosRealizados++;
        }
    }

    // ==================== METODO ABSTRACTO ====================
    
    /**
     * Obtiene el tipo de membresia como texto.
     * 
     * @return Tipo de membresia (MENSUAL, TRIMESTRAL, ANUAL, VITALICIA)
     */
    public abstract String getTipoMembresia();

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico de la membresia */
    public String getIdMembresia() { return idMembresia; }
    
    /** @param idMembresia Nuevo identificador */
    public void setIdMembresia(String idMembresia) { this.idMembresia = idMembresia; }

    /** @return Fecha de inicio */
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    
    /** @param fechaInicio Nueva fecha de inicio */
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    /** @return Fecha de fin */
    public LocalDateTime getFechaFin() { return fechaFin; }
    
    /** @param fechaFin Nueva fecha de fin */
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    /** @return Precio pagado */
    public BigDecimal getPrecioPagado() { return precioPagado; }
    
    /** @param precioPagado Nuevo precio pagado */
    public void setPrecioPagado(BigDecimal precioPagado) { this.precioPagado = precioPagado; }

    /** @return Metodo de pago */
    public String getMetodoPago() { return metodoPago; }
    
    /** @param metodoPago Nuevo metodo de pago */
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    /** @return Dias restantes de vigencia */
    public int getDiasRestantes() { return CalcularDiasRestantes(); }
    
    /** @return Accesos incluidos (0 = ilimitados) */
    public int getAccesosIncluidos() { return accesosIncluidos; }
    
    /** @param accesosIncluidos Nueva cantidad de accesos incluidos */
    public void setAccesosIncluidos(int accesosIncluidos) { this.accesosIncluidos = accesosIncluidos; }

    /** @return Accesos realizados */
    public int getAccesosRealizados() { return accesosRealizados; }
    
    /** @param accesosRealizados Nueva cantidad de accesos realizados */
    public void setAccesosRealizados(int accesosRealizados) { this.accesosRealizados = accesosRealizados; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual de la membresia.
     * Formato: "tipo - Vigente hasta: fecha - Accesos: realizados/incluidos"
     * 
     * @return Cadena con la informacion principal de la membresia
     */
    @Override
    public String toString() {
        return getTipoMembresia() + " - Vigente hasta: " + fechaFin.toLocalDate() + 
               " - Accesos: " + accesosRealizados + "/" + accesosIncluidos;
    }
    
}//fin de la clase