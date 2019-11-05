package com.gri.tp2;

import java.util.Arrays;

/**
 *
 * @author dibop
 */
public class Sommet {

    int num, degre;
    int[] adj;

    public Sommet(int i) {
        this.num = i;
        this.degre = 0;
    }

    @Override
    public String toString() {
        return "(" + num + ", " + degre + ", " + Arrays.toString(adj) + ")";
    }

}
