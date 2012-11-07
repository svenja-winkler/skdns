package de.dhbw.wwi11sca.skdns.client.logout;

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die LogoutSimulation enthält das Frontend des Logouts.
 * 
 */
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.dhbw.wwi11sca.skdns.client.login.LoginSimulation;
import com.google.gwt.user.client.ui.Image;

public class LogoutSimulation implements EntryPoint {
	
	//Constants
	private static final int LOGOX = 50;
	private static final int LOGOY = 50;
	private static final int BTRELOGINX = 350;
	private static final int BTRELOGINY = 189;
	private static final int BTLOGOUTX = 50;
	private static final int BTLOGOUTY = 192;

	private Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");
	private Button btRelogin = new Button("erneuter Login?");
	private final LogoutServiceAsync service = GWT.create(LogoutService.class);

	public final void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();

		// Firmenlogo: logo
		rootPanel.add(logo, LOGOX, LOGOY);
		logo.setSize("405px", "108px");

		// Button erneuter Login?: btRelogin
		rootPanel.add(btRelogin, BTRELOGINX, BTRELOGINY);

		// Eventhandler btRelogin
		btRelogin.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				// Die Loginfläche wird geladen
				RootPanel.get().clear();
				LoginSimulation login = new LoginSimulation();
				login.onModuleLoad();
			}
		}); // btRelogin

		// löscht die SimulationVersionen des ausloggenden Unternehmens,
		// abgesehen der letzten drei
		service.deleteVersions(new DeleteSimulationCallback());
	} // Ende onModuleLoad

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher alle
	 * ,abgesehen der letzten drei SimulationVersion, löscht
	 * 
	 */
	public class DeleteSimulationCallback implements
			AsyncCallback<java.lang.Void> {

		public void onFailure(final Throwable caught) {
		} // Ende method onFailure

		public final void onSuccess(final Void result) {
			// War das Löschen der Simulationsversionen erfolgreich, wird dem
			// ausgeloggten User ausgegeben, dass er sich erfolgreich ausgeloggt
			// hat
			RootPanel.get().add(lbLogout, BTLOGOUTX, BTLOGOUTY);
		} // Ende method onSuccess
	} // Ende class DeleteSimulationCallback
} // Ende classLogoutSimulation

