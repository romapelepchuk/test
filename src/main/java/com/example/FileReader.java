package com.example;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FileReader {

	/**
	 * Retrieve the Stream of Lines from File
	 * 
	 * @param fileName
	 *            the file path and name
	 * @param uriPath
	 * @return the stream
	 * @throws IOException
	 */
	public Stream<String> readFile(URI uriPath) throws IOException {
		Assert.notNull(uriPath, "Uri Path must not be null!");

		Stream<String> fileStream = null;
		try {
			Path path = Paths.get(uriPath);
			fileStream = Files.lines(path, Charset.defaultCharset());
		} catch (IOException ioe) {
			System.out.println("Could not Open File with path " + uriPath + " " + ioe.getMessage());
			ioe.printStackTrace();
			throw ioe;
		}
		return fileStream;
	}
}
