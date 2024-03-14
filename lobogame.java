package lobo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Implementación del juego de roles.
 */
public class JuegoDeRoles {

    private static final int NUMERO_JUGADORES = 5;
    private static final String[] ROLES = {"Bruja", "Lobo", "Visionero", "Cazador", "Aldeano"};
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Método principal que inicia el juego.
     */
    public static void main(String[] args) {
        System.out.println("¡Bienvenido al juego de roles!");

        // Verificar si hay 5 jugadores
        if (!verificarNumeroJugadores()) {
            System.out.println("Debe haber exactamente 5 jugadores.");
            return;
        }

        // Pedir nombres de jugadores
        ArrayList<String> jugadores = pedirNombresJugadores();

        // Asignar roles aleatorios
        ArrayList<String> roles = asignarRoles();

        // Mostrar jugadores con roles asignados
        mostrarJugadoresConRoles(jugadores, roles);

        // Iniciar el juego
        jugar(jugadores, roles);
    }

    /**
     * Verifica si el número de jugadores es 5.
     *
     * @return true si hay 5 jugadores, false de lo contrario.
     */
    private static boolean verificarNumeroJugadores() {
        return true; // Siempre será true para este juego
    }

    /**
     * Pide los nombres de los jugadores al usuario.
     *
     * @return ArrayList de nombres de jugadores.
     */
    private static ArrayList<String> pedirNombresJugadores() {
        ArrayList<String> jugadores = new ArrayList<>();
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            String nombre = pedirString("Ingrese el nombre del jugador " + (i + 1) + ": ");
            jugadores.add(nombre);
        }
        return jugadores;
    }

    /**
     * Asigna roles aleatorios a los jugadores.
     *
     * @return ArrayList de roles asignados.
     */
    private static ArrayList<String> asignarRoles() {
        ArrayList<String> roles = new ArrayList<>();
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            roles.add(ROLES[i]);
        }
        Collections.shuffle(roles);
        return roles;
    }

    /**
     * Muestra los jugadores con sus roles asignados.
     *
     * @param jugadores ArrayList de nombres de jugadores.
     * @param roles     ArrayList de roles asignados.
     */
    private static void mostrarJugadoresConRoles(ArrayList<String> jugadores, ArrayList<String> roles) {
        System.out.println("Roles asignados:");
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            System.out.println("Jugador " + (i + 1) + ": " + jugadores.get(i) + " - " + roles.get(i));
        }
    }

    /**
     * Inicia el juego y controla el flujo del mismo.
     *
     * @param jugadores ArrayList de nombres de jugadores.
     * @param roles     ArrayList de roles asignados.
     */
    private static void jugar(ArrayList<String> jugadores, ArrayList<String> roles) {
        System.out.println("¡Comienza el juego!");

        boolean finJuego = false;

        while (!finJuego) {
            System.out.println("\nRonda (Noche):");
            noche(jugadores, roles);

            System.out.println("\nRonda (Día):");
            dia(jugadores, roles);

            finJuego = verificarFinJuego(roles, jugadores);
        }

        System.out.println("\n¡Fin del juego!");
    }

    /**
     * Realiza las acciones de la ronda nocturna.
     *
     * @param jugadores ArrayList de nombres de jugadores.
     * @param roles     ArrayList de roles asignados.
     */
    private static void noche(ArrayList<String> jugadores, ArrayList<String> roles) {
        String accionBruja = "abstenerse";
        String accionLobo = "abstenerse";

        // La Bruja decide
        if (roles.contains("Bruja")) {
            System.out.println("La Bruja decide (Curar, Matar o Abstenerse):");
            accionBruja = pedirString("Ingrese la acción de la Bruja (curar/matar/abstenerse): ");
        }

        // El Lobo decide
        if (roles.contains("Lobo")) {
            System.out.println("El Lobo decide a quién matar:");
            accionLobo = elegirVictima(jugadores, roles, "a quien matar");
        }

        // Realizar acciones según las decisiones de los jugadores
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            if (roles.get(i).equals("Bruja") && accionBruja.equalsIgnoreCase("matar")) {
                if (jugadores.get(i).equals(accionLobo)) {
                    System.out.println("¡La Bruja no puede matar al Lobo!");
                } else {
                    System.out.println("La Bruja ha matado a " + accionBruja);
                    roles.set(i, "Muerto");
                }
            } else if (roles.get(i).equals("Lobo")) {
                if (jugadores.get(i).equals(accionLobo)) {
                    System.out.println("El Lobo no puede matarse a sí mismo.");
                } else {
                    System.out.println("El Lobo ha matado a " + accionLobo);
                    roles.set(i, "Muerto");
                }
            }
        }
    }

    /**
     * Realiza las acciones de la ronda diurna.
     *
     * @param jugadores ArrayList de nombres de jugadores.
     * @param roles     ArrayList de roles asignados.
     */
    private static void dia(ArrayList<String> jugadores, ArrayList<String> roles) {
        // Mostrar estado de los jugadores
        System.out.println("Estado de los jugadores:");
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            System.out.println(jugadores.get(i) + " - " + roles.get(i));
        }

        // Todos votan
        System.out.println("Todos votan:");
        String victimaVotacion = elegirVictima(jugadores, roles, "a quien votar");
        System.out.println(victimaVotacion + " ha sido eliminado por votación.");

        // Actualizar estado del jugador eliminado
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            if (jugadores.get(i).equals(victimaVotacion)) {
                roles.set(i, "Muerto");
            }
        }
    }

    /**
     * Verifica si hay un ganador.
     *
     * @param roles     ArrayList de roles asignados.
     * @param jugadores ArrayList de nombres de jugadores.
     * @return true si hay un ganador, false de lo contrario.
     */
    private static boolean verificarFinJuego(ArrayList<String> roles, ArrayList<String> jugadores) {
        int malosVivos = 0;
        int buenosVivos = 0;

        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            if (!roles.get(i).equals("Muerto")) {
                if (roles.get(i).equals("Bruja") || roles.get(i).equals("Lobo")) {
                    malosVivos++;
                } else {
                    buenosVivos++;
                }
            }
        }

        return malosVivos == 0 || malosVivos == 2 || buenosVivos == 0;
    }

    /**
     * Pide al usuario que elija una víctima.
     *
     * @param jugadores ArrayList de nombres de jugadores.
     * @param roles     ArrayList de roles asignados.
     * @param accion    La acción que se está realizando (curar, matar, votar, etc.).
     * @return El nombre de la víctima elegida.
     */
    private static String elegirVictima(ArrayList<String> jugadores, ArrayList<String> roles, String accion) {
        ArrayList<String> vivos = new ArrayList<>();
        for (int i = 0; i < NUMERO_JUGADORES; i++) {
            if (!roles.get(i).equals("Muerto")) {
                vivos.add(jugadores.get(i));
            }
        }

        System.out.println("Seleccione " + accion + ":");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.println((i + 1) + ". " + vivos.get(i));
        }

        int seleccion = pedirEntero(1, vivos.size()) - 1;
        return vivos.get(seleccion);
    }

    /**
     * Pide al usuario que ingrese una cadena.
     *
     * @param mensaje El mensaje que se mostrará al usuario.
     * @return La cadena ingresada por el usuario.
     */
    private static String pedirString(String mensaje) {
        System.out.print(mensaje);
        return SCANNER.nextLine();
    }

    /**
     * Pide al usuario que ingrese un número entero dentro de un rango especificado.
     *
     * @param min El valor mínimo del rango.
     * @param max El valor máximo del rango.
     * @return El número entero ingresado por el usuario.
     */
    private static int pedirEntero(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(SCANNER.nextLine());
                if (input < min || input > max) {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        return input;
    }
}
