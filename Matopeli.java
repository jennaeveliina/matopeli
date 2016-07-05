/*
 * Lausekielinen ohjelmointi, 2.harjoitustyö
 * Matopeli
 *
 *Jenna Halmetoja, a616148
 * Halmetoja.Jenna.E@student.uta.fi
 */
public class Matopeli {
    //Alustetaan vakioituja muuttujia.
    private static final String OHJAUS = "(l)eft, (r)ight, (u)p, (d)own, (s)wap or (q)uit?";
    private static final String VIRHE = "Invalid command-line argument!";
    private static final String LOPETUS = "Bye, see you soon.";

    //Metodi tulostaa pelikentän.
    public static void tulostaKentta(char[][] t) {
        //Muistin tarkistaminen.
        if (t != null) {
           int rivlkm = t.length;
           int sarlkm = t[0].length;

            for (int rivi = 0; rivi < rivlkm; rivi++) {
                for (int sarake = 0; sarake < sarlkm; sarake++)
                    System.out.print(t[rivi][sarake]);
                    System.out.println();
            }
        }
    }

    //Kentän alustaminen.
    public static void alustaKentta(char[][] t, char a, char b) {
        if (t != null) {
            int rivlkm = t.length;
            int sarlkm = t[0].length;

        //Asetetaan merkit.
        for (int rivi = 0; rivi < rivlkm; rivi++)
            for (int sarake = 0; sarake < sarlkm; sarake++)
                if (rivi == 0 || rivi == rivlkm - 1 || sarake == 0 || sarake == sarlkm - 1)
                    t[rivi][sarake] = a;
                else if (t[rivi][sarake] != '+'){
                        t[rivi][sarake] = b;
                    }
        }
    }

    //Kentän luontimetodi.
    public static char[][] luoKentta(int rivlkm, int sarlkm) {
        char[][] kentta = new char[rivlkm][sarlkm];
        //alustaKentta metodiin lisätään arvot.
        alustaKentta(kentta, '.', ' ');
        // Palautetaan kenttä.
        return kentta;
    }
    
