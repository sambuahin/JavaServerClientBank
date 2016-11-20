package mid2; /**
 * Created by saana on 10/1/2016.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class MtClient {
    static Socket client;

    //Main Method Begins
    public static void main(String[]args)
    {
//////////////////////


            Scanner s=new Scanner(System.in);//will be used to handle user inputs

            int number=0; //will be used to hold the choice user enters
            boolean isNum; //will be used to check if user enters right numbers or not

            while(number!=4)// this will exit the loop if user enters "4" which is to exit
            {
                //do - while loop checks to number entered. makes sure only integers are entered.
                //if anything else beside an integer is entered, it will loop and ask user to try again
                do {
                    System.out.println("----------------------------");
                    System.out.println("Please Enter the your choice");
                    System.out.println("----------------------------");
                    System.out.println("1: View Current Balance");
                    System.out.println("2: Deposit Money");
                    System.out.println("3: Withdraw Money");
                    System.out.println("4: Exit");

                    if (s.hasNextInt()) {
                        number = s.nextInt();
                        isNum = true;
                    } else {
                        System.out.println("Wrong entry. Enter options 1,2,3 or 4 to exit. Thanks");
                        isNum = false;
                        s.next();
                    }
                }
                while (!(isNum));
                /////action here
                if (number==1)  //if number entered of 1 first choice which is view balance is selected
                {
                    balance(number);//the balance method is called
                }
                else if (number==2)
                {
                    deposit(number);//the deposit method is called to deposit money
                }
                else if (number==3)
                {
                    withdraw(number);// the withdraw method is called to withdraw money
                }
            }
    }
    /////////////////////

    public static void balance(int num)
    {
        try
        {
            client = new Socket("localhost",2000);
            DataInputStream reader = new DataInputStream(client.getInputStream());
            DataOutputStream writer = new DataOutputStream(client.getOutputStream());

            ////////////////

            writer.writeInt(num);
            writer.flush();

            String res=reader.readUTF();
            double v = Double.parseDouble(res);
            reader.close();
            writer.close();
            System.out.println("Your account balance is $" + v);


        }
        catch (IOException IOex)
        {
            System.out.println("Server Error.  Connection terminated by client.");
        }
    }


    public static void deposit(int num)
    {
            try
            {
                client = new Socket("localhost",2000);
                DataInputStream reader = new DataInputStream(client.getInputStream());
                DataOutputStream writer = new DataOutputStream(client.getOutputStream());

           //////////////////
            Scanner c = new Scanner(System.in);

            writer.writeInt(num);//sends the choice to server
            writer.flush();//flushes the bufferwriter after every write

            ////accepts deposit amount frm user
            System.out.println("Enter deposit amount");
            double dep = c.nextDouble();
            //int newDep= (int) (dep*100);//multiplies it by 100 which will be divided by 100
            // at server to keep 2 decimal places intact

                String sa = Double.toString(dep);
                writer.writeUTF(sa);
            writer.flush();

            String result= reader.readUTF();//
                double v = Double.parseDouble(result);

            System.out.println("You deposited $"+dep);
            System.out.println("Your new balance is $" + v);

                reader.close();
                writer.close();
                client.close();

                System.out.println("Client Closing");
            }
            catch (IOException IOex)
            {
                System.out.println("Server Error.  Connection terminated by client.");
            }
        }


    public static void withdraw(int num)
    {
        try
        {
            client = new Socket("localhost",2000);
            DataInputStream reader = new DataInputStream(client.getInputStream());
            DataOutputStream writer = new DataOutputStream(client.getOutputStream());

           ///////////////////////

            Scanner c = new Scanner(System.in);

            writer.writeInt(num);//sends the choice to server
            writer.flush();//flushes the bufferwriter after every write

            //will check if input is more than balance
            int comp = reader.readInt(); //accepts the outstanding amount left from the server to be used to compare to
            //how much money will be entered by the user
            double newC = comp/100;

            System.out.println("You have a balance of $" + newC);
            double withamount=0;


            boolean ch;

            //this side checks to make sure amount been withdrawn is not more than amount left in account.
            do
            {
                System.out.println("Enter amount you want to withdraw");
                //withamount = c.nextDouble();
                if (c.hasNextDouble())
                {
                    withamount = c.nextDouble();

                    if(withamount<=newC)
                    {ch = true;}
                    else ch=false;

                } else {
                    System.out.println("Wrong input");
                    ch = false;
                    c.next();
                }
            }
            while (!(ch));

            double newW=withamount*100;

            int myInt = (int) newW;

            writer.writeInt(myInt);
            writer.flush();

            int result= reader.readInt();
            double newR=result/100;
            System.out.println("You withdrew $"+ withamount);
            System.out.println("Your new balance is $" + newR);


            reader.close();
            writer.close();
            client.close();

            System.out.println("Client Closing");
        }
        catch (IOException IOex)
        {
            System.out.println("Server Error.  Connection terminated by client.");
        }
    }

}
