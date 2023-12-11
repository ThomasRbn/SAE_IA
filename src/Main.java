import lib.MLP;
import lib.Sigmoide;
import lib.TransferFunction;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //On stocke le nombre de neurones par couche
        int[] layers = {2, 3, 1};
        //On stocke le learning rate
        double learningRate = 0.1;
        //On stocke la fonction de transfert
        TransferFunction transferFunction = new Sigmoide();
        //On stocke le nombre d'époques
        int epochs = 10000;

        //On crée le MLP
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        double[] output = {0};
        // table ET
        double[] input = {0,0};
        //Les types des sorties sont soit des doubles soit des tableaux de doubles
        int i = 0;
        //Boucle infinie
        while (i < epochs) {
            //On calcule la sortie du MLP
            output = mlp.execute(input);
            //On calcule l'erreur
            double error = output[0] - 0;
            //Fin boucle infinie
            i++;
        }
    }

}
