package com.gikk.twirk.twichcommands.rafflecommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddWinners extends CommandExampleBase {
	private static String PATTERNA = "!addwinner";

	private final Twirk twirk;

	public AddWinners(Twirk twirk) {
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
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message)  {
		try {
			WriteLogic(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String WriteLogic (TwitchMessage message) throws IOException
	{
		String str = "test";
		str = message.toString();
		Pattern p = Pattern.compile(PATTERNA+ "test");
		Matcher m = p.matcher(str);
		if (m.find())
		{
			str  =  m.group(1);
		}

		// attach a file to FileWriter
		FileWriter fw=new FileWriter("Winners.txt");

		// read character wise from string and write
		// into FileWriter
		fw.write("\n"+str);
		System.out.println("Writing successful");
		//close the file
		fw.close();
		return str;
	}
}
