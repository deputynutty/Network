package modmuss50.network.blocks.tileentities;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 03/03/14 Time: 20:58
 */
public class TileEntityShell extends TileEntityCable {

	public String l0, l1, l2, l3, l4, l5, l6, l7, l8, l9;

	public TileEntityShell() {
		l0 = " ";
		l1 = " ";
		l2 = " ";
		l3 = " ";
		l4 = " ";
		l5 = " ";
		l6 = " ";
		l7 = " ";
		l8 = " ";
		l9 = " ";
	}

	public void processCommand(String command) {
		if (command != null) {
			if (command == "clear") {
				this.clear();
			} else if (command == "test") {
				this.printLn("This is the first test message");
				this.printLn("This is the second test message");
				this.printLn("This is the third test message");
			} else {
				this.printLn("That command does not exist!");
			}

		}

	}

	public void printLn(String text) {
		if (l9 == " ") {
			if (l0 == " ") {
				l0 = text;
			} else if (l1 == " ") {
				l1 = text;
			} else if (l2 == " ") {
				l2 = text;
			} else if (l3 == " ") {
				l3 = text;
			} else if (l4 == " ") {
				l4 = text;
			} else if (l5 == " ") {
				l5 = text;
			} else if (l6 == " ") {
				l6 = text;
			} else if (l7 == " ") {
				l7 = text;
			} else if (l8 == " ") {
				l8 = text;
			} else if (l9 == " ") {
				l9 = text;
			}
		} else {
			l0 = l1;
			l1 = l2;
			l2 = l3;
			l3 = l4;
			l4 = l5;
			l5 = l6;
			l7 = l8;
			l8 = l9;
			l9 = text;
		}
	}

	public void clear() {
		l0 = "";
		l1 = "";
		l2 = "";
		l3 = "";
		l4 = "";
		l5 = "";
		l6 = "";
		l7 = "";
		l8 = "";
		l9 = "";

	}

}
