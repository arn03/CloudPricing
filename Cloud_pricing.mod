/*********************************************
 * OPL 12.8.0.0 Model
 * Author: arn03
 * Creation Date: 18 sept. 2020 at 14:37:17
 *********************************************/


execute CPX_PARAM {
  cplex.tilim=3600;
  cplex.lbheur = 1;

}

int R = ...;
int U=...;
int S=...;
int L=...;
int T=...;
range levels=1..L;
range users= 1..U;
range subscribers=1..S;
range periods=1..T;
int levU[users]=...;
int d[users]=...;
int rd[users]=...;
int dd[users]=...;
int levS[subscribers]=...;
float v[subscribers]=...;
float O[users]=...;
float SFT[levels]=...;
float FF[levels]=...;
float MaxP[levels]=...;
float C[periods][levels]=...;
float demand[subscribers][periods]=...;
int coefM=...;
int coefMp=...;

//prices
dvar float+ Ps[levels][periods];
dvar float+ Pu[levels][periods];

//boolean
dvar boolean /*float+*/ I[subscribers][periods];
dvar boolean /*float+*/ X[users][periods] ;
dvar boolean /*float+*/ A[users] ;

//dual and linearization
dvar float+ omega[users];
dvar float+ mu[subscribers];
dvar float+ lambda[subscribers][periods];
dvar float+ OmegaS[subscribers][periods];
dvar float+ OmegaU[users][periods];


dexpr int Tstar[s in subscribers]=ftoi(floor(((pow(2,levS[s])*T*100-sum(t in 1..T)demand[s][t])/(pow(2,levS[s])*100))));
//dexpr float TstarF[s in subscribers]=(pow(2,levS[s])*100*T-sum(t in 1..T)demand[s][t])/(pow(2,levS[s])*100);

/*execute setting_mipsearch {
  for (var i in users){
 	cplex.setPriority(A[i], (O[i]/d[i])*100+100);
 	cplex.setDirection(A[i],"BranchUp");
	} 	
for (var i in subscribers){
  for (var t in periods){
    cplex.setPriority(I[i][t],Math.pow(2,levS[i]));
    //cplex.setDirection(A[i],"BranchUp");
  }
}
for (var i in users){
  for(var t in periods){
    cplex.setPriority(X[i][t], 0);
  }
}
}//*/

  

maximize sum(u in users, t in rd[u]..dd[u])(OmegaU[u][t]-(C[t][levU[u]]*sum(tp in maxl(t-d[u]+1,rd[u])..t)X[u][tp]))- sum(s in subscribers, t in periods)OmegaS[s][t];

subject to {
  // max on the refunds
	forall(l in levels)
		ctr1: SFT[l] >= (sum(t in periods)Ps[l][t])+FF[l];
  // resources consumption
	forall(t in periods)
		ctr2:R >= sum(u in users, tp in maxl(t-d[u]+1,rd[u])..t) (X[u][tp]*pow(2,(levU[u])))+sum(s in subscribers)(1-I[s][t])*pow(2,levS[s]);
  
  // min cost for users
	forall(t in periods, l in levels) 
		ctr3:C[t][l] <= Pu[l][t];
  //  linear omeg 1
	forall(u in users, t in rd[u]..dd[u])
   		(sum(tp in maxl(t-d[u]+1,rd[u])..t) X[u][tp])*MaxP[levU[u]]/coefM >= OmegaU[u][t];   
    // linear omega 2   
 	forall(u in users,t in rd[u]..dd[u] )
		((sum(tp in maxl(t-d[u]+1,rd[u])..t) X[u][tp])-1) * MaxP[levU[u]]/coefM + Pu[levU[u]][t]     <= OmegaU[u][t];
    // linear omega 3
    forall(u in users, t in rd[u]..dd[u])
      Pu[levU[u]][t] >= OmegaU[u][t];
    // linear omega1b
	forall(t in periods, s in subscribers)
	  ctr7:I[s][t]*MaxP[levS[s]]/coefMp >= OmegaS[s][t];
	// linear omega2b 
	forall(t in periods, s in subscribers)
	  ctr8:(I[s][t]-1)*MaxP[levS[s]]/coefMp+ Ps[levS[s]][t] <= OmegaS[s][t];
	 // linear omega3b
	forall(t in periods, s in subscribers)
	  ctr9:Ps[levS[s]][t] >= OmegaS[s][t];
	  // dual of U 1
	forall(u in users, t in rd[u]..(dd[u]-d[u]+1))
	  omega[u] <= sum(tp in t..(t+d[u]-1))Pu[levU[u]][tp];
	  // dual of u 2
	forall(u in users)
		ctr11:omega[u] <= O[u];
	 // one period for start is selected
	forall(u in users)
		ctr12:1 == A[u] + sum(t in rd[u]..(dd[u]-d[u]+1)) X[u][t];
	 // primal equal dual
	 forall(u in users)
	   ctr13:omega[u] ==A[u]*O[u]+ sum(t in rd[u]..dd[u])(OmegaU[u][t]);
	 // number of periods released
	 forall(s in subscribers)
	  ctr14: Tstar[s]>= sum(t in periods) I[s][t];
	 /* //test
	  forall(s in subscribers)
	  ctrtest: TstarF[s]>= sum(t in periods) I[s][t];*/
	  
	  //max price subscribers
    forall(t in periods, s in subscribers)
	  ctr17a:Ps[levS[s]][t] <= MaxP[levS[s]]/coefMp;
	//max price users
	forall(t in periods, u in users)
	  ctr17b:Pu[levU[u]][t] <= MaxP[levU[u]]/coefM;
	 // dual S
	 forall(s in subscribers, t in periods)
	   ctr15:mu[s]+lambda[s][t] >= Ps[levS[s]][t]-(2*v[s]*(demand[s][t]));
	 // dual= primal s
	  forall(s in subscribers)
	 ctr16:mu[s]*Tstar[s]+sum(t in periods)lambda[s][t] == sum(t in periods)(OmegaS[s][t]-(I[s][t]*2*v[s]*(demand[s][t])));
	  forall(s in subscribers, t in periods)
	     OmegaS[s][t]>=0;
	     
	    forall(u in users, t in periods)
	     OmegaU[u][t]>=0;
}



/*main {
  thisOplModel.generate();
 	

  cplex.solve();
  var ofile = new IloOplOutputFile("modelRun.txt");
  ofile.writeln(thisOplModel.printExternalData());
  ofile.writeln(thisOplModel.printInternalData());
  ofile.writeln(thisOplModel.printSolution());
  ofile.close();
}*/


/*execute DISPLAY {
   for(var i in RgMalt) {
       for(var j in Brewery) 
            if (xm[i][j] >0) writeln("malt transporte de ",i," à ",j," = ",xm[i][j]);
     }       
     
   for(var j in Brewery) 
            if (xbp[j] >0) writeln("biere produite en ",j," = ",xbp[j]);         
            
   for(var i in Brewery) {
       for(var j in Market) 
            if (xb[i][j] >0) writeln("biere transportée de ",i," à ",j," = ",xb[i][j]);
          }            
    for(var i in Brewery) {
       for(var j in Market) 
            if (xb[i][j] == 0) writeln("cout marginal de ",i," à ",j," = ",xb[i][j].reducedCost);
    }  
    //         writeln("x[",j,"]= ",x[j]," cout réduit: ",x[j].reducedCost);
  //for(var i in RgCont) 
   //     writeln("slack ",ct[i].slack," ",ct[i].dual);         

            
}*/