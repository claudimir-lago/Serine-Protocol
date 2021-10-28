package serialSupport;

/**
 * This class allows the implementation of a cyclic buffer for Serine messages.
 */
public class CommandBuffer {

    private final String[] buffer;
    private final int bufferSize;
    private int freePosIndex;
    private int nextCommandIndex;
    private String tempBuffer;

    /**
     * @param bufferSize The maximum number of messages to be stored. This is
     * not the same thing as the number of bytes.
     */
    public CommandBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new String[bufferSize];
        freePosIndex = 0;
        nextCommandIndex = 0;
        tempBuffer = "";
    }

    /**
     * Includes a new message in the buffer.
     *
     * @param message The message to be included. The syntax is not verified.
     */
    public void putMessage(String message) {
        buffer[freePosIndex] = message;
        tempBuffer = "";  // limpa o buffer temporario
        freePosIndex = (freePosIndex + 1) % bufferSize;
    }

    /**
     * Includes a new Char in the buffer
     * 
     * @param c the Char to be included
     */
    public void putChar(char c) {
        if (((byte) c > 32) && ((byte) c < 127)) {
            if (tempBuffer.length() > 1000) {
                tempBuffer = "";
            }
            if (c == '!') {
                reset();
            } else {
                tempBuffer += String.valueOf(c);
                if (c == ';') {
                    putMessage(tempBuffer);
                }
            }
        }
    }

    /**
     * Allows to get a string with a new message from the buffer.
     *
     * @return a valid Serine message or a null string whether no message is
     * available in the buffer.
     */
    public String nextMessage() {
        String agora = "";
        if (numberOfMessages() > 0) {
            agora = buffer[nextCommandIndex];
            nextCommandIndex = (nextCommandIndex + 1) % bufferSize;
        }
        return agora;
    }

    /**
     * It returns the number of Serine messages available at the buffer
     *
     * @return the number of messages in the buffer
     */
    public int numberOfMessages() {
        int delta = freePosIndex - nextCommandIndex;
        if (delta < 0) {
            delta += bufferSize;
        }
        return delta;
    }

    /**
     * Clear both the main and temporary buffers
     */
    public void reset() {
        tempBuffer = "";
        nextCommandIndex = freePosIndex;
    }

    /**
     * Allows reading the temporary buffer. It should be used only for
     * diagnostic purposes.
     *
     * @return the string with the temporary buffer
     */
    public String getTemp() {
        return tempBuffer;
    }
}
