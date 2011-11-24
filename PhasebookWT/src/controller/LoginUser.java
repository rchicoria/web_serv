package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;

/**
 * Servlet implementation class CreateUserForm
 */
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginUser() {}

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
		
		String [] login = {request.getParameter("email"), request.getParameter("password")};
		Message esbMessage = MessageFactory.getInstance().getMessage();
		esbMessage.getBody().add(login);
		
		String [] result = null;;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Objects_in_Messages_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);
			result = (String[])retMessage.getBody().get();
		} catch (MessageDeliverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FaultMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("error", result[0]);
		response.sendRedirect("login.jsp");
		
	}

}
