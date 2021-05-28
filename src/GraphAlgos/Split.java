import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Split {
    public int districtLimit;
    public List<ArrayList<Vertex>> districts;
    public Map<Vertex, Integer> seenSoFar;
    public int currentPosition;
    ArrayList<SplitData> trackerAtIndex;
    Map<Vertex, Integer> attemptedThisPartition;
    Map<Vertex, Integer> seenThisPartition;
    int totalAddedTracker;

    public class SplitData {
        public int clinton;
        public int trump;
        public int total;
        SplitData() {
            clinton = 0;
            trump = 0;
            total = 0;
        }


    }
    public Split(    int districtLimit,
            List<ArrayList<Vertex>> districts,
            Map<Vertex, Integer> seenSoFar,
            int currentPosition,
            ArrayList<SplitData> trackerAtIndex,
                     Map<Vertex, Integer> attemptedThisPartition
    ) {
        this.districtLimit = districtLimit;
        this.districts = districts;
        this.seenSoFar = seenSoFar;
        this.currentPosition = currentPosition;
        this.trackerAtIndex = trackerAtIndex;
        this.attemptedThisPartition = attemptedThisPartition;

    }
    public Split(Split passedIn) {
        this.districtLimit = passedIn.districtLimit;
        this.districts = new ArrayList<ArrayList<Vertex>>();
        for (int i = 0; i < passedIn.districts.size(); i++) {
            districts.add(new ArrayList<Vertex>());
            for (int j =0; j < passedIn.districts.get(i).size(); j++) {
                districts.get(i).add(passedIn.districts.get(i).get(j));
            }
        }

        this.seenSoFar = new HashMap<Vertex, Integer>(passedIn.seenSoFar);
        this.currentPosition = passedIn.currentPosition;
        this.trackerAtIndex = new ArrayList<SplitData>();
        for (int i = 0; i < passedIn.trackerAtIndex.size(); i++) {
            SplitData toSet = new SplitData();
            SplitData former = passedIn.trackerAtIndex.get(i);
            toSet.clinton = former.clinton;
            toSet.trump = former.trump;
            toSet.total = former.total;
            trackerAtIndex.add(toSet);
        }
        this.attemptedThisPartition = new HashMap<Vertex, Integer>(passedIn.attemptedThisPartition);
        totalAddedTracker = passedIn.totalAddedTracker;
        seenThisPartition = new HashMap<Vertex, Integer>(passedIn.seenThisPartition);

    }
    public Split(int numberOfDistricts, int districtLimit) {
        this.districtLimit = districtLimit;
        districts = new ArrayList<ArrayList<Vertex>>();
        trackerAtIndex = new ArrayList<SplitData>();
        seenSoFar = new HashMap<Vertex, Integer>();
        attemptedThisPartition = new HashMap<Vertex, Integer>();
         for (int i = 0; i < numberOfDistricts; i++) {
             districts.add(new ArrayList<Vertex>());
             trackerAtIndex.add(new SplitData());
         }
        this.totalAddedTracker = 0;
        seenThisPartition = new HashMap<Vertex, Integer>();

    }

    /**
     * Stores all districts per each state. Automatically shifts to a new district when an old one has reached
     * population limits.
     * @param toAdd List of vertices to be added to current partition
     */
    public void add(Graph graph, Vertex[]toAdd) {
        if (currentPosition >= districts.size()) {
            return;
        }
        for (int i = 0; i < toAdd.length; i++) {
            toAdd[i] = graph.getRealVertex(toAdd[i].name);
        }

        List<Vertex> current = districts.get(currentPosition);
        for (int i = 0; i < toAdd.length; i++) {
            seenSoFar.put(toAdd[i], 0);
            if (trackerAtIndex.get(currentPosition).total < districtLimit)  {
                trackerAtIndex.get(currentPosition).total += toAdd[i].trump + toAdd[i].clinton;
                trackerAtIndex.get(currentPosition).trump += toAdd[i].trump;
                trackerAtIndex.get(currentPosition).clinton += toAdd[i].clinton;

                current.add(toAdd[i]);
                totalAddedTracker++;
            } else {
                currentPosition++;
                attemptedThisPartition.clear();
                seenThisPartition.clear();
                if (currentPosition >= districts.size()) {
                    return;
                }
                current = districts.get(currentPosition);
                trackerAtIndex.get(currentPosition).total += toAdd[i].trump + toAdd[i].clinton;
                trackerAtIndex.get(currentPosition).trump += toAdd[i].trump;
                trackerAtIndex.get(currentPosition).clinton += toAdd[i].clinton;
                current.add(toAdd[i]);
                totalAddedTracker++;

            }
        }

    }

    public boolean hasBeenSeen(Vertex f) {
        if (seenSoFar.get(f) == null) {
            return false;
        }

        return true;
    }

    public String toString() {
        return districts.toString();
    }
}
