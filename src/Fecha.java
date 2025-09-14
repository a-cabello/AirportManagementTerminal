

/**
* @author Álvaro Cabello.
* @version 1.1
*/

public class Fecha {

    enum Meses { ENERO, FEBRERO, MARZO, ABRIL, MAYO, JUNIO, JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICIEMBRE }

    public static final int PRIMER_ANIO = 1900;

    public static final int ULTIMO_ANIO = 3000;

    public static final int MESES_ANIO = 12;

    public static final int DIAS_MES = 31;

    public static final int DIAS_FEBRERO = 28;
 
    public static final int HORAS_DIA = 24;

    public static final int MINUTOS_HORA = 60;

    public static final int SEGUNDOS_MINUTO = 60;


    private final int dia;

    private final int mes;

    private final int anio;

    private final int hora;

    private final int minuto;

    private final int segundo;

    /**
     * Constructor de la clase Fecha usado para definir
     * únicamente la fecha, pero no la hora del evento.
     *
     * @param dia Dia del evento
     * @param mes Mes del evento
     * @param anio Año del evento
     */
    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        hora = -1;
        minuto = -1;
        segundo = -1;
    }

    /**
     * Constructor de la clase Fecha usado para definir
     * fecha y hora de un evento.
     *
     * @param dia Dia del evento
     * @param mes Mes del evento
     * @param anio Año del evento
     * @param hora Hora del evento
     * @param minuto Minutos del evento
     * @param segundo Segundos del evento
     */
    public Fecha(int dia, int mes, int anio, int hora, int minuto, int segundo) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }


    public int getDia(){
        return dia;
    }


    public int getMes(){
        return mes;
    }


    public int getAnio(){
        return anio;
    }

 
    public int getHora() {
        return hora;
    }


    public int getMinuto() {
        return minuto;
    }


    public int getSegundo() {
        return segundo;
    }

    @Override
    public String toString() {
        String evento;
        if (hora < 0)
            evento = String.format("%02d/%02d/%04d", dia, mes, anio);
        else
            evento = String.format("%02d/%02d/%04d %02d:%02d:%02d", dia, mes, anio, hora, minuto, segundo);
        return evento;
    }


    public boolean coincide(Fecha fecha){
        boolean igual = anio == fecha.getAnio() && mes == fecha.getMes() && dia == fecha.getDia();
        if (hora >= 0 && fecha.hora >= 0){
            igual = igual && hora == fecha.getHora() && minuto == fecha.getMinuto() && segundo == fecha.getSegundo();
        }
        return igual;
    }


    public boolean anterior(Fecha fecha) {
        String thisString = "";
        String fechaString = "";
        if (hora >= 0 && fecha.hora >= 0){
            thisString = String.format("%04d%02d%02d%02d%02d%02d", anio, mes, dia, hora, minuto, segundo);
            fechaString = String.format("%04d%02d%02d%02d%02d%02d", fecha.getAnio(), fecha.getMes(), fecha.getDia(), fecha.getHora(), fecha.getMinuto(), fecha.getSegundo());
        } else {
            thisString = String.format("%04d%02d%02d", anio, mes, dia);
            fechaString = String.format("%04d%02d%02d", fecha.getAnio(), fecha.getMes(), fecha.getDia());
        }
        return thisString.compareTo(fechaString) < 0;
    }


    public static boolean esBisiesto(int anio) {
        final int ANIO_BISIESTO = 4;
        final int SIGLO = 100;
        final int SIGLO_BISIESTO = 400;
        return (anio % ANIO_BISIESTO == 0 && anio % SIGLO != 0) || anio % SIGLO_BISIESTO == 0;
    }

    /**
     * Función que comprueba si una fecha es correcta.
     * Una fecha es correcta cuando los valores de dia, mes y año
     * se encuentra dentro de sus correspondientes rangos, y además
     * se tiene en cuenta los rangos específicos para cada día de
     * cada mes, incluyendo la consideración de años bisiestos para
     * dar el 29 de febrero como válido.
     *
     * @param dia Dia del evento
     * @param mes Mes del evento
     * @param anio Año del evento
     * @return true si la fecha es correcta, false en caso contrario
     */
    public static boolean comprobarFecha(int dia, int mes, int anio) {
        boolean fechaCorrecta = anio >= PRIMER_ANIO && anio <= ULTIMO_ANIO && mes >= 1 && mes <= MESES_ANIO && dia >= 1 && dia <= DIAS_MES;
        if (fechaCorrecta){
            if (mes == Meses.FEBRERO.ordinal() + 1)
                fechaCorrecta = dia <= DIAS_FEBRERO || (dia <= DIAS_FEBRERO + 1 && esBisiesto(anio));
            else if (mes == Meses.ABRIL.ordinal() + 1 || mes == Meses.JUNIO.ordinal() + 1
                    || mes == Meses.SEPTIEMBRE.ordinal() + 1 || mes == Meses.NOVIEMBRE.ordinal() + 1)
                fechaCorrecta = dia <= DIAS_MES - 1;
        }
        return fechaCorrecta;
    }

    /**
     * Función que comprueba si una hora es correcta.
     * Una hora es correcta cuando los valores de hora, minuto y
     * segundo se encuentran dentro de sus correspondientes rangos.
     *
     * @param hora Hora del evento
     * @param minuto Minutos del evento
     * @param segundo Segundos del evento
     * @return true si la hora es correcta, false en caso contrario
     */
    public static boolean comprobarHora(int hora, int minuto, int segundo) {
        return hora >= 0 && hora <= HORAS_DIA - 1
                && minuto >= 0 && minuto <= MINUTOS_HORA - 1
                && segundo >= 0 && segundo <= SEGUNDOS_MINUTO - 1;
    }


    public static Fecha fromString(String texto) {
        final int LENGTH = 3;
        Fecha evento = null;
        int dia = 0;
        int mes = 0;
        int anio = 0;
        int hora = -1;
        int minuto = -1;
        int segundo = -1;
        String[] division = texto.split(" ");
        String[] fecha = division[0].split("/");
        if (fecha.length == LENGTH) {
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]);
            anio = Integer.parseInt(fecha[2]);
        }
        if (division.length > 1) {
            String [] tiempo = division[1].split(":");
            if (tiempo.length == LENGTH) {
                hora = Integer.parseInt(tiempo[0]);
                minuto = Integer.parseInt(tiempo[1]);
                segundo = Integer.parseInt(tiempo[2]);
            }
        }
        if (dia > 0) {
            if (hora >= 0)
                evento = new Fecha(dia, mes, anio, hora, minuto, segundo);
            else
                evento = new Fecha(dia, mes, anio);
        }
        return evento;
    }
}

