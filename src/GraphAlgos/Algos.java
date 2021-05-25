import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
public class Algos {
    public static void partitioner(Graph graph, Split currentSplit, Queue<Vertex> currentProcess, Map<> seenSoFar ) {
        //below is pseudocode for now:
        Vertex j = currentProcess.peek();
        currentProcess.remove();


        List<Vertex> adjacents = graph.getAdjacentVertices(j);
        Set<Vertex> set = new HashSet<Vertex>();


        Set<Set<Vertex>> powerset = Sets.powerSet(set);
        currentSplit.add(adjacents);
        for (int i = 0; i < powerset.size(); i++) {

        }

    }
}