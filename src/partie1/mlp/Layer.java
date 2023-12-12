package partie1.mlp;

public class Layer {
    public Neuron[] neutrons;
    public int length;

    /**
     * Couche de Neurones
     *
     * @param l     Taille de la couche
     * @param prev  Taille de la couche précédente
     */
    public Layer(int l, int prev) {
        length = l;
        neutrons = new Neuron[l];

        for(int j = 0; j < length; j++)
            neutrons[j] = new Neuron(prev);
    }
}
