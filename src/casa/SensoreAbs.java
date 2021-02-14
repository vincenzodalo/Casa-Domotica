package casa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che definisce la base di un sensore
 */
public abstract class SensoreAbs extends Observer implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String nome;
	protected String stato;
	protected List<String> componenti = new ArrayList<>(); 
	
	/**
	 * Imposta il sensore alla casa domotica
	 * @param casa Casa domotica
	 */
	public void setCasa(Sistema casa)
	{
		this.casa=casa;
	}
	/**
	 * Restituisce il nome del sensore
	 * @return Nome del sensore
	 */
	public String getName()
	{
		return this.nome;
	}
	/**
	 * Restituisce lo stato attuale del sensore
	 * @return Stringa con lo stato del sensore
	 */
	public String getStato()
	{
		return this.stato;
	}
	/**
	 * Imposta lo stato del sensore
	 * @param stato Stato da impostare
	 */
	public void setStato(String stato)
	{
		this.stato=stato;
	}
	/**
	 * Restituisce la lista delle componenti del sensore
	 * @return Lista dei sensori 
	 */
	public List<String> getComponents()
	{
		return this.componenti;
	}
	/**
	 * Aggiunge una componente al sensore
	 * @param name Nome della componente
	 */
	public void addComponent(String name)
	{
		this.componenti.add(name);
	}

}
