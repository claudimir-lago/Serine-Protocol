#ifndef Serine_h
#define Serine_h

#include "Arduino.h"

#define BUFFERSIZE B10000000
const int MAX_MESSAGE = 33; // Actually, the maximum size of the message is MAX_MESSAGE - 1

struct SerineVirtualDevice {
    char id;
    char serial[MAX_MESSAGE - 5];
};

/**
 * If the identification string contained in the message matches the one
 * from the virtual device, its identification in the network is changed
 * accordingly to the message instruction.
 * 
 * @param vd the virtual device
 * @param message message with the instructions
 * @return true if the id of the virtual device is changed
 */
boolean serineChangeID(SerineVirtualDevice *vd, char *message);

/**
 * It produces a standard answer containing the full identification of the
 * virtual device.
 * 
 * @param toWhom The addressed virtual device
 * @param vd the virtual device sending the information
 * @param answer the message to be sent 
 */
void serineAnswerID(char toWhom, SerineVirtualDevice vd, char *answer);

/**
 * It produces a standard answer for those cases in which the command in the
 * message is not recognized by the virtual device.
 * 
 * @param message the received message 
 * @param id the virtual device that is going to answer
 * @param answer the message to be sent
 */
void serineIdontKnow(char *message, char id, char *answer);

class SerineBuffer {
private:
    char buffer[BUFFERSIZE]; // The size of the buffer must be a power of 2.
    byte bmask; // The bit mask is the size of the buffer minus 1
    byte iRead; // index to the next character to be read
    byte iWrite; // index of the free position to put a new character
    int numberOfMessages;
public:
    SerineBuffer(void);
    void clear(void);
    void putChar(char newChar);
    void putString(char newChars[]);
    int getSpace();
    void getMessage(char message[]);
    void putUL(unsigned long x, int start, int end, char message[]);
    unsigned long getUL(int start, int end, char message[]);
};

#endif

