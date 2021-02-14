package file;
import casa.Sensore;
import casa.SensoreInt;
import casa.Sistema;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 * Si occupa della gestione del file per gli oggetti della classe Sensore
 */
public class FileSensore{
	private static FileSensore istance;
	
	final char SEPARATORE_CAMPI=';';

    File file;
    FileInputStream fis;
    InputStreamReader isr;
    ObjectInputStream ois;
    BufferedReader br;
    /**
     * Implementazione per singleton restituisce l'istanza unica
     * @return istanza unica della classe
     */
    public static FileSensore getIstance()
    {
    	if(istance==null) {
    		istance=new FileSensore();
    	}
    	return istance;
    }
    /**
     * Cancella il contenuto del file dove sono salvati i sensori e 
     * le componenti
     */
    public void cancellaFile() {
    	try {
			FileWriter fw = new FileWriter("sensore.txt");
			FileWriter fw2 = new FileWriter("comp.txt");
			fw.close();
			fw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Crea lo stream di input per la lettura del file
     * @param nomefiletxt Nome del file di testo da aprire
     * @return true se il file esiste, false se non esiste
     */
    public boolean creaFileInputStream  (String nomefiletxt) throws IOException{

    	file=new File(nomefiletxt);
    	if (file.exists()){
    	    fis=new FileInputStream(file);
    	    isr=new InputStreamReader(fis);
    	    br = new BufferedReader(isr);
    	    return true;
    	}
    	else 
    	    return false;
        }
    /**
     * Crea lo stream per la lettura di oggetti di tipo Sensore
     * @param nomefileObj Nome del file dove sono contenuti gli oggetti di tipo Sensore
     * @return true se il file esiste, false se non esiste
     */    
    public boolean creaObjectInputStream  (String nomefileObj) throws IOException{
	file=new File(nomefileObj);
	if (file.exists()){
	    fis = new FileInputStream(file);
	    ois = new ObjectInputStream(fis);
	    return true;
	}
	else 
	    return false;
    }
    
    public void scriviComp(String nomefiletxt,List <Sensore> s) throws IOException
    {
    	FileWriter fw = null;
    	BufferedWriter bw = null;
    	PrintWriter out = null;
    	try {
    	    fw = new FileWriter(nomefiletxt);
    	    bw = new BufferedWriter(fw);
    	    out = new PrintWriter(bw);
    	    for(Sensore ss : s)
    	    {
    	    	out.print(ss.getName()+"; ");
    	    	for(String st : ss.getComponents())
    	    	{
    	    		out.print(st+";");
    	    	}
    	    	out.print("\n");
    	    	
    	    	out.print(ss.getInt().getName()+"; ");
    	    	for(String st : ss.getInt().getComponents())
    	    	{
    	    		out.print(st+";");
    	    	}
    	    	out.print("\n");
    	    }
    	    out.close();
    	    bw.close();
    	    fw.close();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	finally {
    	    if(out != null)
			    out.close();
    	    try {
    	        if(bw != null)
    	            bw.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	    try {
    	        if(fw != null)
    	            fw.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
    }
    /**
     * Scrive un nuovo sensore nel file di testo
     * @param nomefiletxt File dove si andrà a scrivere il sensore
     * @param s Oggetto di tipo Sensore da scrivere
     */
    public void scriviTesto(String nomefiletxt,Sensore s) throws IOException
    {
    	FileWriter fw = null;
    	BufferedWriter bw = null;
    	PrintWriter out = null;
    	try {
    	    fw = new FileWriter(nomefiletxt, true);
    	    bw = new BufferedWriter(fw);
    	    out = new PrintWriter(bw);
    	    out.println(s.getName()+"; "+s.getInt().getName()+";");
    	    out.close();
    	    bw.close();
    	    fw.close();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	finally {
    	    if(out != null)
			    out.close();
    	    try {
    	        if(bw != null)
    	            bw.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	    try {
    	        if(fw != null)
    	            fw.close();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
    }
    /**
     * Dalla stringa letta ricrea l'oggeto Sensore
     * @param riga Stringa letta dal file di testo
     * @return Oggetto di tipo Sensore
     */
    public Sensore parseSensore(String riga) throws ParseException{
    	Sensore s=null;
    	String[] campi=new String[2];
    	int posSep=0, numCampo=0;

    	while(numCampo<2){
    	    posSep=riga.indexOf(SEPARATORE_CAMPI);
    	    campi[numCampo++]=riga.substring(0,posSep).trim();
    	    riga=riga.substring(posSep+1);
    	}
    	
    	s=new Sensore(campi[0],new SensoreInt(campi[1]));

    	return s;
        }
        /**
        * Converte il file di testo in cui sono salvati gli allarmi in un file 
        * oggetto di Allarmi
        * @param nomefileObj Nome del file oggetto in cui scrivere
        */
        public void convertiFileDiTesto (String nomefileObj,Sistema c) throws IOException, ParseException {
    	File output=new File(nomefileObj);
    	FileOutputStream fos = new FileOutputStream(output);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
    	
    	Sensore s=null;
    	String riga;
    	
    	while ((riga=br.readLine())!=null){
    	    s=parseSensore(riga);
    	    s.setCasa(c);
    	    s.getInt().setCasa(c);
    	    oos.writeObject(s);
    	}
    	oos.close();
        }

     /**
     * Legge gli oggetti di tipo Sensore dal file di oggetti
     * @param c Casa domotica da agganciare al sensore
     */
    public void leggiFileOggetti (Sistema c) throws IOException, ClassNotFoundException {
	Sensore s=null;
	
	while (fis.available()!=0){
	    s=(Sensore) ois.readObject();
	    s.setCasa(c);
	    s.getInt().setCasa(c);
	    c.addSensore(s, s.getInt());
	}
    }
    /**
    * Legge le componenti da aggiungere al sensore dal file di testo
    * @param s Casa domotica contenente la lista dei sensori
    */
    public void leggiComp(Sistema s) throws IOException
    {
    	int posSep=0,j=1;
    	List<String> campi = new ArrayList<>();
    	String riga;
    	
    	while ((riga=br.readLine())!=null){
        	while(riga.length()>1){
        	    posSep=riga.indexOf(SEPARATORE_CAMPI);
        	    campi.add(riga.substring(0,posSep).trim());
        	    riga=riga.substring(posSep+1);
        	}
        	if(campi.size()>1)
        	{
        		for(Sensore sen: s.getSensori())
        		{
        			if(sen.getName().contentEquals(campi.get(0)))
        			{
        				for(j=1;j<campi.size();j++)
        				{
        					sen.addComponent(campi.get(j));
        				}
        			}
        		}
        		for(SensoreInt sen: s.getSensoriI())
        		{
        			if(sen.getName().contentEquals(campi.get(0)))
        			{
        				for(j=1;j<campi.size();j++)
        				{
        					sen.addComponent(campi.get(j));
        				}
        			}
        		}
        		campi.clear();
        	}
    	}
    }

}