package com.logistics.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.Response;
import com.logistics.dao.RequestDAO;
import com.logistics.request.constants.CustomerInfo;
import com.logistics.request.constants.Roles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@CrossOrigin
@RestController
@RequestMapping("/logistics")
@Api(value = "/logistics", tags = "Logistics Customent Login APIS")
public class UserController {
	

	@RequestMapping(value = "/getAllRequests", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json") 
	@ApiOperation(value = "Get Customer info for logistica", 
	notes = "get all Spread Model details or specific spread model detail by providing id as input", 
	
	response = CustomerInfo.class,  responseContainer="List")
	@ApiResponses({
		@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer="List"),
		@ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
	})
	public List<CustomerInfo> getAllRequests(@ApiParam(value = "userId", required = false) @RequestParam(name = "userId", required = true) String userId) throws IOException, ClassNotFoundException  {
		System.out.println("Started getAllRequests");
		RequestDAO requestLayer = new RequestDAO();
		List<CustomerInfo> customerInfo = requestLayer.getAllRequests(userId);
		System.out.println("Ended getAllRequests");		
		return customerInfo;
	}

	@RequestMapping(value = "/addCustomer", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Customer to the System", 
	notes = "Add Customers", 
	
	response = CustomerInfo.class,  responseContainer="List")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Add Customer", response = CustomerInfo.class, responseContainer="List"),
		@ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
	})
	public Response addCustomer(@RequestBody CustomerInfo customerInfo) throws IOException, ClassNotFoundException  {
		System.out.println("Started addCustomer");
		Response response=null;
		try{
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.addCustomer(customerInfo);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended addCustomer");		
		return response;
	}
	
	@RequestMapping(value = "/getRoles", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json") 
	@ApiOperation(value = "Get all the roles for customer", 
	notes = "get all roles for the Customer", 
	
	response = Roles.class,  responseContainer="List")
	@ApiResponses({
		@ApiResponse(code = 200, message = "List of Spread Model", response = Roles.class, responseContainer="List"),
		@ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
	})
	public List<Roles> getRoles() throws IOException, ClassNotFoundException  {
		System.out.println("Started getRoles");
		RequestDAO requestLayer = new RequestDAO();
		List<Roles> rolesInfo = requestLayer.getRoles();
		System.out.println("Ended getRoles");		
		return rolesInfo;
	}
	

}
