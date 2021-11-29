package elena.krunic.elastic.search.lucene.indexing.handlers;

import java.io.File;

import elena.krunic.elastic.search.lucene.model.IndexUnit;

public abstract class DocumentHandler {
	
	public abstract IndexUnit getIndexUnit(File file); 
	public abstract String getText(File file);

}
