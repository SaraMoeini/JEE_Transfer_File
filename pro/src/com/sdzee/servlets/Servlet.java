package com.sdzee.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



public class Servlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int TAILLE_TAMPON = 10240;		//buffer
	public static final String CHEMIN_FICHIERS = "/home/sara/eclipse-workspace/"; // Where to permanently save the file

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/form.jsp").forward(request,  response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String description = request.getParameter("fichier");
		request.setAttribute("description", description);
		
		//
		Part part = request.getPart("fichier");
		
		//check received file
		String fileName = getFileName(part);
		
		//if its ok
		if(fileName !=null && !fileName.isEmpty())	{
			String champName = part.getName();
			
			//correcting an IE bug
			fileName.substring(fileName.lastIndexOf('/')+ 1 ).substring(fileName.lastIndexOf('\\') + 1);
		}
		//write the file permanently on disk
		writeFile(part, fileName, CHEMIN_FICHIERS);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/form.jsp").forward(request,  response);

	}


	private void writeFile(Part part, String fileName, String chemin) throws IOException {

		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		
		try {
			input = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
			output = new BufferedOutputStream(new FileOutputStream(new File(chemin+fileName)), TAILLE_TAMPON);
			

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = input.read(tampon)) > 0) {
                output.write(tampon, 0, longueur);
            }
        } finally {
            try {
                input.close();
            } catch (IOException ignore) {
            }
            try {
                output.close();
            } catch (IOException ignore) {
            }
			
		}
	}


	private String getFileName(Part part) {
		// TODO Auto-generated method stub
		 for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	            if ( contentDisposition.trim().startsWith( "filename" ) ) {
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
	            }
	        }
	        return null;
	    }   	
}
