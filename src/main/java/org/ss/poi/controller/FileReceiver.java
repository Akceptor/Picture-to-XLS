package org.ss.poi.controller;

import com.oreilly.servlet.MultipartRequest;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ss.poi.IMGRead;
import org.ss.poi.POIWrite;
import org.ss.poi.Parameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class FileReceiver {

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return "upload";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void processPicture(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
}
