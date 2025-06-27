package com.hexaware.sis.util;

import java.util.*;

import java.sql.*;

public class DynamicQueryBuilderUtil {
	
    public static List<String[]> executeDynamicQuery(
            String[] columns, 
            String tableName, 
            String condition, 
            String orderBy) {

        List<String[]> result = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = DBConnUtil.getConnection();

            // Construct query
            String colList = (columns == null || columns.length == 0) ? "*" : String.join(", ", columns);
            StringBuilder query = new StringBuilder("SELECT ").append(colList)
                                                              .append(" FROM ").append(tableName);
            if (condition != null && !condition.trim().isEmpty()) {
                query.append(" WHERE ").append(condition);
            }
            if (orderBy != null && !orderBy.trim().isEmpty()) {
                query.append(" ORDER BY ").append(orderBy);
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query.toString());

            // Fetch metadata to determine column count
            int colCount = rs.getMetaData().getColumnCount();

            // Read each row as array of strings
            while (rs.next()) {
                String[] row = new String[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                result.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error executing dynamic query: " + e.getMessage());
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }

        return result;
    }
	
	

}
