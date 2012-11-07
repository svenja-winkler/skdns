package de.dhbw.wwi11sca.skdns.client.login;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die LoginSimulation enthält das Frontend des Logins.
 * 
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;
import de.dhbw.wwi11sca.skdns.shared.User;
import de.dhbw.wwi11sca.skdns.client.admin.AdminSimulation;
import de.dhbw.wwi11sca.skdns.client.home.HomeSimulation;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragEnterEvent;

public class LoginSimulation implements EntryPoint {

	private AbsolutePanel panelLogin = new AbsolutePanel();

	private Image background = new 
			Image("fallstudie/gwt/clean/images/Consulting.png");
	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");

	private TextBox textBoxUsername = new TextBox();
	private PasswordTextBox textBoxPassword = new PasswordTextBox();

	private Button btLogin = new Button("Login");
	private Button btForgotPassword = new Button("Passwort vergessen?");

	private Label lbInfo = new Label();

	private String admin = new String("admin");
	private static User userOnline;

	private final LoginServiceAsync loginService = GWT
			.create(LoginService.class);

	//Constants
	private static final int LOGOX = 545;
	private static final int LOGOY = 34;
	private static final int TEXTBOXUSERNAMEX = 208;
	private static final int TEXTBOXUSERNAMEY = 229;
	private static final int TEXTBOXPASSWORDX = 208;
	private static final int TEXTBOXPASSWORDY = 283;
	private static final int LBINFOX =  208;
	private static final int LBINFOY = 325;
	private static final int BTLOGINX = 208;
	private static final int BTLOGINY = 353;
	private static final int BTFORGOTPWDX = 369;
	private static final int BTFORGOTPWDY = 353;
	
