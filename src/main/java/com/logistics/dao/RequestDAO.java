package com.logistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.logistics.Response;
import com.logistics.common.DBConnectionRest;
import com.logistics.email.notification.SendEmail;
import com.logistics.request.constants.CustomerInfo;
import com.logistics.request.constants.Roles;

/*@Repository*/
public class RequestDAO {
	
	public List<CustomerInfo> getAllRequests(String userId){
		List<CustomerInfo> customerInfo = new ArrayList<CustomerInfo>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;

		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			System.out.println("userId:: "+userId);
			String query= "select FIRSTNAME,LASTNAME,PASSWORD,ROLEID,USERID from CUSTOMERINFO where userid='"+userId+"'";

			
			pstmtPjctRsn = conn.prepareStatement(query);
			rs = pstmtPjctRsn.executeQuery();

			CustomerInfo customerData;
			while (rs.next()) {
				customerData = new CustomerInfo();
				customerData.setFirstName(rs.getString("FIRSTNAME"));
				customerData.setLastName(rs.getString("LASTNAME"));
				customerData.setPassWord(rs.getString("PASSWORD"));
				customerData.setRoleId(rs.getInt("ROLEID"));
				customerData.setUserId(rs.getString("USERID"));
				customerInfo.add(customerData);
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(rs);
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return customerInfo;
	}
	
	
	
	public Response addCustomer(CustomerInfo customerInfo){

		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add customer was failed!");
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		String subject ="You're invited to join Logistics";
		String body ="You're invited to join Logistics. Here is your system generated password <b>welcome123</b>. <br/> <br/>If you're having problems, please feel free to contact us at logistics@artissol.com. We'll be glad to help.<br/> <br/>Regards,<br/>Team Logistics.";
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			String insertQuery =  "insert into CUSTOMERINFO (FIRSTNAME, LASTNAME, PASSWORD, userid, roleid) values (?,?,?,?,?)";
			pstmtInsert = conn.prepareStatement(insertQuery);

			pstmtInsert.setString(1,customerInfo.getFirstName());
			pstmtInsert.setString(2,customerInfo.getLastName());
			pstmtInsert.setString(3,"welcome123");
			pstmtInsert.setString(4,customerInfo.getUserId());
			pstmtInsert.setInt(5,customerInfo.getRoleId());
			insertedRecords = pstmtInsert.executeUpdate();

			if(insertedRecords > 0){ 
				response.setResponseCode("200");
				if(response.getResponseCode().equals("200")){
					SendEmail mail = new SendEmail();
					mail.sendEmail(customerInfo.getUserId(),"","",subject,body,null);
				}
				response.setResponseMessage("Customer added successfully");
				
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnectionRest.closeQuietly(pstmtInsert);
			//DBConnectionRest.closeQuietly(rs);
			//DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return response;
	}
	

	
	public List<Roles> getRoles(){
		List<Roles> optionroles = new ArrayList<Roles>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;

		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();

			String query= "select * from OPTIONROLE";

			
			pstmtPjctRsn = conn.prepareStatement(query);
			rs = pstmtPjctRsn.executeQuery();

			Roles rolesData;
			while (rs.next()) {
				rolesData = new Roles();
				rolesData.setRoleId(rs.getInt("ROLEID"));
				rolesData.setRoleName(rs.getString("ROLENAME"));
				optionroles.add(rolesData);
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(rs);
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return optionroles;
	}
	
	
	public Response forgotPassword(String UserName, String Password, int reason){

		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("ForgotPassword was failed!");
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		String subject ="Password has been changed.";
		String body ="Password has been changed.";
		
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			String insertQuery =  "update CUSTOMERINFO set password =? where emailid =?";
			pstmtInsert = conn.prepareStatement(insertQuery);

			
			pstmtInsert.setString(1,Password);
			pstmtInsert.setString(2,UserName);
			
			insertedRecords = pstmtInsert.executeUpdate();

			if(insertedRecords > 0){ 
				response.setResponseCode("200");
				if(response.getResponseCode().equals("200")){
					SendEmail mail = new SendEmail();
					mail.sendEmail(UserName,null,null,subject,body,null);
				}
				response.setResponseMessage(" Updated New Password successfully");
				
				conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnectionRest.closeQuietly(pstmtInsert);
			//DBConnectionRest.closeQuietly(rs);
			//DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return response;
	}

	
	
	
}
