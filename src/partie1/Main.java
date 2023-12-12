package partie1;

import partie1.knn.*;
import partie1.mlp.*;

public class Main {

    public static int SIZE = 28;

    public static void main(String[] args) {
        runFunctions(new TangenteHyperbolique());
        runFunctions(new Sigmoide());
    }

    private static void runFunctions(TransferFunction transferFunction) {
        // Partie des Images
        Donnees trainingData = Imagette.loadImagettes(2000, "assets/train-images-idx3-ubyte", "assets/train-labels-idx1-ubyte");
        Donnees sampleData = Imagette.loadImagettes(300, "assets/t10k-images-idx3-ubyte", "assets/t10k-labels-idx1-ubyte");

        long startTime = System.currentTimeMillis();
        runKNN(trainingData, sampleData);
        long endTime = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (endTime - startTime) + "ms");

        int[] layers = {28*28, 128, 10};
        startTime = System.currentTimeMillis();
        runMLP(layers, trainingData, sampleData, transferFunction, 0.05);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + "ms");

        /*for (int i = 1; i <= 100; i++) {

            // Nombre de couches
            *//*layers = new int[i+2];
            layers[0] = 28*28;
            for (int j = 1; j < layers.length-1; j++) {
                layers[j] = 128;
            }
            layers[layers.length-1] = 10;*//*

            // Nombre de neurones couche cachée
            *//*layers[1] = i;*//*

            startTime = System.currentTimeMillis();
            runMLP(layers, trainingData, sampleData, transferFunction, 0.05);
            endTime = System.currentTimeMillis();
            System.out.println(" " + (endTime - startTime) + "ms");
        }*/
    }

    private static void runKNN(Donnees trainingData, Donnees sampleData) {
        Knn knn = new Knn(trainingData, 10);
        Statistiques statsKnn = new Statistiques(knn, sampleData, trainingData);
        statsKnn.makeStats();
    }

    private static void runMLP(int[] layers, Donnees trainingData, Donnees sampleData, TransferFunction transferFunction, double learningRate) {
        // Ajout des données essentielles
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
        double[] input = new double[SIZE * SIZE];
        // Tant que la liste n'a pas été déroulée jusqu'au bout
        double erreur = 0;
        double epoch = 5;
        int good = 0;
        for (int e = 0; e < epoch; e++) {
            for (int i = 0; i < trainingDataLength; i++) {
                // Boucle pour remplir la liste avec tous les pixels de l'imagette
                remplissageInput(trainingData, input, i);
                // On fait une rétropropagation avec les réponses attendues
                erreur = mlp.backPropagate(input, outputExpectedTrain[i]);
                i++;
            }
            //System.out.println("Erreur : " + erreur);

            // Test de l'entrainement
            int j = 0;
            good = 0;
            while (j < sampleDataLength) {
                input = new double[SIZE * SIZE];
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

        }
        System.out.print("Learning rate : "+ learningRate +" Pourcentage de réussite : " + (double) good / (double) sampleData.getImagettes().size() * 100 + "% ");
    }

    private static void remplissageInput(Donnees trainingData, double[] input, int i) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                input[j * SIZE + k] = trainingData.getImagettes().get(i).getGris(j, k);
            }
        }
    }

}
