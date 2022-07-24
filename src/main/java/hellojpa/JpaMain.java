package hellojpa;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // code 작성
        try {
            Set<String> favoriteFood = Set.of("치킨", "족발", "피자");
            List<AddressEntity> addressHistory = List.of(new AddressEntity(new Address("city1", "street1", "1000")),
                    new AddressEntity(new Address("city1", "street2", "2000")));
            Member member = new Member(null, "member1", favoriteFood, addressHistory);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new AddressEntity(new Address("city1", "street1", "1000")));
            findMember.getAddressHistory().add(new AddressEntity(new Address("new", "new", "1111")));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
