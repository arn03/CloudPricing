
public class MainCrea {

	public static void main(String[] args) {

		int nbsite=1;
		//int nbTaches=120;
		for (int nbTaches = 30; nbTaches < 121; nbTaches+=30) {
			for (int i = 1; i < 61; i++) {
				for (int j = 1; j < 11; j++) {
					try {
					/*ProblemMS p= new ProblemMS("j"+nbTaches+""+i+"_"+j+".sm", nbsite);
					p.write("MSj"+nbTaches+""+i+"_"+j+"_"+nbsite+".sm");
					//ProblemMS p= new ProblemMS("MSj"+nbTaches+""+i+"_"+j+"_"+nbsite+".sm");
					
						//ProblemMS p= new ProblemMS("RCPSPMS_"+nbTaches+"_"+i+"_"+j+".dat");
						//p.write("RCPSPMS_NIND_"+nbTaches+"_"+i+"_"+j+".dat", 0);*/
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

	}

}
