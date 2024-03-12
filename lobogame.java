package lobo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class HombresLobo {

    private static String[] roles = {"Lobo", "Aldeano", "Cazador", "Bruja", "Vidente"};
    private static ArrayList<String> jugadores = new ArrayList<>();
    private HashMap<String, String> rolesJugadores = new HashMap<>();

    public static void main(String[] args) {
        HombresLobo juego = new HombresLobo();
        juego.iniciarPartida();
    }

    public void iniciarPartida() {
        Scanner scanner = new Scanner(System.in);
        boolean jugarOtraPartida = true;

        while (jugarOtraPartida) {
            iniciarJuego(scanner);
            jugarOtraPartida = preguntarRepetirPartida(scanner);
        }

        scanner.close();
    }

    public void iniciarJuego(Scanner scanner) {
        System.out.println("Bienvenido a Hombres Lobo de Castronegro!");

        // Verificar el número de jugadores
        int cantidadJugadores = obtenerCantidadJugadores(scanner);
        while (cantidadJugadores != roles.length) {
            System.out.println("El número de jugadores debe ser igual al número de roles disponibles (" + roles.length + ").");
            cantidadJugadores = obtenerCantidadJugadores(scanner);
        }

        jugadores.clear();
        for (int i = 0; i < cantidadJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombre = scanner.next();
            while (esNumero(nombre)) {
                System.out.println("Error: El nombre no puede contener números.");
                System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
                nombre = scanner.next();
            }
            jugadores.add(nombre);
        }

        asignarRoles();

        // Iniciar la ronda 1 o noche 1
        iniciarRonda(scanner);

        // Iniciar la fase de discusión para eliminar un jugador
        iniciarDia(scanner);
    }

    public int obtenerCantidadJugadores(Scanner scanner) {
        int cantidadJugadores = 0;

        do {
            System.out.print("Ingrese la cantidad de jugadores (mínimo 5): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Error: Debes ingresar un número.");
                System.out.print("Ingrese la cantidad de jugadores (mínimo 5): ");
                scanner.next();
            }
            cantidadJugadores = scanner.nextInt();

            if (cantidadJugadores < 4) {
                System.out.println("Error: Se necesitan al menos 4 jugadores para iniciar el juego.");
            }
        } while (cantidadJugadores < 4);

        return cantidadJugadores;
    }

    public void asignarRoles() {
        System.out.println("Roles asignados:");
        ArrayList<String> rolesDisponibles = new ArrayList<>(Arrays.asList(roles)); // Lista de roles disponibles
        Collections.shuffle(rolesDisponibles); // Barajar roles disponibles
        for (String jugador : jugadores) {
            if (!rolesDisponibles.isEmpty()) {
                String rolAsignado = rolesDisponibles.remove(0); // Tomar el primer rol de la lista
                System.out.println(jugador + ": " + rolAsignado);
                rolesJugadores.put(jugador, rolAsignado); // Guardar el rol asignado para el jugador
            } else {
                // Si se acaban los roles disponibles, asignar aldeano
                System.out.println(jugador + ": Aldeano");
                rolesJugadores.put(jugador, "Aldeano"); // Guardar el rol asignado como Aldeano
            }
        }
    }

    public void iniciarRonda(Scanner scanner) {
        System.out.println("¡Comienza la ronda 1 o noche 1!");

        // Acciones de los jugadores nocturnos (lobo, bruja, vidente)
        for (String jugador : jugadores) {
            String rol = obtenerRolJugador(jugador);
            switch (rol) {
                case "Lobo":
                    realizarAccionLobo(jugador, scanner);
                    break;
                case "Bruja":
                    realizarAccionBruja(scanner);
                    break;
                case "Vidente":
                    realizarAccionVidente(jugador, scanner);
                    break;
                default:
                    // Otros roles no tienen acciones especiales durante la noche
                    break;
            }
        }

        // Verificar si el lobo ha matado al cazador y a la bruja
        boolean cazadorVivo = jugadores.contains("Cazador");
        boolean brujaViva = jugadores.contains("Bruja");
        if (!cazadorVivo && !brujaViva) {
            System.out.println("El lobo ha matado al cazador y a la bruja. ¡Los lobos ganan!");
        }
    }

    private void realizarAccionVidente(String jugador, Scanner scanner) {
        System.out.println(jugador + " (Vidente) está seleccionando a quién investigar...");

        String jugadorConMasLetrasParecidas = "";
        int maxLetrasParecidas = 0;

        for (String j : jugadores) {
            if (!j.equals(jugador)) {
                int letrasParecidas = contarLetrasParecidas(jugador, j);
                if (letrasParecidas > maxLetrasParecidas) {
                    maxLetrasParecidas = letrasParecidas;
                    jugadorConMasLetrasParecidas = j;
                }
            }
        }

        System.out.println(jugador + " (Vidente) ha investigado a " + jugadorConMasLetrasParecidas +
                " y ha visto que su rol es: " + obtenerRolJugador(jugadorConMasLetrasParecidas));
    }

    private int contarLetrasParecidas(String jugador, String j) {
        int contador = 0;
        for (int i = 0; i < jugador.length(); i++) {
            if (j.contains(Character.toString(jugador.charAt(i)))) {
                contador++;
            }
        }
        return contador;
    }

    private void realizarAccionBruja(Scanner scanner) {
        System.out.println("La Bruja está decidiendo si utilizar sus pociones...");
        int decision = (int) (Math.random() * 2); // 0 para no utilizar la poción, 1 para utilizarla
        if (decision == 0) {
            System.out.println("La Bruja decide no utilizar sus pociones esta noche.");
        } else {
            int accion = (int) (Math.random() * 2); // 0 para curar, 1 para matar
            String accionStr = (accion == 0) ? "curar" : "matar";
            System.out.println("La Bruja decide utilizar su poción para " + accionStr + ".");
            if (accion == 0) {
                System.out.println("La Bruja ha curado al lobo. ¡Habrá otra noche!");
            } else {
                System.out.println("La Bruja ha matado al lobo. ¡El lobo está muerto!");
                if (!jugadores.contains("Lobo")) {
                    System.out.println("El lobo ha sido eliminado, pero el juego continuará hasta que el cazador sea eliminado.");
                }
            }
        }
    }

    private void realizarAccionLobo(String jugador, Scanner scanner) {
        System.out.println(jugador + " (Lobo) está decidiendo a quién atacar...");
        System.out.println("Los jugadores disponibles para atacar son:");
        for (String j : jugadores) {
            if (!j.equals(jugador)) {
                System.out.println("- " + j);
            }
        }
        System.out.print("Ingrese el nombre del jugador a atacar: ");
        String objetivo = scanner.next();
        while (!jugadores.contains(objetivo) || objetivo.equals(jugador)) {
            System.out.print("Nombre inválido. Ingrese nuevamente el nombre del jugador a atacar: ");
            objetivo = scanner.next();
        }
        System.out.println(jugador + " (Lobo) ha atacado a " + objetivo);
    }


    public void iniciarDia(Scanner scanner) {
        System.out.println("¡Comienza el día!");

        System.out.println("Hablen los jugadores. Debatan y voten para eliminar a un jugador sospechoso.");

        // Aquí podría implementarse la lógica para la discusión y votación entre los jugadores
        // Por ejemplo, cada jugador podría expresar su opinión sobre quién podría ser un lobo, y luego se realiza una votación para eliminar a un jugador.

        // Acción del cazador
        if (jugadores.contains("Cazador")) {
            System.out.print("Cazador, decide a quién quieres matar: ");
            String objetivo = scanner.next();
            while (!jugadores.contains(objetivo)) {
                System.out.print("Nombre inválido. Ingresa nuevamente el nombre del jugador a matar: ");
                objetivo = scanner.next();
            }
            if (objetivo.equals("Lobo")) {
                System.out.println("El cazador ha matado al lobo. ¡Los aldeanos ganan!");
                return;
            } else if (objetivo.equals("Bruja") || objetivo.equals("Aldeano")) {
                System.out.println("El cazador ha matado a un " + objetivo + ". Se pasará a la segunda ronda.");
                iniciarRonda(scanner);
            }
        }
    }

    public boolean hayGanador() {
        int cantidadLobos = 0;
        int cantidadAldeanos = 0;

        for (String jugador : jugadores) {
            String rol = rolesJugadores.get(jugador);
            if (rol.equals("Lobo")) {
                cantidadLobos++;
            } else if (rol.equals("Aldeano")) {
                cantidadAldeanos++;
            }
        }

        return cantidadLobos == 0 || cantidadAldeanos == 0;
    }

    public boolean preguntarRepetirPartida(Scanner scanner) {
        System.out.print("¿Deseas jugar otra partida? (S/N): ");
        String respuesta = scanner.next();
        return respuesta.equalsIgnoreCase("S");
    }

    public String obtenerRolJugador(String jugador) {
        return rolesJugadores.get(jugador);
    }

    public boolean esNumero(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
