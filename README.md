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

# Document
Documents are simple java beans, they can be instantiated using `new`, fields are accessed using the according setter/getter accessor methods. Fluent setters are also available.

Example of creating a minimal `Document` with some fields:

    // Source keeps same, use a constant
	private static final Source SOURCE = new Source("MyDMS", null, "https://www.example.com", "contact@example.com");
    
    Document doc = new Document().source(SOURCE);
    DocumentFile docfile = new DocumentFile(omdoc.getTitle());
    docfile.addRevision(new Revision(omdoc.getCreatedTime()).data(stream));
    doc.addDocumentFile(docfile);
    // use a setter
    doc.setNote(omdoc.getNotes());
    // or the fluent methods
    doc.note(omdoc.getNotes()).labels(omdoc.getTagsArray()).idSystem(omdoc.getUuid()).project(omdoc.getCategory());

# Compatiblity
dms-exchange-java supports the [dms-exchange-specification](https://github.com/galan/dms-exchange-specification) version 1.0.0.

When newer versions of dms-exchange-specification will be released, they will be supported additionally. 

# Integration with Maven
Use the [maven repository and artifact](https://github.com/galan/maven-repository) on github.
