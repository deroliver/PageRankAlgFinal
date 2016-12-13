package Map;

// Used to model a vertex or a node in the graph
public class MapNode implements Comparable<MapNode> {
	private String ID = ""; // The ID of the node
	private double rank = 0; // The page rank of this node
	
	// Constructor
	public MapNode(String ID, double rank) {
		this.ID = ID;
		this.rank = rank;
	}
	
	
	/////////////////////////
	// GETTERS AND SETTERS //
	/////////////////////////
	
	public String getID() {
		return ID;
	}
	
	public double getRank() {
		return rank;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void setRank(double rank) {
		this.rank = rank;
	}
	
	@Override
	public int compareTo(MapNode other) {
		return rank > other.getRank() ? -1 : rank < other.getRank() ? 1 : 0;		
	}
	
}
