package marketplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.StringTokenizer;

public class MClient {

    private static final String USAGE = "java MClient <market_rmi_url>";
    private static final String DEFAULT_MARKET_NAME = "JMart";
    TraderAcc traderacc;
    Market market;
    private String marketname;
    String mclientname;

    //enum lists of commands available
    static enum CommandName {
        sell, buy, wish, listProducts, listTraderAccs, newTraderAcc, getTraderAcc, deleteTraderAcc, quit, help;
    };

    //Konstruktor 2
    public MClient(String marketName) {
        this.marketname = marketName;
       
        //startar rmi registret(som dns uppslagning) kopplar mot marknad server 
        try {
            try {
                
                LocateRegistry.getRegistry(1099).list();
                    } 
            catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            //binder stubb och remote objektet
            market = (Market) Naming.lookup(marketname);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to market: " + marketname);
    }

    //Default Konstruktor 1
    public MClient() {
        this(DEFAULT_MARKET_NAME);
    }

    //Kör konsolen
    public void run() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(mclientname + "@" + marketname + ">");
            try {
                String userInput = consoleIn.readLine();
                execute(parse(userInput));
            } catch (RejectedException re) {
                System.out.println(re);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //parse user input
    private Command parse(String userInput) {
        if (userInput == null) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(userInput);
        if (tokenizer.countTokens() == 0) {
            return null;
        }

        //Tokenize the inputs
        CommandName commandName = null;
        String userName = null;
        float amount = 0;
        int userInputTokenNo = 1;

        while (tokenizer.hasMoreTokens()) {
            switch (userInputTokenNo) {
                //word 1
                case 1:
                    try {
                        String commandNameString = tokenizer.nextToken();
                        commandName = CommandName.valueOf(CommandName.class, commandNameString);
                    } catch (IllegalArgumentException commandDoesNotExist) {
                        System.out.println("Illegal command");
                        return null;
                    }
                    break;
                    //word 2
                case 2:
                    userName = tokenizer.nextToken();
                    break;
                    //word 3
                case 3:
                    try {
                        amount = Float.parseFloat(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal amount");
                        return null;
                    }
                    break;
                default:
                    System.out.println("Illegal command");
                    return null;
            }
            userInputTokenNo++;
        }
       
        //returnerar ny objekt Command
        return new Command(commandName, userName, amount);
    }

    //kör Command objektet
    void execute(Command command) throws RemoteException, RejectedException {
        if (command == null) {
            return;
        }

        //Single commands
        switch (command.getCommandName()) {
            case listTraderAccs: //TODO: Rename from list to listTraderAccs
                try {
                    for (String accountHolder : market.listTraderAccs()) {
                        System.out.println(accountHolder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                return;
            case quit:
                System.exit(0);
            case help:
                for (CommandName commandName : CommandName.values()) {
                    System.out.println(commandName);
                }
                return;
                        
        }

        // all further commands require a name to be specified
        String userName = mclientname;
        if (userName == null) {
            userName = command.getName();
        }

        if (userName == null) {
            System.out.println("name is not specified");
            return;
        }
        
        //implementerar command
        switch (command.getCommandName()) {
            case newTraderAcc:
                mclientname = userName;
                
                market.newTraderAcc(command.getName());
                return;
            case deleteTraderAcc:
                mclientname = userName;
                market.deleteTraderAcc(command.getName());
                return;
        }

        // all further commands require a Account reference
        TraderAcc acc = market.getTraderAcc(userName);
        TraderAcc in = (TraderAcc) UnicastRemoteObject.exportObject(acc,0);
       
        if (acc == null) {
            System.out.println("No account for " + userName);
            return;
        } else {
            traderacc = acc;
            mclientname = userName;
        }

        //Commands
        switch (command.getCommandName()) {
            case getTraderAcc:
                acc = market.getTraderAcc(command.getName());
                mclientname = acc.getName();
                break;
            case sell:
                System.out.println("acc " + acc);
               market.sell( new ItemImpl(in,command.getName() , command.getAmount()));
                break;
            case buy:
                market.buy( new ItemImpl(in,command.getName() , command.getAmount()));
                break;
            case wish:
                market.wish(new ItemImpl(in,command.getName() , command.getAmount()));
                break;
            case listProducts: 
              List<String> l =  market.listProducts();
              for(String str : l){
                  System.out.println(str);
              }
             break; 
             
            default:
                System.out.println("Illegal command");
        }
    }

    //Klassen för Command
    private class Command {
        private String name;
        private float amount;
        private CommandName commandName;

        private String getName() {
            return name;
        }

        private float getAmount() {
            return amount;
        }

        private CommandName getCommandName() {
            return commandName;
        }

        //Command konstruktor
        private Command(MClient.CommandName commandName, String userName, float amount) {
            this.commandName = commandName;
            this.name = userName;
            this.amount = amount;
        }
    }

    //Startar Market Klienten
    public static void main(String[] args) {
        if ((args.length > 1) || (args.length > 0 && args[0].equals("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String marketName;
        if (args.length > 0) {
            marketName = args[0];
            new MClient(marketName).run();
        } else {
            new MClient().run();
        }
    }
}
