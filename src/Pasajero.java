
import java.util.Scanner;

/**
 * Clase pasajero, la cual crea un objeto pasajero con nombre, apellidos, numeroDNI, letraDNI, email y maxBilletes, todas declaradas en el constructor.
 * Tiene además una serie de getters y setters que nos permitirán emplear carácterísticas del pasajero en otras clases. Además hay una serie de métodos
 * tales como:
 * numBilletesComprado(devuelve el número entero de la ocupación de la lista de billetes),
 * maxBilletesAlcanzado(Función booleana que devuelve true si está llena la lista de billetes),
 * aniadirBillete(inserta el billete pasado por parámetro en la lista de billetes),
 * buscarBillete(busca un billete a partir de su localizador, y si lo encuentra lo devuelve),
 * cancelarBillete(Elimina el billete de la lista de billetes del pasajero a partir de su localizador),
 * listarBilletes(muestra por pantalla los billetes existentes),
 * seleccionarBillete(Encapsula la funcionalidad seleccionarBillete de ListaBilletes),
 * altaPasajero(Crea un nuevo pasajero no repetido, pidiendo por teclado los datos necesarios al usuario en el orden
 * y con los textos indicados en los ejemplos de ejecución del enunciado. La función solicita repetidamente los parametros hasta que sean correctos),
 * correctoDNI(función booleana que devuelve true si el DNI es correcto; Correcto: 00123456 S, incorrectos: 123456789 A, 12345678 0, 12345678 A),
 * correctoEmail(función booleana que devuelve true si el email es correcto; Correcto: cristian.ramirez@upm.es, incorrecto: cristian.ramirez@gmail.com, cristian-23@upm.es, cristian.@upm.es),
 * @author Álvaro Cabello.
 * @version 1.1
 */

public class Pasajero {
    private final String nombre;
    private final String apellidos;
    private final long numeroDNI;
    private final char letraDNI;
    private final String email;
    private final int maxBilletes;
    public ListaBilletes listaBilletes;


    /**
     * Constructor of the class
     *
     * @param nombre Nombre del pasajero. Ejemplo: Cristian.
     * @param apellidos Apellidos del pasajero. Ejemplo: Ramírez Atencia.
     * @param numeroDNI Número del DNI del pasajero. Deberá estar compuesto por 8 dígitos (como máximo) y una letra. Para que un DNI sea
     * correcto, se debe calcular el resto de la división de este número (de 8 dígitos) entre 23. Ejemplo: 51231621.
     * @param letraDNI Letra del DNI del pasajero, debe corresponderse con la posición indicada en la siguiente tabla según el resto
     * obtenido. Por ejemplo, el DNI 12345678Z es correcto, pues el resto de 12345678 entre 23 es
     * 14, que se corresponde en la tabla con la letra Z. Ejemplo: H.
     * @param email Email del pasajero. Debe acabar en @upm.es o en @alumnos.upm.es, y estar
     * compuesto únicamente por caracteres alfabéticos y el carácter punto ‘.’ entre estos (no
     * pudiendo por tanto estar al principio del correo ni justo delante de la @).
     */

