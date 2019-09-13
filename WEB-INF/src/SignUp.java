import java.util.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class SignUp extends HttpServlet {
    private String username, password, role;
    private Connection con;
    private Statement stmt;

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // Establish connection
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
            stmt = con.createStatement();
            // Fetch data from form
            username = req.getParameter("username");
            password = req.getParameter("password");
            role = req.getParameter("desig");
            // Get usernames and passwords
            boolean valid = true;
            String getUP = "select username, password from users";
            ResultSet rs;
            try {
                rs = stmt.executeQuery(getUP);
                while (rs.next()) {
                    if (rs.getString(1).equals(username)) {
                        // Username already exists
                        valid = false;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            if (valid) {
                if (role.equals("Employee")) {
                    role = "E";
                } else if (role.equals("Leader")) {
                    role = "L";
                } else if (role.equals("Manager")) {
                    role = "M";
                } else {
                    role = "";
                }
                try {
                    String a = "insert into users values(" + "'" + username + "', '" + password + "' , '" + role + "')";
                    stmt.executeUpdate(a);
                    PrintWriter out = res.getWriter();
                    out.println("<p>Registered Successfully</p>");
                    RequestDispatcher rd = req.getRequestDispatcher("/sg.html");
                    rd.forward(req,res);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                PrintWriter out = res.getWriter();
                out.println("<script><alert>Username already exists</alert></script>");
                RequestDispatcher rd = req.getRequestDispatcher("/sg.html");
                rd.include(req, res);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}