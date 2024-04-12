import java.sql.PseudoColumnUsage;

/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
class Patient {
    private double masse;
    private double hauteur;

    public void init(double poids, double taille) {
        if (poids > 0 && taille > 0) {
            masse = poids;
            hauteur = taille;
        } else {
            masse = 0;
            hauteur = 0;
        }
    }

    public double poids() {
        return masse;
    }

    public double taille() {
        return hauteur;
    }
    
    public void afficher() {
        System.out.format("Patient : %.1f kg pour %.1f m", masse, hauteur);
        System.out.println();
    }

    public double imc() {
        if (hauteur == 0) {
            return 0;
        } else {
            return masse / Math.pow(hauteur, 2);
        }
    }
}

/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Imc {
    public static void main(String[] args) {

        Patient quidam = new Patient();
        quidam.init(74.5, 1.75);
        quidam.afficher();
        System.out.println("IMC : " + quidam.imc());
        quidam.init( -2.0, 4.5);
        quidam.afficher();
    }
}
