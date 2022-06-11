package com.example.thymeleafonetomany.product;

import com.example.thymeleafonetomany.category.Category;
import com.example.thymeleafonetomany.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/products")
    public String listProducts(Model model)
    {
        List<Product> listProducts = productRepo.findAll();
        model.addAttribute("listPro", listProducts );
        return "products";
    }
    @GetMapping("/products/new")
    public String showProductForm(Model model)
    {
        Product product = new Product();
        List<Category> listCategories = categoryRepo.findAll();

        model.addAttribute("pro", product);
        model.addAttribute("listCategories", listCategories);
        return "product_form";
    }
    @PostMapping("/products/save")
    public String saveProduct(Model model, Product product, HttpServletRequest request )
    {
        String[] detailNames = request.getParameterValues("detailName");
        String[] detailValues = request.getParameterValues("detailValue");

        for(int i=0;i<detailNames.length;i++)
        {
            product.addDetails(detailNames[i],detailValues[i]);
        }

        productRepo.save(product);
        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Integer id, Model model)
    {
        Product product = productRepo.findById(id).get();
        List<Category> listCategories = categoryRepo.findAll();

        model.addAttribute("pro", product);
        model.addAttribute("listCategories", listCategories);

        return "product_form";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model)
    {
        Product product = new Product();
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}
