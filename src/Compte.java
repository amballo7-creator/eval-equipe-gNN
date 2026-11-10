public abstract class Compte {
    private static int nombreCrees = 0;

    private String numero;
    private String titulaire;
    private long solde;

    public Compte(String numero, String titulaire) {
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = 0;
        nombreCrees++;
    }

    public String getNumero() { return numero; }
    public String getTitulaire() { return titulaire; }
    public long getSolde() { return solde; }
    public static int getNombreCrees() { return nombreCrees; }

    public abstract long frais(long montant);
    public abstract String type();

    public long plafond() { return Parametres.PLAFOND_RETRAIT; }

    public boolean deposer(long montant) {
        if (montant <= 0) {
            return false;
        }
        solde += montant;
        return true;
    }

    public boolean retirer(long montant) {
        if (montant <= 0 || montant > plafond() || montant + frais(montant) > solde) {
            return false;
        }
        solde -= montant + frais(montant);
        return true;
    }

    @Override
    public String toString() {
        return "[" + type() + "] " + numero + " " + titulaire + " " + solde + " FCFA";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Compte)) {
            return false;
        }
        return numero.equals(((Compte) o).numero);
    }

    @Override
    public int hashCode() { return numero.hashCode(); }
}
