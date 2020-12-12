package dao;

import entity.EntityPublication;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PublicationDao implements Dao<EntityPublication> {
    @Override
    public void add(EntityPublication entityPublication) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            String sql = "INSERT INTO publication (name, cost, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entityPublication.getName());
            statement.setInt(2, entityPublication.getCost());
            statement.setString(3, entityPublication.getDescription());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            entityPublication.setId(generatedKeys.getInt(1));
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public EntityPublication getById(int id) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            String selectPublications = "SELECT * FROM publication p WHERE p.id = ?";
            PreparedStatement statement = connection.prepareStatement(selectPublications);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            EntityPublication entityPublication = null;
            if (resultSet.next()) {
                int publicationId = resultSet.getInt("p.id");
                String name = resultSet.getString("p.name");
                int cost = resultSet.getInt("p.cost");
                String description = resultSet.getString("p.description");
                entityPublication = new EntityPublication();
                entityPublication.setId(publicationId);
                entityPublication.setName(name);
                entityPublication.setCost(cost);
                entityPublication.setDescription(description);
            }
            return entityPublication;
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public List<EntityPublication> getList(Criteria criteria) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            PreparedStatement statement;
            if (criteria.existUserId()) {
                String sql = "SELECT * FROM publication p, subscription s WHERE s.user_id = ? AND s.publication_id = p.id AND s.is_paid = 0";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, criteria.getUserId());
            } else {
                String sql = "SELECT * FROM publication";
                statement = connection.prepareStatement(sql);
            }
            ResultSet resultSet = statement.executeQuery();
            LinkedList<EntityPublication> publications = new LinkedList<EntityPublication>();
            while (resultSet.next()) {
                int publicationId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int cost = resultSet.getInt("cost");
                String description = resultSet.getString("description");
                EntityPublication publication = new EntityPublication();
                publication.setId(publicationId);
                publication.setName(name);
                publication.setCost(cost);
                publication.setDescription(description);
                publications.add(publication);
            }
            return publications;
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(EntityPublication entityPublication) throws DaoExcept {
        ConnectPool pool = null;
        Connection connection = null;
        try {
            pool = ConnectPool.getConnectionPool();
            connection = pool.getConnection();
            String sql = "DELETE FROM publication WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, entityPublication.getId());
            statement.executeUpdate();
        } catch (SQLException | ConnectExcept e) {
            throw new DaoExcept(e);
        } finally {
            if (pool != null)
                pool.returnConnection(connection);
        }
    }
}
