package partie1.knn;

public class Statistiques {
    AlgoClassification algo;
    Donnees dataTest;
    Donnees dataTrain;

    public Statistiques(AlgoClassification algo, Donnees dataTest, Donnees dataTrain) {
        this.algo = algo;
        this.dataTest = dataTest;
        this.dataTrain = dataTrain;
    }

    public void makeStats(){
        int rightLabels = 0;

        for (int i = 0; i < dataTest.getImagettes().size(); i++) {
            Imagette imTest = dataTest.getImagettes().get(i);
            int predictedLabel = algo.predireEtiquette(imTest);
            if (checkCorrectLabel(imTest, predictedLabel)) {
                rightLabels++;
            }
        }

        System.out.println("Taux de bonne réponses : " + rightLabels + "/" + dataTest.getImagettes().size());
    }

    public boolean checkCorrectLabel(Imagette imTest, int predictedLabel) {
        return imTest.etiquette == predictedLabel;
    }
}
