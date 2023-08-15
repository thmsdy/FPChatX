package com.fpghoti.fpchatx.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import com.fpghoti.fpchatx.FPChat;
import com.fpghoti.fpchatx.config.MainConfig;
import com.zaxxer.hikari.HikariDataSource;


public class MySQLConnection{

	private FPChat plugin;
	private String host, user, password, database, port;
	private MainConfig config;
	private HikariDataSource hikari;

	public MySQLConnection(FPChat plugin) {
		this.plugin = plugin;
		config = plugin.getMainConfig();
		host = config.getHost();
		user = config.getUser();
		password = config.getPassword();
		database = config.getDatabase();
		port = config.getPort();
		hikari = new HikariDataSource();
		hikari.setMaximumPoolSize(10);
		hikari.addDataSourceProperty("user", user);
		hikari.addDataSourceProperty("password", password);
		if(config.useMariaDBDriver()) {
			hikari.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
			hikari.addDataSourceProperty("url", "jdbc:mariadb://"+ host + ":" + port + "/" + database);
		}else {
			hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
			hikari.addDataSourceProperty("serverName", host);
			hikari.addDataSourceProperty("databaseName", database);
			hikari.addDataSourceProperty("port", port);
		}
	}

	public void generate() {
		if(config.isSQLEnabled()){
			plugin.log(Level.INFO, "Connecting to MySQL...");
			Connection connection = null;
			try {
				connection = hikari.getConnection();
				if(!tableExists(config.getChatFeatureTable(), connection)){
					plugin.log(Level.INFO, "FPChat table not found. Creating new table...");
					update("CREATE TABLE " + config.getChatFeatureTable() + " (player_uuid VARCHAR (36), badge_loadout TEXT, PRIMARY KEY(player_uuid))");
					plugin.log(Level.INFO, "FPChat table created!");
				}
				if(!tableExists(config.getPermSyncTable(), connection)){
					update("CREATE TABLE " + config.getPermSyncTable() + " (player_uuid VARCHAR (36), badges TEXT, PRIMARY KEY(player_uuid))");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			plugin.log(Level.INFO, "FPChat successfully connected to MySQL!");
			plugin.log(Level.INFO, "Badges have been enabled!");
		}else{
			plugin.log(Level.INFO, "Continuing without MySQL.");
		}
	}

	public ResultSet query(String query, Connection connection){
		if (query == null) {
			return null;
		}
		ResultSet results = null;
		try{
			Statement statement = connection.createStatement();
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
		Statement statement;
		Connection connection = null;
		try {
			connection = hikari.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(input);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void asyncUpdate(String input){
		CompletableFuture.runAsync(() -> {
			update(input);
		});	
	}

	public boolean tableExists(String tablename, Connection connection){
		if (tablename == null) {
			return false;
		}
		try{
			if (connection == null) {
				return false;
			}
			if (connection.getMetaData() == null) {
				return false;
			}
			ResultSet results = connection.getMetaData().getTables(null, null, tablename, null);
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
		Connection connection = null;
		try{
			connection = hikari.getConnection();
			ResultSet results = query("SELECT * FROM " + table + " WHERE " + column + "=" + data, connection);
			while (results.next()) {
				if (results.getString(column) != null) {
					return true;
				}
			}

		}catch(SQLException e) {
			plugin.log(Level.SEVERE, "MYSQL itemExists error: " + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void createTable(String table, String columns){
		Connection connection = null;
		try {
			connection = hikari.getConnection();
			if (!tableExists(table, connection)) {
				update("CREATE TABLE " + table + " (" + columns + ")");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() throws SQLException {
		return hikari.getConnection();
	}

	public void disconnect() {
		hikari.close();
	}

}
