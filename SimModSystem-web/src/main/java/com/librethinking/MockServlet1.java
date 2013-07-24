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
package com.librethinking;

import com.librethinking.simmodsys.ejb.ClientSupportBean;
import com.librethinking.simmodsys.ejb.ModelManagement;
import com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@WebServlet(name = "MockServlet", urlPatterns = {"/MockServlet1"})
public class MockServlet1 extends HttpServlet {
    @EJB
    private ClientSupportBean clientSupportBean;

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
            throws ServletException, IOException, CloneNotSupportedException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MockServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MockServlet at " + request.getContextPath() + "</h1>");
           // modelManagement.listAdmTypesPrefix(null);
            
            List<AdmTypes> list = clientSupportBean.getActiveTypes("MT%");
            for(Iterator<AdmTypes> i = list.iterator(); i.hasNext();){
                AdmTypes curr = i.next();              
                String msg = "<p>"+curr.getDsType()+"</p>";
                out.println(msg);
            }               
            
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
       
            try {
                processRequest(request, response);
            } catch (Exception ex) {
                Logger.getLogger(MockServlet1.class.getName()).log(Level.SEVERE, null, ex);
            }
       
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
        
            try {
                processRequest(request, response);
            } catch (Exception ex) {
                Logger.getLogger(MockServlet1.class.getName()).log(Level.SEVERE, null, ex);
            }
       
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
