package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.ArrayList;

public class JoinRaffle extends CommandExampleBase{
	private static String PATTERN = "!joinraffle";

	
	private final Twirk twirk;
	
	public JoinRaffle(Twirk twirk) {
		super(CommandType.CONTENT_COMMAND);
		this.twirk = twirk;
	}
	
	@Override
	protected String getCommandWords() {
		return PATTERN;
	}

	@Override
	protected USER_TYPE getMinUserPrevilidge() {
		return USER_TYPE.DEFAULT;
	}

	@Override
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {

			JoinLogic(sender);

	}
	public void JoinLogic (TwitchUser sender){
		if(!twirk.OldWinners.contains(sender.getDisplayName())) {
			twirk.RaffleUsers.add(sender);
			for(String i: twirk.OldWinners)
			{
				System.out.println("OlD Winner values: " + i.toString());

			}
			System.out.println("sender to string: " + sender.toString());
			System.out.println("Added " + sender.getDisplayName() + " to Sender");
		}
		else
		{
			System.out.println("User is in winner column");
		}
	}
}
