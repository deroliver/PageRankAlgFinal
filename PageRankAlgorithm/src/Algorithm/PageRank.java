package Algorithm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import Graph.JGraphAdapter;
import Map.MapEntry;
import Map.MapNode;


// The algorithm class
public class PageRank {
	private Map<String, ArrayList<MapEntry>> map = null; // Holds the data for the tree
	private List<MapNode> rankedList = null; // List of all nodes
	
	// Constructor
	public PageRank() {
		map = new HashMap<String, ArrayList<MapEntry>>();	
	}
	
	// Returns the map
	public Map<String, ArrayList<MapEntry>> getMap() { 
		return map; 
	};
	
	// @param addr: The address of the node data
	// Reads in node data and their relationships into the map
	public void initializeMap(String addr) {
		BufferedReader inputStream = null;
		String line = null;
		
		try {
			inputStream = new BufferedReader(new FileReader(addr));
			line = inputStream.readLine();
			
			String node1 = null;
			String node2 = null;
			double edgeWeight = 0;
			
			while(line != null) {
				line = line.trim();
				
				String entries[] = line.split("\t");
				
				node1 = entries[0];
				System.out.println();
				node2 = entries[1];
				edgeWeight = Double.parseDouble(entries[2]);
				
				this.addEntry(node1, node2, edgeWeight);
				this.addEntry(node2, node1, edgeWeight);
				
				line = inputStream.readLine();				
			}
			inputStream.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Initialization FINISHED");
	}
	
	// @param node1: The first node to add
	// @param node2: The second node to add
	// @param weight: The weight of this relationship
	// Adds a relationship between two nodes to the map
	public void addEntry(String node1, String node2, double weight) {
		MapEntry entry = new MapEntry(node2, weight);
		if(this.map.containsKey(node1)) { // If the first node is already in the map
			if(!map.get(node1).contains(entry)) {
				this.map.get(node1).add(entry); // Add the relationship to the node
			}
		} else { // Else add a new node to the map
			ArrayList<MapEntry> list = new ArrayList<MapEntry>();
			list.add(entry);
			this.map.put(node1, list);
		}
	}
	
	// @param iterations: The number of iterations
	// Ranks all the nodes in the map
	public void rank(int iterations, double damping) {
		HashMap<String, Double> lastRank = new HashMap<String, Double>();
		HashMap<String, Double> nextRank = new HashMap<String, Double>();
		
		Double startRank = 1.0 / map.size();
		
		// Initialize the rank of all nodes to 1/N where N = the number of nodes
		for(String key : map.keySet()) {
			lastRank.put(key, startRank);
		}
		
		
		double dampingFactor = 1.0 - damping;
		
		// Heart of the algorithm
		for(int i = 0; i < iterations; i++) { // # of iterations
			for(String key : map.keySet()) { // # of nodes
				double totalWeight = 0;
				for(MapEntry entry : map.get(key)) // # of nodes pointed to
					totalWeight += (entry.getWeight() * lastRank.get(entry.getID()) / this.map.get(entry.getID()).size());
				
				Double next = dampingFactor + (damping * totalWeight);
				nextRank.put(key, next);
			}
			lastRank = nextRank;
		}
		
		System.out.println(iterations + " times iteration finished...");		
		rankedList = PageRankVector(lastRank); // Set the rankedList list member variable
	}
	
	// @param top: The number of nodes to print
	// Function to print the results
	public void showResults(int top) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("   node   |    rank    ");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
		
		int start = 0;
		for(int i = 0; i < top; i++) {			
			String key = rankedList.get(start).getID();
			
			while(key.startsWith("N")) {
				start++;
				if(start > rankedList.size()) {
					System.exit(0);
				}
				key = rankedList.get(start).getID();
			}
			
			double rank = rankedList.get(start).getRank();
			System.out.println("    " + key + "  |" + "    " + rank + "    ");
			start++;
		}
		
	}
	
	// Updates the rankedList member variable
	public List<MapNode> PageRankVector(final HashMap<String, Double> LastRanking) {
		List<MapNode> nodeList = new LinkedList<MapNode>();		
		for(String ID : LastRanking.keySet()) {
			MapNode node = new MapNode(ID, LastRanking.get(ID));
			nodeList.add(node);
		}
		Collections.sort(nodeList);
		return nodeList;
	}
}
