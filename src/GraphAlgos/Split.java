import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Split {
    int districtLimit;
    List<ArrayList<Vertex>> districts;
    Map<Vertex, Integer> seenSoFar;
    int currentPosition;
    private class SplitData {
        public int clinton;
        public int trump;
        public int total;
        SplitData() {
            clinton = 0;
            trump = 0;
            total = 0;
        }
    }
    ArrayList<SplitData> trackerAtIndex;
    public Split(int numberOfDistricts, int districtLimit) {
        this.districtLimit = districtLimit;
        districts = new ArrayList<ArrayList<Vertex>>();
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
    public void add(List<Vertex> toAdd) {
        List<Vertex> current = districts.get(currentPosition);
        for (int i = 0; i < toAdd.size(); i++) {
            if (trackerAtIndex.get(currentPosition).total < districtLimit)  {
                trackerAtIndex.get(currentPosition).total += toAdd.get(i).trump + toAdd.get(i).clinton;
                trackerAtIndex.get(currentPosition).trump += toAdd.get(i).trump;
                trackerAtIndex.get(currentPosition).clinton += toAdd.get(i).clinton;

                current.add(toAdd.get(i));
            } else {
                currentPosition++;
                current = districts.get(currentPosition);
                trackerAtIndex.get(currentPosition).total += toAdd.get(i).trump + toAdd.get(i).clinton;
                trackerAtIndex.get(currentPosition).trump += toAdd.get(i).trump;
                trackerAtIndex.get(currentPosition).clinton += toAdd.get(i).clinton;
                current.add(toAdd.get(i));

            }
        }

    }
}
