
public class User {
	private int lr;
	private double opt; // maximum price accepted
	private int rd;//release date
	private int dd;//due date
	private int duration;//duration
	
	
	
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
