package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public class ClearRaffleWinners extends CommandExampleBase{
	private static String PATTERNA = "!ClearWinners";

	private final Twirk twirk;

	public ClearRaffleWinners(Twirk twirk) {
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

					twirk.OldWinners.clear();


	}
}
