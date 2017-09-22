### This is a simple high low game between a server and a client

* It uses TCP as its underlying protocol. Here is a [UDP](../UDP/) version doing the same thing.

* A Host address and port # must be passed in as command line arguments.


* This is simple. Yes I ought to thread this so that I could support multiple games at once. This is not implemented purposely.

* This passes a Serializable object back and forth. A protocol using string can be found [here](../UDP/)
