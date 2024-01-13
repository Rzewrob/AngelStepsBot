package com.gikk.twirk.twichcommands.rafflecommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Raffle extends CommandExampleBase {
	private static String PATTERNA = "!RunRaffle";

	private final Twirk twirk;

	public Raffle(Twirk twirk) {
		super(CommandType.CONTENT_COMMAND);
		this.twirk = twirk;
	}
	
	@Override
	protected String getCommandWords() {
		return  PATTERNA;
	}

	@Override
	protected USER_TYPE getMinUserPrevilidge() {
		return USER_TYPE.MOD;
	}

	@Override
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
			if(twirk.RaffleUsers.isEmpty())
			{
				twirk.channelMessage("Raffle is empty");
			}
			else
			{
				TwitchUser Winner = RaffleLogic();
				if(Winner.getDisplayName() != "")
				{
					twirk.channelMessage("@" + Winner.getDisplayName() + " is our Raffle winner!");
					twirk.RaffleUsers.clear();
				}
			}
	}
	public TwitchUser RaffleLogic (){
		TwitchUser Winner;
		int min = 0;
		int max = twirk.RaffleUsers.size();
		int random_int = (int)Math.floor(Math.random() * (max - min ) + min);
		System.out.println(random_int);
		Winner = twirk.RaffleUsers.get(random_int);
		twirk.OldWinners.add(Winner.getDisplayName());
		System.out.println("Winner: " + Winner.getDisplayName() + ". Random value was " + random_int);
		try {
			WriteRaffleLogic(Winner.getDisplayName());
		}
		catch (IOException e)
		{
			System.out.println("Writing Failed");
		}
		return Winner;
	}
	public void WriteRaffleLogic (String Winner) throws IOException
	{
		FileWriter fw = new FileWriter("Winners.txt",true);
		try {
			// attach a file to FileWriter
			fw.write(Winner);
		}
		catch(IOException e) {
			System.out.println("Writing Failed");
		}
		System.out.println("Writing successful");
		//close the file
		fw.close();
	}
}
