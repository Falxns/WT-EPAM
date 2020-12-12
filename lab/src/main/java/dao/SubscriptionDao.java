package dao;

import entity.EntitySubscription;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubscriptionDao implements Dao<EntitySubscription>{

    @Override
    public void add(EntitySubscription entitySubscription) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            Criteria criteria = new Criteria();
            criteria.setPublicationId(entitySubscription.getPublicationId());
            criteria.setUserId(entitySubscription.getUserId());
            EntitySubscription subscription = getList(criteria).stream().findFirst().orElse(null);
            if (subscription == null) {
                String sql = "INSERT INTO subscription (user_id, publication_id,is_paid, payment_id) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entitySubscription.getUserId());
                statement.setInt(2, entitySubscription.getPublicationId());
                statement.setBoolean(3, entitySubscription.isPaid());
                statement.setInt(4, 0);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                entitySubscription.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public EntitySubscription getById(int id) throws DaoExcept {
        return null;
    }

    @Override
    public List<EntitySubscription> getList(Criteria criteria) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            PreparedStatement statement;
            if (criteria.existPublicationId()) {
                String sql = "SELECT * FROM subscription WHERE publication_id = ? AND user_id = ?";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, criteria.getPublicationId());
                statement.setInt(2, criteria.getUserId());
            } else {
                String sql = "SELECT * FROM subscription WHERE user_id = ?";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, criteria.getUserId());
            }
            ResultSet resultSet = statement.executeQuery();
            LinkedList<EntitySubscription> subscriptions = new LinkedList<EntitySubscription>();
            while (resultSet.next()) {
                int subscriptionId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int publicationId = resultSet.getInt("publication_id");
                int paymentId = resultSet.getInt("payment_id");
                boolean isPaid = resultSet.getBoolean("is_paid");
                EntitySubscription subscription = new EntitySubscription();
                subscription.setId(subscriptionId);
                subscription.setUserId(userId);
                subscription.setPublicationId(publicationId);
                subscription.setPaymentId(paymentId);
                subscription.setPaid(isPaid);
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(EntitySubscription entitySubscription) throws DaoExcept { }

    public void update(EntitySubscription entitySubscription, int paymentId) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            PreparedStatement statement;
            String sql = "UPDATE subscription SET payment_id = ?, is_paid = 1 WHERE subscription.id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, paymentId);
            statement.setInt(2, entitySubscription.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }
}