	public final void onModuleLoad() {

		// AbsolutePanel: panelLogin
		RootPanel.get().add(panelLogin, 0, 0);
		panelLogin.setSize("1024px", "768px");

		// Background: background
		panelLogin.add(background, 0, 0);
		background.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelLogin.add(logo, LOGOX, LOGOY);
		logo.setSize("360px", "110px");

		// TextBox für den Usernamen: textBoxUsername
		panelLogin.add(textBoxUsername, TEXTBOXUSERNAMEX, TEXTBOXUSERNAMEY);
		textBoxUsername.setSize("300px", "24px");
		textBoxUsername.setText("Username");

		// TextBox für das Kennwort: textBoxPassword
		panelLogin.add(textBoxPassword, TEXTBOXPASSWORDX, TEXTBOXPASSWORDY);
		textBoxPassword.setText("Kennwort");
		textBoxPassword.setSize("300px", "24px");

		// Informationslabel: lbInfo
		lbInfo.setSize("310px", "12px");
		panelLogin.add(lbInfo, LBINFOX, LBINFOY);
		lbInfo.setStyleName("gwt-Infolabel");

		// Buttons

		// Button einloggen: btLogin
		panelLogin.add(btLogin, BTLOGINX, BTLOGINY);
		btLogin.setSize("100px", "30px");

		// Button vergessenes Passwort: btForgotPassword
		panelLogin.add(btForgotPassword, BTFORGOTPWDX, BTFORGOTPWDY);
		btForgotPassword.setSize("149px", "30px");

		// Eventhandler

		// Eventhandler Login Click-Handler
		btLogin.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				login();
			}
		}); // Ende btLogin Click-Handler

		// Eventhandler Login Enter-Handler
		btLogin.addDragEnterHandler(new DragEnterHandler() {
			public void onDragEnter(final DragEnterEvent event) {
				login();
			}
		}); // Ende btLogin Enter-Handler

		// Eventhandler vergessenes Passwort ClickHandler
		btForgotPassword.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				userOnline = new User();
				userOnline.setUsername(textBoxUsername.getText());
				loginService.forgotPassword(userOnline,
						new ForgotPasswordCallback());
			}
		}); // Ende btForgortPassword Click-Handler

		// Eventhandler btForgotPassword Enter-Handler
		btForgotPassword.addDragEnterHandler(new DragEnterHandler() {
			public void onDragEnter(final DragEnterEvent event) {
				userOnline = new User();
				userOnline.setUsername(textBoxUsername.getText());
				loginService.forgotPassword(userOnline,
						new ForgotPasswordCallback());
			}
		}); // Ende btForgottPassword Enter-Handler

		// Eventhandler Password TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				textBoxPassword.setText("");
			}
		}); // Ende textBoxPassword

		// Eventhandler Username TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxUsername.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				textBoxUsername.setText("");
			}
		}); // Ende textBoxUsername
	} // Ende onModuleLoad

	/**
	 * login erzeugt einen User mit den eingegebenen Daten und schickt einen
	 * Call an den Server zur Überprüfung der Daten
	 */
	private void login() {
		userOnline = new User();
		userOnline.setPassword(textBoxPassword.getText());
		userOnline.setUsername(textBoxUsername.getText());

		// Überprüfen, ob es sich bei dem einloggenden User um den admin
		// handelt
		if (userOnline.getUsername().equals(admin)) {
			// Ist der User ein Admin wird die checkAdmin-Methode verwendet
			loginService.checkAdmin(userOnline, new CheckAdminCallback());
		} else {
			// Ist der User kein Admin wrd die checkLogin-Methode verwendet
			loginService.checkLogin(userOnline, new CheckLoginCallback());
		} // Ende if-else
	} // Ende method login

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Logindaten mit den Daten der DB vergleicht.
	 * 
	 */
	public class CheckLoginCallback implements AsyncCallback<java.lang.Void> {

		public final void onFailure(final Throwable caught) {
			// Sind die Daten nicht bekannt, wird dem User eine Meldung
			// ausgeworfen
			lbInfo.setText("Username oder Passwort falsch/ unbekannt.");
		} // Ende method onFailure

		public final void onSuccess(final Void result) {
			// Sind die Daten bekannt, wird die nächste Oberfläche geladen
			RootPanel.get().clear();
			HomeSimulation home = new HomeSimulation();
			home.onModuleLoad();
		} // Ende method onSuccess
	} // Ende class CheckLoginCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher
	 * speichert, dass der User sein Passwort vergessen hat
	 * 
	 */
	public class ForgotPasswordCallback implements
			AsyncCallback<java.lang.Void> {

		public final void onFailure(final Throwable caught) {
			// Hat es nicht funktioniert, eine Meldung an den Admin zu schicken,
			// wird dem User eine Meldung ausgeworfen
			lbInfo.setText("Derzeit liegt leider ein Systemfehler vor." 
			+	" Versuchen Sie es sp\u00E4ter erneut.");

		} // Ende method onFailure

		public final void onSuccess(final Void result) {
			// War das Admin informieren erfolgreich, wird dem User eine Meldung
			// ausgeworfen
			lbInfo.setText("Der Admin wurde informiert.");
		} // Ende method onSuccess
	} // Ende class ForgotPasswordCallback

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher das
	 * Adminpasswort mit der DB vergleicht.
	 * 
	 */
	public class CheckAdminCallback implements AsyncCallback<java.lang.Void> {

		public final void onFailure(final Throwable caught) {
			// Ist das Adminpasswort nicht bekannt, wird dem User eine Meldung
			// ausgeworfen
			lbInfo.setText("Adminpasswort falsch");
		} // Ende method onFailure

		public final void onSuccess(final Void result) {
			// Ist das Adminpasswort bekannt, wird die nächste Oberfläche
			// geladen
			RootPanel.get().clear();
			AdminSimulation adminSimulation = new AdminSimulation();
			adminSimulation.onModuleLoad();
		} // Ende method onSuccess
	} // Ende class CheckAdminCallback
} // Ende classLoginSimulation
