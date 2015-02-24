Other use-cases for future use-cases 

## Creating an archive for a single container
A single container archive can be created and read using the `ContainerSerializer` respectivly the `ContainerDeserializer`.

Creation example:

    ContainerSerializer serializer = new ContainerSerializer(); // thread-safe, re-use
	// Archive a single container using to a bytearray
    byte[] tgz = serialzer.archive(document, true);
	// Archive a single container using to an OutputStream
    serialzer.archive(document, true, new FileOutputStream(..));

Reading example:

    ContainerDeserializer deserializer = new ContainerDeserializer(); // thread-safe, re-use
	// Unarchive a single container:
    Document document = deserializer.unarchive(new FileInputStream(..), true);
