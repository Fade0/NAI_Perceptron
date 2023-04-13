import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private List<Double> vectorW;
    private double thetaThreshold;
    private double alpha;

    public Perceptron(int vectorSize, double alpha) {
        this.alpha = alpha;
        this.vectorW = new ArrayList<>();
        for (int i = 0; i < vectorSize ; i++)
            //-5 to 5
            this.vectorW.add((Math.random()*10)-5);

        this.thetaThreshold = Math.random()*10-5;
    }

    public List<Double> getVectorW() {
        return vectorW;
    }


    public double getThetaThreshold() {
        return thetaThreshold;
    }

    public void setThetaThreshold(double thetaThreshold) {
        this.thetaThreshold = thetaThreshold;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setVectorW(List<Double> vectorW) {
        this.vectorW = vectorW;
    }

    public double getAlpha() {
        return alpha;
    }


    public void learn(Node node, int correctAnswer){
        int y = 0;
        double net = 0;
        // Calculate X * W
        for (int i = 0; i < node.getAttributesColumn().size() ; i++) {
            net += node.getAttributesColumn().get(i) * this.vectorW.get(i);
        }

        //net = d - theta
        if (net >= this.thetaThreshold){
            y = 1;
        }
        else{
            y = 0;
        }

        if (y != correctAnswer) {
            //Main vector
            List<Double> vectorWPrime = new ArrayList<>(this.vectorW);

            // W' = W + (Correct-Y) * Alpha * X
            for (int i = 0; i < node.getAttributesColumn().size(); i++)
                vectorWPrime.set(i, (this.vectorW.get(i) + ((correctAnswer - y) * alpha * node.getAttributesColumn().get(i))));

            this.vectorW = vectorWPrime;
            this.thetaThreshold = thetaThreshold + (correctAnswer - y) * alpha * -1;
        }
    }

    //Net
    public int evaluate(Node node){
        double net = 0;
        // Calculate X * W
        for (int i = 0; i < node.getAttributesColumn().size() ; i++)
            net += node.getAttributesColumn().get(i) * this.vectorW.get(i);

        if (net >= thetaThreshold) {
            return 1;
        }
        else{
            return 0;
        }

    }

    @Override
    public String toString() {
        return "Perceptron{" +
                "vectorW=" + vectorW +
                ", thetaThreshold=" + thetaThreshold +
                '}';
    }
}



