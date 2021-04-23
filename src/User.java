

public class User {
	private int lr;
	private double opt; // maximum price accepted
	private int rd;//release date
	private int dd;//due date
	private int duration;//duration
	
	
	public User(int T, MersenneTwisterFast r) {
		
		lr=drawLevelForUser(r);
		duration=drawDurationUser(r, T);
		rd=r.nextInt(T-duration+1)+1;
		dd=r.nextInt(T-duration-rd+2)+rd+duration-1;
		int rdm=r.nextInt(3);
		double pricePerH=(rdm>0)?Data.pricePerHourA[lr-1]*(1-(r.nextDouble()/4)) :(rdm>1)?Data.pricePerHourG[lr-1]*(1-(r.nextDouble()/4)):Data.pricePerHour[lr-1]*(1+(r.nextDouble()/4));
		opt=(int)(duration*pricePerH*1000)/1000.0;
	}
	
	private int drawLevelForUser(MersenneTwisterFast m) {
		int rand=m.nextInt(Data.nbUser);
		int i=1;
		while(rand>Data.userByLevel[i-1]) {
			rand-=Data.userByLevel[i-1];
			i++;
		}
		return i;
	}
	
	private int drawDurationUser(MersenneTwisterFast r, int T) {
		int rand=r.nextInt(Data.nbUser);
		int i=1;
		while(rand>Data.durationUsers[i-1] && i<T) {
			rand-=Data.durationUsers[i-1];
			i++;
		}
		return i;
	}

	public User(int T, int duratio, int level, boolean OVH, MersenneTwisterFast r) {
		
		lr=level;
		this.duration=Math.min(duratio, T);
		if (T==duration) {
			rd=1;
			dd=T;
		}else {
			rd=r.nextInt(T-duration+1)+1;
			dd=r.nextInt(T-duration-rd+2)+rd+duration-1;
		}
		
		int rdm=r.nextInt(2);
		double pricePerH;
		if (OVH) {
			pricePerH=Data.pricePerHour[lr-1]*(1+(r.nextDouble()/4));
		}else {
			pricePerH=(rdm>0)?Data.pricePerHourA[lr-1]*(1-(r.nextDouble()/4)):Data.pricePerHourG[lr-1]*(1-(r.nextDouble()/4));
		}
		
		opt=(int)(duration*pricePerH*1000)/1000.0;
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
