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
 * Servlet implementation class GetMoneyForm
 */
public class GetMoneyForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMoneyForm() {
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
			PhasebookUserRemote user;
			user = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
			
			String money = request.getParameter("money");
			String error = formValidation(money);
			
			if (error != null)
				session.setAttribute("error", error);
			else
				user.deposit(session.getAttribute("id"), Float.parseFloat(money),
						session.getAttribute("id"), session.getAttribute("password"));
			response.sendRedirect(Utils.url("lottery"));
		} catch (NamingException e) {
			e.printStackTrace();
			session.setAttribute("error", "The submited data is incorrect");
			response.sendRedirect(Utils.url("lottery"));
		}
			
	}
		
	private String formValidation(String money) {
		// Money can't be blank
		if (money == null || money.length() == 0) {
			return "You must specify an amount";
		}

		// Money must be a number
		try {
			Float.parseFloat(money);
		} catch (Exception e) {
			return "The amount specified is not valid";
		}
		
		return null;
	}

}
