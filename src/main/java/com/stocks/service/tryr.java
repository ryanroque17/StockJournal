package com.stocks.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class tryr {
	public static void main(String[] args) {
//	    HttpURLConnection connection = null;
//		String inputLine;
//		try {
//			URL url = new URL("http://phisix-api3.appspot.com/stocks/LTG.json");
//			// Make connection
//			connection = (HttpURLConnection) url.openConnection();
//			// Set request type as HTTP GET
//			connection.setRequestMethod("GET");
//			connection.connect();
//			String inline = "";
//			int response = connection.getResponseCode();
//			Scanner sc = new Scanner(url.openStream());
//			while(sc.hasNext()) {
//				inline+=sc.nextLine();
//			}
//			
//			System.out.println(inline);
//			
//			try {
//				JSONParser parser = new JSONParser();
//
//				JSONObject json = (JSONObject) parser.parse(inline);
//				
//				JSONArray jsonarr = (JSONArray) json.get("stock");
//				
//				json = (JSONObject) jsonarr.get(0);
//				String symbol = (String) json.get("symbol");
//
//				json = (JSONObject) json.get("price");
//				Double price = (Double) json.get("amount");
//				System.out.println(symbol + " : " +price);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		   
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		Map<String, Double> trade_map = new HashMap<String, Double>();
		trade_map.put("PXP", 0.01);
		trade_map.put("ABC", 0.22);
		trade_map.put("CDE", 1.25);
		trade_map.put("FGH", 3.02);
		trade_map.put("SMC", 10.2);
		trade_map.put("QWE", 0.1);
		trade_map.put("WEW", 0.5);
		trade_map.put("RYA", 3.12);
		System.out.println(trade_map.get("ABC"));
		trade_map.replace("ABC", 0.03);
		System.out.println(trade_map.get("ABC"));

//		Date date=null;
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			date = format.parse( "2009-12-31" );
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}    
//		String dateString = format.format(date);
//		
//		System.out.println(date);
//		System.out.println(dateString);

		System.out.println(trade_map.toString());
		
		Map<String, Double> sorted_map = sortByValue(trade_map,"losers");
		System.out.println(sorted_map.toString());
		
//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//		try {
//			date = format.parse(dateString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(date);

	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, String action) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
       
        if(action.equals("gainers"))
        	Collections.reverse((List<V>) list);

        int ctr = 0;
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
            ctr++;
            if(ctr==5)
            	break;
        }
       
        return result;
    }
}
