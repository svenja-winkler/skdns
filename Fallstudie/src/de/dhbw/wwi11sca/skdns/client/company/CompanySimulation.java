package de.dhbw.wwi11sca.skdns.client.company;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 * Die CompanySimulation enth�lt das Frontend, 
 * auf welchem Unternehmen angelegt und ver�ndert werden k�nnen.
 * 
 */
import java.util.List;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import de.dhbw.wwi11sca.skdns.client.logout.LogoutSimulation;
import de.dhbw.wwi11sca.skdns.shared.Company;
import de.dhbw.wwi11sca.skdns.shared.Machines;
import de.dhbw.wwi11sca.skdns.shared.OwnCompany;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

public class CompanySimulation implements EntryPoint {

	// Allgemeine Panel
	private AbsolutePanel absolutePanelCreate = new AbsolutePanel();
	private TabLayoutPanel tabPanelCreateCompanies = new TabLayoutPanel(1.5,
			Unit.EM);

	// Widgets
	private Label lbHome = new Label("Home");
	private Label lbCreate = new Label("> Unternehmen anlegen");
	private Button btLogout = new Button("Logout");

	private AbsolutePanel absolutePanelOwnCompany = new AbsolutePanel();
	private Label lbTradeName = new Label("Firma:");
	private Label lbTopLineOwnCompany = new Label("Umsatz:");
	private Label lbMarketShareOwnCompany = new Label("Marktanteil:");
	private Label lbFixedCosts = new Label("Fixkosten:");
	private Label lbProductPriceOwnCompany = new Label("Produktpreis:");
	private Label lbVariableCosts = new Label("variable St\u00FCckkosten:");
	private Label lbNumberOfStaff = new Label("Anzahl Mitarbeiter:");
	private Label lbSalaryOfStaff = new Label("durch. Mitarbeitergehalt:");
	private TextBox textBoxTradeName = new TextBox();
	private TextBox textBoxTopLineOwnCompany = new TextBox();
	private TextBox textBoxMarketShareOwnCompany = new TextBox();
	private TextBox textBoxFixedCosts = new TextBox();
	private TextBox textBoxProductPriceOwnCompany = new TextBox();
	private TextBox textBoxVariableCosts = new TextBox();
	private TextBox textBoxNumberOfStaff = new TextBox();
	private TextBox textBoxSalaryOfStaff = new TextBox();
	private Button btDeleteOwnCompany = new Button("L\u00F6schen");
	private Button btSaveOwnCompany = new Button("\u00DCbernehmen");
	private CellTable<Machines> cellTableMachines;
	private List<Machines> machinesOwnCompany;

	private AbsolutePanel absolutePanelCompany1 = new AbsolutePanel();
	private Label lbTopLineCompany1 = new Label("Umsatz:");
	private Label lbMarketShareCompany1 = new Label("Marktanteil:");
	private Label lbProductPriceCompany1 = new Label("Produktpreis:");
	private TextBox textBoxTopLineCompany1 = new TextBox();
	private TextBox textBoxMarketShareCompany1 = new TextBox();
	private TextBox textBoxProductPriceCompany1 = new TextBox();
	private Button btDeleteCompany1 = new Button("L\u00F6schen");
	private Button btSaveCompany1 = new Button("\u00DCbernehmen");

	private AbsolutePanel absolutePanelCompany2 = new AbsolutePanel();
	private Label lbTopLineCompany2 = new Label("Umsatz:");
	private Label lbMarketShareCompany2 = new Label("Marktanteil:");
	private Label lbProductPriceCompany2 = new Label("Produktpreis:");
	private TextBox textBoxTopLineCompany2 = new TextBox();
	private TextBox textBoxMarketShareCompany2 = new TextBox();
	private TextBox textBoxProductPriceCompany2 = new TextBox();
	private Button btDeleteCompany2 = new Button("L\u00F6schen");
	private Button btSaveCompany2 = new Button("\u00DCbernehmen");