    //Madon piirtäminen.
    public static boolean piirraMato(char[][] merkit, int[][] paikat) {
        // Vakioidaan piirtomerkit.
        final char VARTALO = 'o';
        final char PAA2 = 'x';
        final char PAA1 = 'X';

        // Testataan mahtuuko molempiin taulukoihin.
        if (merkit != null && paikat != null) {
            int merkkienLkm = paikat.length;

            // Sijoitetaan kuvion merkit kenttään.
            for (int paikanInd = 0; paikanInd < merkkienLkm; paikanInd++) {
                // Kuvion merkin rivi- ja sarakeindeksit.
                int merkinRivInd = paikat[paikanInd][0];
                int merkinSarInd = paikat[paikanInd][1];
                
                //asetetaan piirtomerkki alku-paikkaan.
                if (paikanInd == 0){
                    merkit[merkinRivInd][merkinSarInd]= PAA1;
                }
                else if (paikanInd == 1){
                    merkit[merkinRivInd][merkinSarInd]= PAA2;
                }
                else {
                    merkit[merkinRivInd][merkinSarInd]= VARTALO;
                }
            }
             
            // Voitiin piirtää.
            return true;
        }
        // ei voitu piirtää.
        else
            return false;
    }
    //vaihdetaan madon pään ja hännän paikat keskenään(swap).
    public static void vaihda(int[][] paikat) {
        //tarkistetaan onko varattu muistia.
        if (paikat != null) {
            //käydään kaikki paikat kerrallaan läpi
            //madon puoliväliin asti.
            for (int i = 0; i < paikat.length / 2; i++) {
                int apuRivi = paikat[i][0];
                int apuSarake = paikat[i][1];
                //asetetaan madon pään paikan merkit häntään
                //ja päin vastoin
                paikat[i][0] = paikat[paikat.length - 1 - i][0];
                paikat[i][1] = paikat[paikat.length - 1 - i][1];
                paikat[paikat.length - 1 - i][0] = apuRivi;
                paikat[paikat.length - 1 - i][1] = apuSarake;
            }
        }
    }
    //Liikutetaan matoa.
    public static void liiku(int[][] mato, char[][] kentta, char syote) {
        if (mato != null && kentta != null) {
            int paanUusiRivi = mato[0][0];
            int paanUusiSarake = mato[0][1];

            // Päätellään madon päälle uusi paikka.
            if (syote == 'l'){
                paanUusiSarake--;
            }
            else if (syote == 'r'){
                paanUusiSarake++;
            }
            else if (syote == 'u'){
                paanUusiRivi--;
            }
            else if (syote == 'd'){
                paanUusiRivi++;
            }

            // Kieräytetään pää tarvittaessa toiselle reunalle.
            if (paanUusiSarake < 0){
                paanUusiSarake = kentta[0].length - 1;
            }
            else if (paanUusiSarake > kentta[0].length - 1){
                paanUusiSarake = 0;
            }
            else if (paanUusiRivi < 0){
                paanUusiRivi = kentta.length - 1;
            }
        else if (paanUusiRivi > kentta.length - 1){
            paanUusiRivi = 0;
        }

            // Vältetään pakitus.
            boolean oikeaSuunta = false;
            if (paanUusiRivi != mato[1][0] || paanUusiSarake != mato[1][1]){
                oikeaSuunta = true;
            }
            //jos suunta on oikea.
            if (oikeaSuunta) {
                kentta[mato[mato.length - 1][0]] [mato[mato.length - 1][1]] = ' ';
                for (int i = mato.length - 1; i > 0; i-- ) {
                    mato[i][0] = mato[i - 1][0];
                    mato[i][1] = mato[i - 1][1];
                }

                // Päivitetään pään paikka.
                mato[0][0] = paanUusiRivi;
                mato[0][1] = paanUusiSarake;
            }
        }
    }
    // Jos ruokaa, mato kasvaa, kasvata pituuslaskuria ja uusi ruoka kentälle.
    public static int[][] kasvataMatoa(int[][] paikat, int[][] hannanVanhaPaikka) {
        //paikat taulukosta uusi taulukko +1 riviä
        int [][] uudetPaikat = new int[paikat.length + 1][paikat[0].length];
        //silmukka joka kopioi paikat uuteen taulukkoon
        for(int i = 0; i < paikat.length; i++){
            uudetPaikat[i][0] = paikat[i][0];
            uudetPaikat[i][1] = paikat[i][1];
        }
        //hännänvanhalle paikalle kopioidaan uudetMerkit
        uudetPaikat[uudetPaikat.length - 1][0] = hannanVanhaPaikka[0][0];
        uudetPaikat[uudetPaikat.length - 1][1] = hannanVanhaPaikka[1][0];
        
        return uudetPaikat;
    }
    
