package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.mercato.Appuntamento;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.Token;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ScambioTest {

	@Test
	void rispostaFineChiusaTest() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 1);

		a1.cambioStatoOfferta(Offerta.SELEZIONATA);
		a2.cambioStatoOfferta(Offerta.ACCOPPIATA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);

		Scambio s1 = new Scambio(articoli.getLista().size(), articoli.getLista().size() - 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);

		scambi.controllaScadenza(punto, articoli);

		s1.risposta(articoli);

		assertEquals(a1.statoOffertaAttuale(), Offerta.CHIUSA);
	}
	
	@Test
	void rispostaTokenTest() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 1);

		a1.cambioStatoOfferta(Offerta.SELEZIONATA);
		a2.cambioStatoOfferta(Offerta.ACCOPPIATA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);

		Scambio s1 = new Scambio(articoli.getLista().size(), articoli.getLista().size() - 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);

		scambi.controllaScadenza(punto, articoli);

		s1.risposta(articoli, new Appuntamento("Nuovo posto", null, null, null), Token.B);

		assertEquals(a1.statoOffertaAttuale(), Offerta.IN_SCAMBIO);
	}
	
	@Test
	void getDataScadenzaTest() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 0);

		a1.cambioStatoOfferta(Offerta.SELEZIONATA);
		a2.cambioStatoOfferta(Offerta.ACCOPPIATA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);

		Scambio s1 = new Scambio(articoli.getLista().size(), articoli.getLista().size() - 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);

		s1.getDataScadenza(punto, articoli);

		assertEquals(s1.getDataScadenza(punto, articoli).format(DateTimeFormatter.ofPattern("dd MM yyyy")), LocalDate.now().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
	}
}
