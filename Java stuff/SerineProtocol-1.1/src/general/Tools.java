package general;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A lot of useful methods
 * 
 * @author Claudimir Lucio do Lago
 */
public class Tools
{

    /**
     * Pause the thread
     *
     * @param time in milliseconds
     */
    public static void sleep(long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Given a date, a timestamp string is generated. It is useful to name
     * data files.
     *
     * @param date date to be used
     * @param separator a string to be inserted between the values
     * @return A string such as "20100922203245" containing from year to seconds
     */
    static public String timeStamp(Calendar date, String separator)
    {
        String[] rot = new String[6];
        rot[0] = String.valueOf(date.get(Calendar.YEAR));
        rot[1] = String.valueOf(date.get(Calendar.MONTH) + 1);
        rot[2] = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        rot[3] = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
        rot[4] = String.valueOf(date.get(Calendar.MINUTE));
        rot[5] = String.valueOf(date.get(Calendar.SECOND));
        for (int i = 1; i < rot.length; i++)
            if (rot[i].length() < 2)
                rot[i] = "0" + rot[i];
        return rot[0] + separator + rot[1] + separator + rot[2]
                + separator + rot[3] + separator + rot[4] + separator + rot[5];
    }

    /**
     * Gives a timestamp string using the present date. It is useful to name
     * data files.
     *
     * @param separator a string to be inserted between the values
     * @return A string such as "20100922203245" containing from year to seconds
     */
    static public String timeStamp(String separator)
    {
        Calendar agora = Calendar.getInstance();
        return timeStamp(agora, separator);
    }
    
    /**
     * Gives a timestamp string using the present date. It is useful to name
     * data files.
     * 
     * @return A string such as "20100922203245" containing from year to seconds
     */
    static public String timeStamp()
    {
        Calendar agora = Calendar.getInstance();
        return timeStamp(agora, "");
    }

    /**
     * Limits a parameter between to limiting values
     * 
     * @param value original value
     * @param min minimum value
     * @param max maximum value
     * @return the coerced value
     */
    static public int constrain(int value, int min, int max)
    {
        if (value < min)
            value = min;
        else if (value > max)
            value = max;
        return value;
    }
    
    /**
     * Limits a parameter between to limiting values
     * 
     * @param value original value
     * @param min minimum value
     * @param max maximum value
     * @return the coerced value
     */
    static public double constrain(double value, double min, double max)
    {
        if (value < min)
            value = min;
        else if (value > max)
            value = max;
        return value;
    }

    /**
     * Limits a parameter between to limiting values
     * 
     * @param value original value
     * @param min minimum value
     * @param max maximum value
     * @return the coerced value
     */
    static public float constrain(float value, float min, float max)
    {
        if (value < min)
            value = min;
        else if (value > max)
            value = max;
        return value;
    }
    
    /**
     * Check the presence of a char in a string
     * @param string the string to be checked
     * @param character the char under investigation
     * @return true if character is any position along the string
     */
    static public boolean contains(String string, char character)
    {
        boolean ret = false;
        for (int i = 0; i < string.length(); i++)
            if (string.charAt(i) == character)
                ret = true;
        return ret;
    }
    
    /**
     * Check the presence of a char in an array
     * @param array array of char under investigation
     * @param begin initial position
     * @param end final position
     * @param c the char under investigation
     * @return true if c is in the specific range of the array
     */
    static public boolean contains(char[] array, int begin, int end, char c){
        boolean ret = false;
        for (int i = begin; i < end; i++)
            if (array[i] == c)
                ret = true;
        return ret;
    }

    /**
     * Get all the tags, separated by spaces, in a string. Spaces are removed.
     * The text after a // is considered a comment.
     * 
     * @param line the string to be analyzed
     * @return a array of strings with the tags
     */
    public static String[] getTags(String line) {
        // elimina espaços nas pontas
        line = line.trim();
        // Remove comentários da linha
        int ultimo = line.indexOf("//");
        if (ultimo < 0) {
            ultimo = line.length();
        }
        line = line.substring(0, ultimo);
        // Elimina espaços adicionais
        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        String[] tag = line.split(" ");
        if (tag[0].equals("")) {
            return null;
        } else {
            return tag;
        }
    }

    /**
     * Allows to overwrite a numeric value on a given string. The number will
     * be right aligned if the number of digits is smaller than the size of the
     * field. 
     * 
     * @param number the numeric value
     * @param begin initial position to putLongIn the number
     * @param end final position to putLongIn the number
     * @param orig original string
     * @return a new string containing the number
     */
    public static String putLongIn(long number, int begin, int end, String orig) {
        if (end >= orig.length()) {
            end = orig.length() - 1;
        }
        if (begin < 0) {
            begin = 0;
        } else if (begin > end) {
            begin = end;
        }
        String s = String.valueOf(number); // converte número em String
        int campo = end - begin + 1; // tamanho do campo
        int offset = campo - s.length(); // calcula o offset
        if (offset < 0) {
            offset = 0;
        }
        return orig.substring(0, begin + offset) + s + orig.substring(end + 1, orig.length());
    }

    /**
     * Converts the digit in its equivalent numeric value. The character '0'
     * is the base value. Therefore its value is zero.
     * 
     * @param c the character
     * @return the numeric value
     */
    public static int convertToNum(char c) {
        return (int) (c - '0');
    }

    /**
     * Allows to get a integer value from a field inside a string
     * 
     * @param begin initial position
     * @param end final position
     * @param text the string
     * @return the numeric value
     */
    public static long getLongFrom(int begin, int end, String text) {
        return Long.valueOf(text.substring(begin, end + 1));
    }

    /**
     * Converts a string in a numeric value. The result is confined in a range.
     * If the string does not contain a valid number, a default value is 
     * returned. It is useful when you are reading a string from a form or a
     * field in a front end.
     * 
     * @param string the string to be converted
     * @param min the minimum valid value
     * @param max the maximum valid value
     * @param other a default value to be returned whether the string is not a number
     * @return a numeric value
     */
    public static Double convertToDouble(String string, Double min, Double max, Double other) {
        Double d = other;
        try {
            d = Double.parseDouble(string);
        } catch (NumberFormatException ex) {
        }
        if (d < min) {
            d = min;
        } else if (d > max) {
            d = max;
        }
        return d;
    }

    /**
     * Returns a string containing a right-aligned integer value. The places
     * at left of the number is filled with a declared char
     * @param num the numeric value
     * @param size the size of the numeric field to be created
     * @param fill the char to be used at left
     * @return a string with a right-aligned number
     */
    public static String format(Double num, int size, char fill) {
        if (Math.abs(num) < 1.0) {
            num = 0.0;
        }
        // prepara o campo para o preechimento
        char[] campo = new char[size];
        for (int i = 0; i < campo.length; i++) {
            campo[i] = fill;
        }
        // converte o valor numérico em string
        String numS = String.valueOf(Math.round(num));
        // copia a parte inteira
        int i = campo.length - 1;
        int iS = numS.length() - 1;
        while ((i >= 0) && (iS >= 0)) {
            campo[i] = numS.charAt(iS);
            i--;
            iS--;
        }
        return String.copyValueOf(campo);
    }

    /**
     * Returns a string containing a right-aligned  value. The places
     * at left of the number is filled with a declared char
     * @param num the numeric value
     * @param size the size of the numeric field to be created
     * @param dec number of decimal places
     * @param fill the char to be used at left
     * @return a string with a right-aligned number
     */
    public static String format(Double num, int size, int dec, char fill) {
        if (Math.abs(num) < Math.pow(10, -dec)) {
            num = 0.0;
        }
        // prepara o campo para o preechimento
        char[] campo = new char[size];
        for (int i = 0; i < campo.length; i++) {
            campo[i] = fill;
        }
        // localiza o ponto decimal no campo
        if (dec < 1) {
            dec = 1;
        }
        int ponto = campo.length - dec - 1;
        campo[ponto] = '.';
        // converte o valor numérico em string e acha o ponto
        String numS = String.valueOf(num);
        int pontoS = numS.indexOf('.');
        if (pontoS == -1) {
            pontoS = numS.length();
        }
        // copia a parte decimal
        int i = ponto + 1;
        int iS = pontoS + 1;
        while ((i < campo.length) && (iS < numS.length())) {
            campo[i] = numS.charAt(iS);
            i++;
            iS++;
        }
        // preenche com zeros as casas decimais restantes
        while (i < campo.length) {
            campo[i] = '0';
            i++;
        }
        // copia a parte inteira
        i = ponto - 1;
        iS = pontoS - 1;
        while ((i >= 0) && (iS >= 0)) {
            campo[i] = numS.charAt(iS);
            i--;
            iS--;
        }
        return String.copyValueOf(campo);
    }

    /**
     * Returns a string containing a right-aligned  value. The places
     * at left of the number is filled with spaces
     * 
     * @param num the numeric value
     * @param size the size of the numeric field to be created
     * @return a string with a right-aligned number
     */
    public static String format(Double num, int size) {
        return format(num, size, ' ');
    }

    /**
     * Returns a string containing a right-aligned  value. The places
     * at left of the number is filled with spaces
     * 
     * @param num the numeric value
     * @param size the size of the numeric field to be created
     * @param dec number of decimal places (minimum = 1)
     * @return a string containing the number
     */
    public static String format(Double num, int size, int dec) {
        return format(num, size, dec, ' ');
    }

    /**
     * Check if two strings are equivalent. Equivalence means: the same size,
     * the same characters (case sensitive). The position contained the defined
     * wildcard are ignored.
     * 
     * @param x a string
     * @param y another string
     * @param wild wildcard character
     * @return true if x and y are equivalent
     */
    public static boolean equivalent(String x, String y, char wild) {
        boolean igual = true;
        if (x.length() == y.length()) {
            for (int i = 0; i < x.length(); i++) {
                if ((x.charAt(i) != wild) && (y.charAt(i) != wild)) {
                    if (x.charAt(i) != y.charAt(i)) {
                        igual = false;
                    }
                }
            }
        } else {
            igual = false;
        }
        return igual;
    }
}
