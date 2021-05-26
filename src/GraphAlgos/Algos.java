import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Algos {
    /**
     * Central algo to Gerrymander congressional Districts
     * @param graph A graph of Precints in a specific state
     * @param currentSplit The current split deployed by the recursive backtracking algo
     * @param currentProcess The current vertices in the queue to be processed
     * @return An optimal split
     */
    public static Split partitioner(Graph graph, Split currentSplit, Queue<Vertex> currentProcess) {
        System.out.println("Lets get this show on the road");
        /**
         * There are TWO Base Cases
         * 1. We are out of queues to process however we do not have a fully functional Split
         * 2. We have a valid split and can return
         */
        if (currentProcess.isEmpty() && currentSplit.seenSoFar.size() != graph.vertices.size()) {
            return null;
        } else if (currentProcess.isEmpty()) {
            return currentSplit;
        }
        /**
         * Pop off the Vertex to be processed
         */
        Vertex j = currentProcess.peek();
        currentProcess.remove();

        /**
         * get all adjacent vertices
         */
        List<Vertex> adjacents = graph.getAdjacentVertices(j);
        /**
         * Create a new set
         */
        Set<Vertex> set = new HashSet<Vertex>();
        /**
         * copy over valid vertices to that set (they havent already been processed)
         */
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
                List<ArrayList<Vertex>> currentDistricts = new ArrayList<ArrayList<Vertex>>(currentSplit.districts);
                Map<Vertex, Integer> seen = new HashMap<Vertex, Integer>(currentSplit.seenSoFar);
                int currentPos = currentSplit.currentPosition;

                Vertex[] toCopy = currentProcess.toArray(new Vertex[10000]);
                Queue<Vertex> newQueue = new ArrayBlockingQueue<Vertex>(10000);
                Split forCopy = new Split(districtLim, currentDistricts, seen, currentPos, currentSplit.trackerAtIndex);
                forCopy.add(graph, finalPerm);
                if (forCopy.currentPosition == currentPos) {
                    for (int s = 0; s < toCopy.length; s++) {
                        if (toCopy[s] != null) {
                            newQueue.add(toCopy[s]);
                        }
                    }
                    newQueue = new ArrayBlockingQueue<Vertex>(10000);
                }
                if (finalPerm.length == 0) {
                    trackingSplits.add(partitioner(graph, currentSplit, currentProcess));
                }

                for (int z = 0; i < finalPerm.length; i++) {
                    newQueue.add(finalPerm[z]);
                }
                trackingSplits.add(partitioner(graph, forCopy, newQueue));

            }
        }
        return DistrictMax(trackingSplits);

    }

    public static Split DistrictMax(List<Split> passedIn) {
    Split currentMax = null;
    int maxAt = 0;
    for (int i = 0; i < passedIn.size(); i++) {
        Split temp = passedIn.get(i);
        int currentCounter = 0;
        if (temp == null) {
            continue;
        }
        for (int j = 0; j < temp.trackerAtIndex.size(); j++) {
            int clintonVotes = temp.trackerAtIndex.get(j).clinton;
            int trumpVotes = temp.trackerAtIndex.get(j).trump;
            if (clintonVotes - trumpVotes > 0) {
                currentCounter++;
            }
        }
        if (currentCounter >= maxAt) {
            maxAt = currentCounter;
            currentMax = temp;

        }
    }

    return currentMax;
    }
}