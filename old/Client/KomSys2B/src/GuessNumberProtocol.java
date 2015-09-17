import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class GuessNumberProtocol implements arrayClients{
	private static final int WAITING = 0;
	private static final int GETHELLO = 1;
	private static final int SENT = 2;
	private static final int GUESSNUMBER = 3;
	private static final int DONE = 4;
	private static int randNum;
	private static int guessNum;
	private int state = WAITING;
	private static int size;
	private String currentIp;

	public GuessNumberProtocol(String currentIp) {
		this.currentIp = currentIp;
	}

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			state = GETHELLO;
		} else if (state == GETHELLO) {
			if (theInput.equalsIgnoreCase("HELLO")) {
				if (array_of_clients.get(0).equals(currentIp)) {
					theOutput = "OK";
					state = SENT;
				} else {
					theOutput = "BUSY";
				}
				
			} else {
				theOutput = " You must write = HELLO , otherwise I can't continue";
			}
		} else if (state == SENT) {
			if (theInput.equalsIgnoreCase("START")) {
				Random rand = new Random();
				int Low = 1;
				int High = 100;
				randNum = rand.nextInt(High - Low) + Low;

				System.out.println("RADNNUM " + randNum);
				theOutput = "READY";
				state = GUESSNUMBER;
			} else {
				theOutput = " You must write = START , otherwise I can't continue";
			}
		} else if (state == GUESSNUMBER) {
			String tmp = null;
			for (int i = 0; i < theInput.length(); i++) {
				if (i != 0 && theInput.charAt(i - 1) == ' ') {
					tmp = theInput.charAt(i) + "";
					if (i + 1 != theInput.length()) {
						for (int j = i + 1; j < theInput.length(); j++) {
							tmp += theInput.charAt(j);
						}
					}
					i = theInput.length();
				}
			}
			if (tmp != null) {
				guessNum = Integer.parseInt(tmp);
				if (theInput.equalsIgnoreCase("GUESS " + guessNum)) {
					if (randNum == guessNum) {
						theOutput = "CORRECT, play again? (y/n)";
						state = DONE;
					} else if (randNum > guessNum) {
						theOutput = "LO";
					} else if (randNum < guessNum) {
						theOutput = "HI";
					}

				}
				
			} else {
				theOutput = " You must write = GUESS number , otherwise I can't continue";
			}
		}else if(state == DONE){
			if(theInput.equals("y")){
				theOutput = " A new game is coming right up! just write START to play a new game";
				state = SENT; 
			}else if(theInput.equals("n")){
				theOutput = " Thank you for playing! ";
			}
			else{
				theOutput = " You must write y if you want to play again or n if you want to quit , otherwise I can't continue";
			}
		}
		return theOutput;
	}
}