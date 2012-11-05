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

	private final LoginServiceAsync LoginService = GWT
			.create(LoginService.class);

	// Panel
	private AbsolutePanel panelLogin = new AbsolutePanel();
	Image background = new Image("fallstudie/gwt/clean/images/Consulting.png");
	// Widgets
	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private TextBox textBoxUsername = new TextBox();
	private PasswordTextBox textBoxPassword = new PasswordTextBox();
	private Button btLogin = new Button("Login");
	private Button btForgotPassword = new Button("Passwort vergessen?");

	public static User userOnline;
	private Label lbInfo = new Label();
	String admin = new String("admin");

	public void onModuleLoad() {

		// AbsolutePanel: panelLogin
		RootPanel.get().add(panelLogin, 0, 0);
		panelLogin.setSize("1024px", "768px");
		lbInfo.setSize("310px", "12px");

		// Background: background
		panelLogin.add(background, 0, 0);
		background.setSize("1024px", "768px");

		// Firmenlogo: logo
		panelLogin.add(logo, 545, 34);
		logo.setSize("360px", "110px");

		// TextBox für den Usernamen: textBoxUsername
		panelLogin.add(textBoxUsername, 208, 229);
		textBoxUsername.setSize("300px", "24px");
		textBoxUsername.setText("Username");

		// TextBox für das Kennwort: textBoxPassword
		panelLogin.add(textBoxPassword, 208, 283);
		textBoxPassword.setText("Kennwort");
		textBoxPassword.setSize("300px", "24px");

		// Button einloggen: btLogin
		panelLogin.add(btLogin, 208, 353);
		btLogin.setSize("100px", "30px");

		// Eventhandler

		// Eventhandler Login
		btLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userOnline = new User();
				userOnline.setPassword(textBoxPassword.getText());
				userOnline.setUsername(textBoxUsername.getText());
				// Überprüfen, ob es sich bei dem einloggenden User um den admin
				// handelt
				if (userOnline.getUsername().equals(admin)) {
					LoginService.checkAdmin(userOnline,
							new CheckAdminCallback());
				} else {
					LoginService.checkLogin(userOnline,
							new CheckLoginCallback());
				}
			}
		}); // Ende btLogin Click-Handler
		btLogin.addDragEnterHandler(new DragEnterHandler() {
			public void onDragEnter(DragEnterEvent event) {
				userOnline = new User();
				userOnline.setPassword(textBoxPassword.getText());
				userOnline.setUsername(textBoxUsername.getText());
				// Überprüfen, ob es sich bei dem einloggenden User um den admin
				// handelt
				if (userOnline.getUsername().equals(admin)) {
					LoginService.checkAdmin(userOnline,
							new CheckAdminCallback());
				} else {
					LoginService.checkLogin(userOnline,
							new CheckLoginCallback());
				}
			}
		}); // Ende btLogin Enter-Handler

		// Buttons

		// Button vergessenes Passwort: btForgotPassword
		panelLogin.add(btForgotPassword, 369, 353);
		btForgotPassword.setSize("149px", "30px");

		// Eventhandler vergessenes Passwort
		btForgotPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userOnline = new User();
				userOnline.setUsername(textBoxUsername.getText());
				LoginService.forgotPassword(userOnline,
						new ForgotPasswordCallback());
			}
		}); // Ende btForgortPassword Click-Handler
		btForgotPassword.addDragEnterHandler(new DragEnterHandler() {
			public void onDragEnter(DragEnterEvent event) {
				userOnline = new User();
				userOnline.setUsername(textBoxUsername.getText());
				LoginService.forgotPassword(userOnline,
						new ForgotPasswordCallback());
			}
		}); // Ende btForgottPassword Enter-Handler

		// Informationsfeld: lfInfo
		panelLogin.add(lbInfo, 208, 325);
		lbInfo.setStyleName("gwt-Infolabel");

		// Eventhandler Password TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxPassword.setText("");
			}

		}); // Ende textBoxPassword

		// Eventhandler Username TextBox: löscht den Textboxinhalt, damit der
		// User Daten eingeben kann
		textBoxUsername.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				textBoxUsername.setText("");
			}
		}); // Ende textBoxUsername

	} // Ende onModuleLoad

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher die
	 * Logindaten mit den Daten der DB vergleicht.
	 * 
	 */
	public class CheckLoginCallback implements AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Username oder Passwort falsch/ unbekannt.");

		} // Ende method onFailure

		@Override
		public void onSuccess(Void result) {

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

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Derzeit liegt leider ein Systemfehler vor. Versuchen Sie es sp\u00E4ter erneut.");

		} // Ende method onFailure

		@Override
		public void onSuccess(Void result) {

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

		@Override
		public void onFailure(Throwable caught) {
			lbInfo.setText("Adminpasswort falsch");

		} // Ende method onFailure

		@Override
		public void onSuccess(Void result) {

			RootPanel.get().clear();
			AdminSimulation admin = new AdminSimulation();
			admin.onModuleLoad();

		} // Ende method onSuccess
	} // Ende class CheckAdminCallback

} // Ende classLoginSimulation
