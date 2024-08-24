package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.CategoryRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enity.Category;
import NoiThatGroup.Home.Repository.CategoryRepository;
import NoiThatGroup.Home.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    ApiResponses<String> createCategory(@RequestBody CategoryRequest categoryRequest){

      return  ApiResponses.<String>builder()
                .result("Category: "  + categoryService.createCategory(categoryRequest.getName()))
                .build();
    }
}
