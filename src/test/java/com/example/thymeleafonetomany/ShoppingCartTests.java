package com.example.thymeleafonetomany;

import com.example.thymeleafonetomany.product.Product;
import com.example.thymeleafonetomany.shoppingcart.CartItem;
import com.example.thymeleafonetomany.shoppingcart.CartItemRepository;
import com.example.thymeleafonetomany.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SuppressWarnings("ALL")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTests {
    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddItemFromDatabase()
    {
        Product product = entityManager.find(Product.class,1);
        User user  = entityManager.find(User.class,1);

        CartItem item = new CartItem(1, product, user);

        cartRepo.save(item);
    }
    @Test
    public void testAddItemById()
    {
        Product product = new Product(2);
        User user = new User(5);
        CartItem item = new CartItem(2, product, user);

        cartRepo.save(item);
    }
    @Test
    public  void testAddMultipleItems()
    {
        User user = new User(2);

        Product product1 = new Product(1);
        Product product2 = new Product(2);
        Product product3 = new Product(3);
        Product product4 = new Product(4);

        CartItem item1 = new CartItem(1, product1,user);
        CartItem item2 = new CartItem(2, product2,user);
        CartItem item3 = new CartItem(3, product3,user);
        CartItem item4 = new CartItem(4, product4,user);

        cartRepo.saveAll(List.of(item1,item2,item3,item4));
    }
    @Test
    public void testListItems()
    {
        List<CartItem> listItems = cartRepo.findAll();
        listItems.forEach(System.out::println);
    }
    @Test
    public void testUploadItem()
    {
        CartItem item = cartRepo.findById(1).get();

        item.setProduct(new Product(3));
        item.setUser(new User(3));
        item.setQuantity(10);

        cartRepo.save(item);
    }
}
