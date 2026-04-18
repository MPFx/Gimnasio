package model.operaciones;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.miembros.Miembro;

/**
 * Clase que gestiona las notificaciones y recordatorios para los miembros del gimnasio.
 * Permite enviar notificaciones, programar recordatorios, enviar promociones
 * de membresia y recordatorios de clases.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Miembro
 */
public class Notificacion {

    // ==================== ATRIBUTOS ====================
    
    /** Lista de notificaciones pendientes de enviar. */
    private List<String> notificacionesPendientes;
    
    /** Fecha y hora de la ultima notificacion enviada. */
    private LocalDateTime ultimaNotificacionEnviada;
    
    /** Metodo preferido para enviar notificaciones (EMAIL, SMS, PUSH). */
    private String metodoNotificacionPreferido;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor de la clase Notificacion.
     * Inicializa la lista de notificaciones pendientes como vacia
     * y establece el metodo de notificacion preferido como "EMAIL".
     */
    public Notificacion() {
        this.notificacionesPendientes = new ArrayList<>();
        this.metodoNotificacionPreferido = "EMAIL";
    }

    // ==================== METODOS DE ENVIO ====================
    
    /**
     * Envia una notificacion a un miembro especifico.
     * Muestra en consola la informacion de la notificacion y
     * actualiza la fecha de la ultima notificacion enviada.
     * 
     * @param miembro Miembro destinatario de la notificacion
     * @param mensaje Contenido del mensaje a enviar
     */
    public void EnviarNotificacion(Miembro miembro, String mensaje) {
        System.out.println("\n NOTIFICACION para " + miembro.getNombreCompleto());
        System.out.println("   Mensaje: " + mensaje);
        System.out.println("   Metodo: " + metodoNotificacionPreferido);
        System.out.println("   Enviada: " + LocalDateTime.now());
        
        this.ultimaNotificacionEnviada = LocalDateTime.now();
    }

    /**
     * Programa un recordatorio para un miembro en una fecha especifica.
     * Agrega el recordatorio a la lista de notificaciones pendientes.
     * 
     * @param miembro Miembro destinatario del recordatorio
     * @param fecha Fecha y hora programada para el recordatorio
     * @param motivo Descripcion del motivo del recordatorio
     */
    public void ProgramarRecordatorio(Miembro miembro, LocalDateTime fecha, String motivo) {
        String recordatorio = "Recordatorio para " + miembro.getNombreCompleto() + 
                            " - " + motivo + " - Fecha: " + fecha;
        notificacionesPendientes.add(recordatorio);
        System.out.println("Recordatorio programado: " + recordatorio);
    }

    /**
     * Marca una notificacion pendiente como enviada y la remueve de la lista.
     * 
     * @param index Indice de la notificacion pendiente a marcar como enviada
     */
    public void MarcarComoEnviada(int index) {
        if (index >= 0 && index < notificacionesPendientes.size()) {
            String enviada = notificacionesPendientes.remove(index);
            System.out.println("Notificacion enviada y removida de pendientes: " + enviada);
        }
    }

    // ==================== METODOS ESPECIALIZADOS ====================
    
    /**
     * Envia una promocion especial de membresia a un miembro.
     * 
     * @param miembro Miembro destinatario de la promocion
     * @param tipoMembresia Tipo de membresia ofrecida en la promocion
     */
    public void enviarPromocionMembresia(Miembro miembro, String tipoMembresia) {
        String mensaje = "Aprovecha nuestra promocion especial para membresia " + 
                        tipoMembresia + "! 20% de descuento si renuevas hoy.";
        EnviarNotificacion(miembro, mensaje);
    }

    /**
     * Envia un recordatorio de clase a un miembro.
     * Programa el recordatorio para la fecha y hora de la clase.
     * 
     * @param miembro Miembro destinatario del recordatorio
     * @param nombreClase Nombre de la clase
     * @param horario Fecha y hora de la clase
     */
    public void enviarRecordatorioClase(Miembro miembro, String nombreClase, LocalDateTime horario) {
        String mensaje = "Recordatorio: Tienes clase de " + nombreClase + 
                        " manana a las " + horario.toLocalTime();
        ProgramarRecordatorio(miembro, horario, mensaje);
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Lista de notificaciones pendientes */
    public List<String> getNotificacionesPendientes() { return notificacionesPendientes; }
    
    /** @param notificacionesPendientes Nueva lista de notificaciones pendientes */
    public void setNotificacionesPendientes(List<String> notificacionesPendientes) { this.notificacionesPendientes = notificacionesPendientes; }

    /** @return Fecha de la ultima notificacion enviada */
    public LocalDateTime getUltimaNotificacionEnviada() { return ultimaNotificacionEnviada; }
    
    /** @param ultimaNotificacionEnviada Nueva fecha de ultima notificacion */
    public void setUltimaNotificacionEnviada(LocalDateTime ultimaNotificacionEnviada) { this.ultimaNotificacionEnviada = ultimaNotificacionEnviada; }

    /** @return Metodo de notificacion preferido (EMAIL, SMS, PUSH) */
    public String getMetodoNotificacionPreferido() { return metodoNotificacionPreferido; }
    
    /** @param metodoNotificacionPreferido Nuevo metodo de notificacion */
    public void setMetodoNotificacionPreferido(String metodoNotificacionPreferido) { this.metodoNotificacionPreferido = metodoNotificacionPreferido; }
    
}//fin de la clase