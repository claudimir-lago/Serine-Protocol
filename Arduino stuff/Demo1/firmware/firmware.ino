// Arduino Firmware for ...
#define VERSION "0.1"

#include "Arduino.h"
#include "Serine.h"

const char SPACE = ' ';
const int MAX_MESSAGE = 32; // Actually, the maximum size of the message is  MAX_MESSAGE - 1

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
                break;
        }
    } else if (message[0] == 'B') {
    }
}

void setup() {
    Serial.begin(115200);
}

void loop() {
    delay(1000);
    char message[MAX_MESSAGE];
    while (Serial.available() && usb.getSpace() > 1) usb.putChar(Serial.read());
    Serial.println(usb.getSpace());
    usb.getMessage(message);
    if (message[0] != NULL) handleMessage(message);
}