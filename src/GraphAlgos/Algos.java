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
        //System.out.println("Lets get this show on the road");
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
        System.out.println("Working on " + j);

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

        if (set.isEmpty() && currentProcess.isEmpty() && currentSplit.seenSoFar.size() >= graph.vertices.size()) {
            return currentSplit;
        }

        //        public int districtLimit;
//        public List<ArrayList<Vertex>> districts;
//        public Map<Vertex, Integer> seenSoFar;
//        public int currentPosition;
//        ArrayList<Split.SplitData> trackerAtIndex;
        int copyDistrictLimit = currentSplit.districtLimit;
        List<ArrayList<Vertex>> copyDistricts = new ArrayList<ArrayList<Vertex>>(currentSplit.districts);
        Map<Vertex, Integer> copySeenSoFar = new HashMap<Vertex, Integer>(currentSplit.districtLimit);
        int copyCurrentPosition = currentSplit.currentPosition;
        ArrayList<Split.SplitData> copyTrackerAtIndex = new ArrayList<Split.SplitData>(currentSplit.trackerAtIndex);


        int copyDistrictLimit2 = currentSplit.districtLimit;
        List<ArrayList<Vertex>> copyDistricts2 = new ArrayList<ArrayList<Vertex>>(currentSplit.districts);
        Map<Vertex, Integer> copySeenSoFar2 = new HashMap<Vertex, Integer>(currentSplit.districtLimit);
        int copyCurrentPosition2 = currentSplit.currentPosition;
        ArrayList<Split.SplitData> copyTrackerAtIndex2 = new ArrayList<Split.SplitData>(currentSplit.trackerAtIndex);

        Queue<Vertex> copyCurrentProcess = new ArrayBlockingQueue<Vertex>(10000);
        Queue<Vertex> copyCurrentProcess2 = new ArrayBlockingQueue<Vertex>(10000);

        Vertex[] queueToArray = currentProcess.toArray(new Vertex[currentProcess.size()]);
        copyCurrentProcess.addAll(currentProcess);
        copyCurrentProcess2.addAll(currentProcess);
        copyCurrentProcess2.addAll(adjacents);
        Split originalSplit = new Split(copyDistrictLimit, copyDistricts, copySeenSoFar, copyCurrentPosition, copyTrackerAtIndex);
        Split newSplit = new Split(copyDistrictLimit2, copyDistricts2, copySeenSoFar2, copyCurrentPosition2, copyTrackerAtIndex2);
        Vertex[] bruh = new Vertex[1];
        bruh[0] = j;
        newSplit.add(graph, bruh);



        Split withoutCurrent = partitioner(graph, originalSplit, copyCurrentProcess);
        Split withCurrent = partitioner(graph, newSplit, copyCurrentProcess2);
        List<Split> f = new ArrayList<Split>();
        f.add(withoutCurrent);
        f.add(withCurrent);

        //calculate the valid power set

        return DistrictMax(f);

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