package com.majun.privateapp.mapper;

import com.majun.privateapp.db.DB;
import com.majun.privateapp.domain.DomainObject;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.omg.CORBA.portable.ApplicationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ma Jun on 2015/10/3 0003.
 */
abstract public class AbstractMapper {

    protected Map loadedMap = new HashMap();
    abstract protected String findStatement();
    protected DomainObject abstractFind(Long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);
        if (result != null) return result;
        PreparedStatement findStatement = null;
        try {
            findStatement = DB.prepare(findStatement());
            findStatement.setLong(1, id.longValue());
            ResultSet rs = findStatement.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(findStatement);
        }

    }

    protected DomainObject load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        if (loadedMap.containsKey(id))
            return (DomainObject) loadedMap.get(id);
        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);
        return result;
    }

    abstract protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException;

    protected List loadAll(ResultSet rs) throws SQLException{
        List result = new ArrayList();
        while (rs.next())
            result.add(load(rs));
        return result;
    }

    public List findMany(StatementSource source) {

    }
}
