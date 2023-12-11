package knn;

import java.util.ArrayList;
import java.util.List;

public class Donnees {
    List<Imagette> imagettes;

    public Donnees() {
        this.imagettes = new ArrayList<>();
    }

    public List<Imagette> getImagettes() {
        return imagettes;
    }

    public void setImagettes(List<Imagette> imagettes) {
        this.imagettes = imagettes;
    }
}
