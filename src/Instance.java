import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Stack;


public class Instance {
	private final int T;
	private int R;
	private final int L;
	private final User[] users;
	private final Subscriber[] subscribers;
	private final double SFT[];
	private final double FF[];
	private final Double[][] cost;
	
	public Instance(int t, int r, int l, User[] users, Subscriber[] subscribers, double sFT[], double fF[], Double[][] cost) {
		super();
		T = t;
		R = r;
		L = l;
		this.users = users;
		this.subscribers = subscribers;
		SFT = sFT;
		FF = fF;
		this.cost = cost;
	}
	
	public Instance(int T, int S, int U, double percentageR, MersenneTwisterFast m) {
		this.T=T;
		this.L=Data.L;
		users=new User[U];
		subscribers= new Subscriber[S];
		SFT=Data.SFT;
		FF=Data.FF;
		
		//generate random instances
		
		//setup the cost
		cost=new Double[T][];
		for (int i = 0; i < cost.length; i++) {
			cost[i]=new Double[L];
			for (int j = 0; j < cost[i].length; j++) {
				cost[i][j]=(int)((FF[j]/Data.H)*100)/100.0;
			}
		}
		
		//create the users
		
		for (int i = 0; i < users.length; i++) {
			users[i]=new User(T,m);
		}
		
		//create the subscribers
		
		for (int j = 0; j < subscribers.length; j++) {
			subscribers[j]=new Subscriber(T,m);
		}
		// compute R
		int rMin=this.computeMinR();
		int demandUser=this.computeDemandusers();
		this.R=rMin+(int)(percentageR*demandUser/100);
		//System.out.println(R);
		
		
	}
	


