package com.tylerpedersen.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tylerpedersen.model.Racecar;


public class RacecarManager {

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private List<Racecar> raceCars;
	
	private Path path;

	public RacecarManager(Path path) {
		super();
		this.setPath(path);
		try {
			raceCars = this.objectMapper.readValue(path.toFile(),
													new TypeReference<List<Racecar>>() {} );
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Returns a List<Racecar> sorted by speed ascending
	 */
	public List<Racecar> getBySpeedAsc() {
		return raceCars.stream()
				.sorted(Comparator.comparingDouble(Racecar::getSpeed))
				.collect(Collectors.toList());
	}
	
	/*
	 * Returns a String of the JSON representation of List<Racecar>
	 * after removing duplicate elements.
	 */
	public String getRemoveDuplicateDriver() throws JsonGenerationException, JsonMappingException, IOException {
	    List<Racecar> unique = raceCars.stream()
        .collect(
        		Collectors.collectingAndThen(
        				Collectors.toCollection(
        						() -> new TreeSet<>(
        								Comparator.comparing(Racecar::getDriver)
        								)
        						),
        						ArrayList::new)
        		);
		return objectMapper.writeValueAsString(unique);

	}
	
	/*
	 * remove all duplicate drivers
	 */
	public List<Racecar> getRacecarsWithDuplicateDriversRemoved() {
		// for storing uniques
		List<Racecar> uniques = new ArrayList<>();
		// get racecars grouped by count so we know who has duplicates
		Map<String, List<Racecar>> grouped = this.raceCars.stream()
						.collect(Collectors.groupingBy( r -> r.getDriver() ,Collectors.toList()));
		// store uniques which have a size of 1
		for (Map.Entry<String, List<Racecar>> entry : grouped.entrySet()) {
			if( entry.getValue().size() == 1) {
				uniques.add(entry.getValue().get(0));
			}
    		
		}
		return uniques;
	}
	
	/*
	 * Gets all drivers but only once each
	 */
	public List<Racecar> getRacecarUniqueDrivers() {
		return this.raceCars.stream()
        .filter( distinctByKey(r -> r.getDriver() ) )
        .collect( Collectors.toList() );
	}
	
	/*
	 * Sort list descending and group by make
	 */
	public Map<String, List<Racecar>> getBySpeedDescWithGroupBy() {
				
		return this.raceCars.stream()
				.sorted(Comparator.comparingDouble(Racecar::getSpeed).reversed())
				.collect(Collectors.groupingBy(Racecar::getMake));
	}
	
	/*
	 * Find race cars with the supplied driver
	 */
	public List<Racecar> getRacecarSameDriver(String driver) {
				
		return this.raceCars.stream()
					.filter( r -> r.getDriver().equals(driver) )
					.collect(Collectors.toList());
		
	}
	
    private <T>Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor){
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        // add if not in map
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public List<Racecar> getRaceCars() {
		return raceCars;
	}

	public void setRaceCars(List<Racecar> raceCars) {
		this.raceCars = raceCars;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
