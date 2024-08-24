package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enity.Category;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @PostMapping
    ApiResponses<String> createCategory(@RequestBody String category){
        ApiResponses.<String>builder()
                .result("Category: " )
                .build()
    }
}
