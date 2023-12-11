package lib;

public class Sigmoide implements TransferFunction{

    /**
     * Function de transfert
     * @param value entrée
     * @return sortie de la fonction sur l'entrée
     */
    public double evaluate(double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }

    /**
     * Dérivée de la fonction de tranfert
     * @param value entrée
     * @return sortie de la fonction dérivée sur l'entrée
     */
    public double evaluateDer(double value) {
        return evaluate(value) - (evaluate(value) * evaluate(value));
    }
}
