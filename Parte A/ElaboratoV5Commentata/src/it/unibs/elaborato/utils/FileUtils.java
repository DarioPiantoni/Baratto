package it.unibs.elaborato.utils;

import java.io.*;

/**Classe di utilita per la gestione del salvataggio e caricamento da file  
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class FileUtils {
	//costanti per i messaggi di carimento e salvataggio file
	/**messaggio che dice che il file è stato caricato correttamente (visualizza anche il nome del file)**/
	public static final String FILE_CARICATO="I dati sono stati caricati correttamente dal file %s\n";
	/**messaggio che dice che il file è stato salvato correttamente (visualizza anche il nome del file)**/
	public static final String FILE_SALVATO="I dati sono stati salvati correttamente sul file %s\n";
	/**messaggio che dice che i file sono stati caricati correttamente**/
	public static final String FILE_CARICATI="I dati sono stati caricati correttamente dai file";
	/**messaggio che dice che i file sono stati salvati correttamente**/
	public static final String FILE_SALVATI="I dati sono stati salvati correttamente sui file";
	
	//costanti per i file
	private static final String SEPARATORE_ESTENSIONE=".";
	private static final String ESTENSIONE_FILE_OGGETTI=".dat";
	
	//costanti per i messaggi di errore dei file
	private static final String ERRORE_ESTENSIONE="L'estensione per i file che con contengono oggetti deve essere "+ESTENSIONE_FILE_OGGETTI;
	private static final String ERRORE_FILE_NON_TROVATO="Il file %s non è stato trovato\n";
	private static final String ERRORE_GENERICO="Errore";
	private static final String ERRORE_LETTURA="Non è stato possibile leggere il file";
	private static final String ERRORE_CLASSE="La classe letta dal file non corrisponde alla classe assegnata";
	private static final String ERRORE_CHIUSURA_FILE="Non è stato possibile chiudere il file";
	private static final String ERRORE_SCRITTURA="Non è stato possibile scrivere il file";
	
	/**metodo per sapere l'estensione di un file
	 * @param file file di cui si vuole sapere l'estensione
	 * @return il metodo ritorna una stringa contenente l'estensione del file
	 */
	private static String estensioneFile(File file) {
		String estensioneFile;
		String nomeFile;
		nomeFile=file.getName();
		int indice=nomeFile.lastIndexOf(SEPARATORE_ESTENSIONE);
		estensioneFile=nomeFile.substring(indice);
		return estensioneFile;
	}
	
	/**metodo per caricare i dati di un file in un oggetto di tipo generico T
	 * @param <T>tipo generico
	 * @param percorsoFile percorso del file da cui si vuole caricare i dati
	 * @param nomeFile nome del file da cui si vuole caricare i dati
	 * @return il metodo ritorna un oggetto di tipo generico T se il caricamento è andato a buon fine, altrimenti null
	 */
	public static <T extends Serializable> T caricaFile(String percorsoFile,String nomeFile) {
		return caricaFile(percorsoFile+"\\"+nomeFile);
	}
	
	/**metodo per caricare i dati di un file in un oggetto di tipo generico T
	 * @param <T>tipo generico
	 * @param percorsoNomeFile percorso e nome del file da cui si vuole caricare i dati
	 * @return il metodo ritorna un oggetto di tipo generico T se il caricamento è andato a buon fine, altrimenti null
	 */
	public static <T extends Serializable> T caricaFile(String percorsoNomeFile) {
		File file=new File(percorsoNomeFile);
		return caricaFile(file);
	}
	
	/**metodo per caricare i dati di un file in un oggetto di tipo generico T
	 * @param <T>tipo generico
	 * @param fileCaricare oggetto File da cui si vuole caricare i dati
	 * @return il metodo ritorna un oggetto di tipo generico T se il caricamento è andato a buon fine, altrimenti null
	 */
	public static <T extends Serializable> T caricaFile(File fileCaricare) {
		String estensioneFile;
		T oggettoLetto=null;
		
		if(fileCaricare.exists()) {
			estensioneFile=estensioneFile(fileCaricare);
			if(estensioneFile.equalsIgnoreCase(ESTENSIONE_FILE_OGGETTI))
			{
				ObjectInputStream input=null;
				try {
					input=new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileCaricare)));
					oggettoLetto=(T)input.readObject();
				} catch (FileNotFoundException e) {
					System.out.printf(ERRORE_FILE_NON_TROVATO,fileCaricare.getName());
				} catch (IOException e) {
					System.out.println(ERRORE_LETTURA);
				} catch (ClassNotFoundException e) {
					 System.out.println(ERRORE_CLASSE);
				} catch (Exception e) {
					 System.out.println(ERRORE_GENERICO);
				}finally {
					if(input!=null) {
						try {
							input.close();
						} catch (IOException e) {
							System.out.println(ERRORE_CHIUSURA_FILE);
						} catch (Exception e) {
							 System.out.println(ERRORE_GENERICO);
						}
					}
				}
			}
			else
				System.out.println(ERRORE_ESTENSIONE);
			
		}
		
		return oggettoLetto;
	}
	
	/**metodo per salvare i dati di un oggetto di tipo generico T in un file
	 * @param <T>tipo generico
	 * @param oggettoSalvare oggetto di tipo generico da salvare
	 * @param percorsoFile percorso del file su cui si vuole salvare i dati
	 * @param nomeFile nome del file su cui si vuole salvare i dati
	 * @return il metodo ritorna true se il salvataggio è andato a buon fine, false altrimenti
	 */
	public static<T extends Serializable> boolean salvaFile(T oggettoSalvare,String percorsoFile,String nomeFile) {
		return salvaFile(oggettoSalvare,percorsoFile+"\\"+nomeFile);
	}
	
	/**metodo per salvare i dati di un oggetto di tipo generico T in un file
	 * @param <T>tipo generico
	 * @param oggettoSalvare oggetto di tipo generico da salvare
	 * @param percorsoNomeFile percorso e nome del file su cui si vuole salvare i dati
	 * @return il metodo ritorna true se il salvataggio è andato a buon fine, false altrimenti
	 */
	public static<T extends Serializable> boolean salvaFile(T oggettoSalvare,String percorsoNomeFile) {
		File file=new File(percorsoNomeFile);
		return salvaFile(oggettoSalvare,file);
	}
	
	/**metodo per salvare i dati di un oggetto di tipo generico T in un file
	 * @param <T>tipo generico
	 * @param oggettoSalvare oggetto di tipo generico da salvare
	 * @param fileSalvare oggetto File su cui si vuole salvare i dati
	 * @return il metodo ritorna true se il salvataggio è andato a buon fine, false altrimenti
	 */
	public static<T extends Serializable> boolean salvaFile(T oggettoSalvare,File fileSalvare) {
		String estensioneFile;
		ObjectOutputStream output=null;
		boolean salvato=true;
		
		estensioneFile=estensioneFile(fileSalvare);
		if(estensioneFile.equalsIgnoreCase(ESTENSIONE_FILE_OGGETTI))
		{
			try {
				output=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileSalvare)));
				output.writeObject(oggettoSalvare);
			} catch (FileNotFoundException e) {
				System.out.printf(ERRORE_FILE_NON_TROVATO,fileSalvare.getAbsoluteFile());
				salvato=false;
			} catch (IOException e) {
				System.out.println(ERRORE_SCRITTURA);
				salvato=false;
			} catch (Exception e) {
				 System.out.println(ERRORE_GENERICO);
				 salvato=false;
			}finally {
				if(output!=null) {
					try {
						output.close();
					} catch (IOException e) {
						System.out.println(ERRORE_CHIUSURA_FILE);
						salvato=false;
					} catch (Exception e) {
						 System.out.println(ERRORE_GENERICO);
						 salvato=false;
					}
				}
			}
		}
		else
		{
			System.out.println(ERRORE_ESTENSIONE);
			salvato=false;
		}
		return salvato;
	}
}
