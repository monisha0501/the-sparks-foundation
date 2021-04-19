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
import java.sql.Time;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Monisha
 */
public class TransactionHistory extends HttpServlet {

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
            out.println("<title>Servlet TransactionHistory</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransactionHistory at " + request.getContextPath() + "</h1>");
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
        RequestDispatcher dis = req.getRequestDispatcher("view_trans.html");
        dis.include(req, res);
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Transfer Money</title>");
        pw.println("<link href='templates/table.css' rel='stylesheet' type='text/css'>");
        pw.println("</head>");
        pw.println("<body>");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://localhost:3306/bank";
            Connection con = DriverManager.getConnection(database, "root", "root");
            Statement stmt = con.createStatement();
            String query = "Select * from Transactions";
            ResultSet rs = stmt.executeQuery(query);
            pw.println("<table border=1 width=50% height=50%>");
            pw.println("<tr><th>S.No</th><th>Sender</th><th>Receiver</th><th>Amount</th><th>Date</th><th>Time</th></tr>");
            while(rs.next())
            {
                int sn = rs.getInt("S_No");
                String send = rs.getString("Sender");
                String rece = rs.getString("Receiver");
                Double amt = rs.getDouble("Amount");
                Date d = rs.getDate("Trans_date");
                Time t = rs.getTime("Trans_time");
                pw.println("<tr><td>" +sn+ "</td><td>" +send+ "</td><td>" +rece+ "</td><td>" +amt+ "</td><td>" +d+ "</td><td>" +t+ "</td></tr>");
            }
            pw.println("</table>");
        } 
        catch (ClassNotFoundException ex) {
            pw.println(ex);
        } 
        catch (SQLException ex) {
            pw.println(ex);
        }
        pw.println("</body>");
        pw.println("</html>");
        
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
