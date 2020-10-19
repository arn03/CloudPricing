import ilog.concert.IloMPModeler;

public class MainCrea {

	public static void main(String[] args) {
		int Ts[]= {24,168,780};
		int Ss[]= {50,100,500};
		int Us[]= {50,100,500};
		double Rs[]= {100, 120, 200};// percentage of resources available compare to subscribers usage
		MersenneTwisterFast m= new MersenneTwisterFast();
		m.setSeed(123);

		//
		for (int t: Ts) {
			for (int nbS : Ss) {
				for (int nbU: Us) {
					for (double r: Rs) {
						try {
							Instance i= new Instance(t, nbS, nbU, r ,m);
							String file="Price_"+t+"_"+nbS+"_"+nbU+"_"+(int)(r)+".dat";
							i.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

}
