package com.gikk.twirk;

import com.gikk.twirk.twichcommands.*;
import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.twichcommands.Quotebot.Quote;
import com.gikk.twirk.twichcommands.Quotebot.SetQuote;
import com.gikk.twirk.twichcommands.rafflecommands.JoinRaffle;
import com.gikk.twirk.twichcommands.rafflecommands.Raffle;
import com.timer.CountDownTimer;
import kotlin.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**Simple example of how Twirk can be used. <br><br>
 * 
 * Make sure that you replace the <code>SETTINGS.MY_NICK</code> and <code>SETTINGS.MY_PASS</code> with the 
 * proper values. To generate a Twitch Irc password, visit 
 * <a href="https://twitchapps.com/tmi/"> https://twitchapps.com/tmi/ </a>
 * 
 * @author Gikkman
 *
 */
public class BotExample {
	public int MoneyValue = 0;
	public int Bitcount =0;
	public int Subcount =0;

	public int seconds = 0;





	public static void main(String[] args) throws IOException, InterruptedException{
		//Is rafflebot active
		boolean RaffleActive = true;
		System.out.println("Welcome to this Bot example. In this example you will be able \n"
				         + "to send and receive messages from a Twitch chat channel. You will \n"
				         + "make all input directly here in the command prompt. \n\n"
				         + "Enter channel to join:");


		CountDownTimer watch = new CountDownTimer(10);
		Thread thread = new Thread(watch);
		Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		String channel = scanner.nextLine();
		System.out.println("Please input file for raffles (i.e winners2.txt)");
		String file = scanner.nextLine();

		
		final Twirk twirk = new TwirkBuilder(channel, SETTINGS.MY_NICK, SETTINGS.MY_PASS)
								.setVerboseMode(true)
								.build();


		ReadinWinnersFile(file,twirk);
		Quotesstarter(twirk);


		twirk.addIrcListener( getOnDisconnectListener(twirk) );
//		twirk.addIrcListener(new SetQuote(twirk));
//		twirk.addIrcListener(new Quote(twirk));
		twirk.addIrcListener( new JoinRaffle(twirk) );
		twirk.addIrcListener( new Counts(twirk) );
		twirk.addIrcListener( new Raffle(twirk) );
		twirk.addIrcListener(new PatternTest(twirk));
		twirk.addIrcListener(new CheerPattern(twirk) );
		twirk.addIrcListener(new SubPattern(twirk) );
		twirk.addIrcListener(new Online(twirk));
		twirk.addIrcListener( new Raffle(twirk) );

		System.out.println("To reconnect to Twitch, type .reconnect and press Enter");
		System.out.println("To exit this example, type .quit and press Enter");

		Thread.sleep(2000);
		twirk.connect();	//Connect to Twitch
		
		//As long as we don't type .quit into the command prompt, send everything we type as a message to twitch
		String line;
		while( (line = scanner.nextLine()) != null ) {

			if(".quit".equals(line)) {
				Quit(twirk);
				break;
			}
			else if(".reconnect".equals(line)) {
				//Close the connection to Twitch, and release all resources. This will fire the onDisconnect method
				//however, which will cause us to reconnect to Twitch.
				twirk.disconnect();
			}
			else if (".togglebot".equals(line))
			{
				ToggleRafflebot(twirk,RaffleActive);
			}
			else
			{
				SetandListommands(twirk,line);
			}
		}

		scanner.close();	//Close the scanner
	}

