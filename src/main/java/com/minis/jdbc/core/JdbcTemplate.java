package com.minis.jdbc.core;

import java.sql.*;

public abstract class JdbcTemplate {
    public JdbcTemplate(){
    }

    public Object query(StatementCallBack stmtCallBack){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Object rtnObj = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("xxx");
            stmt = con.createStatement();
            return stmtCallBack.doStatement(stmt);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {}
        }
    }

    protected abstract Object rs2Object(ResultSet rs);
}
