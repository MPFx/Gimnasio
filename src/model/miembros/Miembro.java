package model.miembros;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un miembro del gimnasio.
 * Contiene informacion personal del miembro, su estado de salud,
 * objetivos, historial de membresias y la membresia activa actual.
 * Permite calcular la edad, activar/desactivar membresia y asignar nuevas membresias.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Membresia
 */
public class Miembro {

    // ==================== ATRIBUTOS ====================
    
    /** Identificador unico del miembro. */
    private String idMiembro;
    
    /** Nombre completo del miembro. */
    private String nombreCompleto;
    
    /** Documento de identidad (cedula, NIT, pasaporte). */
    private String documentoIdentidad;
    
    /** Fecha de nacimiento del miembro. */
    private LocalDateTime fechaNacimiento;
    
    /** Telefono de contacto del miembro. */
    private String telefonoContacto;
    
    /** Correo electronico de contacto. */
    private String emailContacto;
    
    /** Direccion de residencia del miembro. */
    private String direccionResidencia;
    
    /** Fecha de registro del miembro en el sistema. */
    private LocalDateTime fechaRegistro;
    
    /** Condiciones de salud o restricciones medicas del miembro. */
    private String estadoSalud;
    
    /** Objetivo principal del miembro (perder peso, ganar masa, etc.). */
    private String objetivoPrincipal;
    
    /** URL o ruta de la foto de perfil del miembro. */
    private String fotoPerfil;
    
    /** Indica si el miembro tiene una membresia activa. */
    private boolean estaActivo;
    
    /** Lista de todas las membresias que ha tenido el miembro. */
    private List<Membresia> historialMembresias;
    
    /** Membresia actualmente activa del miembro. */
    private Membresia membresiaActiva;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor para crear un nuevo miembro.
     * Inicializa la fecha de registro actual, estado activo true
     * y el historial de membresias como lista vacia.
     * 
     * @param idMiembro Identificador unico del miembro
     * @param nombreCompleto Nombre completo del miembro
     * @param documentoIdentidad Documento de identidad
     */
    public Miembro(String idMiembro, String nombreCompleto, String documentoIdentidad) {
        this.idMiembro = idMiembro;
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaRegistro = LocalDateTime.now();
        this.estaActivo = true;
        this.historialMembresias = new ArrayList<>();
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula la edad actual del miembro basada en su fecha de nacimiento.
     * 
     * @return Edad en años cumplidos, o 0 si no hay fecha de nacimiento
     */
    public int CalcularEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento.toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    // ==================== METODOS DE MEMBRESIA ====================
    
    /**
     * Activa la membresia del miembro.
     * Cambia el estado activo a true.
     */
    public void ActivarMembresia() {
        this.estaActivo = true;
    }

    /**
     * Desactiva la membresia del miembro.
     * Cambia el estado activo a false.
     */
    public void DesactivarMembresia() {
        this.estaActivo = false;
    }

    /**
     * Asigna una nueva membresia al miembro.
     * La establece como membresia activa y la agrega al historial.
     * 
     * @param membresia Membresia a asignar
     */
    public void asignarMembresia(Membresia membresia) {
        this.membresiaActiva = membresia;
        historialMembresias.add(membresia);
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Identificador unico del miembro */
    public String getIdMiembro() { return idMiembro; }
    
    /** @param idMiembro Nuevo identificador del miembro */
    public void setIdMiembro(String idMiembro) { this.idMiembro = idMiembro; }

    /** @return Nombre completo del miembro */
    public String getNombreCompleto() { return nombreCompleto; }
    
    /** @param nombreCompleto Nuevo nombre completo */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    /** @return Documento de identidad */
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    
    /** @param documentoIdentidad Nuevo documento de identidad */
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }

    /** @return Fecha de nacimiento */
    public LocalDateTime getFechaNacimiento() { return fechaNacimiento; }
    
    /** @param fechaNacimiento Nueva fecha de nacimiento */
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    /** @return Telefono de contacto */
    public String getTelefonoContacto() { return telefonoContacto; }
    
    /** @param telefonoContacto Nuevo telefono de contacto */
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    /** @return Correo electronico */
    public String getEmailContacto() { return emailContacto; }
    
    /** @param emailContacto Nuevo correo electronico */
    public void setEmailContacto(String emailContacto) { this.emailContacto = emailContacto; }

    /** @return Direccion de residencia */
    public String getDireccionResidencia() { return direccionResidencia; }
    
    /** @param direccionResidencia Nueva direccion */
    public void setDireccionResidencia(String direccionResidencia) { this.direccionResidencia = direccionResidencia; }

    /** @return Fecha de registro */
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    
    /** @param fechaRegistro Nueva fecha de registro */
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    /** @return Estado de salud */
    public String getEstadoSalud() { return estadoSalud; }
    
    /** @param estadoSalud Nuevo estado de salud */
    public void setEstadoSalud(String estadoSalud) { this.estadoSalud = estadoSalud; }

    /** @return Objetivo principal */
    public String getObjetivoPrincipal() { return objetivoPrincipal; }
    
    /** @param objetivoPrincipal Nuevo objetivo principal */
    public void setObjetivoPrincipal(String objetivoPrincipal) { this.objetivoPrincipal = objetivoPrincipal; }

    /** @return URL o ruta de la foto de perfil */
    public String getFotoPerfil() { return fotoPerfil; }
    
    /** @param fotoPerfil Nueva URL de la foto */
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    /** @return true si el miembro tiene membresia activa */
    public boolean isEstaActivo() { return estaActivo; }
    
    /** @param estaActivo Nuevo estado activo */
    public void setEstaActivo(boolean estaActivo) { this.estaActivo = estaActivo; }

    /** @return Lista de membresias del historial */
    public List<Membresia> getHistorialMembresias() { return historialMembresias; }
    
    /** @return Membresia actualmente activa */
    public Membresia getMembresiaActiva() { return membresiaActiva; }

    // ==================== SOBREESCRITURAS ====================
    
    /**
     * Devuelve una representacion textual del miembro.
     * Formato: "nombre (documento) - (Membresia activa / Sin membresia)"
     * 
     * @return Cadena con la informacion principal del miembro
     */
    @Override
    public String toString() {
        return nombreCompleto + " (" + documentoIdentidad + ") - " + 
               (membresiaActiva != null ? "Membresia activa" : "Sin membresia");
    }
    
}//fin de la clase