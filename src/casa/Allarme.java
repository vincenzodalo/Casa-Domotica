package casa;
import java.io.Serializable;

/**
 * Implementa un allarme di un sensore
 */
public class Allarme implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String data;
	/**
	 * Costruttore di default 
	 * @param name Nome sensore
	 * @param data Data allarme
	 */
	public Allarme(String name,String data)
	{
		this.name=name;
		this.data=data;
	}
	/**
	 * Restituisce il nome del sensore che ha generato l'allarme
	 * @return Nome del sensore
	 */
	public String getName()
	{
		return this.name;
	}
	/**
	 * Restituisce la data in cui si è verificato l'allarme
	 * @return Data dell'allarme
	 */
	public String getDate()
	{
		return this.data;
	}
}
