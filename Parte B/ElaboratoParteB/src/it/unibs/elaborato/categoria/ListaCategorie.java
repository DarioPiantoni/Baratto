package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.unibs.elaborato.utils.Lista;

/**
 * Classe che raggruppa gli oggetti Categoria in una lista e permette di
 * effetturaci modifche
 * 
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 * 
 * @invariant listaCategorie!=null
 */
public class ListaCategorie implements Lista<Categoria>, Serializable {
	private static final long serialVersionUID = 1L;
	private Collection<Categoria> listaCategorie;

	/**
	 * metodo costruttore che istanzia l'array
	 */
	public ListaCategorie() {
		super();
		this.listaCategorie = new ArrayList<Categoria>();
	}

	/**
	 * Metodo per aggiungere una categoria alla lista delle categorie presenti nel
	 * sistema
	 * 
	 * @param c è la categoria da aggiungere
	 * @return true se la categoria è stata aggiunta, false se la categoria non è
	 *         stata aggiunta
	 * 
	 * @precondition c!=null
	 * @postcondition listaCategorie.size()<listaCategorie'.size()
	 */
	public boolean aggiungi(Categoria c) {
		return this.listaCategorie.add(c);
	}

	/**
	 * Metodo per ritornare tutto l'albero sotto una categoria radice
	 * 
	 * @param radice è la categoria da cui si ricava tutto l'albero
	 * @return ritorna l'albero
	 * 
	 * @precondition radice!=null
	 * @postcondition albero.size()>=1
	 */
	public Collection<Categoria> getAlbero(Categoria radice) {
		Collection<Categoria> albero = new ArrayList<>();

		albero.add(radice);
		for (Categoria c : getFigli(radice)) {
			albero.add(c);
		}

		return albero;
	}

	/**
	 * Metodo per ritornare i figli di una certa categoria padre
	 * 
	 * @param padre è la categoria padre da cui si ricavano i figli
	 * @return ritorna i figli di una categoria
	 * 
	 * @precondition padre!=null
	 * @postcondition figli.size()>=0
	 */
	private Collection<Categoria> getFigli(Categoria padre) {
		Collection<Categoria> figli = new ArrayList<>();

		for (Categoria cat : listaCategorie) {
			if (cat.getPadre() != null) {
				if (cat.getPadre().equals(padre)) {
					figli.add(cat);
					if (!getFigli(cat).isEmpty()) {
						for (Categoria c : getFigli(cat))
							figli.add(c);
					}
				}
			}
		}

		return figli;
	}

	/**
	 * Metodo che ritorna tutte le categorie radice presenti nel sistema
	 * 
	 * @return ritorna le categorie radice presenti nel sistema
	 */
	public Collection<Categoria> getRadici() {
		Collection<Categoria> radici = new ArrayList<>();

		for (Categoria cat : listaCategorie)
			if (cat.getPadre() == null)
				radici.add(cat);

		return radici;
	}

	public Collection<Categoria> getListaCategorie() {
		return listaCategorie;
	}

	

	/**
	 * Metodo che raccoglie e ritorna tutte le categorie FOGLIA(cioè che hanno
	 * almeno un padre) presenti nella lista
	 * 
	 * @return ritorna un Array di Categorie
	 * 
	 * @precondition
	 * @postcondition foglie!=null
	 */
	public List<Categoria> getCategorieFoglie() {
		List<Categoria> foglie = new ArrayList<Categoria>();
		boolean isFoglia;

		for (Categoria c1 : this.listaCategorie) {
			isFoglia = true;
			for (Categoria c2 : this.listaCategorie) {
				if (c2.getPadre() != null && c2.getPadre().equals(c1))
					isFoglia = false;
			}
			if (isFoglia)
				foglie.add(c1);
		}

		return foglie;
	}

	/**
	 * Metodo che ritorna la lista dei campi appartenenti ad una categoria passata
	 * per parametro
	 * 
	 * @param foglia Categoria da cui estrarre i campi
	 * @return ritorna una Lista di Campi
	 * 
	 * @precondition foglia!=null
	 * @postconditon campi!=null
	 */
	public Collection<Campo> getCampiCategoria(Categoria foglia) {
		Collection<Campo> campi = new ArrayList<Campo>();
		Categoria padre = foglia.getPadre();

		if (padre != null) {
			do {
				for (Campo c : padre.getCampi()) {
					campi.add(c);
				}
				padre = padre.getPadre();
			} while (padre != null);
		}

		for (Campo c : foglia.getCampi())
			campi.add(c);

		return campi;
	}

	public int size() {
		return this.listaCategorie.size();
	}

	@Override
	public boolean elimina(Categoria elemento) {
		return this.listaCategorie.remove(elemento);
	}
}
