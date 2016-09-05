package com.compactsolutions;

import java.io.IOException;

public final class CsvSplitterApplication {

	public static void main(String[] args) throws IOException {
		final CsvSplitter csvSplitter = new CsvSplitter(args[0], args[1], Integer.parseInt(args[2]));
		csvSplitter.splitOrders();
		System.out.println("Orders have been split");
	}
}
