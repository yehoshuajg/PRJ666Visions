package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Discount {
	
	//Values
	private int id;
	private double discountValue;
	private double discountPercent;
	private String type;
	
	Discount(){
		id = 0;
		discountValue = 0;
		discountPercent = 0;
		type = null;
	}
	Discount(int id, double discountValue, double discountPercent){
		this.id = id;
		this.discountValue = discountValue;
		this.discountPercent = discountPercent;
	}
	public int getPromotionCount(){
		int count = 0;
		try {
			Connect connect = new Connect();
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			Statement state = null;
			
			state = con.createStatement();
			String sql;
			sql = "SELECT * FROM Promotion";
			ResultSet rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				count++;
			}
			//Clean-up environment
			rs.close();
			state.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public void getPromotion(int id){
		int count = 0;
		try {
			Connect connect = new Connect();
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			Statement state = null;
			
			state = con.createStatement();
			String sql;
			sql = "SELECT * FROM Promotion where ID = '" + id + "'";
			ResultSet rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.id = rs.getInt("ID");
				this.discountValue = rs.getDouble("DiscountValue");
				this.discountPercent = rs.getDouble("DiscountPercent");
				if(this.discountValue > 0){
					this.type = "$";
				}
				else if(this.discountPercent > 0){
					this.type = "%";
				}
				count++;
			}
			//Clean-up environment
			rs.close();
			state.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public double calculateDiscount(double total, String discountValue, Vector<Discount> discount){
		char d = discountValue.charAt(0);
		char d2 = discountValue.charAt(discountValue.length()-1);
		char p = discountValue.charAt(discountValue.length()-1);
		if(d == '$' || p == '%' || d2 == '$'){
			return calculateDiscountByPercentage(total,discountValue,discount);
		}
		else if(Integer.parseInt(discountValue) >= 1 && Integer.parseInt(discountValue) <= discount.size()){
			double value = Double.parseDouble(discountValue);
			return calculateDiscountByList(total,value,discount);
		}
		else{
			JOptionPane.showMessageDialog(null,"Invalid option.");
			return total;
		}
	}
	public double calculateDiscountByList(double total, double discountValue, Vector<Discount> discount){
		for(int i = 0; i < discount.size(); i++){
			if(discountValue == discount.get(i).getID()){
				if(discount.get(i).getDiscountValue() > 0){
					total -= discount.get(i).getDiscountValue();
					type = "$"+discount.get(i).getDiscountValue();
				}
				else if(discount.get(i).getDiscountPercent() > 0){
					double temp = (discount.get(i).getDiscountPercent() / 100) * total;
					total -= temp;
					type = discount.get(i).getDiscountPercent()+"%";
				}
				else{
					JOptionPane.showMessageDialog(null,"Invalid option.");
					type = " ";
				}
			}
		}
		return total;
	}
	public double calculateDiscountByPercentage(double total, String discountValue, Vector<Discount> discount){
		String newValue = null;
		boolean check = false;
		if(discountValue.charAt(0) == '$'){
			newValue = discountValue.substring(1,discountValue.length());
			double temp = Double.parseDouble(newValue);
			for(int i = 0; i < discount.size(); i++){
				if(discount.get(i).getDiscountValue()  == temp){
					type = "$"+temp;
					check = true;
					return total -= temp;
				}
				else{
					check = false;
				}
			}
			if(check == false){
				JOptionPane.showMessageDialog(null,"Amount entered does not exist in promotion.");
			}
		}
		else if(discountValue.charAt(discountValue.length()-1) == '$'){
			boolean check2 = false;
			newValue = discountValue.substring(0,discountValue.length()-1);
			double temp = Double.parseDouble(newValue);
			for(int i = 0; i < discount.size(); i++){
				if(discount.get(i).getDiscountValue() == temp){
					type = "$"+temp;
					check2 = true;
					return total -= temp;
				}
				else{
					check2 = false;
				}
			}
			if(check2 == false){
				JOptionPane.showMessageDialog(null,"Amount entered does not exist in promotion.");
			}
		}
		else if(discountValue.charAt(discountValue.length()-1) == '%'){
			boolean check3 = false;
			newValue = discountValue.substring(0,discountValue.length()-1);
			double temp = Double.parseDouble(newValue);
			for(int i = 0; i < discount.size(); i++){
				if(discount.get(i).getDiscountPercent() == temp){
					type = temp+"%";
					check = true;
					temp = (temp / 100) * total;
					return total -= temp;
				}
				else{
					check = false;
				}
			}
			if(check3 == false){
				JOptionPane.showMessageDialog(null,"Percentage entered does not exist in promotion.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Invalid input.");
		}
		return total;
	}
	
	//Setters
	public void setID(int id){ this.id = id; }
	public void setDiscountValue(double discountValue){ this.discountValue = discountValue; }
	public void setDiscountPercent(double discountPercent){ this.discountPercent = discountPercent;	}
	public void setType(String type){ this.type = type; }
	
	//Getters
	public int getID(){ return this.id; }
	public double getDiscountValue(){ return this.discountValue; }
	public double getDiscountPercent(){ return this.discountPercent; }
	public String getType(){ return this.type; }
}
