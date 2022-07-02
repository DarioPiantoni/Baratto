package it.unibs.elaborato.main;

import it.unibs.elaborato.mvc.controller.Controller;
import it.unibs.elaborato.mvc.model.Model;
import it.unibs.elaborato.mvc.view.View;
/**
 * classe main avvia il programma
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public class BarattoMain {
	public static void main(String[] args) {
		//creo modello mvc 
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
		
		//tramite il controller carico i dati da file
		controller.carica();
		controller.controllaScadenze();
		controller.accesso();
		controller.salva();
	}
}