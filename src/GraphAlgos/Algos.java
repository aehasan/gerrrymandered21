import com.google.common.collect.Sets;

import java.util.*;


public class Algos {
    /**
     * Central algo to Gerrymander congressional Districts
     * @param graph A graph of Precints in a specific state
     * @param currentSplit The current split deployed by the recursive backtracking algo
     * @param currentProcess The current vertices in the queue to be processed
     * @return An optimal split
     */
    public static Split partitioner(Graph graph, Split currentSplit, Queue<Vertex> currentProcess) {
        //Pop off the next vertex to be processed
        Vertex j = currentProcess.peek();
        currentProcess.remove();

        //get adjacent vertices copy them over to a set remove ones that have already been seen
        List<Vertex> adjacents = graph.getAdjacentVertices(j);
        Set<Vertex> set = new HashSet<Vertex>();
        for (int i = 0; i < adjacents.size(); i++) {
            if (!currentSplit.hasBeenSeen(adjacents.get(i))) {
                set.add(adjacents.get(i));
            }
        }
        //calculate the valid power set
        Set<Set<Vertex>> powerSet = Sets.powerSet(set);
        List<Split> trackingSplits = new ArrayList<>();
        for (int i = 0; i < powerSet.size(); i++) {
            Iterator<Set<Vertex>> first = powerSet.iterator();
            while(first.hasNext()) {
                Set<Vertex> inner = first.next();
                Vertex[] finalPerm = inner.toArray(new Vertex[inner.size()]);
//                public int districtLimit;
//                public List<ArrayList<Vertex>> districts;
//                public Map<Vertex, Integer> seenSoFar;
//                public int currentPosition;
//                ArrayList<SplitData> trackerAtIndex;
                int districtLim = currentSplit.districtLimit;
                List<ArrayList<Vertex>> currentDistricts = currentSplit.districts;
                Map<Vertex, Integer> seen = currentSplit.seenSoFar;
                int currentPos = currentSplit.currentPosition;

                Queue<Vertex> newQueue = currentProcess;
                Split forCopy = new Split(districtLim, currentDistricts, seen, currentPos, currentSplit.trackerAtIndex);
                forCopy.add(finalPerm);

                for (int z = 0; i < finalPerm.length; i++) {
                    newQueue.add(finalPerm[z]);
                }
                trackingSplits.add(partitioner(graph, forCopy, newQueue));

            }
        }
        return DistrictMax(trackingSplits);

    }

    public static Split DistrictMax(List<Split> passedIn) {

    }
}