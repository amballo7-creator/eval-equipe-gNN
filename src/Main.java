public class Main {
    public static void main(String[] args) {
        Plateforme p = new Plateforme();
        p.ajouter(new CompteStandard("771234567", "Awa Diop"));
        p.ajouter(new ComptePremium("781112233", "Moussa Fall"));
        p.ajouter(new CompteMarchand("701234500", "Fatou Sarr", "Boutique Keur Fatou"));
        p.ajouter(new CompteStandard("761010101", "Ibrahima Ndiaye"));
        p.rechercher("771234567").deposer(200_000);
        p.rechercher("781112233").deposer(300_000);
        p.rechercher("761010101").deposer(15_000);

        String[] codes = {
            "*144*1*78 111 22 33*25000#",
            "*144*1*+221701234500*120000#",
            "*144*2#",
            "*144*1*761010101*500000#",
            "*144*1*761010101*5O00#",
            "*145*1#",
            "*144*1*7712*1000#",
        };
        for (String code : codes) {
            System.out.println(Ussd.executer(p, "771234567", code));
        }
        System.out.println(Ussd.executer(p, "781112233", "*144*1*701234500*60000#"));

        p.trierParSolde();
        for (Compte c : p.comptes()) {
            System.out.println(c);
        }
        for (String ligne : p.journal()) {
            System.out.println(ligne);
        }
        System.out.println("Frais collectes : " + Ussd.formaterMontant(p.getTotalFrais()) + " FCFA");
    }
}
