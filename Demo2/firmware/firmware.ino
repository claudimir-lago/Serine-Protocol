// Arduino Firmware for ...
#define VERSION "0.2"

#include "Arduino.h"
#include "Serine.h"

const char SPACE = ' ';
const int MAX_MESSAGE = 32; // Actually, the maximum size of the message is MAX_MESSAGE - 1

/* Virtual devices in this microcontroller */
char ID_fool = 'f';
char serial_fool[] = "123deOliveira4";

SerineBuffer usb;
char sender = '?'; // To be read from the message

void handleMessage(char message[]) {
    char answer[MAX_MESSAGE];
    if (message[0] == ID_fool) {
        switch (message[2]) {
            case 'I':
                if (message[3] == 'x') {
                    if (strcmp(serial_fool, message) < 0) ID_fool = message[4];
                } else {
                    answer[0] = message[1];
                    answer[1] = ID_fool;
                    answer[2] = 'i';
                    answer[3] = NULL;
                    strcat(answer, serial_fool);
                    strcat(answer, ";");
                    Serial.print(answer);
                }
                break;
            default:
                answer[0] = message[1];
                answer[1] = ID_fool;
                answer[2] = '?';
                answer[3] = message[2];
                answer[4] = ';';
                answer[5] = NULL;
                Serial.print(answer);
        }
    } else if (message[0] == 'B') {
        switch (message[2]) {
            case 'I':
                answer[0] = message[1];
                answer[1] = ID_fool;
                answer[2] = 'i';
                answer[3] = NULL;
                strcat(answer, serial_fool);
                strcat(answer, ";");
                Serial.print(answer);
                break;
            default:
                answer[0] = message[1];
                answer[1] = ID_fool;
                answer[2] = '?';
                answer[3] = message[2];
                answer[4] = ';';
                answer[5] = NULL;
                Serial.print(answer);
        }
    }
}

void setup() {
    Serial.begin(115200);
}

void loop() {
    delay(1000);
    while (Serial.available() && usb.getSpace() > 1) usb.putChar(Serial.read());
    char message[MAX_MESSAGE];
    usb.getMessage(message);
    if (message[0] != NULL) handleMessage(message);
}