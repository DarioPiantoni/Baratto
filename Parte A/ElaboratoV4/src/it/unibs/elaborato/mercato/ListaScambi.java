package it.unibs.elaborato.mercato;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.mercato.articolo.ListaArticoli;
import it.unibs.elaborato.mercato.articolo.Offerta;
import it.unibs.elaborato.utente.Fruitore;

public class ListaScambi implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Scambio> scambi;

	public ListaScambi() {
		super();
		scambi=new ArrayList<Scambio>();
	}
	
	public void aggiungiScambio(Scambio s) {
		scambi.add(s);
	}
	
	public boolean eliminaScambio(Scambio s) {
		return scambi.remove(s);
	}
	
	public void controllaScadenza(PuntoInteresse punto, ListaArticoli articoli) {
        ArrayList<Scambio> eliminati = new ArrayList<Scambio>();
        for (Scambio s : scambi) {
                if (s.getDataScadenza(punto, articoli).isBefore(LocalDateTime.now())
                                && (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.ACCOPPIATA)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.SELEZIONATA)
                                || articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO)
                                || articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO)))
                        eliminati.add(s);
        }

        for (Scambio s : eliminati) {
                articoli.getArticolo(s.getIdArticoloA()).cambioStatoOfferta(Offerta.APERTA);
                articoli.getArticolo(s.getIdArticoloB()).cambioStatoOfferta(Offerta.APERTA);
                eliminaScambio(s);
        }
	}

	public ArrayList<Scambio> getScambiAttivi(Fruitore fruitore,ListaArticoli articoli) {
		ArrayList<Scambio> scambiAttivi=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore) || articoli.getArticolo(s.getIdArticoloB()).getProprietario().equals(fruitore))
				scambiAttivi.add(s);
		}
		return scambiAttivi;
	}
	
	//proposte da rispondere
	public ArrayList<Scambio> getProposte(Fruitore fruitore,ListaArticoli articoli){
		ArrayList<Scambio> proposte=new ArrayList<Scambio>();
		
		for(Scambio s:scambi) {
			if(s.getToken().equals(Token.A) && articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore) && !articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.CHIUSA))
				proposte.add(s);
			if(s.getToken().equals(Token.B) && articoli.getArticolo(s.getIdArticoloB()).getProprietario().equals(fruitore) && !articoli.getArticolo(s.getIdArticoloB()).statoOffertaAttuale().equals(Offerta.CHIUSA))
				proposte.add(s);
		}
		
		return proposte;
	}
	
	public ArrayList<Scambio> getOfferteFruitore(Fruitore fruitore,ListaArticoli articoli){
		ArrayList<Scambio> offerteFruitore=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getProprietario().equals(fruitore))
				offerteFruitore.add(s);
		}
		
		return offerteFruitore;
	}
	
	public ArrayList<Scambio> getOfferteCategoria(Categoria categoria,ListaArticoli articoli){
		ArrayList<Scambio> offerteCategoria=new ArrayList<Scambio>();
		for(Scambio s:scambi) {
			if(articoli.getArticolo(s.getIdArticoloA()).getCategoria().equals(categoria) && (articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.IN_SCAMBIO) || articoli.getArticolo(s.getIdArticoloA()).statoOffertaAttuale().equals(Offerta.CHIUSA)))
				offerteCategoria.add(s);
		}
		return offerteCategoria;
	}
	
}
