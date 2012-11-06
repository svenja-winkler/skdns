package de.dhbw.wwi11sca.skdns.client.admin;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die AdminSimulation enthält das Frontend der Adminfunktionen.
 * 
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import de.dhbw.wwi11sca.skdns.client.logout.LogoutSimulation;
import de.dhbw.wwi11sca.skdns.shared.Admin;
import de.dhbw.wwi11sca.skdns.shared.User;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class AdminSimulation implements EntryPoint {

	AbsolutePanel panelAdmin = new AbsolutePanel();

	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	CellTable<User> cellTableUser = new CellTable<User>();

	Button btLogout = new Button("Logout");

	AbsolutePanel absolutePanelUserStats = new AbsolutePanel();
	Label lbExistingUsers = new Label("aktuell angelegte User:");
	Label lbLoginCounter = new Label("0");
	Label lbLogins = new Label("bisherige Anmeldungen (gesamt):");
	Label lbExistingUserCounter = new Label("0");

	AbsolutePanel absolutePanelCreateUser = new AbsolutePanel();
	Label lbCreateNewUser = new Label("neuen User anlegen:");
	TextBox textBoxUsername = new TextBox();
	TextBox textBoxMail = new TextBox();
	TextBox textBoxPassword = new TextBox();
	Button btCreateUser = new Button("neuen User anlegen");
	User newUser;

	AbsolutePanel absolutePanelDeleteUser = new AbsolutePanel();
	TextBox textBoxUsernameDelete = new TextBox();
	Label lbDeleteUser = new Label("User l\u00F6schen:");
	Button btDelete = new Button("L\u00F6schen");
	String deleteUser;
	Boolean alreadyExistingUser = false;

	List<User> userList;
	String changeInfo;

	private RegExp expName = RegExp
			.compile("[a-zA-Z0-9\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6]+");
	private RegExp expMail = RegExp
			.compile("^([a-zA-Z0-9]+)(\u0040)([a-zA-Z0-9]+)(\\.)([a-z]+)$");

	private AdminServiceAsync service = GWT.create(AdminService.class);

	public void onModuleLoad() {
		// RootPanel : root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel : panelAdmin
		root.add(panelAdmin, -10, 0);
		panelAdmin.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelAdmin.add(logo, 30, 30);
		logo.setSize("339px", "100px");

		// Buttons

		// Button Logout: btLogout
		panelAdmin.add(btLogout, 933, 10);
		btLogout.setSize("81px", "30px");

		// Button User erzeugen: btCreateUser
		absolutePanelCreateUser.add(btCreateUser, 243, 135);
		btCreateUser.setSize("173px", "30px");

		// Button User löschen: btDelete
		absolutePanelDeleteUser.add(btDelete, 245, 46);
		btDelete.setSize("173px", "30px");

		// Statistiken
		panelAdmin.add(absolutePanelUserStats, 514, 47);
		absolutePanelUserStats.setStyleName("gwt-Panel-Invest");
		absolutePanelUserStats.setSize("339px", "63px");
		// Labels Summe der User
		absolutePanelUserStats.add(lbExistingUsers, 20, 14);
		absolutePanelUserStats.add(lbExistingUserCounter, 285, 10);
		lbExistingUserCounter.setSize("44px", "18px");
		// Label Summe der Anmeldungen
		absolutePanelUserStats.add(lbLogins, 10, 38);
		absolutePanelUserStats.add(lbLoginCounter, 285, 34);
		lbLoginCounter.setSize("47px", "18px");

		// Usertabelle
		getUserTable();

		// neuen User anlegen
		panelAdmin.add(absolutePanelCreateUser, 547, 239);
		absolutePanelCreateUser.setStyleName("gwt-Panel-Invest");
		absolutePanelCreateUser.setSize("426px", "181px");

		absolutePanelCreateUser.add(lbCreateNewUser, 10, 10);
		lbCreateNewUser.setStyleName("gwt-Home-Label");

		absolutePanelCreateUser.add(textBoxUsername, 10, 42);
		textBoxUsername.setSize("159px", "18px");
		textBoxUsername.setText("Username");
		absolutePanelCreateUser.add(textBoxPassword, 10, 88);
		textBoxPassword.setText("Kennwort");
		absolutePanelCreateUser.add(textBoxMail, 10, 135);
		textBoxMail.setText("E-Mail");

		// User löschen
		panelAdmin.add(absolutePanelDeleteUser, 545, 453);
		absolutePanelDeleteUser.setStyleName("gwt-Panel-Invest");
		absolutePanelDeleteUser.setSize("428px", "98px");

		absolutePanelDeleteUser.add(lbDeleteUser, 10, 10);
		lbDeleteUser.setStyleName("gwt-Home-Label");
		absolutePanelDeleteUser.add(textBoxUsernameDelete, 10, 42);
		textBoxUsernameDelete.setText("Username");

		// Calls
		// Usertabelle befüllen
		service.getUser(new GetUserCallback());
		// Statistiken angeben
		service.getStats(new GetStatsCallback());

		// Eventhandler

		// Eventhandler btDelete
		btDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// User löschen, dessen Name angezeigt wurde
				deleteUser = textBoxUsernameDelete.getText().trim();

				// Überprüfung, ob es sich um den Admin handelt
				if ((deleteUser.toLowerCase()).equals("admin")) {
					Window.alert("Sie k\u00f6nnen den Admin nicht l\u00f6schen!");
				} else {
					service.deleteUser(deleteUser, new DeleteUserCallback());
				} // Ende if-else
			}
		}); // Ende btDelete

		// Eventhandler btCreateUser
		btCreateUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Überprüfung, ob die Eingabe korrekt war
				if (expName.test(textBoxUsername.getText())
						&& expName.test(textBoxPassword.getText())
						&& expMail.test(textBoxMail.getText())) {

					// Überprüfung, ob User schon existiert
					for (User user : userList) {
						if (textBoxUsername.getText()
								.equals(user.getUsername())) {
							alreadyExistingUser = true;
						} // Ende if-Statement
					} // Ende for-Schleife
						// Existiert der User bereits wird eine Meldung
						// ausgegeben
					if (alreadyExistingUser == true) {
						Window.alert("Dieser User existiert bereits.");
						alreadyExistingUser = false;
					} else {
						// existiert der User noch nicht, kann er angelegt
						// werden
						newUser = new User();
						newUser.setUsername(textBoxUsername.getText());
						newUser.setPassword(textBoxPassword.getText());
						newUser.setMail(textBoxMail.getText());
						// Call
						// User speichern
						service.saveUser(newUser, new SaveUserCallback());
					} // Ende if-else(alreadyExistingUser == true)
				} else {
					// Eingabe ist nicht korrekt oder nicht vollständig
					Window.alert("Bitte geben sie alle Daten an!");
				} // Ende if-else (expName.test(textBoxUsername.getText())
			}
		}); // Ende btCreateUser

		// Eventhandler ausloggen
		btLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Logoutoberfläche laden
				RootPanel.get().clear();
				LogoutSimulation logout = new LogoutSimulation();
				logout.onModuleLoad();
			}
		}); // Ende btLogout

		// Eventhandler Username TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit der
		// Admin Daten eingeben kann
		textBoxUsername.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsername.getValue().equals("Username")) {
					textBoxUsername.setText("");
				} // Ende if-Statement

			}
		}); // Ende textBoxUsername

		// Eventhandler Mail TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit der
		// Admin Daten eingeben kann
		textBoxMail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxMail.getValue().equals("E-Mail")) {
					textBoxMail.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername

		// Eventhandler Password TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit der
		// Admin Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxPassword.getValue().equals("Kennwort")) {
					textBoxPassword.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername

		// Eventhandler User löschen TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit
		// der Admin Daten eingeben kann
		textBoxUsernameDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsernameDelete.getValue().equals("Username")) {
					textBoxUsernameDelete.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername
	} // Ende onModuleLoad

	/**
	 * getUserTable lädt die angezeigte Usertabelle
	 */
	private void getUserTable() {
		// die User werden in eine Tabelle geladen und angezeigt
		cellTableUser.setPageSize(30);
		cellTableUser.setSize("489px", "407px");
		panelAdmin.add(cellTableUser, 30, 181);
		cellTableUser.setStyleName("cellTableHeader");

		// Usertabelle mit DB-Usern befüllen: Username
		TextColumn<User> UsernameColumn = new TextColumn<User>() {
			public String getValue(User user) {
				return new String(user.getUsername());
			}
		}; // Ende UsernameColumn

		// Usertabelle mit DB-Usern befüllen: Kennwort
		final TextInputCell passwordCell = new TextInputCell();
		Column<User, String> PasswordColumn = new Column<User, String>(
				passwordCell) {
			public String getValue(User object) {
				return object.getPassword();
			}
		}; // Ende PasswordColumn

		// Usertabelle mit DB-Usern befüllen: E-Mail
		TextColumn<User> EMailColumn = new TextColumn<User>() {
			public String getValue(User user) {
				return new String(user.getMail());
			}
		}; // Ende EMailColumn

		// Usertabelle mit DB-Usern befüllen: forgottenPassword
		List<String> pwdCell = new ArrayList<String>();
		pwdCell.add("true");
		pwdCell.add("false");
		SelectionCell forgottenPasswordCell = new SelectionCell(pwdCell);
		Column<User, String> forgottenPasswordColumn = new Column<User, String>(
				forgottenPasswordCell) {
			public String getValue(User object) {
				return new String(
						new Boolean(object.isForgottenPassword()).toString());
			}
		}; // Ende forgottenPasswordColumn

		// DB-Userdaten anzeigen lassen
		cellTableUser.addColumn(UsernameColumn, "Username");
		cellTableUser.addColumn(PasswordColumn, "Kennwort");
		cellTableUser.addColumn(EMailColumn, "Email");
		cellTableUser.addColumn(forgottenPasswordColumn, "Passwort vergessen");

		// Fieldupdater

		// Fieldupdater PasswortColumn
		PasswordColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			public void update(int index, User object, String value) {
				((User) object).setPassword((String) value);
				object.setForgottenPassword(false);
				// Call
				// Tabelle aktualisieren
				service.updateTable((User) object, new UpdateTableCallback());
				cellTableUser.redraw();
			}
		}); // Ende FieldUpdater PasswordColum

		// Fieldupdater forgottenPasswordColumn
		forgottenPasswordColumn
				.setFieldUpdater(new FieldUpdater<User, String>() {
					@Override
					public void update(int index, User object, String value) {
						((User) object)
								.setForgottenPassword(new Boolean(value));
						// Call
						// Tabelle aktualisieren
						service.updateTable((User) object,
								new UpdateTableCallback());
						cellTableUser.redraw();
					}
				}); // Ende Fieldupdater forgottenPasswordColumn
	} // Ende method getUserTable

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher den neu
	 * erzeugten User in der db speichert
	 * 
	 */
	public class UpdateTableCallback implements AsyncCallback<java.lang.Void> {

		public void onFailure(Throwable caught) {
		} // Ende onFailure

		public void onSuccess(Void result) {
			// War die Passwortänderung erfolgreich, wird dem Admin eine Meldung
			// ausgeworfen
			Window.alert("\u00c4nderung erfolgreich. Informieren Sie den Kunden.");
		} // Ende method onSuccess
	} // Ende class SaveUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Userdaten aus der Datenbank zurückgibt, deren Passwort geändert werden
	 * müssen
	 * 
	 */
	public class GetUserCallback implements AsyncCallback<List<User>> {

		public void onFailure(Throwable caught) {
		} // Ende onFailure

		public final void onSuccess(List<User> result) {
			// Unternehmenstabelle mit dem DataProvider verbinden
			ListDataProvider<User> dataProvider = new ListDataProvider<User>();
			dataProvider.addDataDisplay(cellTableUser);

			// Liste befüllen
			userList = dataProvider.getList();
			for (User user : result) {
				userList.add(user);
			} // Ende for-Schleife
		} // Ende method onSuccess
	} // Ende class GetChangeUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher den neu
	 * erzeugten User in der db speichert
	 * 
	 */
	public class SaveUserCallback implements AsyncCallback<java.lang.Void> {

		public void onFailure(Throwable caught) {
		} // Ende onFailure

		public void onSuccess(Void result) {
			// Wurde der User erfolgreich gespeichert, wird dem User eine
			// Meldung ausgeworfen
			Window.alert("User wurde hinzugef\u00fcgt.");
			// Call
			// User
			service.getUser(new GetUserCallback());
			// Statistiken
			service.getStats(new GetStatsCallback());
		} // Ende method onSuccess
	} // Ende class SaveUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher einen
	 * gewählten User aus der DB löscht
	 * 
	 */
	public class DeleteUserCallback implements AsyncCallback<java.lang.Void> {

		public void onFailure(Throwable caught) {
		} // Ende method onFailure

		public void onSuccess(Void result) {
			// Wurde der User erfolgreich gelöscht, wird dem Admin eine Meldung
			// ausgeworfen
			Window.alert("User wurde entfernt.");
			// Call: User
			service.getUser(new GetUserCallback());
		} // Ende method onSuccess
	} // Ende class DeleteUserCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Anzahl der existierenden User und der bisher insgesamt getätigten Logins
	 * zurückgibt
	 * 
	 */
	public class GetStatsCallback implements AsyncCallback<Admin> {

		public void onFailure(Throwable caught) {
		} // Ende method onFailure

		public void onSuccess(Admin result) {
			// Setzt den Text der StatistikLabel
			lbLoginCounter.setText(result.getLoginCount() + "");
			lbExistingUserCounter.setText(result.getExistingUserCount() + "");
		} // Ende method onSuccess
	} // Ende class GetStatsCallback
} // Ende class AdminSimulation
