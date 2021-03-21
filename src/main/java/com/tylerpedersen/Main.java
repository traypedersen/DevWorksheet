package com.tylerpedersen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tylerpedersen.data.RacecarManager;
import com.tylerpedersen.model.Racecar;

public class Main {

	
	public static void main(String[] args){

		RacecarManager rcm = new RacecarManager(Path.of("./src/main/resources/data/data.json"));
		
		System.out.println("Item 4...");
		rcm.getBySpeedAsc().stream().forEach(System.out::println);
		System.out.println("...");
		
		System.out.println("Item 5...");
		Map<String, List<Racecar>> groupByMakesDesc = rcm.getBySpeedDescWithGroupBy();
		for (Map.Entry<String, List<Racecar>> entry : groupByMakesDesc.entrySet()) {
    		System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		System.out.println("...");
	
		System.out.println("Item 6...");
		rcm.getRacecarSameDriver("Tyler").stream().forEach(System.out::println);
		System.out.println("...");
		
		System.out.println("Item 7...");
		rcm.getRacecarUniqueDrivers().stream().forEach(System.out::println);
		System.out.println("...");
		
		System.out.println("Item 8...");
		rcm.getRacecarsWithDuplicateDriversRemoved().stream().forEach(System.out::println);
		System.out.println("...");
		
		System.out.println("Item 9...");
		try {
			System.out.println(rcm.getRemoveDuplicateDriver());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("...");

	}

}
