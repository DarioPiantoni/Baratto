package it.unibs.elaborato.mercato;

import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.stato.ArticoloState;

/**Classe che contiene i metodi per il calcolo della scadenza di uno scambio
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class Scadenza {
	/**
	 * Metodo che calcola le date di scadenza delle offerte in corso per gli articoli in scambio
	 * @param punto punti di interesse presenti nel sistema
	 * @param articoli articoli presenti nel sistema
	 * @return ritorna la data di scadenza dello scambio
	 * 
	 * @precondition punto!=null && articoli!=null
	 * @postcondition dataScadenza!=null
	 */
	public static LocalDateTime getDataScadenza(Scambio scambio,PuntoInteresse punto,ListaArticoli articoli){
		LocalDateTime dataInizioScambio=articoli.getArticolo(scambio.getIdArticoloA()).ultimoStato();
		LocalDateTime dataScadenza=dataInizioScambio.plusDays(punto.getScadenza());
		
		return dataScadenza;
	}
	
	/**
	 * Metodo che controlla se ci sono scambi scaduti nella lista e in tal caso li elimina
	 * @param punto lista dei punti di interesse configurati
	 * @param articoli lista degli articoli inseriti nel sistema
	 * 
	 * @precondition punto!=null && articoli!=null
	 * @postcondition scambi.size()>=scambi'.size() 
	 */
	public static void controllaScadenza(ListaScambi scambi,PuntoInteresse punto, ListaArticoli articoli) {
        ArrayList<Scambio> eliminati = new ArrayList<Scambio>();
        for (Scambio s : scambi.getScambi()) {
                if (getDataScadenza(s,punto, articoli).isBefore(LocalDateTime.now())
                                && (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().toString().equals(ArticoloState.ACCOPPIATA)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().toString().equals(ArticoloState.SELEZIONATA)
                                || articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().toString().equals(ArticoloState.IN_SCAMBIO)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().toString().equals(ArticoloState.IN_SCAMBIO)))
                        eliminati.add(s);
        }

        for (Scambio s : eliminati) {
                articoli.getArticolo(s.getIdArticoloA()).aperta();
                articoli.getArticolo(s.getIdArticoloB()).aperta();
                scambi.elimina(s);
        }
	}
}
