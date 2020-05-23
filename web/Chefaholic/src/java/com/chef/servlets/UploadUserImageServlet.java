package com.chef.servlets;

import com.chef.bean.RecipieBean;
import com.chef.manager.ImageManager;
import com.chef.manager.UserManager;
import com.chef.util.Appconstants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class UploadUserImageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
         response.setContentType("application/octet-stream");
        try {
            System.out.println("Upload image");
            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
            Object object = ois.readObject();
            RecipieBean bean = null;
            if (object instanceof RecipieBean) {
               bean = (RecipieBean) object;
            }
            String folder = getServletContext().getRealPath(Appconstants.PROFILE_PIC);
            String path = new ImageManager().uploadImage(bean.getPic(), folder, bean.getUsername());
            String uploadProfilePic = new UserManager().uploadProfilePic(bean.getUsername(), path);
            System.out.println(uploadProfilePic);
            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
            oos.writeObject(uploadProfilePic);
            oos.flush();
            oos.close();         
        } catch (Exception ex) {
            Logger.getLogger(UploadUserImageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
