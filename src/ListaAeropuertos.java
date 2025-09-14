import java.io.*;
import java.util.Scanner;

/**
 * Clase ListaAeropuertosque crea un objeto listaAviones con la propiedad de capacidad, declarada en el constructor. Por otro lado,
 * se añaden los atributos de un valor entero ocupacion (Número entero que va contando la ocupación de la lista). Cuenta también con
 * una serie de getters para poder utilizar sus componentes en otras clases. Por último, integra funciones tales como:
 * estaLlena(Función booleana que devuelve true si la lista de aviones está llena),
 * insertarAvion(Función booleana que devuelve true si se ha podido insertar el avión pasado por parámetro,
 * además lo añade a la lista e incrementa en uno su ocupación),
 * buscarAvion(función que recorre la lista de aviones buscando un avión conociendo su matrícula.
 * Si lo encuentra devuelve el avión),
 * listarBilletes(Muestra por pantalla los billetes de la lista),
 * seleccionarAvion(Permite seleccionar un Avión existente a partir de su matrícula,
 * usando el mensaje pasado como argumento para la solicitudy siguiendo el orden y los textos mostrados en el enunciado.
 * La función solicita repetidamente hasta que se introduzca una matrícula correcta, dado que el avión tendrá que tener un alcance suficiente),
 * aniadirAvionesCsv(Función booleana que añade los aviones al final de un fichero CSV sin sobreescribirlo,
 * si se ejecuta de forma correcta devuelve true),
 * leerAvionesCsv(Lee los aviones del fichero CSV).
 * 
 * @author Álvaro Cabello
 * @version     1.1
 */
public class ListaAeropuertos {
    private final int capacidad;
    private final Aeropuerto[] aeropuertos;
    private int ocupacion;


    /**
     * Constructor of the class
     *
     * @param capacidad es la cantidad de aviones que puede tener el aeropuerto.
     */
    public ListaAeropuertos(int capacidad){
        this.capacidad = capacidad;
        aeropuertos = new Aeropuerto[capacidad];
        ocupacion = 0;

    }
    public int getOcupacion(){
        return ocupacion;
    }
    public boolean estaLlena(){
        return capacidad==ocupacion;
    }
    public Aeropuerto getAeropuerto(int i){
        Aeropuerto sal=null;
        if ((i>0 )&&(i<this.ocupacion)){
            sal=aeropuertos[i];
        }
        return sal;
    }
    public boolean insertarAeropuerto(Aeropuerto aeropuerto){
        boolean insertado=false;
        if(!estaLlena()){
            aeropuertos[this.ocupacion] = aeropuerto;
            this.ocupacion++;
            insertado=true;
        }
        return insertado;
    }
    public Aeropuerto buscarAeropuerto(String codigo){
        Aeropuerto aeropuerto=null;
        for(int i =0;i<capacidad;i++){
            if(codigo.equalsIgnoreCase(aeropuertos[i].getCodigo())){
                aeropuerto=aeropuertos[i];
            }
        }
        return aeropuerto;
    }


    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje){
        Aeropuerto aeropuerto;
        boolean codigoCorrecto = false;
        do{
            System.out.print(mensaje);
            aeropuerto=buscarAeropuerto(teclado.nextLine());
            if(aeropuerto!=null && buscarAeropuerto(aeropuerto.getCodigo())!=null){
                codigoCorrecto=true;
            }
            if(!codigoCorrecto){
                System.out.println("Código de aeropuerto no encontrado.");
            }

        }while(!codigoCorrecto);
        return aeropuerto;
    }


    public boolean escribirAeropuertosCsv(String nombre){
        PrintWriter out=null;
        boolean correcto = true;
        try {
            out=new PrintWriter(nombre);
            for (int i = 0; i < getOcupacion(); i++) {
                out.println(getAeropuerto(i).getNombre() + ";" + getAeropuerto(i).getCodigo() + ";" + getAeropuerto(i).getLatitud() + ";" + getAeropuerto(i).getLongitud() + ";" + getAeropuerto(i).getTerminales());
            }
        }
        catch(IOException ex){
            System.out.println("Error de escritura en fichero "+ex.getMessage()+".");
            correcto=false;

        }finally {
            if(out!=null){
                out.close();
            }
        }
        return correcto;
    }


    public static ListaAeropuertos leerAeropuertosCsv(String fichero, int capacidad){
        ListaAeropuertos listaAeropuertos = new ListaAeropuertos(capacidad);
        Aeropuerto aeropuerto;
        FileReader archivo = null;
        try {
            archivo = new FileReader(fichero);
            try (BufferedReader buffer = new BufferedReader(archivo)) {
                String linea = buffer.readLine();
                while(linea != null){
                    String[] separador = linea.split(";");
                    aeropuerto = new Aeropuerto(separador[0],separador[1],Double.parseDouble(separador[2]),
                            Double.parseDouble(separador[3]),Integer.parseInt(separador[4]));
                    listaAeropuertos.insertarAeropuerto(aeropuerto);
                    linea = buffer.readLine();

                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block

            }

        }
        catch (FileNotFoundException e){
            System.out.println("Fichero "+e.getMessage()+" no encontrado.");
        }
        catch (IOException ex){
            System.out.println("Error de lectura de fichero "+ex.getMessage()+".");
        }
        finally{
            try{
                if(archivo!=null){
                    archivo.close();
                }

            }catch(IOException e){
                System.out.println("Error de cierre de fichero "+e.getMessage()+".");
            }
        }
        return listaAeropuertos;

    }
}

