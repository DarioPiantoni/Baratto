package it.unibs.elaborato.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.unibs.elaborato.categoria.*;
import it.unibs.elaborato.mercato.*;
/**Classe di utilità per leggere da file xml
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class XMLUtils {
	
	/**
	 * Metodo che legge da un file xml le categorie
	 * @param percorsoFile percorso da file xml
	 * @param nomeFile nome del file xml
	 * @return ritorna una lista di Categorie
	 */
	public static ListaCategorie leggiCategorie(String percorsoFile, String nomeFile) {
		String file = percorsoFile + "\\" + nomeFile;
		ArrayList<Categoria> categorie = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(file));

			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("categoria");

			for (int temp = 0; temp < list.getLength(); temp++) {

				Node node = list.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					String nome = element.getElementsByTagName("nome").item(0).getTextContent();
					String descrizione = element.getElementsByTagName("descrizione").item(0).getTextContent();
					String nome_categoria_padre = element.getElementsByTagName("nome_categoria_padre").item(0)
							.getTextContent();

					for (int i = 0; i < element.getElementsByTagName("campi").getLength(); i++) {
						String[] campo = element.getElementsByTagName("campi").item(0).getTextContent().split("\n");
						ArrayList<String> campi = new ArrayList<String>();

						for (int j = 0; j < campo.length; j++)
							if (!campo[j].isBlank())
								campi.add(campo[j]);

						ArrayList<Campo> campiCategoria = new ArrayList<Campo>();

						for (int x = 0; x < campi.size(); x = x + 3) {
							TipoDato tipoDato = null;
							switch (campi.get(x + 1).trim()) {
							case "PAROLA":
								tipoDato = TipoDato.PAROLA;
								break;
							case "INTERO":
								tipoDato = TipoDato.INTERO;
								break;
							case "DECIMALE":
								tipoDato = TipoDato.DECIMALE;
								break;
							case "VEROFALSO":
								tipoDato = TipoDato.VEROFALSO;
								break;
							}
							campiCategoria.add(new Campo(campi.get(x).trim(), tipoDato,
									Boolean.parseBoolean(campi.get(x + 2).trim())));
						}
						if (nome_categoria_padre.equalsIgnoreCase("null"))
							categorie.add(new Categoria(nome, descrizione, campiCategoria, null));
						else
							categorie.add(new Categoria(nome, descrizione, campiCategoria, new Categoria(nome_categoria_padre, null, null, null)));
					}

				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		for (Categoria c : categorie)
			if (c.getPadre() != null && c.getPadre().getDescrizione() == null)
				for (Categoria c1 : categorie)
					if (c.getPadre().getNome().equals(c1.getNome()))
						c.setPadre(c1);

		ListaCategorie lista = new ListaCategorie();

		for (Categoria c : categorie)
			lista.aggiungiCategoria(c);

		return lista;
	}
	
	/**
	 * Metodo che legge da file xml una lista di punti di interesse
	 * @param percorsoFile percorso del file xml
	 * @param nomeFile nome del file xml
	 * @return ritorna una lista di Punti di Interesse
	 */
	public static PuntoInteresse leggiPuntiInteresse(String percorsoFile, String nomeFile) {
		String file = percorsoFile + "\\" + nomeFile;
		PuntoInteresse punto = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File(file));

			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("puntoInteresse");

			for (int temp = 0; temp < list.getLength(); temp++) {

				Node node = list.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					String piazza = element.getElementsByTagName("piazza").item(0).getTextContent();
					ArrayList<String> luoghi = new ArrayList<String>();
					for (int i = 0; i < element.getElementsByTagName("luoghi").getLength(); i++) {
						String[] luogo = element.getElementsByTagName("luoghi").item(0).getTextContent().split("\n");

						for (int j = 0; j < luogo.length; j++)
							if (!luogo[j].isBlank())
								luoghi.add(luogo[j]);

						ArrayList<String> luoghiPuntiInteresse = new ArrayList<String>();
						for (int x = 0; x < luoghi.size(); x++) {
							luoghiPuntiInteresse.add(luoghi.get(x).trim());
						}

					}

					ArrayList<Giorno> giorniPuntiInteresse = new ArrayList<Giorno>();
					for (int i = 0; i < element.getElementsByTagName("giorni").getLength(); i++) {
						String[] giorno = element.getElementsByTagName("giorni").item(0).getTextContent().split("\n");
						ArrayList<String> giorni = new ArrayList<String>();

						for (int j = 0; j < giorno.length; j++)
							if (!giorno[j].isBlank())
								giorni.add(giorno[j]);

						Giorno giornoSettimana = null;
						for (int x = 0; x < giorni.size(); x++) {
							switch (giorni.get(x).trim()) {
							case "LUNEDI":
								giornoSettimana = Giorno.LUNEDI;
								break;
							case "MARTEDI":
								giornoSettimana = Giorno.MARTEDI;
								break;
							case "MERCOLEDI":
								giornoSettimana = Giorno.MERCOLEDI;
								break;
							case "GIOVEDI":
								giornoSettimana = Giorno.GIOVEDI;
								break;
							case "VENERDI":
								giornoSettimana = Giorno.VENERDI;
								break;
							case "SABATO":
								giornoSettimana = Giorno.SABATO;
								break;
							case "DOMENICA":
								giornoSettimana = Giorno.DOMENICA;
								break;
							}
							giorniPuntiInteresse.add(giornoSettimana);
						}
					}

					ArrayList<IntervalloOrario> intervalliOrari = new ArrayList<IntervalloOrario>();
					for (int i = 0; i < element.getElementsByTagName("intervalli_orari").getLength(); i++) {
						String[] intervalli = element.getElementsByTagName("intervalli_orari").item(0).getTextContent().split("\n");
						ArrayList<String> prova = new ArrayList<String>();

						for (int j = 0; j < intervalli.length; j++)
							if (!intervalli[j].isBlank())
								prova.add(intervalli[j]);

						for (int x = 0; x < prova.size(); x = x + 2) {
							String[] elementi = prova.get(x).trim().split(":");
							int oraInizio = Integer.parseInt(elementi[0]);
							int minutiInizio = Integer.parseInt(elementi[1]);
							elementi = prova.get(x + 1).trim().split(":");
							int oraFine = Integer.parseInt(elementi[0]);
							int minutiFine = Integer.parseInt(elementi[1]);
							intervalliOrari.add(new IntervalloOrario(LocalTime.of(oraInizio, minutiInizio),
									LocalTime.of(oraFine, minutiFine)));
						}

					}

					String scadenza = element.getElementsByTagName("scadenza").item(0).getTextContent();
					punto = new PuntoInteresse(piazza, luoghi, giorniPuntiInteresse, intervalliOrari,
							Integer.parseInt(scadenza));
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return punto;
	}
}
