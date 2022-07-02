package it.unibs.elaborato.utils;

import java.io.*;

import it.unibs.elaborato.mvc.view.View;

/**Classe di utilita per la gestione del salvataggio e caricamento da file  
 * @author Aboufaris Yassine [727829] 
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 * 
 */
public class FileUtils {
	//costanti per i file
	private static final String SEPARATORE_ESTENSIONE=".";
	private static final String ESTENSIONE_FILE_OGGETTI=".dat";

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
					View.stampaErroreFileNonTrovato(fileCaricare);
				} catch (IOException e) {
					View.stampaErroreLettura();
				} catch (ClassNotFoundException e) {
					View.stampaErroreClasse();
				} catch (Exception e) {
					View.stampaErroreGenerico();
				}finally {
					if(input!=null) {
						try {
							input.close();
						} catch (IOException e) {
							View.stampaErroreChiusuraFile();
						} catch (Exception e) {
							View.stampaErroreGenerico();
						}
					}
				}
			} else
				View.stampaErroreEstensione();
			
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
				View.stampaErroreFileNonTrovato(fileSalvare.getAbsoluteFile());
				salvato=false;
			} catch (IOException e) {
				View.stampaErroreScrittura();
				salvato=false;
			} catch (Exception e) {
				 View.stampaErroreGenerico();
				 salvato=false;
			}finally {
				if(output!=null) {
					try {
						output.close();
					} catch (IOException e) {
						View.stampaErroreChiusuraFile();
						salvato=false;
					} catch (Exception e) {
						View.stampaErroreGenerico();
						 salvato=false;
					}
				}
			}
		}
		else
		{
			View.stampaErroreEstensione();
			salvato=false;
		}
		return salvato;
	}
}
