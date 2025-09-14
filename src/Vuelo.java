import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


/**
 * Clase vuelo que crea un objeto vuelo con las siguientes propiedades:id, avion, origen,
 * terminalOrigen, salida, destino, terminalDestino, llegada, precio. Además hay otras variables
 * declaradas como: asientos(matriz booleana que devuelve false si no está ocupada y true si lo está,
 * asientosLibres(número entero que lleva la cuenta de los asientos libres que tiene el avión del vuelo y
 * listaDeBilletes(almacena los billetes comprados para dicho vuelo, limitada por el número de asientos del avión.
 * Por otro lado, tiene lugar la implementación de funciones como numAsientoslibres (devuelve un entero con el número de
 * asientos libres que hay), vueloLleno (devuelve false si el vuelo no está lleno y true cuando sí lo está),
 * asientoOcupado (devuelve true si el asiento indicado mediante fila y columna está ocupado y false si no lo está),
 * buscarBillete (devuelve un billete que coincida con el localizador que se le pasa).
 * desocuparAsiento (busca el billete que se quiere desocupar a través del localizador, devuelve true si
 * se ha podido desocupar el asiento, eliminándolo también de la matriz de asientos),
 * añadirBilletesCsv (Añade los billetes al final de un fichero CSV, sin sobreescribirlo), dos funciones ToString (devuelven
 * una frase con distintas propiedades del vuelo),
 * coincide (//Devuelve true si el código origen, destino y fecha son los mismos que el vuelo),
 * imprimirMatrizAsientos(Muestra por pantalla la matriz de asientos del vuelo),
 * generarListaPasajeros (Devuelve true si ha podido escribir en un fichero la lista de pasajeros del vuelo),
 * generarID (Genera un ID de vuelo. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros serán PM y
 * los 4 siguientes serán números aleatorios. Ejemplo: PM0123) y
 * altaVuelo (Crea y devuelve un objeto Vuelo de los datos que selecciona el usuario de aeropuertos y aviones y la restricción de que no puede estar repetido el identificador,
 * la función solicita repetidamente los parametros hasta que sean correctos).
 *
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class Vuelo {
    private final String id;
    private final Avion avion;
    private final Aeropuerto origen;
    private final int terminalOrigen;
    private final Fecha salida;
    private final Aeropuerto destino;
    private final int terminalDestino;
    private final Fecha llegada;
    private final double precio;
    private final boolean asientos[][];
    private int asientosLibres;
    public ListaBilletes listaDeBilletes;




    /**
     * Constructor of the class
     *
     * @param id ID del vuelo: consiste en una cadena de 6 caracteres, de los cuales los dos primeros serás 'PM' y los 4 siguientes será dígitos aleatorios.
     * Cada vuelo debe tener un ID único. Ejemplo: PM1234.
     * @param avion Es el avión que se emplea en dicho vuelo. Ejemplo: EC-LKF
     * @param origen aeropuerto de origen del vuelo. Ejemplo: MAD
     * @param terminalOrigen Terminal del aeropuerto de origen. Ejemplo: 4
     * @param salida Fecha de salida del vuelo. Ejemplo: 24/12/2022 12:35:00
     * @param destino Aeropuerto de destino del vuelo. Ejemplo: BCN
     * @param terminalDestino Terminal del aeropuerto de destino. Ejemplo: 1
     * @param llegada Fecha de llegada del vuelo. Ejemplo: 24/12/2022 14:05:30
     * @param precio Precio base de un billete para dicho vuelo. Ejemplo: 100.0
     */
    public Vuelo(String id, Avion avion, Aeropuerto origen, int terminalOrigen, Fecha salida, Aeropuerto destino, int terminalDestino, Fecha llegada, double precio) {
        this.id = id;
        this.avion = avion;
        this.origen = origen;
        this.terminalOrigen = terminalOrigen;
        this.salida = salida;
        this.destino = destino;
        this.terminalDestino = terminalDestino;
        this.llegada = llegada;
        this.precio = precio;
        asientos = new boolean[getAvion().getFilas()][getAvion().getColumnas()];
        asientosLibres= avion.getFilas()* avion.getColumnas();
        listaDeBilletes=new ListaBilletes(avion.getFilas() * avion.getColumnas());
    }

    public String getID(){
        return id;
    }
    public Avion getAvion(){
        return avion;
    }
    public Aeropuerto getOrigen(){
        return origen;
    }
    public int getTerminalOrigen(){
        return terminalOrigen;
    }
    public Fecha getSalida(){
        return salida;
    }
    public Aeropuerto getDestino(){
        return destino;
    }
    public int getTerminalDestino(){
        return terminalDestino;
    }
    public Fecha getLlegada(){
        return llegada;
    }
    public double getPrecio(){
        return precio;
    }
    public double getPrecioPreferente(){
        return (getPrecio()+(getPrecio()*25/100));
    }//25% aumento
    public double getPrecioPrimera() {
        return (getPrecio()+(getPrecio()*50/100));
    }
    public int numAsientosLibres(){
        int asientosOcupados=0;
        for(int i = 1;i<getAvion().getFilas();i++){
            for(int j = 1;j<getAvion().getColumnas();j++){
                if(asientoOcupado(i, j)){
                    asientosOcupados++;
                }


            }
        }
        asientosLibres=(getAvion().getColumnas()*getAvion().getFilas())-asientosOcupados;
        return asientosLibres;

    }
    public boolean vueloLleno(){
        boolean vueloLleno = false;
        if(this.asientosLibres==0){
            vueloLleno=true;
        }
        return vueloLleno;
    }
    public boolean asientoOcupado(int fila, int columna) {
        boolean resultado=false;
        if(asientos[fila-1][columna-1]){
            resultado=true;
        }
        return resultado;
    }
    public Billete buscarBillete(String localizador){
        return listaDeBilletes.buscarBillete(localizador);
    }

    public Billete buscarBillete(int fila, int columna){
        return listaDeBilletes.buscarBillete(this.getID(),fila,columna);
    }
    public boolean ocuparAsiento(Billete billete){
        boolean ocupado = false;
        if(!vueloLleno()) {
            if (!asientos[billete.getFila() - 1][billete.getColumna() - 1] ) {
                ocupado = true;
                listaDeBilletes.insertarBillete(billete);
                asientos[billete.getFila() - 1][billete.getColumna() - 1] = true;
                asientosLibres--;
            }
        }
        return ocupado;
    }
    public boolean desocuparAsiento(String localizador){
        boolean desocupar = false;
        Billete billete=listaDeBilletes.buscarBillete(localizador);
        if(asientos[billete.getFila()-1][billete.getColumna()-1]){
            desocupar = true;
            asientos[billete.getFila()-1][billete.getColumna()-1]=false;
            asientosLibres++;
        }
        return desocupar;

    }
    public boolean aniadirBilletesCsv(String fichero){
        PrintWriter out=null;
        boolean correcto=true;
        try {
            out=new PrintWriter(fichero);
            if (listaDeBilletes.getOcupacion()>0){
                for(int i=0;i<listaDeBilletes.getOcupacion();i++){
                    out.println(listaDeBilletes.getBillete(i).toString());
                }
            }
        }catch (IOException ex) {
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
    @Override
    public String toString(){
        return "Vuelo "+id+" de "+origen.getCodigo()+" T"+terminalOrigen+" ("+salida+") a "+destino.getCodigo()+" T"+terminalDestino+" ("+llegada+")";
    }

    public String toStringSimple(){
        return "Vuelo "+id+" de "+origen.toStringSimple()+" "+terminalOrigen+" "+salida+" a "+destino.toStringSimple()+" "+terminalDestino+" "+llegada;
    }

    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        return codigoOrigen.equalsIgnoreCase(String.valueOf(origen)) && codigoDestino.equalsIgnoreCase(String.valueOf(destino)) && fecha.equals(llegada);
    }

    public void imprimirMatrizAsientos(){
        String columnas="ABCDEFGHIJKLMN";
        for (int i=0; i<this.avion.getColumnas();i++){
            System.out.print("  "+columnas.charAt(i));
        }
        System.out.println();
        for (int i=1;i<=avion.getFilas();i++){
            System.out.print(i);
            if(i==1){
                for (int j=1;j<= avion.getColumnas();j++){
                    if(asientoOcupado(i,j)){
                        System.out.print("(X)");
                    }else {
                        System.out.print("( )");
                    }
                }
            } else if (i>0&&i<5) {
                for (int j=1;j<= avion.getColumnas();j++){
                    if(asientoOcupado(i,j)){
                        System.out.print("{X}");
                    }else {
                        System.out.print("{ }");
                    }
                }
            } else {
                for (int j=1;j<= avion.getColumnas();j++){
                    if(asientoOcupado(i,j)){
                        System.out.print("[X]");
                    }else {
                        System.out.print("[ ]");
                    }
                }
            }
            System.out.println();
        }
    }

    public boolean generarListaPasajeros(String fichero){
        PrintWriter out = null;
        boolean correcto = true;
        try {
            out=new PrintWriter(fichero);
            out.println("""
                        --------------------------------------------------
                        ------- Lista de pasajeros en vuelo """+getID()+" -------\n" +
                    "--------------------------------------------------" );
            out.println("Asiento   Tipo      Pasajero");
            for (int i=0;i<listaDeBilletes.getOcupacion();i++){
                out.println(listaDeBilletes.getBillete(i).getAsiento()+"        "+listaDeBilletes.getBillete(i).getTipo()
                        + listaDeBilletes.getBillete(i).getPasajero().toString());
            }
        }catch (IOException ex) {
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


    public static String generarID(Random rand) {
        return "PM"+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10);
    }

    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos){
        Vuelo vuelo = null;
        double distancia;
        Fecha salida;
        Fecha llegada;
        String id;
        Aeropuerto origen = aeropuertos.seleccionarAeropuerto(teclado,"Ingrese código de Aeropuerto de Origen:");
        int terminalOrigen = Utilidades.leerNumero(teclado,"Ingrese Terminal Origen (1 - " + origen.getTerminales() + "):" ,1,origen.getTerminales());
        Aeropuerto destino = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
        int terminalDestino = Utilidades.leerNumero(teclado, "Ingrese Terminal Destino (1 - " + destino.getTerminales() + "):",1,destino.getTerminales());
        distancia = origen.distancia(destino);
        Avion avion = aviones.seleccionarAvion(teclado,"Ingrese matrícula de Avión:",distancia);
        do{
            salida = Utilidades.leerFechaHora(teclado,"Fecha de Salida:");
            llegada = Utilidades.leerFechaHora(teclado,"Fecha de Llegada:");
            if(!salida.anterior(llegada)){
                System.out.println("Llegada debe ser posterior a salida");
            }
        }while(!salida.anterior(llegada));
        double  precio = Utilidades.leerNumero(teclado,"Ingrese precio del pasaje:",0,Double.MAX_VALUE);
        do{
            id = generarID(rand);
            vuelo = new Vuelo(id,avion,origen,terminalOrigen,salida,destino,terminalDestino,llegada,precio);
            vuelos.insertarVuelo(vuelo);
        }while(vuelos.buscarVuelo(id)==null);
        System.out.println("Vuelo "+vuelo.getID()+" creado con éxito.");
        System.out.println();
        return vuelo;
    }


}


