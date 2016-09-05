package com.compactsolutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class CsvSplitter {

	private final Path inputFile;
	private final Path outputDirectory;
	private final Path[] outputFiles;	
	
	public CsvSplitter(String inputFileString, String outputDirectoryString, int numberOfOutputFiles) throws IOException {
		if (numberOfOutputFiles < 1) {
			throw new IllegalArgumentException("There should be at least one output file");
		}
		inputFile = Paths.get(inputFileString);
		outputDirectory = Paths.get(outputDirectoryString);
		outputFiles = new Path[numberOfOutputFiles];
		createOutputDirectoryAndOutputFiles();
	}
	
	public final void splitOrders() throws IOException {
		final BufferedReader bufferedReader = Files.newBufferedReader(inputFile);
		final String splitBySymbol = ",";
		String line;
		Path currentSmallestFile = outputFiles[0];
		while ((line = bufferedReader.readLine()) != null) {
			final String[] row = line.split(splitBySymbol);
			if (row[0].equals("ORDER")) {
				currentSmallestFile = outputFiles[chooseTheSmallestOutputFileIndex()];
			}
			line += "\n";
			Files.write(currentSmallestFile, line.getBytes(), StandardOpenOption.APPEND);
		}
	}
	
	private int chooseTheSmallestOutputFileIndex() throws IOException {
		int index = 0;
		Path theSmallestFile = outputFiles[0];
		for (int i = 1; i < outputFiles.length; i++) {
			if (Files.size(outputFiles[i]) < Files.size(theSmallestFile)) {
				theSmallestFile = outputFiles[i];
				index = i;
			}
		}
		return index;
	}

	private void createOutputDirectoryAndOutputFiles() throws IOException {
			Files.createDirectories(outputDirectory);
			for (int i = 0; i < outputFiles.length; i++) {
				outputFiles[i] = Paths.get(outputDirectory.toString(), Integer.toString(i) + ".csv");
				Files.createFile(outputFiles[i]);
			}			
	}
}
