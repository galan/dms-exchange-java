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
