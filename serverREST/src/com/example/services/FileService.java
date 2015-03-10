package com.example.services;

import com.example.model.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.example.exceptions.FileAlreadyExistsException;
import com.example.exceptions.FileNotFoundException;

public class FileService {
	private final ConcurrentMap<String, File> files = new ConcurrentHashMap<String, File>();
	
		
	public Collection< File > getFile( int page, int pageSize ) {
		final Collection< File > slice = new ArrayList< File >( pageSize );
		
        final Iterator< File > iterator = files.values().iterator();
        for( int i = 0; slice.size() < pageSize && iterator.hasNext(); ) {
        	if( ++i > ( ( page - 1 ) * pageSize ) ) {
        		slice.add( iterator.next() );
        	}
        }
		
		return slice;
	}
	
	public File getByPath(final String filePath) {
		final File file = files.get(filePath);	
		if(file == null) {
			throw new FileNotFoundException(filePath);
		}
		return file;
	}

	public File addFile(String filePath, String name) {
		File file = new File(filePath, name);			
		if( files.putIfAbsent(filePath, file) != null ) {
			throw new FileAlreadyExistsException(filePath);
		}
		return file;
	}
	
	public void removeFile(String filePath) {
		if( files.remove(filePath) == null ) {
			throw new FileNotFoundException(filePath);
		}
	}

	
}
