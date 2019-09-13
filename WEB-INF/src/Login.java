import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Login extends HttpServlet{
    private String username,password;
    private Connection con;
    private Statement stmt;
    private RequestDispatcher rd;
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        
        username = req.getParameter("username");
        password = req.getParameter("password");
        String query = "select count(*) from users where username='" + username + "' AND password='" + password + "'";
        // Get connection
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
            stmt = con.createStatement();

            // Get count of matching usernames and passwords
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Username : " + username);
            System.out.println("Password : " + password);
            rs.next();
            int count = rs.getInt(1);
            System.out.println("Count of records : " + count);
            if(count == 1){
                query = "select role from users where username='" + username + "' AND password='" + password + "'";
                rs = stmt.executeQuery(query);
                rs.next();
                String role = rs.getString(1);
                if(role.equals("E")){
                    // Forward to Employee dashboard
                    rd = req.getRequestDispatcher("/empDashboard.html");
                }else if(role.equals("L")){
                    // Forward to Leader dashboard
                    rd = req.getRequestDispatcher("/ldrDashboard.html");
                }else if(role.equals("M")){
                    // Forward to Manager dashboard
                    rd = req.getRequestDispatcher("/mgrDashboard.html");
                }
            }else{
                // Invalid username or password
                rd = req.getRequestDispatcher("/lg.html");
                PrintWriter out = res.getWriter();
                out.println("<body bgcolor='red'></body>");
                out.println("<script>alert('Invalid username or password')</script>");
            }
        rd.forward(req,res);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}