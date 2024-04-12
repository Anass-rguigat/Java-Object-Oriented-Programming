/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;

class Piece {
    private String nom;


    public Piece (String nom) {
        this.nom = nom;
    }
    public String getNom() { return this.nom; }
    public String toString() { return getNom(); }
}

class Simple extends Piece {
    public static final String DEFAULT_ORIENTATION = "aucune";
    private String orientation = DEFAULT_ORIENTATION;

    public Simple(String nom, String orientation) {
        super(nom);
        this.orientation = orientation;
    }
    public Simple(String nom) {
        super(nom);
    }

    public String getOrientation() { return this.orientation; }
    public String toString() {
        String str = this.getNom();
        if (this.orientation != DEFAULT_ORIENTATION) {
            str += " " + this.getOrientation();
        }
        return str;
    }
}

class Composee extends Piece {
    private ArrayList<Piece> pieces;
    private final int n;

    public Composee(String nom, int n) {
        super(nom);
        this.pieces = new ArrayList<Piece>();
        this.n = n;
    }
    public int taille() { return this.pieces.size(); }
    public int tailleMax() { return this.n; }
    public void construire(Piece p) {
        if (this.taille() >= this.tailleMax() ) {
            System.out.println("Construction impossible");
        } else {
            this.pieces.add(p);
        }

    }
    public String toString() {
        String str = this.getNom();
        for (int i = 0; i < this.pieces.size(); i++) {
            if (i == 0) {
                str += " (";
            }
            str += this.pieces.get(i).toString();
            if (i == this.pieces.size() - 1) {
                str += ")";
            } else {
                str += ",";
            }
        }
        return str;
    }

}

class Composant {
    private Piece piece;
    private final int n;

    public Composant(Piece piece, int n) {
        this.piece = piece;
        this.n = n;
    }

    public Piece getPiece() { return this.piece; }
    public int getQuantite() { return this.n; }
}

class Construction {
    private ArrayList<Composant> composants;
    private int size;

    public Construction(int size) {
        this.size = size;
        this.composants = new ArrayList<Composant>();
    }
    public int taille() { return this.composants.size(); }
    public int tailleMax() { return this.size; }
    public void ajouterComposant(Piece p, int n) {
        if (this.taille() >= this.tailleMax()) {
            System.out.println("Ajout de composant impossible");
        } else {
            this.composants.add(new Composant(p, n));
        }
    }
    public String toString() {
        String str = "";
        for (int i = 0; i < this.composants.size(); i++) {
            str += String.format("%s (quantite %d)", this.composants.get(i).getPiece(), this.composants.get(i).getQuantite());
            if (i < this.composants.size() - 1) {
                str += "\n";
            }
        }
        return str;
    }
}

/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
/*******************************************
 * Ce qui suit est propose' pour vous aider
 * dans vos tests, mais votre programme sera
 * teste' avec d'autres donnees.
 *******************************************/

class Lego {

    public static void main(String[] args) {
        // declare un jeu de construction de 10 pieces
        Construction maison = new Construction(10);

        // ce jeu a pour premier composant: 59 briques standard
        // une brique standard a par defaut "aucune" comme orientation
        maison.ajouterComposant(new Simple("brique standard"), 59);

        // declare une piece dont le nom est "porte", composee de 2 autres pieces
        Composee porte = new Composee("porte", 2);

        // cette piece composee est constituee de deux pieces simples:
        // un cadran de porte orient'e a gauche
        // un battant orient'e a gauche
        porte.construire(new Simple("cadran porte", "gauche"));
        porte.construire(new Simple("battant", "gauche"));

        // le jeu a pour second composant: 1 porte
        maison.ajouterComposant(porte, 1);

        // déclare une piece composee de 3 autres pieces dont le nom est "fenetre"
        Composee fenetre = new Composee("fenetre", 3);

        // cette piece composee est constitu'ee des trois pieces simples:
        // un cadran de fenetre (aucune orientation)
        // un volet orient'e a gauche
        // un volet orient'e a droite
        fenetre.construire(new Simple("cadran fenetre"));
        fenetre.construire(new Simple("volet", "gauche"));
        fenetre.construire(new Simple("volet", "droit"));

        // le jeu a pour troisieme composant: 2 fenetres
        maison.ajouterComposant(fenetre, 2);

        // affiche tous les composants composants (nom de la piece et quantit'e)
        // pour les pieces compos'ees : affiche aussi chaque piece les constituant
        System.out.println("Affichage du jeu de construction : ");
        System.out.println(maison);
    }
}
