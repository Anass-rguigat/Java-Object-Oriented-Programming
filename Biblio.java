import java.util.ArrayList;

class Auteur {

    /*******************************************
     * Completez le programme a partir d'ici.
     *******************************************/
	// Completer la classe Auteur ici
    private String nom;
    private boolean prix;

    public Auteur(String n, boolean p)
    {
        nom = n;
        prix = p;
    }

    public String getNom() { return nom; }
    public boolean getPrix() { return prix; }
}

class Oeuvre
{
 	// Completer la classe Oeuvre ici
    private String titre;
    private final Auteur auteur;
    private String langue;

    public Oeuvre(String t, final Auteur a, String l)
    {
        titre = t;
        auteur = a;
        langue = l;
    }
    public Oeuvre(String t, final Auteur a)
    {
        this(t, a, "francais");
    }

    public String getTitre() { return titre; }
    public final Auteur getAuteur() { return auteur; }
    public String getLangue() { return langue; }
    public String toString()
    {
        String s = String.format("%s, %s, en %s", getTitre(), getAuteur().getNom(), getLangue());
        return s;
    }
    public void afficher() { System.out.println(this); }
}

class Exemplaire {
    private final Oeuvre oeuvre;

    public Exemplaire(final Oeuvre o)
    {
        oeuvre = o;
        System.out.print("Nouvel exemplaire -> ");
        System.out.println(oeuvre);
    }
    public Exemplaire(Exemplaire e)
    {
        oeuvre = e.oeuvre;
        System.out.print("Copie d'un exemplaire de -> ");
        System.out.println(oeuvre);
    }

    public final Oeuvre getOeuvre() { return oeuvre; }
    public String toString()
    {
        String s = String.format("Un exemplaire de " + getOeuvre());
        return s;
    }
    public void afficher() { System.out.println(this); }
}
// completer les autres classes ici

class Bibliotheque {
    private String nom;
    private ArrayList<Exemplaire> exemplaires;

    public Bibliotheque(String n)
    {
        nom = n;
        exemplaires = new ArrayList<Exemplaire>();
        System.out.format("La bibliothèque %s est ouverte !", nom);
        System.out.println();
    }
    public String getNom() { return nom; }
    public int getNbExemplaires() { return exemplaires.size(); }
    public void stocker(final Oeuvre o, int n)
    {
        for (int i = 0; i < n; i++) {
            exemplaires.add(new Exemplaire(o));
        }
    }
    public void stocker(final Oeuvre o)
    {
        stocker(o, 1);
    }

    public ArrayList<Exemplaire> listerExemplaires(String langue)
    {
        ArrayList<Exemplaire> res = new ArrayList<Exemplaire>();

        for (Exemplaire e : exemplaires) {
            if (langue.equals("") || e.getOeuvre().getLangue().equals(langue)) {
                res.add(e);
            }
        }
        return res;
    }
    public ArrayList<Exemplaire> listerExemplaires()
    {
        return listerExemplaires("");
    }

    public int compterExemplaires(final Oeuvre oeuvre)
    {
        int res = 0;
        for (Exemplaire e : exemplaires) {
            if (e.getOeuvre() == oeuvre) {
                res++;
            }
        }
        return res;
    }

    public void afficherAuteur(boolean prix)
    {   
        for (Exemplaire e : exemplaires) {
            Auteur a = e.getOeuvre().getAuteur();
            if (prix == a.getPrix()) {
                System.out.println(a.getNom());
            }
        }
    }
    public void afficherAuteur()
    {   
        afficherAuteur(true);
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

public class Biblio {

    public static void afficherExemplaires(ArrayList<Exemplaire> exemplaires) {
        for (Exemplaire exemplaire : exemplaires) {
            System.out.print("\t");
            exemplaire.afficher();
        }
    }

    public static void main(String[] args) {
        // create and store all the exemplaries
        Auteur a1 = new Auteur("Victor Hugo", false);
        Auteur a2 = new Auteur("Alexandre Dumas", false);
        Auteur a3 = new Auteur("Raymond Queneau", true);

        Oeuvre o1 = new Oeuvre("Les Miserables", a1, "francais");
        Oeuvre o2 = new Oeuvre("L\'Homme qui rit", a1, "francais");
        Oeuvre o3 = new Oeuvre("Le Comte de Monte-Cristo", a2, "francais");
        Oeuvre o4 = new Oeuvre("Zazie dans le metro", a3, "francais");
        Oeuvre o5 = new Oeuvre("The count of Monte-Cristo", a2, "anglais");

        Bibliotheque biblio = new Bibliotheque("municipale");
        biblio.stocker(o1, 2);
        biblio.stocker(o2);
        biblio.stocker(o3, 3);
        biblio.stocker(o4);
        biblio.stocker(o5);

        // ...
        System.out.println("La bibliotheque " + biblio.getNom() + " offre ");
        afficherExemplaires(biblio.listerExemplaires());
        String langue = "anglais";
        System.out.println("Les exemplaires en " + langue + " sont  ");
        afficherExemplaires(biblio.listerExemplaires(langue));
        System.out.println("Les auteurs a succes sont  ");
        biblio.afficherAuteur();
        System.out.print("Il y a " + biblio.compterExemplaires(o3) + " exemplaires");
        System.out.println(" de  " + o3.getTitre());
    }
}

