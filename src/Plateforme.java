public class Plateforme {
    private Compte[] comptes = new Compte[Parametres.CAPACITE];
    private int nombre = 0;
    private String[] journal = new String[100];
    private int nbLignes = 0;
    private long totalFrais = 0;

    public boolean ajouter(Compte c) {
        if (c == null || nombre == comptes.length || rechercher(c.getNumero()) != null) {
            return false;
        }
        comptes[nombre] = c;
        nombre++;
        return true;
    }

    public Compte rechercher(String numero) {
        if (numero == null) {
            return null;
        }
        for (int i = 0; i < nombre; i++) {
            if (comptes[i].getNumero().equals(numero)) {
                return comptes[i];
            }
        }
        return null;
    }

    public int getNombre() { return nombre; }

    public long getTotalFrais() { return totalFrais; }

    public boolean transferer(String de, String vers, long montant) {
        if (montant <= 0 || de == null || de.equals(vers)) {
            return false;
        }
        Compte source = rechercher(de);
        Compte dest = rechercher(vers);
        if (source == null || dest == null) {
            return false;
        }
        long f = source.frais(montant);
        if (!source.retirer(montant)) {
            return false;
        }
        dest.deposer(montant);
        totalFrais += f;
        if (nbLignes < journal.length) {
            nbLignes++;
            journal[nbLignes - 1] = "TX" + quatreChiffres(nbLignes) + " " + de + " -> " + vers
                    + " " + montant + " FCFA (frais " + f + ")";
        }
        return true;
    }

    private String quatreChiffres(int n) {
        String s = "" + n;
        while (s.length() < 4) {
            s = "0" + s;
        }
        return s;
    }

    public String[] journal() {
        String[] copie = new String[nbLignes];
        for (int i = 0; i < nbLignes; i++) {
            copie[i] = journal[i];
        }
        return copie;
    }

    public int[] compterParType() {
        int[] r = new int[3];
        for (int i = 0; i < nombre; i++) {
            if (comptes[i] instanceof ComptePremium) {
                r[1]++;
            } else if (comptes[i] instanceof CompteStandard) {
                r[0]++;
            } else if (comptes[i] instanceof CompteMarchand) {
                r[2]++;
            }
        }
        return r;
    }

    private boolean avant(Compte a, Compte b) {
        if (a.getSolde() != b.getSolde()) {
            return a.getSolde() > b.getSolde();
        }
        return a.getNumero().compareTo(b.getNumero()) < 0;
    }

    public void trierParSolde() {
        for (int i = 1; i < nombre; i++) {
            Compte cle = comptes[i];
            int j = i - 1;
            while (j >= 0 && avant(cle, comptes[j])) {
                comptes[j + 1] = comptes[j];
                j--;
            }
            comptes[j + 1] = cle;
        }
    }

    public Compte[] comptes() {
        Compte[] copie = new Compte[nombre];
        for (int i = 0; i < nombre; i++) {
            copie[i] = comptes[i];
        }
        return copie;
    }

    public Compte[] comptesAuDessus(long seuil) {
        int n = 0;
        for (int i = 0; i < nombre; i++) {
            if (comptes[i].getSolde() >= seuil) {
                n++;
            }
        }
        Compte[] r = new Compte[n];
        int k = 0;
        for (int i = 0; i < nombre; i++) {
            if (comptes[i].getSolde() >= seuil) {
                r[k] = comptes[i];
                k++;
            }
        }
        return r;
    }

    
}