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

import com.librethinking.simmodsys.SIMModel;
import com.librethinking.simmodsys.SIMParameter;
import com.librethinking.simmodsys.SIMState;
import com.librethinking.simmodsys.ejb.ClientSupportBean;
import com.librethinking.simmodsys.ejb.ModelBean;
import com.librethinking.simmodsys.exceptions.business.FunctionalException;
import com.librethinking.simmodsys.exceptions.business.TechnicalException;
import com.librethinking.simmodsys.jasper.reports.pesm.PESMStateReportFacade;
import com.librethinking.simmodsys.jasper.reports.pesm.PESMStateReportFacadeConfigurator;
import com.librethinking.simmodsys.models.pesm.PESMModel;
import com.librethinking.simmodsys.persistence.jpa.hibernate.AdmTypes;
import com.librethinking.simmodsys.persistence.jpa.hibernate.Model;
import com.librethinking.simmodsys.persistence.jpa.hibernate.User;
import com.librethinking.simmodsys.web.adapters.SIMParameterAdapter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * This Controller is in charge of all the operations concerning Models. 
 * This may be seeing as the main controller in the application.
 * @author Christian Vielma <cvielma@librethinking.com>
 */
@WebServlet(name = "ModelsController", urlPatterns = {"/ListModels", 
                                                      "/SaveModel", 
                                                      "/ConfigureModel", 
                                                      "/ExecuteModel",
                                                      "/ShowReport",
                                                      "/ShowResults"})
public class ModelsController extends HttpServlet {
    @EJB
    private ModelBean modelBean;
    @EJB
    private ClientSupportBean clientSupportBean;
    
    /** Prefix used to determine parameters with initial state values */
    private static final String INITIALPREF = "initial.";
    
