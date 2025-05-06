package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class FetchJoinMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team1 = new Team();
        team1.setName("Team 1");
        em.persist(team1);

        Team team2 = new Team();
        team2.setName("Team 2");
        em.persist(team2);

        Member member1 = new Member();
        member1.setUsername("member1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setTeam(team2);
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("member3");
        member3.setTeam(team1);
        em.persist(member3);

        Member member4 = new Member();
        member4.setUsername("member4");
        em.persist(member4);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member: " + member.getUsername() + " team: " + member.getTeam().getName());
        }
    }
}
