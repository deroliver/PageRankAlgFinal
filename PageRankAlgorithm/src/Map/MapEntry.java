package Map;

// Used to model an edge between two vertices
public class MapEntry {
	private String ID = ""; // The ID for this edge
	private double weight; // The weight of this edge
	
	// Constructor
	public MapEntry(String ID, double weight) {
		this.ID = ID;
		this.weight = weight;
	}
	

	
	/////////////////////////
	// GETTERS AND SETTERS //
	/////////////////////////
	
	public String getID() {
		return this.ID;	
	}

	public void setID(String ID) {
		this.ID = ID;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
