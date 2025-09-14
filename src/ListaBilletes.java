

import java.io.*;
import java.util.Scanner;
/**
 * Clase ListaBilletes que crea un objeto listaBilletes con la propiedad de capacidad, declarada en el constructor. Por otro lado,
 * se añaden los atributos de un valor entero ocupacion (Número entero que va contando la ocupación de la lista). Cuenta también con
 * una serie de getters para poder utilizar sus componentes en otras clases. Por último, integra funciones tales como:
 * estaLlena(Función booleana que devuelve true si la lista de billetes está llena),
 * insertarBillete(Función booleana que devuelve true si se ha podido insertar el billete pasado por parámetro,
 * además lo añade a la lista e incrementa en uno su ocupación),
 * (*2)buscarBillete(Funciones que recorren la lista de billetes buscando un billete conociendo su localizador o el idVuelo, fila y columna.
 * Si lo encuentra devuelve el billete),
 * eliminarBillete(Función booleana que recorre la lista de billetes buscando uno a partir de un localizador,
 * si lo encuentra lo elimina y devuelve true),
 * listarBilletes(Muestra por pantalla los billetes de la lista),
 * seleccionarBillete(Permite seleccionar un billete existente a partir de su localizador,
 * usando el mensaje pasado como argumento para la solicitudy siguiendo el orden y los textos mostrados en el enunciado.
 * La función solicita repetidamente hasta que se introduzca un localizador correcto),
 * aniadirBilletesCsv(Función booleana que añade los billetes al final de un fichero CSV sin sobreescribirlo,
 * si se ejecuta de forma correcta devuelve true),
 * leerBilletesCsv(Lee los billetes del fichero CSV y los añade a las listas de sus respectivos Vuelos y Pasajeros).
 * 
 * @author Álvaro Cabello
 * @version     1.1
 */
public class ListaBilletes {
    private final int capacidad;
    private int ocupacion;
    private final Billete[] billetes;
    /**
     * Constructor of the class
     *
     * @param capacidad Ocupación máxima que tiene la lista (por eso es final, pues no varía).
     */
    public ListaBilletes(int capacidad){
        this.capacidad = capacidad;
        ocupacion = 0;
        billetes = new Billete[capacidad];
    }
    public int getOcupacion(){
        return ocupacion;
    }
    public boolean estaLlena(){
        return ocupacion==capacidad;
    }
    public Billete getBillete(int i){
        return billetes[i];
    }
    public boolean insertarBillete (Billete billete){
        boolean insertado = false;
        if(!estaLlena()){
            billetes[ocupacion]=billete;
            ocupacion++;
            insertado=true;
        }
        return insertado;
    }
    public Billete buscarBillete (String localizador){
        Billete billete = null;
        for(int i = 0; i< ocupacion;i++){
            if(localizador.equalsIgnoreCase(getBillete(i).getLocalizador())){
                billete = billetes[i];
            }

        }
        return billete;
    }
    public Billete buscarBillete (String idVuelo, int fila, int columna){
        Billete billete = null;
        for(int i = 0;i<ocupacion;i++){
            if(billetes[i].getFila()==fila && billetes[i].getColumna()==columna && billetes[i].getVuelo().getID().equalsIgnoreCase(idVuelo)){
                billete = billetes[i];
            }
        }
        return billete;
    }
    public boolean eliminarBillete (String localizador){
        boolean eliminarBillete = false;
        for(int i = 0; i< ocupacion;i++){
            if(localizador.equalsIgnoreCase(getBillete(i).getLocalizador())){
                billetes[i]=null;
                eliminarBillete=true;
                ocupacion--;

            }

        }
        return eliminarBillete;
    }

    public void listarBilletes(){
        for(int i=0;i<getOcupacion();i++){
            System.out.println(billetes[i].toString());
        }
    }
    
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        Billete billete=null;
        do {
            System.out.print(mensaje);
            String localizador = teclado.next();
            billete = buscarBillete(localizador);
            if (billete == null) {
                System.out.println("Localizador no encontrado.");
            }
        }while (billete==null);
        return billete;
    }

    public boolean aniadirBilletesCsv(String fichero){
        boolean correcto = true;
        try (PrintWriter out = new PrintWriter(fichero)) {
            for (int i = 0; i < ocupacion; i++) {
                out.println(getBillete(i).getLocalizador() + ";" );
            }
        }
        catch(IOException ex){
            System.out.println("Error de escritura en fichero "+ex.getMessage()+".");
            correcto=false;

        }
        return correcto;
    }


    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros){
        Billete billete;
        Pasajero pasajero;
        Vuelo vuelo;
        FileReader archivo = null;
        try {
            archivo = new FileReader(ficheroBilletes);
            try (BufferedReader buffer = new BufferedReader(archivo)) {
                String linea = buffer.readLine();
                while (linea != null) {
                    String[] separador = linea.split(";");
                    Billete.TIPO tipo = Billete.TIPO.valueOf(separador[3]);
                    pasajero = pasajeros.buscarPasajeroDNI(separador[2]);
                    vuelo = vuelos.buscarVuelo(separador[1]);
                    billete = new Billete(separador[0], vuelo, pasajero, tipo, Integer.parseInt(separador[4]),
                            Integer.parseInt(separador[5]), Double.parseDouble(separador[6]));
                    if(pasajero!=null&&vuelo!=null) {
                        pasajero.listaBilletes.insertarBillete(billete);
                        vuelo.ocuparAsiento(billete);
                    }
                    linea = buffer.readLine();
                }
            } catch (NumberFormatException e) {
           }

        } catch (FileNotFoundException exception) {
            System.out.println("Fichero " + exception.getMessage()+" no encontrado.");
        } catch (IOException e) {
            System.out.println("Error de lectura de fichero " + e.getMessage()+".");
        } finally {
            try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                System.out.println("Error de cierre de fichero "+e.getMessage()+".");
            }

        }
    }
}


