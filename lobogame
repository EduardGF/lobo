package lobo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa el juego de Hombres Lobo de Castronegro.
 */
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa el juego de Hombres Lobo de Castronegro.
 */
public class HombresLobo {

    // Colecciones estáticas para almacenar los roles y jugar
    private static String[] roles = {"Lobo", "Aldeano", "Cazador", "Bruja", "Vidente"};
    private static ArrayList<String> jugadores = new ArrayList<>();

    /**
     * Método principal que inicia el juego.
     */
    public static void main(String[] args) {
        HombresLobo juego = new HombresLobo();
        juego.iniciarPartida();
    }

    /**
     * Método que inicia una partida completa del juego.
     */
    public void iniciarPartida() {
        Scanner scanner = new Scanner(System.in);
        boolean jugarOtraPartida = true;

        while (jugarOtraPartida) {
            iniciarJuego();
            jugarOtraPartida = preguntarRepetirPartida(scanner);
        }

        scanner.close();
    }

    /**
     * Método que inicia una ronda del juego.
     */
    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a Hombres Lobo de Castronegro!");
        System.out.print("Ingrese la cantidad de jugadores: ");
        int cantidadJugadores = scanner.nextInt();

        // Control de errores
        if (cantidadJugadores < 4) {
            System.out.println("Se necesitan al menos 4 jugadores para iniciar el juego.");
            return;
        }

        // Inicializar jugadores
        jugadores.clear(); // Limpiar jugadores de la partida anterior
        for (int i = 0; i < cantidadJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombre = scanner.next();
            jugadores.add(nombre);
        }

        // Mostrar roles asignados
        asignarRoles();
        mostrarRoles();

        // Iniciar rondas de juego
        while (!hayGanador()) {
            iniciarNoche();
            iniciarDia();
        }

        System.out.println("¡El juego ha terminado!");
        scanner.close();
    }

    /**
     * Método que asigna roles aleatorios a los jugadores.
     */
    public void asignarRoles() {
        // Lógica de asignación de roles aleatorios
        // Aquí puedes implementar la lógica de asignación de roles a cada jugador
        // Por ahora, simplemente asignaremos un rol aleatorio a cada jugador
        for (String jugador : jugadores) {
            int indiceRol = (int) (Math.random() * roles.length);
            String rol = roles[indiceRol];
            System.out.println("El jugador " + jugador + " es " + rol);
        }
    }

    /**
     * Método que muestra los roles asignados a los jugadores.
     */
    public void mostrarRoles() {
        System.out.println("Roles asignados:");
        for (String jugador : jugadores) {
            System.out.println(jugador + ": " + obtenerRolJugador(jugador));
        }
    }

    /**
     * Método que inicia la fase nocturna del juego.
     */
    public void iniciarNoche() {
        System.out.println("¡Comienza la noche!");
        // Aquí puedes implementar lógica para acciones nocturnas como lobos, bruja y vidente
    }

    /**
     * Método que inicia la fase diurna del juego.
     */
    public void iniciarDia() {
        System.out.println("¡Comienza el día!");
        // Aquí puedes implementar lógica para discusión y votación para eliminar a un jugador
    }

    /**
     * Método que verifica si hay un ganador en el juego.
     * @return Verdadero si hay un ganador, falso en caso contrario.
     */
    public boolean hayGanador() {
        int cantidadLobos = 0;
        int cantidadAldeanos = 0;

        for (String jugador : jugadores) {
            String rol = obtenerRolJugador(jugador);
            if (rol.equals("Lobo")) {
                cantidadLobos++;
            } else if (rol.equals("Aldeano")) {
                cantidadAldeanos++;
            }
        }

        // El juego termina si no hay lobos o no hay aldeanos
        return cantidadLobos == 0 || cantidadAldeanos == 0;
    }

    /**
     * Método que pregunta al jugador si desea jugar otra partida.
     * @param scanner Scanner para recibir la entrada del usuario.
     * @return Verdadero si el jugador desea jugar otra partida, falso en caso contrario.
     */
    public boolean preguntarRepetirPartida(Scanner scanner) {
        System.out.print("¿Deseas jugar otra partida? (S/N): ");
        String respuesta = scanner.next();
        return respuesta.equalsIgnoreCase("S");
    }

    /**
     * Método que obtiene el rol de un jugador.
     * @param jugador Nombre del jugador.
     * @return Rol del jugador.
     */
    public String obtenerRolJugador(String jugador) {
        // Implementar la lógica para obtener el rol de un jugador
        // Por ahora, devolvemos un rol aleatorio
        return roles[(int) (Math.random() * roles.length)];
    }
}
