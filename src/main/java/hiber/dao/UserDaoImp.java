package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
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
// "unchecked"/
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String name, int series) {
//       Query query = sessionFactory.getCurrentSession().createQuery("select u from User u where u.car.name =:name and u.car.series = :series");
       Query query = sessionFactory.getCurrentSession().createNativeQuery("select users.* from users inner join cars on users.car_id=cars.id where cars.name = :name and cars.series = :series",User.class);
        query.setParameter("name", name);
        query.setParameter("series", series);
        User user = (User) query.uniqueResult();
        return user;


    }

}
