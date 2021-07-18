package demos.hibernate;


import demos.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaDemoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();

        User user = new User("John Smith");
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();


        User u = em.find(User.class, 1);
        System.out.println(String.format("User: %s", u));

//        em.getTransaction().begin();
//        //  List<User> users =
//        em.createQuery("SELECT u FROM User u", User.class)
//                .setFirstResult(0)
//                .setMaxResults(5)
//                .getResultList().stream()
//                .forEach(System.out::println);
//        em.getTransaction().commit();


        // TypeSafe queries
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<User> r = criteria.from(User.class);
        criteria.select(r).where(builder.like(r.get("name"), "J%"));

        em.createQuery(criteria)
                .setMaxResults(3)
                .getResultList().stream()
                .forEach(System.out::println);


    }
}
