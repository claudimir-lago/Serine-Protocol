# Serine-Protocol

1.	The Serine Protocol is aimed at allowing communication among small microcontroller as well as with computers and humans. The hardware infrastructure should allow the configuration of a network of virtual devices, which is intended to represent a real device, instrument, or equipment that should exchange messages among them.
2.	Message is a variable-size string of ASCII characters ending by a semicolon (code 59). Only a subset of the ASCII codes are used to compose a message:
2.1.	Codes 32 (space) and below should be suppressed from the string before interpreting it as a message.
2.2.	Code 33 (!) informs that any other previously received character or complete messages not yet processed should be disregarded.
2.3.	Although codes above 126 are allowed, their usage is strongly discouraged whether the messages are intended to be exchanged with humans.
3.	Each virtual device has an identification string containing only ASCII characters in the range of codes 34 (“) and 126 (~), except for code 59 (;), and this string should be unique among all the possible virtual devices that are or can be engaged in the network.
4.	Each virtual device is identified by one ASCII characters in the range of codes 34 (“) and 126 (~), except for code 59 (;), and it must be unique in the network.
4.1.	The first character in a message identifies the addressed virtual device.
4.2.	The second character in a message identifies the sender of the message, i.e., the virtual device to which a message should be send in case of reply.
4.3.	The virtual device B (code 66) is a reserved virtual device that means broadcast. When a message is send to B, all virtual devices should handle it.
5.	The content of the message starts at the third position and should be interpreted from this position on up to the ending semicolon.
5.1.	A content starting with and I (code 73) is a reserved command that ask the identification of the virtual device. As the answer, the virtual device should send back a message with the content starting with an i (code 105) followed by its identification string.
5.2.	A content starting with Ix (codes 73 and 120) renames the addressed device using the first character after the Ix command, if the remainder of the content matches the identification string contained in the remainder content of the message.
