package uz.educrmsystem.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import uz.educrmsystem.entity.User;

import java.util.Optional;


@Component
@Transactional
public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(entityManager.createQuery("select u from users u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult());
    }

    public User saveUser(User user) {
        entityManager.persist(user);
        return user;
    }
}
