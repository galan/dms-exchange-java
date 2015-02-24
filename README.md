This java-library is the reference-implementation for the [dms-exchange-specification](https://github.com/galan/dms-exchange-specification). It covers the following use-cases:
* Creating new export-archives and adding document-containers
* Reading existing document-container from existing export-archives

The library can be used in any existing dms/software to enable support for the dms-exchange-specification. It is licenced under the conditions of the Apache License 2.0 (see LICENCE file).

# Usage

## Creating an export-archive
Create the writer using one of the convenience construction methods from DmsExchange: 

    DmsWriter writer = DmsExchange.createWriter("/path/to/archive.tgz");

Feed the writer with documents:

    writer.add(...);

As last step, close the writer (AutoClosable can be used):

    writer.close();

Other writer also support writing to OutputStreams or splitting files depending on conditions, such as amount of document or filesize.


## Reading an export-archive
Create the reader using one of the convenience construction methods from DmsExchange: 

    DmsReader reader = DmsExchange.createReader("/path/to/archive.tgz");

Read the documents using a single consumer:

    reader.readDocuments(document -> /* eg. import to your system */);

