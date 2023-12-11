package knn;

public abstract class AlgoClassification {

    private Donnees trainData;

    public AlgoClassification(Donnees trainData) {
        this.trainData = trainData;
    }

    public abstract int predireEtiquette(Imagette imagette);

    public Donnees getTrainData() {
        return trainData;
    }

    public void setTrainData(Donnees trainData) {
        this.trainData = trainData;
    }
}
