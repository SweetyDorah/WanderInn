package Login;
import java.io.*;
import java.util.*;
import Dash.Dashboard;
import Dash.Admin;

class IncorrectUsernameException extends Exception{
    public IncorrectUsernameException(String message){
        super(message);
    }
}

class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}

public class Login {
    String name;
    String password;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static void checkUser(String name,String password){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\Login.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                String namefromcsv = fields[0].trim();
                String passwordfromcsv = fields[1].trim();

                try {
                    if (name.equalsIgnoreCase(namefromcsv)) {
                        if (password.equalsIgnoreCase(passwordfromcsv)) {
                            System.out.println("                                                                      LOGIN SUCCESSFULL!                                                 ");
                            System.out.println("\n\n                                                                          DASHBOARD                                                 ");
                            System.out.println("                                                                          ---------                                                              ");
                            System.out.println("                                                                        WELCOME,"+name.toUpperCase()+"                                                 ");
                            Dashboard dashboard = new Dashboard();
                            dashboard.getDestination();
                            return;
                        } else {
                            throw new IncorrectPasswordException("~Incorrect password");
                        }
                    }
                }
                catch(IncorrectPasswordException e) {
                    System.out.println(e.getMessage());
                    askForCredentials();
                    return;
                }
            }
            throw new IncorrectUsernameException("~Username not found");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(IncorrectUsernameException e){
            System.out.println(e.getMessage());
            askForCredentials();
        }
    }

    public static void askForCredentials(){
        Scanner z=new Scanner(System.in);
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Name:");
        String name = z.nextLine();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Password:");
        String password = z.nextLine();
        checkUser(name,password);
    }

    public static void create_account(String name,String password){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\Login.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(name + "," + password);
            bw.newLine();
            bw.flush();
            System.out.println("                                                                     ACCOUNT CREATED!                                                                           ");
            Dashboard dashboard = new Dashboard();
            dashboard.getDestination();
            return;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Scanner x = new Scanner(System.in);
        System.out.println("\n                                                                   WELCOME TO WANDERINN!                                              ");
        System.out.print("                                                                   ---------------------                                                                   \n");
        System.out.println("\nFinding a place in short order has never been easier . WanderInn allows you to search for hotels nearby ,fill in a few details, and secure your reservation.");
        System.out.println("\n                                                     1.CUSTOMER           2.ADMIN            3.EXIT                          ");
        System.out.print("\nEnter Your Choice:");
        int choice=x.nextInt();
        x.nextLine();
        if(choice==1){
            System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("                                                               1.SIGNUP           2.LOGIN                                                                           ");
            System.out.print("Enter Your Choice(1/2):");
            int ch=x.nextInt();
            if(ch==1){
                x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("\nName:");
                String name = x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("Password:");
                String password = x.nextLine();
                create_account(name,password);
                return;
            }
            else if(ch==2){
                x.nextLine();
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("Name:");
                String name = x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.print("Password:");
                String password = x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                checkUser(name,password);
            }
        }
        else if(choice==2){
            Admin.admin_page();;
            return;
        }
        else if(choice==3){
            System.exit(1);
        }
    }
}
