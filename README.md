This java-library is the reference-implementation for the [dms-exchange-specification](https://github.com/galan/dms-exchange-specification). It covers the following use-cases:
* Creating new export-archives and adding document-containers
* Reading existing document-container from existing export-archives

The library can be used in any existing dms/software to enable support for the dms-exchange-specification. It is licenced under the conditions of the Apache License 2.0 (see LICENCE file).

# Usage

## Creating an export-archive
Create the writer using one of the convenience construction methods from DmsExchange: 

    DmsWriter writer = DmsExchange.createWriter("/path/to/archive.zip");

Feed the writer with documents:

    writer.add(...);

As last step, close the writer (AutoClosable can be user):

    writer.close();



## Reading an export-archive
Create the reader using one of the convenience construction methods from DmsExchange: 

    DmsReader reader = DmsExchange.createReader("/path/to/archive.zip");

Read the documents using a single consumer:

    reader.readDocuments(document -> /* eg. import to your system */);

Close the reader when finished (AutoClosable can be user):

    reader.close();



## Creating a single container
Use a ContainerSerializer that can be used to convert a single container to a tgz archive. 

    ContainerSerializer serializer = new ContainerSerializer(); // thread-safe

Archive a single container:

	// to an bytearray
    byte[] tgz = serialzer.archive(document, true);
    // to an OutputStream
    serialzer.archive(document, true, new FileOutputStream(..));



## Reading a single container
Use a ContainerDeserializer to convert tgz archive back to a single container:

    ContainerDeserializer deserializer = new ContainerDeserializer(); // thread-safe

Unarchive a single container:

    Document document = deserializer.unarchive(new FileInputStream(..), true);
