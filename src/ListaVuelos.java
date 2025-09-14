
import java.io.*;
import java.util.Scanner;


/**
 * Clase ListaVuelos que crea un objeto listaVuelos con la propiedad de capacidad, declarada en el constructor. Por otro lado,
 * se añaden los atributos de un valor entero ocupacion (Número entero que va contando la ocupación de la lista). Cuenta también con
 * una serie de getters para poder utilizar sus componentes en otras clases. Por último, integra funciones tales como:
 * estaLlena(Función booleana que devuelve true si la lista de vuelos está llena),
 * insertarVuelo(Función booleana que devuelve true si se ha podido insertar el vuelo pasado por parámetro,
 * además lo añade a la lista e incrementa en uno su ocupación),
 * buscarVuelo(función que recorre la lista de vuelos buscando un vuelo conociendo su id.
 * Si lo encuentra devuelve el vuelo),
 * buscarVuelos(función que recorre la lista de vuelos buscando un vuelo conociendo su codigo de origen y de destino.
 * Si lo encuentra devuelve el vuelo),
 * listarVuelos(Muestra por pantalla los vuelos de la lista),
 * seleccionarVuelo(Permite seleccionar un Vuelo existente a partir de su id,
 * usando el mensaje pasado como argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
 * La función solicita repetidamente hasta que se introduzca un id correcta),
 * escribirVuelosCsv(Función booleana que añade los vuelos al final de un fichero CSV sin sobreescribirlo,
 * si se ejecuta de forma correcta devuelve true),
 * leerVuelosCsv(Lee los vuelos del fichero CSV).
 * 
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class ListaVuelos {
    private final int capacidad;
    private final Vuelo[] vuelos;
    private int ocupacion;


    /**
     * Constructor of the class
     *
     * @param capacidad ocupación máxima de los vuelos.
     */
    public ListaVuelos(int capacidad) {
        this.capacidad = capacidad;
        vuelos = new Vuelo[capacidad];
        ocupacion = 0;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public boolean estaLlena() {
        return ocupacion==capacidad;
    }

    public Vuelo getVuelo(int i) {
        return vuelos[i];
    }

    public boolean insertarVuelo(Vuelo vuelo) {
        boolean insertado = false;
        if (!estaLlena()) {
            vuelos[ocupacion] = vuelo;
            ocupacion++;
            insertado=true;
        }
        return insertado;
    }

 
    public Vuelo buscarVuelo(String id) {
        Vuelo vuelo = null;
        if(ocupacion>0) {
            for (int i = 0; i < ocupacion; i++) {
                if (id.equalsIgnoreCase(vuelos[i].getID())) {
                    vuelo = vuelos[i];
                }
            }
        }
        return vuelo;


    }

    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaVuelos vuelo = null;
        for (int i = 0; i < ocupacion; i++) {
            if (vuelos[i].getOrigen().getCodigo().equalsIgnoreCase(codigoOrigen)&&vuelos[i].getDestino().getCodigo().equalsIgnoreCase(codigoDestino)
                    &&vuelos[i].getSalida().getDia()==fecha.getDia()&&vuelos[i].getSalida().getMes()==fecha.getMes()&&
                    vuelos[i].getSalida().getAnio()==fecha.getAnio()) {
                vuelo=new ListaVuelos(capacidad);
                vuelo.insertarVuelo(vuelos[i]);
            }
        }
        return vuelo;
    }

    public void listarVuelos() {
        for (int i = 0; i < ocupacion; i++) {
            System.out.println(vuelos[i].toString());
        }
    }


    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar) {
        String opcion;
        Vuelo vuelo=null;
        do {
            System.out.print(mensaje);
            opcion=teclado.nextLine();
            if (!opcion.equalsIgnoreCase(cancelar)) {
                for (int i = 0; i < ocupacion; i++) {
                    if (opcion.equalsIgnoreCase(getVuelo(i).getID())) {
                        vuelo = getVuelo(i);
                    }
                }
                if (vuelo == null) {
                    System.out.println("ID de vuelo no encontrado.");
                }
            }
        }while (vuelo==null && !opcion.equalsIgnoreCase(cancelar));
        return vuelo;

    }


    public boolean escribirVuelosCsv(String fichero) {
        boolean correcto = true;
        try (PrintWriter out = new PrintWriter(fichero)) {
            for (int i = 0; i < ocupacion; i++) {
                out.print(getVuelo(i).getID() + ";" + getVuelo(i).getAvion().getMatricula() + ";" + getVuelo(i).getOrigen().getCodigo() + ";" + getVuelo(i).getTerminalOrigen() + ";" + getVuelo(i).getSalida() + ";" + getVuelo(i).getDestino().getCodigo() + ";" + getVuelo(i).getTerminalDestino() + ";" + getVuelo(i).getLlegada() + ";" + getVuelo(i).getPrecio());
                out.println();
            }
        } catch (IOException ex) {
            System.out.println("Error de escritura en fichero " + ex.getMessage());
            correcto = false;
        }
        return correcto;
    }

    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos aeropuertos, ListaAviones aviones) {
        int contador = 0;
        ListaVuelos listaVuelos = null;
        try (Scanner entrada = new Scanner(new FileReader(fichero))) {
            listaVuelos = new ListaVuelos(capacidad);
            while (contador < capacidad) {
                String linea = entrada.nextLine();
                String[] separador = linea.split(";");
                listaVuelos.insertarVuelo(new Vuelo(separador[0], aviones.buscarAvion(separador[1]), aeropuertos.buscarAeropuerto(separador[2]), Integer.parseInt(separador[3]),
                        Fecha.fromString(separador[4]), aeropuertos.buscarAeropuerto(separador[5]), Integer.parseInt(separador[6]),
                        Fecha.fromString(separador[7]), Double.parseDouble(separador[8])));
                contador++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero Vuelos no encontrado.");
        } catch (IOException e) {
            System.out.println("Error de lectura de fichero Vuelos.");
        } finally {

            return listaVuelos;
        }
    }
}



