import java.util.Objects;

public class Vertex {

    public int clinton;
    public int trump;
    public String name;
    public Vertex(String name, int clinton, int trump) {
        this.name = name;
        this.clinton = clinton;
        this.trump = trump;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    @Override
    public boolean equals(Object obj) {
        Vertex f = (Vertex) obj;
        if (name == f.name) {
            return true;
        }

        return false;
    }
    @Override
    public String toString() {
        return "(" + name + ", " + clinton + ", " + trump + ")";
    }
}
