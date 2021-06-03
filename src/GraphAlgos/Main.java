import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String args[]) {


        Graph graph = new Graph("/Users/ahmed/Desktop/Projects/gerrymandered/src/GraphAlgos/testingOnLarger.csv");

        System.out.println(Arrays.toString(graph.vertices.toArray()));
        System.out.println(graph.adjacencyStuff.toString());
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("./graphStructure.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myWriter.write(Arrays.toString(graph.vertices.toArray()) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myWriter.write(graph.adjacencyStuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Split split = new Split(4, 180000);
        Queue queue = new ArrayBlockingQueue<Vertex>(10000);
        queue.add(graph.vertices.get(0));
        Split j = Algos.partitioner(graph, split, queue);
        //boolean f = Algos.isConnected(graph);
        System.out.println(j);
        List<Split.SplitData> f = j.trackerAtIndex;
        int counter = 0;
        for (int i = 0; i < f.size(); i++) {
            System.out.println(f.get(i).clinton + "-" + f.get(i).trump );
            if (f.get(i).clinton > f.get(i).trump) {
                counter++;
            }
        }
        System.out.println(counter);
    }
}
