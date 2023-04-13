import java.util.List;

public class Node {

    private final List<Double> attributesColumn;
    private final String className;

    public Node(List<Double> attributesColumn, String className) {
        this.attributesColumn = attributesColumn;
        this.className = className;
    }

    public List<Double> getAttributesColumn() {
        return attributesColumn;
    }

    public String getClassName() {
        return className;
    }


    @Override
    public String toString() {
        return "Node{" +
                "attributesColumn=" + attributesColumn +
                ", className='" + className + '\'' +
                '}';
    }
}

