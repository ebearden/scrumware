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
    	
    	com.scrumware.login.SessionHelper.validateSession(request, response);
    	
    	user_id = com.scrumware.login.SessionHelper.getSessionUserId(request);
        project_id = Integer.parseInt(request.getParameter("project_id"));
    	
    	
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
        		
        		FileItem item = iter.next();

        		Asset asset = new Asset();
        		
        		String fileName = item.getName();  	
        		
        		asset.setName(fileName);
		        
        		System.out.println(servletContext+File.separator+folderLocation+File.separator+project_id+
        				File.separator+fileName);
        		
        		String uploadPath = getServletContext().getRealPath("")
        			    + File.separator + folderLocation;
        		
        		//String uploadPath = File.separator + folderLocation + File.separator + project_id;
        		
        		File uploadDir = new File(uploadPath);
        		if (!uploadDir.exists()) {
        		    uploadDir.mkdir();
        		    System.out.println("projectassets does not exist.");
        		} else {
        			System.out.println("projectassets exists.");
        		}
        		
        		uploadPath = uploadPath+ File.separator + project_id;
        		
        		uploadDir = new File(uploadPath);
        		if (!uploadDir.exists()) {
        		    uploadDir.mkdir();
        		    System.out.println("project folder does not exist.");
        		} else {
        			System.out.println("project folder exists.");
        		}
        		
		        File uploadedFile = 
		        		new File(uploadPath+File.separator+fileName); 
		        
		        asset.setLocation(uploadPath+File.separator+fileName);
		        
		        item.write(uploadedFile);
        		
        		item = iter.next();
        		
        		asset.setDescription(item.getString());
        		
        		asset.setProjectID(project_id);
        		
        		asset.setCreatedBy(user_id);
        		asset.setUpdatedBy(user_id);
        		
        		AssetDB assetdb = new AssetDB();
        		assetdb.insert(asset);
        		
        		/*
        		while (iter.hasNext()) {
        		    FileItem item = iter.next();

        		    if (item.isFormField()) {
        		        processFormField(item);
        		    } else {
        		        processUploadedFile(item);
        		        
        		        // Process a file upload  
        		        String fileName = item.getName();  
        		           
        		        File uploadedFile = 
        		        new File(servletContext.getAttribute("javax.servlet.context.tempdir")+
        		        		File.separator+"projectassets"+File.separator+fileName);  
        		        item.write(uploadedFile);   
        		        
        		    }
        		}
        		*/
        		
        	} 
        	
        	response.sendRedirect(request.getContextPath() + "/asset/assets?project_id="+project_id);
        	
		} catch(FileUploadException fue) {  
			fue.printStackTrace();  
			throw new ServletException(fue.getMessage());  
		} catch(Exception e) {  
			e.printStackTrace();  
			throw new ServletException(e.getMessage());  
		}  
	}  
        	
            
    private void processFormField(FileItem item) {
    	System.out.println("This is a form field:");
    	System.out.println(item.getFieldName());
    	System.out.println(item.getString());
    	
    }
    
    private void processUploadedFile(FileItem item) {
    	System.out.println("This is a file:");
    	System.out.println(item.getFieldName());
    	System.out.println(item.getName());
    	System.out.println(item.getSize());
    	System.out.println(item.getContentType());
    	
    	
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
		
	}
    

}
