package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUser(String carModel, int carSeries) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TypedQuery<User> userQuery = session.createQuery("from User where car.model = :modelParam and car.series=:seriesParam");
        userQuery.setParameter("modelParam", carModel);
        userQuery.setParameter("seriesParam", carSeries);
        User user = userQuery.setMaxResults(1).getResultList().get(0);

        session.getTransaction().commit();
        return user;
    }

}
