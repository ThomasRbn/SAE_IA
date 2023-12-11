package lib;

public class TangenteHyperbolique implements TransferFunction {
    /**
     * Function de transfert
     *
     * @param value entrée
     * @return sortie de la fonction sur l'entrée
     */
    public double evaluate(double value) {
        return Math.tanh(value);
    }

    /**
     * Dérivée de la fonction de tranfert
     *
     * @param value entrée
     * @return sortie de la fonction dérivée sur l'entrée
     */
    public double evaluateDer(double value) {
        return 1 - Math.pow(Math.tanh(value), 2);
    }
}
