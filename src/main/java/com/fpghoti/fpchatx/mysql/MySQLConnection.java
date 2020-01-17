package com.fpghoti.fpchatx.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.scheduler.BukkitRunnable;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.config.MainConfig;


public class MySQLConnection{

	private FPChat plugin;
	private String host, user, password, database, port;
	private MainConfig config;

	public MySQLConnection(FPChat plugin) {
		this.plugin = plugin;
		config = plugin.getMainConfig();
		host = config.getHost();
		user = config.getUser();
		password = config.getPassword();
		database = config.getDatabase();
		port = config.getPort();
	}

	private Connection connection;

	public Connection getConnection(){
		return connection;
	}

	public void generate() {
		if(config.mySQLEnabled()){
			plugin.log(Level.INFO, "Connecting to MySQL...");
			connect();
			if(!tableExists(config.getChatFeatureTable())){
				plugin.log(Level.INFO, "FPChat table not found. Creating new table...");
				//createTable(config.getChatFeatureTable(), "player_uuid VARCHAR (36), badge_slot1 INT (11), badge_slot2 INT (11), badge_slot3 INT (11)");
				update("CREATE TABLE " + config.getChatFeatureTable() + " (player_uuid VARCHAR (36), badge_slot1 INT (11), badge_slot2 INT (11), badge_slot3 INT (11), PRIMARY KEY(player_uuid))");
				plugin.log(Level.INFO, "FPChat table created!");
			}
			if(!tableExists(config.getPermSyncTable())){
				//createTable(config.getPermSyncTable(), "player_uuid VARCHAR (36), badges TEXT");
				update("CREATE TABLE " + config.getPermSyncTable() + " (player_uuid VARCHAR (36), badges TEXT, PRIMARY KEY(player_uuid))");
			}
			plugin.log(Level.INFO, "FPChat successfully connected to MySQL!");
			plugin.log(Level.INFO, "Badges have been enabled!");
		}else{
			plugin.log(Level.INFO, "Continuing without MySQL.");
		}
	}

	public void connect(){

		if (host.equalsIgnoreCase("") || host == null) {
			plugin.log(Level.SEVERE, "You have not specified a host in the FPChatX config!");
		} else if (user.equalsIgnoreCase("") || user == null) {
			plugin.log(Level.SEVERE, "You have not specified a user in the FPChatX config!");
		} else if (password.equalsIgnoreCase("") || password == null) {
			plugin.log(Level.SEVERE, "You have not specified a password in the FPChatX config!");
		} else if (database.equalsIgnoreCase("") || database == null) {
			plugin.log(Level.SEVERE, "You have not specified a database in the FPChatX config!");
		} else {
			login();
		}
	}

	public void disconnect(){
		try{
			if (getConnection() != null){
				connection.close();
			}
			else{
				plugin.log(Level.SEVERE, "There was an issue with MySQL: FPChatX is not currently connected to a database.");
			}
		}catch(SQLException e){
			plugin.log(Level.SEVERE, "There was an issue with MySQL: " + e.getMessage());
			e.printStackTrace();
		}
		connection = null;
	}

	public void reconnect(){
		disconnect();
		connect();
	}

	public void login(){
		try{
			if (getConnection() != null){
				connection.close();
			}
		}
		catch (Exception e){}
		connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
		}catch(SQLException e){
			plugin.log(Level.SEVERE, "There was an issue with MySQL: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ResultSet query(String query){
		if (query == null) {
			return null;
		}
		connect();
		ResultSet results = null;
		try{
			Statement statement = getConnection().createStatement();
			results = statement.executeQuery(query);
		}catch(SQLException e){
			plugin.log(Level.SEVERE, "There has been an error:" + e.getMessage());
			plugin.log(Level.SEVERE,"Failed Query in MySQL using the following query input:");
			plugin.log(Level.SEVERE, query);
			e.printStackTrace();
		}
		return results;
	}

	public void update(String input){
		if (input == null){
			return;
		}
		connect();
		try{
			Statement statement = getConnection().createStatement();
			statement.executeUpdate(input);
			statement.close();
		}catch(SQLException e){
			plugin.log(Level.SEVERE, "There has been an error:" + e.getMessage());
			plugin.log(Level.SEVERE,"Failed to update MySQL using the following update input:");
			plugin.log(Level.SEVERE, input);
			e.printStackTrace();
		}
	}

	public boolean tableExists(String tablename){
		if (tablename == null) {
			return false;
		}
		try{
			if (getConnection() == null) {
				return false;
			}
			if (getConnection().getMetaData() == null) {
				return false;
			}
			ResultSet results = getConnection().getMetaData().getTables(null, null, tablename, null);
			if (results.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean itemExists(String column, String data, String table){
		if (data != null) {
			data = "'" + data + "'";
		}
		try{
			ResultSet results = query("SELECT * FROM " + table + " WHERE " + column + "=" + data);
			while (results.next()) {
				if (results.getString(column) != null) {
					return true;
				}
			}
		}catch(SQLException e) {
			plugin.log(Level.SEVERE, "MYSQL itemExists error: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void createTable(String table, String columns){
		if (!tableExists(table)) {
			update("CREATE TABLE " + table + " (" + columns + ")");
		}
	}

	public void insertInto(final String columns, final String values, final String table){
		new BukkitRunnable() {
			@Override
			public void run() {
				update("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
			}
		}.runTaskAsynchronously(plugin);
	}


	public void set(final String selected, final Object object, final String column, final String equality, final String data, final String table){
		new BukkitRunnable() {
			@Override
			public void run() {
				Object gobject = object;
				String gdata = data;
				if (gobject != null) {
					gobject = "'" + gobject + "'";
				}
				if (gdata != null) {
					gdata = "'" + gdata + "'";
				}
				update("UPDATE " + table + " SET " + selected + "=" + gobject + " WHERE " + column + equality + gdata + ";");
			}
		}.runTaskAsynchronously(plugin);
	}
	
	
	public Object get(String selected, String column, String equality, String data, String table){
		if (data != null) {
			data = "'" + data + "'";
		}
		try{
			ResultSet rs = query("SELECT * FROM " + table + " WHERE " + column + equality + data);
			if (rs.next()) {
				return rs.getObject(selected);
			}
		}catch(SQLException e) {
			plugin.log(Level.SEVERE, "MySQL get error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
