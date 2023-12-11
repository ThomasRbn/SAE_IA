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

        int[] layers = {16 * 16, 8, 4, 5, 10};

        run(layers, trainingData, sampleData, transferFunction);
    }

    private static void run(int[] layers, Donnees trainingData, Donnees sampleData, TransferFunction transferFunction) {
        double learningRate = 0.1;
        int epochs = 10000;
        double[][] output = new double[4][1];
        MLP mlp = new MLP(layers, learningRate, transferFunction);
        int dataLength = trainingData.getImagettes().size();

        int i = 0;
        while (i < dataLength) {
            double[] input = new double[16 * 16];
            double[] outputExpected = new double[dataLength];
            for (int j = 0; j < dataLength; j++) {
                outputExpected[j] = trainingData.getImagettes().get(j).getEtiquette();
            }
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    input[j * 16 + k] = trainingData.getImagettes().get(i).getGris(j, k);
                }
                double[] outputMLP = mlp.execute(input);
                mlp.backPropagate(outputExpected, outputMLP);
            }

            i++;
        }
        System.out.println("--------------------------------------------------");
    }

}
