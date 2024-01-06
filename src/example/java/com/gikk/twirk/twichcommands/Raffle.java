package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.ArrayList;

public class Raffle extends CommandExampleBase{
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
					twirk.channelMessage(Winner.getDisplayName() + " is our Raffle winner!");
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
		return Winner;
	}
}
