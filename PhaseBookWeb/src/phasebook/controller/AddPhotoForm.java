package phasebook.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import phasebook.photo.Photo;
import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserRemote;

/**
 * Servlet implementation class CreatePostForm
 */
public class AddPhotoForm extends HttpServlet {
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
		realPath = tempFile.getAbsolutePath().substring(0, tempFile.getAbsolutePath().lastIndexOf("/"))+
				DESTINATION_DIR_PATH;
		
 
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPhotoForm() {
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
			
			PhasebookUser user = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
			
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
				List items = uploadHandler.parseRequest(request);
				Iterator itr = items.iterator();
				while(itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					/*
					 * Write file to the ultimate location.
					 */
					String ext="";
					String error = null;
					
					error = formValidation(item.getName());
					if (error != null) {
						session.setAttribute("error", error);
						response.sendRedirect(Utils.url(""));
					} else {
						
						destinationDir = new File(realPath+"/"+user.getId());
						destinationDir.mkdirs();
						if(!destinationDir.isDirectory()) {
							throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
						}
						long time = System.currentTimeMillis();
						File file = new File(destinationDir, time+ext );
						item.write(file);
						Photo photo = userBean.addPhoto(time+ext,
								session.getAttribute("id"), session.getAttribute("password"));
						userBean.setProfilePicture(user, photo.getId(),
								session.getAttribute("id"), session.getAttribute("password"));
						response.sendRedirect(Utils.url(""));
					}
				}
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
	
	private String formValidation(String name) {
		if (name.length()!=0){
			String extension = getExtension(name);
			if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") 
					|| extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".gif"))
				return null;
		}
		return "Please insert a valid image file";
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
