package de.dhbw.wwi11sca.skdns.client.logout;

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

/**
 * 
 * @author SKDNS Marktsimulationen
 * 
 *         Die LogoutSimulation enthält das Frontend des Logouts.
 * 
 */

public class LogoutSimulation implements EntryPoint {

	Image logo = new Image("fallstudie/gwt/clean/images/Logo.JPG");
	private Label lbLogout = new Label("Sie wurden erfolgreich ausgeloggt.");
	private Button btRelogin = new Button("erneuter Login?");

	private final LogoutServiceAsync service = GWT.create(LogoutService.class);

	@Override
	public void onModuleLoad() {
		
		RootPanel rootPanel = RootPanel.get();
		
		rootPanel.add(btRelogin, 350, 189);
			
		
		// Firmenlogo: logo
		rootPanel.add(logo, 50, 50);
		logo.setSize("405px", "108px");
		
		btRelogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				LoginSimulation login = new LoginSimulation();
				login.onModuleLoad();
			}
		}); // btRelogin
		
		service.deleteVersions(new DeleteSimulationCallback());
	}

	/**
	 * 
	 * Klasse, die für den Asynchronen Callback zuständig ist, welcher alle
	 * ,abgesehen der letzten drei SimulationVersion, löscht
	 * 
	 */
	public class DeleteSimulationCallback implements
			AsyncCallback<java.lang.Void> {

		@Override
		public void onFailure(Throwable caught) {
		} // Ende method onFailure

		@Override
		public void onSuccess(Void result) {
			
			RootPanel.get().add(lbLogout, 50, 192);
			
		} // Ende method onSuccess

	} // Ende class DeleteSimulationCallback
} // Ende classLogoutSimulation

