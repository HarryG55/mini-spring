package com.minis.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArgumentPreparedStatementSetter {
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    public void setValues(PreparedStatement pstmt) throws SQLException{
        if(args != null){
            for(int i = 0; i < args.length; i++){
                Object arg = args[i];
                doSetValue(pstmt,i + 1,arg);
            }
        }
    }

    protected void doSetValue(PreparedStatement pstmt,int parameterPosition,Object argValue) throws SQLException{
        if(argValue instanceof String){
            pstmt.setString(parameterPosition,(String)argValue);
        }else if(argValue instanceof Integer){
            pstmt.setInt(parameterPosition,(Integer)argValue);
        }else if(argValue instanceof Long){
            pstmt.setLong(parameterPosition,(Long)argValue);
        }else if(argValue instanceof Double){
            pstmt.setDouble(parameterPosition,(Double)argValue);
        }else if(argValue instanceof Float){
            pstmt.setFloat(parameterPosition,(Float)argValue);
        }else if(argValue instanceof Boolean){
            pstmt.setBoolean(parameterPosition,(Boolean)argValue);
        }else if(argValue instanceof java.util.Date){
            pstmt.setDate(parameterPosition,new java.sql.Date(((java.util.Date)argValue).getTime()));
        }else if(argValue instanceof java.sql.Date){
            pstmt.setDate(parameterPosition,(java.sql.Date)argValue);
        }else if(argValue instanceof java.sql.Timestamp){
            pstmt.setTimestamp(parameterPosition,(java.sql.Timestamp)argValue);
        }else if(argValue instanceof java.sql.Time){
            pstmt.setTime(parameterPosition,(java.sql.Time)argValue);
        }else if(argValue instanceof java.sql.Array){
            pstmt.setArray(parameterPosition,(java.sql.Array)argValue);
        }else if(argValue instanceof java.sql.Blob){
            pstmt.setBlob(parameterPosition,(java.sql.Blob)argValue);
        }else if(argValue instanceof java.sql.Clob){
            pstmt.setClob(parameterPosition,(java.sql.Clob)argValue);
        }else if(argValue instanceof java.sql.NClob){
            pstmt.setNClob(parameterPosition,(java.sql.NClob)argValue);
        }else if(argValue instanceof java.sql.Ref){
            pstmt.setRef(parameterPosition,(java.sql.Ref)argValue);
        }else if(argValue instanceof java.sql.RowId){
            pstmt.setRowId(parameterPosition,(java.sql.RowId)argValue);
        }else if(argValue instanceof java.sql.SQLXML){
            pstmt.setSQLXML(parameterPosition,(java.sql.SQLXML)argValue);
        }else{
            throw new IllegalArgumentException("Value of class [" + argValue.getClass().getName() + "] is not supported");
        }
    }
}
