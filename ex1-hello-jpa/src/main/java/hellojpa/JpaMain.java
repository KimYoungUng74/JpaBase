package hellojpa;

import hellojpa.domain.*;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("Team 1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("김영웅");
            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("송유빈");
            member2.setTeam(team);
            member2.setType(MemberType.USER);
            em.persist(member2);

            // 조회
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query.getResultList();

            for (Member m : resultList) {
                System.out.println("memberName = " + m.getUsername());
            }

            // 파라미터 사용한 조회
            TypedQuery<Member> memberTypedQuery = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "김영웅");
            List<Member> memberList = memberTypedQuery.getResultList();

            for (Member m : memberList) {
                System.out.println("param ex memberName = " + m.getUsername());
            }

            // new 명령어로 조회
            List<MemberDTO> dtoResultList = em.createQuery("select new hellojpa.domain.MemberDTO(m.username, m.age) from Member m where m.id = :id")
                    .setParameter("id", 2l).getResultList();

            MemberDTO dto = dtoResultList.get(0);
            System.out.println("dto = " + dto);
            System.out.println("userName = " + dto.getUsername());
            System.out.println("age = " + dto.getAge());

            // join 예제
            String queryString = "select m from Member m inner join m.team t";
            List<Member> result = em.createQuery(queryString).getResultList();

            for (Member m : result) {
                System.out.println("join ex memberName = " + m.getUsername());
            }

            // enum타입 조회 예시
            queryString = "select m.username, 'HELLO', TRUE from Member m " +
                          "where m.type = hellojpa.domain.MemberType.ADMIN";
            List<Object[]> objResultList = em.createQuery(queryString).getResultList();
            System.out.println(" objResultList 시작");
            for (Object[] objects : objResultList) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
