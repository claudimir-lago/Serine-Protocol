package infra;

import serialSupport.SerineSerial;
import serineDispatcher.VirtualDevice;

/**
 * A simple demo. There is only two virtual devices: the master and one
 * hypothetical device
 *
 * @author Claudimir Lucio do Lago
 */
public class MyDevice {

    SerineSerial serialPort;
    Master master;
    char masterID;
    char slaveID;
    String messageHeader; /** Just to make easy the message composition. It is to be used as the header of each message.*/
    String lastMessage = "";
    boolean inTouch = false;

    /**
     * Just to see what is been sent to the serial. It is helpful to try to
     * identify if a Serine virtual device is attached to the port.
     *
     * @return the last message receive since the last request
     */
    public String getLastMessage() {
        String message = lastMessage;
        lastMessage = "";
        return message;
    }

    /**
     * @return true if the serial communication is OK
     */
    public boolean isSerialConnected() {
        return serialPort.isConnected();
    }

    /**
     * Opens a serial port
     *
     * @param portName a valid port name
     * @param baudeRate a valid baude rate
     * @return true if the serial port is opened
     */
    public boolean open(String portName, int baudeRate) {
        if (serialPort.isConnected()) {
            serialPort.close();
        }
        return serialPort.connect(portName, baudeRate);
    }

    /**
     * Since this demo is for a simple master/slave formation, only these two
     * virtual devices should be named.
     * @param id_for_the_master a character valid to identify the master
     * @param id_for_the_slave a character valid to identify the slave
     */
    public MyDevice(char id_for_the_master, char id_for_the_slave) {
        master = new Master(id_for_the_master, id_for_the_slave);
        serialPort = new SerineSerial();
        serialPort.setAddressed(master);
        messageHeader = String.valueOf(slaveID) + String.valueOf(masterID);
    }

    /**
     * Not only close the serial port, but it also runs the disengage method.
     */
    public void close() {
        disengage();
        serialPort.close();
    }

    /**
     * Send a Serine message. The syntax is not checked.
     * @param message a string containing a valid Serine message
     */
    public void send(String message) {
        if (serialPort.isConnected()) {
            serialPort.postHere(message);
        } else {
            System.out.println("The serial port is not connected.");
        }
    }

    /**
     * Essentially, this class allows the communication with the slave and 
     * message processing for the master
     */
    public class Master extends VirtualDevice {

        private Master(char master_ID, char slave_ID) {
            super(master_ID);
            masterID = master_ID;
            slaveID = slave_ID;
        }

        @Override
        public void processMessageToMe() {
            lastMessage = messageString;
            switch (messageChar[2]) {
                case 'S':
                    break;
                case 'T':
                    break;
                case 'X':
                    break;
            }
        }

        @Override
        public void processBroadcastMessage() {
            lastMessage = messageString;
            switch (messageChar[2]) {
                case 'X':
                    break;
            }
        }

    ;

    }

    /**
     * This method should be populated with messages exchange and other
     * stuffs to assure that master and slave had agreed to communicate with
     * each other. This is not about the serial communication. This is the 
     * contrary of the disengage method.
     */
    public void engage() {
        inTouch = true;
    }

    /**
     * This method should be populated with messages exchange and other stuffs
     * to assure that master and slave lose connection without problems. This
     * is the contrary of the engage method.
     */
    public void disengage() {
        inTouch = false;
    }

    /**
     * Informs whether master and slave are in touch
     * @return true if master and slave are committed
     */
    public boolean isEngaged() {
        return inTouch;
    }

}
