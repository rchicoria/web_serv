package phasebook.controller;

import client.artefact.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import client.artefact.Methods;
import client.artefact.MethodsService;

import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class CreatePostForm
 */
public class CreatePostForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String TMP_DIR_PATH = "/temp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH ="/server/default/deploy/ROOT.war/photos";
	private File destinationDir;
	String realPath;
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		tmpDir.mkdirs();
		if(!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		File tempFile = new File("");
		System.out.println("\n\n\n\naBSOLUTE:" + File.separator + "\n\n\n\n");
		realPath = tempFile.getAbsolutePath().substring(0, tempFile.getAbsolutePath().lastIndexOf(File.separator))+
				DESTINATION_DIR_PATH;
		
 
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePostForm() {
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
			PhasebookUserRemote userBean;
			userBean = (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
			
			PhasebookUser fromUser = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
			PhasebookUser toUser = null;

			String text = "";
			String privacy = "";
			
			//PrintWriter out = response.getWriter();
	 
			DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
			/*
			 *Set the size threshold, above which content will be stored on disk.
			 */
			fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
			/*
			 * Set the temporary directory to store the uploaded files of size above threshold.
			 */
			fileItemFactory.setRepository(tmpDir);
	 
			ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
			try {
				/*
				 * Parse the request
				 */
				List<?> items = uploadHandler.parseRequest(request);
				Iterator<?> itr = items.iterator();
				boolean hasFile = false;
				long time = 0;
				String ext = "";
				while(itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					/*
					 * Handle Form Fields.
					 */
					if(item.isFormField()) {
						if (item.getFieldName().compareTo("post")==0){
							text = item.getString();
						}
						else if (item.getFieldName().compareTo("privacy")==0){
							privacy = item.getString();
						}
						else if (item.getFieldName().compareTo("toUser")==0){
							toUser = userBean.getUserById(item.getString(),
									session.getAttribute("id"), session.getAttribute("password"));
						}
					} else {
						/*
						 * Write file to the ultimate location.
						 */
						String error = null;
						if(item.getName().length()!=0){
							ext = getExtension(item.getName());
							error = formPhotoValidation(ext);
						}
						else
							error = formTextValidation(text);
						if (error != null) {
							session.setAttribute("error", error);
							session.setAttribute("post", text);
							session.setAttribute("privacy", privacy);
							response.sendRedirect(Utils.url("user&id="+toUser.getId()));
							return;
						} else {
							if(item.getName().length()!=0){
								destinationDir = new File(realPath+"/"+toUser.getId());
								destinationDir.mkdirs();
								if(!destinationDir.isDirectory()) {
									throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
								}
								time = System.currentTimeMillis();
								File file = new File(destinationDir, time+ext );
								item.write(file);
								hasFile=true;
							}
						}
					}
				}
				
				MethodsService cs = new MethodsService();
				Methods m = cs.getMethodsPort();
				System.out.println("CHEGUEI CA 2   "+hasFile);
				if(hasFile)
				{
					if(m.createPost(fromUser.getId(), toUser.getId(), text, privacy,  time+ext,
							(new Date()).getTime(), fromUser.getId(), (String)session.getAttribute("token"), 
							((Long)session.getAttribute("expiration")).longValue()) == -1){
							Utils.auth(session, response, request);
							System.out.println("CHEGOU A FORM: "+m.createPost(fromUser.getId(), toUser.getId(), text, privacy,  time+ext,
									(new Date()).getTime(), fromUser.getId(), (String)session.getAttribute("token"), 
									((Long)session.getAttribute("expiration")).longValue()));
						}
				}
				else{
					if(m.createPost(fromUser.getId(), toUser.getId(), text, privacy,  "",
						(new Date()).getTime(), fromUser.getId(), (String)session.getAttribute("token"), 
						((Long)session.getAttribute("expiration")).longValue()) == -1){
						Utils.auth(session, response, request);
						System.out.println("CHEGOU A FORM: "+m.createPost(fromUser.getId(), toUser.getId(), text, privacy,  "",
								(new Date()).getTime(), fromUser.getId(), (String)session.getAttribute("token"), 
								((Long)session.getAttribute("expiration")).longValue()));
					}
				}
						
				response.sendRedirect(Utils.url("user&id="+toUser.getId()));
			}catch(FileUploadException ex) {
				log("Error encountered while parsing the request",ex);
			} catch(Exception ex) {
				log("Error encountered while uploading file",ex);
			}

		} catch (NamingException e) {
			e.printStackTrace();
			session.setAttribute("error", "The submited data is incorrect");
			response.sendRedirect(Utils.url("login"));
		}
	}
	
	private String formPhotoValidation(String extension) {
		
		if (!extension.equalsIgnoreCase(".png") && !extension.equalsIgnoreCase(".jpg") 
				|| !extension.equalsIgnoreCase(".jpeg") && !!extension.equalsIgnoreCase(".gif"))
			return "Please insert a valid image file";

		return null;
	}
	
	private String formTextValidation(String text) {
		// Text can't be blank
		if (text == null || text.length() == 0) {
			return "Please write something to post";
		}

		return null;
	}
	
	private String getExtension(String filename)
	{
		try{
			return filename.substring(filename.lastIndexOf("."), filename.length());
		} catch (StringIndexOutOfBoundsException e){
			return "";
		}
	}

}
