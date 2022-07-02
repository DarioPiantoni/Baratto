package it.unibs.elaborato.utils;

/**Interfaccia che definisce una lista di oggetti generici
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * @param <T> tipo di dato generico che è contenuto nella lista
 */
public interface Lista<T> {
	/**metodo per aggiungere un elemento alla lista
	 * @param elemento elemento da aggiungere alla lista
	 * @return il metodo ritorna un booleano in relazione all'esito dell'aggiunta
	 */
	public boolean aggiungi(T elemento);
	
	/**metodo per eliminare un elemento dalla lista
	 * @param elemento elemento da eliminare dalla lista
	 * @return il metodo ritorna un booleano in relazione all'esito dell'eliminazione
	 */
	public boolean elimina(T elemento);
	
	/**metodo per ottenere il numero di elementi nella lista
	 * @return il metodo ritorna il numero di elementi nella lista
	 */
	public int size();
}
