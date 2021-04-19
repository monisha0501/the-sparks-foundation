/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Monisha
 */
public class TransferMoney extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TransferMoney</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransferMoney at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        pw.println("<html>");
        
        pw.println("<body>");
        
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://localhost:3306/bank";
            Connection con = DriverManager.getConnection(database, "root", "root");
            Statement stmt = con.createStatement();
            String query = "Select Name, Balance from TransferMoney";
            ResultSet rs;
            rs = stmt.executeQuery(query);
            pw.println("<form align='center' action='Transferred' method='POST'>");
            pw.println("<select name='sender'>");
            pw.println("<option>Select Sender</option>");
            while(rs.next())
            {
                String send = rs.getString("Name");
                pw.println("<option>");
                pw.println(send);
                pw.println("</option><br>");
            }
            pw.println("</select>");
            
            rs = stmt.executeQuery(query);
            pw.println("<select name='receiver'>");
            pw.println("<option>Select Receiver</option>");
            while(rs.next())
            {
                String send = rs.getString("Name");
                pw.println("<option>");
                pw.println(send);
                pw.println("</option>");
            }
                            
            pw.println("</select>");
            pw.println("<label for='amount'>Amount:</label>");
            pw.println("<input type='number' step='any' min='1' name='amount'>");
            pw.println("<br><br>");
            pw.println("<input type='reset' value='Reset'>");
            pw.println("<input type='submit' value='Transfer'>");
            pw.println("</form><br><br>");
                        
            stmt.close();
            con.close();
            
        } 
        catch (SQLException ex) {
            pw.println(ex);
        } 
        catch (ClassNotFoundException ex) {
            pw.println(ex);
        }
        
        pw.println();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
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
