package com.parkhomenko.persistence.config;

import com.parkhomenko.common.domain.special_types.DecimalMonetaryAmount;
import com.parkhomenko.common.domain.special_types.MonetaryAmount;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Currency;

/**
 * @author Dmytro Parkhomenko
 *         Created on 06.09.16.
 */

public class DecimalMonetaryAmountCompositeUserType implements CompositeUserType {

    @Override
    public String[] getPropertyNames() {
        return new String[] { "value", "currency" };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.CURRENCY };
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        MonetaryAmount monetaryAmount = (MonetaryAmount) component;
        if (property == 0) {
            return monetaryAmount.getValue();
        }
        return monetaryAmount.getCurrency();
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        throw new UnsupportedOperationException("Immutable!");
    }

    @Override
    public Class returnedClass() {
        return DecimalMonetaryAmount.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull()) return null;
        BigDecimal value = rs.getBigDecimal( names[0] );
        Currency currency = Currency.getInstance(rs.getString( names[1] ));
        return new DecimalMonetaryAmount(value, currency);
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value==null) {
            statement.setNull(index, Types.NUMERIC);
            statement.setNull(index+1, Types.VARCHAR);
        } else {
            MonetaryAmount amount = (MonetaryAmount) value;
            String currencyCode = amount.getCurrency().getCurrencyCode();
            statement.setBigDecimal( index, amount.getValue() );
            statement.setString( index+1, currencyCode );
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
        return original;
    }
}