    public Pasajero(String nombre, String apellidos, long numeroDNI, char letraDNI, String email, int maxBilletes) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDNI = numeroDNI;
        this.letraDNI = letraDNI;
        this.email = email;
        this.maxBilletes = maxBilletes;
        listaBilletes= new ListaBilletes(maxBilletes);
    }

    public String getNombre(){
        return nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public long getNumeroDNI(){
        return numeroDNI;
    }
    public char getLetraDNI(){
        return letraDNI;
    }
    public String getDNI(){
        long numero=getNumeroDNI();
        int longitud=8;
        return String.format(String.format("%0"+longitud+"d",numero)+(getLetraDNI()));
    }

    public String getEmail(){
        return email;
    }
    @Override
    public String toString(){
        return getNombre()+" "+getApellidos()+", "+getDNI()+", "+getEmail();
    }
    public int numBilletesComprado(){
        return this.listaBilletes.getOcupacion();
    }
    public boolean maxBilletesAlcanzado(){
        return this.listaBilletes.estaLlena();
    }
    public Billete getBillete(int i){
        return listaBilletes.getBillete(i);
    }
    public boolean aniadirBillete(Billete billete){
        return listaBilletes.insertarBillete(billete);
    }
    public Billete buscarBillete(String localizador){
        return listaBilletes.buscarBillete(localizador);
    }
    public boolean cancelarBillete(String localizador){
        return listaBilletes.eliminarBillete(localizador);

    }
    public void listarBilletes(){
        listaBilletes.listarBilletes();
    }
    public Billete seleccionarBillete(Scanner teclado, String mensaje){
        return listaBilletes.seleccionarBillete(teclado, mensaje);
    }

    public static Pasajero altaPasajero(Scanner teclado, ListaPasajeros pasajeros, int maxBilletes) {
        Pasajero pasajero;
        System.out.print("Ingrese nombre:");
        String nombre = teclado.nextLine();
        System.out.print("Ingrese apellidos:");
        String apellidos = teclado.nextLine();
        long numero;
        char letra;
        String dni;
        do {
            numero = Utilidades.leerNumero(teclado,"Ingrese número de DNI:",0L,99999999L);
            letra = Utilidades.leerLetra(teclado,"Ingrese letra de DNI:",'A','Z');
            dni= Long.toString(numero)+letra;
            if(!Pasajero.correctoDNI(numero,letra)) {
                System.out.println("DNI incorrecto.");
            }
            if(pasajeros.buscarPasajeroDNI(dni)!=null&&correctoDNI(numero,letra)){
                System.out.println("DNI ya existe.");
            }
        } while (!correctoDNI(numero, letra)||pasajeros.buscarPasajeroDNI(dni)!=null);
        String email;
        do {
            System.out.print("Ingrese email:");
            email = teclado.nextLine();
            if(!correctoEmail(email)){
                System.out.println("Email incorrecto.");
            }
            if(pasajeros.buscarPasajeroEmail(email)!=null&&correctoEmail(email)){
                System.out.println("Email ya existe.");
            }
        } while (!correctoEmail(email)||pasajeros.buscarPasajeroEmail(email)!=null);
        pasajero = new Pasajero(nombre,apellidos,numero,letra,email,maxBilletes);
        pasajeros.insertarPasajero(pasajero);
        System.out.println("Pasajero con DNI "+pasajero.getDNI()+" dado de alta con éxito.");
        System.out.println();
        return pasajero;
    }
    public static boolean correctoDNI(long numero, char letra) {
        int posicionletra = (int) (numero % 23);
        boolean correctoDni = false;
        char letrasDNI[] = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        if (numero < 100000000L && letra == letrasDNI[posicionletra]) {
            correctoDni = true;
        }
        return correctoDni;
    }
    public static boolean correctoEmail(String email){
        boolean correcto = false;
        int contadorArroba = 0;

        if(email.endsWith("@upm.es")||email.endsWith("@alumnos.upm.es")){
            correcto = true;
            for(int arroba=0;arroba<email.length();arroba++){
                if(email.charAt(arroba)=='@'){
                    contadorArroba++;
                }
            }
            if(contadorArroba>1){
                correcto=false;
            }
            String[] separador = email.split("@");
            if(separador[0].charAt(0)=='.'||separador[0].charAt(separador[0].length()-1)=='.'){
                correcto=false;
            }

            for(int i = 0;i<separador[0].length();i++){
                if(separador[0].charAt(separador[0].length()-1)!='.') {
                    if (separador[0].charAt(i) == '.' && separador[0].charAt(i + 1) == '.') {
                        correcto = false;
                    }
                }
                if(!Character.isLetter(separador[0].charAt(i))&&separador[0].charAt(i)!='.'){
                    correcto=false;
                }
            }
            if(separador[0].charAt(0)=='.'||separador[0].charAt(separador[0].length()-1)=='.'){
                correcto=false;
            }


        }
        return correcto;
    }

}


