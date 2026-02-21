package com.example.trafficcounter;

public class TrafficCounterApplication {

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Input file path is required as an argument.");
		}

		try {
			String filePath = args[0];
			TrafficCounterRunner runner = new TrafficCounterRunner();
			runner.run(filePath);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
