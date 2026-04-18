package ui;

import java.util.Scanner;
import model.miembros.Miembro;
import service.AccesoService;
import service.ClaseService;
import service.MiembroService;

/**
 * Clase que implementa la interfaz de usuario por consola para el sistema de gimnasio.
 * Gestiona la interaccion con el usuario, muestra los menus y procesa las opciones
 * seleccionadas. Coordina los servicios de miembros, clases y acceso.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see MiembroService
 * @see ClaseService
 * @see AccesoService
 */
public class MenuConsola {

    // ==================== ATRIBUTOS ====================
    
    /** Scanner para leer la entrada del usuario. */
    private Scanner scanner;
    
    /** Servicio para operaciones de miembros. */
    private MiembroService miembroService;
    
    /** Servicio para operaciones de clases. */
    private ClaseService claseService;
    
    /** Servicio para operaciones de acceso. */
    private AccesoService accesoService;
    
    /** Miembro actualmente autenticado en sesion. */
    private Miembro miembroActual;
    
    /** Indica si hay una sesion de miembro activa. */
    private boolean sesionActiva;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del menu de consola.
     * Inicializa el scanner y crea las instancias de los servicios necesarios.
     * La sesion comienza como inactiva.
     */
    public MenuConsola() {
        this.scanner = new Scanner(System.in);
        this.miembroService = new MiembroService();
        this.claseService = new ClaseService();
        this.accesoService = new AccesoService();
        this.sesionActiva = false;
    }

    // ==================== METODO PRINCIPAL ====================
    
    /**
     * Inicia el bucle principal del menu.
     * Muestra diferentes menus segun si hay una sesion activa o no.
     * Permite navegar entre todas las opciones del sistema hasta que el usuario decida salir.
     */
    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            if (!sesionActiva) {
                mostrarMenuPrincipal();
                int opcion = leerOpcion();
                
                switch (opcion) {
                    case 1:
                        iniciarSesion();
                        break;
                    case 2:
                        miembroService.registrarMiembro(scanner);
                        pausa();
                        break;
                    case 3:
                        System.out.println("Gracias por visitar Power Gym!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } else {
                mostrarMenuMiembro();
                int opcion = leerOpcion();
                
                switch (opcion) {
                    case 1:
                        miembroService.consultarMiembro(scanner);
                        pausa();
                        break;
                    case 2:
                        claseService.listarClases();
                        pausa();
                        break;
                    case 3:
                        claseService.inscribirMiembro(scanner, miembroActual);
                        pausa();
                        break;
                    case 4:
                        accesoService.registrarIngreso(scanner, miembroActual);
                        pausa();
                        break;
                    case 5:
                        accesoService.verVisitasActivas();
                        pausa();
                        break;
                    case 6:
                        accesoService.generarReporteEstadistico();
                        pausa();
                        break;
                    case 7:
                        administrarGimnasio();
                        break;
                    case 8:
                        cerrarSesion();
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            }
        }
        
        scanner.close();
    }

    // ==================== METODOS DE MENU ====================
    
    /**
     * Muestra el menu principal cuando no hay una sesion activa.
     * Opciones: Iniciar sesion, Registrar nuevo miembro, Salir.
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n*** POWER GYM MEXICO ***");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Registrar nuevo miembro");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Muestra el menu de miembro cuando hay una sesion activa.
     * Opciones: Mi perfil, Ver clases, Inscribirme a clase, Registrar ingreso,
     * Ver visitas activas, Estadisticas, Administracion, Cerrar sesion.
     */
    private void mostrarMenuMiembro() {
        System.out.println("\n=== BIENVENIDO, " + miembroActual.getNombreCompleto() + " ===");
        System.out.println("1. Mi perfil");
        System.out.println("2. Ver clases disponibles");
        System.out.println("3. Inscribirme a clase");
        System.out.println("4. Registrar ingreso");
        System.out.println("5. Ver visitas activas");
        System.out.println("6. Estadisticas del gimnasio");
        System.out.println("7. Administracion (solo staff)");
        System.out.println("8. Cerrar sesion");
        System.out.print("Seleccione: ");
    }

    // ==================== SUBMENU DE ADMINISTRACION ====================
    
    /**
     * Muestra el submenu de administracion para personal autorizado.
     * Opciones: Crear nueva clase, Listar miembros, Asignar membresia,
     * Registrar salida, Volver.
     */
    private void administrarGimnasio() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n=== ADMINISTRACION ===");
            System.out.println("1. Crear nueva clase");
            System.out.println("2. Listar todos los miembros");
            System.out.println("3. Asignar membresia");
            System.out.println("4. Registrar salida");
            System.out.println("5. Volver");
            System.out.print("Seleccione: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    claseService.crearClase(scanner);
                    break;
                case 2:
                    miembroService.listarMiembros();
                    break;
                case 3:
                    miembroService.asignarMembresia(scanner);
                    break;
                case 4:
                    accesoService.registrarSalida(scanner);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
            
            if (!volver) pausa();
        }
    }

    // ==================== METODOS DE AUTENTICACION ====================
    
    /**
     * Inicia sesion de un miembro existente.
     * Busca al miembro por su documento de identidad.
     * 
     * @return true si la autenticacion es exitosa, false en caso contrario
     */
    private void iniciarSesion() {
        System.out.println("\n=== INICIO DE SESION ===");
        System.out.print("Documento de identidad: ");
        String documento = scanner.nextLine();
        
        Miembro miembro = repository.DataStore.getInstance().getMiembroByDocumento(documento);
        if (miembro == null) {
            System.out.println("Miembro no encontrado");
            return;
        }
        
        this.miembroActual = miembro;
        this.sesionActiva = true;
        System.out.println("Bienvenido " + miembro.getNombreCompleto() + "!");
    }

    /**
     * Cierra la sesion actual del miembro.
     * Limpia el miembro actual y desactiva la sesion.
     */
    private void cerrarSesion() {
        this.sesionActiva = false;
        this.miembroActual = null;
        System.out.println("Sesion cerrada");
        pausa();
    }

    // ==================== METODOS AUXILIARES ====================
    
    /**
     * Lee y valida la opcion ingresada por el usuario.
     * 
     * @return Numero entero de la opcion seleccionada, o 0 si no es valida
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Pausa la ejecucion hasta que el usuario presione Enter.
     * Util para que el usuario pueda leer mensajes antes de continuar.
     */
    private void pausa() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
}//fin de la clase