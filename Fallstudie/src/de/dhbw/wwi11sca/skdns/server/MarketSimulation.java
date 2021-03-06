package de.dhbw.wwi11sca.skdns.server;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * In der MarketSimulation werden alle Berechnungen
 *  f�r die Marktsimulation ausgef�hrt.
 *
 */
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;

public class MarketSimulation {

    // Companys
    private OwnCompany ownCompany;
    private Company company1;
    private Company company2;
    private Company company3;

    // Sonderfall 1
    private double freePersonal = 0;
    private int necessaryPersonal = 0;

    // Schritt 2
    private int random = 0;
    private int topLineMarket = 0;
    private int topLineMarketArithmetic = 0;
    private static final double[] INCREASERANDOM = { 1.01, 1.01, 1.02, 1.03,
            1.04, 1.02, 1.02, 1.01, 1.00, 0.99, 0.98, 1.01, 1.05, 1.02, 0.99,
            1.03 };

    // Schritt 3
    private static final double[] MARKETINGRANDOM = { 1.00, 1.00, 1.00, 1.00,
            1.05, 1.05, 1.05, 1.05, 1.05, 1.05, 1.1, 1.1, 1.1, 1.15, 1.15, 1.2 };

    // Schritt 5
    private int amount = 0;
    private int amortization = 0;

    // Constants
    private static final double ZEROPOINTTWO = 0.2;
    private static final double ZEROPOINTFIVE = 0.5;
    private static final double ZEROPOINTSEVEN = 0.7;
    private static final double ZEROPOINTNINETYNINE = 0.99;
    private static final double ONEPOINTZEROONE = 1.01;
    private static final double ONEPOINTTWO = 1.2;
    private static final int TEN = 10;
    private static final int FOURTEEN = 14;
    private static final int SIXTEEN = 16;
    private static final int HUNDRED = 100;

