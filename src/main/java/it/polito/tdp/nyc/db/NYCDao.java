package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public void getAllHotspot(Map<Integer,Hotspot> idMap){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
			Hotspot h=new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode"));
				idMap.put(h.getObjectId(), h);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		
	}
	public List<String> getProvider(){
		String sql = "SELECT DISTINCT  n.Provider as p "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "ORDER BY n.Provider";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("p"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public List<String> getVertici(String provider){
		String sql = "SELECT distinct  n.City as citta "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider=?";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("citta"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public List<Adiacenza> getArchi(String provider){
		String sql = "SELECT n1.City as c1,n2.City as c2,AVG(n1.Latitude) AS lat1,AVG(n1.Longitude) AS long1,AVG(n2.Latitude) AS lat2,AVG(n2.Longitude) AS long2 "
				+ "FROM nyc_wifi_hotspot_locations n1,nyc_wifi_hotspot_locations n2 "
				+ "WHERE n1.Provider=? AND n1.Provider=n2.Provider AND n1.City<n2.City "
				+ "GROUP BY n1.City,n2.City";
		List<Adiacenza> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				String s1=res.getString("c1");
				String s2=res.getString("c2");
				double lat1= res.getDouble("lat1");
			  double long1=res.getDouble("long1");
			  double lat2= res.getDouble("lat2");
			  double long2=res.getDouble("long2");
			 LatLng distanza1= new LatLng(lat1,long1);
			 LatLng distanza2= new LatLng(lat2,long2);
			 double peso=LatLngTool.distance(distanza1, distanza2, LengthUnit.KILOMETER);
			 Adiacenza a = new Adiacenza(s1,s2,peso);
			 result.add(a);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public Map<String,Hotspot> getHotspotQuartiere(String provider,Map<Integer,Hotspot> idMap,String quartiere){
		String sql = "SELECT n.OBJECTID as id "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider=? AND n.City=?";
		Map<String,Hotspot> result = new HashMap<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			st.setString(2, quartiere);
			ResultSet res = st.executeQuery();

			while (res.next()) {
			Hotspot h=idMap.get(res.getInt("id"));
			result.put(quartiere, h);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
}
