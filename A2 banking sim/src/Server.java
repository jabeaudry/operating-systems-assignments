
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package comp546pa1w2020;*/

/** Server class
 *
 * @author Kerly Titus
 */

public class Server extends Thread {
	
	/* NEW : Shared member variables are now static for the 2 receiving threads */
	private static int numberOfTransactions;         	/* Number of transactions handled by the server */
	private static int numberOfAccounts;             	/* Number of accounts stored in the server */
	private static int maxNbAccounts;                		/* maximum number of transactions */
	private static Accounts [] account;              		/* Accounts to be accessed or updated */
	
	/* NEW : member variabes to be used in PA2 with appropriate accessor and mutator methods */
	private String serverThreadId;				 /* Identification of the two server threads - Thread1, Thread2 */
	private static String serverThreadRunningStatus1;	 /* Running status of thread 1 - idle, running, terminated */
	private static String serverThreadRunningStatus2;	 /* Running status of thread 2 - idle, running, terminated */
  
    /** 
     * Constructor method of Client class
     * 
     * @return 
     * @param stid
     */
    Server(String stid)
    {
    	if ( !(Network.getServerConnectionStatus().equals("connected")))
    	{
    		System.out.println("\n Initializing the server ...");
    		numberOfTransactions = 0;
    		numberOfAccounts = 0;
    		maxNbAccounts = 100;
    		serverThreadId = stid;							/* unshared variable so each thread has its own copy */
    		serverThreadRunningStatus1 = "idle";	
    		account = new Accounts[maxNbAccounts];
    		
    		
    		
    		
    		System.out.println("\n Inializing the Accounts database ...");
    		initializeAccounts( );
    		System.out.println("\n Connecting server to network ...");
    		if (!(Network.connect(Network.getServerIP())))
    		{
    			System.out.println("\n Terminating server application, network unavailable");
    			System.exit(0);
    		}
    	}
    	else
    	{
    		serverThreadId = stid;							/* unshared variable so each thread has its own copy */
    		serverThreadRunningStatus2 = "idle";				
    	}
    }
  
    /** 
     * Accessor method of Server class
     * 
     * @return numberOfTransactions
     * @param
     */
     public int getNumberOfTransactions()
     {
         return numberOfTransactions;
     }
         
    /** 
     * Mutator method of Server class
     * 
     * @return 
     * @param nbOfTrans
     */
     public void setNumberOfTransactions(int nbOfTrans)
     { 
         numberOfTransactions = nbOfTrans;
     }

    /** 
     * Accessor method of Server class
     * 
     * @return numberOfAccounts
     * @param
     */
     public int getNumberOfAccounts()
     {
         return numberOfAccounts;
     }
         
    /** 
     * Mutator method of Server class
     * 
     * @return 
     * @param nbOfAcc
     */
     public void setNumberOfAccounts(int nbOfAcc)
     { 
         numberOfAccounts = nbOfAcc;
     }
         
     /** 
      * Accessor method of Server class
      * 
      * @return maxNbAccounts
      * @param
      */
      public int getMxNbAccounts()
      {
          return maxNbAccounts;
      }
          
     /** 
      * Mutator method of Server class
      * 
      * @return 
      * @param nbOfAcc
      */
      public void setMaxNbAccounts(int nbOfAcc)
      { 
    	  maxNbAccounts = nbOfAcc;
      }
           
      /** 
       * Accessor method of Server class
       * 
       * @return serverThreadId
       * @param
       */
       public String getServerThreadId()
       {
           return serverThreadId;
       }
           
      /** 
       * Mutator method of Server class
       * 
       * @return 
       * @param tId
       */
       public void setServerThreadId(String stid)
       { 
     	  serverThreadId = stid;
       }

       /** 
        * Accessor method of Server class
        * 
        * @return serverThreadRunningStatus1
        * @param
        */
        public String getServerThreadRunningStatus1()
        {
            return serverThreadRunningStatus1;
        }
            
       /** 
        * Mutator method of Server class
        * 
        * @return 
        * @param runningStatus
        */
        public void setServerThreadRunningStatus1(String runningStatus)
        { 
      	  serverThreadRunningStatus1 = runningStatus;
        }
        
        /** 
         * Accessor method of Server class
         * 
         * @return serverThreadRunningStatus2
         * @param
         */
         public String getServerThreadRunningStatus2()
         {
             return serverThreadRunningStatus2;
         }
             
        /** 
         * Mutator method of Server class
         * 
         * @return 
         * @param runningStatus
         */
         public void setServerThreadRunningStatus2(String runningStatus)
         { 
       	  serverThreadRunningStatus2 = runningStatus;
         }
         
