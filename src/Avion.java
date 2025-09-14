


/**
 * Clase Avión que crea un objeto avión con las siguientes propiedades: marca, modelo, matrícula, columnas, filas y alcance.
 * Hay una serie de getters que permiten saber ciertas propiedades del avión en otras clases. Además, hay dos funciones de
 * tipo String que sirven para devolver una frase que contiene ciertas propiedades del avión.
 * 
 * @author Álvaro Cabello.
 * @version     1.1
 */
public class Avion {

    private final String marca;
    private String modelo;
    private final String matricula;
    private final int columnas;
    private final int filas;
    private final double alcance;


    /**
     * Constructor of the class
     * @param marca Es la marca del avión. Ejemplo: Boeing.
     * @param modelo Es el modelo del avión. Ejemplo: 737.
     * @param matricula Es la matrícula del avión. Ejemplo: (EC-LKE).
     * @param columnas Número de columnas que tiene el avión.
     * @param filas Número de filas que tiene el avión.
     * @param alcance todos los kilómetros posibles que podrá recorrer el avión. Los aviones no pueden realizar
     * vuelos entre Aeropuertos cuya distancia supere al del avión, para calcular esta distancia se implementa la siguiente fórmula:
     * Distancia(A,B) = arccos(sin𝜙a * sin𝜙b + cos𝜙a * cos𝜙b * cos(𝜆a - 𝜆b)) * R. R es el radio (6378km). A y B son los aeropuertos.
     * 𝜙 (latitud en radianes) y  𝜆 (longitud en radianes).
     */
    public Avion(String marca, String modelo, String matricula, int columnas, int filas, double alcance) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.columnas = columnas;
        this.filas = filas;
        this.alcance = alcance;

    }

    public String getMarca(){
        return this.marca;
    }
    public String getModelo(){
        return this.modelo;
    }
    public String getMatricula(){
        return this.matricula;
    }
    public int getColumnas(){
        return this.columnas;
    }
    public int getFilas(){
        return this.filas;
    }
    public double getAlcance(){
        return this.alcance;
    }
    @Override
    public String toString(){
        int asientos = filas*columnas;
        return this.marca+" "+this.modelo+"("+this.matricula+"): "+(asientos)+" asientos, hasta "+this.alcance+" km";
    }

    public String toStringSimple(){
        return this.marca+" "+this.modelo+"("+this.matricula+")";
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
