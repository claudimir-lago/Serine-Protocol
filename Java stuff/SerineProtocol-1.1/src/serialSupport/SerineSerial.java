package serialSupport;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serineDispatcher.Postable;

/**
 * This class allows implementing a serial channel of communication 
 * for the Serine Protocol. The messages should be posted at postHere method.
 * The method setAddressed should be used to indicate the Postable object 
 * to which the messages coming from the serial port will be sent.
 *
 * @author Claudimir
 */
public class SerineSerial implements SerialPortEventListener, Postable {

    public CommandBuffer inBuffer;
    public final int MAX_IN_BUFFER = 100;
    public SerialPort serialPort;
    protected Postable dispatcher = null;
    private boolean isConnected = false;
    private String portName = "";

    /**
     * Allows identifying, opening, and connecting the stream of the serial port.
     *
     * @param portName for example, "COM5", in Windows.
     * @param rate a valid baud rate
     * @param databits number of data bits
     * @param stopbits number of stop bits
     * @param parity parity bit
     * @return true if the port was correctly opened.
     */
    public boolean connect(String portName, int rate, int databits, int stopbits, int parity) {
        if (!isConnected) {
            serialPort = new SerialPort(portName);
            try {
                serialPort.openPort();
                serialPort.setParams(rate, databits, stopbits, parity);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                serialPort.addEventListener(this);
                isConnected = true;
                this.portName = portName;
            } catch (SerialPortException ex) {
                System.out.println("Error: unable to open serial port.");
            }
        }
        return isConnected;
    }

    /**
     * A simplified version of connect, in which N = 8, stopbits = 1, no parity
     *
     * @param portName for example, "COM5", in Windows.
     * @param rate a valid baud rate
     * @return true if the port was correctly opened.
     */
    public boolean connect(String portName, int rate) {
        return connect(portName, rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Returns the port name as, for example, "COM5", in Windows.
     * 
     * @return a string with the port name.
     */
    public String getPortName() {
        return portName;
    }

    /**
     * @param inBufferSize The maximum number of messages in the buffer
     */
    public SerineSerial(int inBufferSize) {
        isConnected = false;
        inBuffer = new CommandBuffer(inBufferSize);
    }

    /**
     * In this case, the buffer size will be given by MAX_IN_BUFFER
     */
    public SerineSerial() {
        isConnected = false;
        inBuffer = new CommandBuffer(MAX_IN_BUFFER);
    }

    /**
     * Allows to identify to whom the messages will be send. This guy can be
     * either a virtual device or another Postable class that is able to handle
     * and dispatch messages to other Postables. the virtual device to which the
     * messages will be sent.
     *
     * @param dispatcher the Postable to be addressed
     */
    public void setAddressed(Postable dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * Allows send a string (or a valid message) through the serial port.
     *
     * @param message the string to be send through the serial port.
     * @return true if all bytes were sent.
     */
    @Override
    public boolean postHere(String message) {
        boolean foi = false;
        try {
            foi = serialPort.writeString(message);
        } catch (SerialPortException ex) {
            System.out.println("Error: unable to send the message string to the serial port. " + ex);
        }
        return foi;
    }

    /**
     * It handles the bytes coming in from the serial port and send them to the
     * Serine buffer. This method should not be called by the user.
     *
     * @param event RXCHAR is the only event that matters.
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            try {
                int numBytes = serialPort.getInputBufferBytesCount();
                for (int i = 0; i < numBytes; i++) {
                    inBuffer.putChar((char) serialPort.readBytes(1)[0]);
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(SerineSerial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int nc = 0;
        while (inBuffer.numberOfMessages() > 0 && nc <= 10) {
            nc++;
            String mensagem = inBuffer.nextMessage();
            if (dispatcher != null) {
                dispatcher.postHere(mensagem);
            }
        }
    }

    /**
     * Friendly closes the serial port.
     */
    public void close() {
        if (isConnected) {
            try {
                serialPort.closePort();
                isConnected = false;
            } catch (SerialPortException ex) {
                System.out.println("Error: unable to close the serial port");
            }
        }
    }

}
