import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
/**
 * A barebones Graph class to accomplish the problem at hand. :). It gets the job done
 */
public class Graph {
    /**
     * Most Likely Going to accept a CSV
     */
     public Graph() {

     }

    /**
     * A List of all vertices and their associated values.
     */
     private List<Vertex> vertices;

    /**
     * A list of all Edges and there associated values;
     */
    private List<Edge> edges;
    /**
     * An adjacency matrix for O(1) lookup.
     */
    private Map<Vertex, ArrayList<Vertex>> adjacencyStuff;

    /**
     *
     * @param f The vertex you are tryna find.
     * @return A list of relevant vertices
     */
    public List<Vertex> getAdjacentVertices(Vertex f) {
        return adjacencyStuff.get(f);
    }

}