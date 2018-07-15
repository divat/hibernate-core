package de.laliluna.other.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * CustomType to be used in Hibernate Garanties that the key is lowercase and
 * has no trailing whitespaces
 *
 * @author Sebastian Hennebrueder
 */
public class KeyType implements UserType {

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return new String((String) value);

    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        boolean result = false;
        if (x == y)
            result = true;
        else {
            if (x == null || y == null) // both cannot be null else x== y
                result = false;
            else {
                result = x.equals(y);
            }
        }
        return result;
    }

    public int hashCode(Object x) throws HibernateException {
        int hash = 0;
        if (x != null)
            hash = x.hashCode();
        return hash;
    }

    public boolean isMutable() {
        return true;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

        // user type is mapped to one column, so get this value
        String key = rs.getString(names[0]);
        if (rs.wasNull())
            return null;
        else
            return key;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {

        if (value != null) {
            String key = (String) value;
            key = key.toLowerCase().trim();
            st.setString(index, key);
        } else
            st.setNull(index, Types.VARCHAR);

    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return new String((String) original);
    }

    public Class returnedClass() {
        return String.class;
    }

    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

}
