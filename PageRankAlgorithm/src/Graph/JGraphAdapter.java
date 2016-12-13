package Graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import com.jgraph.layout.*;
import com.jgraph.layout.graph.*;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

import Algorithm.PageRank;
import Map.MapEntry;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgraph.graph.GraphLayoutCache;

// The applet that gets drawn
public class JGraphAdapter extends JApplet {
    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 1024, 768 );
    
    // 
    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    
    public static void main(String[] args) {		    	
		// Create an instance of our JGraphAdapter class
    	JGraphAdapter applet = new JGraphAdapter();
    	applet.init();
    	
		// Create a JFrame and add our JGraphAdapter instance to it
    	JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("PageRank");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }

    /**
     * @see java.applet.Applet#init().
     */
    public void init( ) {	    	
    	String readAddr = "tfacts_result.txt";		
		int iterations = 10; // # of iterations for the algorithm
		double damping = 0.85;
		int topK = 5; // # of pages to show in the results
		
		// Used to print the total time the program took to run
		long startTime = System.currentTimeMillis();					
		
		// Create an istance of our algorithm class
		PageRank PR = new PageRank();
		PR.initializeMap(readAddr); // Initialize the map using the path for the text file
		PR.rank(iterations, damping); // Executes the algorithm
		PR.showResults(topK); // Prints the resulting page ranks
		
		// Used to print the total time the program took to run
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("programs runs " + time + "ms");
		
        // create a JGraphT graph
        ListenableDirectedGraph g = new ListenableDirectedGraph( DefaultEdge.class );

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>( g );
        
		// Add the graph to the applet screen
        getContentPane().add(new mxGraphComponent(jgxAdapter));
        resize(DEFAULT_SIZE);               
        
	    // for loop that adds all the vertices and edges to the JGraph in order to display the
		// graph to the screen
		for(Map.Entry<String, ArrayList<MapEntry>> entry : PR.getMap().entrySet()) {
			String starting = entry.getKey();
			ArrayList<MapEntry> list = entry.getValue();
			g.addVertex(starting);
		  
			for(MapEntry ent : list) {
				g.addVertex(ent.getID());
				g.addEdge(starting, ent.getID());
		    }
		}
      
		// Sets the layout of the JGraph instance
	    mxCircleLayout layout = new mxCircleLayout(jgxAdapter); 
	    layout.execute(jgxAdapter.getDefaultParent());
    }
}
