
/**
 *
 * @author dibop
 */
public class Partition {

    public int[] v;
    
    public Partition(int[] pv) {
        this.v = pv;
    }

    public double Q() {
        double rslt = 0.0;
        for (int i = 0; i < v.length; i++) {
            rslt += (1 / TP34.g.m) - (Math.pow(calcV(), 2.0) / (4 * Math.pow(TP34.g.m, 2.0)));
        }
        return rslt;
    }

    private double calcV() {
        double rslt = 0.0;
        for (int i = 0; i < v.length; i++) {
            rslt += TP34.g.V[v[i]].degre;
        }

        return rslt;
    }
    
    private double m(int i, int j) {
        double rslt = 0.0;
        
        
        
        return rslt;
    }

    /*public double Q() {
        double rslt = 0.0;
        for (int i : v) {
            rslt += (TP34.g.V[i].degre / TP34.g.m) - (Math.pow(calcV(), 2.0) / (4 * Math.pow(TP34.g.m, 2.0)));
        }

        return rslt;
    }

    private double calcV() {
       double rslt = 0.0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                if(i == j) {
                    rslt += TP34.g.V[i].degre;
                } else {
                    rslt += TP34.g.V[i].degre * TP34.g.V[i].degre;
                }
            }
        }

        return rslt;
    }*/
}
