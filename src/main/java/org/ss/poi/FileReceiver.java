package org.ss.poi;

import com.oreilly.servlet.MultipartRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FileReceiver extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Blindly take it on faith this is a multipart/form-data request
            // Construct a MultipartRequest to help read the information.
            // Pass in the request, a directory to save files to, and the
            // maximum POST size we should attempt to handle.
            // Here we (rudely) write to /tmp and impose a 500 K limit.
            MultipartRequest multi =
                    new MultipartRequest(req, System.getProperty("java.io.tmpdir") + "/", 500 * 1024 * 1024,
                            new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

            Enumeration<?> params = multi.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                String value = multi.getParameter(name);
                out.println(name + " = " + value);

            }

            Enumeration<?> files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                String filename = multi.getFilesystemName(name);
                System.err.println(filename);
                if (name.endsWith("gif")) {
                    GIFSplitter gs = new GIFSplitter();
                    gs.split(System.getProperty("java.io.tmpdir") + "/" + filename);
                } else {
                    IMGRead ir = new IMGRead();
                    Map<String, Object[]> data = ir.read(System.getProperty("java.io.tmpdir") + File.separator + filename);
                    POIWrite pw = new POIWrite();
                    pw.write(data, new HSSFWorkbook(), "Picture", System.getProperty("java.io.tmpdir") + File.separator + filename);

                }
                //send file
                res.setContentType("application/octet-stream");
                res.setHeader("Content-Disposition", "attachment; filename=\\" + filename + ".xls");
                File f = new File(System.getProperty("java.io.tmpdir"));
                List<String> names = new ArrayList<String>(Arrays.asList(f.list()));
                System.err.println(names);
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(System.getProperty("java.io.tmpdir") + "/" + filename + ".xls");
                int i;
                while ((i = fileInputStream.read()) != -1) {
                    out.write(i);
                }
                fileInputStream.close();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        out.close();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<HTML>");
        out.println("<HEAD><TITLE>Picture to Excell</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("<H1>Picture to Excell</H1>");
        out.println("<FORM ACTION=\"poipainter-0.0.1-SNAPSHOT/\" ENCTYPE=\"multipart/form-data\" METHOD=POST>");
        out.println("Select picture <INPUT TYPE=FILE NAME=file>");
        out.println("<INPUT TYPE=SUBMIT>");
        out.println("</FORM>");
        out.println("</BODY></HTML>");

    }
}