    /**
     * simulate f�hrt alle Schritte der Rechenlogik aus und ruft alle weiteren
     * notwendigen Methoden auf
     */
    public final SimulationVersion simulate(final SimulationVersion version) {
        // �bernehmen der Unternehmn in der Simulationsversion
        ownCompany = version.getOwnCompany();
        company1 = version.getCompany1();
        company2 = version.getCompany2();
        company3 = version.getCompany3();

        // Hinzuf�gen des neu eingestellten Personals zum Personal des eigenen
        // Unternehmens
        ownCompany.setNumberOfStaff(ownCompany.getNumberOfStaff()
                + version.getPersonal());

        // Sonderfall 1

        // �berpr�ft, ob gen�gend Personal f�r den Einsatz aller Maschinen
        // vorhanden ist
        specialCaseOne(version);

        // Schritt 1

        // Berechnung des Gesamtumsatz des Marktes
        // Umsatz aller Unternehmen addieren
        topLineMarket = calculateTopLineMarket(topLineMarket,
                ownCompany.getTopLine(), company1.getTopLine(),
                company2.getTopLine(), company3.getTopLine());

        // Schritt 2

        // Ermittlung des Marktwachstums und erneute Berechnung des Gesamtumsatz
        // des Markets
        // Wachstum des Markets bestimmen (in Abh�ngigkeit des Umsatzes)
        increaseRandom(1, SIXTEEN, random);

        // Berechnen des Gesamtumsatzes der zu berechneden Periode
        topLineMarket = (int) Math.ceil(INCREASERANDOM[random] * topLineMarket);

        // Schritt 3

        // Ermittlung des Umsatz aller Unternehmen
        // Marketingma�nahmen und Preis werden ber�cksichtigt
        // bei den Unternehmen Ber�cksichtigung durch Randomwerte
        ownCompany.setTopLine(calculateTopLineOwnCompany(
                version.getMarketing(), ownCompany.getTopLine(), ownCompany
                        .getProduct().getPrice(), version.getPrice()));

        company1.setTopLine(calculateTopLineCompanies(company1));
        company2.setTopLine(calculateTopLineCompanies(company2));
        company3.setTopLine(calculateTopLineCompanies(company3));

        // Schritt 4

        // Ermittlung des Marktgesamtumsatzes anhand der in Schritt 3
        // ermittelten Unternehmensums�tze
        topLineMarketArithmetic = calculateTopLineMarket(
                topLineMarketArithmetic, ownCompany.getTopLine(),
                company1.getTopLine(), company2.getTopLine(),
                company3.getTopLine());

        // Ermittlung der Unternehmensmarktanteile
        ownCompany.setMarketShare(calculateMarketShare(topLineMarketArithmetic,
                ownCompany.getTopLine(), ownCompany.getMarketShare()));
        company1.setMarketShare(calculateMarketShare(topLineMarketArithmetic,
                company1.getTopLine(), company1.getMarketShare()));
        company2.setMarketShare(calculateMarketShare(topLineMarketArithmetic,
                company2.getTopLine(), company2.getMarketShare()));
        company3.setMarketShare(calculateMarketShare(topLineMarketArithmetic,
                company3.getTopLine(), company3.getMarketShare()));

        // Schritt 5

        // Ermittlung der Unternehmensabsatzmengen
        ownCompany.getProduct().setSalesVolume(
                calculateSalesVolume(topLineMarket,
                        ownCompany.getMarketShare(), ownCompany.getProduct()
                                .getPrice(), ownCompany.getProduct()
                                .getSalesVolume()));
        company1.getProduct().setSalesVolume(
                calculateSalesVolume(topLineMarket, company1.getMarketShare(),
                        company1.getProduct().getPrice(), company1.getProduct()
                                .getSalesVolume()));
        company2.getProduct().setSalesVolume(
                calculateSalesVolume(topLineMarket, company1.getMarketShare(),
                        company1.getProduct().getPrice(), company2.getProduct()
                                .getSalesVolume()));
        company3.getProduct().setSalesVolume(
                calculateSalesVolume(topLineMarket, company1.getMarketShare(),
                        company1.getProduct().getPrice(), company3.getProduct()
                                .getSalesVolume()));

        // �berpr�fung, ob der Markt w�chst oder sinkt
        if (INCREASERANDOM[random] > 1) {
            // der Markt w�chst == 1
            version.setMarketIncrease(1);
        } else if (INCREASERANDOM[random] < 1) {
            // der Markt sinkt == -1
            version.setMarketIncrease(-1);
        } else {
            // der Markt ver�ndert sich nicht == 0
            version.setMarketIncrease(0);
        } // Ende if-else

        // Anzahl der neu eingestellten Mitarbeiter wird auf die
        // Mitarbeiteranzahl des eigenen Unternehmens gerechnet
        ownCompany.setNumberOfStaff(ownCompany.getNumberOfStaff()
                + version.getPersonal());

        // Die Werte der neu gekauften Maschine werden auf die Werte der
        // Maschine des eigenen Unternehmens gerechnet;
        // Maschinenkapazit�t
        ownCompany.getMachines().setCapacity(
                version.getMachineCapacity()
                        + ownCompany.getMachines().getCapacity());
        // n�tige Mitarbeiter
        ownCompany.getMachines()
                .setStaff(
                        version.getMachineStaff()
                                + ownCompany.getMachines().getStaff());
        // Buchwert
        ownCompany.getMachines().setAccountingValue(
                version.getMachineValue()
                        + ownCompany.getMachines().getAccountingValue());

        // Sonderfall 2

        // Ermittelt, ob die Kapazit�ten der Maschinen ausgelastet sind
        specialCaseTwo(version, ownCompany);

        // Ermittlung der Maschinenabschreibungen
        amortization = getAmortization(amortization, version.getMachineValue(),
                ownCompany.getMachines());

        // Schritt 6 Ausgaben berechnen

        // Ermittlung des Gewinns des eigenen Unternehmens
        amount = (int) Math.ceil(ownCompany.getTopLine()
                - ownCompany.getFixedCosts() - ownCompany.getSalaryStaff()
                * ownCompany.getNumberOfStaff() - version.getMarketing()
                - ownCompany.getVariableCosts()
                * ownCompany.getProduct().getSalesVolume() - amortization);

        ownCompany.setAmount(amount);

        // �bergeben der Unternehmen in die SimulationsVersion
        version.setOwnCompany(ownCompany);
        version.setCompany1(company1);
        version.setCompany2(company2);
        version.setCompany3(company3);

        return version;
    } // Ende method simulate

    /**
     * specialCaseTwo ermittelt, ob gen�gend Personal f�r die Maschinen
     * vorhanden sind
     */
    public final void specialCaseOne(final SimulationVersion version) {
        // ermitteln des freien Personals
        freePersonal = (ownCompany.getNumberOfStaff())
                * ZEROPOINTSEVEN
                - (ownCompany.getMachines().getStaff() + version
                        .getMachineStaff());

        // ist freePersonal kleiner als 0 wird Personal ben�tigt
        if (freePersonal < 0.0) {
            necessaryPersonal = (int) Math
                    .ceil((version.getMachineStaff() + ownCompany.getMachines()
                            .getStaff())
                            / ZEROPOINTSEVEN
                            - ownCompany.getNumberOfStaff());

            version.setNecessaryPersonal(necessaryPersonal);

        } else {
            version.setNecessaryPersonal(0);
        }

    } // Ende method specialCaseOne

