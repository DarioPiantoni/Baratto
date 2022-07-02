package it.unibs.elaborato.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import it.unibs.elaborato.categoria.Campo;
import it.unibs.elaborato.categoria.Categoria;
import it.unibs.elaborato.categoria.ListaCategorie;
import it.unibs.elaborato.categoria.TipoDato;

/**@author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
class ListaCategoriaTest {

  @Test
  void aggiungiCategoriaTest() {
    ListaCategorie categorie = new ListaCategorie();
    Categoria c = new Categoria("Categoria Test", "Categoria di prova", null, null);
    categorie.aggiungi(c);
    
    assertEquals(categorie.size(), 1);
  }
  
  @Test
  void getCategorieFoglieTest() {
    ListaCategorie categorie = new ListaCategorie();
    Categoria c = new Categoria("Categoria Test", "Categoria di prova", null, null);
    categorie.aggiungi(c);
    Categoria foglia = new Categoria("Foglia", null, null, c);
    categorie.aggiungi(foglia);
    
    assertEquals(categorie.getCategorieFoglie().size(), 1);
  }
  
  @Test
  void getCampiCategoriaTest() {
    ListaCategorie categorie = new ListaCategorie();
    ArrayList<Campo> campiPadre=new ArrayList<Campo>();
    ArrayList<Campo> campiFoglia=new ArrayList<Campo>();
    campiPadre.add(new Campo("Campo di prova", TipoDato.DECIMALE, true));
    campiFoglia.add(new Campo("Campo di prova 2", TipoDato.DECIMALE, true));
    
    Categoria c = new Categoria("Categoria Test", "Categoria di prova", campiPadre, null);
    categorie.aggiungi(c);
    
    Categoria foglia = new Categoria("Foglia", null, campiFoglia, c);
    categorie.aggiungi(foglia);
    
    assertEquals(categorie.getCampiCategoria(foglia).size(), 4);
  }

}