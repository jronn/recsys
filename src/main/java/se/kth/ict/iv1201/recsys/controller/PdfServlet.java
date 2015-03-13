/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used to create a PDF document of an application
 * 
 * @author Josef
 */
@WebServlet(name = "PdfServlet", urlPatterns = {"/PdfServlet"})
public class PdfServlet extends HttpServlet {
    
    /**
     * Servlet GET, returns a PDF with inforation specified as 
     * parameters if the user is logged in and of right user role
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            Principal user = request.getUserPrincipal();
            if(user == null || !request.isUserInRole("recruiter"))
                throw new ServletException("Not logged in");
            
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String submitDate = request.getParameter("submitdate");
            String[] competences = request.getParameter("comp").split("_");
            String[] availabilities = request.getParameter("avail").split("_");
            
            Font titleFont = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
            
            response.setContentType("application/pdf");
            response.setHeader(
		"Content-disposition",
		"attachment; filename=" + name + surname + "-application.pdf" );
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            
            Document doc = new Document();
            ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
            PdfWriter docWriter = PdfWriter.getInstance(doc, baosPDF);
            
            doc.open();
            
            doc.add(new Paragraph("Application", titleFont));
            doc.add(new Paragraph("Name: " + name +
                    "\nSurname: " + surname +
                    "\nApplication submit date: " + submitDate));
            
            doc.add(new Paragraph("Competences", titleFont));
            StringBuilder sb = new StringBuilder();           
            for(String comp : competences) {
                sb.append(comp + "\n");
            }            
            doc.add(new Paragraph(sb.toString()));
            
            doc.add(new Paragraph("Availabilities", titleFont));
            sb = new StringBuilder();
            for(String avail : availabilities) {
                sb.append(avail + "\n");
            }    
            doc.add(new Paragraph(sb.toString()));
            
            doc.close();
            docWriter.close();
            
            response.setContentLength(baosPDF.size());
            
            ServletOutputStream sos = response.getOutputStream();
            baosPDF.writeTo(sos);
            sos.flush();
        } catch (DocumentException ex) {
            Logger.getLogger(PdfServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
