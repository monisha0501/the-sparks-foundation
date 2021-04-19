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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Monisha
 */
public class Transferred extends HttpServlet {

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
            out.println("<title>Servlet Transferred</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Transferred at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        String sender = req.getParameter("sender");
        String receiver = req.getParameter("receiver");
        String amt = req.getParameter("amount");
        if(amt == "" || amt == null)
        {
            pw.println("You have not entered the amount");
        }
        double amount = Double.parseDouble(amt);
                      
        int z = 0;
        int n = 0;
        if(sender.equals("Select Sender"))
        {
            pw.println("You have not selected sender");
            n = 1;
        }
        if(receiver.equals("Select Receiver"))
        {
            pw.println("You have not selected receiver");
            n = 1;
        }
        if(sender.equals(receiver))
        {
            pw.println("You selected same person as both sender and receiver");
        }
        
        else{
        pw.println("<html>");
        pw.println("<body>");
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                String DB1 = "jdbc:mysql://localhost:3306/bank";
                try (Connection con = DriverManager.getConnection(DB1, "root", "root")) {
                    Statement stmt = con.createStatement();
                    String query1 = "Select * from TransferMoney where Name = '" +sender+ "'";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    Double bal1 = 0.0;
                    while(rs1.next())
                    {
                        bal1 = rs1.getDouble("Balance");
                    }
                    if(amount > bal1 && n==0)
                    {
                        pw.println("Entered amount is greater than the amount sender has");
                    }
                    else
                    {                        
                        String query2 = "Select * from TransferMoney where Name = '" +receiver+ "'";
                        ResultSet rs2 = stmt.executeQuery(query2);
                        Double bal2 = 0.0;
                        while(rs2.next())
                        {
                            bal2 = rs2.getDouble("Balance");
                        }
                        double amt1 = bal1 - amount;
                        String query3 = "Update TransferMoney set Balance = " + amt1 + " where Name = '" +sender+ "'";
                        int j = 0;
                        int i = stmt.executeUpdate(query3);
                        j += i;
                        double amt2 = bal2 + amount;
                        String query4 = "Update TransferMoney set Balance = " + amt2 + " where Name = '" +receiver+ "'";
                        int k = stmt.executeUpdate(query4);
                        j += k;
                        if(j == 2)
                        {                            
                            String query5 = "Insert into Transactions(Sender, Receiver, Amount, Trans_Date, Trans_Time) values('" +sender+ "','" +receiver +"'," +amount+ ",curdate(), curtime() )";
                            z = stmt.executeUpdate(query5);
                            pw.println("<h1>Money transferred successfully</h1><br>");
                            pw.println("<h3>To see Transaction History, <a href='TransactionHistory'>click here<a>.</h3>");
                        }
                    }
                    stmt.close();
                }
            } 
            catch (SQLException | ClassNotFoundException ex) {
                pw.println(ex);
            }
                
            pw.println("</body>");
            pw.println("</html>");
        }
        pw.close();
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
