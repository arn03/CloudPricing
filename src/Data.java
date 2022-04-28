
public class Data {
	

	static final public int R=40000;
	static final public int L=5;
	static final public int H=730;//730 heures par mois.
	static public double SFT[]= {22,42,85,165,325};// price of month //b2 linux
	static public double FF[]= {22*0.5,42*0.5,85*0.5,165*0.5,325*0.5};//80% of price
	static public double[] pricePerHour= {0.0619, 0.1169 , 0.2369, 0.4589, 0.9029}; //b2 linux
	static public double[] pricePerHourG= {0.06701*.85, 0.13402*.85 , 0.26805*0.85, 0.53609*0.85, 1.0600*0.85}; //e2 
	static public double[] pricePerHourA= {0.0510*.85, 0.102*.85 , 0.204*0.85, 0.408*0.85, 0.816*0.85}; //a1
	static public double[] CPU24Hour= {0.1, 0.1 , 0.1, 0.1, 0.1, 0.1, 0.2 , 0.4, 0.5, 0.8, 0.8, 0.7,0.7, 0.8,0.9,0.9,0.9,0.9,0.7,0.5,0.3,0.2,0.2,0.1};
	static public long startTime=	1569880800L;
	static public int subByLevel[]= {8835,4370,1653,495,76};
	static public int userByLevel[]= {1975,1050,688,244,34};
	static public int durationUsers[]= {1200,400,320,260,180,140,80,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,64,972};
	public static int nbSub=15429;
	public static int nbUser=3991;
}
