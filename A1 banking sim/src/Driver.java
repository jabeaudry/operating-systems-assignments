import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	 /*******************************************************************************************************************************************
    	  * TODO : implement all the operations of main class   																					*
    	  ******************************************************************************************************************************************/
        
        File file = new File("account.txt", "r");
        System.out.println(file.exists());
        System.out.println(file.canRead());
    	
    	Network objNetwork = new Network("network");            /* Activate the network */
        objNetwork.start();
        Server objServer = new Server();   /* Activate the server */
        objServer.start();
        Client objSendingClient = new Client("sending");	
        objSendingClient.start();	/* Activate the client (send) */
//        Client objReceivingClient = new Client("receiving");	/* Activate the client (receive) */
//        objReceivingClient.start();
        
        /* Complete here the code for the main method ...*/
    }
}
