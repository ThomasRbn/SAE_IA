import knn.*;
import mlp.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runFunctions(new Sigmoide());
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

        int[] layers = {16 * 16, 50, 40, 30, 20, 10, 10, 10, 10, 10, 10};

        run(layers, trainingData, sampleData, transferFunction);
    }

    private static void run(int[] layers, Donnees trainingData, Donnees sampleData, TransferFunction transferFunction) {
        // Ajout des données essentielles
        double learningRate = 0.1;
        int epochs = 10000;
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        // Longueur des données
        int dataLength = trainingData.getImagettes().size();

        int i = 0;
        // Tableau à 2 dimensions pour les sorties attendues
        double[][] outputExpected = new double[dataLength][10];

        // Remplissage du tableau avec les étiquettes
        // 0 -> 1 0 0 0 0 0 0 0 0 0
        // 1 -> 0 1 0 0 0 0 0 0 0 0
        // etc
        for (int j = 0; j < dataLength; j++) {
            Imagette curr = trainingData.getImagettes().get(j);
            outputExpected[j][curr.getEtiquette()] = 1;
        }

        // Liste des entrées courantes (tous les pixels de l'imagette)
        double[] input = new double[16 * 16];
        // Tant que la liste n'a pas été déroulée jusqu'au bout
        while (i < dataLength) {
            // Boucle pour remplir la liste avec tous les pixels de l'imagette
            remplissageInput(trainingData, input, i);
            // On fait une rétropropagation avec les réponses attendues
            mlp.backPropagate(input, outputExpected[i]);
            i++;
        }

        // Test de l'entrainement
        int j = 0;
        int good = 0;
        while (j < sampleData.getImagettes().size()) {
            input = new double[16 * 16];
            // On remplit la liste avec les pixels de l'imagette
            remplissageInput(sampleData, input, j);
            // On récupère la sortie du MLP avec la méthode execute
            double[] outputMLP = mlp.execute(input);

            // On regarde la valeur max de la sortie du tableau
            // Exemple : [0.1, 0.2, 0.3, 0.4, 0.5] -> 0.5
            int max = 0;
            for (int k = 0; k < outputMLP.length; k++) {
                if (outputMLP[k] > outputMLP[max]) {
                    max = k;
                }
            }
            // Si la valeur max est la même que l'étiquette de l'imagette,
            // on incrémente le nombre de réussites
            if (max == sampleData.getImagettes().get(j).getEtiquette()) {
                good++;
            }
            j++;
        }
        // affichage des résultats
        System.out.println("Pourcentage de réussite : " + (double) good / (double) sampleData.getImagettes().size() * 100 + "%");

        System.out.println("--------------------------------------------------");
    }

    private static void remplissageInput(Donnees trainingData, double[] input, int i) {
        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                input[j * 16 + k] = trainingData.getImagettes().get(i).getGris(j, k);
            }
        }
    }

}
