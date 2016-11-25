package infra;
/**
 * @TODO Transformar Parameters em abstrat e instanciar diretamente na
 * classe que faz a interface (MultiPump)
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import swingFrontEnd.FrontEnd;

/**
 * A class containing a set of parameters to be saved and retrieved from one
 * to other run.
 * 
 * @author Claudimir Lucio do Lago
 */
public class Parameters implements Serializable {

    /**
     * The list of parameters to be saved. It is a good idea to set a initial
     * value to each one of them. These values will be used whether those ones
     * that were saved last time are lost.
     */
    public String portaSerial = "COM3";
    public int baudRate = 115200;

    /**
     * Allows to save the parameters in a file
     * 
     * @param fileName the complete path with the file name
     * @return true if everything is OK
     */
    public boolean save(String fileName) {
        boolean ok = false;
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
                out.writeObject(this);
                out.close();
                ok = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    /**
     * Allows to retrieve the parameters saved in a file
     * 
     * @param fileName the complete path with the file name
     * @return the previously stored parameters
     */
    static public Parameters retrieve(String fileName) {
        Parameters p = new Parameters();
        try {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
                try {
                    p = (Parameters) in.readObject();
                    in.close();
                    calc();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    /**
     * This method should be called after retrieving of parameters from a file
     * or after some change. The idea is to keep consistence among the values
     * and units.
     */
    public static void calc(){
    }
}
