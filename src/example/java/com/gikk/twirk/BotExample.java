package com.gikk.twirk;

import com.crazy.CheerParser;
import com.crazy.FileWriter;
import com.crazy.SubParser;
import com.gikk.twirk.commands.*;
import com.gikk.twirk.events.TwirkListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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

	private final static String patternA = "!subcount";
	private final static String patternB = "!bitcount";
	private final static String patternC = "!setbits";
	private final static String patternD = "!setsubs";
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("Welcome to this Bot example. In this example you will be able \n"
				         + "to send and receive messages from a Twitch chat channel. You will \n"
				         + "make all input directly here in the command prompt. \n\n"
				         + "Enter channel to join:");
		Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		String channel = scanner.nextLine();
		
		final Twirk twirk = new TwirkBuilder(channel, SETTINGS.MY_NICK, SETTINGS.MY_PASS)
								.setVerboseMode(true)
								.build();

		final FileWriter fileWriter = new FileWriter();
		fileWriter.initFiles(null);
		final CheerParser cheerParser = new CheerParser();
		final SubParser subParser = new SubParser();
		
		twirk.addIrcListener( getOnDisconnectListener(twirk) );
		//twirk.addIrcListener( new PatternCommandExample(twirk) );
//		twirk.addIrcListener( new PrefixCommandExample(twirk) );
		twirk.addIrcListener(new PatternTest(twirk));
		twirk.addIrcListener(new CheerPattern(twirk, fileWriter, cheerParser) );
		twirk.addIrcListener(new SubPattern(twirk, fileWriter, subParser) );

		System.out.println("To reconnect to Twitch, type .reconnect and press Enter");
		System.out.println("To exit this example, type .quit and press Enter");

		Thread.sleep(2000);
		twirk.connect();	//Connect to Twitch
		
		//As long as we don't type .quit into the command prompt, send everything we type as a message to twitch
		String line;
		while( (line = scanner.nextLine()) != null ) {
			if(".quit".equals(line)) {
				//Close the connection to Twitch, and release all resources. This will not fire the onDisconnect
				//method
				twirk.close();
				int cheerCount = twirk.getCheerCount();
				int subcount = twirk.getSubcount();
				String textToDump = "**** Final Bits: " + cheerCount + " - Final Subs: " + subcount;
				System.out.println(textToDump);
				fileWriter.writeLineToFile(textToDump, null);
				break;
			}
			else if(".reconnect".equals(line)) {
				//Close the connection to Twitch, and release all resources. This will fire the onDisconnect method
				//however, which will cause us to reconnect to Twitch.
				twirk.disconnect();
			}
			else
			{
				//Any message typed in bot will be posted in twitch chat...
//				twirk.channelMessage(line);
				if(line.equals(patternA) ) {
					System.out.println("**** Current Subs: " + twirk.getSubcount() + " for a value of " + String.format("%.2f",twirk.getSubValue()));
					System.out.println("**** Tier1Subs: " + twirk.getTier1() + " | Tier2Subs: " + twirk.getTier2() + " | Tier3Subs: " + twirk.getTier3());
				} else if(line.equals(patternB) ) {
					System.out.println("**** Current Bits: " + twirk.getCheerCount() + " for a value of " + String.format("%.2f",twirk.getCheervalue()));
				} else if(line.contains(patternC) ) {
					try {
						String newValue = line.replace(patternC, "").trim();
						int cheerCount = twirk.getCheerCount();
						twirk.setCheerCount(Integer.parseInt(newValue));
						System.out.println("**** Changed Bits - Current: " + cheerCount + " - New: " + newValue);
					} catch (Exception e) {
						System.out.println("**** Command Error - " + e);
					}
				} else if (line.contains(patternD)){
					try {
						String newValue = line.replace(patternD, "").trim();
						int subcount = twirk.getSubcount();
						twirk.setSubcount(Integer.parseInt(newValue));
						System.out.println("**** Changed Subs - Current: " + subcount + " - New: " + newValue );
					} catch (Exception e) {
						System.out.println("**** Command Error - " + e);
					}
				} else {
					System.out.println("**** Unknown Command ****");
				}
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
	public String Messagepasser(String line)
	{

		return "";
	}
}
