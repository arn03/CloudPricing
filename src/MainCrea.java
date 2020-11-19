import ilog.concert.IloMPModeler;

public class MainCrea {

	public static void main(String[] args) {
		//Generation method
		String str="random"; // random or fromFile
		int nbInstByParam=10;

		//param
		int Ts[]= {24}; //{24,168,730};
		int Ss[]= {50,100,500};
		int Us[]= {50,100,500};
		double Rs[]= {0, 20, 100};// percentage of resources available compare to subscribers usage
		MersenneTwisterFast m= new MersenneTwisterFast();
		m.setSeed(123);

		//datafile
		String usrFile="user_det.csv";
		String subFile="sub_det.csv";

		switch (str) {
		case "random":
			//random creation
			for (int t: Ts) {
				for (int nbS : Ss) {
					for (int nbU: Us) {
						for (int k = 0; k < nbInstByParam; k++) {
							Instance i= new Instance(t, nbS, nbU, 100 ,m);
							for (double r: Rs) {
								try {
									String file="Price_"+t+"_"+nbS+"_"+nbU+"_"+(int)(r)+"_"+k+".dat";
									i.setR((int)(r*i.computeMinR()/100.0));
									i.write(file);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			break;
		case "fromFile":
			//random creation
			for (int t: Ts) {
				for (int k = 0; k < nbInstByParam; k++) {
					Instance i= new Instance(t, usrFile, subFile, 100 ,m);
					for (double r: Rs) {
						try {
							String file="Price_"+t+"_"+"oct2019"+"_"+(int)(r)+"_"+k+".dat";
							i.setR((int)(r*i.computeMinR()/100.0));
							i.write(file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			break;
		default:
			break;
		}

		// from datafile

	}

}
