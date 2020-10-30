package model.entity.dao.mappers;

import model.entity.Check;
import model.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CheckMapper implements ObjectMapper<Check> {
    @Override
    public Check extractFromResultSet(ResultSet resultSet) throws SQLException {
        Check check = new Check();
        check.setId(resultSet.getInt("check_id"));
        check.setTotalPrice(resultSet.getDouble("totalprice"));
        check.setLocalDate(resultSet.getDate("create_date").toLocalDate());
        check.setStatus(Check.Status.valueOf(resultSet.getString("status")));
        User user = new UserMapper().extractFromResultSet(resultSet);
        check.setCashier(user);
        return check;
    }

    public PreparedStatement extractToStatement(PreparedStatement statement, Check check) throws SQLException {
        statement.setDouble(1, check.getTotalPrice());
        statement.setDate(2, Date.valueOf(check.getLocalDate()));
        statement.setString(3, String.valueOf(check.getStatus()));
        statement.setInt(4, check.getCashier().getId());
        return statement;
    }

    @Override
    public Check makeUnique(Map<Integer, Check> cache, Check user) {
        return null;
    }
}
