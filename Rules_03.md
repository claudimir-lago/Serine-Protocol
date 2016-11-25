# The Serine Protocol (version 0.3)

The Serine Protocol is aimed at allowing communication among small microcontroller as well as computers and humans. The hardware infrastructure should allow the configuration of a network of virtual devices, which is intended to represent a real device, instrument, or equipment that should exchange messages among them. In order to claim to be adherent to Serine Protocol, the devices and network must obey the following rules.

1. A message is a variable-size string of ASCII characters ending by a semicolon (code 59).
  * 1.1. The maximum size of a message is 32 bytes including the semicolon (code 59).
2. A subset of the ASCII codes are used for the communication:
  * 2.1. Only characters whose codes are in the range from 34 (“) up to 126 (~) are valid to compose a message. Any other character out of this range should be suppressed from the string before any attempt of interpreting a message.
  * 2.2. Code 33 (!) informs that it and any other previously received character or complete messages not yet processed should be disregarded without further treatment.
  * 2.3. The messages are case sensitive.
3. Each virtual device is identified by one ASCII characters in the range of codes 34 (“) and 126 (~), except for code 59 (;), and it must be unique in the network.
  * 3.1. The first character in a message identifies the addressed virtual device.
  * 3.2. The second character in a message identifies the sender of the message, i.e., the virtual device to which a message should be send in case of reply.
  * 3.3. The virtual device B (code 66) is a reserved virtual device that means broadcasting. When a message is send to B, all virtual devices should be able to handle it.
4. The content of the message starts at the third position of the string and should be interpreted from this position up to the ending semicolon.
5. Each virtual device must have an identification string containing only ASCII characters in the range from codes 34 (“) up to 126 (~), except for code 59 (;), and this string should be unique among all the possible virtual devices that are or can be engaged in the network.
  * 5.1. Only identification strings provided by the [Serine Identification Service](https://github.com/claudimir-lago/Serine-Identification-Service) begin with S (code 83).
  * 5.2. Proprietary identification strings must begin with P (code 80).
  * 5.3. An identification string starting with a t (code 116) means that this identification is temporary and should be changed in the future.
  * 5.4. The remainder codes may be used by other public providers of identification service.
6. A content starting with and I (code 73) is a reserved command that ask the identification of the virtual device. As the answer, the virtual device should send back a message with the content starting with an i (code 105) followed by its identification string.
  * 6.1. A content starting with Ix (codes 73 and 120) renames the addressed device using the first character after the Ix command, if the remainder of the content matches the identification string of that virtual device.
