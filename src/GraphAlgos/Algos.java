
import java.sql.Array;
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
    public static Split partitioner(Graph graph, Split currentSplit, Stack<Vertex> currentProcess) {
        //System.out.println("Lets get this show on the road");
        /**
         * There are TWO Base Cases
         * 1. We are out of queues to process however we do not have a fully functional Split
         * 2. We have a valid split and can return
         */

        /**
         * Pop off the Vertex to be processed
         */

        if (currentProcess.isEmpty() && currentSplit.totalAddedTracker != graph.vertices.size()) {
            //System.out.println(currentProcess);
            //System.out.println(currentSplit);
            return null;
        } else if (currentProcess.isEmpty()) {
            //System.out.println("hereeee");
            //System.out.println(currentSplit);
            return currentSplit;
        }

        Vertex j = currentProcess.peek();
        currentProcess.pop();
        //System.out.println("Working on " + j);

        /**
         * get all adjacent vertices
         */
        List<Vertex> adjacentsOriginal = graph.getAdjacentVertices(j);
        /**
         * copy over valid vertices to that set (they havent already been processed)
         */
        List<Vertex> adjacents = new ArrayList<Vertex>();
        for (int i = 0; i < adjacentsOriginal.size(); i++) {
            if ((currentSplit.seenSoFar.get(adjacentsOriginal.get(i)) == null)
                    && (currentSplit.attemptedThisPartition.get(adjacentsOriginal.get(i)) == null) && (currentSplit.seenThisPartition.get(adjacentsOriginal.get(i)) == null)) {
                adjacents.add(adjacentsOriginal.get(i));
            }
        }


        //        public int districtLimit;
//        public List<ArrayList<Vertex>> districts;
//        public Map<Vertex, Integer> seenSoFar;
//        public int currentPosition;
//        ArrayList<Split.SplitData> trackerAtIndex;
//        int copyDistrictLimit = currentSplit.districtLimit;
//        List<ArrayList<Vertex>> copyDistricts = new ArrayList<ArrayList<Vertex>>(currentSplit.districts);
//        Map<Vertex, Integer> copySeenSoFar = new HashMap<Vertex, Integer>(currentSplit.districtLimit);
//        int copyCurrentPosition = currentSplit.currentPosition;
//        ArrayList<Split.SplitData> copyTrackerAtIndex = new ArrayList<Split.SplitData>(currentSplit.trackerAtIndex);
//        Map<Vertex, Integer> copyAttemptedThisPartition = new HashMap<Vertex, Integer>(currentSplit.attemptedThisPartition);
//
//
//        int copyDistrictLimit2 = currentSplit.districtLimit;
//        List<ArrayList<Vertex>> copyDistricts2 = new ArrayList<ArrayList<Vertex>>(currentSplit.districts);
//        Map<Vertex, Integer> copySeenSoFar2 = new HashMap<Vertex, Integer>(currentSplit.districtLimit);
//        int copyCurrentPosition2 = currentSplit.currentPosition;
//        ArrayList<Split.SplitData> copyTrackerAtIndex2 = new ArrayList<Split.SplitData>(currentSplit.trackerAtIndex);
//        Map<Vertex, Integer> copyAttemptedThisPartition2 = new HashMap<Vertex, Integer>(currentSplit.attemptedThisPartition);
//        copyAttemptedThisPartition.put(j, 0);
//        copyAttemptedThisPartition2.put(j, 0);
        Stack<Vertex> copyCurrentProcess = new Stack<Vertex>();
        Stack<Vertex> copyCurrentProcess2 = new Stack<Vertex>();
        
        Vertex[] queueToArray = currentProcess.toArray(new Vertex[currentProcess.size()]);
        copyCurrentProcess.addAll(currentProcess);
        copyCurrentProcess2.addAll(currentProcess);
        copyCurrentProcess2.addAll(adjacents);
        Split originalSplit = new Split(currentSplit);
        Split newSplit = new Split(currentSplit);
        // make the current vertex partition as attempted
        originalSplit.attemptedThisPartition.put(j, 0);
        newSplit.attemptedThisPartition.put(j, 0);
        //make all neighbours as has been seen for newSplit
        for (int i = 0; i < adjacents.size(); i++) {
            newSplit.seenThisPartition.put(adjacents.get(i), 0);
        }

        Vertex[] bruh = new Vertex[1];
        bruh[0] = j;
        newSplit.add(graph, bruh);
        adjacents.clear();
        for (int i = 0; i < adjacentsOriginal.size(); i++) {
            if ((newSplit.seenSoFar.get(adjacentsOriginal.get(i)) == null)
                    && (newSplit.attemptedThisPartition.get(adjacentsOriginal.get(i)) == null) && (newSplit.seenThisPartition.get(adjacentsOriginal.get(i)) == null)) {
                adjacents.add(adjacentsOriginal.get(i));
            }
        }
        //copyCurrentProcess.addAll(adjacents);
        List<Vertex> testingThing = new ArrayList<Vertex>();
        for (int i = 0; i < adjacentsOriginal.size(); i++) {
            if ((newSplit.seenSoFar.get(adjacentsOriginal.get(i)) == null)
                    && (newSplit.attemptedThisPartition.get(adjacentsOriginal.get(i)) == null)) {
                testingThing.add(adjacentsOriginal.get(i));
            }
        }
        copyCurrentProcess2.addAll(adjacents);
//        System.out.println(j);
        //System.out.println(originalSplit);
        //System.out.println(newSplit);
        //System.out.println(copyCurrentProcess2);

//        if (currentSplit.totalAddedTracker != graph.vertices.size() && currentProcess.isEmpty()) {
//            System.out.println("this null");
//            return null;
//        } else if (adjacents.isEmpty() && currentProcess.isEmpty()) {
//            return currentSplit;
//        }

        Split withoutCurrent = null;
        if (testingThing.size() >= 13) {
            withoutCurrent = partitioner(graph, originalSplit, copyCurrentProcess);
        }

        Split withCurrent = partitioner(graph, newSplit, copyCurrentProcess2);
        List<Split> f = new ArrayList<Split>();
            f.add(withoutCurrent);

        f.add(withCurrent);
        //System.out.println(newSplit.districts);
        //calculate the valid power set

        return DistrictMax(f);

    }

    public static Split DistrictMax(List<Split> passedIn) {
        //System.out.println("its someting");
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
        if (currentCounter > maxAt) {
            maxAt = currentCounter;
            currentMax = temp;

        }
    }

    return currentMax;
    }

    public static boolean isConnected(Graph graph) {
        Stack<Vertex> queue = new Stack();
        queue.add(graph.vertices.get(0));
        Map<Vertex, Integer> visitTracker = new HashMap<Vertex, Integer>();
        visitTracker.put(graph.vertices.get(0), 1);
        while (!queue.isEmpty()) {
            Vertex j = queue.peek();
            queue.pop();
            System.out.println(j);
            List<Vertex> adjacents = graph.getAdjacentVertices(j);
            if (j.equals(new Vertex("BELVIDERE 6", 0, 0))) {
                System.out.println(adjacents);
            }
            for (int i = 0; i < adjacents.size(); i++) {

                if (visitTracker.containsKey(adjacents.get(i)) == false) {
                    visitTracker.put(adjacents.get(i), 1);
                    queue.add(adjacents.get(i));
                }
            }

        }
        if (visitTracker.size() == graph.vertices.size()) {
            return true;
        }
        System.out.println(visitTracker.size());
        List<Vertex> newVertex = new ArrayList<>();
        for (int i = 0; i < graph.vertices.size(); i++) {
            newVertex.add(new Vertex(graph.vertices.get(i).name, graph.vertices.get(i).clinton, graph.vertices.get(i).trump));
        }
        for (Map.Entry mapElement : visitTracker.entrySet()) {
            Vertex key = (Vertex) mapElement.getKey();
            for (int i = 0; i < newVertex.size(); i++) {
                if (newVertex.get(i).equals(key)) {
                    newVertex.remove(i);
                    break;
                }
            }

        }

        System.out.println(newVertex);

        return false;

    }

    public static Split iterativeGerrymander(Graph graph) {
        //Map<Vertex, Integer> seenSoFar = new HashMap<Vertex, Integer>();
        Stack<Vertex> stack = new Stack<Vertex>();
        //ArrayList<Vertex> newStack = new ArrayList<Vertex>();
        //Split split = new Split(18, 290000);
        //ewStack.add(graph.vertices.get(3000));
        int lastPicked = 0;
        int lastPicked2 = 0;
        int lastPicked3 = 0;
        int totalMax = 0;
        Split maxMax = null;
        ArrayList<Split> maxSplit = new ArrayList<Split>();
        for (int o = 0; o < 1; o++) {
            System.out.println(o);
            Split split = new Split(18, 300000);
            ArrayList<Vertex> newStack = new ArrayList<Vertex>();
            ArrayList<Vertex> magic = new ArrayList<Vertex>();
            Map<Vertex, Integer> seenSoFar = new HashMap<Vertex, Integer>();
            newStack.add(graph.vertices.get(5000));
            seenSoFar.put(graph.vertices.get(5000), 0);
            int one = 0;
            int two = 0;
            int three = 0;
            while ((split.seenSoFar.size() != graph.vertices.size())) {
                Vertex f = null;
                int clinton = split.trackerAtIndex.get(split.currentPosition).clinton;
                int trump = split.trackerAtIndex.get(split.currentPosition).trump;
                int total = split.trackerAtIndex.get(split.currentPosition).total;
//                if (clinton > trump) {
                   //f = newStack.get(0);
                   //newStack.remove(0);
//                    lastPicked = 0;
//                    //lastPicked2 = 0;
//                System.out.println(f);
//                one = 0;
//                two = 0;
//                three = 0;
//
//                } else {
                    f = newStack.get(newStack.size() - 1);
                    newStack.remove(newStack.size() - 1);
//                    if (one == 0) {
//                        one = 1;
//                    } else if(two == 0) {
//                        two = 1;
//                    } else if (three == 0) {
//                        three = 1;
//                    }
//                }
                    Vertex[] j = {f};
                    int beforePosition = split.currentPosition;
                    split.add(graph, j);
//                    if (beforePosition != split.currentPosition) {
//
//                        seenSoFar = new HashMap<Vertex, Integer>(split.seenSoFar);
//
//                        Vertex z = newStack.get(0);
//                        f = z;
//                        j[0] = f;
//
//                        split.add(graph, j);
//
//                        newStack = new ArrayList<Vertex>();
//                        //newStack.remove(0);
//                    }



                    List<Vertex> adjacents = graph.getAdjacentVertices(f);

                    for (int i = 0; i < adjacents.size(); i++) {
                        if (!seenSoFar.containsKey(adjacents.get(i))) {
                            seenSoFar.put(adjacents.get(i), 0);
                            newStack.add(adjacents.get(i));
                        }
                    }


            }

            List<Split.SplitData> current = split.trackerAtIndex;
            int max = 0;
            for (int z = 0; z < current.size(); z++) {
                if (current.get(z).clinton > current.get(z).trump) {
                    max++;
                }
            }

            if (max > totalMax) {
                totalMax = max;
                maxMax = split;
            }
            System.out.println(totalMax);
            //System.out.println(maxMax);

            //System.out.println(split);

        }
        System.out.println("t" + totalMax);

        return maxMax;
    }
}