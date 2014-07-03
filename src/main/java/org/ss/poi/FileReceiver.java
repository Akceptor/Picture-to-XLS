package org.ss.poi;

import com.oreilly.servlet.MultipartRequest;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

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
            // Here we (rudely) write to /tmp and impose a 1M (1024K) limit.
            MultipartRequest multi =
                    new MultipartRequest(req, System.getProperty("java.io.tmpdir") + "/", 1024 * 1024 * 1024,
                            new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

            Enumeration<?> params = multi.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                String value = multi.getParameter(name);
                //out.println(name + " = " + value);
                Parameters.setCellSize(multi.getParameter("CELL SIZE"));
                Parameters.setDecreaseColor(multi.getParameter("DECREASE COLOR"));
            }

            Enumeration<?> files = multi.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                String filename = multi.getFilesystemName(name);
                System.err.println(filename);

                IMGRead ir = new IMGRead();
                Map<String, Color[]> data = ir.read(System.getProperty("java.io.tmpdir") + File.separator + filename);
                POIWrite pw = new POIWrite();
                pw.write(data, new XSSFWorkbook(), "Picture", System.getProperty("java.io.tmpdir") + File.separator + filename);

                //send file
                res.setContentType("application/octet-stream");
                res.setHeader("Content-Disposition", "attachment; filename=\\" + filename + ".xlsx");
                File f = new File(System.getProperty("java.io.tmpdir"));
                List<String> names = new ArrayList<String>(Arrays.asList(f.list()));
                System.err.println(names);
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(System.getProperty("java.io.tmpdir") + "/" + filename + ".xlsx");
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
        out.println("<HEAD><TITLE>Picture to Excell</TITLE>");
        out.println("<script type='text/javascript' src='//code.jquery.com/jquery-1.9.1.js'></script>");
        out.println("<script type='text/javascript'>");
        out.println("$(window).load(function(){function readURL(input) {\n" +
                "    if (input.files && input.files[0]) {\n" +
                "        var reader = new FileReader();\n" +
                "        reader.onload = function (e) {\n" +
                "            $('#preview').attr('src', e.target.result);\n" +
                "            $('#preview').attr('width', \"512px\");\n" +
                "        }\n" +
                "        reader.readAsDataURL(input.files[0]);\n" +
                "    }\n" +
                "}\n" +
                "$(\"#imgInp\").change(function(){\n" +
                "    readURL(this);\n" +
                "});});");
        out.println("</script>");
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println("<H1>Picture to Excell</H1>");
        out.println("<FORM ACTION=\"poipainter-0.0.1-SNAPSHOT/\" ENCTYPE=\"multipart/form-data\" METHOD=POST>");
        out.println("Select picture <INPUT TYPE=FILE NAME=file id=\"imgInp\"><BR>");
        out.println("<img id=\"preview\" src=\"#\" alt=\"Preview\" width=\"1px\"/><br>");
        out.println("<hr><BR><h2>Color depth</h2>");

        out.println("<INPUT TYPE=CHECKBOX NAME='DECREASE COLOR' CHECKED>Decrease to 8 bits per pixel (256 colors)</INPUT><BR><h2>Picture pixel size</h2>");
        out.println("<input type=\"radio\" name=\"CELL SIZE\" value=\"PIXEL\" checked>PIXEL<br>\n" +
                "<input type=\"radio\" name=\"CELL SIZE\" value=\"SMALL\">SMALL<br>\n" +
                "<input type=\"radio\" name=\"CELL SIZE\" value=\"BIG\">BIG<br>\n");
        out.println("<INPUT TYPE=SUBMIT>");
        out.println("</FORM>");
        out.println("</BODY></HTML>");

    }
}