    /** 
     * Initialization of the accounts from an input file
     * 
     * @return 
     * @param
     */  
     public void initializeAccounts()
     {
        Scanner inputStream = null; /* accounts input file stream */
        int i = 0;                  /* index of accounts array */
        
        try
        {
         inputStream = new Scanner(new FileInputStream("account.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File account.txt was not found");
            System.out.println("or could not be opened.");
            System.exit(0);
        }
        while (inputStream.hasNextLine())
        {
            try
            {   account[i] = new Accounts();
                account[i].setAccountNumber(inputStream.next());    /* Read account number */
                account[i].setAccountType(inputStream.next());      /* Read account type */
                account[i].setFirstName(inputStream.next());        /* Read first name */
                account[i].setLastName(inputStream.next());         /* Read last name */
                account[i].setBalance(inputStream.nextDouble());    /* Read account balance */                
            }
            catch(InputMismatchException e)
            {
                System.out.println("Line " + i + "file account.txt invalid input");
                System.exit(0);
            }
            i++;
        }
        setNumberOfAccounts(i);			/* Record the number of accounts processed */
        
        System.out.println("\n DEBUG : Server.initializeAccounts() " + getNumberOfAccounts() + " accounts processed"); 
        
        inputStream.close( );
     }
         
    /** 
     * Find and return the index position of an account 
     * 
     * @return account index position or -1
     * @param accNumber
     */
     public int findAccount(String accNumber)
     {
         int i = 0;
         
         /* Find account */
         while ( !(account[i].getAccountNumber().equals(accNumber)))
             i++;
         if (i == getNumberOfAccounts())
             return -1;
         else
             return i;
     }
     
    /** 
     * Processing of the transactions
     * 
     * @return 
     * @param trans
     */
     public boolean processTransactions(Transactions trans)
     {   
    	 
    	 int accIndex;             	/* Index position of account to update */
         double newBalance; 		/* Updated account balance */
    	 
         System.out.println("\n DEBUG : Server.processTransactions() " + getServerThreadId() ); 
         
         /* Process the accounts until the client disconnects */
         while ((!Network.getClientConnectionStatus().equals("disconnected")))
         {	 
        	// if the input buffer is empty, the server thread yields
             
        	 //while ( (Network.getInBufferStatus().equals("empty") && !Network.getClientConnectionStatus().equals("disconnected")) ) 
        	 //{ 
        		 //Thread.yield(); 	/* Yield the cpu if the network input buffer is empty */
        	 //}

        	 
        	 if (!Network.getInBufferStatus().equals("empty"))
        	 { 
        		// System.out.println("\n DEBUG : Server.processTransactions() - transferring in account " + trans.getAccountNumber()); 
        		 
        		 /* Transfer a transaction from the network input buffer */
        		 try {
					Network.transferIn(trans);
        		 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
        		 }                              

        		 accIndex = findAccount(trans.getAccountNumber());
        		 
        		 
        		 if (trans.getOperationType().equals("DEPOSIT"))
        		 {
        			 
        			 newBalance = deposit(accIndex, trans.getTransactionAmount()); 
        			 trans.setTransactionBalance(newBalance);
        			 trans.setTransactionStatus("done");
        			 
        			 //System.out.println("\n DEBUG : Server.processTransactions() - Deposit of " + trans.getTransactionAmount() + " in account " + trans.getAccountNumber()); 
        		 }
        		 else
        			 /* Process withdraw operation */
        			 if (trans.getOperationType().equals("WITHDRAW"))
        			 {
        				 
        				 newBalance = withdraw(accIndex, trans.getTransactionAmount());
        				 trans.setTransactionBalance(newBalance);
        				 trans.setTransactionStatus("done");
        				 
        				 //System.out.println("\n DEBUG : Server.processTransactions() - Withdrawal of " + trans.getTransactionAmount() + " from account " + trans.getAccountNumber()); 
        			 }
        			 else
        				 /* Process query operation */
        				 if (trans.getOperationType().equals("QUERY"))
        				 {
                            newBalance = query(accIndex);
                            trans.setTransactionBalance(newBalance);
                            trans.setTransactionStatus("done");
                            
                             //System.out.println("\n DEBUG : Server.processTransactions() - Obtaining balance from account" + trans.getAccountNumber()); 
					} 

        		// while the ouput buffer is full, the server thread yields
        		 //while (Network.getOutBufferStatus().equals("full")) 
        		 //{ 
        			 //Thread.yield();		/* Yield the cpu if the network output buffer is full */
        		 //}
        		
        		 //System.out.println("\n DEBUG : Server.processTransactions() - transferring out account " + trans.getAccountNumber()); 

        		 
        		 /* Transfer a completed transaction from the server to the network output buffer */
        		 try {
					Network.transferOut(trans);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                            		
        		 setNumberOfTransactions( (getNumberOfTransactions() +  1) ); 	/* Count the number of transactions processed */
        	 }
         }
         
         //System.out.println("\n DEBUG : Server.processTransactions() - " + getNumberOfTransactions() + " accounts updated"); 
              
         return true;
     }
         
    /** 
     * Processing of a deposit operation in an account
     * 
     * @return balance
     * @param i, amount
     */
   
     public double deposit(int i, double amount)
     {  
    	
    	 double curBalance;      /* Current account balance */
       		
    	 //synchronized block
    	 // We are using blocks on the current account that is being accessed. A synchronized method deposit would not work, as 
    	 //.this would be used by default and it references only the current thread object. Using synchronized blocks on an object
    	 //shared by both threads assures proper synchronization.
    	 	synchronized(account[i]) {
    	 		curBalance = account[i].getBalance( );          /* Get current account balance */
    	 	
	     		/* NEW : A server thread is blocked before updating the 10th , 20th, ... 70th account balance in order to simulate an inconsistency situation */
	     		if (((i + 1) % 10 ) == 0)
	     		{
	     			try {
	     					Thread.sleep(100);
	     				}
	     				catch (InterruptedException e) {
	        	
	     				} 
	     		} 
	        
	     		System.out.println("\n DEBUG : Server.deposit - " + "i " + i + " Current balance " + curBalance + " Amount " + amount + " " + getServerThreadId());
     		
     			
	     		account[i].setBalance(curBalance + amount);     /* Deposit amount in the account */
	     		 //System.out.println(account[i].getAccountNumber());
	     		return account[i].getBalance ();                /* Return updated account balance */
     		}
     }
         
    /**
     *  Processing of a withdrawal operation in an account
     * 
     * @return balance
     * @param i, amount
     */
 
     public double withdraw(int i, double amount)
     {  
    	
    	 double curBalance;      /* Current account balance */
    	//synchronized block
    	 // We are using blocks on the current account that is being accessed. A synchronized method withdraw would not work, as 
    	 //.this would be used by default and it references only the current thread object. Using synchronized blocks on an object
    	 //shared by both threads assures proper synchronization.
    	synchronized(account[i]) {
	        curBalance = account[i].getBalance( );          /* Get current account balance */
	    	account[i].setBalance(curBalance - amount);     /* Withdraw amount in the account */
	        System.out.println("\n DEBUG : Server.withdraw - " + "i " + i + " Current balance " + curBalance + " Amount " + amount + " " + getServerThreadId());
	        //System.out.println(account[i].getAccountNumber());
	
	        return account[i].getBalance ();                /* Return updated account balance */
    	}
     	
       
     }

    /**
     *  Processing of a query operation in an account
     * 
     * @return balance
     * @param i
     */
 
     public double query(int i)
     
     {  
    	 double curBalance;      /* Current account balance */
        
	    
	    curBalance = account[i].getBalance( );          /* Get current account balance */ 
	    
     	
        System.out.println("\n DEBUG : Server.query - " + "i " + i + " Current balance " + curBalance + " " + getServerThreadId()); 
        
        return curBalance;                              /* Return current account balance */
 
     }
         
     /**
      *  Create a String representation based on the Server Object
     * 
     * @return String representation
     */
     public String toString() 
     {	
    	 return ("\n server IP " + Network.getServerIP() + "connection status " + Network.getServerConnectionStatus() + "Number of accounts " + getNumberOfAccounts());
     }
     
    /**
     * Code for the run method
     * 
     * @return 
     * @param
     */
      
    public void run()
    {  
    	
 
    	Transactions trans = new Transactions();
    	long serverStartTime = 0, serverEndTime = 0;
    	System.out.println("\n DEBUG : Server.run() - starting server thread " + Network.getServerConnectionStatus());
    	
    	serverStartTime = System.currentTimeMillis();
    	
    	//System.out.print(serverThreadId);
    	
    	if (serverThreadId.equals("Thread1")) {
    		processTransactions(trans);
    		serverThreadRunningStatus1 = "terminated";
    	}
    	else if (serverThreadId.equals("Thread2")) {
    		processTransactions(trans);
    		serverThreadRunningStatus2 = "terminated";
    		
    	}
    	
    	

    	serverEndTime = System.currentTimeMillis();
    	System.out.println("\n Terminating server "+serverThreadId+" - " + " Running time " + (serverEndTime - serverStartTime) + " milliseconds");
    		//if both server threads are terminated, the server can disconnect
    		if (serverThreadRunningStatus1.equals("terminated") && serverThreadRunningStatus2.equals("terminated")) {
    			String sip = Network.getServerIP();
    	        Network.disconnect(sip);
    		}
        
    	        
        
    
	 
    	
    	/* .....................................................................................................................................................................................................*/
        
       
	
    }
}


