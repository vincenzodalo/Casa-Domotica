package casa;
/**
	 * Classe astratta per l'implementazione del pattern Observer
	 */
public abstract class Observer {
	protected Sistema casa; // oggetto da osservare 
	public abstract void attiva();

}
