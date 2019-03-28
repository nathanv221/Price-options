package financeProj2;

public class binTree {

	//I found it annoying to have 6 text prompts, so set your values here instead
	public static double  S_0 =100; //Initial value
	public static double T=0.25; //amount of time
	public static double sigma; //=30; //volitity
	public static int n; //=5000;  //number of steps
	public static double r=1; //riskfree rate
	
	static int K=90; //please set your strike number
	static int PutCallFuture=1; //please set to 1/2/3 to put/call/future
	
	
	public static void main(String[] args) 
	{
		for (int rep=0; rep<9; rep++)
		{
			
			if (rep <3)
				{n=10;}
			else if (rep<6)
				{n=30;}
			else if (rep<9)
				{n=50;}
			if (rep %3 ==1)
			{sigma =10;}
			else if (rep%3==2)
			{sigma = 20;}
			else if (rep%3==0)
			{sigma = 30;}
			
			
		r=r/100;
		sigma=sigma/100; //convert to decimal
		double dt =T/n;
		double ptilda=0.5*(1+((r/sigma)-(sigma/2))*dt);
		
		
		
		
		int numSpaces=0; //the total size of the array
		for (int i=0; i<=n; i++)
		{
			numSpaces=numSpaces+i;
		}
		
		
		double outtree[] = new double[numSpaces];
		outtree[0]=S_0; //sets the first place in the tree to the initial price
		outtree[1]=up(S_0);
		//outtree[2]=down(S_0);
		
		//System.out.println(outtree[0]);
		//System.out.println(outtree[1]);
		
		int numtimes=0; //this will keep track of the number of times the array is in a section
		int backspace=2;  //this will keep track of how many spaces before it is the last node on the tree
		for (int i=2; i<numSpaces; i++)
		{
			
			numtimes=numtimes+1; //this and the if statement following it are based on a pattern
			//that is hard to explain in words the graph explaining it is in my notebook
			
			if (numtimes==backspace+2)
			{
				backspace=backspace+1;
				numtimes=1;
			}
			
			if (numtimes==1)
				outtree[i]=down(outtree[i-backspace]);
			else
				outtree[i]=up(outtree[i-backspace]);
			

			//System.out.println(outtree[i]);
		}
		
		
		double[] intree = new double[numSpaces];
		if (PutCallFuture == 1)
		{
			for (int i=0; i<n; i++)
			{
				intree[i]=put(outtree[numSpaces-1-i]);
			}
		}
		
		else if (PutCallFuture == 2)
		{
			for (int i=0; i<n; i++)
			{
				intree[i]=call(outtree[numSpaces-1-i]);
				//System.out.println(intree[i] + "done");
			}
		}
		
		else if (PutCallFuture == 3)
		{
			for (int i=0; i<n; i++)
			{
				intree[i]=future(outtree[numSpaces-1-i]);
				//System.out.println(intree[i] + "done");
			}
		}
		
		
		// this loop is intended to go back to the first node with the pricing function avarged
		
		int j=0;
		int k=1;
		int tempN=n; //a placeholder for n so I can change it in the loop but not elsewhere
		//System.out.println(intree[n] +" this is n");
		for (int i=n-1; i<numSpaces-1; i++)
		{
			if(k == tempN)
			{
				tempN=tempN-1;
				j=j+1;
				k=1;
			}
			intree[i+1]=(1-ptilda)*intree[j]+(ptilda)*intree[j+1];
			j++;
			k++;
			//System.out.println(intree[i+1]);
			
			
		}
		System.out.println("the price at n=" + n + " with volatility: " +sigma+ " is: " + intree[numSpaces-1]);
	}
	}

	private static double future(double s) {
		double F=s-K;
		return F;
	}

	private static double call(double s) {
		double F=Math.max(s-K, 0);
		return F;
	}

	private static double put(double s) {
		double F=Math.max(K-s, 0);
		return F;
	}

	private static double down(double s) {
		double dt =T/n;
		double P =Math.exp(Math.log(s)+(-1*sigma*Math.sqrt(dt)));
		return P;
	}

	private static double up(double s)
	{
		double dt=T/n;
		double P =Math.exp(Math.log(s)+(sigma*Math.sqrt(dt)));
		return P;
		
	}
	
}
