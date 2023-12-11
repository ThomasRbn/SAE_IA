import java.util.ArrayList;
import java.util.HashMap;

class Resultat {
    int etiquette;
    long distance;

    public Resultat(int etiquette, long distance) {
        this.etiquette = etiquette;
        this.distance = distance;
    }
}

public class Knn extends AlgoClassification {

    int k;

    public Knn(Donnees trainData, int k) {
        super(trainData);
        this.k = k;
    }

    @Override
    public int predireEtiquette(Imagette imagette) {
        //On crée un tableau de taille k
        ArrayList<Object[]> tableau = new ArrayList<>();
        //On initialise la distance la plus grande du tableau a MAX_Value
        long plusGrandeDistance = Long.MAX_VALUE;
        //Pour chaque image d'entrainement
        for (Imagette img : this.getTrainData().getImagettes()) {
            //On calcule la distance entre l'image d'entrainement et l'image de test
            long distance = calculateDistance(img, imagette);
            //Si la distance est plus petite que la plus grande distance du tableau
            if (distance < plusGrandeDistance) {
                //On ajoute l'image d'entrainement et sa distance dans le tableau
                tableau.add(new Object[]{img, distance});
                //On trie le tableau par ordre croissant de distance
                tableau.sort((o1, o2) -> {
                    long distance1 = (long) o1[1];
                    long distance2 = (long) o2[1];
                    return (int) (distance1 - distance2);
                });
                //Si le tableau est plus grand que k
                if (tableau.size() > k) {
                    //On supprime le dernier élément du tableau
                    tableau.remove(tableau.size() - 1);
                    //On met à jour la plus grande distance du tableau
                    plusGrandeDistance = (long) tableau.get(tableau.size() - 1)[1];
                }
            }
        }
        //Fin pour

        HashMap<Integer, Integer> map = new HashMap<>();
        //Pour chaque élément du tableau
        for (int i = 0; i < 10; i++) {
            map.put(i, 0);
        }
        for (Object[] img : tableau) {
            //On récupère l'étiquette de l'image
            int etiquette = ((Imagette) img[0]).etiquette;
            //On incrémente le nombre d'occurence de l'étiquette dans la map
            map.put(etiquette, map.get(etiquette) + 1);
        }
        //Fin pour
        //On récupère l'étiquette qui a le plus d'occurence
        int etiquette = 0;
        int max = 0;
        for (int i = 0; i < 10; i++) {
            if (map.get(i) > max) {
                etiquette = i;
                max = map.get(i);
            }
        }
        //On retourne l'étiquette
        return etiquette;
    }

    private long calculateDistance(Imagette img1, Imagette img2) {
        long difference = 0;
        for (int col = 0; col < img1.gris.length; col++) {
            for (int ligne = 0; ligne < img1.gris[0].length; ligne++) {
                int niveauGrisImg1 = img1.gris[col][ligne];
                int niveauGrisImg2 = img2.gris[col][ligne];
//                difference += (Math.abs(niveauGrisImg1 - niveauGrisImg2));
                difference += (Math.pow(niveauGrisImg1 - niveauGrisImg2, 2));
            }
        }
        return difference;
    }
}
