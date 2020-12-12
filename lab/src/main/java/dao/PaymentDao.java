package dao;

import entity.EntityPayment;
import java.sql.*;
import java.util.List;

public class PaymentDao implements Dao<EntityPayment>{
    @Override
    public void add(EntityPayment entityPayment) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            String sql = "INSERT INTO payment (cost) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entityPayment.getCost());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            entityPayment.setId(generatedKeys.getInt(1));
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public EntityPayment getById(int id) throws DaoExcept {
        return null;
    }

    @Override
    public List<EntityPayment> getList(Criteria criteria) throws DaoExcept {
        return null;
    }

    @Override
    public void delete(EntityPayment entityPayment) throws DaoExcept {}
}