    /** Prefix used to determine parameters with final state values */
    private static final String FINALPREF = "final.";
      

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
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getServletPath()) {
            case "/ListModels":
                this.listModels(request, response);
                break;
            case "/ConfigureModel":
                this.configureModel(request, response);
                break;
            case "/ExecuteModel":
                this.executeModel(request, response);
                break;
            case "/ShowReport":
                this.showReport(request, response);
                break;
            case "/SaveModel":
                this.saveModel(request, response);
                break;
            case "/ShowResults":
                this.showResults(request, response);
                break;
        }
        
        
    }
    
    /** This method list the possible models to be configured.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void listModels(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try { 
                List<AdmTypes> models = clientSupportBean.getActiveTypes("MT%");
                List<Model> savedModels = new ArrayList<>();
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                
                if(user!=null){
                    savedModels = modelBean.getSavedModelsList(user.getCUser());
                }
                                
                if(models.isEmpty() && savedModels.isEmpty()){
                    request.setAttribute("info", "There are no models available.");      //TODO: use bundle          
                } 
                else{
                    session.setAttribute("models", models);   //TODO: index of session variables             
                    session.setAttribute("savedModels", savedModels);                
                }
                request.getRequestDispatcher("Model/listModels.jsp").forward(request, response);
                
                
            } 
            catch (TechnicalException ex){
                request.setAttribute("error", "This is REALLY bad: "+ ex.getMessage());
                request.getRequestDispatcher(request.getContextPath()).forward(request, response);
            }
            catch (Exception ex){
                request.setAttribute("error", "Unexpected error: "+ ex.getMessage());
                request.getRequestDispatcher(request.getContextPath()).forward(request, response);
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

    /** This method creates model to be configured by the user
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void configureModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = (String) request.getParameter("modelid");
        if(type==null){
            request.setAttribute("error", "Type selected is null. Please select a valid type."); //TODO: use bundle
            request.getRequestDispatcher("Model/listModels.jsp").forward(request, response);
        }
        else {
            try {
                SIMModel model = modelBean.createModel(type);
                HttpSession session = request.getSession();
                ResourceBundle bundle = ResourceBundle.getBundle(ModelsController.class.getPackage().getName()+".Parameters"); //this.getClass().getPackage().getName()+
                session.setAttribute("configuremodel", model);   
                session.setAttribute("parameterbundle", bundle);
                request.getRequestDispatcher("Model/configureModel.jsp").forward(request, response);
                
            } catch (FunctionalException ex) {
                Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("error", "Error selecting model: "+ex.getMessage());
                request.getRequestDispatcher("ListModels").forward(request, response);
            }
            catch (TechnicalException ex) {
                Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("error", "This is REALLY bad: "+ ex.getMessage());
                request.getRequestDispatcher("ListModels").forward(request, response);
            }
            catch (Exception ex){
                request.setAttribute("error", "Unexpected error: "+ ex.getMessage());
                request.getRequestDispatcher("ListModels").forward(request, response);
            }
            
        }
    }
    
    
    /** This method creates a new state given the information from the request
     *  @param request Is map generated by calling request.getParameterMap(). This
     * contains data submitted by the user.
     *  @param prefix Is the prefix used to parse the parameters. 
     *  @param mock State with some parameters to be searched in the request.
     *  @param newState The state that will be set by the parameter values found.
     * 
     *  @return newState with the values found in the request set.
     * 
     *  @throws FunctionalException if a parameters value isn't set or if isn't of the 
     * type expected.
     *  @throws TehnicalException if at least one of the fields of the parameter 
     * can't be found, or there are problems reflecting the parameter.
     *  @throws RuntimeException for any other unexpected error.
     * 
     */
    private SIMState createState(Map<String, String[]> request, String prefix, SIMState mock, SIMState newState) throws FunctionalException{
        
        for(Set<SIMParameter> currSetParam : mock.getParameters()){
            SIMParameter currParam = (SIMParameter) currSetParam.toArray()[0];
            Set<SIMParameter> paramsSet = SIMParameterAdapter.MapToSIMParameter(currParam, prefix, request);
            if(!paramsSet.isEmpty()){
              newState.setParameter(currParam.getName(), paramsSet);
             }
        }
        
        return newState;
    }
    
    

     /** This method parses and executes a model configured by the user
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void executeModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SIMModel model = (SIMModel) session.getAttribute("configuremodel");     
        ResourceBundle bundle = ResourceBundle.getBundle(ModelsController.class.getPackage().getName()+".Parameters");
        
        SIMState initialMock = model.newInitialStateMock();
        SIMState finalMock = model.newFinalStateMock();
        SIMState initialNew = model.newDefStateInstance();
        SIMState finalNew = model.newDefStateInstance();
        Map<String, String[]> requestMap = request.getParameterMap();
        try { 
            initialNew = createState(requestMap, INITIALPREF, initialMock, initialNew);
            finalNew = createState(requestMap, FINALPREF, finalMock, finalNew);
            
            model = modelBean.executeModel(model.getType(), initialNew, finalNew);
             
            session.setAttribute("model", model);           
            
            response.sendRedirect("ShowResults");
            
        } catch (FunctionalException ex) { 
            request.setAttribute("error", "Error executing model: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);                
            //response.sendRedirect(request.getHeader("referer"));
            request.getRequestDispatcher("ConfigureModel?modelid="+model.getType()).forward(request, response);
        } catch (TechnicalException ex) { 
            request.setAttribute("error", "This is REALLY bad: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);                
            //response.sendRedirect(request.getHeader("referer"));
            request.getRequestDispatcher("ConfigureModel?modelid="+model.getType()).forward(request, response);
        } catch (Exception ex) { 
            request.setAttribute("error", "Unexpected error: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);               
            //response.sendRedirect(request.getHeader("referer"));
            request.getRequestDispatcher("ConfigureModel?modelid="+model.getType()).forward(request, response);
        }
        
    }

    
    //TODO: javadoc
    private void prepareReport(ResourceBundle bundle, SIMModel model, HttpServletResponse response) throws JRException, MalformedURLException, IOException{
        //TODO: use factory for this
        if(PESMModel.MODELTYPE.equals(model.getType())){
            JasperReport jasperReport = null;
            JasperDesign jasperDesign = null;
            Map parameters = new HashMap();
            String path = getServletContext().getRealPath("/WEB-INF/");
            jasperDesign = JRXmlLoader.load(path+"/classes/com/librethinking/simmodsys/jasper/reports/PESMReport.jrxml");
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            PESMStateReportFacadeConfigurator config = new PESMStateReportFacadeConfigurator();
            String title = bundle.getString(model.getType().toLowerCase()+".report.text.0");
            String series1 = bundle.getString(model.getType().toLowerCase()+".report.text.1");;
            String series2 = bundle.getString(model.getType().toLowerCase()+".report.text.2");;;
            String series3 = bundle.getString(model.getType().toLowerCase()+".report.text.3");;;
            String series4 = bundle.getString(model.getType().toLowerCase()+".report.text.4");;;
            
            config.setTexts(title, series1, series2, series3, series4); //TODO: set dynamically
            config.setModel((PESMModel) model);
            byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters, new JRBeanCollectionDataSource(PESMStateReportFacade.getStatesList(config)));
                        
            OutputStream outStream = response.getOutputStream();
            response.setHeader("Content-Disposition","inline, filename=myReport.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(byteStream.length);
            outStream.write(byteStream,0,byteStream.length);

        }
        
    }

    private void showReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SIMModel model = (SIMModel) session.getAttribute("model"); //TODO: do not hardcode session variables
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("parameterbundle");
        try { 
            prepareReport(bundle, model, response);            
            
        } catch (Exception ex) { 
            request.setAttribute("error", "Unexpected error: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);                           
            request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
        }
    }

    private void saveModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { 
            HttpSession session = request.getSession();
            SIMModel model = (SIMModel) session.getAttribute("model");      //TODO: remove hardcode
            String modelName = (String) request.getParameter("modelname");
            User user = (User) session.getAttribute("user");
            
            if(user!=null && modelName!= null){
                model.setName(modelName);
                model.setUser(user);
                modelBean.saveSIMModel(model);        
                request.setAttribute("modelSaved", true);//TODO: use field in model to know if it was saved or not
                //request.setAttribute("info", "message.modelsaved");   //TODO: avoid hardcode
                request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
            }
            else{
                throw new FunctionalException(user==null? "error.nologgedin": "error.nomodelname"); //TODO: remove hardcode
            }
            
        } catch (FunctionalException ex) { 
            request.setAttribute("error", "Error saving model: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);                
            request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
        } catch (TechnicalException ex) { 
            request.setAttribute("error", "This is REALLY bad: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);                
            request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
        } catch (Exception ex) { 
            request.setAttribute("error", "Unexpected error: "+ex.getMessage());
            Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);               
            request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
        }
    } 

    private void showResults(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        HttpSession session = request.getSession();
        String modelId = (String) request.getParameter("modelid");
        User user = (User) session.getAttribute("user");
        ResourceBundle bundle = ResourceBundle.getBundle(ModelsController.class.getPackage().getName()+".Parameters"); //TODO: parameterize
        session.setAttribute("parameterbundle", bundle);
        if(user != null){
            if(modelId!=null){
                int mId = Integer.parseInt(modelId); //TODO: handle parse exception
                
                 SIMModel model =  modelBean.getSavedModel(mId, user.getCUser());
                 
                 session.setAttribute("model", model);
                 request.setAttribute("modelSaved", true);//TODO: use field in model to know if it was saved or not
                 request.setAttribute("modelLoaded", true);//TODO: use field in model to know if it was saved or not
                 
                
                request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
            }else{
            request.getRequestDispatcher("Model/showResults.jsp").forward(request, response);
            }
        }
        else{
            request.getRequestDispatcher("/").forward(request, response);
        }
        } catch (FunctionalException ex) {
                    Logger.getLogger(ModelsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
