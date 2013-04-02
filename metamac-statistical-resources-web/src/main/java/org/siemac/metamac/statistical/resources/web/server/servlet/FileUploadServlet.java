package org.siemac.metamac.statistical.resources.web.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(FileUploadServlet.class.getName());

    private File          tmpDir;

    // private File destinationDir;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("FileUpload Servlet");
        tmpDir = new File(((File) getServletContext().getAttribute("javax.servlet.context.tempdir")).toString());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check that we have a file upload request
        if (ServletFileUpload.isMultipartContent(request)) {
            processFiles(request, response);
        } else {
            processQuery(request, response);
        }
    }

    @SuppressWarnings("rawtypes")
    private void processFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = new String();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Get the temporary directory (this is where files that exceed the threshold will be stored)
            factory.setRepository(tmpDir);

            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List items = upload.parseRequest(request);

            // Process the uploaded items
            Iterator itr = items.iterator();

            
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                File tempFile =  File.createTempFile("datasource-", ".dat");
                FileUtils.copyInputStreamToFile(item.getInputStream(), tempFile);
                fileName = tempFile.getAbsolutePath();
            }

            response.setContentType("text/html");
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\">");
            out.println("if (parent.uploadComplete) parent.uploadComplete('" + fileName + "');");
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
            out.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error importing file = " + fileName + ". " + e.getMessage());
            response.setContentType("text/html");
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<script type=\"text/javascript\">");
            out.println("if (parent.uploadFailed) parent.uploadFailed('" + fileName + "');");
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
            out.flush();
        }
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
