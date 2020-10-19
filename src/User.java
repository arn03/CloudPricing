

public class User {
	private int lr;
	private double opt; // maximum price accepted
	private int rd;//release date
	private int dd;//due date
	private int duration;//duration
	
	
	public User(int T, MersenneTwisterFast r) {
		
		lr=r.nextInt(Data.L)+1;
		duration=r.nextInt(T/4)+1;
		rd=r.nextInt(T-duration)+1;
		dd=r.nextInt(T-duration-rd+1)+rd+duration;
		int rdm=r.nextInt(3);
		double pricePerH=(rdm>0)?Data.pricePerHourA[lr-1]*(1-(r.nextDouble()/4)) :(rdm>1)?Data.pricePerHourG[lr-1]*(1-(r.nextDouble()/4)):Data.pricePerHour[lr-1]*(1+(r.nextDouble()/4));
		opt=(int)(duration*pricePerH*100)/100.0;
	}
	
	public User(int lr, double opt, int rd, int dd, int duration) {
		super();
		this.lr = lr;
		this.opt = opt;
		this.rd = rd;
		this.dd = dd;
		this.duration = duration;
	}
	public int getLr() {
		return lr;
	}
	public double getOpt() {
		return opt;
	}
	public int getRd() {
		return rd;
	}
	public int getDd() {
		return dd;
	}
	public int getDuration() {
		return duration;
	}
	
}
