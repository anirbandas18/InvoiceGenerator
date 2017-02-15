package com.chocomint.asudyog.application.system;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import com.chocomint.asudyog.application.entities.Goods;
import com.chocomint.asudyog.application.entities.Invoice;
import com.chocomint.asudyog.application.viewer.Search;
import com.chocomint.asudyog.util.Constants;

public class DBConnectivity {
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	private ResultSet invoiceResults,goodsResult;
	private String path, sql;

	public DBConnectivity() {
		path = Constants.databaseName;
		sql = "";
	}
	
	public boolean connect(boolean flag) {
		if(flag == true) {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				con = DriverManager.getConnection("jdbc:derby:" + path + ";create=true");
				st = con.createStatement(ResultSet.CONCUR_UPDATABLE, 
					ResultSet.TYPE_SCROLL_SENSITIVE);
				Preferences pref = Preferences.userRoot().
						node("Invoice Generator");
				String create = pref.get("create", "");
				if(create.equals("false") || create.equals("")) {
					sql = "create table Invoice"
							+ "("
							+ "Bill_No varchar(100),"
							+ "VAT_No bigint,"
							+ "Bill_Date date,"
							+ "Messers varchar(100),"
							+ "Buyers_VAT_No bigint,"
							+ "Address varchar(200),"
							+ "Buyers_Order_No bigint,"
							+ "Date_1 date,"
							+ "Challan_No varchar(100),"
							+ "Date_2 date,"
							+ "Total_Items integer,"
							+ "Grand_Total double precision,"
							+ "File_Name varchar(100),"
							+ "primary key(Bill_No)"
							+ ")";
					st.executeUpdate(sql);
					sql = "create table Goods"
							+ "("
							+ "Bill_No_ varchar(100),"
							+ "Serial_No integer,"
							+ "Description varchar(100),"
							+ "Quantity decimal,"
							+ "Rate decimal,"
							+ "Per varchar(100),"
							+ "Value_ decimal,"
							+ "VAT DOUBLE PRECISION,"
							+ "Tax decimal,"
							+ "Sub_Total DOUBLE PRECISION"
							+ ")";
					st.executeUpdate(sql);
				}
					pref.put("create", "true");
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(), 
						"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(), 
						"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			try {
				con.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(), 
						"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}
	
	public int inputData(Invoice invoice, Goods goods) {
		int r1 = 0;
		int r2 = 0;
		sql = "insert into Invoice values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, invoice.getBill_no().toPlainString());
			ps.setBigDecimal(2, new BigDecimal(Constants.VAT_No));
			ps.setDate(3, invoice.getBill_date());
			ps.setString(4, invoice.getMessers());
			ps.setBigDecimal(5, invoice.getBuyers_vat_no());
			ps.setString(6, invoice.getAddress().trim());
			ps.setBigDecimal(7, invoice.getBuyers_order_no());
			ps.setDate(8, invoice.getDate_1());
			ps.setBigDecimal(9, invoice.getChallan_no());
			ps.setDate(10, invoice.getDate_2());
			ps.setInt(11, invoice.getTotal_items());
			ps.setDouble(12, invoice.getGrand_total());
			ps.setString(13, invoice.getFile_Name());
			r1 = ps.executeUpdate();
			ArrayList<HashMap<String, Object>> list = goods.getList();
			for(HashMap<String,Object> item : list) {
				sql =  "insert into Goods values('"
						+ invoice.getBill_no() + "', " + item.get("Serial No.") + 
						", '"+ item.get("Description") + "', " + item.get("Quantity") + 
						", " + item.get("Rate") + ", '" + item.get("Per") + "', " 
						+ item.get("Value") + ", " + item.get("VAT")
						+ ", " + item.get("Tax") + ", " + item.get("Sub - Total") + ")";
				r2 = st.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		return r1|r2;
	}
	
	public ArrayList<Invoice> getInvoices(String sql, Object value[], int mode) {
		ArrayList<Invoice> i = new ArrayList<Invoice>();
		try {
			ps = con.prepareStatement(sql);
			switch(mode) {
				case Search.Invoice_No : ps.setString(1, "%" + (String)value[0] + "%"); 
										 break;
				case Search.Invoice_Date : ps.setDate(1, (Date)value[0]); 
				 						 break;
				case Search.Messers : ps.setString(1, "%" + (String)value[0] + "%"); 
				 						 break;
				case Search.Challan_No : ps.setString(1, "%" + (String)value[0] + "%"); 
				 						 break;
				case Search.Grand_Total : ps.setDouble(1, (double)value[0]);
										  ps.setDouble(2, (double)value[1]);
				 						  break;
			}
			invoiceResults = ps.executeQuery();
			while(invoiceResults.next()) {
				Invoice invoice = new Invoice();
				invoice.setBill_no(invoiceResults.getString(1));
				invoice.setBill_date(invoiceResults.getDate(3));
				invoice.setMessers(invoiceResults.getString(4));
				invoice.setBuyers_vat_no(invoiceResults.getBigDecimal(5));
				invoice.setAddress(invoiceResults.getString(6));
				invoice.setBuyers_order_no(invoiceResults.getBigDecimal(7));
				invoice.setDate_1(invoiceResults.getDate(8));
				invoice.setChallan_no(invoiceResults.getBigDecimal(9));
				invoice.setDate_2(invoiceResults.getDate(10));
				invoice.setTotal_items(invoiceResults.getInt(11));
				invoice.setGrand_total(invoiceResults.getDouble(12));
				invoice.setFile_Name(invoiceResults.getString(13));
				i.add(invoice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		return i;
	}
	
	public Goods getGoods(Invoice invoice) {
		Goods goods = new Goods();
		try {
			sql = "select * from Goods where Bill_No_ = '" + invoice.getBill_no() + "'";
			goodsResult = st.executeQuery(sql);
			while(goodsResult.next()) {
					goods.newItem();
					goods.setSerialNo(goodsResult.getInt(1));
					goods.setDescription(goodsResult.getString(2));
					goods.setQuantity(goodsResult.getInt(3));
					goods.setRate(goodsResult.getDouble(4));
					goods.setPer(goodsResult.getString(5));
					goods.setValue(goodsResult.getDouble(6));
					goods.setVAT(goodsResult.getDouble(7));
					goods.setTax(goodsResult.getDouble(8));
					goods.setSubTotal(goodsResult.getDouble(9));
					goods.addItem();
				}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
			goods = null;
		}
		return goods;
	}
	
	public ResultSet getInvoiceData(String bill_no) {
		try {
			//sql = readSQLScript(bill_no);
			sql = "SELECT "
				     + "Goods.Serial_No AS Goods_Serial_No,"
				     + "Goods.Description AS Goods_Description,"
				     + "Goods.Quantity AS Goods_Quantity,"
				     + "Goods.Rate AS Goods_Rate,"
				     + "Goods.Per AS Goods_Per,"
				     + "Goods.Value_ AS Goods_Value_,"
				     + "Goods.VAT AS Goods_VAT,"
				     + "Goods.Tax AS Goods_Tax,"
				     + "Goods.Sub_Total AS Goods_Sub_Total,"
				     + "Invoice.Bill_No AS Invoice_Bill_No,"
				     + "Invoice.VAT_No AS Invoice_VAT_No,"
				     + "Invoice.Bill_Date AS Invoice_Bill_Date,"
				     + "Invoice.Messers AS Invoice_Messers,"
				     + "Invoice.Buyers_VAT_No AS Invoice_Buyers_VAT_No,"
				     + "Invoice.Address AS Invoice_Address,"
				     + "Invoice.Buyers_Order_No AS Invoice_Buyers_Order_No,"
				     + "Invoice.Date_1 AS Invoice_Date_1,"
				     + "Invoice.Challan_No AS Invoice_Challan_No,"
				     + "Invoice.Date_2 AS Invoice_Date_2,"
				     + "Invoice.Total_Items AS Invoice_Total_Items,"
				     + "Invoice.Grand_Total AS Invoice_Grand_Total "
				+ "FROM "
				     + "Goods,"
				     + "Invoice "
				+ "WHERE "
				      + "Invoice.Bill_No = '" + bill_no + "' and Goods.Bill_No_ = '" +  bill_no + "'";
			invoiceResults = st.executeQuery(sql);
		} catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
			invoiceResults = null;
		}
		return invoiceResults;
	}
	
	@SuppressWarnings("finally")
	public String getFileName(String invoice_no) {
		String file_name = "";
		try {
			String sql = "select File_Name from Invoice where Bill_No = '" + invoice_no + "'";
			invoiceResults = st.executeQuery(sql);
			invoiceResults.next();
			file_name = invoiceResults.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
			file_name = "";
		} finally {
			return file_name;
		}
	}
	
	@SuppressWarnings("finally")
	public int updateInvoiceFileName(String fileName, String invoiceNumber) {
		sql = "update Invoice set File_Name = '" + fileName 
				+ "' where Bill_No = '" + invoiceNumber + "'";
		int r = 0;
		try {
			r = st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			return r;
		}
	}
	
	public int removeData(String bill_no) {
		int r1 = 0, r2 = 0;
		try {
			sql="DELETE from Goods where Bill_No_ = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, bill_no);
			r1 = ps.executeUpdate();
			sql="DELETE from Invoice where Bill_No = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, bill_no);
			r2 = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		return r1 | r2;
	}
}
