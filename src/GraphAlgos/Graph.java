import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * A barebones Graph class to accomplish the problem at hand. :). It gets the job done
 */
public class Graph {
    /**
     * Most Likely Going to accept a CSV
     * Assume 0 has the vertex name 1 has the clinton votes 2 has the trump votes and 3 has neighboring districts
     */
     public Graph(String path) {
         vertices = new ArrayList<Vertex>();
         adjacencyStuff = new HashMap<Vertex, ArrayList<Vertex>>();
         BufferedReader csvReader = null;
         try {
              csvReader = new BufferedReader(new FileReader(path));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
         String row = null;
         try {
             row = csvReader.readLine();
         } catch (IOException e) {
             e.printStackTrace();
         }
         while (row != null) {
             System.out.println(row);
             String[] data = row.split(",");
             System.out.println(data.length);
             System.out.println(Arrays.toString(data));
             String adjacents = data[3];
             ArrayList<Vertex> myAdjacencyMatrix = new ArrayList<Vertex>();
             for (int j = 3; j < data.length; j++) {
                 if (data[j].trim().replaceAll("\"", "").equals("")) {
                     continue;
                 }
                 if (data[j].trim().replaceAll("\"", "").equals("GALE")) {
                     continue;
                 }
                 myAdjacencyMatrix.add(new Vertex(data[j].trim().replaceAll("\"", ""), 0, 0));
             }
             Vertex toAdd = new Vertex(data[0].trim().replaceAll("\"", ""), Integer.parseInt(data[1].trim()), Integer.parseInt(data[2].trim()));
             vertices.add(toAdd);
             adjacencyStuff.put(toAdd, myAdjacencyMatrix);

             try {
                 row = csvReader.readLine();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }


     }



    /**
     * A List of all vertices and their associated values.
     */
     public List<Vertex> vertices;

    /**
     * A list of all Edges and there associated values;
     */
    private List<Edge> edges;
    /**
     * An adjacency matrix for O(1) lookup.
     */
    public Map<Vertex, ArrayList<Vertex>> adjacencyStuff;

    /**
     *
     * @param f The vertex you are tryna find.
     * @return A list of relevant vertices
     */
    public List<Vertex> getAdjacentVertices(Vertex f) {
        return adjacencyStuff.get(f);
    }

    public Vertex getRealVertex(String f) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).name.equals(f)) {
                return vertices.get(i);
            }
        }

        return null;
    }

}