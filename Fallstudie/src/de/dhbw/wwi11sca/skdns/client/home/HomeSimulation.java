package de.dhbw.wwi11sca.skdns.client.home;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die HomeSimulation enthält das Frontend des Homescreens des
 * jeweiligen Users.
 * 
 */
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.dhbw.wwi11sca.skdns.client.logout.LogoutSimulation;
import de.dhbw.wwi11sca.skdns.client.simulation.Simulation;
import de.dhbw.wwi11sca.skdns.client.company.CompanySimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class HomeSimulation implements EntryPoint {
	
	//Constants
	private static final int LOGOX = 333;
	private static final int LOGOY = 96;
	private static final int TABLECOMX = 110;
	private static final int TABLECOMY = 268;
	private static final int BTCOMCHANGEX = 267;
	private static final int BTCOMCHANGEY = 533;
	private static final int BTSIMULATIONX = 569;
	private static final int BTSIMULATIONY = 533;
	private static final int BTLOGOUTX = 914;
	private static final int BTLOGOUTY = 10;

	private AbsolutePanel panelHome = new AbsolutePanel();
	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private CellTable<Company> tableCompanies = new CellTable<Company>();
	private Button btCompaniesChange = new Button("Unternehmen bearbeiten");
	private Button btSimulation = new Button("Simulation starten");
	private Button btLogout = new Button("Logout");
	private List<Company> companyList;
	private int emptyCompanyCounter = 0;
	private HomeServiceAsync service = GWT.create(HomeService.class);

	public final void onModuleLoad() {
		// RootPanel: root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel: absolutePanelHome
		root.add(panelHome, 0, 0);
		panelHome.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelHome.add(logo, LOGOX, LOGOY);
		logo.setSize("359px", "93px");

		// CellTable für Konkurrenzunternehmen
		panelHome.add(tableCompanies, TABLECOMX, TABLECOMY);
		tableCompanies.setSize("805px", "200px");
		tableCompanies.setStyleName("cellTableHeader");

		// Buttons

		// Button Unternehmen bearbeiten: btUNBearbeiten
		panelHome.add(btCompaniesChange, BTCOMCHANGEX, BTCOMCHANGEY);
		btCompaniesChange.setSize("200px", "35px");

		// Button Simulation starten: btSimulation
		panelHome.add(btSimulation, BTSIMULATIONX, BTSIMULATIONY);
		btSimulation.setSize("200px", "35px");

		// Button Logout: btLogout
		panelHome.add(btLogout, BTLOGOUTX, BTLOGOUTY);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Eventhandler Unternehmen bearbeiten
		btCompaniesChange.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				// Laden der Unternehmen bearbeiten Oberfläche
				RootPanel.get().clear();
				CompanySimulation company = new CompanySimulation();
				company.onModuleLoad();
			}
		}); // Ende btCompaniesChange

		// Eventhandler Simualation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				// Laden der Simulationsoberfläche
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		}); // Ende btSimulation

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				// Laden der Logoutoberfläche
				RootPanel.get().clear();
				LogoutSimulation logout = new LogoutSimulation();
				logout.onModuleLoad();
			}
		}); // btLogout

		// Tabelle mit Unternehmensdaten befüllen
		setTableCompanies();

		// Call: Unternehmen laden
		service.getCompany(new GetCompanyCallback());
	} // Ende method onModuleLoad

	private void setTableCompanies() {

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Firma
		TextColumn<Company> tradeNameColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new String(company.getTradeName());

			}

		}; // Ende tradeNameColumn

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> topLineColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				if (company.getTopLine() != 0) {
					return new Integer(company.getTopLine()).toString();
				} else {
					// Ist der Wert 0 wird k.A. gegeben
					return new String("k.A.");
				}
			}
		}; // Ende topLineColumn

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Gewinn
		TextColumn<Company> amountColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				if (company.getAmount() != 0) {
					return new Integer(company.getAmount()).toString();
				} else {
					// Ist der Wert 0 wird k.A. gegeben
					return new String("k.A.");
				}
			}
		}; // Ende amountColumn

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Marktanteil
		TextColumn<Company> marketShareColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				if (company.getMarketShare() != 0.0) {
					return new Double(company.getMarketShare()).toString();
				} else {
					// Ist der Wert 0 wird k.A. gegeben
					return new String("k.A.");
				}
			}
		}; // Ende marketShareColumn

		// Unternehmensdaten der Konkurrenzunternehmen befüllen:
		// Produktmenge
		TextColumn<Company> salesVolumeColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				if (company.getProduct().getSalesVolume() != 0) {
					return new Integer(company.getProduct().getSalesVolume())
							.toString();
				} else {
					// Ist der Wert 0 wird k.A. gegeben
					return new String("k.A.");
				}
			}
		}; // Ende salesVolumeColumn

		// Unternehmensdaten der Konkurrenzunternehmen befüllen:
		// Produktpreis
		TextColumn<Company> productPriceColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				if (company.getProduct().getPrice() != 0.0) {
					return new Double(company.getProduct().getPrice())
							.toString();
				} else {
					// Ist der Wert 0 wird k.A. gegeben
					return new String("k.A.");
				}
			}
		}; // Ende productPriceColumn

		// Unternehmensdaten der Konkurrenzunternehmen anzeigen lassen
		tableCompanies.addColumn(tradeNameColumn, "Firma");
		tableCompanies.addColumn(topLineColumn, "Umsatz");
		tableCompanies.addColumn(amountColumn, "Gewinn");
		tableCompanies.addColumn(marketShareColumn, "Marktanteil");
		tableCompanies.addColumn(productPriceColumn, "Produktpreis");
		tableCompanies.addColumn(salesVolumeColumn, "Absatzmenge");
	
		
	}
	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * der Konkurrenzunternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetCompanyCallback implements AsyncCallback<List<Company>> {

		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		public final void onSuccess(final List<Company> result) {

			// Unternehmenstabelle mit dem DataProvider verbinden
			ListDataProvider<Company> dataProvider = new 
					ListDataProvider<Company>();
			dataProvider.addDataDisplay(tableCompanies);

			// Liste befüllen
			companyList = dataProvider.getList();

			// Ermitteln, wie viele gefüllte Unternehmen zurückgeliefert werden
			for (Company company : result) {
				companyList.add(company);
				if (company.getTopLine() == 0) {
					emptyCompanyCounter++;
				} // Ende if-Statement
			} // Ende for-Schleife

			// Wenn weniger als zwei Unternehmen aus der DB zurückgeliefert
			// werden, kann die Simulation nicht stattfinden.
			// Daher wird der Button btSimulation deaktiviert
			if (emptyCompanyCounter > 2) {
				btSimulation.setEnabled(false);
			} // Ende if-Statement
		} // Ende method onSuccess
	} // Ende class GetCompanyCallback
} // Ende class HomeSimulation
