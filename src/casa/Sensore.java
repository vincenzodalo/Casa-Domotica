package casa;
import file.FileAllarme;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Sensore per il monitoraggio della casa domotica
 */
public class Sensore extends SensoreAbs implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int SOGLIA=200; // oltre questo valore scatta l'allarme
	private boolean allarme;
	private SensoreInt sensore; // sensore per l'intervento associato
	private static List<Allarme> alarmList = new ArrayList<>(); // lista di allarmi
	/**
	 * Costruttore di default
	 * @param nome Nome del sensore
	 * @param sensore Sensore di intervento associato
	 */
	public Sensore(String nome, SensoreInt sensore)
	{
		this.nome=nome;
		this.stato="Non Attivo";
		this.sensore=sensore;
		this.allarme=false;
	}
	/**
	 * Restituisce il sensore di intervento associato
	 * @return Sensore di intervento
	 */
	public SensoreInt getInt()
	{
		return this.sensore;
	}
	/**
	 * Restituisce lo stato dell'allarme
	 * @return Stato dell'allarme
	 */
	public boolean getStateAlarm()
	{
		return this.allarme;
	}
	/**
	 * Restituisce la lista degli allarmi che si sono verificati
	 * @return Lista degli allarmi
	 */
	public static List<Allarme> getAlarm()
	{
		return alarmList;
	}
	/**
	 * Aggiunge un allarme alla lista degi allarmi
	 * @param a Allarme da aggiungere alla lista
	 */
	public static void setAlarm(Allarme a)
	{
		Sensore.alarmList.add(a);
	}
	/**
	 * Svuota la lista degli allarmi
	 */
	public static void svuotaAl()
	{
		Sensore.alarmList.clear();
	}
	/**
	 * Viene fatto un controllo sullo stato della casa e in 
	 * base a ciò cambierà lo stato del sensore
	 */
	public void attiva()
	{
		Runnable runnable = () -> { 
		    while(casa.getStato()=="Attivato")
		    {
		    	try {
					monitora();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
		    }
		};
		Thread t = new Thread(runnable);
		if(casa.getStato()=="Attivato")
		{
			this.stato="Attivo";
			t.start();
		}
		else
		{
			this.stato="Non Attivo";
			this.allarme=false;
			t.interrupt();
		}
		
	}
	/**
	 * Monitora se il valore che rileva supera una certa soglia
	 * e nel caso fa intervenire il sensore
	 */
	private void monitora () throws IOException, ClassNotFoundException
	{
		int val= (int)(Math.random() * 256);
		
		if(val>SOGLIA)
		{
			Allarme a = new Allarme(this.getName(),new Date().toString());
			Sensore.alarmList.add(a);
			FileAllarme.getIstance().scriviTesto("allarme.txt", a);
			this.allarme=true;
			this.sensore.intervieni();
		}
		else 
		{
			this.allarme=false;
			this.sensore.spegni();
		}
	}
	

}
