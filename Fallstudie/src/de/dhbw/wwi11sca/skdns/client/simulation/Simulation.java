package de.dhbw.wwi11sca.skdns.client.simulation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.client.logout.LogoutSimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.SimulationVersion;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Image;

public class Simulation implements EntryPoint {

	// Panels
	AbsolutePanel absolutePanelSimulation = new AbsolutePanel();
	AbsolutePanel absolutePanelInvestments = new AbsolutePanel();

	// Widgets
	Label lbHome = new Label("Home");
	Label lbSimulation = new Label(">  Simulation");

	Button btSimulation = new Button("Simulation starten");
	Button btNextYear = new Button("Folgejahr");
	Button btLogout = new Button("Logout");

	Label lbInvestments = new Label("Investitionen:");
	Label lbMarketing = new Label("Marketing:");
	Label lbMachine = new Label("Maschinen:");
	Label lbMachineValue = new Label("Wert:");
	Label lbUsedPersonal = new Label("n\u00f6tiges Personal:");
	Label lbMachineCapacity = new Label("Kapazit\u00E4t:");
	Label lbPersonal = new Label("Personal:");
	Label lbPrice = new Label("Produktpreis:");

	IntegerBox integerBoxMarketing = new IntegerBox();
	IntegerBox integerBoxMachineValue = new IntegerBox();
	IntegerBox integerBoxCapacity = new IntegerBox();
	IntegerBox integerBoxMachineStaff = new IntegerBox();
	IntegerBox integerBoxPersonal = new IntegerBox();
	DoubleBox doubleBoxPrice = new DoubleBox();

	ScrollPanel scrollPanelYears = new ScrollPanel();
	TabLayoutPanel tabPanelYears = new TabLayoutPanel(1.5, Unit.EM);
	CellTable<Company> tableCompanies = new CellTable<Company>();
	List<Company> companyList;
	List<Company> companyListSimulation;
	AbsolutePanel[] absolutePanelYear = new AbsolutePanel[1000];

	AbsolutePanel absolutePanelPieChart;
	VerticalPanel verticalPanelColumns;
	VerticalPanel verticalPanelInput;
	AbsolutePanel absolutePanelMarketIncrease;
	Image arrowImage;
	Label lbResults;
	Label lbInvestMarketing;
	Label lbInvestPersonal;
	Label lbInvestPrice;
	Label lbInvestMachineValue;
	Label lbInvestMachinesCapacity;
	Label lbInvestMachinePersonal;
	Label necessaryPersonalInfo;
	Label unusedMachineCapacityInfo;
	Label lbAmount;

	int stackYear = 0;
	int simulationYear = 1;
	int simulationVersion = 1;
	int deleteCounter = 0;

	private SimulationServiceAsync service = GWT
			.create(SimulationService.class);

