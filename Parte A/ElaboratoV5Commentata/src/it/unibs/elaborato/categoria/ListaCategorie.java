package it.unibs.elaborato.categoria;

import java.io.Serializable;
import java.util.ArrayList;

/**Classe che raggruppa gli oggetti Categoria in una lista e permette di effetturaci modifche 
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 *  
 * 
 * @invariant listaCategorie!=null
 */
public class ListaCategorie implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Categoria> listaCategorie;
	
	/**
	 * metodo costruttore che istanzia l'array
	 */
	public ListaCategorie() {
		super();
		this.listaCategorie=new ArrayList<Categoria>();
	}
	
	/**Metodo per aggiungere una categoria alla lista delle categorie presenti nel sistema
	   * @param c è la categoria da aggiungere
	   * @return true se la categoria è stata aggiunta, false se la categoria non è stata aggiunta
	   * 
	   * @precondition c!=null
	   * @postcondition listaCategorie.size()<listaCategorie'.size()
	 */
	public boolean aggiungiCategoria(Categoria c) {
		return this.listaCategorie.add(c);
	}
	

	 /**
	   * Metodo per ritornare tutto l'albero sotto una categoria radice
	   * @param radice è la categoria da cui si ricava tutto l'albero
	   * @return ritorna l'albero
	   * 
	   * @precondition radice!=null
	   * @postcondition albero.size()>=1
	 */
	public ArrayList<Categoria> getAlbero(Categoria radice) {
		ArrayList<Categoria> albero = new ArrayList<>();

		albero.add(radice);
		for (Categoria c : getFigli(radice)) {
			albero.add(c);
		}

		return albero;
	}

	 /**
	   * Metodo per ritornare i figli di una certa categoria padre
	   * @param padre è la categoria padre da cui si ricavano i figli
	   * @return ritorna i figli di una categoria
	   * 
	   * @precondition padre!=null
	   * @postcondition figli.size()>=0
	*/
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

   /**
    * Metodo che ritorna tutte le categorie radice presenti nel sistema
    * @return ritorna le categorie radice presenti nel sistema
   */
	public ArrayList<Categoria> getRadici() {
		ArrayList<Categoria> radici = new ArrayList<>();

		for (Categoria cat : listaCategorie)
			if (cat.getPadre() == null)
				radici.add(cat);

		return radici;
	}
	
	/**
	 * Metodo che raccoglie tutte le categorie RADICE (cioe quelle che non hanno una categoria padre)
	 * @return ritorna una stringa contenente tutte le categorie radice 
	 * 
	 * @precondition
	 * @postcondition testo!=null 
	 * 				  && listaCategorie.size()==listaCategorie'.size()
	 * 				  && per ogni i intero, 0<i<listaCategorie.size(), listaCategorie.get(i)==listaCategorie'.get(i) 
	 */
	public String visualizzaCategorieRadice() {
		StringBuilder testo=new StringBuilder();
		int cont=0;
		
		for(Categoria c:listaCategorie) {
			if(c.getPadre()==null) {
				testo.append(c.toString() + "\n");
				cont++;
			}
		}
		if(cont==0)
			return "Non ci sono categorie radice";
		else
			return testo.toString();
	}

	/**
	 * Metodo che raccoglie e ritorna tutte le categorie FOGLIA(cioè che hanno almeno un padre) presenti nella lista
	 * @return ritorna un Array di Categorie 
	 * 
	 * @precondition 
	 * @postcondition foglie!=null
	 */
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
	
	/**
	 * Metodo che ritorna la lista dei campi appartenenti ad una categoria passata per parametro 
	 * @param foglia Categoria da cui estrarre i campi
	 * @return ritorna una Lista di Campi 
	 * 
	 * @precondition foglia!=null
	 * @postconditon campi!=null
	 */
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
	/**
	 * Metodo che ritorna la lista delle sottoCategorie (escluse quindi le categorie radice)
	 * @return String contenente tutte le sottoCategorie presenti nella lista
	 */
	public String visualizzaSottoCategorie() {
		StringBuilder testo=new StringBuilder();
		int cont=0;
		
		for(Categoria c:listaCategorie) {
			if(c.getPadre()!=null) {
				testo.append(c.toString() + "\n");
				cont++;
			}
		}
		
		if(cont==0)
			return "Non ci sono sottocategorie";
		else
			return testo.toString();
	}
	
	public int size() {
		return this.listaCategorie.size();
	}

	@Override
	public String toString() {
		if(!listaCategorie.isEmpty())
			return visualizzaCategorieRadice()+visualizzaSottoCategorie();
		else
			return "Non sono presenti categorie";
	}
}
