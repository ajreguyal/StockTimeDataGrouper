package com.ajreguyal.main.java;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.ajreguyal.builder.CandleBuilder;
import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.model.Candle;

public class StockTimeDataGrouper {
	private Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Candle getCandle(String tableName, long start, long end) throws SQLException {
		CandleBuilder builder = new CandleBuilder();
		if (connection == null) {
			throw new java.sql.SQLException("No connection");
		}
		
		String sql = "SELECT distinct(select price from " + tableName + " \r\n" + 
				"WHERE id >= " + start + " AND id < " + end + " order by id asc limit 1) AS priceOpen, \r\n" + 
				"(select price from " + tableName + " \r\n" + 
				"WHERE id >= " + start + " AND id < " + end + " order by id desc limit 1) AS priceClose, \r\n" + 
				"(select price from " + tableName + " \r\n" + 
				"WHERE id >= " + start + " AND id < " + end + " order by price asc limit 1) AS priceLow, \r\n" + 
				"(select price from " + tableName + " \r\n" + 
				"WHERE id >= " + start + " AND id < " + end + " order by price desc limit 1) AS priceHigh, \r\n" + 
				"(select SUM(volume) from " + tableName + " \r\n" + 
				"WHERE id >= " + start + " AND id < " + end + ") AS totalVolume FROM " + tableName + "\r\n" + 
				"";
		
//		System.out.println(sql);
		
		try (PreparedStatement pSt = connection.prepareStatement(sql)){
			ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
            	builder.setLow(rs.getString("priceLow"));
            	builder.setHigh(rs.getString("priceHigh"));
            	builder.setOpen(rs.getString("priceOpen"));
            	builder.setClose(rs.getString("priceClose"));
            	builder.setVolume(rs.getString("totalVolume"));
            }
            pSt.close();
        } catch(Exception e) {
//            throw new java.sql.SQLException("Error getting table data count " + e.toString());
        }
		
 		return builder.build();
	}
	
	public static void main(String[] args) {
		DBBackend backend = new DBBackend();
		backend.connect();
		
		StockTimeDataGrouper grouper = new StockTimeDataGrouper();
		grouper.setConnection(backend.getConnection());
		long initialTime = 1472693400000L;
		long currentTime = 1472702377000L;//getNextTime(initialTime, 100);
		
		while (initialTime < currentTime) {
			int timeFrame = 5;
			try {
				System.out.println("time: " + convertTime(initialTime) + " - " + convertTime(getNextTime(initialTime, timeFrame)  + 1000));
				Candle candle = (grouper.getCandle("transactionmed", initialTime, getNextTime(initialTime, timeFrame)  + 1000));
				if (candle == null) {
					System.out.println("no candle");
				} else {
					System.out.println(candle.toString());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initialTime = getNextTime(initialTime, timeFrame);
		}
	}

	private static long getNextTime(long time, int timeFrame) {
		return time + (timeFrame * 60000);
	}
	
	public static String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("HH:mm:ss a");
	    return format.format(date);
	}
}
