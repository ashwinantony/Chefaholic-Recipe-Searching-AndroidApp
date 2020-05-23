package com.chef.servlets;

import com.chef.manager.RecipieManager;
import com.chef.util.Appconstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class SearchUsingTagsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            System.out.println("in search using tags");
            String[] tagsArray = null;
            String tags = request.getParameter("tags");
            System.out.println("tags : " + tags);
            String username = request.getParameter("username");
            System.out.println("username : " + username);
            if (!tags.equals("")) {
                tagsArray = tags.split(",");
                if (tagsArray != null) {
                    if (!username.equals(Appconstants.ADMIN)) {
                        JSONArray searchUsingTags = new RecipieManager().searchUsingTags(tagsArray, username);
                        out.println(searchUsingTags);
                    } else {
                        JSONArray searchUsingTags = new RecipieManager().searchAdminRecipieUsingTags(tagsArray);
                        out.println(searchUsingTags);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchUsingTagsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchUsingTagsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
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
