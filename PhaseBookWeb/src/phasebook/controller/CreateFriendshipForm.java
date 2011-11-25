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
				PhasebookUserRemote userBean;
				userBean = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
				
				PhasebookUser fromUser = userBean.getUserById(session.getAttribute("id"),
						session.getAttribute("id"), session.getAttribute("password"));
				PhasebookUser toUser = userBean.getUserById(request.getParameter("toUser"),
						session.getAttribute("id"), session.getAttribute("password"));
	
	
				userBean.invite(fromUser, toUser,
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
				PhasebookUserRemote userBean;
				FriendshipRemote friendshipBean;
				userBean = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
				friendshipBean = (FriendshipRemote) ctx.lookup("FriendshipBean/remote");
				
				PhasebookUser fromUser = userBean.getUserById(session.getAttribute("id"),
						session.getAttribute("id"), session.getAttribute("password"));
				PhasebookUser toUser = userBean.getUserById(request.getParameter("toUser"),
						session.getAttribute("id"), session.getAttribute("password"));
	
				friendshipBean.acceptFriendship(toUser,fromUser,
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
