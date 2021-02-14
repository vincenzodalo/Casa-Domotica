package file;
import casa.Allarme;
import casa.Sensore;
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
/**
 * Si occupa della gestione del file per gli oggetti della classe Allarme
 */
public class FileAllarme {
	private static FileAllarme istance;
	
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
    public static FileAllarme getIstance()
    {
    	if(istance==null) {
    		istance=new FileAllarme();
    	}
    	return istance;
    }
    /**
     * Cancella il contenuto del file dove sono salvati gli allarmi
     */
    public void cancellaFile() {
    	try {
			FileWriter fw = new FileWriter("allarme.txt");
			fw.close();
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
     * Crea lo stream per la lettura di oggetti di tipo Allarme
     * @param nomefileObj Nome del file dove sono contenuti gli oggetti di tipo Allarme
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
    /**
     * Scrive un nuovo allarme nel file di testo
     * @param nomefiletxt File dove si andrà a scrivere l'allarme
     * @param a Oggetto di tipo Allarme da scrivere
     */
    public void scriviTesto(String nomefiletxt,Allarme a) throws IOException
    {
    	FileWriter fw = null;
    	BufferedWriter bw = null;
    	PrintWriter out = null;
    	try {
    	    fw = new FileWriter(nomefiletxt, true);
    	    bw = new BufferedWriter(fw);
    	    out = new PrintWriter(bw);
    	    out.println(a.getName()+"; "+a.getDate()+";");
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
     * Dalla stringa letta ricrea l'oggeto Allarme
     * @param riga Stringa letta dal file di testo
     * @return Oggetto di tipo Allarme
     */
    public Allarme parseAllarme(String riga) throws ParseException{
    	Allarme a=null;
    	String[] campi=new String[2];
    	int posSep, numCampo=0;

    	while(numCampo<2){
    	    posSep=riga.indexOf(SEPARATORE_CAMPI);
    	    campi[numCampo++]=riga.substring(0,posSep).trim();
    	    riga=riga.substring(posSep+1);
    	}
    	a=new Allarme(campi[0],campi[1]);

    	return a;
        }
        /**
        * Converte il file di testo in cui sono salvati gli allarmi in un file 
        * oggetto di Allarmi
        * @param nomefileObj Nome del file oggetto in cui scrivere
        */
        public void convertiFileDiTesto (String nomefileObj) throws IOException, ParseException {
    	File output=new File(nomefileObj);
    	FileOutputStream fos = new FileOutputStream(output);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
    	
    	Allarme a=null;
    	String riga;
    	
    	while ((riga=br.readLine())!=null){
    	    a=parseAllarme(riga);
    	    oos.writeObject(a);
    	}
    	oos.close();
        }

    /**
    * Legge gli oggetti di tipo Allarme dal file di oggetti
    */
    public void leggiFileOggetti () throws IOException, ClassNotFoundException {
	Allarme a=null;
	
	while (fis.available()!=0){
	    a=(Allarme) ois.readObject();
	    Sensore.setAlarm(a);
	}
    }

}