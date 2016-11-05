#include "Arduino.h"
#include "Serine.h"

/**
 * The function allows putting an unsigned long in a right-aligned field
 * of zeros in a message.
 *
 * @param x the number
 * @param start initial position (starts in zero)
 * @param end final position
 * @param message the message string
 */
void SerineBuffer::putUL(unsigned long x, int start, int end, char message[]) {
    int field = end - start + 1; // size of the output field
    char b[11]; // 0 to 4 294 967 295
    ultoa(x, b, 10); // converts x in the string b on decimal base
    int nDec = strlen(b); // number of digits of b
    if (field < nDec) nDec = field; // limits nDec to the size of the output string
    int offset = field - nDec; // relative initial position
    for (int i = 0; i < offset; i++) message[i + start] = '0';
    for (int i = 0; i < nDec; i++) message[i + offset + start] = b[i];
}

/**
 * The function allow getting a unsigned long from the message.
 *
 * @param start initial position (starts in zero)
 * @param end the final position
 * @param message the message string
 * @return the number in that field
 */
unsigned long SerineBuffer::getUL(int start, int end, char message[]) {
    int pos = start;
    while (message[pos] == '0') pos++;
    unsigned long sum = 0;
    unsigned long x2;
    unsigned long x8;
    while (pos <= end) {
        x2 = sum << 1;
        x8 = x2 << 2;
        sum = x8 + x2 + (message[pos] - '0'); // (x8 + x2) is the same as multiplying by 10
        pos++;
    }
    return sum;
}

SerineBuffer::SerineBuffer(void) {
    bmask = sizeof (buffer) - 1;
    clear();
}

/**
 * Clear the whole buffer.
 */
void SerineBuffer::clear() {
    buffer[0] = ';';
    iRead = 1;
    iWrite = 1;
    numberOfMessages = 0;
}

/**
 * Adds a new character in the buffer. Space and lower codes are ignored. A "!"
 * is used to clear the buffer. A ";" ends a message.
 *
 * @param newChar
 */
void SerineBuffer::putChar(char newChar) {
    if (newChar < 34) {
        if (newChar == '!') clear();
    } else {
        if (newChar < 127) {
            buffer[iWrite] = newChar;
            iWrite++;
            iWrite &= bmask;
            if (newChar == ';') numberOfMessages++;
        }
    }
}

/**
 * Allows the addition of multiple characters to the buffer.
 *
 * @param newChars
 */
void SerineBuffer::putString(char newChars[]) {
    for (int i = 0; newChars[i] != NULL; i++) putChar(newChars[i]);
}

/**
 * Get the number of free bytes in the buffer.
 * 
 * @return the number of free bytes 
 */
int SerineBuffer::getSpace() {
    return BUFFERSIZE - ((iWrite - iRead) & 127);
}

/**
 * Gets a message from the buffer or a null string otherwise.
 *
 * @param message
 */
void SerineBuffer::getMessage(char *message) {
    message[0] = NULL;
    if (numberOfMessages > 0) {
        byte i = 0;
        noInterrupts(); // It is important whether the port is I2C
        while (buffer[iRead] != ';') {
            message[i] = buffer[iRead];
            i++;
            iRead++;
            iRead &= bmask;
        };
        iRead++; // jump the ';' and points to the next message
        iRead &= bmask;
        numberOfMessages--;
        interrupts(); // It is important whether the port is I2C
        message[i] = ';';
        message[i + 1] = NULL; // put a NULL char at the end position
    };
}

boolean serineChangeID(SerineVirtualDevice *vd, char *message) {
    char serialMessage[MAX_MESSAGE - 5];
    int i = 0;
    do {
        serialMessage[i] = message[5 + i];
        i++;
    } while (message[5 + i] != ';');
    serialMessage[i] = NULL;
    boolean sameSerial = strcmp(vd->serial, serialMessage) == 0;
    if (sameSerial) vd->id = message[4];
    return sameSerial;
}

void serineAnswerID(char toWhom, SerineVirtualDevice vd, char *answer) {
    answer[0] = toWhom;
    answer[1] = vd.id;
    answer[2] = 'i';
    answer[3] = NULL;
    strncat(answer, vd.serial, 27);
    strcat(answer, ";");
}

void serineIdontKnow(char *message, char id, char *answer) {
    answer[0] = message[1];
    answer[1] = id;
    answer[2] = '?';
    answer[3] = message[2];
    answer[4] = ';';
    answer[5] = NULL;
}