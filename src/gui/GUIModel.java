package gui;
import casa.*;
import file.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Fornisce una semplificazione nella comunicazione con la 
 * casa domotica
 */
public class GUIModel {
	private Sistema casa;
	private Memento restore;
	
	public GUIModel() throws IOException, ClassNotFoundException, ParseException
	{
		this.casa=new Sistema();
		restore= casa.createMemento();
		if (FileAllarme.getIstance().creaFileInputStream("allarme.txt")){
		    FileAllarme.getIstance().convertiFileDiTesto("alarm");
		}
		if (FileAllarme.getIstance().creaObjectInputStream("alarm")){
		    FileAllarme.getIstance().leggiFileOggetti();
		}
		if (FileSensore.getIstance().creaFileInputStream("sensore.txt")){
		    FileSensore.getIstance().convertiFileDiTesto("sensor",casa);
		}
		if (FileSensore.getIstance().creaObjectInputStream("sensor")){
		    FileSensore.getIstance().leggiFileOggetti(casa);
		}
		if (FileSensore.getIstance().creaFileInputStream("comp.txt")){
		    FileSensore.getIstance().leggiComp(casa);
		}
	}

	/**
	 * Aggiunge i sensori alla casa domotica e li salva su un file
	 * @param name Nome sensore di monitoraggio
	 * @param n2 Nome sensore di intervento
	 */
	public void addSens(String name, String n2) throws IOException
	{
		SensoreInt s=new SensoreInt(n2);
		s.setCasa(casa);
		Sensore s2 = new Sensore(name,s);
		s2.setCasa(casa);
		FileSensore.getIstance().scriviTesto("sensore.txt", s2);
		casa.addSensore(s2,s);
	}
	/**
	 * Resetta tutti i sensori e i suoi allarmi, inoltre cancella il
	 * contenuto dei file associati
	 */
	public void resetSensori()
	{
		restore.restoreState();
		Sensore.svuotaAl();
		FileAllarme.getIstance().cancellaFile();
		FileSensore.getIstance().cancellaFile();
	}
	/**
	 * Aggiunge una componente ad un sensore e le salva in un file
	 * @param name Nome della componente
	 * @param sens Nome del sensore
	 * @return true se il sensore a cui si vuole aggiungere una componente
	 * esiste, altrimenti false
	 */
	public boolean addComp(String name,String sens) 
	{
		 List<Sensore> sensori = casa.getSensori();
	     List<SensoreInt> sensoriInt = casa.getSensoriI();
	     boolean condition = false;
	     
	        for (Sensore b : sensori) {
	            if(b.getName().contentEquals(sens))
	            {
	            	b.addComponent(name);
	            	condition=true;
	            }
	        }
	        
	        for (SensoreInt b : sensoriInt) {
	            if(b.getName().contentEquals(sens))
	            {
	            	b.addComponent(name);
	            	condition=true;
	            }
	        }
	        
            try {
				FileSensore.getIstance().scriviComp("comp.txt", sensori);
			} catch (IOException e) {
				e.printStackTrace();
			}
	  
	        return condition;	     
	}
	/**
	 * Restituisce la lista di sensori di monitoraggio in forma di matrice di stringhe 
	 * in cui la prima colonna è il nome del sensore la seconda lo stato del sensore e 
	 * la terza lo stato dell'allarme
	 * @return Matrice di stringhe
	 */
	public String[][] getSensorData() {

        List<Sensore> sensori = casa.getSensori();
        String[][] data = new String[sensori.size()][];

        int j = 0;

        for (Sensore b : sensori) {
            data[j] = new String[3];
            data[j][0] = b.getName();
            data[j][1] = b.getStato();
            data[j][2] = Boolean.toString(b.getStateAlarm());
            j++;
        }
        return data;
    }
	/**
	 * Restituisce la lista di sensori di intervento in forma di matrice di stringhe
	 * in cui la prima colonna è il nome del sensore e la seconda lo stato del sensore
	 * @return Matrice di stringhe
	 */
	public String[][] getSensorDataI() {

        List<SensoreInt> sensori = casa.getSensoriI();
        String[][] data = new String[sensori.size()][];

        int j = 0;

        for (SensoreInt b : sensori) {
            data[j] = new String[3];
            data[j][0] = b.getName();
            data[j][1] = b.getStato();
            j++;
        }
        return data;
    }
	/**
	 * Restituisce la lista di componenti in forma di matrice di stringhe
	 * in cui la prima colonna è il nome del sensore e la seconda un elenco
	 * delle sue componenti
	 * @return Matrice di stringhe
	 */
	public String[][] getSensorDataComp() {

        List<Sensore> sensori = casa.getSensori();
        List<SensoreInt> sensoriInt = casa.getSensoriI();
        String[][] data = new String[sensori.size()+sensoriInt.size()][];

        int j = 0;

        for (Sensore b : sensori) {
            data[j] = new String[2];
            data[j][0] = b.getName();
            List<String> temp = b.getComponents();
            data[j][1]="";
            for(String t : temp)
            {
            	data[j][1]+=t+" ";
            }
            j++;
        }
        
        for (SensoreInt b : sensoriInt) {
            data[j] = new String[2];
            data[j][0] = b.getName();
            List<String> temp = b.getComponents();
            data[j][1]="";
            for(String t : temp)
            {
            	data[j][1]+=t+" ";
            }
            j++;
        }
        return data;
    }
	/**
	 * Restituisce la lista di allarmi in forma di matrice di stringhe in cui
	 * la prima colonna è il sensore che ha portato l'allarme e la seconda la
	 * data dell'allarme
	 * @return Matrice di stringhe
	 */
	public String[][] getAlarm()
	{
		List<Allarme> alarms= Sensore.getAlarm();
		String[][] data = new String[alarms.size()][];

        int j = 0;

        for (Allarme b : alarms) {
            data[j] = new String[3];
            data[j][0] = b.getName();
            data[j][1] = b.getDate().toString();
            j++;
        }
        return data;
		
	}
	/**
	 * Imposta lo stato della casa con la stringa passata
	 * @param s Stringa con lo stato del sistema
	 */
	public void setStato(String s)
	{
		this.casa.setStato(s);
	}
	/**
	 * Restituisce lo stato della casa domotica
	 * @return Stringa con lo stato della casa domotica
	 */
	public String getStato()
	{
		return this.casa.getStato();
	}

}
