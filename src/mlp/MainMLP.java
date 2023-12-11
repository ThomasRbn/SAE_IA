package mlp;

import java.util.Arrays;

public class MainMLP {

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

        // table random
        double[][] inputsRandom = {{0,0}, {0,1}, {1,0}, {1,1}};
        double[][] outputsRandom = {{0}, {1}, {0}, {1}};

        run(inputsET, outputsET, transferFunction);
        run(inputsOU, outputsOU, transferFunction);
        run(inputsXOR, outputsXOR, transferFunction);
        run(inputsRandom, outputsRandom, transferFunction);
    }

    private static void run(double[][] inputs, double[][] outputs, TransferFunction transferFunction) {
        if (transferFunction instanceof TangenteHyperbolique){
            for(int i = 0; i < inputs.length; i++){
                for(int j = 0; j < inputs[i].length; j++){
                    if(inputs[i][j] == 0)
                        inputs[i][j] = -1;
                }
            }
            for(int i = 0; i < outputs.length; i++){
                for(int j = 0; j < outputs[i].length; j++){
                    if(outputs[i][j] == 0)
                        outputs[i][j] = -1;
                }
            }
        }
        //On stocke le nombre de neurones par couche
        int[] layers = {2, 3, 1};
        //On stocke le learning rate
        double learningRate = 0.1;
        //On stocke la fonction de transfert
        //On stocke le nombre d'époques
        int epochs = 10000;
        double[][] output = new double[4][1];
        //On crée le MLP
        MLP mlp = new MLP(layers, learningRate, transferFunction);

        //Les types des sorties sont soit des doubles soit des tableaux de doubles
        int i = 0;
        //Boucle infinie
        while (i < epochs) {
            //On calcule la sortie du MLP
            output[i % 4] = mlp.execute(inputs[i % 4]);
            //On calcule l'erreur
            double error = mlp.backPropagate(inputs[i % 4], outputs[i % 4]);
            i++;
        }
        //On affiche les résultats
        for (i = 0; i < 4; i++) {
            System.out.println("Output : " + outputs[i][0] + " | " + Arrays.toString(output[i]));
        }
        System.out.println("--------------------------------------------------");
    }

}
