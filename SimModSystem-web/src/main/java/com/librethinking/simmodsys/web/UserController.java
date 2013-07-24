/*
 * Copyright 2012 Christian Vielma <cvielma@librethinking.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.librethinking.simmodsys.web;

import com.librethinking.simmodsys.ejb.UserBean;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.persistence.jpa.hibernate.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@WebServlet(name = "UserController", urlPatterns = {"/Logout", "/RegisterUser", "/SaveUser", "/CheckLogin"})
public class UserController extends HttpServlet {
    @EJB 
    private UserBean userBean;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getServletPath().equals("/RegisterUser")){
            request.getRequestDispatcher("User/registerUser.jsp").forward(request, response);
        }        
        else if(request.getServletPath().equals("/CheckLogin")){
           this.checkLogin(request, response);
        }
        else if(request.getServletPath().equals("/SaveUser")){
           this.saveUser(request, response);
        }
        else if(request.getServletPath().equals("/Logout")){
            HttpSession session = request.getSession();
            session.invalidate();
            request.getRequestDispatcher("/simmodsys").forward(request, response);            
        }
        
    }
    
    /** This method saves the user information in the persistence manager.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     */
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
            String userid = (String) request.getParameter("userid");
            String userpasswd = (String) request.getParameter("userpasswd");
            String userFn = (String) request.getParameter("userfn");
            String userLn = (String) request.getParameter("userln");
            String userEm = (String) request.getParameter("userem");
            try {
                User user = new User(userid, false);
                user.setNmFirstname(userFn);
                user.setNmLastname(userLn);
                user.setVlEmail(userid);
                user.setVlPassword(userpasswd);
                
                userBean.saveUser(user);
                
                HttpSession session = request.getSession();
                //session.setAttribute("user", user); 
                request.setAttribute("info", "User successfully registered!"); //TODO: parameterize
                
                request.getRequestDispatcher("/simmodsys").forward(request, response);
                
                
            } catch (FunctionalException ex) {
                request.setAttribute("error", "Error in information provided: "+ ex.getMessage());
                request.getRequestDispatcher("User/registerUser.jsp").forward(request, response);
            }
            catch (TechnicalException ex){
                request.setAttribute("error", "There was a REALLY bad error: "+ ex.getMessage());
                request.getRequestDispatcher("User/registerUser.jsp").forward(request, response);
            }
            catch (Exception ex){
                request.setAttribute("error", "Unexpected error: "+ ex.getMessage());
                request.getRequestDispatcher("User/registerUser.jsp").forward(request, response);
            }
    }
    
    /** This method process the login information of an user
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs 
     */
    private void checkLogin(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
            String userid = (String) request.getParameter("userid");
            String userpasswd = (String) request.getParameter("userpasswd");
            try {
                User user = userBean.getUser(userid, userpasswd);
                if(user == null){
                    request.setAttribute("error", "Invalid login.");                
                }
                else{
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);                
                }
                request.getRequestDispatcher("/simmodsys").forward(request, response);
                
                
            } catch (FunctionalException ex) {
                request.setAttribute("error", "Login Failed: "+ ex.getMessage());
                request.getRequestDispatcher("/simmodsys").forward(request, response);
            }
            catch (TechnicalException ex){
                request.setAttribute("error", "This is REALLY bad: "+ ex.getMessage());
                request.getRequestDispatcher("/simmodsys").forward(request, response);
            }
            catch (Exception ex){
                request.setAttribute("error", "Unexpected error: "+ ex.getMessage());
                request.getRequestDispatcher("/simmodsys").forward(request, response);
            }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
