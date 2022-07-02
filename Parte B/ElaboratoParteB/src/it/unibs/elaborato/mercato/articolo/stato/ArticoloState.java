package it.unibs.elaborato.mercato.articolo.stato;

/**
 * Interfaccia di stato contenete i prototipi dei metodi relativi ad ogni stato possibile dell' articolo (pattern STATE)
 * @author Aboufaris Yassine [727829]
 * @author Arcaini Matteo [729794]
 * @author Piantoni Dario [730057]
 */
public interface ArticoloState {
	public static final String APERTA="APERTA";
	public static final String CHIUSA="CHIUSA";
	public static final String IN_SCAMBIO="IN_SCAMBIO";
	public static final String RITIRATA="RITIRATA";
	public static final String ACCOPPIATA="ACCOPPIATA";
	public static final String SELEZIONATA="SELEZIONATA";
	void aperta();

	void chiusa();

	void inScambio();

	void ritirata();

	void accoppiata();

	void selezionata();

}
