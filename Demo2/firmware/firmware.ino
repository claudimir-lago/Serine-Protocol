// Arduino Firmware for ...
#define VERSION "0.3"

#include "Arduino.h"
#include "Serine.h"

/* Virtual devices in this microcontroller */
SerineVirtualDevice fool = {'f', "t123deOliveira4haha"};

SerineBuffer usb;
char sender = '?';

void inline handleFool(char *message) {
    char answer[MAX_MESSAGE];
    switch (message[2]) {
        case 'I':
            if (message[3] == 'x') {
                serineChangeID(&fool, message);
            } else {
                serineAnswerID(message[1], fool, answer);
                Serial.print(answer);
            }
            serineAnswerID(message[1], fool, answer);
            Serial.print(answer);
            break;
        default:
            serineIdontKnow(message, fool.id, answer);
            Serial.print(answer);
    }
}

void inline handleBroadcast(char *message) {
    char answer[MAX_MESSAGE];
    switch (message[2]) {
        case 'I':
            serineAnswerID(message[1], fool, answer);
            Serial.print(answer);
            break;
        default:
            serineIdontKnow(message, fool.id, answer);
            Serial.print(answer);
    }
}

void inline handleMessage(char *message) {
    if (message[0] == fool.id) handleFool(message);
    else if (message[0] == 'B') handleBroadcast(message);
}

void setup() {
    Serial.begin(115200);
}

void loop() {
    delay(100);
    while (Serial.available() && usb.getSpace() > 1) usb.putChar(Serial.read());
    char message[MAX_MESSAGE];
    usb.getMessage(message);
    if (message[0] != NULL) handleMessage(message);
}
