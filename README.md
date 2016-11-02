# Serine-Protocol

  _Serine is an abbreviation of Serial Network and a chemical compound – after all, the protocol was born in a chemistry laboratory. Serine has two optical isomers: L-serine, the well-known proteinogenic amino acid, and D-serine, a signaling substance in bacteria and a neurotransmitter in humans. Therefore, Serine is anyway about communication among small and big things._

Serine Protocol is a light communication protocol intended to be used with small microcontrollers and some sort of serial communication channels, such as UART, RS232, I2C, USB, and so on. Some headlines guided its formulation and usage:

 * The exchanged messages should be easily and unequivocally interpreted by a simple microcontroller.

 * The messages should resist, as originally generated, to the particularities of all hardware and software protocols used over the entire network.

 * The messages should be as readable and writable as possible by humans.

The messages are exchanged among several logical pieces named virtual devices. A virtual device is something that is visualized by the members of the network as an entity that is able to do things, reacts to the received messages, and produces its own messages to the other members. A virtual device is implemented on one or more pieces of hardware. Therefore:

 * A virtual device may be implemented in one exclusive microcontroller;

 * A virtual device may be implemented in two or more microcontrollers, each one responding for specific features of the virtual device;

 * On the other hand, a microcontroller may be the site for two or more virtual devices.

The pieces of hardware are connected thru a physical network and they are responsible to decide through which communication channel a message must follow. Therefore, when a message is received, it has to know if the addressed device (first character of the message) is reachable through, for instance, the USB, UART, or Ethernet ports. The hardware piece sending the message does not know whether the destination was implemented in the next hardware piece, or the message will follow through two or more other hardware pieces and different communication channels. There is neither checking point, nor dynamic management of the physical network. Therefore, the designer must be watchful.

Some scientific papers in which the Serine Protocol (formerly LAIA Protocol) was used in developing of the instrumentation:

  - Francisco, K. J. M. & do Lago, C. L. A capillary electrophoresis system with dual capacitively coupled contactless conductivity detection and electrospray ionization tandem mass spectrometry. _Electrophoresis_ __37__, 1718-1724, [doi:10.1002/elps.201600005](http://onlinelibrary.wiley.com/doi/10.1002/elps.201600005/abstract) (2016).

  - da Costa, E. T. et al. Unmanned platform for long-range remote analysis of volatile compounds in air samples. _Electrophoresis_ __33__, 2650-2659, [doi:10.1002/elps.201200273](http://onlinelibrary.wiley.com/doi/10.1002/elps.201200273/abstract) (2012).

  - Francisco, K. J. M. & do Lago, C. L. A compact and high-resolution version of a capacitively coupled contactless conductivity detector. _Electrophoresis_ __30__, 3458-3464, [doi:10.1002/elps.200900080](http://onlinelibrary.wiley.com/doi/10.1002/elps.200900080/abstract) (2009).


