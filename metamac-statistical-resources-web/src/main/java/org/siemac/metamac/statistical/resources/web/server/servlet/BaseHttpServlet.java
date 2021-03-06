package org.siemac.metamac.statistical.resources.web.server.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebTranslateExceptions;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.siemac.metamac.web.common.shared.exception.MetamacWebExceptionItem;

public abstract class BaseHttpServlet extends HttpServlet {

    private static final long serialVersionUID = 4606765255026000292L;

    protected File            tmpDir;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    protected abstract void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * Given an {@link InputStream}, creates a file in the temporal directory
     * 
     * @param fileName
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected String inputStreamToTempFile(String fileName, InputStream inputStream) throws IOException {

        OutputStream outputStream = null;
        File outputFile = null;

        try {

            File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");

            outputFile = new File(tempDir, fileName);
            outputStream = new FileOutputStream(outputFile);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } catch (IOException e) {
            throw e;

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return outputFile.getPath();
    }

    protected List<URL> getURLsFromFiles(List<File> files) throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        for (File file : files) {
            urls.add(file.toURI().toURL());
        }
        return urls;
    }

    protected void sendResponse(HttpServletResponse response, String action) throws IOException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<script type=\"text/javascript\">");
        out.println(action);
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    protected String getMessageFromMetamacWebException(MetamacWebException e) {
        List<MetamacWebExceptionItem> items = e.getWebExceptionItems();
        if (items != null && !items.isEmpty()) {
            return items.get(0).getMessage(); // only return the first message error
        }
        return null;
    }

    protected void throwMetamacWebException(String exceptionCode) throws MetamacWebException {
        String exceptionMessage = getTranslatedMessage(exceptionCode);
        throw new MetamacWebException(exceptionCode, exceptionMessage);
    }

    private String getTranslatedMessage(String messageCode) {

        WebTranslateExceptions webTranslateExceptions = (WebTranslateExceptions) ApplicationContextProvider.getApplicationContext().getBean("webTranslateExceptions");

        Locale locale = (Locale) ServiceContextHolder.getCurrentServiceContext().getProperty(LocaleConstants.locale);
        return webTranslateExceptions.getTranslatedMessage(messageCode, locale);
    }
}
