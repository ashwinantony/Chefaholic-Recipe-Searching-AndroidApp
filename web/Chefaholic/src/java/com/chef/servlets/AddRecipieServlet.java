package com.chef.servlets;

import com.chef.bean.RecipieBean;
import com.chef.manager.ImageManager;
import com.chef.manager.RecipieManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddRecipieServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/octet-stream");
        try {
            System.out.println("Add Recipie");
            RecipieBean bean = null;
            String path = "";
            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
            Object object = ois.readObject();
            if (object instanceof RecipieBean) {
                bean = (RecipieBean) object;
            }
            String folderPath = getServletContext().getRealPath(bean.getUsername());
            String imageName = bean.getFoodName();
            if (bean.getPic() != null) {
                path = new ImageManager().uploadImage(bean.getPic(), folderPath, imageName);
            }
            String addRecipie = new RecipieManager().addRecipie(bean, path);
            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
            oos.writeObject(addRecipie);
            oos.flush();
            oos.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddRecipieServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddRecipieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
