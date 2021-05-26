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
            ArrayList<SplitData> trackerAtIndex
    ) {
        this.districtLimit = districtLimit;
        this.districts = districts;
        this.seenSoFar = seenSoFar;
        this.currentPosition = currentPosition;
        this.trackerAtIndex = trackerAtIndex;

    }
    public Split(int numberOfDistricts, int districtLimit) {
        this.districtLimit = districtLimit;
        districts = new ArrayList<ArrayList<Vertex>>();
        trackerAtIndex = new ArrayList<SplitData>();
        seenSoFar = new HashMap<Vertex, Integer>();
         for (int i = 0; i < numberOfDistricts; i++) {
             districts.add(new ArrayList<Vertex>());
             trackerAtIndex.add(new SplitData());
         }
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
            } else {
                currentPosition++;
                if (currentPosition >= districts.size()) {
                    return;
                }
                current = districts.get(currentPosition);
                trackerAtIndex.get(currentPosition).total += toAdd[i].trump + toAdd[i].clinton;
                trackerAtIndex.get(currentPosition).trump += toAdd[i].trump;
                trackerAtIndex.get(currentPosition).clinton += toAdd[i].clinton;
                current.add(toAdd[i]);

            }
        }

    }

    public boolean hasBeenSeen(Vertex f) {
        if (seenSoFar.get(f) == null) {
            return false;
        }

        return true;
    }
}
