package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaCategorie implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Categoria> listaCategorie;

	public ListaCategorie() {
		super();
		this.listaCategorie = new ArrayList<Categoria>();
	}

	public boolean aggiungiCategoria(Categoria c) {
		return this.listaCategorie.add(c);
	}

	public ArrayList<Categoria> getAlbero(Categoria radice) {
		ArrayList<Categoria> albero = new ArrayList<>();

		albero.add(radice);
		for (Categoria c : getFigli(radice)) {
			albero.add(c);
		}

		return albero;
	}

	private ArrayList<Categoria> getFigli(Categoria padre) {
		ArrayList<Categoria> figli = new ArrayList<>();

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

	public ArrayList<Categoria> getRadici() {
		ArrayList<Categoria> radici = new ArrayList<>();

		for (Categoria cat : listaCategorie)
			if (cat.getPadre() == null)
				radici.add(cat);

		return radici;
	}

	public String visualizzaCategorieRadice() {
		StringBuilder testo = new StringBuilder();

		for (Categoria c : listaCategorie) {
			if (c.getPadre() == null)
				testo.append(c.toString() + "\n");
		}
		return testo.toString();
	}

	public String visualizzaSottoCategorie() {
		StringBuilder testo = new StringBuilder();

		for (Categoria c : listaCategorie) {
			if (c.getPadre() != null)
				testo.append(c.toString() + "\n");
		}
		return testo.toString();
	}

	@Override
	public String toString() {
		return visualizzaCategorieRadice() + visualizzaSottoCategorie();
	}
}
