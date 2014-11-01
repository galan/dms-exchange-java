package de.galan.dmsexchange;

/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Draft {

	public static void main(String[] args) {
		//DocumentVersions versions = new DocumentVersions();
		//Verjson<Document> verjson = Verjson.create(Document.class, versions);

		/*
		DmsExchange exchange = DmsExchange.create();
		Export export = exchange.export(location eg. stream,file,filename);
		export.addListener(listeners); // better eventbus
		export.exportBy(email);
		export.put(Document); // loop
		export.split(); // ? maybe automaticallly by setting up conditions
		export.finished(); // notifies potential listeners

		DmsExchange exchange = new DmsExchange();
		exchange.register(...);

		// write
		ExportArchive archive = exchange.createArchive(File);

		DocumentContainer container = new DocumentContainer();
		container.addFile();

		archive.addContainer(container);


		Document doc = new Document();
		doc.set..(..);
		DocumentFile file = new DocumentFile();
		ZonedDateTime tsChange = ...;
		file.add(File, tsAdded, "name");
		file.add(InputStream, tsAdded, "name");
		file.add(byte[], tsAdded, "name");
		doc.addFile(DocumentFile file);

		archive.append(document); // Constructs Container, after appended no change
		archive.append(document, archivePath);
		archive.close();

		// read
		ExportArchive archive = exchange.loadArchive(File);
		archive.
		archive.iterate(); // using eventbus
		archive.iterate(Consumer); // + consumer

		listener.onDocument(Document doc) {
			doc.get..();
			List<DocumentFile> file = doc.getFiles();
			file.getTsUpdate();
			File file = file.getCurrent();
			List<File> file.getRevisions();

		}

		 */

		/*
		Import import = exchange.createExport();
		import.register(listener);
		import.import(location eg. stream, file, filename);
		...
		listener.onDocument(Document document);
		listener.afterExport(Export export);
		 */
	}

}
