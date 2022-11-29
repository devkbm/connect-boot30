package com.like.system.core.jpa.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class AuditorDetailsType implements UserType {

	@Override
	public int getSqlType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Class returnedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object deepCopy(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable disassemble(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object detached, Object managed, Object owner) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
