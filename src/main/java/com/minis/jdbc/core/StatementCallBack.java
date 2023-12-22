package com.minis.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementCallBack {
    Object doStatement(Statement stmt) throws SQLException;
}
