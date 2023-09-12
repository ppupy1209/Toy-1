package toyproject.toyshop.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Address;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.item.Book;
import toyproject.toyshop.domain.item.Item;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Test
    public void 상품주문() throws {
        // given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        // when

        //then
    }

    @Test
    public void 주문취소() throws {

    }

    @Test
    public void 상품주문_재고수량초과() throws {
        // given

        // when

        //then
    }

}