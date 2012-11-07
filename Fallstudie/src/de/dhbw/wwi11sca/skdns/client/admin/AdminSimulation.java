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
	
	//Constants
	private static final int PANELADMINX = -10;
	private static final int LOGOX = 30;
	private static final int LOGOY = 30;
	private static final int BTLOGOUTX = 933;
	private static final int BTLOGOUTY = 10;
	private static final int BTCREATEUSERX = 243;
	private static final int BTCREATEUSERY = 135;
	private static final int BTDELETEX = 245;
	private static final int BTDELETEY = 46;
	private static final int USERSTATSX = 514;
	private static final int USERSTATSY = 47;
	private static final int LBEXUSERSX = 20;
	private static final int LBEXUSERSY = 14;
	private static final int LBEXUSERSCNTX = 285;
	private static final int LBEXUSERSCNTY = 10;
	private static final int LBLOGINSX = 10;
	private static final int LBLOGINSY = 38;
	private static final int LBLOGINCNTX = 285;
	private static final int LBLOGINCNTY = 34;
	private static final int PANCREATEUSERX = 547;
	private static final int PANCREATEUSERY = 239;
	private static final int LBCREATEUSERX = 10;
	private static final int LBCREATEUSERY = 10;
	private static final int TBOXUSERX = 10;
	private static final int TBOXUSERY = 42;
	private static final int TBOXPWDX = 10;
	private static final int TBOXPWDY = 88;
	private static final int TBOXMAILX = 10;
	private static final int TBOXMAILY = 135;
	private static final int PANDELETEUSERX = 545;
	private static final int PANDELETEUSERY = 453;
	private static final int LBDELETEUSERX = 10;
	private static final int LBDELETEUSERY = 10;
	private static final int TBOXUSERDELETEX = 10;
	private static final int TBOXUSERDELETEY = 42;
	private static final int CELLTBLSIZE = 30;
	private static final int CELLTBLUSERX = 30;
	private static final int CELLTBLUSERY = 181;

	private AbsolutePanel panelAdmin = new AbsolutePanel();
	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private CellTable<User> cellTableUser = new CellTable<User>();
	private Button btLogout = new Button("Logout");
	private AbsolutePanel absolutePanelUserStats = new AbsolutePanel();
	private Label lbExistingUsers = new Label("aktuell angelegte User:");
	private Label lbLoginCounter = new Label("0");
	private Label lbLogins = new Label("bisherige Anmeldungen (gesamt):");
	private Label lbExistingUserCounter = new Label("0");

	private AbsolutePanel absolutePanelCreateUser = new AbsolutePanel();
	private Label lbCreateNewUser = new Label("neuen User anlegen:");
	private TextBox textBoxUsername = new TextBox();
	private TextBox textBoxMail = new TextBox();
	private TextBox textBoxPassword = new TextBox();
	private Button btCreateUser = new Button("neuen User anlegen");
	private User newUser;

	private AbsolutePanel absolutePanelDeleteUser = new AbsolutePanel();
	private TextBox textBoxUsernameDelete = new TextBox();
	private Label lbDeleteUser = new Label("User l\u00F6schen:");
	private Button btDelete = new Button("L\u00F6schen");
	private String deleteUser;
	private Boolean alreadyExistingUser = false;

	private List<User> userList;
	private RegExp expName = RegExp
			.compile("[a-zA-Z0-9\u00E4\u00FC\u00F6\u00C4\u00DC\u00DF\u00D6]+");
	private RegExp expMail = RegExp
			.compile("^([a-zA-Z0-9]+)(\u0040)([a-zA-Z0-9]+)(\\.)([a-z]+)$");

	private AdminServiceAsync service = GWT.create(AdminService.class);

	public final void onModuleLoad() {
		// RootPanel : root
		RootPanel root = RootPanel.get();
		root.setSize("1024px", "768px");

		// AbsolutePanel : panelAdmin
		root.add(panelAdmin, PANELADMINX, 0);
		panelAdmin.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelAdmin.add(logo, LOGOX, LOGOY);
		logo.setSize("339px", "100px");

		// Buttons

		// Button Logout: btLogout
		panelAdmin.add(btLogout, BTLOGOUTX, BTLOGOUTY);
		btLogout.setSize("81px", "30px");

		// Button User erzeugen: btCreateUser
		absolutePanelCreateUser.add(btCreateUser, BTCREATEUSERX, BTCREATEUSERY);
		btCreateUser.setSize("173px", "30px");

		// Button User löschen: btDelete
		absolutePanelDeleteUser.add(btDelete, BTDELETEX, BTDELETEY);
		btDelete.setSize("173px", "30px");

		// Statistiken
		panelAdmin.add(absolutePanelUserStats, USERSTATSX, USERSTATSY);
		absolutePanelUserStats.setStyleName("gwt-Panel-Invest");
		absolutePanelUserStats.setSize("339px", "63px");
		// Labels Summe der User
		absolutePanelUserStats.add(lbExistingUsers, LBEXUSERSX, LBEXUSERSY);
		absolutePanelUserStats.add(lbExistingUserCounter, LBEXUSERSCNTX,
				LBEXUSERSCNTY);
		lbExistingUserCounter.setSize("44px", "18px");
		// Label Summe der Anmeldungen
		absolutePanelUserStats.add(lbLogins, LBLOGINSX, LBLOGINSY);
		absolutePanelUserStats.add(lbLoginCounter, LBLOGINCNTX, LBLOGINCNTY);
		lbLoginCounter.setSize("47px", "18px");

		// Usertabelle
		getUserTable();

		// neuen User anlegen
		panelAdmin.add(absolutePanelCreateUser, PANCREATEUSERX, PANCREATEUSERY);
		absolutePanelCreateUser.setStyleName("gwt-Panel-Invest");
		absolutePanelCreateUser.setSize("426px", "181px");

		absolutePanelCreateUser.add(lbCreateNewUser, LBCREATEUSERX,
				LBCREATEUSERY);
		lbCreateNewUser.setStyleName("gwt-Home-Label");

		absolutePanelCreateUser.add(textBoxUsername, TBOXUSERX, TBOXUSERY);
		textBoxUsername.setSize("159px", "18px");
		textBoxUsername.setText("Username");
		absolutePanelCreateUser.add(textBoxPassword, TBOXPWDX, TBOXPWDY);
		textBoxPassword.setText("Kennwort");
		absolutePanelCreateUser.add(textBoxMail, TBOXMAILX, TBOXMAILY);
		textBoxMail.setText("E-Mail");

		// User löschen
		panelAdmin.add(absolutePanelDeleteUser, PANDELETEUSERX, PANDELETEUSERY);
		absolutePanelDeleteUser.setStyleName("gwt-Panel-Invest");
		absolutePanelDeleteUser.setSize("428px", "98px");

		absolutePanelDeleteUser.add(lbDeleteUser, LBDELETEUSERX, LBDELETEUSERY);
		lbDeleteUser.setStyleName("gwt-Home-Label");
		absolutePanelDeleteUser.add(textBoxUsernameDelete, TBOXUSERDELETEX,
				TBOXUSERDELETEY);
		textBoxUsernameDelete.setText("Username");

		// Calls
		// Usertabelle befüllen
		service.getUser(new GetUserCallback());
		// Statistiken angeben
		service.getStats(new GetStatsCallback());

		setButtons();
	} // Ende onModuleLoad

	/**
	 * legt die Eventhandler für dieButtons fest
	 */
	public final void setButtons() {
		// Eventhandler

		// Eventhandler btDelete
		btDelete.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				// User löschen, dessen Name angezeigt wurde
				deleteUser = textBoxUsernameDelete.getText().trim();

				// Überprüfung, ob es sich um den Admin handelt
				if ((deleteUser.toLowerCase()).equals("admin")) {
					Window.alert("Sie k\u00f6nnen den Admin"
							+ " nicht l\u00f6schen!");
				} else {
					service.deleteUser(deleteUser, new DeleteUserCallback());
				} // Ende if-else
			}
		}); // Ende btDelete

		// Eventhandler btCreateUser
		btCreateUser.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
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
					if (alreadyExistingUser) {
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
			public void onClick(final ClickEvent event) {
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
			public void onClick(final ClickEvent event) {
				if (textBoxUsername.getValue().equals("Username")) {
					textBoxUsername.setText("");
				} // Ende if-Statement

			}
		}); // Ende textBoxUsername

		// Eventhandler Mail TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit der
		// Admin Daten eingeben kann
		textBoxMail.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (textBoxMail.getValue().equals("E-Mail")) {
					textBoxMail.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername

		// Eventhandler Password TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit der
		// Admin Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (textBoxPassword.getValue().equals("Kennwort")) {
					textBoxPassword.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername

		// Eventhandler User löschen TextBox: löscht den Textboxinhalt, wenn er
		// "default"-Inhalt, damit
		// der Admin Daten eingeben kann
		textBoxUsernameDelete.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (textBoxUsernameDelete.getValue().equals("Username")) {
					textBoxUsernameDelete.setText("");
				} // Ende if-Statement
			}
		}); // Ende textBoxUsername

	}

	/**
	 * getUserTable lädt die angezeigte Usertabelle
	 */
	private void getUserTable() {
		// die User werden in eine Tabelle geladen und angezeigt
		cellTableUser.setPageSize(CELLTBLSIZE);
		cellTableUser.setSize("489px", "407px");
		panelAdmin.add(cellTableUser, CELLTBLUSERX, CELLTBLUSERY);
		cellTableUser.setStyleName("cellTableHeader");

		// Usertabelle mit DB-Usern befüllen: Username
		TextColumn<User> usernameColumn = new TextColumn<User>() {
			public String getValue(final User user) {
				return new String(user.getUsername());
			}
		}; // Ende UsernameColumn

		// Usertabelle mit DB-Usern befüllen: Kennwort
		final TextInputCell passwordCell = new TextInputCell();
		Column<User, String> passwordColumn = new Column<User, String>(
				passwordCell) {
			public String getValue(final User object) {
				return object.getPassword();
			}
		}; // Ende PasswordColumn

		// Usertabelle mit DB-Usern befüllen: E-Mail
		TextColumn<User> eMailColumn = new TextColumn<User>() {
			public String getValue(final User user) {
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
			public String getValue(final User object) {
				return new String(
						new Boolean(object.isForgottenPassword()).toString());
			}
		}; // Ende forgottenPasswordColumn

		// DB-Userdaten anzeigen lassen
		cellTableUser.addColumn(usernameColumn, "Username");
		cellTableUser.addColumn(passwordColumn, "Kennwort");
		cellTableUser.addColumn(eMailColumn, "Email");
		cellTableUser.addColumn(forgottenPasswordColumn, "Passwort vergessen");

		// Fieldupdater

		// Fieldupdater PasswortColumn
		passwordColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			public void update(final int index, final User object,
					final String value) {
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
					public void update(final int index, final User object,
							final String value) {
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

		public void onFailure(final Throwable caught) {
		} // Ende onFailure

		public final void onSuccess(final Void result) {
			// War die Passwortänderung erfolgreich, wird dem Admin eine Meldung
			// ausgeworfen
			Window.alert("\u00c4nderung erfolgreich. "
					+ "Informieren Sie den Kunden.");
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

		public void onFailure(final Throwable caught) {
		} // Ende onFailure

		public final void onSuccess(final List<User> result) {
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

		public void onFailure(final Throwable caught) {
		} // Ende onFailure

		public final void onSuccess(final Void result) {
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

		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		public final void onSuccess(final Void result) {
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

		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		public final void onSuccess(final Admin result) {
			// Setzt den Text der StatistikLabel
			lbLoginCounter.setText(result.getLoginCount() + "");
			lbExistingUserCounter.setText(result.getExistingUserCount() + "");
		} // Ende method onSuccess
	} // Ende class GetStatsCallback
} // Ende class AdminSimulation
