package com.chef.servlets;

import com.chef.manager.UserManager;
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

public class FollowOrUnfollowServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            System.out.println("follow or unfollow");
            String username = request.getParameter("username");
            String followerUsername = request.getParameter("followerUsername");
            boolean present = new UserManager().checkIfFollowing(username, followerUsername);
            if (present) {
                boolean unfollow = new UserManager().unfollow(username, followerUsername);
                if (unfollow) {
                    out.println(Appconstants.UNFOLLOW);
                } else {
                    out.println(Appconstants.FAILED);
                }
            } else {
                boolean follow = new UserManager().follow(username, followerUsername);
                 if (follow) {
                    out.println(Appconstants.FOLLOW);
                } else {
                    out.println(Appconstants.FAILED);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FollowOrUnfollowServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FollowOrUnfollowServlet.class.getName()).log(Level.SEVERE, null, ex);
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
