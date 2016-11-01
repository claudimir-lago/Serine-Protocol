#ifndef Serine_h
#define Serine_h

#include "Arduino.h"

#define BUFFERSIZE B10000000

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

