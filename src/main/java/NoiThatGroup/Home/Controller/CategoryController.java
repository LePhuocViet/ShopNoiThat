package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.CategoryRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enity.Category;
import NoiThatGroup.Home.Enity.Item;
import NoiThatGroup.Home.Repository.CategoryRepository;
import NoiThatGroup.Home.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping
    ApiResponses<List<Category>> getCategory(){
        return  ApiResponses.<List<Category>>builder()
                .result(categoryService.getCategory())
                .build();
    }

    @GetMapping("/find")
    ApiResponses<List<Item>> getCategoryByName(@RequestParam("name") String name){
        return ApiResponses.<List<Item>>builder()
                .result(categoryService.getItemByCategory(name))
                .build();
    }
    @DeleteMapping
    ApiResponses<Boolean> deletedCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.deletedCategory(categoryRequest.getName());
        return  ApiResponses.<Boolean>builder()
                .result(true)
                .build();
    }
    @PostMapping
    ApiResponses<String> createCategory(@RequestBody CategoryRequest categoryRequest){

        return  ApiResponses.<String>builder()
                .result("Category: "  + categoryService.createCategory(categoryRequest.getName()))
                .build();
    }
}
