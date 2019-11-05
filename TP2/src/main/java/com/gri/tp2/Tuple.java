package com.gri.tp2;

import java.util.Objects;

/**
 *
 * @author Pierre Dibo
 */
public class Tuple {

    public Sommet x, y;

    Tuple(Sommet x, Sommet y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.x.num);
        hash = 61 * hash + Objects.hashCode(this.y.num);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple other = (Tuple) obj;
        if (!Objects.equals(this.x.num, other.x.num) && !Objects.equals(this.x.num, other.y.num)) {
            return false;
        }
        if (!Objects.equals(this.y.num, other.y.num) && !Objects.equals(this.y.num, other.x.num)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x.num + ", " + y.num + ")";
    }

}
