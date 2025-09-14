

import java.io.*;
import java.util.Scanner;


/**
 * Clase ListaAviones que crea un objeto listaAviones con la propiedad de capacidad, declarada en el constructor. Por otro lado,
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
public class ListaAviones {
    private final int capacidad;
    private Avion[] aviones;
    private int ocupacion;

    /**
     * Constructor of the class
     *
     * @param capacidad es la cantidad de pasajeros que puede llevar el avión.
     */
    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
        aviones = new Avion[capacidad];
        ocupacion=0;
    }
    public int getOcupacion(){
        return ocupacion;
    }
    public boolean estaLlena(){
        return capacidad==ocupacion;
    }
    public Avion getAvion(int posicion){
        return aviones [posicion];
    }
    public boolean insertarAvion(Avion avion) {
        boolean insertado=false;
        if(!estaLlena()) {
            aviones[getOcupacion()] = avion;
            ocupacion++;
            insertado=true;

        }
        return insertado;
    }


    public Avion buscarAvion(String matricula){
        Avion avion = null;
        if(ocupacion>0) {
            for (int i = 0; i < ocupacion; i++) {
                if (matricula.equalsIgnoreCase(aviones[i].getMatricula())) {
                    avion = aviones[i];
                }
            }
        }
        return avion;
    }

    public Avion seleccionarAvion(Scanner teclado, String mensaje, double alcance){
        boolean correcto = true;
        Avion avion=null;
        do{
            correcto = true;
            System.out.print(mensaje);
            String matricula=teclado.nextLine();
            avion=buscarAvion(matricula);
            if((avion==null)||(buscarAvion(avion.getMatricula()))==null){
                correcto=false;
                System.out.println("Matrícula de avión no encontrada.");
            }
            if(avion!=null && avion.getAlcance()<alcance){
                System.out.println("Avión seleccionado con alcance insuficiente (menor que "+alcance+")");
                correcto = false;
            }
        }while(!correcto);
        return avion;
    }

    public boolean escribirAvionesCsv(String nombre){ 
        boolean correcto=true;
        try (PrintWriter out = new PrintWriter(nombre)) {
            for (int i = 0; i < getOcupacion(); i++) {
                out.println(getAvion(i).getMarca() + ";" + getAvion(i).getModelo() + ";"
                        + getAvion(i).getMatricula() + ";" + getAvion(i).getFilas() + ";" +
                        getAvion(i).getColumnas() + ";" + getAvion(i).getAlcance());
            }
        } catch (IOException ex) {
            System.out.println("Error de escritura en fichero Aviones " + ex.getMessage());
            correcto = false;
        }
        return correcto;

    }

    public static ListaAviones leerAvionesCsv(String fichero, int capacidad){ 
        ListaAviones listaAviones = new ListaAviones(capacidad);
        Avion avion;
        FileReader archivo = null;
        try{
            archivo = new FileReader(fichero);
            try (BufferedReader buffer = new BufferedReader(archivo)) {
                String linea = buffer.readLine();
                while(linea!=null){
                    String[] separador = linea.split(";");
                    avion = new Avion(separador[0],separador[1],separador[2],Integer.parseInt(separador[4]),
                            Integer.parseInt(separador[3]),Double.parseDouble(separador[5]));
                    listaAviones.insertarAvion(avion);
                    linea = buffer.readLine();
                }
            } catch (NumberFormatException e) {

            }
        }catch(FileNotFoundException ex){
            System.out.println("Fichero "+ex.getMessage()+" no encontrado.");
        }
        catch (IOException e){
            System.out.println("Error de lectura de fichero "+e.getMessage()+".");
        }
        finally{
            try{
                if(archivo!=null){
                    archivo.close();
                }
            }catch (IOException e){
                System.out.println("Error de cierre de fichero "+e.getMessage()+".");
            }
        }
        return listaAviones;

    }
}


