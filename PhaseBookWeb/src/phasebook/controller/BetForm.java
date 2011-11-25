package phasebook.controller;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phasebook.lotterybet.LotteryBetRemote;
import phasebook.user.*;

/**
 * Servlet implementation class BetForm
 */
public class BetForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BetForm() {
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
			LotteryBetRemote bet = (LotteryBetRemote) ctx.lookup("LotteryBetBean/remote");
			
			String number = request.getParameter("number");
			String error = formValidation(number);
			
			if (error != null)
				session.setAttribute("errorBet", error);
			else
				if (!bet.createBet(session.getAttribute("id"), Integer.parseInt(number),
						session.getAttribute("id"), session.getAttribute("password")))
					session.setAttribute("errorBet", "The draw is already closed, try betting in a new one");
				else {
					PhasebookUserRemote user = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
					user.deposit(session.getAttribute("id"), (float)-1,
							session.getAttribute("id"), session.getAttribute("password"));
				}
			response.sendRedirect(Utils.url("lottery"));
		} catch (NamingException e) {
			e.printStackTrace();
			session.setAttribute("error", "The submited data is incorrect");
			response.sendRedirect(Utils.url("lottery"));
		}
	}
	
	private String formValidation(String number) {
		// Must be a number between 1 and 100
		try {
			int n = Integer.parseInt(number);
			if (n < 1 || n > 100) {
				return "You can only bet a number between 1 and 100";
			}
		} catch (Exception e) {
			return "You can only bet a number between 1 and 100";
		}
		
		return null;
	}

}
