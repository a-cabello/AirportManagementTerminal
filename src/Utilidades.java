

import java.util.Scanner;

/**
 * clase que nos permite dinamizar el código, generando métodos concretos dirigidos al usuario para introducir un número o fecha.
 * 
 * @author Álvaro Cabello
 * @version     1.1
 */
public class Utilidades {
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo){
        int numero;
        do{
            System.out.print(mensaje);
            numero = teclado.nextInt();
            teclado.nextLine();
        }while(numero<minimo || numero>maximo);
        return numero;
    }
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo){
        long numero;
        do{
            System.out.print(mensaje);
            numero = teclado.nextLong();
            teclado.nextLine();
        }while(numero<minimo || numero>maximo);
        return numero;
    }
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo){
        double numero;
        do{
            System.out.print(mensaje);
            numero = teclado.nextDouble();
            teclado.nextLine();
        }while(numero<minimo || numero>maximo);
        return numero;
    }
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo){
        char caracter;
        do{
            System.out.print(mensaje);
            caracter = teclado.nextLine().charAt(0);
        }while(caracter<minimo || caracter>maximo );
        return caracter;
    }
    public static Fecha leerFecha(Scanner teclado, String mensaje){
        boolean fechaTrue;
        Fecha fecha;
        int dia, mes , anio;
        do {
            System.out.println(mensaje);
            dia = leerNumero(teclado,"Ingrese día: ",1,Fecha.DIAS_MES);
            mes = leerNumero(teclado, "Ingrese mes: ",1,Fecha.MESES_ANIO);
            anio = leerNumero(teclado,"Introduzca año: ",Fecha.PRIMER_ANIO,Fecha.ULTIMO_ANIO);
            fechaTrue = Fecha.comprobarFecha(dia,mes,anio);
            if(!fechaTrue){
                System.out.println("Fecha introducida incorrecta.");
            }
        }while(!fechaTrue);
        fecha = new Fecha(dia,mes, anio);
        return fecha;
    }
    public static Fecha leerFechaHora(Scanner teclado, String mensaje){
        boolean fechaTrue1 = false;
        Fecha fechaHora;
        int dia, mes, anio, hora, minuto, segundo;
        do{
            System.out.println(mensaje);
            dia = leerNumero(teclado,"Ingrese día: ",1,Fecha.DIAS_MES);
            mes = leerNumero(teclado,"Ingrese mes: ",1,Fecha.MESES_ANIO);
            anio = leerNumero(teclado,"Ingrese año: ",Fecha.PRIMER_ANIO,Fecha.ULTIMO_ANIO);
            hora = leerNumero(teclado,"Ingrese hora: ",0,Fecha.HORAS_DIA);
            minuto = leerNumero(teclado,"Ingrese minuto: ",0,Fecha.MINUTOS_HORA);
            segundo = leerNumero(teclado,"Ingrese segundo: ",0,Fecha.SEGUNDOS_MINUTO);
            if(Fecha.comprobarHora(hora, minuto, segundo) && Fecha.comprobarFecha(dia, mes, anio)){
                fechaTrue1 = true;
            }
            if(!fechaTrue1){
                System.out.println("Fecha u hora introducida incorrecta.");
            }
        }while(!fechaTrue1);
        fechaHora = new Fecha(dia, mes, anio,hora,minuto,segundo);
        return fechaHora;
    }
}

