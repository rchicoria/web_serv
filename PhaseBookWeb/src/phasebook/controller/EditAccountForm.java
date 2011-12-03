package phasebook.controller;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class EditAccountForm
 */
public class EditAccountForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAccountForm() {
        super();
    }

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
		
		InitialContext ctx = null;
		HttpSession session = request.getSession();
		try {
			ctx = new InitialContext();
			PhasebookUserRemote user = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
			
			String name = request.getParameter("name");
			String photo = request.getParameter("avatar");
			String password1 = request.getParameter("password1");
			String password2 = request.getParameter("password2");
			String error = formValidation(name, photo, password1, password2);
			
			if (error != null) {
				session.setAttribute("error", error);
				response.sendRedirect(Utils.url("edit"));
			}
			else {
				if(!password1.equals("")){
					password1 = Utils.byteArrayToHexString(Utils.computeHash(password1 + "salt" + 
							user.getUserById(session.getAttribute("id"),
									session.getAttribute("id"), session.getAttribute("password")).getEmail()));
				}
				else {
					password1 = "";
				}
				user.editAccount(session.getAttribute("id"), name, photo, password1,
						session.getAttribute("id"), session.getAttribute("password"));
				session.setAttribute("password", password1);
				response.sendRedirect(Utils.url(""));
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			session.setAttribute("error", "The submited data is incorrect");
			response.sendRedirect(Utils.url("edit"));
		}
	}
	
	private String formValidation(String name, String photo, String password1, String password2) {
		// Name can't be blank
		if (name == null || name.length() == 0) {
			return "You must specify your name";
		}
		
		// Photo can't be blank
		if (photo == null || photo.length() == 0) {
			return "You must choose a profile picture";
		}
		
		// Passwords must be both or none blank
		if ((password1 == null && password2 != null) || (password1 != null && password2 == null))
		{
			return "The new password must be typed in both boxes";
		}
		
		// Passwords must match
		if (password1.compareTo(password2) != 0) {
			return "The passwords do not match";
		}
		
		return null;
	}

}
