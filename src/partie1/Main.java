package partie1;

import partie1.knn.*;
import partie1.mlp.*;

public class Main {

    public static void main(String[] args) {
        runFunctions(new Sigmoide());
        runFunctions(new TangenteHyperbolique());
    }

    private static void runFunctions(TransferFunction transferFunction) {
        System.out.println("Tests avec fonction de transfert " + transferFunction.getClass().getName() + " :");
        // Partie des Images
        Donnees trainingData = Imagette.loadImagettes(2000, "assets/train-images-idx3-ubyte", "assets/train-labels-idx1-ubyte");
        Donnees sampleData = Imagette.loadImagettes(300, "assets/t10k-images-idx3-ubyte", "assets/t10k-labels-idx1-ubyte");
        runKNN(trainingData, sampleData);

        int[] layers = {16 * 16, 16 * 16, 10};
        runMLP(layers, trainingData, sampleData, transferFunction);
    }

    private static void runKNN(Donnees trainingData, Donnees sampleData) {
        Knn knn = new Knn(trainingData, 10);
        Statistiques statsKnn = new Statistiques(knn, sampleData, trainingData);
        statsKnn.makeStats();
    }

    private static void runMLP(int[] layers, Donnees trainingData, Donnees sampleData, TransferFunction transferFunction) {
        // Ajout des données essentielles
        double learningRate = 0.05;
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        // Longueur des données
        int trainingDataLength = trainingData.getImagettes().size();
        int sampleDataLength = sampleData.getImagettes().size();

        // Tableau à 2 dimensions pour les sorties attendues
        double[][] outputExpectedTrain = new double[trainingDataLength][10];
        // Remplissage du tableau avec les étiquettes
        // 0 -> 1 0 0 0 0 0 0 0 0 0
        // 1 -> 0 1 0 0 0 0 0 0 0 0
        // etc
        for (int j = 0; j < trainingDataLength; j++) {
            Imagette curr = trainingData.getImagettes().get(j);
            for (int k = 0; k < 10; k++) {
                if (transferFunction instanceof TangenteHyperbolique)
                    outputExpectedTrain[j][k] = -1;
                else
                    outputExpectedTrain[j][k] = 0;
            }
            outputExpectedTrain[j][curr.getEtiquette()] = 1;
        }

        // Liste des entrées courantes (tous les pixels de l'imagette)
        double[] input = new double[16 * 16];
        // Tant que la liste n'a pas été déroulée jusqu'au bout
        double erreur = 0;
        double epoch = 10;
        for (int e = 0; e < epoch; e++) {
            for (int i = 0; i < trainingDataLength; i++) {
                // Boucle pour remplir la liste avec tous les pixels de l'imagette
                remplissageInput(trainingData, input, i);
                // On fait une rétropropagation avec les réponses attendues
                erreur = mlp.backPropagate(input, outputExpectedTrain[i]);
                i++;
            }
        }
        System.out.println("Erreur : " + erreur);

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
            double maxOutput = 0;
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
