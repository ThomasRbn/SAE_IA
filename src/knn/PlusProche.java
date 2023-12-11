public class PlusProche extends AlgoClassification {

    public PlusProche(Donnees dataEntrainement) {
        super(dataEntrainement);
    }

    @Override
    public int predireEtiquette(Imagette img) {
        int bestEtiquette = -1;
        long difference = -1;
        for(Imagette currImg : this.getTrainData().getImagettes()){
            // Calcul de la distance pour une imagette
            long currDiffernence = calculateDistance(img, currImg);
            // Si la diff courante est moins élevée que l'autre ou que difference est a -1
            if(currDiffernence < difference || difference == -1){
                difference = currDiffernence;
                bestEtiquette = currImg.etiquette;
            }
        }
        return bestEtiquette;
    }

    private long calculateDistance(Imagette img1, Imagette img2) {
        long difference = 0;
        for(int col = 0; col < img1.gris.length; col++){
            for(int ligne = 0; ligne < img1.gris[0].length; ligne++){
                int niveauGrisImg1 = img1.gris[col][ligne];
                int niveauGrisImg2 = img2.gris[col][ligne];
                difference += (Math.abs(niveauGrisImg1 - niveauGrisImg2));
            }
        }
        return difference;
    }
}