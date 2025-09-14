

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;



/**
 * Clase billete que crea un objeto billete con las siguientes propiedades: localizador, vuelo, pasajero, tipo, fila, columna y precio.
 * Además, tiene una serie de getters que nos permiten la utilizacion de sus propiedades en otras clases. Por otro lado, tienen lugar varias
 * funciones como toString (devuelve una cadena de caracteres con las propiedades del billete), cancelar (cancela un billete, eliminándolo de
 * la lista de billetes del vuelo y del pasajero correspondiente, generarFactura (Se guarda la factura del billete, escribiéndola en un fichero)
 * 
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class Billete {
    private final String localizador;
    private final Vuelo vuelo;
    private Pasajero pasajero;
    private final TIPO tipo;
    private final int fila;
    private final int columna;
    private double precio;

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    enum TIPO {TURISTA, PREFERENTE, PRIMERA}

    /**
     * Constructor of the class
     *
     * @param localizador Es el localizador del billete. Este consistirá en una cadena de 10 caracteres, de los cuales los seis
     * primeros será el ID del vuelo asociado y los 4 siguientes serán letras mayúsculas aleatorias.
     * Cada Localizador debe ser único (por ejemplo, no puede haber dos Billetes con el Localizador
     * PM1234ABCD)
     * @param vuelo Vuelo asociado el billete.
     * @param pasajero Pasajero que posee el billete.
     * @param tipo Tipo de billete.  Será un valor de tipo enumerado que puede ser clase TURISTA, PREFERENTE
     * o PRIMERA. Para cualquier Vuelo de AirUPM, se define que los asientos de la primera fila sean
     * de tipo PRIMERA, los de las filas 2 a 5 de tipo PREFERENTE, y el resto de asientos de tipo
     * TURISTA
     * @param fila Fila del asiento asociado al billete.
     * @param columna Columna asociada al billete.
     * @param precio Precio del Billete. Para el Tipo de Billete TURISTA, el precio del Billete será el definido por el
     * Vuelo. Para el Tipo de Billete PREFERENTE, se aplicará un incremento del 25% al precio base
     * definido por el Vuelo. Para el Tipo de Billete PRIMERA, se aplicará un incremento del 50% al
     * precio base definido por el vuelo. Por ejemplo, para un Vuelo con Precio base de 100€, el
     * billete TURISTA vale 100€, el PREFERENTE 125€ y el PRIMERA 150€.
     */
    public Billete(String localizador, Vuelo vuelo, Pasajero pasajero, TIPO tipo, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    public String getLocalizador() {
        return localizador;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getAsiento() {
        char columnaletra = (char) (columna + 'A' - 1);
        return String.valueOf(fila) + columnaletra;
    }

    public double getPrecio() {
        if(null==tipo) {
            precio=getVuelo().getPrecio();
        } else precio = switch (tipo) {
            case PRIMERA -> getVuelo().getPrecioPrimera();
            case PREFERENTE -> getVuelo().getPrecioPreferente();
            default -> getVuelo().getPrecio();
        };
        return precio;
    }

    @Override
    public String toString() {
        return "Billete " + localizador + " para " + vuelo.toString() + " en asiento " + getAsiento() + " (" + getTipo() + ") por " + getPrecio() + "€";
    }

    public boolean cancelar() {
        pasajero.cancelarBillete(getLocalizador());
        vuelo.listaDeBilletes.eliminarBillete(getLocalizador());
        vuelo.desocuparAsiento(getLocalizador());
        System.out.println("Billete "+pasajero.buscarBillete(getLocalizador()).getLocalizador()+" cancelado.");
        return true;
    }

    public boolean generarFactura(String fichero) {
        boolean correcto = true;
        try(PrintWriter out = new PrintWriter(fichero)) {
            out.println("--------------------------------------------------");
            out.println("--------- Factura del billete " + getLocalizador() + " ---------");
            out.println("--------------------------------------------------");
            out.println("Vuelo: " + getVuelo().getID());
            out.println("Origen: " + getVuelo().getOrigen() + getVuelo().getTerminalOrigen());
            out.println("Destino: " + getVuelo().getDestino() + getVuelo().getTerminalDestino());
            out.println("Salida: " + getVuelo().getSalida());
            out.println("Llegada: " + getVuelo().getLlegada());
            out.println("Pasajero: " + getPasajero().toString());
            out.println("Tipo de billete: " + getTipo());
            out.println("Asiento: " + getAsiento());
            out.println("Precio: " + getPrecio() + "€");
        }
        catch (IOException ex){
            System.out.println("Error de escritura en fichero "+ex.getMessage()+".");
            correcto=false;
        }
        return correcto;
    }

    public static String generarLocalizador(Random rand, String idVuelo) {
        return idVuelo + (char) (rand.nextInt(27) + 'A') + (char) (rand.nextInt(27) + 'A') + (char) (rand.nextInt(27) + 'A') + (char) (rand.nextInt(27) + 'A');
    }

    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero) {
        vuelo.imprimirMatrizAsientos();
        System.out.println("Tipo de asiento: '[ ]' = TURISTA, '{ }' = PREFERENTE, '( )' = PRIMERA");
        String asiento;
        int fila;
        char columna;
        double precio;
        Billete billete = null;
        TIPO tipo1;
        String localizador = null;

        do {
            fila = Utilidades.leerNumero(teclado, "Ingrese fila del asiento (1-6):", 1, 6);
            columna = Utilidades.leerLetra(teclado, "Ingrese columna del asiento (A-C):", 'A', 'C');
            asiento = String.valueOf(fila) + columna;
            if (vuelo.asientoOcupado(fila,(int)columna-64)) {
                System.out.println("El asiento " + asiento + " ya está reservado.");
            } else {
                localizador = Billete.generarLocalizador(rand, vuelo.getID());
            }
            if(fila<=1){
                tipo1 = TIPO.PRIMERA;
                precio = vuelo.getPrecioPrimera();
            } else if (fila<=5) {
                tipo1 = TIPO.PREFERENTE;
                precio = vuelo.getPrecioPreferente();
            } else {
                tipo1 = TIPO.TURISTA;
                precio = vuelo.getPrecio();
            }
        } while (vuelo.asientoOcupado(fila,(int)columna-64));
        billete = new Billete(localizador,vuelo,pasajero,tipo1,fila,(int)columna-64,precio);
        vuelo.ocuparAsiento(billete);
        return billete;
    }
}


