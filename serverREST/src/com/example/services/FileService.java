package com.example.services;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileService {
	private final ConcurrentMap<Integer, File> files = new ConcurrentHashMap<Integer, File>();

	
}