	public Instance(int T, String userFile, String subscriberFile, double percentageR, MersenneTwisterFast m) {
		this.T=T;
		this.L=Data.L;
		SFT=Data.SFT;
		FF=Data.FF;
		
		//setup the cost
		cost=new Double[T][];
		for (int i = 0; i < cost.length; i++) {
			cost[i]=new Double[L];
			for (int j = 0; j < cost[i].length; j++) {
				cost[i][j]=(int)((FF[j]/Data.H)*100)/100.0;
			}
		}
		
		Subscriber[] subs=null;
		//read the file with data over subscribers
		try {
			BufferedReader buff = new BufferedReader(new FileReader(subscriberFile));
			String str = null;
			String[] wrd=null;
			str = buff.readLine();
			Stack<Subscriber> sub= new Stack<Subscriber>();
			while(str!=null){
				wrd=str.split(","); //0 start; 1 end; 2 level 
				Long start= Long.valueOf(wrd[0]);
				Long end= Long.valueOf(wrd[1]);
				int perStart=(int)((start-Data.startTime)/(3600))+1;
				int perEnd=(int)((end-Data.startTime)/3600)+1;
				if (perStart>T) {
					str = buff.readLine();
					continue;
				}
				//TODO refaire en considérant temps de non consomation par subscriber
				switch (wrd[2]) {
				case "b2-7":
					sub.add(new Subscriber(T, 1, perStart, perEnd, m));
					break;
				case "b2-15":
					sub.add(new Subscriber(T, 2, perStart, perEnd, m));
					break;
				case "b2-30":
					sub.add(new Subscriber(T, 3, perStart, perEnd, m));
					break;
				case "b2-60":
					sub.add(new Subscriber(T, 4, perStart, perEnd, m));
					break;
				case "b2-120":
					sub.add(new Subscriber(T, 5, perStart, perEnd, m));
					break;
				default:
					System.out.println("Type of machine not recognize");
					break;
				}		
				str = buff.readLine();
			}
			
			subs= new Subscriber[sub.size()];
			int indice=0;
			while (!sub.empty()) {
				subs[indice]=sub.pop();
				indice++;
			}			
			buff.close();
			
			
		}catch (Exception e) {	
			e.printStackTrace();
		}
		subscribers=subs;
		
		//create the users from the file
		User[] usrs=null;
		try {
			BufferedReader buff = new BufferedReader(new FileReader(userFile));
			String str = null;
			String[] wrd=null;
			str = buff.readLine();
			Stack<User> use= new Stack<User>();
			while(str!=null){
				wrd=str.split(","); //0 start; 1 end; 2 level 
				Long start= Long.valueOf(wrd[0]);
				Long end= Long.valueOf(wrd[1]);
				int perStart=(int)((start-Data.startTime)/3600)+1;
				int perEnd=(int)((end-Data.startTime)/3600)+1;
				int duration =perEnd -perStart;
				int chevauchement=0;
				if (perStart<0) {
					chevauchement=perEnd;
				}else if (perEnd>T) {
					chevauchement=T-perStart;
				}else {
					chevauchement=duration;
				}
				
				if (perStart>T || m.nextDouble()>(chevauchement*1.0/duration*1.0)) {
					str = buff.readLine();
					continue;
				}
				//adding the user from ovh and a user with the same characteristics but from another CSP
				switch (wrd[2]) {
				case "b2-7":
					use.add(new User(T, duration, 1, true,  m));
					use.add(new User(T, duration, 1, false,  m));
					break;
				case "b2-15":
					use.add(new User(T, duration, 2, true,  m));
					use.add(new User(T, duration, 2, false,  m));
					break;
				case "b2-30":
					use.add(new User(T, duration, 3, true,  m));
					use.add(new User(T, duration, 3, false,  m));
					break;
				case "b2-60":
					use.add(new User(T, duration, 4, true,  m));
					use.add(new User(T, duration, 4, false,  m));
					break;
				case "b2-120":
					use.add(new User(T, duration, 5, true,  m));
					use.add(new User(T, duration, 5, false,  m));
					break;
				default:
					System.out.println("Type of machine not recognize");
					break;
				}		
				str = buff.readLine();
			}
			
			
			//adding all users
			usrs= new User[use.size()];
			int indice=0;
			while (!use.empty()) {
				usrs[indice]=use.pop();
				indice++;
			}			
			buff.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		users=usrs;
		
		
		// compute R
		int rMin=this.computeMinR();
		int demandUser=this.computeDemandusers();
		this.R=rMin+(int)(percentageR*demandUser/100);
	}
	
	public int computeMinR() {
		int rMin=0;
		for (int i = 0; i < subscribers.length; i++) {
			rMin+=Math.pow(2,subscribers[i].getLr());
		}
		return rMin;
	}
	
	public int computeDemandusers() {
		int dem=0;
		for (int i = 0; i < users.length; i++) {
			dem+=Math.pow(2, users[i].getLr());
		}
		return dem;
	}

	public void write(String OutFileName, int mult, int multDem) {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(OutFileName));
			String s="";

			out.println("T\t" + "=\t" + T+"\t;");
			out.println("R\t" + "=\t" + R+"\t;");
			out.println("U\t" + "=\t" + users.length+"\t;");
			out.println("S\t" + "=\t" + subscribers.length+"\t;");
			out.println("L\t" + "=\t" + L+"\t;");
			

			//LevU
			out.print("levU\t" + "=\t" + "[");
			s="";
			for(int l=0;l<users.length;l++){
				s+="\t"+users[l].getLr();
				
			}
			out.print(s);
			out.println(" ];");
			
			//LevS
			out.print("levS\t" + "=\t" + "[");
			s="";
			for(int l=0;l<subscribers.length;l++){
				s+="\t"+subscribers[l].getLr();
				
			}
			out.print(s);
			out.println(" ];");
			
			//d
			out.print("d\t" + "=\t" + "[");
			s="";
			for(int l=0;l<users.length;l++){
				s+="\t"+users[l].getDuration();
				
			}
			out.print(s);
			out.println(" ];");
			
			//rd
			out.print("rd\t" + "=\t" + "[");
			s="";
			for(int l=0;l<users.length;l++){
				s+="\t"+users[l].getRd();
				
			}
			out.print(s);
			out.println(" ];");
			
			//dd
			out.print("dd\t" + "=\t" + "[");
			s="";
			for(int l=0;l<users.length;l++){
				s+="\t"+users[l].getDd();
				
			}
			out.print(s);
			out.println(" ];");
			
			//O
			out.print("O\t" + "=\t" + "[");
			s="";
			for(int l=0;l<users.length;l++){
				s+="\t"+String.format("%.4f", users[l].getOpt()*mult).replace(',', '.');
				
			}
			out.print(s);
			out.println(" ];");
			
			//v
			out.print("v\t" + "=\t" + "[");
			s="";
			for(int l=0;l<subscribers.length;l++){
				s+="\t"+String.format("%.4f", (subscribers[l].getV()*mult)/multDem).replace(',', '.');
				
			}
			out.print(s);
			out.println(" ];");
			
			//SFT
			out.print("SFT\t" + "=\t" + "[");
			s="";
			for(int l=0;l<SFT.length;l++){
				s+="\t"+SFT[l]*mult;
				
			}
			out.print(s);
			out.println(" ];");
			
			//FF
			out.print("FF\t" + "=\t" + "[");
			s="";
			for(int l=0;l<FF.length;l++){
				s+="\t"+FF[l]*mult;
				
			}
			out.print(s);
			out.println(" ];");
			
			//MaxP
			out.print("MaxP\t" + "=\t" + "[");
			s="";
			for(int l=0;l<SFT.length;l++){
				s+="\t"+Data.pricePerHour[l]*10*mult;
				
			}
			out.print(s);
			out.println(" ];");
			
			//C
			out.print("C\t" + "=\t" + "[");
			for(int l=0;l<cost.length;l++){
				s="";
				for(int m=0;m<cost[l].length;m++){
					s+="\t"+String.format("%.2f", cost[l][m]*mult).replace(',', '.');
				}
				out.println("[" + s +"\t]");
			}
			out.println("];");
			
			//demand
			out.print("demand\t" + "=\t" + "[");
			for(int l=0;l<subscribers.length;l++){
				s="";
				for(int m=0;m<subscribers[l].getAprioriDemand().length;m++){
					//s+="\t"+String.format("%.2f", subscribers[l].getAprioriDemand()[m]).replace(',', '.');
					s+="\t"+String.format("%.2f", (subscribers[l].getAprioriDemand()[m]*multDem)).replace(',', '.');
				}
				out.println("[" + s +"\t]");
			}
			out.println("];");

			

			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getT() {
		return T;
	}


	public int getR() {
		return R;
	}


	public int getL() {
		return L;
	}


	public User[] getUsers() {
		return users;
	}


	public Subscriber[] getSubscribers() {
		return subscribers;
	}


	public double[] getSFT() {
		return SFT;
	}


	public double[] getFF() {
		return FF;
	}


	public Double[][] getCost() {
		return cost;
	}

	public void setR(int r) {
		R = r;
	}
	
	
}
