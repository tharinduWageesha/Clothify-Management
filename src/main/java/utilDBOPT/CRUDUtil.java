package utilDBOPT;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDUtil {
    public static <T> T execute(String sql, Object... val) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        // Set parameters for the SQL query (for both SELECT and non-SELECT queries)
        for (int i = 0; i < val.length; i++) {
            pstm.setObject(i + 1, val[i]);
        }

        if (sql.trim().toUpperCase().startsWith("SELECT")) {
            return (T) pstm.executeQuery(); // Return the ResultSet
        } else {
            return (T) (Boolean) (pstm.executeUpdate() > 0); // Return boolean for non-SELECT queries
        }
    }
}

