package phasebook.controller;

import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phasebook.friendship.FriendshipRemote;
import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class CreatePostForm
 */
public class CreateFriendshipForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFriendshipForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletReque
	 * st request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InitialContext ctx = null;
		HttpSession session = request.getSession();
		
		int typeOfAction= Integer.parseInt(request.getParameter("relationship").toString());
		if(typeOfAction==0)
		{
			try {
				ctx = new InitialContext();
				FriendshipRemote friendshipBean = (FriendshipRemote) ctx.lookup("FriendshipBean/remote");
	
				friendshipBean.invite(Integer.parseInt(session.getAttribute("id").toString()),
						Integer.parseInt(request.getParameter("toUser").toString()),
						session.getAttribute("id"), session.getAttribute("password"));
				response.sendRedirect("?p=user&id="+request.getParameter("toUser").toString());
				
			}
			catch (NamingException e) {
				e.printStackTrace();
				session.setAttribute("error", "The submited data is incorrect");
				response.sendRedirect(Utils.url("register"));
			}
		}
		else if(typeOfAction==2)
		{
			try {
				ctx = new InitialContext();
				FriendshipRemote friendshipBean = (FriendshipRemote) ctx.lookup("FriendshipBean/remote");
	
				friendshipBean.acceptFriendship(Integer.parseInt(request.getParameter("toUser").toString()),
						Integer.parseInt(session.getAttribute("id").toString()),
						session.getAttribute("id"), session.getAttribute("password"));
				response.sendRedirect("?p=user&id="+request.getParameter("toUser").toString());
				
			}
			catch (NamingException e) {
				e.printStackTrace();
				session.setAttribute("error", "The submited data is incorrect");
				response.sendRedirect(Utils.url("register"));
			}
		}
	}
}