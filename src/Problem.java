
public class Problem {
	protected Subcriber[] subscribers;
	protected User[] users;
	protected int T;// number of periods
	protected int L;//number of level of service
	protected double[] SFT;// Price for subscribers for periods 
	protected double[] FF;// 
	protected int R;// capacity for the resources
	
	
	public Problem (int T, int L, double[] SFT, double[] FF, int R, int nbSubscribers, int nbUsers) {
		this.T=T;
		this.L=L;
		this.FF=FF;
		this.SFT=SFT;
		this.R=R;
		subscribers=new Subcriber[nbSubscribers];
		users= new User[nbUsers];
		for (int i = 0; i < subscribers.length; i++) {
			subscribers[i]=generateRandomSub(T);
		}
		for (int i = 0; i < users.length; i++) {
			users[i]=generateRandomUser(T);
		}
	}
	
	public Problem (String filename) {
		//TODO
	}
	
	public boolean write(String filename) {
		//TODO
		return false;
	}

	private User generateRandomUser(int T) {
		// TODO Auto-generated method stub
		return null;
	}

	private Subcriber generateRandomSub(int T) {
		// TODO Auto-generated method stub
		return null;
	}
}
