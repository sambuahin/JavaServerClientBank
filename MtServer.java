package mid2; /**
 * Created by saana on 10/1/2016.
 */

import java.io.*;
import java.net.*;


public class MtServer {

    //want to instantiate these yet though.
    static ServerSocket server;
    static Socket client;
    static double money;//this will be used for the amount of money the user has



    //Main method begins
    public static void main(String[] args) {
        try {
            System.out.println("Server started.");
            server = new ServerSocket(2000);


            while (true) {
                client = server.accept();

                //Milestone reached - client connected.  Print to Server Console.
                System.out.println("Client connected.");

                DataInputStream reader = new DataInputStream(client.getInputStream());
                DataOutputStream writer = new DataOutputStream(client.getOutputStream());
////////////////////

                int choice = reader.readInt();//reads this value from the client and knows what choice to choose to serve the client with

                if (choice == 1) // call the method to get balance if choice equals 1
                {
                    // double res = getBalance();//gets the balance and assigns it to a variable
                    double newR = getBalance();//res*100; //multiplies result by 100 and sends it so to keep the decimal after divided by 100
                    //by client

                    String sa = Double.toString(newR);
                    writer.writeUTF(sa);
                    writer.flush();//fulshes buffered writer.

                    writer.close();
                    reader.close();
                    //close the writer
                    //writer.close();
                } else if (choice == 2) // calls the method to make a deposit if choice is equal to 2
                {
                    // int dep = br.read();
                    // String r = br.read();

                    String sa = reader.readUTF();
                    double newDep = Double.parseDouble(sa);//accepts input from client and divides by 100 to restore


                    money = getBalance();//gets the balance and assigns it to a money variable
                    double result = dePosit(money, newDep);
                    setBalance(result);

                    String saq = Double.toString(result);
                    writer.writeUTF(saq);
                    writer.flush();
                } else if (choice == 3) // if 3 is selected this calls the method to make a deposit
                {
                    //will send balance to client to be used to check for withdrawal amount
                    money = getBalance(); //gets the balance and assigns it to a money variable
                    double newM = money * 100;
                    int myInt = (int) newM;

                    writer.writeInt(myInt);
                    writer.flush();



                    int with = reader.readInt();
                    double newW = with / 100;
                    double result = withDraw(money, newW);
                    setBalance(result);

                    double newR = result * 100;

                    int myInt1 = (int) newR;

                    writer.writeInt(myInt1);
                    writer.flush();

                }

/////////////
                // System.out.println("Server Closing");
            }
        }catch (IOException IOex) {
            System.out.println("Server Error.  Connection Terminated by server.");
        }
    }

    public MtServer(double money)
    {
        this.money=money;
    }
    //this method sets the balance to a certain amount
    static void setBalance(double money)
    {
        MtServer.money =money;
    }

    //this method gets the balance
    public static double getBalance()
    {
        System.out.println("You are checking your account balance. Its $" + money);
        return money;
    }

    //this method adds money to you account
    public static double dePosit(double money, double depo)
    {
        System.out.println("You are depositing " + depo); //prints your actions to server
        double w = money + depo;//adds the amount been deposited to the outstanding amount
        return w;
    }

    //tis method takes money out of your account
    public static double withDraw(double money, double withdraw)
    {
        System.out.println("You are withdrawing "+ withdraw);//prints your actions to server

        double w = money - withdraw;//does the deduction
        return w;
    }

}
