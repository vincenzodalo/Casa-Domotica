package casa;
import java.io.Serializable;
/**
 * Sensore per l'intervento per la casa domotica
 */
public class SensoreInt extends SensoreAbs implements Serializable{	
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore di default 
	 * @param nome Nome del sensore
	 */
	public SensoreInt(String nome)
	{
		this.nome=nome;
		this.stato="Non Attivo";
	}
	/**
	 * Viene impostato lo stato del sensore ad "Attivo"
	 */
	public void intervieni()
	{
		this.stato="Attivo";
	}
	/**
	 * Viene impostato lo stato del sensore ad "In Attesa"
	 */
	public void spegni()
	{
		this.stato="In attesa";
	}
	/**
	 * Viene fatto un controllo sullo stato della casa e in 
	 * base a ciò cambierà lo stato del sensore
	 */
	public void attiva()
	{
		if(casa.getStato()=="Attivato")
		{
			this.stato="In attesa";
		}
		else
		{
			this.stato="Non Attivo";
		}
	}

}
