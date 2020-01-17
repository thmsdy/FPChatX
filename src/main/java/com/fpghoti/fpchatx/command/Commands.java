package com.fpghoti.fpchatx.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.fpghoti.fpchatx.FPChat;

public abstract class Commands {

	private static ArrayList<Commands> commands = new ArrayList<Commands>();

	public static void register(Commands cmd) {
		commands.add(cmd);
	}
	
	public static ArrayList<Commands> getCommands(){
		return commands;
	}
	
	private static boolean blocked = false;

	public static boolean performCommand(CommandSender sender, Command command, String label, String[] args) {
		if(blocked) {
			return false;
		}
		String input = label + " ";
		for (String s : args) {
			input += s + " ";
		}

		Commands match = null;
		String[] trim = null;
		StringBuilder lab = new StringBuilder();

		for(Commands cmd : commands) {
			StringBuilder l = new StringBuilder();
			String[] temp = cmd.getArgs(input, l);
			if (l.length() > lab.length()) {
				lab = l;
				match = cmd;
				trim = temp;
			}
		}

		if(match != null) {
			if (trim == null || (trim.length > 0 && trim[0].equals("?"))) {
				sender.sendMessage(ChatColor.RED + "Command:" + ChatColor.YELLOW + " " + match.getName());
				sender.sendMessage(ChatColor.RED + "Description:" + ChatColor.YELLOW + " " + match.getDescription());
				sender.sendMessage(ChatColor.RED + "Syntax:" + ChatColor.YELLOW + " " +  match.getSyntax());
				ArrayList<String> notes = match.getMisc();
				for (String note : notes) {
					sender.sendMessage(ChatColor.YELLOW + note);
				}
			} else {
				match.execute(sender, trim);
			}
		}
		return true;
	}
	
	public static void blockCommands() {
		blocked = true;
	}
	
	public static void allowCommands() {
		blocked = false;
	}

	protected FPChat plugin;
	protected String name;
	protected String syntax;
	protected String description;
	protected int minArgs;
	protected int maxArgs;
	protected ArrayList<String> labels;
	protected ArrayList<String> misc;

	public Commands(FPChat plugin) {
		this.plugin = plugin;
		this.labels = new ArrayList<String>();
		this.misc = new ArrayList<String>();
	}

	public String[] getArgs(String input, StringBuilder label) {
		boolean found = false;
		int count = 0;
		for(int i = 0; i < labels.size(); i++) {
			String lab = labels.get(i).toLowerCase();
			if (input.toLowerCase().matches(lab + "(\\s+.*|\\s*)")) {
				if (lab.length() > labels.get(count).length() || !found) {
					count = i;
					found = true;
				}
			}
		}
		if (found) {
			label = label.append(labels.get(count));
			String[] args = input.substring(label.length()).trim().split(" ");
			if (args[0].isEmpty()) {
				args = new String[0];
			}
			if (args.length >= minArgs && args.length <= maxArgs) {
				return args;
			}
		}
		return null;
	}


	public ArrayList<String> getLabels() {
		return this.labels;
	}

	public void setlabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	public String getName() {
		return this.name;
	}

	public String getSyntax() {
		return this.syntax;
	}

	public String getDescription() {
		return this.description;
	}

	public ArrayList<String> getMisc() {
		return this.misc;
	}
	
	public abstract void execute(CommandSender sender, String[] args);

}
