
import java.util.Random;
import java.util.Scanner;

/**
 * Es la clase principal, desde la cual se ejecutarán todos los métodos
 * necesarios para su correcto funcionamiento. Estará compuesto por los siguientes métodos:
 * cargarDatos(Lee los datos de los ficheros especificados y los agrega a AirUPM),
 * guardarDatos(Almacena los datos de AirUPM en los ficheros CSV especificados),
 * maxVuelosAlcanzado(devuelve true si se ha alcanzado el máximo de vuelos),
 * insertarVuelo(introduce un nuevo vuelo en la listaVuelos),
 * maxPasajerosAlcanzado(devuelve true si se ha alcanzado el máximo de pasajeros),
 * insertarPasajero(introduce un nuevo pasajero en la listaPasajeros),
 * buscarVuelo(Funcionalidad buscarVuelo especificada en el enunciado del proyecto, que devuelve una lista de vuelos entre dos aeropuertos y
 * con una fecha de salida solicitados por teclado al usuario en el orden y con los textos indicados en los ejemplos de
 * ejecución del enunciado),
 * comprarBillete( Funcionalidad comprarBillete especificada en el enunciado del proyecto, que compra un billete para un vuelo especificado,
 * pidiendo por teclado los datos necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del
 * enunciado. Si la lista de pasajeros está vacía, creará un nuevo pasajero, si está llena seleccionará un pasajero, en cualquier
 * otro caso, deberá preguntar al usuario si crear o seleccionar),
 * menu(Muestra el menú y solicita una opción por teclado),
 * main(Carga los datos de los ficheros CSV pasados por argumento (consola) en AirUPM, llama iterativamente al menú y realiza la
 * opción especificada hasta que se indique la opción Salir, y finalmente guarda los datos de AirUPM en los mismos ficheros CSV).
 * 
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class Main {
    private final int maxAeropuertos;
    private final int maxAviones;
    private final int maxVuelos;
    private final int maxPasajeros;
    private final int maxBilletesPasajero;
    private ListaAeropuertos listaDeAeropuertos;
    private ListaAviones listaDeAviones;
    private ListaVuelos listaDeVuelos;
    private ListaPasajeros listaDePasajeros;
    private ListaBilletes listaDeBilletes;
    /**
     * Constructor of the class
     * HECHO
     * @param maxAeropuertos número máximo de aeropuertos.
     * @param maxAviones número máximo de aviones.
     * @param maxVuelos número máximo de vuelos.
     * @param maxPasajeros número máximo de pasajeros.
     * @param maxBilletesPasajero número máximo de billetes
     */
    public Main(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero){
        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajero = maxBilletesPasajero;
    }
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        listaDeAeropuertos=ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos,maxAeropuertos);
        listaDeAviones=ListaAviones.leerAvionesCsv(ficheroAviones,maxAviones);
        listaDeVuelos=ListaVuelos.leerVuelosCsv(ficheroVuelos,maxVuelos,listaDeAeropuertos,listaDeAviones);
        listaDePasajeros=ListaPasajeros.leerPasajerosCsv(ficheroPasajeros,maxPasajeros,maxBilletesPasajero);
        ListaBilletes.leerBilletesCsv(ficheroBilletes,listaDeVuelos,listaDePasajeros);

    }
    public boolean guardarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes){
        boolean aeropuertos, aviones, vuelos, pasajeros;
        ListaAeropuertos listaAeropuertos = new ListaAeropuertos(maxAeropuertos);
        ListaAviones listaAviones = new ListaAviones(maxAviones);
        ListaVuelos listaVuelos = new ListaVuelos(maxVuelos);
        ListaPasajeros listaPasajeros = new ListaPasajeros(maxPasajeros);
        aeropuertos = listaAeropuertos.escribirAeropuertosCsv(ficheroAeropuertos);
        aviones = listaAviones.escribirAvionesCsv(ficheroAviones);
        vuelos = listaVuelos.escribirVuelosCsv(ficheroVuelos);
        pasajeros = listaPasajeros.escribirPasajerosCsv(ficheroPasajeros);
        return aeropuertos && aviones && vuelos && pasajeros;
    }
    public boolean maxVuelosAlcanzado(){
        boolean maxVuelosAlcanzado=false;
        if (listaDeVuelos.getOcupacion()==maxVuelos){
            maxVuelosAlcanzado=true;
        }
        return maxVuelosAlcanzado;
    }
    public boolean insertarVuelo (Vuelo vuelo) {
        return listaDeVuelos.insertarVuelo(vuelo);
    }
    public boolean maxPasajerosAlcanzado(){
        return listaDePasajeros.estaLlena();
    }

    public boolean insertarPasajero (Pasajero pasajero){
        return listaDePasajeros.insertarPasajero(pasajero);
    }


    public ListaVuelos buscarVuelo(Scanner teclado){
        Aeropuerto origen;
        Aeropuerto destino;
        Fecha salida;
        Vuelo vuelo;
        ListaVuelos resultado;
        origen = listaDeAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto de Origen:");
        destino = listaDeAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
        salida = Utilidades.leerFecha(teclado, "Fecha de Salida:");
        resultado=listaDeVuelos.buscarVuelos(origen.getCodigo(), destino.getCodigo(), salida);
        if (resultado!=null) {
            resultado.listarVuelos();
            vuelo = listaDeVuelos.seleccionarVuelo(teclado, "Ingrese ID de vuelo para comprar billete o escriba CANCELAR:", "CANCELAR");
            if (vuelo != null) {
                comprarBillete(teclado, new Random(), vuelo);
                resultado.insertarVuelo(vuelo);
            }
        }else{
            System.out.println("No se ha encontrado ningún vuelo.");
        }
        return resultado;
    }

    public void comprarBillete(Scanner teclado, Random rand, Vuelo vuelo){
        String dni="";
        char opcion=0;
        Billete billete=null;
        Pasajero pasajero = null;
        if(!listaDeBilletes.estaLlena()) {
            do{
                opcion = Utilidades.leerLetra(teclado,"¿Comprar billete para un nuevo pasajero (n)," +
                        " o para uno ya existente (e)?",'a','z');
                if(opcion!='e'&&opcion!='n'){
                    System.out.println("El valor de entrada debe ser 'n' o 'e'");
                }
            }while (opcion!='e'&&opcion!='n');
            if (opcion == 'e') {
                do {
                    System.out.print("Ingrese DNI del pasajero:");
                    dni = teclado.nextLine();
                    pasajero=listaDePasajeros.buscarPasajeroDNI(dni);
                    if(pasajero == null){
                        System.out.println("DNI no encontrado.");
                    }
                } while (pasajero == null);
                if(pasajero.maxBilletesAlcanzado()){
                    System.out.println("El Pasajero seleccionado no puede adquirir más billetes.");
                }else {
                    billete = Billete.altaBillete(teclado, rand, vuelo, listaDePasajeros.buscarPasajeroDNI(dni));
                }
            } else {
                pasajero = Pasajero.altaPasajero(teclado, listaDePasajeros, maxBilletesPasajero);
                billete = Billete.altaBillete(teclado, rand, vuelo, pasajero);
            }
            if(billete!=null){
                System.out.println("Billete " + billete.getLocalizador() +" comprado con éxito.");
            }
        }else{
            System.out.println("El vuelo "+vuelo.getID()+ "está lleno, no se pueden comprar más billetes\n");
        }
    }


    public static int menu(Scanner teclado){
        int opt;
        System.out.println("1. Alta Vuelo");
        System.out.println("2. Alta Pasajero");
        System.out.println("3. Buscar vuelo");
        System.out.println("4. Mostrar billetes de Pasajero");
        System.out.println("5. Generar lista de Pasajeros");
        System.out.println("0. Salir");
        opt = Utilidades.leerNumero(teclado, "Seleccione opción:", 0, 5);
        return opt;
    }

    public static void main(String[] args){
        Main Air = new Main(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        Air.listaDeAeropuertos = new ListaAeropuertos(Air.maxAeropuertos);
        Air.listaDeAviones = new ListaAviones(Air.maxAviones);
        Air.listaDePasajeros = new ListaPasajeros(Air.maxPasajeros);
        Air.listaDeBilletes = new ListaBilletes(Air.maxBilletesPasajero*Air.maxPasajeros);
        Air.listaDeVuelos = new ListaVuelos(Air.maxVuelos);
        Scanner teclado = new Scanner(System.in);
        Air.cargarDatos("../aeropuertos.csv","../aviones.csv","../vuelos.csv","../pasajeros.csv","../billetes.csv");
        int opcion,cont1=0;
        Vuelo vuelo;
        Pasajero pasajero ;
        Billete billete;
        do{
            opcion=menu(new Scanner(System.in));
            switch (opcion){
                case 1 -> {
                    if(cont1==0){
                        vuelo = Vuelo.altaVuelo(teclado,new Random(),Air.listaDeAeropuertos,Air.listaDeAviones,Air.listaDeVuelos);
                        Air.insertarVuelo(vuelo);
                        cont1++;
                    }else{
                        System.out.println("No se pueden dar de alta más vuelos.");
                    }
                }

                case 2 -> {
                    pasajero = Pasajero.altaPasajero(teclado,Air.listaDePasajeros, Air.maxBilletesPasajero);
                    Air.insertarPasajero(pasajero);
                }

                case 3 -> Air.buscarVuelo(teclado);
                case 4 -> {
                    pasajero = Air.listaDePasajeros.seleccionarPasajero(teclado,"Ingrese DNI del pasajero: ");
                    if(pasajero.listaBilletes.getOcupacion()>0){
                        pasajero.listarBilletes();
                    }else {
                        System.out.println("El pasajero seleccionado no ha adquirido ningún billete.");
                    }
                    billete = pasajero.listaBilletes.seleccionarBillete(teclado,"Ingrese localizador del billete: ");
                    char letra = ' ';
                    do{
                        System.out.print("¿Generar factura del billete (f), cancelarlo (c) o volver al menú (m)?" );
                        letra=teclado.next().charAt(0);
                        if(letra != 'f'&&letra!='c'&&letra!='m'){
                            System.out.println("El valor de entrada debe ser 'f', 'c' o 'm'");
                        }
                    }while(letra != 'f'&&letra!='c'&&letra!='m');
                    if(letra == 'f'){
                        System.out.print("Introduzca la ruta donde generar la factura: ");
                        String fichero = teclado.next();
                        if(billete.generarFactura(fichero)){
                            System.out.println("factura de billete "+billete.getLocalizador()+" generada en "+fichero);
                        }
                    }else if(letra == 'c'){
                        System.out.println("Billete  cancelado.");
                        
                    }
                }

                case 5 -> {
                    String idVuelo;
                    String ruta;
                    System.out.print("Ingrese ID del vuelo:");
                    idVuelo=teclado.next();
                    System.out.print("Introduzca la ruta donde generar la lista de pasajeros:");
                    ruta=teclado.next();
                    Air.listaDeVuelos.buscarVuelo(idVuelo).generarListaPasajeros(ruta);
                    System.out.println("Lista de pasajeros del Vuelo "+idVuelo+" generada en "+ruta);
                }

            }
        }while(opcion!=0);



    }

    public int getMaxVuelos() {
        return maxVuelos;
    }

    public int getMaxPasajeros() {
        return maxPasajeros;
    }

}

