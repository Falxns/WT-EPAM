package services;

import dao.*;
import entity.*;
import java.util.List;

public class Service {
    private final UserDao userDao = new UserDao();
    private final PublicationDao publicationDao = new PublicationDao();
    private final SubscriptionDao subscriptionDao = new SubscriptionDao();
    private final PaymentDao paymentDao = new PaymentDao();

    public boolean register(EntityUser user) throws ServiceExcept {
        Criteria criteria = new Criteria();
        criteria.setEmail(user.getEmail());
        EntityUser existingUser;
        try {
            existingUser = userDao.getList(criteria).stream().findFirst().orElse(null);
            if (existingUser != null) {
                return false;
            }
            userDao.add(user);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
        return true;
    }

    public boolean auth(EntityUser user) throws ServiceExcept {
        Criteria criteria = new Criteria();
        criteria.setUsername(user.getUsername());
        EntityUser existingUser;
        try {
            existingUser = userDao.getList(criteria).stream().findFirst().orElse(null);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return false;
        }
        user.setId(existingUser.getId());
        user.setUsername(existingUser.getUsername());
        user.setRole(existingUser.getRole());
        return true;
    }

    public List<EntityPublication> getPublications() throws ServiceExcept {
        Criteria criteria = new Criteria();
        try {
            return publicationDao.getList(criteria);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public void savePubl(EntityPublication publication) throws ServiceExcept {
        try {
            publicationDao.add(publication);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public void deletePubl(int publicationId) throws ServiceExcept {
        EntityPublication publication = new EntityPublication();
        publication.setId(publicationId);
        try {
            publicationDao.delete(publication);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public EntityPublication getPublication(int publicationId) throws ServiceExcept {
        try {
            return publicationDao.getById(publicationId);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public void order(int publicationId, EntityUser user) throws ServiceExcept {
        EntitySubscription subscription = new EntitySubscription();
        subscription.setUserId(user.getId());
        subscription.setPublicationId(publicationId);
        try {
            subscriptionDao.add(subscription);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public List<EntityPublication> payment(int userId) throws ServiceExcept{
        Criteria criteria = new Criteria();
        criteria.setUserId(userId);
        try {
            return publicationDao.getList(criteria);
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }

    public void paymentSubmit(List<EntityPublication> list, int userId) throws ServiceExcept {
        try {
            EntityPayment entityPayment = new EntityPayment();
            int sum = 0;
            for (EntityPublication entity:list) {
                sum += entity.getCost();
            }
            entityPayment.setCost(sum);
            paymentDao.add(entityPayment);
            Criteria criteria = new Criteria();
            for (EntityPublication entity:list) {
                criteria.setPublicationId(entity.getId());
                criteria.setUserId(userId);
                EntitySubscription subscription = subscriptionDao.getList(criteria).stream().findFirst().orElse(null);
                if (subscription != null) {
                    subscriptionDao.update(subscription, entityPayment.getId());
                }
            }
        } catch (DaoExcept e) {
            throw new ServiceExcept(e);
        }
    }
}