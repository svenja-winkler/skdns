package de.dhbw.wwi11sca.skdns.client.home;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die HomeSimulation enthält das Frontend des Homescreens des
 *         jeweiligen Users.
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.client.logout.LogoutSimulation;
import de.dhbw.wwi11sca.skdns.client.simulation.Simulation;
import de.dhbw.wwi11sca.skdns.client.company.CompanySimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class HomeSimulation implements EntryPoint {

	// Panels
	private AbsolutePanel panelHome = new AbsolutePanel();

	// Widgets
	private CellTable<Company> tableCompanies = new CellTable<Company>();
	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	private Button btCompaniesChange = new Button("Unternehmen bearbeiten");
	private Button btSimulation = new Button("Simulation starten");
	private Button btLogout = new Button("Logout");

	private List<Company> companyList;
	int emptyCompanyCounter = 0;

	private HomeServiceAsync service = GWT.create(HomeService.class);

	@Override
	public void onModuleLoad() {

		// RootPanel: root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel: absolutePanelHome
		root.add(panelHome, 0, 0);
		panelHome.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelHome.add(logo, 333, 96);
		logo.setSize("359px", "93px");

		// Buttons

		// Button Unternehmen bearbeiten: btUNBearbeiten
		panelHome.add(btCompaniesChange, 267, 533);
		btCompaniesChange.setSize("200px", "35px");

		// Button Simulation starten: btSimulation
		panelHome.add(btSimulation, 569, 533);
		btSimulation.setSize("200px", "35px");

		// Button Logout: btLogout
		panelHome.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Eventhandler Unternehmen bearbeiten
		btCompaniesChange.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				CompanySimulation company = new CompanySimulation();
				company.onModuleLoad();
			}
		}); // Ende btCompaniesChange

		// Eventhandler Simualation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				RootPanel.get().clear();
				Simulation simulation = new Simulation();
				simulation.onModuleLoad();
			}
		}); // Ende btSimulation

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				LogoutSimulation logout = new LogoutSimulation();
				logout.onModuleLoad();
			}
		}); // btLogout

		// CellTable für Konkurrenzunternehmen
		panelHome.add(tableCompanies, 110, 268);
		tableCompanies.setSize("805px", "200px");
		tableCompanies.setStyleName("cellTableHeader");

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> tradeNameColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new String(company.getTradeName()).toString();
			}

		}; // Ende tradeNameColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> topLineColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getTopLine()).toString();
			}
		}; // Ende topLineColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Gewinn
		TextColumn<Company> amountColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new Integer(company.getAmount()).toString();
			}
		}; // Ende amountColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Marktanteil
		TextColumn<Company> marketShareColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Double(company.getMarketShare()).toString();
			}
		}; // Ende marketShareColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen:
			// Produktmenge
		TextColumn<Company> salesVolumeColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Integer(company.getProduct().getSalesVolume())
						.toString();
			}
		}; // Ende salesVolumeColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen:
			// Produktpreis
		TextColumn<Company> productPriceColumn = new TextColumn<Company>() {
			@Override
			public String getValue(final Company company) {
				return new Double(company.getProduct().getPrice()).toString();
			}
		}; // Ende productPriceColumn

		// Unternehmensdaten der Konkurrenzunternehmen anzeigen lassen
		tableCompanies.addColumn(tradeNameColumn, "Firma");
		tableCompanies.addColumn(topLineColumn, "Umsatz");
		tableCompanies.addColumn(amountColumn, "Gewinn");
		tableCompanies.addColumn(marketShareColumn, "Marktanteil");
		tableCompanies.addColumn(productPriceColumn, "Produktpreis");
		tableCompanies.addColumn(salesVolumeColumn, "Absatzmenge");

		// Call
		// Unternehmen
		service.getCompany(new GetCompanyCallback());

	} // Ende method onModuleLoad

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die Daten
	 * der Konkurrenzunternehmen aus der Datenbank zurückgibt
	 * 
	 */
	public class GetCompanyCallback implements AsyncCallback<List<Company>> {
		@Override
		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		@Override
		public final void onSuccess(List<Company> result) {

			ListDataProvider<Company> dataProvider = new ListDataProvider<Company>();
			// Connect the table to the data provider.
			dataProvider.addDataDisplay(tableCompanies);

			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			companyList = dataProvider.getList();

			
			
			for (Company company : result) {
				companyList.add(company);
				if (company.getTopLine() == 0){
					emptyCompanyCounter++;
				}
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
