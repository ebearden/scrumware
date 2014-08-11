package com.scrumware.asset;

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

import com.scrumware.login.SessionHelper;


/**
 * Servlet implementation class AssetUpload
 * @author Emily Kubic
 */
@WebServlet(name="/AssetUpload", urlPatterns={"/AssetUpload","/asset/upload"})
public class AssetUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String folderLocation = null;
	private int user_id = 0;
	private int project_id = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetUpload() {
        super();
        // TODO Auto-generated constructor stub
        //this.folderLocation = "/Users/eakubic/EclipseProjects/SCRUMware/WebContent/projectassets";
        this.folderLocation = "projectassets";
    }


    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
    	
    	if (!SessionHelper.validateSession(request, response)) {
			return;
		}
    	
    	response.setContentType("text/html;charset=UTF-8");
    	
    	user_id = com.scrumware.login.SessionHelper.getSessionUserId(request);
        project_id = Integer.parseInt(request.getParameter("project_id"));
        String errmsg = null;
    	
    	
    	// Check that we have a file upload request  
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request); 


        try {
        	
        	if(isMultipart){
        		
        		// Create a factory for disk-based file items
        		DiskFileItemFactory factory = new DiskFileItemFactory();

        		// Configure a repository (to ensure a secure temp location is used)
        		ServletContext servletContext = this.getServletConfig().getServletContext();
        		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        		//System.out.println(servletContext.getAttribute("javax.servlet.context.tempdir"));
        		
        		factory.setRepository(repository);

        		// Create a new file upload handler
        		ServletFileUpload upload = new ServletFileUpload(factory);

        		upload.setFileSizeMax(1048576*10);

        		List<FileItem> items = upload.parseRequest(request);
        		
        		Iterator<FileItem> iter = items.iterator();
        		
        		FileItem a_file = iter.next();
        		FileItem a_desc = iter.next();
        		boolean update = false;
        		boolean exists = false;
        		

    			//System.out.println("Starting Logic");
        		
        		if (request.getParameter("update") != null) {
        			update = Boolean.parseBoolean(request.getParameter("update"));
        			//System.out.println("update = " + update);
        		}
        		
        		if (a_file.getSize() >= upload.getFileSizeMax()) {
        			//System.out.println("file size: " + a_file.getSize());
        			//System.out.println("upload size: " + upload.getSizeMax());
        			errmsg = "Max file size exceeded.";
        		}
        		
        		Asset asset = new Asset();
        		
        		String fileName = a_file.getName();  	
        		
        		if (AssetDB.exists(fileName, project_id)) {
        			asset = AssetDB.getAsset(fileName, project_id);
        			exists = true;
        		}
        		
        		if (exists == true && update == false) {
        			//System.out.println("already exists");
        			errmsg="This file already exists.  "+
        					"Do you want to overwrite it with a new version?<br/>"+
        					"If so, please select the file again and click \"Confirm\"";
        		}
        		
        		if (errmsg != null && update == false) {
        			//System.out.println("setting params");
        			request.setAttribute("exists", exists);
        			request.setAttribute("errmsg", errmsg);
        			request.setAttribute("project_id", project_id);
        			request.setAttribute("a_desc", a_desc.getString());
        			getServletContext().getRequestDispatcher("/asset/asset_upload.jsp?project_id="+project_id).forward(request, response);
        			
        		} else {
        		
			        
	        		//System.out.println(servletContext+File.separator+folderLocation+File.separator+project_id+
	        		//		File.separator+fileName);
	        		
	        		String uploadPath = getServletContext().getRealPath("")
	        			    + File.separator + folderLocation;
	        		
	        		//String uploadPath = File.separator + folderLocation + File.separator + project_id;
	        		
	        		File uploadDir = new File(uploadPath);
	        		if (!uploadDir.exists()) {
	        		    uploadDir.mkdir();
	        		    //System.out.println("projectassets does not exist.");
	        		}
	        		
	        		uploadPath = uploadPath+ File.separator + project_id;
	        		
	        		uploadDir = new File(uploadPath);
	        		if (!uploadDir.exists()) {
	        		    uploadDir.mkdir();
	        		    //System.out.println("project folder does not exist.");
	        		}
	        		
			        File uploadedFile = 
			        		new File(uploadPath+File.separator+fileName); 
			        
			        a_file.write(uploadedFile);
			        
			        if (update) {
			        	
			        	//System.out.println(a_desc.getString());
		        		asset.setDescription(a_desc.getString());
			        	asset.setUpdatedBy(user_id);
			        	AssetDB.update(asset);
			        	//System.out.println("Updated file info.");
			        
			        } else {
		        		asset.setName(fileName);
				        
				        asset.setLocation(uploadPath+File.separator+fileName);
		        		asset.setDescription(a_desc.getString());
		        		
		        		asset.setProjectID(project_id);
		        		
		        		asset.setCreatedBy(user_id);
		        		asset.setUpdatedBy(user_id);
		        		
		        		AssetDB.insert(asset);
			        }

	            	response.sendRedirect(request.getContextPath() + "/asset/assets?project_id="+project_id);
	        		
	        	} 
        	}
        	
		} catch(FileUploadException fue) {  
			fue.printStackTrace();  
			throw new ServletException(fue.getMessage());  
		} catch(Exception e) {  
			e.printStackTrace();  
			throw new ServletException(e.getMessage());  
		}  
	}  
     
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
		
	}
    

}
