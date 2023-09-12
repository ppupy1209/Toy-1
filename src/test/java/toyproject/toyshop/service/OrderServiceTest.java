package toyproject.toyshop.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.toyshop.domain.Address;
import toyproject.toyshop.domain.Member;
import toyproject.toyshop.domain.Order;
import toyproject.toyshop.domain.OrderStatus;
import toyproject.toyshop.domain.item.Book;
import toyproject.toyshop.exception.NotEnoughStockException;
import toyproject.toyshop.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 ORDER" );
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수 확인");
        assertEquals(10000 * orderCount,getOrder.getTotalPrice(),"주문 가격 확인");
        assertEquals(10-orderCount,book.getStockQuantity(),"재고가 주는지 확인");
    }



    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order order = orderRepository.findOne(orderId);
        // when

        orderService.cancelOrder(orderId);

        // then
        assertEquals(OrderStatus.CANCEL,order.getStatus(),"주문 취소 상태 확인");
        assertEquals(10,book.getStockQuantity(),"재고 확인");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;
        // when
        try {
            orderService.order(member.getId(), book.getId(),orderCount);
        } catch (NotEnoughStockException e) {
            return;
        }

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }
}