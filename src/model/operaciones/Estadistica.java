package model.operaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que genera estadisticas y reportes del gimnasio.
 * Permite calcular ocupacion promedio, analizar horas pico de acceso,
 * generar reportes diarios y estadisticas por tipo de membresia o clase.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see RegistroVisita
 */
public class Estadistica {

    // ==================== ATRIBUTOS ====================
    
    /** Promedio de personas por hora en el gimnasio. */
    private BigDecimal ocupacionPromedio;
    
    /** Lista de horas del dia con mayor afluencia de visitas. */
    private List<LocalTime> horasPicoAcceso;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor de la clase Estadistica.
     * Inicializa la lista de horas pico como vacia y la ocupacion promedio en cero.
     */
    public Estadistica() {
        this.horasPicoAcceso = new ArrayList<>();
        this.ocupacionPromedio = BigDecimal.ZERO;
    }

    // ==================== METODOS DE CALCULO ====================
    
    /**
     * Calcula la ocupacion promedio del gimnasio basado en las visitas.
     * Agrupa las visitas por hora y calcula el promedio de visitas por hora.
     * 
     * @param visitas Lista de registros de visita a analizar
     */
    public void CalcularOcupacionPromedio(List<RegistroVisita> visitas) {
        if (visitas.isEmpty()) {
            this.ocupacionPromedio = BigDecimal.ZERO;
            return;
        }

        Map<Integer, Integer> visitasPorHora = new HashMap<>();
        
        for (RegistroVisita v : visitas) {
            int hora = v.getFechaHoraIngreso().getHour();
            visitasPorHora.put(hora, visitasPorHora.getOrDefault(hora, 0) + 1);
        }

        double total = visitasPorHora.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        this.ocupacionPromedio = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Analiza y determina las horas pico de acceso al gimnasio.
     * Identifica las horas con mayor cantidad de visitas registradas.
     * 
     * @param visitas Lista de registros de visita a analizar
     */
    public void AnalizarHorasPico(List<RegistroVisita> visitas) {
        if (visitas.isEmpty()) return;

        Map<Integer, Integer> conteoPorHora = new HashMap<>();
        
        for (RegistroVisita v : visitas) {
            int hora = v.getFechaHoraIngreso().getHour();
            conteoPorHora.put(hora, conteoPorHora.getOrDefault(hora, 0) + 1);
        }

        int maxVisitas = conteoPorHora.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        
        horasPicoAcceso = conteoPorHora.entrySet().stream()
                .filter(e -> e.getValue() == maxVisitas)
                .map(e -> LocalTime.of(e.getKey(), 0))
                .collect(Collectors.toList());
    }

    // ==================== METODOS DE REPORTES ====================
    
    /**
     * Genera un reporte diario completo del gimnasio.
     * Incluye total de visitas, visitas a gimnasio, visitas a clases,
     * ocupacion promedio y horas pico.
     * 
     * @param visitas Lista de registros de visita del dia
     * @return Reporte formateado como String
     */
    public String GenerarReporteDiario(List<RegistroVisita> visitas) {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("\n REPORTE DIARIO DE GIMNASIO \n");
        reporte.append("================================\n");
        reporte.append("Total visitas hoy: ").append(visitas.size()).append("\n");
        
        long visitasGimnasio = visitas.stream().filter(v -> "GIMNASIO".equals(v.getTipoAcceso())).count();
        long visitasClase = visitas.stream().filter(v -> "CLASE".equals(v.getTipoAcceso())).count();
        
        reporte.append("Visitas a gimnasio: ").append(visitasGimnasio).append("\n");
        reporte.append("Visitas a clases: ").append(visitasClase).append("\n");
        reporte.append("Ocupacion promedio: ").append(ocupacionPromedio).append(" personas/hora\n");
        
        reporte.append("Horas pico: ");
        for (LocalTime hora : horasPicoAcceso) {
            reporte.append(hora.getHour()).append(":00 ");
        }
        reporte.append("\n================================\n");
        
        return reporte.toString();
    }

    /**
     * Genera estadisticas de visitas agrupadas por tipo de membresia.
     * 
     * @param visitas Lista de registros de visita
     * @return Mapa con el conteo de visitas por tipo de membresia
     */
    public Map<String, Long> generarEstadisticasPorMembresia(List<RegistroVisita> visitas) {
        return visitas.stream()
                .filter(v -> v.getMembresia() != null)
                .collect(Collectors.groupingBy(
                    v -> v.getMembresia().getTipoMembresia(),
                    Collectors.counting()
                ));
    }

    /**
     * Genera estadisticas de visitas agrupadas por tipo de clase.
     * 
     * @param visitas Lista de registros de visita
     * @return Mapa con el conteo de visitas por tipo de clase
     */
    public Map<String, Long> generarEstadisticasPorClase(List<RegistroVisita> visitas) {
        return visitas.stream()
                .filter(v -> v.getClase() != null)
                .collect(Collectors.groupingBy(
                    v -> v.getClase().getTipoClase(),
                    Collectors.counting()
                ));
    }

    // ==================== GETTERS Y SETTERS ====================
    
    /** @return Ocupacion promedio (personas por hora) */
    public BigDecimal getOcupacionPromedio() { return ocupacionPromedio; }
    
    /** @param ocupacionPromedio Nueva ocupacion promedio */
    public void setOcupacionPromedio(BigDecimal ocupacionPromedio) { this.ocupacionPromedio = ocupacionPromedio; }

    /** @return Lista de horas pico de acceso */
    public List<LocalTime> getHorasPicoAcceso() { return horasPicoAcceso; }
    
    /** @param horasPicoAcceso Nueva lista de horas pico */
    public void setHorasPicoAcceso(List<LocalTime> horasPicoAcceso) { this.horasPicoAcceso = horasPicoAcceso; }
    
}//fin de la clase