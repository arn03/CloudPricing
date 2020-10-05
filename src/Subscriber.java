
public class Subscriber {
	private double aprioriDemand[];// 
	private double v; //coeficcien of willingness to change habits
	private int lr;// level of service required
	
	
	
	public Subscriber(double[] aprioriDemand, double v, int lr) {
		super();
		this.aprioriDemand = aprioriDemand;
		this.v = v;
		this.lr = lr;
	}
	
	public double[] getAprioriDemand() {
		return aprioriDemand;
	}
	public double getV() {
		return v;
	}
	public int getLr() {
		return lr;
	}
	
	
}