    //päämetodi.
    public static void main(String[] args) {
        /*Alustetaan muuttujia.
         */
        int pituus = 5;
        int aukko = 0;

        int rivi = 0;
        int sarake = 0;

        int siemen = 0;
        
        boolean jatketaan = true;
        boolean argsOK = true;
        boolean syoty = false;
        boolean ruokaOnKentalla = true;
        
        char[][] merkkiTaulukko;
        
        //madon merkkien paikat
        int[][] merkkienPaikat ={ { 1, 5 },
                                  { 1, 4 },
                                  { 1, 3 },
                                  { 1, 2 },
                                  { 1, 1 } };

        //Tulostetaan ohjelman nimi näytölle.
        System.out.println("~~~~~~~~~~~");
        System.out.println("~ W O R M ~");
        System.out.println("~~~~~~~~~~~");

        //Testataan komentoparametrien oikeellisuus.
        if ((args.length == 3) || (rivi >=3) || (sarake >=7)) {
        
            //Testataan ovatko kaikki int-tyyppisiä.
            try {
                siemen = Integer.parseInt(args[0]);
                rivi = Integer.parseInt(args[1]);
                sarake = Integer.parseInt(args[2]);
                }
            //napataan virhe.
            catch (NumberFormatException e) {
                argsOK = false; }
            }
        //Katsotaan ovatko annetut syötteet oikeellisia.
        else if ((rivi < 3) || (sarake < 7) || (args.length != 3)) {
            argsOK = false;
        }
        else
            argsOK = true;
        
        //Jos joku ehdoista ei täyty, tulostetaan virheilmoitus ja lopetetaan.
        if (!argsOK) {
            System.out.println(VIRHE);
            System.out.println(LOPETUS);
        }
        else {
        
            //Asetetaan ruoka kentälle.
            Automaatti.kaynnista(siemen);
            
            //kutsutaan luoKentta-metodia
            merkkiTaulukko = luoKentta(rivi, sarake);
            
            //Piirretään mato
            boolean piirtoOK = piirraMato(merkkiTaulukko, merkkienPaikat);
            
            //tarjoile ruoka
            Automaatti.tarjoile(merkkiTaulukko);
            
            //ollaan silmukassa niin kauan kunnes päätetään lopettaa
            //tai peli loppuu
            while (jatketaan) {
                //tulostetaan laskurit näkyville.
                System.out.println("Worm length: " + pituus + ", wormholes: " + aukko + ".");

                //Tulostetaan kenttä.
                tulostaKentta(merkkiTaulukko);
                                
                System.out.println(OHJAUS);

                //Luetaan pelaajan komento.
                char liike = In.readChar();
                    
                  
                //Jos liikutaan vasemmalle.
                //Jos liikutaan oikealle.
                //Jos liikutaan ylös.
                //Jos liikutaan alas.
                if (liike == 'l' || liike == 'r' || liike == 'u' || liike == 'd') {
                    int[][] hannanVanhaPaikka = { { merkkienPaikat[merkkienPaikat.length - 1][0] },
                                                  { merkkienPaikat[merkkienPaikat.length - 1][1] } };
                    //kutsutaan liiku-metodia.
                    liiku(merkkienPaikat, merkkiTaulukko, liike);

                    // Syönti ja törmäykset.
                    if (merkkiTaulukko[merkkienPaikat[0][0]][merkkienPaikat[0][1]] == '+'){
                        syoty = true;
                    }
                        //jos ruoka on syöty, lisätään pituuslaskuria ja
                        //käännetään lippumuuttujat false:ksi.
                        if (syoty){
                        pituus++;
                        syoty = false;
                        ruokaOnKentalla = false;
                        
                        merkkienPaikat = kasvataMatoa(merkkienPaikat, hannanVanhaPaikka);
                        
                    }
                    
                    
                    
                    // Jos reunaa, niin kasvatetaan aukkolaskuria
                    if (merkkiTaulukko[merkkienPaikat[0][0]][merkkienPaikat[0][1]] == '.'){
                        aukko++;
                    }
                    // ja käännetään pääsilmukan lippu, jos törmäyksiä on yli 3.
                    if(aukko > 3){
                        System.out.println(LOPETUS);
                        jatketaan = false;
                    }
                    // Jos matoa ('o'), niin käännä pääsilmukan lippu ja lopeta peli.
                    if (merkkiTaulukko[merkkienPaikat[0][0]][merkkienPaikat[0][1]] == 'o'){
                        System.out.println(LOPETUS);
                        jatketaan = false;
                    }
                }
                //Jos vaihdetaan pään ja hännän paikkaa.
                else if (liike == 's') {
                    vaihda(merkkienPaikat);
                }
                //Jos lopetetaan peli.
                else if (liike == 'q'){
                    System.out.println(LOPETUS);
                    jatketaan = false;
                }
                //Piirretään mato
                piirtoOK = piirraMato(merkkiTaulukko, merkkienPaikat);
                // Jos ei ole ruokaa, tarjoillaan se kentälle ja
                //kutsutaan kasvataMatoa-metodia.
                if (!ruokaOnKentalla){
                    ruokaOnKentalla = Automaatti.tarjoile(merkkiTaulukko);
                }
            }
        }
    }
}