	private static TwirkListener getOnDisconnectListener(final Twirk twirk) {
		
		return new TwirkListener() {
			@Override
			public void onDisconnect() {
				//Twitch might sometimes disconnects us from chat. If so, try to reconnect. 
				try { 
					if( !twirk.connect() )
						//Reconnecting might fail, for some reason. If so, close the connection and release resources.
						twirk.close();
				} 
				catch (IOException e) { 
					//If reconnection threw an IO exception, close the connection and release resources.
					twirk.close(); 
				} 
				catch (InterruptedException ignored) {  }
			}
		};
	}
	public static void ReadinWinnersFile (String Filename, Twirk twirk)
	{
		//Read in winners to Winners Logic
		FileReader fr=null;
		try {
			fr = new FileReader(Filename);
			BufferedReader inStream = new BufferedReader(fr);
			String inString;
			while ((inString = inStream.readLine()) != null) {
				String User[] = inString.split(" ");
				twirk.OldWinners.add(inString);
			}
			// close the file
			inStream.close();
			fr.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public static void Quotesstarter (Twirk twirk)
	{

		//Read in quotes file
		FileReader AQ = null;
		try {
			AQ = new FileReader("AngelQuotes.txt");
			BufferedReader inStream = new BufferedReader(AQ);
			String inString;
			while ((inString = inStream.readLine()) != null) {
				String User[] = inString.split(" ");
				twirk.AngelQuotes.add(inString);
			}
			// close the file
			inStream.close();
			AQ.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public static void Quit(Twirk twirk)
	{
		//Close the connection to Twitch, and release all resources. This will not fire the onDisconnect
		//method
		twirk.close();
		int cheerCount = twirk.getCheerCount();
		int subcount = twirk.getSubcount();
		String textToDump = "**** Final Bits: " + cheerCount + " - Final Subs: " + subcount;
		System.out.println(textToDump);
		twirk.fileWriter.writeLineToFile("Dono", textToDump);
	}
	public static void SetandListommands(Twirk twirk, String line)
	{
			//Lit of other commands
			Pair<String,String> listCommand = new Pair<>("!list", "Lists all Commands");
			Pair<String,String> subCountCommand = new Pair<>("!subcount", "Dumps the subcount to terminal");
			Pair<String,String> bitCountCommand = new Pair<>("!bitcount", "Dumps the bits to terminal");
			Pair<String,String> setBitsCommand = new Pair<>("!setbits", "Set the value for bits - Takes Integer");
			Pair<String,String> setSubsCommand = new Pair<>("!setsubs", "Set the value for subs - Takes Integers separated by comas - 0,0,0,0,0 - Subs,Tier1,Tier2,Tier3,TierPrime - Bad formatting will set all or individual ones to 0");

			ArrayList<Pair<String,String>> commandList = new ArrayList<>();
			commandList.add(listCommand);
			commandList.add(subCountCommand);
			commandList.add(bitCountCommand);
			commandList.add(setBitsCommand);
			commandList.add(setSubsCommand);
			//Any message typed in bot will be posted in twitch chat...
			//Handles logic for subcountCommand
			if(line.equals(subCountCommand.getFirst()) ) {
				System.out.println("**** Current Subs: " + twirk.getSubcount() + " for a value of " + String.format("%.2f",twirk.getSubValue()));
				System.out.println("**** Tier1Subs: " + twirk.getTier1() + " | Tier2Subs: " + twirk.getTier2() + " | Tier3Subs: " + twirk.getTier3() + " | TierPrimeSubs: " + twirk.getTierPrime());
			}
			//Hanldes Bit count Command
			else if(line.equals(bitCountCommand.getFirst()) ) {
				System.out.println("**** Current Bits: " + twirk.getCheerCount() + " for a value of " + String.format("%.2f",twirk.getCheervalue()));
			}
			//Handles Setbits command
			else if(line.contains(setBitsCommand.getFirst()) ) {
				try {
					String newValue = line.replace(setBitsCommand.getFirst(), "").trim();
					int cheerCount = twirk.getCheerCount();
					twirk.setCheerCount(Integer.parseInt(newValue));
					System.out.println("**** Changed Bits - Current: " + cheerCount + " - New: " + newValue);
				} catch (Exception e) {
					System.out.println("**** Command Error - " + e);
				}
			}
			//Handles Set subs command
			else if (line.contains(setSubsCommand.getFirst())){
				try {
					String newValue = line.replace(setSubsCommand.getFirst(), "").trim();
					String[] values = newValue.split(",");
					int subcount = twirk.getSubcount();
					int tier1 = twirk.getTier1();
					int tier2 = twirk.getTier2();
					int tier3 = twirk.getTier3();
					int tierprime = twirk.getTierPrime();
					twirk.setSubcount(Integer.parseInt(values[0]));
					twirk.setTier1(Integer.parseInt(values[1]));
					twirk.setTier2(Integer.parseInt(values[2]));
					twirk.setTier3(Integer.parseInt(values[3]));
					twirk.setTier3(Integer.parseInt(values[4]));
					System.out.println("**** Changed Subs - Current: " + subcount + "," + tier1 + "," + tier2 + "," + tier3 + "," + tierprime + " - New: " + values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] );
				} catch (Exception e) {
					System.out.println("**** Command Error - " + e);
				}
			}
			//handles printing out commands
			else if (line.equals(listCommand.getFirst())) {
				commandList.forEach( it ->
						System.out.println(it.getFirst() + " - " + it.getSecond())
				);
			} else {
				System.out.println("**** Unknown Command ****");
			}
	}
	public static void ToggleRafflebot(Twirk twirk, boolean RaffleActive)
	{
		if(RaffleActive)
		{
			twirk.removeIrcListener( new Raffle(twirk));
			System.out.println("Raffle Bot Closed");
		}
		else
		{
			twirk.addIrcListener( new Raffle(twirk) );
			System.out.println("Raffle Bot Openmed");
		}
	}
}
