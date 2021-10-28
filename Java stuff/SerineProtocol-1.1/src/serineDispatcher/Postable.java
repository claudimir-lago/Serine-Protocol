package serineDispatcher;

/**
 * Interface to be implemented by virtual devices using Serine Protocol.
 * 
 * @author Claudimir Lucio do Lago
 */
public interface Postable
{

    /**
     * The sender posts the message, which will be processed
     * accordingly the implementation of the method in that class.
     * 
     * @param message A valid Serine Protocol message
     * 
     * @return true if the message was correctly processed (whatever it means).
     */
    public boolean postHere(String message);
}
