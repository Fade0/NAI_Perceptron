import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {

        public static void main(String[] args) throws IOException{

            System.out.println("Perceptron implementation");

            List<Node> nodeTrainList;
            List<Node> nodeTestList;
            String pathTrainingFile;
            //alpha
            double a = 1;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Insert value for Alfa");
            a = Double.parseDouble(bufferedReader.readLine());

            //Loading TrainingData
            System.out.println("Insert path to training file");
            pathTrainingFile = bufferedReader.readLine();
            nodeTrainList = getNodeList(pathTrainingFile);

            //Loading TestData
            String userChoice = "";
            System.out.println("Do you want to load test data from file or insert vector \ntype Vector or file name ");
            userChoice = bufferedReader.readLine();
            System.out.println(userChoice);

            //numberOfClasses
            int n = 0;

            HashMap<String,Integer> answerMap = new HashMap<>();
            for (Node node : nodeTrainList) {
                if (!answerMap.containsKey(node.getClassName()))
                    answerMap.put(node.getClassName(),n++);

                if (answerMap.size()==2)
                    break;
            }

            Collections.shuffle(nodeTrainList);

            //Creating perceptron and learning it
            Perceptron perceptron = new Perceptron(nodeTrainList.get(0).getAttributesColumn().size(),a);
            for (Node value : nodeTrainList)
                perceptron.learn(value, answerMap.get(value.getClassName()));

            //UserVector
            if (userChoice.trim().equals("Vector")) {

                    System.out.println("Insert values for vector split them with \";\"] \n " +
                            "Remember that if you insert double you should use \".\" ");

                    String line = bufferedReader.readLine();
                    line += ";[Labeless]";
                    List<Node> nodeSet = new ArrayList<>();

                    //; because of csv file formatting
                    String[] tmp = line.split(";");
                    System.out.println(tmp);
                    List<Double> attributesColumn = new ArrayList<>();

                    for (int i = 0; i < tmp.length - 1; i++) {
                        attributesColumn.add(Double.parseDouble(tmp[i]));
                    }
                    nodeSet.add(new Node(attributesColumn, tmp[tmp.length - 1]));
                    nodeTestList = nodeSet;

                    for (Node node : nodeTestList) {
                        int y = perceptron.evaluate(node);
                        for (String key : answerMap.keySet()) {
                            if (answerMap.get(key) == y) {
                                System.out.println("Answer: " + key + "\n");
                            }
                        }

                    }

            }

            //Training File
            else {
                nodeTestList = getNodeList(userChoice);
                n = 0;
                int correctAnswerFirst = 0;
                int correctAnswerSecond = 0;
                int appearancesFirst = 0;
                int appearancesSecond = 0;


                //Size is 2 cause of number of classes in csv
                String[] keyArray = new String[2];
                for (String key : answerMap.keySet())
                    keyArray[n++] = key;



                for (Node node : nodeTestList) {
                    int y = perceptron.evaluate(node);

                    if (answerMap.get(node.getClassName()) == 0) {
                        appearancesFirst++;
                    }

                    if (answerMap.get(node.getClassName()) == 1) {
                        appearancesSecond++;
                    }

                    if (answerMap.get(keyArray[0]) == y && y == answerMap.get(node.getClassName())) {
                        correctAnswerFirst++;
                    }

                    if (answerMap.get(keyArray[1]) == y && y == answerMap.get(node.getClassName())) {
                        correctAnswerSecond++;
                    }
                }
                double firstAcc = ((double) correctAnswerFirst / appearancesFirst) * 100;
                double secondAcc = ((double) correctAnswerSecond / appearancesSecond) * 100;
                double totalAcc = (double) (correctAnswerFirst + correctAnswerSecond) / nodeTestList.size() * 100;

                System.out.println("Accuracy for " + keyArray[0] + ": " + firstAcc + "%");
                System.out.println("Accuracy for " + keyArray[1] + ": " + secondAcc + "%");
                System.out.println("Total accuracy: " + totalAcc + "%");
                System.out.println("Final Vector: " + perceptron.getVectorW() + " Theta threshold: " + perceptron.getThetaThreshold());
            }
        }

    public static List<Node> getNodeList(String fileAddress) throws IOException {
        String line;
        List<Node> nodeSet = new ArrayList<>();

        FileReader fileReader = new FileReader(fileAddress);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine())!=null && (!line.equals(""))){
            String [] tmp = line.split(";");
            List<Double> attributesColumn = new ArrayList<>();

            for (int i = 0; i < tmp.length-1 ; i++) {
                attributesColumn.add(Double.parseDouble(tmp[i]));
            }

            nodeSet.add(new Node(attributesColumn,tmp[tmp.length-1]));
        }

        return nodeSet;
    }
}
