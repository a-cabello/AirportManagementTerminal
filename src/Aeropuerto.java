

/**
 * Clase Aeropuerto que crea un objeto aeropuerto con las siguientes propiedades : nombre, código, latitud,
 * longitud, terminables y además se declara la constante del radio. En cuanto a los métodos, tenemos la función distancia, la cual
 * recibe como argumento un aeropuerto destino y calcula la distancia hasta dicho aeropuerto. Por último tenemos dos toString que
 * que devuelven las propiedades del propio aeropuerto.
 * 
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class Aeropuerto {

    private final String nombre;
    private final String codigo;
    private final double latitud;
    private final double longitud;
    private final int terminales;
    private final int radio;

    /**
     * Constructor of the class
     * @param nombre nombre del aeropuerto. Ejemplo: Adolfo Suarez Madrid - Barajas
     * @param codigo  Código IATA del aeropuerto (3 letras). Ejemplo: MAD
     * @param latitud coordenadas de laitud del aeropuerto expresadas en grados. Ejemplo: 40.4927751
     * @param longitud coordenadas de longitud del aeropuerto expresadas en grados. Ejemplo: -3.5778
     * @param terminales número de terminales que tiene el aeropuerto. Ejemplo: 4
     */
    public Aeropuerto(String nombre, String codigo, double latitud, double longitud, int terminales) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.terminales = terminales;
        radio = 6378;
    }

    public String getNombre(){
        return nombre;
    }
    public String getCodigo(){
        return codigo;
    }
    public double getLatitud(){
        return latitud;
    }
    public double getLongitud(){
        return longitud;
    }
    public int getTerminales(){
        return terminales;
    }
    public double distancia(Aeropuerto destino){
        return  Math.acos(Math.sin(Math.toRadians(this.latitud))*
                Math.sin(Math.toRadians(destino.latitud))+
                Math.cos(Math.toRadians(this.latitud))*
                        Math.cos(Math.toRadians(destino.latitud))*
                        Math.cos(Math.toRadians(this.longitud) -
                                (Math.toRadians(destino.longitud))))*radio;
    }

    @Override
    public String toString(){
        return nombre+"("+codigo+"), en ("+this.latitud+" - "+this.longitud+"), con +"+terminales+" terminales.";
    }
 
    public String toStringSimple(){
        return nombre+"("+codigo+")";
    }
}