    /**
     * specialCaseTwo �berpr�ft, ob die Maschinen des eigenen Unternehmens
     * gen�gend Kapazit�t aufweisen, um die errechnete Absatzmenge zu
     * produzieren
     */
    public final void specialCaseTwo(final SimulationVersion version,
            final OwnCompany ownCompany) {

        // Absatzmenge des eigenen Unternehmens berechnen
        int salesVolume = calculateSalesVolume(ownCompany.getTopLine(),
                ownCompany.getProduct().getPrice());

        // �berpr�fung, ob Kapazit�t kleiner als Absatzmenge ist
        if (ownCompany.getMachines().getCapacity() < salesVolume) {

            // Die Absatzmenge entspricht der Maschinenauslastung von 100%
            ownCompany.getProduct().setSalesVolume(
                    ownCompany.getMachines().getCapacity());

            // --> Der Umsatz sinkt
            ownCompany.setTopLine((int) Math.ceil(salesVolume
                    * ownCompany.getProduct().getPrice()));

            // setzt den boolean in der SimulationVersion auf true, damit der
            // User darauf hingewiesen werden kann
            version.setUnusedMachineCapacity(true);

            // Marktanteil der Konkurrenzunternehmen berechnen
            double marketSharesCompanies = company1.getMarketShare()
                    + company2.getMarketShare() + company3.getMarketShare();

            // Umrechnung der �berschussabsatzmenge auf die
            // Konkurrenzunternehmen
            calculateAdditionalVolume(marketSharesCompanies, company1);
            calculateAdditionalVolume(marketSharesCompanies, company2);
            calculateAdditionalVolume(marketSharesCompanies, company3);

            // Berechnen des neuen Gesamtmarktes
            topLineMarket = calculateTopLineMarket(topLineMarket,
                    ownCompany.getTopLine(), company1.getTopLine(),
                    company2.getTopLine(), company3.getTopLine());

            // neue Marktanteile berechen
            // Ermittlung der Unternehmensmarktanteile
            ownCompany.setMarketShare(calculateMarketShare(
                    topLineMarketArithmetic, ownCompany.getTopLine(),
                    ownCompany.getMarketShare()));
            company1.setMarketShare(calculateMarketShare(
                    topLineMarketArithmetic, company1.getTopLine(),
                    company1.getMarketShare()));
            company2.setMarketShare(calculateMarketShare(
                    topLineMarketArithmetic, company2.getTopLine(),
                    company2.getMarketShare()));
            company3.setMarketShare(calculateMarketShare(
                    topLineMarketArithmetic, company3.getTopLine(),
                    company3.getMarketShare()));

        } else {
            // ist die Kapazit�t gro� genug, bleibt es bei der Absatzmenge
            ownCompany.getProduct().setSalesVolume(salesVolume);
        } // Ende if-else

        // Setzen der Absatzmengen der Konkurrenzunternehmen
        company1.getProduct().setSalesVolume(
                calculateSalesVolume(company1.getTopLine(), company1
                        .getProduct().getPrice()));
        company2.getProduct().setSalesVolume(
                calculateSalesVolume(company2.getTopLine(), company2
                        .getProduct().getPrice()));
        company3.getProduct().setSalesVolume(
                calculateSalesVolume(company3.getTopLine(), company3
                        .getProduct().getPrice()));
    } // Ende method specialCaseTwo

    /**
     * calculateSalesVolume berechnet anhand von Preis und Umsatz des
     * Unternehmens
     */
    private int calculateSalesVolume(final int topLine, final double price) {
        double topLineCalc = topLine; // Umsatz

        int salesVolume = (int) Math.ceil(topLineCalc / price);
        return salesVolume;
    }

    /**
     * calculateAdditionalVolume berechnet die zus�tzliche Absatzmenge der
     * Konkurrenzunternehmen, falls nicht gen�gend Maschinenkapazit�t vorhanden
     * ist
     */
    private Company calculateAdditionalVolume(
            final double marketSharesCompanies, final Company company) {
        double distributionKey = company.getMarketShare()
                / marketSharesCompanies;
        double salesVolume = (ownCompany.getProduct().getSalesVolume() - ownCompany
                .getMachines().getCapacity())
                * ownCompany.getProduct().getPrice()
                * distributionKey
                + company.getProduct().getSalesVolume();

        company.getProduct().setSalesVolume((int) Math.ceil(salesVolume));

        // neuer Umsatz des Unternehmens
        company.setTopLine((int) (salesVolume * company.getProduct().getPrice()));
        return company;

    } // Ende method calculateAdditionalVolume

