package com.filters;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import java.util.Base64;

import java.io.IOException;

@Provider
public class AuthFilter implements ContainerRequestFilter {

	private static final String username = "user";
	private static final String password = "password";

	@Override
	public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

	    String authCredentials = containerRequest.getHeaderString("Authorization");

	    boolean authStatus = authenticate(authCredentials);

	    if (!authStatus) {
	      throw new WebApplicationException(Status.UNAUTHORIZED);
	    }
	  }

	  private boolean authenticate(String authCredentials) {
		  if (authCredentials == null || !authCredentials.startsWith("Basic "))
			  return false;

		  final String encodedUserPassword = authCredentials.replaceFirst("Basic ", "");
		  String usernameAndPassword = null;
		  try {
			  byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			  usernameAndPassword = new String(decodedBytes, "UTF-8");
		  } catch(IOException e) {
			  e.printStackTrace();
			  return false;
		  }
		  if (usernameAndPassword == null || !usernameAndPassword.contains(":"))
			  return false;

		  String[] parts = usernameAndPassword.split(":");
		  if (parts.length < 2 || parts[0] == null || parts[1] == null)
			  return false;


		 return (parts[0].equals(username) && parts[1].equals(password));
	  }
}
