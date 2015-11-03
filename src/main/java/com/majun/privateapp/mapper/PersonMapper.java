package com.majun.privateapp.mapper;

import com.majun.privateapp.db.DB;
import com.majun.privateapp.domain.DomainObject;
import com.majun.privateapp.domain.Person;
import org.omg.CORBA.portable.ApplicationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ma Jun on 2015/10/3 0003.
 */
public class PersonMapper extends AbstractMapper{
    protected String findStatement() {
        return "SELECT "+COLUMNS+" FROM people WHERE id =?";
    }

    protected String findLastNameStatement =
            "Select "+COLUMNS+" FROM people "
            +" WHERE UPPER(lastname) like UPPER(?) "
            +" ORDER BY lastname";

    public static final String COLUMNS = " id, lastname, firstname, number_of_dependents ";

    public Person find(Long id) {
        return (Person)abstractFind(id);
    }

    public Person find(long id) {
        return find(new Long(id));
    }

    @Override
    protected DomainObject doLoad(Long id, ResultSet rs) throws SQLException {
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        return new Person(id, lastNameArg, firstNameArg, numDependentsArg);
    }

    public List findByLastName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DB.prepare(findLastNameStatement);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }


}
