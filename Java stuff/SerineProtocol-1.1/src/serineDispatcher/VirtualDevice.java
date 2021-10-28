package serineDispatcher;

/**
 * This abstract class provides the foundations for virtual devices that
 * are exchanging messages according to the Serine Protocol. The ID may be
 * inputted either during the creation or by a posterior call. In accordance
 * with this ID, messages are selected. Of course, a broadcast message is also
 * a valid message to this device.
 *
 * @author Claudimir
 */
public abstract class VirtualDevice implements Postable {

    protected char id = ' ';
    protected String messageString;
    protected char[] messageChar;
    protected char destination;
    protected char sender;
    protected Postable addressed;

    /**
     * Construct the virtual devices with a defined ID
     *
     * @param laiaID A valid ID in the network
     */
    public VirtualDevice(char laiaID) {
        id = laiaID;
    }

    /**
     * Construct the virtual devices without a defined ID
     */
    public VirtualDevice() {
    }

    /**
     * @return the ID in the Serine network
     */
    public char getId() {
        return id;
    }

    /**
     * Changes the ID of the virtual device in the network.
     *
     * @param newID The new valid ID
     */
    public void setId(char newID) {
        this.id = newID;
    }


    /**
     * This is a message processor to be implemented by the class. Please, 
     * note that both the messages sent to the ID of this virtual device as
     * well as those ones sent as broadcast messages are valid messages. 
     * Broadcast messages should be processed by the method 
     * processBroadcastMessage. 
     * The message to be processed is available as a String and a array of Char.
     */
    public abstract void processMessageToMe();
    
    
    /**
     * This is a message processor to be implemented by the class. Please, 
     * note that both the messages sent to the ID of this virtual device as
     * well as those ones sent as broadcast messages are valid messages. 
     * Specific messages should be processed by the method 
     * processMessageToMe. 
     * The message to be processed is available as a String and a array of Char.
     */
    public abstract void processBroadcastMessage();

    /**
     * 
     * This method allows posting a valid Serine Protocol message to this 
     * virtual device.
     *
     * @param message A valid message string. For example: "drCxxx;"
     * @return true if the message has been processed by this virtual device.
     */
    @Override
    public boolean postHere(String message) {
        messageString = message;
        messageChar = message.toCharArray();
        destination = messageChar[0];
        sender = messageChar[1];
        boolean itIsMine = false;
        if (destination == id) {
            itIsMine = true;
            processMessageToMe();
        } else if (destination == 'B'){
            itIsMine = true;
            processBroadcastMessage();
        }
        return itIsMine;
    }

}
