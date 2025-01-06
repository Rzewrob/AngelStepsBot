package com.gikk.twirk.twichcommands.rafflecommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Raffle extends CommandExampleBase {
	private static String PATTERNA = "!RunRaffle";

	private final Twirk twirk;
	private String file;

	public Raffle(Twirk twirk, String file) {
		super(CommandType.CONTENT_COMMAND);
		this.twirk = twirk;
		this.file = file;
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
				TwitchUser Winner = RaffleLogic(file);
				if(Winner.getDisplayName() != "")
				{
					twirk.channelMessage("@" + Winner.getDisplayName() + " is our Raffle winner!");
					twirk.RaffleUsers.clear();
				}
			}
	}
	public TwitchUser RaffleLogic (String file){
		TwitchUser Winner;
		int min = 0;
		int max = twirk.RaffleUsers.size();
		int random_int = (int)Math.floor(Math.random() * (max - min ) + min);
		System.out.println(random_int);
		Winner = twirk.RaffleUsers.get(random_int);
		twirk.OldWinners.add(Winner.getDisplayName());
		System.out.println("Winner: " + Winner.getDisplayName() + ". Random value was " + random_int);
		try {
			WriteRaffleLogic(Winner.getDisplayName(), file);
		}
		catch (IOException e)
		{
			System.out.println("Writing Failed");
		}
		return Winner;
	}
	public void WriteRaffleLogic (String Winner, String file) throws IOException
	{
		FileWriter fw = new FileWriter(file,true);
		BufferedReader br = new BufferedReader(new FileReader("Winners2.txt"));
		try {
			// attach a file to FileWriter
			if(br.readLine()==null) {
				//System.out.println("Fix for first entry");
				fw.write( Winner);
			}
			else {
				//System.out.println("2nd Entry");
				fw.write("\n" + Winner);
			}
		}
		catch(IOException e) {
			System.out.println("Writing Failed");
		}
		System.out.println("Writing successful");
		//close the file
		fw.close();
	}
}
