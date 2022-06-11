package com.example.thymeleafonetomany.brand;

import com.example.thymeleafonetomany.category.Category;
import com.example.thymeleafonetomany.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.nio.file.Files;

@Controller
public class BrandController {
    @Autowired
    private BrandRepository brandRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/brands")
    public String listBrands(Model model)
    {
        List<Brand> listBrands = brandRepo.findAll();
        model.addAttribute("listBra", listBrands);
        return "brands";
    }

    @GetMapping("/brands/new")
    public String showBrandForm(Model model)
    {
        List<Category> listCategories = categoryRepo.findAll();
        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        model.addAttribute("listCat",listCategories);
        return "brand_form";
    }
    @PostMapping("/brands/save")
    public String saveBrand(Model model, @ModelAttribute(name = "brand") Brand brand,@RequestParam("fileImage") MultipartFile multipartFile)
            throws IOException
    {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        brand.setLogo(fileName);
        Brand savedBrand = brandRepo.save(brand);
        String uploadDir = "./brand-images/" + savedBrand.getId();

        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }
        try{
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            throw new IOException("Could not save upload file: " + fileName);
         }

        return "redirect:/brands";
    }
    @GetMapping("/brands/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model)
    {
        List<Category> listCategories = categoryRepo.findAll();
        Brand brand = brandRepo.findById(id).get();

        model.addAttribute("brand", brand);
        model.addAttribute("listCat",listCategories);

        return "brand_form";
    }
    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable("id") Integer id, Model model)
    {
        brandRepo.deleteById(id);
        return "redirect:/brands";
    }
}
