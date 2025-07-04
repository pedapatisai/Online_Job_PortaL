package ojp.text.com;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Declare resources
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "ojp_dbs", "welcome1");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {

            // Set query parameters
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Execute query
            try (ResultSet rs = stmt.executeQuery()) {
                resp.setContentType("text/html");

                if (rs.next()) {
                    // Login successful, start session
                    HttpSession session = req.getSession();
                    session.setAttribute("userEmail", email);

                    // Redirect to home page
                    resp.sendRedirect("home.jsp");
                } else {
                    // Login failed
                    resp.sendRedirect("login.html?error=true");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
