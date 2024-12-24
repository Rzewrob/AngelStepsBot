package com.gikk.twirk.twichcommands.EventShoutouts;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartEventMessages extends CommandExampleBase {
	private static String PATTERN1 = "!Start";
	private static String PATTERN2 = "!Pause";
	private static String PATTERN3 = "!Next";
	private static String PATTERN4 = "!Back";
	private List<String> EventsList;

	public volatile int Index = 0;
	public volatile long delaytime = 12000;

	public volatile boolean	Pause = false;
	public boolean StartRunning = false;
	Iterator<String> iterator;



	private final Twirk twirk;

	public StartEventMessages(Twirk twirk, List<String> Events) {
		super(CommandType.CONTENT_COMMAND);
		this.twirk = twirk;
		EventsList = Events;
	}

	@Override
	protected String getCommandWords() {
		return PATTERN1 + "|" + PATTERN2 + "|" + PATTERN3 + "|" + PATTERN4;
	}

	@Override
	protected USER_TYPE getMinUserPrevilidge() {
		return USER_TYPE.DEFAULT;
	}

	@Override
	protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
		String Command = null;
		//  #angel_steps :!setQuote.*

		String TMessage = message.toString();
		TMessage.toLowerCase();

		if (TMessage.contains("!start"))
		{
			Start();
//			System.out.println("Start Test");

		}
		else if(TMessage.contains("!pause"))
		{
			Pause();
//			System.out.println("Pause Test");
		}
		else if (TMessage.contains("!next")	)
		{
			Next();
//			System.out.println("Next Test");
		}
		else if (TMessage.contains("!back")	)
		{
			Back();
//			System.out.println("Next Back");
		}
		else{
			System.out.println("Error in Event Mangement");
		}

	}
	public void Start ()  {
		if(!StartRunning) {
			System.out.println("Start Running");
			ExecutorService execService = Executors.newFixedThreadPool(1);
			//Below contains code to be executed in Run()
			execService.execute(new Runnable() {
				public void run() {
					StartRunning = true;


//                    System.out.println("Size of list " + EventsList.size());
					//Loop through the list of users and shouts them out.
					while (EventsList.iterator().hasNext() || Index < EventsList.size()) {
						System.out.println("While loop is running");
						//If last user does not exist we just break loop before we hold a thread for 2 minutes
						if (Index+2 > EventsList.size()) {
							twirk.channelMessage("Please congratulate");
							break;
						}
						//Pause logic for loop
						System.out.println("Pause is " +Pause + " before pause section");
						if (Pause) {
							try {
								System.out.println("Paused");
								Thread.sleep(10);
								continue;
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}
						}
						//First Shoutout for Event
						if (Index == 0) {
                            try {
								Thread.sleep(5000);
								twirk.channelMessage("Please cheer our next act!  " + EventsList.get(Index) + ". Performing " + EventsList.get(Index + 1));
								Thread.sleep(5000);
								twirk.channelMessage("Please go support the performers! " + EventsList.get(Index+3) + " " + EventsList.get(Index+4));
								Index += 5;
								Thread.sleep(5000);
							} catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            continue;
						}
						//Delay timing before next shoutout and to give time to pause between events
						delaytime = CalcDelayTime();
						try {
							Thread.sleep(delaytime);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						//Action to shoutout rest of events but with a delay
						try {
							Thread.sleep(5000);
							twirk.channelMessage("Please cheer our next act  " + EventsList.get(Index) + "!!! Performing " + EventsList.get(Index + 1));
							Thread.sleep(5000);
							twirk.channelMessage("Please go support the performers! " + EventsList.get(Index+3) + "  " + EventsList.get(Index+4));
							Thread.sleep(5000);
							Index += 5;
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				}
			});
		}
	}
	public void Pause ()  {
		Pause = !Pause;
		System.out.println("Pause is " + Pause);
	}
	public void Next (){
		Index+=5;
		System.out.println("Index is now " + Index + " or next Singer is " + EventsList.get(Index));
	}
	public void Back (){
		Index-=10;
		System.out.println("Index is now " + Index + " or next Singer is " + EventsList.get(Index));
	}
	public long CalcDelayTime()
	{
//		System.out.println("Index 1: " +EventsList.get(Index) + "   Index 2: " EventsList.get(Index + 1))
		double Minutes = Double.parseDouble(EventsList.get(Index+2));
		long Millis = (long) (Minutes * 60 * 1000);
		return Millis;
	}



}
