package phasebook.controller;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phasebook.post.PostRemote;
import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class RemovePostForm
 */
public class RemovePostForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovePostForm() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		InitialContext ctx = null;
		HttpSession session = request.getSession();
		try {
			ctx = new InitialContext();
			PostRemote myPost;
			PhasebookUserRemote myUser;
			myPost = (PostRemote) ctx.lookup("PostBean/remote");
			myUser = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
			
			String post = request.getParameter("postId");
			String userId = request.getParameter("userId");
			
			myPost.removePost(post,
					session.getAttribute("id"), session.getAttribute("password"));
			PhasebookUser user = myUser.getUserById(userId,
					session.getAttribute("id"), session.getAttribute("password"));
			
			if (myPost.getPostById(post,
					session.getAttribute("id"), session.getAttribute("password")).getPhotoId() != -1 && user.getPhotoId() != -1)
			{
				if(user.getPhotoId() == myPost.getPostById(post,
						session.getAttribute("id"), session.getAttribute("password")).getPhotoId())
					myUser.setProfilePicture(user, -1,
							session.getAttribute("id"), session.getAttribute("password"));
			}
			response.sendRedirect(Utils.url("user&id="+myUser.getUserById(userId,
					session.getAttribute("id"), session.getAttribute("password")).getId()));

		} catch (NamingException e) {
			e.printStackTrace();
			session.setAttribute("error", "The submited data is incorrect");
			response.sendRedirect(Utils.url("register"));
		}
	}

}
