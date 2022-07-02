package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.mercato.Appuntamento;
import it.unibs.elaborato.mercato.ListaScambi;
import it.unibs.elaborato.mercato.PuntoInteresse;
import it.unibs.elaborato.mercato.Scambio;
import it.unibs.elaborato.mercato.articolo.Articolo;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaScambiTest {

	@Test
	void scadenzaTest() {
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

		assertEquals(scambi.getScambiAttivi(new Fruitore("yassiabou", "aboufaris"), articoli).size(), 1);

		s1.risposta(articoli);

		assertEquals(a1.statoOffertaAttuale(), Offerta.CHIUSA);
	}

	@Test
	void rispostaConclusaTest() {
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
	void scadenzaTest2() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 0);

		a1.cambioStatoOfferta(Offerta.RITIRATA);
		a2.cambioStatoOfferta(Offerta.RITIRATA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);

		Scambio s1 = new Scambio(articoli.getLista().size(), articoli.getLista().size() - 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);

		scambi.controllaScadenza(punto, articoli);

		assertEquals(scambi.getScambiAttivi(new Fruitore("yassiabou", "aboufaris"), articoli).size(), 1);

		s1.risposta(articoli);

		assertEquals(a1.statoOffertaAttuale(), Offerta.CHIUSA);
	}

	@Test
	void aggiungiScambioTest() {
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

		assertEquals(scambi.size(), 1);
	}

	@Test
	void eliminaScambioTest() {
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

		scambi.eliminaScambio(s1);

		assertEquals(scambi.size(), 0);
	}

	@Test
	void scambiAttiviTest() {
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

		ArrayList<Scambio> scambiAttivi = scambi.getScambiAttivi(f, articoli);

		assertEquals(scambiAttivi.size(), 1);
	}

	/*
	 * Provo che gli scambi attivi siano a 0 quando passo un'altro fornitore che non
	 * ha scambi attivi
	 */
	@Test
	void scambiAttiviTest2() {
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

		ArrayList<Scambio> scambiAttivi = scambi.getScambiAttivi(new Fruitore("dariop2000", "abcd"), articoli);

		assertNotEquals(scambiAttivi.size(), 1);
	}

	@Test
	void getProposteTest() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);
		Articolo a3 = new Articolo(f, "Articolo 3", c, null);
		Articolo a4 = new Articolo(f, "Articolo 4", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 0);

		a1.cambioStatoOfferta(Offerta.SELEZIONATA);
		a2.cambioStatoOfferta(Offerta.ACCOPPIATA);
		a3.cambioStatoOfferta(Offerta.CHIUSA);
		a4.cambioStatoOfferta(Offerta.CHIUSA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);
		articoli.aggiungiArticolo(a3);
		articoli.aggiungiArticolo(a4);

		Scambio s1 = new Scambio(0, 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);
		
		Scambio s2 = new Scambio(2, 3);
		s2.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s2);

		ArrayList<Scambio> proposte = scambi.getProposte(f, articoli);

		assertEquals(proposte.size(), 1);
	}
	
	@Test
	void getOfferteFruitoreTest() {
		ListaArticoli articoli = new ListaArticoli();
		ListaScambi scambi = new ListaScambi();
		Categoria c = new Categoria("Cat", "", new ArrayList<>(), null);
		Fruitore f = new Fruitore("yassiabou", "aboufaris");
		Fruitore f1 = new Fruitore("dariop2000", "abcd");
		Articolo a1 = new Articolo(f, "Articolo 1", c, null);
		Articolo a2 = new Articolo(f, "Articolo 2", c, null);
		Articolo a3 = new Articolo(f1, "Articolo 3", c, null);
		Articolo a4 = new Articolo(f1, "Articolo 4", c, null);

		PuntoInteresse punto = new PuntoInteresse("Prova piazza", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 0);

		a1.cambioStatoOfferta(Offerta.SELEZIONATA);
		a2.cambioStatoOfferta(Offerta.ACCOPPIATA);
		a3.cambioStatoOfferta(Offerta.SELEZIONATA);
		a4.cambioStatoOfferta(Offerta.ACCOPPIATA);

		articoli.aggiungiArticolo(a1);
		articoli.aggiungiArticolo(a2);
		articoli.aggiungiArticolo(a3);
		articoli.aggiungiArticolo(a4);

		Scambio s1 = new Scambio(0, 1);
		s1.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s1);
		
		Scambio s2 = new Scambio(2, 3);
		s2.setAppuntamento(new Appuntamento(punto.getPiazza(), null, null, null));
		scambi.aggiungiScambio(s2);

		ArrayList<Scambio> offerteFruitore = scambi.getProposte(f, articoli);

		assertEquals(offerteFruitore.size(), 1);
	}
}
