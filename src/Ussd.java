public class Ussd {

    private static boolean estChiffre(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean numeroValide(String n) {
        if (n == null || n.length() != 9) {
            return false;
        }
        for (int i = 0; i < n.length(); i++) {
            if (!estChiffre(n.charAt(i))) {
                return false;
            }
        }
        if (n.charAt(0) != '7') {
            return false;
        }
        char c = n.charAt(1);
        return c == '0' || c == '5' || c == '6' || c == '7' || c == '8';
    }

    public static String masquer(String n) {
        if (n == null || n.length() < 4) {
            return n;
        }
        StringBuilder sb = new StringBuilder(n.substring(0, 2));
        for (int i = 2; i <= n.length() - 3; i++) {
            sb.append('*');
        }
        sb.append(n.substring(n.length() - 2));
        return sb.toString();
    }

    public static long lireMontant(String s) {
        if (s == null || s.isEmpty() || s.length() > 9) {
            return -1;
        }
        long m = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!estChiffre(c)) {
                return -1;
            }
            m = m * 10 + (c - '0');
        }
        return m;
    }

    public static String formaterMontant(long m) {
        String s = "" + m;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (s.length() - i) % 3 == 0) {
                sb.append(' ');
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static String normaliser(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ' && c != '.' && c != '-') {
                sb.append(c);
            }
        }
        String r = sb.toString();
        if (r.startsWith("+221")) {
            r = r.substring(4);
        } else if (r.startsWith("00221")) {
            r = r.substring(5);
        }
        return numeroValide(r) ? r : null;
    }

    public static String[] decouper(String code) {
        if (code == null || code.length() < 3) {
            return null;
        }
        if (code.charAt(0) != '*' || code.charAt(code.length() - 1) != '#') {
            return null;
        }
        String corps = code.substring(1, code.length() - 1);
        if (corps.indexOf('#') >= 0) {
            return null;
        }
        int n = 1;
        for (int i = 0; i < corps.length(); i++) {
            if (corps.charAt(i) == '*') {
                n++;
            }
        }
        String[] champs = new String[n];
        int k = 0;
        int debut = 0;
        for (int i = 0; i <= corps.length(); i++) {
            if (i == corps.length() || corps.charAt(i) == '*') {
                if (i == debut) {
                    return null;
                }
                champs[k++] = corps.substring(debut, i);
                debut = i + 1;
            }
        }
        return champs;
    }

    public static String executer(Plateforme p, String appelant, String code) {
        String[] c = decouper(code);
        if (c == null) {
            return "Code invalide";
        }
        if (!c[0].equals("144")) {
            return "Service inconnu";
        }
        Compte compte = p.rechercher(appelant);
        if (compte == null) {
            return "Compte inconnu";
        }
        if (c.length < 2) {
            return "Code invalide";
        }
        if (c[1].equals("1")) {
            if (c.length != 4) {
                return "Code invalide";
            }
            String dest = normaliser(c[2]);
            if (dest == null) {
                return "Numero invalide";
            }
            long montant = lireMontant(c[3]);
            if (montant <= 0) {
                return "Montant invalide";
            }
            long frais = compte.frais(montant);
            if (!p.transferer(appelant, dest, montant)) {
                return "Transfert refuse";
            }
            return "Transfert de " + formaterMontant(montant) + " FCFA vers " + masquer(dest)
                    + " effectue. Frais : " + formaterMontant(frais) + " FCFA";
        }
        if (c[1].equals("2")) {
            if (c.length != 2) {
                return "Code invalide";
            }
            return "Solde : " + formaterMontant(compte.getSolde()) + " FCFA";
        }
        return "Service inconnu";
    }
}
