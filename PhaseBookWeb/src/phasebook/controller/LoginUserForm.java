package phasebook.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;

import client.artefact.*;
import phasebook.post.PostRemote;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class CreateUserForm
 */
public class LoginUserForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginUserForm() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String error = formValidation(email, password);
		if (error != null)
		{
			session.setAttribute("error", error);
			System.out.println(error);
			response.sendRedirect(Utils.url("login"));
		}
		else
		{
			password = Utils.byteArrayToHexString(Utils.computeHash(password + "salt" + email));
			MethodsService cs = new MethodsService();
			Methods m = cs.getMethodsPort();
			
			long current = (new Date()).getTime();
			
			AuthInfo object =  m.loginUser(email, password, current);
			int id = object.getId();
			System.out.println("\n\n\n\n*********************"+id);
			String token = object.getToken();
			long expiration = object.getExpiration();
			
			if(id!=-1){
				session.setAttribute("id", id);
				session.setAttribute("password", password);
				session.setAttribute("current", current);
				session.setAttribute("expiration", expiration);
				session.setAttribute("token", token);
				response.sendRedirect(Utils.url(""));
			}
			else
			{
				session.setAttribute("error", "Wrong email or password");
				response.sendRedirect(Utils.url("login"));
			}
		}
		
	}
	
	private String formValidation(String email, String password)
	{
		
		// Email can't be blank
		if (email == null || email.length() == 0)
		{
			return "Can't login without an email";
		}
		
		// Email must be valid
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (!m.matches())
		{
			return "Can't login without a valid email";
		}
		
		// Password can't be blank
		if (password == null || password.length() == 0)
		{
			return "Can't login without a password";
		}
		
		return null;
	}

}
