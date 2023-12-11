import lib.MLP;
import lib.Sigmoide;
import lib.TangenteHyperbolique;
import lib.TransferFunction;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runFunctions(new Sigmoide());
        runFunctions(new TangenteHyperbolique());
    }

    private static void runFunctions(TransferFunction transferFunction) {
        System.out.println("Tests avec fonction de transfert " + transferFunction.getClass().getName() + " :");
        // table ET
        double[][] inputsET = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] outputsET = {{0}, {0}, {0}, {1}};

        // table OU
        double[][] inputsOU = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] outputsOU = {{0}, {1}, {1}, {1}};

        // table XOR
        double[][] inputsXOR = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] outputsXOR = {{0}, {1}, {1}, {0}};

        run(inputsET, outputsET, transferFunction);
        run(inputsOU, outputsOU, transferFunction);
        run(inputsXOR, outputsXOR, transferFunction);
    }

    private static void run(double[][] inputs, double[][] outputs, TransferFunction transferFunction) {
        //On stocke le nombre de neurones par couche
        int[] layers = {2, 3, 1};
        //On stocke le learning rate
        double learningRate = 0.1;
        //On stocke la fonction de transfert
        //On stocke le nombre d'époques
        int epochs = 10000;

        //On crée le MLP
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        //Les types des sorties sont soit des doubles soit des tableaux de doubles
        int i = 0;
        //Boucle infinie
        while (i < epochs) {
            //On calcule la sortie du MLP
            mlp.execute(inputs[i % 4]);
            //On calcule l'erreur
            double error = mlp.backPropagate(inputs[i % 4], outputs[i % 4]);
            i++;
        }
        //On affiche les résultats
        for (i = 0; i < 4; i++) {
            System.out.println("Input : " + Math.round(inputs[i][0]) + " " + Math.round(inputs[i][1]));
            System.out.println("Output : " + Math.round(mlp.execute(inputs[i])[0]));
        }
        System.out.println("--------------------------------------------------");
    }

}
