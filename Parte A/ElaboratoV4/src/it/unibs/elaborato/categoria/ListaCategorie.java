package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaCategorie implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Categoria> listaCategorie;

	public ListaCategorie() {
		super();
		this.listaCategorie=new ArrayList<Categoria>();
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
		StringBuilder testo=new StringBuilder();
		
		for(Categoria c:listaCategorie) {
			if(c.getPadre()==null)
				testo.append(c.toString() + "\n");
		}
		return testo.toString();
	}

	//metodo per ricavare le categorie foglie
	public ArrayList<Categoria> getCategorieFoglie(){
		ArrayList<Categoria> foglie=new ArrayList<Categoria>();
		boolean isFoglia;
		
		for(Categoria c1:this.listaCategorie) {
			isFoglia=true;
			for(Categoria c2:this.listaCategorie) {
				if(c2.getPadre()!=null && c2.getPadre().equals(c1))
					isFoglia=false;
			}
			if(isFoglia)
				foglie.add(c1);
		}
		
		return foglie;
	}
	public ArrayList<Campo> getCampiCategoria(Categoria foglia){
		ArrayList<Campo> campi=new ArrayList<Campo>();
	    Categoria padre = foglia.getPadre();
	    
	    if(padre != null) {
	      do {
	        for(Campo c: padre.getCampi()) {
	          campi.add(c);
	        }
	        padre=padre.getPadre();
	      }while(padre != null);
	    }
	    
	    for(Campo c: foglia.getCampi())
	      campi.add(c);
	    
	    return campi;
	}
	
	public String visualizzaSottoCategorie() {
		StringBuilder testo=new StringBuilder();
		
		for(Categoria c:listaCategorie) {
			if(c.getPadre()!=null)
				testo.append(c.toString() + "\n");
		}
		return testo.toString();
	}

	@Override
	public String toString() {
		return visualizzaCategorieRadice()+visualizzaSottoCategorie();
	}
}
