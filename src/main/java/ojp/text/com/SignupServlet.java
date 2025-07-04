package ojp.text.com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userType = req.getParameter("userType");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "ojp_dbs", "welcome1");

            PreparedStatement stmt;
            int result = 0;

            if ("JobSeeker".equalsIgnoreCase(userType)) {
                String jobStatus = req.getParameter("jobStatus");
                String phoneNumber = req.getParameter("phoneNumber");
                String dateOfBirthStr = req.getParameter("dateOfBirth");
                String gender = req.getParameter("gender");
                String location = req.getParameter("location");
                String highestDegree = req.getParameter("highestDegree");
                String fieldOfStudy = req.getParameter("fieldOfStudy");
                String institutionName = req.getParameter("institutionName");
                String completionYear = req.getParameter("completionYear");
                String skills = req.getParameter("skills");
                String languages = req.getParameter("languages");

                Date dateOfBirth = dateOfBirthStr != null && !dateOfBirthStr.isEmpty()
                        ? Date.valueOf(dateOfBirthStr)
                        : null;

                String query = "INSERT INTO users (ID, first_name, last_name, email, password, user_type, job_status, phone_number, date_of_birth, gender, location, highest_degree, field_of_study, institution_name, completion_year, skills, languages) "
                        + "VALUES (users_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, userType);
                stmt.setString(6, jobStatus);
                stmt.setString(7, phoneNumber);
                stmt.setDate(8, dateOfBirth);
                stmt.setString(9, gender);
                stmt.setString(10, location);
                stmt.setString(11, highestDegree);
                stmt.setString(12, fieldOfStudy);
                stmt.setString(13, institutionName);
                stmt.setString(14, completionYear);
                stmt.setString(15, skills);
                stmt.setString(16, languages);

                result = stmt.executeUpdate();

            } else if ("Employer".equalsIgnoreCase(userType)) {
                String companyName = req.getParameter("companyName");
                String companyGST = req.getParameter("companyGST");
                String companyAddress = req.getParameter("companyAddress");
                String companyWebsite = req.getParameter("companyWebsite");
                String companyDescription = req.getParameter("companyDescription");

                String query = "INSERT INTO recruiters (ID, first_name, last_name, email, password, user_type, company_name, company_gst, company_address, company_website, company_description) "
                        + "VALUES (recruiters_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, userType);
                stmt.setString(6, companyName);
                stmt.setString(7, companyGST);
                stmt.setString(8, companyAddress);
                stmt.setString(9, companyWebsite);
                stmt.setString(10, companyDescription);

                result = stmt.executeUpdate();
            }

            resp.setContentType("text/html");
            resp.getWriter().println("<h2>Registration Successful!</h2>");

        } catch (Exception e) {
            resp.setContentType("text/html");
            resp.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
