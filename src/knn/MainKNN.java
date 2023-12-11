package knn;

public class MainKNN {

    public static void main(String[] args) {
        Donnees trainingData = Imagette.loadImagettes(1000, "assets/train-images-idx3-ubyte", "assets/train-labels-idx1-ubyte");
        Donnees sampleData = Imagette.loadImagettes(100, "assets/t10k-images-idx3-ubyte", "assets/t10k-labels-idx1-ubyte");
        PlusProche algo = new PlusProche(trainingData);
        Knn knn = new Knn(trainingData, 10);

        Statistiques stats = new Statistiques(algo, sampleData, trainingData);
        Statistiques statsKnn = new Statistiques(knn, sampleData, trainingData);
        stats.makeStats();
        statsKnn.makeStats();
    }
}
