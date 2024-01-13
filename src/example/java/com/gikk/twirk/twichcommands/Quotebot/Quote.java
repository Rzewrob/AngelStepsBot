package com.gikk.twirk.twichcommands.Quotebot;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quote extends CommandExampleBase {
	private static String PATTERN = "!quote";



	private final Twirk twirk;

	public Quote(Twirk twirk) {
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
		int Size = twirk.AngelQuotes.size();
		String Quote = null;
		//  #angel_steps :!setQuote.*
		String TMessage = message.toString();
		Pattern p = Pattern.compile( "!quote"+"(\\d*)");
		Matcher m = p.matcher(TMessage);
		if (m.find())
		{
			Quote  =  m.group(1);
		}
		System.out.println(Quote);
		if(Quote == null) {
			try {
				Quote = ReadQuote(Quote);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else
		{
			int Random = (int)Math.floor(Math.random() * (Size - 0 ) + 0);
			try {
//				System.out.println("Got to Random Logic. Size: " + Size);
				Quote = ReadQuote(Random);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		twirk.channelMessage(Quote);


	}
	public String ReadQuote (String Quote) throws IOException {
		System.out.println(Integer.parseInt(Quote));
		//Code to check if reader is working
		for (int i = 0; i < twirk.AngelQuotes.size(); i++) {

			if (i == Integer.parseInt(Quote)) {
				System.out.println(twirk.AngelQuotes.get(i).toString());
				return twirk.AngelQuotes.get(i).toString();
			}

		}
		return null;
	}
	public String ReadQuote (int Quote) throws IOException {
		System.out.println("Random Value: "+Quote);
		//Code to check if reader is working
		for (int i = 0; i < twirk.AngelQuotes.size(); i++) {
			if (i == Quote) {
				System.out.println(twirk.AngelQuotes.get(i).toString());
				return twirk.AngelQuotes.get(i).toString();
			}

		}
		return null;
	}
}
