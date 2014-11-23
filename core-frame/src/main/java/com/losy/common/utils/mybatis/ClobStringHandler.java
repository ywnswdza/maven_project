package com.losy.common.utils.mybatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class ClobStringHandler extends BaseTypeHandler<Object> {

	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
	throws SQLException
	{
		String s = (String)parameter;
		StringReader reader = new StringReader(s);
		ps.setCharacterStream(i, reader, s.length());
	}
	
	public Object getNullableResult(ResultSet rs, String columnName)
		throws SQLException
	{
		String value = "";
		Reader in = rs.getCharacterStream(columnName);
		if (in != null){
			BufferedReader br = new BufferedReader(in);
            char[] buff = new char[4096];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            try {
				while( ( len= br.read(buff, 0, 4096)) !=-1){
				        sb.append(buff, 0, len);
				}
				value = sb.toString();
			} catch (IOException e) {
				throw new SQLException(e);
			}	
		}
		return value;
	}
	
	public Object getNullableResult(CallableStatement cs, int columnIndex)
		throws SQLException
	{
		String value = "";
		Clob clob = cs.getClob(columnIndex);
		if (clob != null)
		{
			int size = (int)clob.length();
			value = clob.getSubString(1L, size);
		}
		return value;
	}

	@Override
	public Object getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		
		return null;
	}

}