	public void onModuleLoad() {
		// RootPanel : root
		RootPanel root = RootPanel.get();
		root.setSize("1024", "768");

		// Absolute Panel: absolutePanelSimulation
		root.add(absolutePanelSimulation, 0, 0);
		absolutePanelSimulation.setSize("1024px", "768px");
		lbSimulation.setStyleName("gwt-Label-Info");
		// Informationsfelder

		// Label zurück zur Home: lbHome
		absolutePanelSimulation.add(lbHome, 30, 30);
		lbHome.setStyleName("gwt-Home-Label");
		// Label Simulation : lbSimulation
		absolutePanelSimulation.add(lbSimulation, 71, 30);

		// ScrollPanel, auf dem der TabPanel angebracht wird
		absolutePanelSimulation.add(scrollPanelYears, 60, 401);
		scrollPanelYears.setSize("894px", "300px");

		// TabPanel, auf dem die Ergebnisse der Simulation angezeigt werden
		scrollPanelYears.add(tabPanelYears);
		tabPanelYears.setSize("100%", "300px");

		// Darstellung der Unternehmen
		summaryCompanies();

		// Investitionen
		loadInvestment();

		// Buttons

		// Simulation starten : buttonSimulation
		absolutePanelSimulation.add(btSimulation, 795, 274);
		btSimulation.setSize("127px", "35px");

		// Simulation für das Folgejahr starten : buttonFolgejahr
		absolutePanelSimulation.add(btNextYear, 795, 334);
		btNextYear.setSize("127px", "35px");
		btNextYear.setEnabled(false);

		// Logout : btLogout
		absolutePanelSimulation.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// Eventhandler

		// Simulation starten
		btSimulation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				getInput();

				stackYear++;
				simulationVersion++;
				btNextYear.setEnabled(true);
			}
		}); // Ende btSimulation

		// Simulation Folgejahr starten
		btNextYear.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten aktualisieren
				service.getCompany(companyListSimulation,
						new GetCompanyCallback());
				companyList.clear();
				summaryCompanies();

				// Alle Versionen (außer der letzten) des letzten Jahres werden
				// aus der Ansicht gelöscht:
				for (int i = deleteCounter; i < stackYear - 1; i++) {
					tabPanelYears.remove(absolutePanelYear[i]);
				}

				deleteCounter = stackYear;

				simulationYear++;
				simulationVersion = 1;

				getInput();

				stackYear++;
				simulationVersion++;

			}
		}); // Ende btNextYear

		// zur Home zurückkehren
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				HomeSimulation home = new HomeSimulation();
				home.onModuleLoad();
			}
		}); // Ende lbHome

		// Logout
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				LogoutSimulation logout = new LogoutSimulation();
				logout.onModuleLoad();
			}
		}); // btLogout

	} // Ende onModuleLoad()

	private void getInput() {
		// eine neue Simulationsversion des laufenden Jahres wird
		// angebracht
		absolutePanelYear[stackYear] = new AbsolutePanel();
		absolutePanelYear[stackYear].setSize("892px", "280px");

		SimulationVersion version = new SimulationVersion(simulationYear,
				simulationVersion);

		if (integerBoxMarketing.getValue() != null)
			version.setMarketing(integerBoxMarketing.getValue());
		if (integerBoxMachineValue.getValue() != null)
			version.setMachineValue(integerBoxMachineValue.getValue());
		if (integerBoxCapacity.getValue() != null)
			version.setMachineCapacity(integerBoxCapacity.getValue());
		if (integerBoxMachineStaff.getValue() != null)
			version.setMachineStaff(integerBoxMachineStaff.getValue());
		if (integerBoxPersonal.getValue() != null)
			version.setPersonal(integerBoxPersonal.getValue());
		if (doubleBoxPrice.getValue() != null) {
			version.setPrice(doubleBoxPrice.getValue());
		} else {
			version.setPrice(0.0);
		}

		deleteValueInvestments();

		service.createSimulationCallback(version,
				new CreateSimulationCallback());

	}

	private void deleteValueInvestments() {
		integerBoxMarketing.setValue(null);
		integerBoxMachineValue.setValue(null);
		integerBoxCapacity.setValue(null);
		integerBoxMachineStaff.setValue(null);
		integerBoxPersonal.setValue(null);
		doubleBoxPrice.setValue(null);

	} // Ende method deleteValueInvestments

	public void summaryCompanies() {

		tableCompanies = new CellTable<Company>();
		absolutePanelSimulation.add(tableCompanies, 60, 79);
		tableCompanies.setSize("894px", "156px");
		tableCompanies.setStyleName("cellTableHeader");

		// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> tradeNameColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				return new String(company.getTradeName());

			}

		}; // Ende tradeNameColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Umsatz
		TextColumn<Company> topLineColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				if (company.getTopLine() != 0) {
					return new Integer(company.getTopLine()).toString();
				} else {
					return new String("k.A.");
				}
			}
		}; // Ende topLineColumn
			// Unternehmensdaten der Konkurrenzunternehmen befüllen: Gewinn
		TextColumn<Company> amountColumn = new TextColumn<Company>() {
			@Override
			public String getValue(Company company) {
				if (company.getAmount() != 0) {
					return new Integer(company.getAmount()).toString();
				} else {
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
					return new String("k.A.");
				}
			}
		}; // Ende productPriceColumn

		tableCompanies.addColumn(tradeNameColumn, "Firma");
		tableCompanies.addColumn(topLineColumn, "Umsatz");
		tableCompanies.addColumn(amountColumn, "Gewinn");
		tableCompanies.addColumn(marketShareColumn, "Marktanteil");
		tableCompanies.addColumn(productPriceColumn, "Produktpreis");
		tableCompanies.addColumn(salesVolumeColumn, "Absatzmenge");

		// TODO: Wenn eine Simulation aufgerufen wurde, soll die Liste
		// aktualisiert werden
		// dabei sollen die Daten aus der Liste companyListSimulation genommen
		// werden
		service.getCompany(new GetCompanyCallback());

	} // Ende method summaryCompanies

	private void loadInvestment() {
		// Panel, um die Investitionen zu tätigen : absolutePanelSimulation
		absolutePanelSimulation.add(absolutePanelInvestments, 84, 248);
		absolutePanelInvestments.setSize("650px", "133px");
		absolutePanelInvestments.setStyleName("gwt-Panel-Invest");
		// Labels

		lbInvestments.setStyleName("gwt-UnternehmenLabel");
		absolutePanelInvestments.add(lbInvestments, 10, 10);
		lbInvestments.setSize("282px", "18px");
		// Marketing
		absolutePanelInvestments.add(lbMarketing, 20, 45);
		// Maschinen
		absolutePanelInvestments.add(lbMachine, 14, 73);
		// Maschinen Wert der Maschinen
		absolutePanelInvestments.add(lbMachineValue, 21, 103);
		lbMachineValue.setSize("60px", "18px");
		// Maschinen nötiges Personal
		absolutePanelInvestments.add(lbUsedPersonal, 410, 103);
		// Maschinen Kapazität
		absolutePanelInvestments.add(lbMachineCapacity, 239, 103);
		// Personal
		absolutePanelInvestments.add(lbPersonal, 239, 45);
		// Produktpreis
		absolutePanelInvestments.add(lbPrice, 410, 47);
		lbPrice.setSize("101px", "18px");
		lbPrice.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		// Eingabefelder
		// Marketing
		absolutePanelInvestments.add(integerBoxMarketing, 86, 34);
		integerBoxMarketing.setSize("94px", "25px");
		// Personal
		absolutePanelInvestments.add(integerBoxPersonal, 300, 34);
		integerBoxPersonal.setSize("94px", "25px");
		// Produktpreis
		absolutePanelInvestments.add(doubleBoxPrice, 522, 34);
		doubleBoxPrice.setSize("92px", "23px");
		// Maschinenwert
		absolutePanelInvestments.add(integerBoxMachineValue, 86, 90);
		integerBoxMachineValue.setSize("94px", "25px");
		// Maschinenkapazität
		absolutePanelInvestments.add(integerBoxCapacity, 300, 90);
		integerBoxCapacity.setSize("94px", "25px");
		// Maschinen Anzahl der notwendigen Mitarbeiter
		absolutePanelInvestments.add(integerBoxMachineStaff, 520, 90);
		integerBoxMachineStaff.setSize("94px", "25px");

	} // Ende method loadInvestment

	public void showInput(SimulationVersion result) {
		// Eingegebene Investitionen werden angezeigt
		verticalPanelInput = new VerticalPanel();
		lbResults = new Label("Ihre Eingabe: ");
		lbResults.setStyleName("gwt-Panel-Invest-Inputlabel");
		absolutePanelYear[stackYear - 1].add(lbResults, 10, 10);
		absolutePanelYear[stackYear - 1].add(verticalPanelInput, 10, 34);
		verticalPanelInput.setSize("154px", "18px");

		lbInvestMarketing = new Label("Marketing: " + result.getMarketing());
		lbInvestPersonal = new Label("Personal: " + result.getPersonal());
		lbInvestPrice = new Label("Produktpreis: " + result.getPrice());
		lbInvestMachineValue = new Label("Maschinenwert: "
				+ result.getMachineValue());
		lbInvestMachinesCapacity = new Label("Maschinenkapazit\u00E4t: "
				+ result.getMachineCapacity());
		lbInvestMachinePersonal = new Label("Maschinenpersonal: "
				+ result.getMachineStaff());

		verticalPanelInput.setSize("210px", "236px");
		verticalPanelInput.add(lbInvestMarketing);
		verticalPanelInput.add(lbInvestPersonal);
		verticalPanelInput.add(lbInvestPrice);
		verticalPanelInput.add(lbInvestMachineValue);
		verticalPanelInput.add(lbInvestMachinesCapacity);
		verticalPanelInput.add(lbInvestMachinePersonal);

	}

	public void showPieChart(final List<Company> companyListSimulation) {
		// Marktanteilstorte wird erstellt und angezeigt
		absolutePanelPieChart = new AbsolutePanel();
		absolutePanelYear[stackYear - 1].add(absolutePanelPieChart, 160, 10);
		absolutePanelPieChart.setSize("335px", "260px");

		Runnable onLoadCallback = new Runnable() {
			public void run() {

				PieOptions options = PieOptions.create();
				options.setWidth(325);
				options.setHeight(260);
				options.setColors("#FF9900", "#A3A3A3", "#969696", "#BDBDBD");
				options.set3D(true);
				options.setTitle("Markanteile der Unternehmen");

				// Create a pie chart visualization.
				PieChart pie = new PieChart(
						createPieTable(companyListSimulation), options);

				absolutePanelPieChart.add(pie);
			}
		};

		// Load the visualization api, passing the onLoadCallback to be
		// called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback,
				PieChart.PACKAGE);

	}

	private DataTable createPieTable(List<Company> companies) {

		// Datentabelle erzeugen
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Unternehmen");
		data.addColumn(ColumnType.NUMBER, "Marktanteil");
		data.addRows(companies.size());
		int rowIndex = 0;
		// Datentabelle mit Daten befüllen
		for (Company company : companies) {
			data.setValue(rowIndex, 0, company.getTradeName());
			data.setValue(rowIndex, 1,
					(int) Math.round(company.getMarketShare() * 100));
			rowIndex++;
		}
		return data;
	}

	private void showMarketIncrease(int marketIncrease) {
		absolutePanelMarketIncrease = new AbsolutePanel();
		absolutePanelYear[stackYear - 1].add(absolutePanelMarketIncrease, 339,
				241);
		absolutePanelMarketIncrease.setSize("240px", "29px");

		Label lbMarketIncrease = new Label("Marktwachstum: ");
		absolutePanelMarketIncrease.add(lbMarketIncrease, 0, 0);

		// Marktwachstumspfeil
		if (marketIncrease == -1) {
			arrowImage = new Image("fallstudie/gwt/clean/images/redArrow.png");
		} else if (marketIncrease == 1) {
			arrowImage = new Image("fallstudie/gwt/clean/images/greenArrow.png");
		} else {
			arrowImage = new Image(
					"fallstudie/gwt/clean/images/orangeArrow.png");
		}

		absolutePanelMarketIncrease.add(arrowImage, 102, 0);
		arrowImage.setSize("20px", "20px");

	}

	private void showColumnChart(final List<Company> companyListSimulation) {
		// ColumnChart
		verticalPanelColumns = new VerticalPanel();
		absolutePanelYear[stackYear - 1].add(verticalPanelColumns, 500, 10);
		verticalPanelColumns.setSize("382px", "260px");

		Runnable onLoadCallback = new Runnable() {
			public void run() {

				Options options = CoreChart.createOptions();
				options.setHeight(240);
				options.setWidth(400);
				options.setColors("#FF9900");

				AxisOptions axisOptions = AxisOptions.create();
				axisOptions.setMinValue(0);
				axisOptions.setMaxValue(2000);
				options.setVAxisOptions(axisOptions);

				ColumnChart columnChart = new ColumnChart(
						createColumnTable(companyListSimulation), options);

				verticalPanelColumns.add(columnChart);
			}
		};

		// Load the visualization api, passing the onLoadCallback to be
		// called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback,
				ColumnChart.PACKAGE);
	}

	private DataTable createColumnTable(List<Company> companies) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Unternehmen");
		data.addColumn(ColumnType.NUMBER, "Umsatz");
		data.addRows(companies.size());
		int rowIndex = 0;
		// Datentabelle mit Daten befüllen

		for (Company company : companies) {
			data.setValue(rowIndex, 0, company.getTradeName());
			data.setValue(rowIndex, 1, company.getTopLine());
			rowIndex++;
		}

		return data;
	}

	/**
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher angelegte
	 * Unternehmen zurückgibt
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
			} // Ende for-Schleife

		} // Ende method onSuccess
	} // Ende class GetCompanyCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Berechnungen der Marktsimulation durchführt und eine SimulationVersion
	 * zurückgibt
	 * 
	 */
	public class CreateSimulationCallback implements
			AsyncCallback<SimulationVersion> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende method onFailure

		@Override
		public void onSuccess(SimulationVersion result) {

			tabPanelYears.add(absolutePanelYear[stackYear - 1], "Jahr "
					+ simulationYear + " (" + (simulationVersion - 1) + ")",
					true);
			absolutePanelYear[stackYear - 1].setStyleName("gwt-Panel-results");
			showInput(result);
			// Angezeigte Daten ind er Tabelle neu laden

			companyListSimulation = new ArrayList<Company>();
			companyListSimulation.add(result.getOwnCompany());
			companyListSimulation.add(result.getCompany1());
			companyListSimulation.add(result.getCompany2());
			companyListSimulation.add(result.getCompany3());

			for (Company company : companyListSimulation) {
				if (company.getTopLine() == 0) {
					companyListSimulation.remove(company);
				}
			}
			// PieChart
			showPieChart(companyListSimulation);

			// Marktwachstum
			showMarketIncrease(result.getMarketIncrease());

			// ColumnChart
			showColumnChart(companyListSimulation);

			if (result.getNecessaryPersonal() > 0) {
				necessaryPersonalInfo = new Label("Es fehlen mindestens "
						+ result.getNecessaryPersonal() + " Mitarbeiter.");
				absolutePanelYear[stackYear - 1].add(necessaryPersonalInfo,
						105, 6);
				necessaryPersonalInfo.setStyleName("gwt-Infolabel");

			}
			if (result.isUnusedMachineCapacity() == true) {

				unusedMachineCapacityInfo = new Label(
						"M\u00f6chten Sie noch eine Maschine kaufen? Sie k\u00f6nnten dadurch Ihr Betriebergebnis steigern!");
				absolutePanelYear[stackYear - 1].add(unusedMachineCapacityInfo,
						105, 18);
				unusedMachineCapacityInfo.setStyleName("gwt-Infolabel");
			}

			lbAmount = new Label("Gewinn des eigenen Unternehmen: "
					+ result.getOwnCompany().getAmount());
			absolutePanelYear[stackYear - 1].add(lbAmount, 516, 251);
			lbAmount.setStyleName("gwt-Panel-Invest-Inputlabel");

		} // Ende method onSuccess

	} // Ende class CreateSimulationCallback
} // Ende class Simulation