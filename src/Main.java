import knn.*;
import mlp.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runFunctions(new Sigmoide());
        runFunctions(new TangenteHyperbolique());
    }

    private static void runFunctions(TransferFunction transferFunction) {
        System.out.println("Tests avec fonction de transfert " + transferFunction.getClass().getName() + " :");
        // Partie des Images
        Donnees trainingData = Imagette.loadImagettes(1000, "assets/train-images-idx3-ubyte", "assets/train-labels-idx1-ubyte");
        Donnees sampleData = Imagette.loadImagettes(100, "assets/t10k-images-idx3-ubyte", "assets/t10k-labels-idx1-ubyte");
        PlusProche algo = new PlusProche(trainingData);
        Knn knn = new Knn(trainingData, 10);

        Statistiques stats = new Statistiques(algo, sampleData, trainingData);
        Statistiques statsKnn = new Statistiques(knn, sampleData, trainingData);
        stats.makeStats();
        statsKnn.makeStats();

        int[] layers = {16 * 16, 10, 10, 20, 10};

        run(layers, trainingData, sampleData, transferFunction);
    }

    private static void run(int[] layers, Donnees trainingData, Donnees sampleData, TransferFunction transferFunction) {
        double learningRate = 0.1;
        int epochs = 10000;
        MLP mlp = new MLP(layers, learningRate, transferFunction);
        int dataLength = trainingData.getImagettes().size();
        double[][] outputs = new double[dataLength][10];

        int i = 0;
        double[][] outputExpected = new double[dataLength][10];
        for (int j = 0; j < dataLength; j++) {
            Imagette curr = trainingData.getImagettes().get(j);
            outputExpected[j][curr.getEtiquette()] = 1;
        }
        while (i < dataLength) {
            double[] input = new double[16 * 16];
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    input[j * 16 + k] = trainingData.getImagettes().get(i).getGris(j, k);
                }
            }
            double[] outputMLP = mlp.execute(input);
            outputs[i] = outputMLP;
            mlp.backPropagate(input, outputExpected[i]);
            System.out.println("Epoch " + i + " : " + Arrays.toString(outputMLP) + " expected : " + Arrays.toString(outputExpected[i]));
            i++;
        }

        System.out.println("--------------------------------------------------");
    }

}
