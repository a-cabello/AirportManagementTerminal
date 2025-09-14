
import java.io.*;
import java.util.Scanner;

/**
 * Clase ListaPasajeros que crea un objeto listaPasajeros con la propiedad de capacidad, declarada en el constructor. Por otro lado,
 * se añaden los atributos de un valor entero ocupacion (Número entero que va contando la ocupación de la lista). Cuenta también con
 * una serie de getters para poder utilizar sus componentes en otras clases. Por último, integra funciones tales como:
 * estaLlena(Función booleana que devuelve true si la lista de pasajeros está llena),
 * insertarPasajero(Función booleana que devuelve true si se ha podido insertar el billete pasado por parámetro,
 * además lo añade a la lista e incrementa en uno su ocupación),
 * (*2)buscarPasajero(Funciones que recorren la lista de billetes buscando un billete conociendo su email o el DNI.
 * Si lo encuentra devuelve el pasajero),
 * seleccionarPasajero(Permite seleccionar un pasajero existente a partir de su DNI,
 * usando el mensaje pasado como argumento para la solicitudy siguiendo el orden y los textos mostrados en el enunciado.
 * La función solicita repetidamente hasta que se introduzca un DNI correcto),
 * escribirPasajerosCsv(Función booleana que añade los pasajeros al final de un fichero CSV sin sobreescribirlo,
 * si se ejecuta de forma correcta devuelve true),
 * leerPasajerosCsv(Lee los billetes del fichero CSV y los añade a las listas de sus respectivos Vuelos y Pasajeros).
 * 
 * @author Álvaro Cabello
 * @version     1.1
 */
public class ListaPasajeros {
    private final int capacidad;
    private int ocupacion;
    private final Pasajero[] pasajeros;

    /**
     * Constructor of the class
     *
     * @param capacidad ocupación máxima.
     */
    public ListaPasajeros(int capacidad) {
        this.capacidad = capacidad;
        ocupacion = 0;
        pasajeros = new Pasajero[capacidad];
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public boolean estaLlena() {
        return capacidad==ocupacion;
    }

    public Pasajero getPasajero(int i) {
        return pasajeros[i];
    }

    public boolean insertarPasajero(Pasajero pasajero) {
        boolean insertado = false;
        if (!estaLlena()) {
            pasajeros[getOcupacion()] = pasajero;
            ocupacion++;
            insertado=true;
        }
        return insertado;
    }

    public Pasajero buscarPasajeroDNI(String dni) {
        Pasajero pasajero = null;
        if(ocupacion>0) {
            for (int i = 0; i < ocupacion; i++) {
                if (dni.equalsIgnoreCase(pasajeros[i].getDNI())) {
                    pasajero = pasajeros[i];
                }
            }
        }
        return pasajero;
    }

    public Pasajero buscarPasajeroEmail(String email) {
        Pasajero pasajero = null;
        if(ocupacion>0) {
            for (int i = 0; i < ocupacion; i++) {
                if (email.equalsIgnoreCase(pasajeros[i].getEmail())) {
                    pasajero = pasajeros[i];
                }
            }
        }
        return pasajero;
    }

    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje) {
        String dni;
        Pasajero pasajero;
        do {
            System.out.print(mensaje);
            dni=teclado.next();
            pasajero = buscarPasajeroDNI(dni);
            if(pasajero == null){
                System.out.println("DNI no encontrado");
            }

        } while (pasajero == null);
        return pasajero;

    }

    public boolean escribirPasajerosCsv(String fichero) {
        PrintWriter out = null;
        boolean correcto = true;
        try {
            out = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i++) {
                out.print(getPasajero(i).getNombre() + "; " + getPasajero(i).getApellidos() + "; "
                        + getPasajero(i).getDNI() + ";" + getPasajero(i).getLetraDNI() + "; " +
                        getPasajero(i).getEmail());
                out.println();
            }
        }
        catch (IOException ex) {
            System.out.println("Error de escritura en fichero " + ex.getMessage());
            correcto = false;
        }
        finally{
            if(out!=null){
                out.close();
            }
        }
        return correcto;
    }

    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajero) {
    ListaPasajeros listaPasajeros = new ListaPasajeros(capacidad);

        try (BufferedReader buffer = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = buffer.readLine()) != null) {
                try {
                    String[] separador = linea.split(";");
                    Pasajero pasajero = new Pasajero(
                        separador[0],
                        separador[1],
                        Long.parseLong(separador[2]),
                        separador[3].charAt(0),
                        separador[4],
                        maxBilletesPasajero
                    );
                    listaPasajeros.insertarPasajero(pasajero);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error al procesar la línea: " + linea + " -> " + e.getMessage());
                    // Aquí decides si ignoras la línea o terminas la lectura
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero " + fichero + " no encontrado.");
        } catch (IOException e) {
            System.out.println("Error de lectura de fichero " + e.getMessage() + ".");
        }

    return listaPasajeros;
}

}


