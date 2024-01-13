package com.gikk.twirk.twichcommands.Quotebot;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetQuote extends CommandExampleBase {
	private static String PATTERN = "!setquote";


	private final Twirk twirk;

	public SetQuote(Twirk twirk) {
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
		String Quote = null;
		//  #angel_steps :!setQuote.*
		String TMessage = message.toString();
		Pattern p = Pattern.compile( "!setquote"+"(.*)");
		Matcher m = p.matcher(TMessage);
		if (m.find())
		{
			Quote  =  m.group(1);
			Quote.trim();
//			System.out.println(Quote);
		}
		if(!Quote.isEmpty()) {
			try {
				WriteQuote(Quote);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public void WriteQuote (String Quote) throws IOException
	{
		FileWriter fw = new FileWriter("AngelQuotes.txt",true);
		try {
			// attach a file to FileWriter
			fw.write(Quote +" - Angel_steps " + Year.now().getValue() + "\n");
		}
		catch(IOException e) {
			System.out.println("Writing Failed");
		}
		System.out.println("Writing successful");
		//close the file
		fw.close();
	}

}
