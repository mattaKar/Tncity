package calculMat;

/**
 * The MatRes cells contains 3 type of informations regarding roads, power and water
 */
public enum TypeRes {
	Route,Elec,Eau;
	
	/**
	 * sets the number of networks effective
	 */
	protected static int nbRes=3;
	
	/**
	 * gives a number to each specified type of network
	 */
	public int numRes(){
		int numRes;
		switch (this){
		case Route:
			numRes=0;
			break;
		case Elec:
			numRes=1;
			break;
		case Eau:
			numRes=2;
			break;
		default:
			numRes=0;
			System.out.println("Erreur de Condition");
			break;
		}
		return numRes;
	}
	
	/**
	 * tells the Network of each specified number
	 */
	public static TypeRes intToTypeRes(int i){
		switch(i){
		case 0:
			return Route;
		case 1:
			return Elec;
		case 2:
			return Eau;
		default:
			System.out.println("Erreur de Condition");
			return Route;
		}
	}

	/**
	 * gives the number of networks being used
	 */
	public static int getNbRes() {
		return nbRes;
	}
	
}
