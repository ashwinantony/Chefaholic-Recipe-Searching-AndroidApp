package com.chef.servlets;

import com.chef.bean.StoreBean;
import com.chef.manager.StoreManager;
import com.chef.util.Appconstants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.session.StoreBase;
import org.json.JSONObject;

public class AddStoreServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/octet-stream");
        // PrintWriter out = response.getWriter();
        try {
            System.out.println("Add store");
            StoreBean bean = null;
            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
            Object object = ois.readObject();            
            if (object instanceof StoreBean) {
                bean = (StoreBean) object;
            }        
            String[] latLng = bean.getLatLng().split(",");
            bean.setLatitude(latLng[0]);
            bean.setLongitude(latLng[1]);
            boolean exist = new StoreManager().checkIfAdded(bean);
            if (!exist) {
                String added = new StoreManager().addStore(bean);
                ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
                oos.writeObject(added);
                oos.flush();
                oos.close();
            } else {
                ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
                oos.writeObject(Appconstants.EXIST);
                oos.flush();
                oos.close();              
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddStoreServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddStoreServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // out.close();
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
