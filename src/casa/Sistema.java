package casa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa il sistema che gestisce la casa domotica.
 */
public class Sistema implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Sensore> sensori = new ArrayList<>(); // Lista dei sensori di monitoraggio
	private List<SensoreInt> sensoriInt = new ArrayList<>(); // Lista dei sensori di intervento
	private String stato; // Stato del sistema 
	
	/**
	 * Costruttore di default che imposta il sistema nello stato di "Collaudo"
	 */
	public Sistema()
	{
		this.stato="Collaudo";
	}
	/**
	 * Aggiunge i sensori al sistema
	 * @param s Sensore di monitoraggio 
	 * @param s2 Sensore di intervento
	 */	
	public void addSensore(Sensore s, SensoreInt s2)
	{
		sensori.add(s);
		sensoriInt.add(s2);
	}
	/**
	 * Restituisce la lista dei sensori di monitoraggio
	 * @return Lista di sensori 
	 */	
	public List<Sensore> getSensori()
	{
		return this.sensori;
	}
	/**
	 * Restituisce la lista dei sensori di intervento
	 * @return Lista di sensori 
	 */	
	public List<SensoreInt> getSensoriI()
	{
		return this.sensoriInt;
	}
	/**
	 * Crea uno stamp che memorizza lo stato attuali delle
	 * liste dei sensori
	 * @return stamp dei sensori
	 */	
	public Memento createMemento()
	{
		return new SistemaMemento();
	}
	/**
	 * Restituisce lo stato attuale del sistema
	 * @return Stringa con lo stato attuale del sistema
	 */	
	public String getStato()
	{
		return this.stato;
	}
	/**
	 * Imposta lo stato del sistema 
	 * @param s Stato del sistema
	 */	
	public void setStato(String s)
	{
		this.stato=s;
		this.notifyObserver();
	}
	/**
	 * Notifica a tutti i sensori che lo stato è cambiato
	 */	
	public void notifyObserver()
	{
		for(int i=0; i<this.sensori.size();i++)
		{
			this.sensori.get(i).attiva();
			this.sensoriInt.get(i).attiva();
		}
	}
	
	/**
	 * Implementa il salvataggio di uno stato precedente
	 */	
	class SistemaMemento implements Memento,Serializable{
		private static final long serialVersionUID = 1L;
		private List<Sensore> mem_sensori = new ArrayList<>();
		private List<SensoreInt> mem_sensoriInt = new ArrayList<>();
		/**
		 * Costruttore che salva le liste allo stato attuale
		 */	
		public SistemaMemento()
		{
			for (Sensore a: sensori)
			{
				mem_sensori.add(a);
			}
			for (SensoreInt b: sensoriInt ) {
				mem_sensoriInt.add(b);
			}
		}
		/**
		 * Ripristina le due liste allo stato salvato
		 */	
		public void restoreState()
		{
			sensori.clear();
			sensoriInt.clear();
			for (Sensore a: mem_sensori)
			{
				sensori.add(a);
			}
			for (SensoreInt b: mem_sensoriInt ) {
				sensoriInt.add(b);
			}
		}
	}
	

}
