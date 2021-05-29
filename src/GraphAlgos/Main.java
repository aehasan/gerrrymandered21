import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String args[]) {


        Graph graph = new Graph("/Users/ahmed/Desktop/Projects/gerrymandered/src/GraphAlgos/illinoisDistrictsTrimmed.csv");

        System.out.println(Arrays.toString(graph.vertices.toArray()));
        System.out.println(graph.adjacencyStuff.toString());

        Split split = new Split(17, 310000);
        Queue queue = new ArrayBlockingQueue<Vertex>(10000);
        queue.add(graph.vertices.get(0));
        Split j = Algos.partitioner(graph, split, queue);
        System.out.println(j);
    }
}