    /**
     * calculateTopLineMarket berechnet den Gesamtumsatz des Marktes
     */
    public static int calculateTopLineMarket(int topLineMarket,
            final int tLOwnCompany, final int tLCompany1, final int tLCompany2,
            final int tLCompany3) {

        topLineMarket = tLOwnCompany + tLCompany1 + tLCompany2 + tLCompany3;
        return topLineMarket;
    } // Ende method calculateTopLineMarket

    /**
     * increaseRandom ermittelt anhand der Randomfunktion den Marktwachstum
     */
    public static int increaseRandom(final int low, final int high, int random) {
        random = (int) Math.round(Math.random() * (high - low) + low);
        return random;
    } // Ende method increaseRandom

    /**
     * calculateTopLineCompanies berechnet den Umsatz der Konkurrenzunternehmen
     * mittels Random f�r Marketing und Preis�nderung
     */
    public static int calculateTopLineCompanies(final Company company) {
        int topLine = company.getTopLine();

        // Marketingumsatzanstieg berechnen (anhand Random)
        int random = (int) Math.ceil(Math.random() * FOURTEEN + 1);
        double marketing = MARKETINGRANDOM[random];

        // Preisfaktor berechnen (anhand Random)
        double priceFactor = Math.random()
                * (ONEPOINTZEROONE - ZEROPOINTNINETYNINE) + ZEROPOINTNINETYNINE;

        // neuer Preis des Unternehmens ermitteln
        company.getProduct().setPrice(
                Math.ceil(company.getProduct().getPrice() / priceFactor));

        // Umsatz berechnen
        topLine = (int) Math.ceil(topLine * marketing * priceFactor);
        return topLine;
    } // Ende method calculateTopLineCompanies

    /**
     * calculateTopLineOwnCompany berechnet den Umsatz des eigenen Unternehmens
     * mit Ber�cksichtigung der get�tigten Investitionen zu Marketing und
     * Preis�nderung
     */
    public static int calculateTopLineOwnCompany(final int marketingInput,
            int topLine, final double priceOld, double priceNew) {

        double marketingInputCalc = marketingInput;
        double topLineCalc = topLine;

        // Marketingumsatzanstieg berechnen
        double marketing = marketingInputCalc / topLineCalc;

        // �berpr�fung, ob das Ergebnis gr��er ist als 0,2
        if (marketing > ZEROPOINTTWO) {
            marketing = ONEPOINTTWO;
        } else {
            marketing = marketing + 1.0;// Ende if-Statement
        }

        // Preis�nderung ermitteln
        // Kontrolle, ob eine Preis�nderung get�tigt wurde
        if (priceNew == 0) {
            priceNew = priceOld;
        }
        double priceFactor = priceOld / priceNew;

        // Berechnung des Umsatzes
        topLine = (int) (Math.ceil(topLine * marketing * priceFactor));
        return topLine;
    } // Ende method calculateTopLineOwnCompany

    /**
     * calculateMarketShare ermittelt den Marktanteil f�r das angegebene
     * Unternehmen
     */
    public static double calculateMarketShare(final int topLine,
            final int topLineCompany, double marketShare) {
        double topLineCompanyCalc = topLineCompany; // Gesamtumsatz des Marktes
        double topLineCalc = topLine; // Umsatz des eigenen Unternehmens

        // Marktanteil anhand von Gesamtmarkt und Umsatz
        marketShare = Math
                .round((marketShare + (topLineCompanyCalc / topLineCalc)
                        * HUNDRED)
                        * ZEROPOINTFIVE);
        return marketShare;
    } // Ende method calculateMarketShare

    /**
     * calculateSalesVolume ermittelt die Absatzmenge des angegebenen
     * Unternehmens
     */
    public static int calculateSalesVolume(final int topLineMarket,
            final double marketShare, final double price, int salesVolume) {

        int realTopLine = (int) (topLineMarket * marketShare / HUNDRED); // realer
                                                                         // Umsatz

        salesVolume = (int) Math.ceil(realTopLine / price);
        return salesVolume;
    } // Ende method calculateSalesVolume

    /**
	 */
    private static int getAmortization(int amortization,
            final int machineValue, final Machines machine) {

        amortization = (int) Math.ceil(machineValue / TEN
                + machine.getAccountingValue() / machine.getServiceLife());
        return amortization;
    } // Ende method getAmortization
} // Ende class MarketSimulation