	private AbsolutePanel absolutePanelCompany3 = new AbsolutePanel();
	private Label lbTopLineCompany3 = new Label("Umsatz:");
	private Label lbMarketShareCompany3 = new Label("Marktanteil:");
	private Label lbProductPriceCompany3 = new Label("Produktpreis:");
	private TextBox textBoxTopLineCompany3 = new TextBox();
	private TextBox textBoxMarketShareCompany3 = new TextBox();
	private TextBox textBoxProductPriceCompany3 = new TextBox();
	private Button btDeleteCompany3 = new Button("L\u00F6schen");
	private Button btSaveCompany3 = new Button("\u00DCbernehmen");

	private OwnCompany ownCom;
	private List<Company> companies;
	private RegExp expTradeName = RegExp
			.compile("^([\u00C4\u00DC\u00D6A-Z])[0-9a-z\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6A-Z\\s]*");
	private RegExp expInteger = RegExp.compile("^(\\d*)$");
	private RegExp expPeriod = RegExp.compile(".");
	private RegExp expDouble = RegExp.compile("^([1-9]\\d*|0)(\\.\\d)?$");

	private CompanyServiceAsync service = GWT.create(CompanyService.class);

	@Override
	public void onModuleLoad() {

		// AbsolutePanel : absolutePanelCreate
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(absolutePanelCreate, 0, 0);
		absolutePanelCreate.setSize("1024px", "768px");

		// Informationsfelder

		// Label zur�ck zur Home : lbHome
		absolutePanelCreate.add(lbHome, 30, 30);
		lbHome.setStyleName("gwt-Home-Label");
		// Label Unternehmen anlegen: lbCreate
		absolutePanelCreate.add(lbCreate, 80, 30);
		lbCreate.setStyleName("gwt-Label-Info");

		// Buttons
		absolutePanelCreate.add(btLogout, 914, 10);
		btLogout.setSize("100px", "35px");

		// TabPanel, auf dem die Unternehmen angezeigt werden :
		// tabPanelCreateCompanies
		absolutePanelCreate.add(tabPanelCreateCompanies, 90, 78);
		tabPanelCreateCompanies.setSize("844px", "605px");

		// Eventhandler
		lbHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if ((ownCom.getMarketShare()
						+ companies.get(0).getMarketShare()
						+ companies.get(1).getMarketShare() + companies.get(2)
						.getMarketShare()) == 100) {

					RootPanel.get().clear();
					HomeSimulation home = new HomeSimulation();
					home.onModuleLoad();

				} else {
					Window.alert("Der Marktanteil aller Unternehmen muss zusammen 100 betragen!");
				}

			}
		}); // Ende lbHome

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				LogoutSimulation logout = new LogoutSimulation();
				logout.onModuleLoad();
			}
		});
		
		// Maschinen
		addCellTableMachines();
		
		Button btAddMachines = new Button("+");
		absolutePanelOwnCompany.add(btAddMachines, 677, 273);
		btAddMachines.setSize("30px", "30px");
				
		Button btDeleteMachines = new Button("-");
		absolutePanelOwnCompany.add(btDeleteMachines, 713, 273);
		btDeleteMachines.setSize("30px", "30px");
				
		btAddMachines.addClickHandler(new ClickHandler() {
					
				@Override
				public void onClick(ClickEvent event) {
					Machines newMachine = new Machines();
					machinesOwnCompany.add(newMachine);					
				}
		});
		btDeleteMachines.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				machinesOwnCompany.remove(machinesOwnCompany.size()-1)	;			
			}
	});
	
		// AbsolutePanels f�r die Unternehmen anbringen
		// Tab f�r das eigene Unternehmen
		addTabPanelOwnCompany();
		// Tab f�r das erste Unternehmen
		addTabPanelCompanyOne();
		// Tab f�r das zweite Unternehmen
		addTabPanelCompanyTwo();
		// tab f�r das dritte Unternehmen
		addTabPanelCompanyThree();

		// Asynchroner Call: Falls Daten vorhanden sind, aus der Datenbank
		// auslesen, ansonsten Felder im TabPanelUnternehmenAnlegen frei lassen
		// service.getEigenesUnternehmen(new GetEigenesUnternehmenCallback());

		startCall();

	} // Ende onModuleLoad

	private void addTabPanelOwnCompany() {
		absolutePanelOwnCompany.setSize("100%", "586px");
		tabPanelCreateCompanies.add(absolutePanelOwnCompany,
				"Eigenes Unternehmen", false);
		absolutePanelOwnCompany.setStyleName("gwt-Panel");
		// Buttons
		// Unternehmensdaten l�schen
		absolutePanelOwnCompany.add(btDeleteOwnCompany, 718, 541);
		btDeleteOwnCompany.setSize("100px", "35px");
		// Unternehmensdaten speichern
		absolutePanelOwnCompany.add(btSaveOwnCompany, 612, 541);
		btSaveOwnCompany.setSize("100px", "35px");

		// Eventhandler
		// Unternehmen l�schen
		btDeleteOwnCompany.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO: Meldung: Sind sie sich sicher
				textBoxTradeName.setText(null);
				textBoxTopLineOwnCompany.setValue(null);
				textBoxMarketShareOwnCompany.setValue(null);
				textBoxProductPriceOwnCompany.setValue(null);
				textBoxVariableCosts.setValue(null);
				textBoxNumberOfStaff.setValue(null);
				textBoxSalaryOfStaff.setValue(null);
				// TODO Maschinen in Oberfl�che l�schen
				// TODO EigenesUnternehmen aus DB l�schen
			}
		}); // Ende btDeleteOwnCompany
		// Unternehmen speichern
		btSaveOwnCompany.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (expTradeName.test(textBoxTradeName.getText())
						&& expInteger.test(textBoxTopLineOwnCompany.getText())
						&& expDouble.test(textBoxMarketShareOwnCompany
								.getText())
						&& expDouble.test(textBoxFixedCosts.getText())
						&& expInteger.test(textBoxNumberOfStaff.getText())
						&& expInteger.test(textBoxSalaryOfStaff.getText())
						&& expDouble.test(textBoxProductPriceOwnCompany
								.getText())
						&& expDouble.test(textBoxVariableCosts.getText())

				) {
					ownCom.setTradeName(textBoxTradeName.getText());
					ownCom.setTopLine(new Integer(textBoxTopLineOwnCompany
							.getText()));
					ownCom.setMarketShare(new Double(
							textBoxMarketShareOwnCompany.getText()));
					ownCom.setFixedCosts(new Double(textBoxFixedCosts.getText()));
					ownCom.setNumberOfStaff(new Integer(textBoxNumberOfStaff
							.getText()));
					ownCom.setSalaryStaff(new Integer(textBoxSalaryOfStaff
							.getText()));
					ownCom.getProduct()
							.setPrice(
									new Double(textBoxProductPriceOwnCompany
											.getText()));
					ownCom.setVariableCosts(new Double(textBoxVariableCosts
							.getText()));

					// ownCom.getProduct().setSalesVolume(
					// new Integer(textBoxVariableCosts.getText()));

					service.addOwnCompany(ownCom, new AddOwnCompanyCallback());

				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");

				}

			}
		}); // Ende btSaveOwnCompany

		// Elemente
		// Firma
		absolutePanelOwnCompany.add(lbTradeName, 41, 48);
		absolutePanelOwnCompany.add(textBoxTradeName, 136, 36);
		// Umsatz
		absolutePanelOwnCompany.add(lbTopLineOwnCompany, 41, 105);
		absolutePanelOwnCompany.add(textBoxTopLineOwnCompany, 136, 93);
		textBoxTopLineOwnCompany.setSize("162px", "24px");
		// Marktanteil
		absolutePanelOwnCompany.add(lbMarketShareOwnCompany, 41, 159);
		absolutePanelOwnCompany.add(textBoxMarketShareOwnCompany, 136, 147);
		textBoxMarketShareOwnCompany.setSize("161px", "24px");
		// Fixkosten
		absolutePanelOwnCompany.add(lbFixedCosts, 41, 211);
		absolutePanelOwnCompany.add(textBoxFixedCosts, 136, 199);
		textBoxFixedCosts.setSize("161px", "24px");
		// Produktpreis
		absolutePanelOwnCompany.add(lbProductPriceOwnCompany, 432, 159);
		absolutePanelOwnCompany.add(textBoxProductPriceOwnCompany, 577, 147);
		textBoxProductPriceOwnCompany.setSize("161px", "24px");
		// Absatzmenge
		absolutePanelOwnCompany.add(lbVariableCosts, 432, 211);
		absolutePanelOwnCompany.add(textBoxVariableCosts, 577, 199);
		textBoxVariableCosts.setSize("161px", "24px");
		// Anzahl Mitarbeiter
		absolutePanelOwnCompany.add(lbNumberOfStaff, 432, 48);
		absolutePanelOwnCompany.add(textBoxNumberOfStaff, 577, 36);
		textBoxNumberOfStaff.setSize("161px", "24px");
		// durchschnittliches Mitarbeitergehalt
		absolutePanelOwnCompany.add(lbSalaryOfStaff, 432, 105);
		absolutePanelOwnCompany.add(textBoxSalaryOfStaff, 577, 93);
		textBoxSalaryOfStaff.setSize("161px", "24px");
	} // Ende method addTabPanelOwnCompany
	private void addCellTableMachines(){
		// Maschinen
		cellTableMachines = new CellTable<Machines>();
		absolutePanelOwnCompany.add(cellTableMachines, 88, 277);
		cellTableMachines.setSize("656px", "100px");
		cellTableMachines.setStyleName("cellTableHeader");


		final EditTextCell serviceLifeCell = new EditTextCell();
		Column<Machines, String> serviceLifeColumn = new Column<Machines, String>(
				serviceLifeCell){

					@Override
					public String getValue(Machines object) {
						return new Integer(object.getServiceLife()).toString();
					}
			
		};
		serviceLifeColumn.setFieldUpdater(new FieldUpdater<Machines, String>() {
			@Override
			public void update(int index, Machines object, String value) {
			
				if( expInteger.test(value)){
					ownCom.getMachines().setServiceLife(new Integer(value));
					cellTableMachines.redraw();
				}else{
					Window.alert("Die Eingabe ist ung\u00FCltig und wird nicht gespeichert!");					
				}
				
			}
		});//Ende serviceLifeColumn
		
		final EditTextCell capacityCell = new EditTextCell();
		Column<Machines, String> capacityColumn = new Column<Machines, String>(
				capacityCell){

					@Override
					public String getValue(Machines object) {
						return new Integer(object.getCapacity()).toString();
					}
			
		};
		capacityColumn.setFieldUpdater(new FieldUpdater<Machines, String>() {
			@Override
			public void update(int index, Machines object, String value) {
			
				if( expInteger.test(value)){
					ownCom.getMachines().setCapacity(new Integer(value));
					cellTableMachines.redraw();
				}else{
					Window.alert("Die Eingabe ist ung\u00FCltig und wird nicht gespeichert!");					
				}
				
			}
		});	//Ende capacityColumn
		
		final EditTextCell accountingValueCell = new EditTextCell();
		Column<Machines, String> accountingValueColumn = new Column<Machines, String>(
				accountingValueCell){

					@Override
					public String getValue(Machines object) {
						return new Double(object.getAccountingValue()).toString();
					}
			
		};
		accountingValueColumn.setFieldUpdater(new FieldUpdater<Machines, String>() {
			@Override
			public void update(int index, Machines object, String value) {
			
				if( expDouble.test(value)){
					ownCom.getMachines().setAccountingValue(new Double(value));
					cellTableMachines.redraw();
				}else{
					Window.alert("Die Eingabe ist ung\u00FCltig und wird nicht gespeichert!");					
				}
				
			}
		});	//Ende accountingValueColumn
		
		final EditTextCell staffCell = new EditTextCell();
		Column<Machines, String> staffColumn = new Column<Machines, String>(
				staffCell){

					@Override
					public String getValue(Machines object) {
						return new Integer(object.getStaff()).toString();
					}
			
		};
		staffColumn.setFieldUpdater(new FieldUpdater<Machines, String>() {
			@Override
			public void update(int index, Machines object, String value) {
			
				if( expInteger.test(value)){
					ownCom.getMachines().setStaff(new Integer(value));
					cellTableMachines.redraw();
				}else{
					Window.alert("Die Eingabe ist ung\u00FCltig und wird nicht gespeichert!");					
				}
				
			}
		});	//Ende staffColumn
		
				cellTableMachines.addColumn(serviceLifeColumn,
						"bisherige Nutzungsdauer");
				cellTableMachines.addColumn(capacityColumn, "Kapazit\u00e4t");
				cellTableMachines.addColumn(accountingValueColumn, "Preis");
				cellTableMachines.addColumn(staffColumn, "Notwendige Mitarbeiter");
		
	}
	private void addTabPanelCompanyOne() {
		// Tab f�r Unternehmen 1 anbringen
		absolutePanelCompany1.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany1, "Unternehmen 1",
				false);
		absolutePanelCompany1.setStyleName("gwt-Panel");
		// Buttons
		absolutePanelCompany1.add(btDeleteCompany1, 416, 173);
		btDeleteCompany1.setSize("100px", "35px");

		absolutePanelCompany1.add(btSaveCompany1, 233, 173);
		btSaveCompany1.setSize("100px", "35px");

		// Eventhandler
		// Unternehmen l�schen
		btDeleteCompany1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 1 aus DB l�schen
				textBoxTopLineCompany1.setValue(null);
				textBoxMarketShareCompany1.setValue(null);
				textBoxProductPriceCompany1.setValue(null);
			}
		}); // Ende btDeleteCompany1
		// Unternehmen speichern
		btSaveCompany1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (expInteger.test(textBoxTopLineCompany1.getText())
						&& expDouble.test(textBoxMarketShareCompany1.getText())
						&& expDouble.test(textBoxProductPriceCompany1.getText())

				) {
					companies.get(0).setTopLine(
							new Integer(textBoxTopLineCompany1.getText()));
					companies.get(0).setMarketShare(
							new Double(textBoxMarketShareCompany1.getText()));
					companies
							.get(0)
							.getProduct()
							.setPrice(
									new Double(textBoxProductPriceCompany1
											.getText()));

					service.addCompany(companies.get(0),
							new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else

			}
		}); // Ende btSaveCompany1

		// Elemente
		// Umsatz
		absolutePanelCompany1.add(lbTopLineCompany1, 41, 48);
		absolutePanelCompany1.add(textBoxTopLineCompany1, 136, 36);
		textBoxTopLineCompany1.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany1.add(lbMarketShareCompany1, 432, 48);
		absolutePanelCompany1.add(textBoxMarketShareCompany1, 577, 36);
		textBoxMarketShareCompany1.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany1.add(lbProductPriceCompany1, 41, 104);
		absolutePanelCompany1.add(textBoxProductPriceCompany1, 136, 94);
		textBoxProductPriceCompany1.setSize("161px", "22px");

	} // Ende method addTabPanelCompanyOne

	private void addTabPanelCompanyTwo() {
		// Tab f�r Unternehmen 2 anbringen
		absolutePanelCompany2.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany2, "Unternehmen 2",
				false);
		absolutePanelCompany2.setStyleName("gwt-Panel");
		// Buttons
		absolutePanelCompany2.add(btDeleteCompany2, 416, 173);
		btDeleteCompany2.setSize("100px", "35px");

		absolutePanelCompany2.add(btSaveCompany2, 233, 173);
		btSaveCompany2.setSize("100px", "35px");
		// Eventhandler
		// Unternehmen l�schen
		btDeleteCompany2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 2 aus DB l�schen
				textBoxTopLineCompany2.setValue(null);
				textBoxMarketShareCompany2.setValue(null);
				textBoxProductPriceCompany2.setValue(null);
			}
		}); // Ende btDeleteCompany2
		// Unternehmen speichern
		btSaveCompany2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (expInteger.test(textBoxTopLineCompany2.getText())
						&& expDouble.test(textBoxMarketShareCompany2.getText())
						&& expDouble.test(textBoxProductPriceCompany2.getText())

				) {
					companies.get(1).setTopLine(
							new Integer(textBoxTopLineCompany2.getText()));
					companies.get(1).setMarketShare(
							new Double(textBoxMarketShareCompany2.getText()));
					companies
							.get(1)
							.getProduct()
							.setPrice(
									new Double(textBoxProductPriceCompany2
											.getText()));
					service.addCompany(companies.get(1),
							new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else
			}
		}); // Ende btSaveCompany2

		// Elemente
		// Umsatz
		absolutePanelCompany2.add(lbTopLineCompany2, 41, 48);
		absolutePanelCompany2.add(textBoxTopLineCompany2, 136, 36);
		textBoxTopLineCompany2.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany2.add(lbMarketShareCompany2, 432, 48);
		absolutePanelCompany2.add(textBoxMarketShareCompany2, 577, 36);
		textBoxMarketShareCompany2.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany2.add(lbProductPriceCompany2, 41, 104);
		absolutePanelCompany2.add(textBoxProductPriceCompany2, 136, 94);
		textBoxProductPriceCompany2.setSize("161px", "22px");
	} // Ende method addTabPanelCompany2

	private void addTabPanelCompanyThree() {
		// Tab f�r Unternehmen 3 anbringen
		absolutePanelCompany3.setSize("100%", "596");
		tabPanelCreateCompanies.add(absolutePanelCompany3, "Unternehmen 3",
				false);
		absolutePanelCompany3.setStyleName("gwt-Panel");
		// Buttons
		absolutePanelCompany3.add(btDeleteCompany3, 416, 173);
		btDeleteCompany3.setSize("100px", "35px");
		absolutePanelCompany3.add(btSaveCompany3, 233, 173);
		btSaveCompany3.setSize("100px", "35px");
		// Eventhandler
		// Unternehmen l�schen
		btDeleteCompany3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO Daten Unternehmen 3 aus DB l�schen
				textBoxTopLineCompany3.setValue(null);
				textBoxMarketShareCompany3.setValue(null);
				textBoxProductPriceCompany3.setValue(null);
			}
		}); // Ende btDeleteCompany3
		// Unternehmen speichern
		btSaveCompany3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (expInteger.test(textBoxTopLineCompany3.getText())
						&& expDouble.test(textBoxMarketShareCompany3.getText())
						&& expDouble.test(textBoxProductPriceCompany3.getText())) {
					companies.get(2).setTopLine(
							new Integer(textBoxTopLineCompany3.getText()));
					companies.get(2).setMarketShare(
							new Double(textBoxMarketShareCompany3.getText()));
					companies
							.get(2)
							.getProduct()
							.setPrice(
									new Double(textBoxProductPriceCompany3
											.getText()));
					service.addCompany(companies.get(2),
							new AddCompanyCallback());
				} else {
					Window.alert("Bitte Eingabe \u00FCberpr\u00FCfen");
				} // Ende if-else
			}
		}); // Ende btSaveCompany3

		// Elemente
		// Umsatz
		absolutePanelCompany3.add(lbTopLineCompany3, 41, 48);
		absolutePanelCompany3.add(textBoxTopLineCompany3, 136, 36);
		textBoxTopLineCompany3.setSize("161", "24");
		// Marktanteil
		absolutePanelCompany3.add(lbMarketShareCompany3, 432, 48);
		absolutePanelCompany3.add(textBoxMarketShareCompany3, 577, 36);
		textBoxMarketShareCompany3.setSize("161", "24");
		// Produktpreis
		absolutePanelCompany3.add(lbProductPriceCompany3, 41, 104);
		absolutePanelCompany3.add(textBoxProductPriceCompany3, 136, 94);
		textBoxProductPriceCompany3.setSize("161px", "22px");
	} // Ende method addTabPanelCompanyThree

	private void startCall() {
		service.getOwnCompany(new GetOwnCompanyCallback());
		service.getCompany(new GetCompanyCallback());
	} // Ende method startCall

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher ein
	 * angelegtes eigenes Unternehmen speichert
	 * 
	 */
	public class AddOwnCompanyCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			Window.alert("Das eigene Unternehmen wurde aktualisiert");
		} // Ende method onSuccess
	} // Ende class AddOwnCompanyCallback

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher ein
	 * angelegtes Unternehmen speichert
	 * 
	 */
	public class AddCompanyCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(Void result) {
			Window.alert("Das Unternehmen wurde aktualisiert");
		} // Ende method onSuccess
	} // Ende class AddCompanyCallback

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher ein
	 * bereits angelegtes eigenes Unternehmen aus der Datenbank zur�ckgibt
	 * 
	 */
	public class GetOwnCompanyCallback implements AsyncCallback<OwnCompany> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(OwnCompany result) {

			ownCom = new OwnCompany();
			ownCom = result;

			ListDataProvider<Machines> dataOwnProvider = new ListDataProvider<Machines>();
			// Connect the table to the data provider.
			dataOwnProvider.addDataDisplay(cellTableMachines);
			// Add the data to the data provider, which automatically pushes it
			// to the widget.
			
			machinesOwnCompany = dataOwnProvider.getList();
			//Wenn es mehre Maschinen gibt:
//			Machines machines = result.getMachines();
//			for(OwnCompany ownCom: machines){
//				machinesOwnCompany.add(ownCom.getMachines());	
//			}
			//sonst:
			machinesOwnCompany.add(ownCom.getMachines());

			textBoxTradeName.setText(result.getTradeName());
			textBoxTopLineOwnCompany.setText(new Integer(result.getTopLine())
					.toString());
			textBoxMarketShareOwnCompany.setText(new Double(result
					.getMarketShare()).toString());
			textBoxFixedCosts.setText(new Double(result.getFixedCosts())
					.toString());
			textBoxProductPriceOwnCompany.setText(new Double(result
					.getProduct().getPrice()).toString());
			textBoxVariableCosts.setText(new Double(result.getVariableCosts())
					.toString());
			textBoxNumberOfStaff.setText(new Integer(result.getNumberOfStaff())
					.toString());
			textBoxSalaryOfStaff.setText(new Integer(result.getSalaryStaff())
					.toString());
		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback

	/**
	 * 
	 * Klasse, die f�r den Asynchronen Callback zust�ndig ist, welcher bereits
	 * angelegte Unternehmen aus der Datenbank zur�ckgibt
	 * 
	 */
	public class GetCompanyCallback implements AsyncCallback<List<Company>> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende onFailure

		@Override
		public void onSuccess(List<Company> result) {
			// Anzeige der Unternehmen mit Daten aus Mongodb f�llen
			companies = result;
			textBoxTopLineCompany1.setText(new Integer(result.get(0)
					.getTopLine()).toString());
			textBoxMarketShareCompany1.setValue(new Double(result.get(0)
					.getMarketShare()).toString());
			textBoxProductPriceCompany1.setValue(new Double(result.get(0)
					.getProduct().getPrice()).toString());

			textBoxTopLineCompany2.setValue(new Integer(result.get(1)
					.getTopLine()).toString());
			textBoxMarketShareCompany2.setValue(new Double(result.get(1)
					.getMarketShare()).toString());
			textBoxProductPriceCompany2.setValue(new Double(result.get(1)
					.getProduct().getPrice()).toString());
			textBoxTopLineCompany3.setValue(new Integer(result.get(2)
					.getTopLine()).toString());
			textBoxMarketShareCompany3.setValue(new Double(result.get(2)
					.getMarketShare()).toString());
			textBoxProductPriceCompany3.setValue(new Double(result.get(2)
					.getProduct().getPrice()).toString());
		} // Ende method onSuccess
	} // Ende class GetOwnCompanyCallback

} // Ende class CompanySimulation