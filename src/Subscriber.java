
public class Subscriber {
	private Double aprioriDemand[];// 
	private double v; //coeficcien of willingness to change habits
	private int lr;// level of service required
	
	
	
	public Subscriber(int T,MersenneTwisterFast m) {
		lr=m.nextInt(Data.L)+1;
		v=(int)((m.nextDouble())*Data.SFT[lr-1]/Data.H/Math.pow(2, lr)*10000)/10000.0;
		aprioriDemand=new Double[T];
		for (int i = 0; i < aprioriDemand.length; i++) {
			aprioriDemand[i]=(int)(Math.pow(2, lr)*(Math.min((Data.CPU24Hour[i%24])*m.nextDouble()*2, 1))*100)/100.0;
		};
	}
	
	public Subscriber(Double[] aprioriDemand, double v, int lr) {
		super();
		this.aprioriDemand = aprioriDemand;
		this.v = v;
		this.lr = lr;
	}
	
	public Double[] getAprioriDemand() {
		return aprioriDemand;
	}
	public double getV() {
		return v;
	}
	public int getLr() {
		return lr;
	}
	
	
}
