package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Enity.Category;
import NoiThatGroup.Home.Enity.Item;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.CategoryRepository;
import NoiThatGroup.Home.Repository.ItemRepository;
import NoiThatGroup.Home.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryImplement implements CategoryService {
    ItemRepository itemRepository;
    CategoryRepository categoryRepository;
    @Override
    public String createCategory(String name) {
        if(name.equals(categoryRepository.findById(name)))
            throw new AppException(ErrorCode.CATEGORY_IS_EXIST);
        categoryRepository.save(new Category(name));
        return name;
    }

    @Override
    public List<Category> getCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    @Override
    public boolean deletedCategory(String name) {
        if (!categoryRepository.existsById(name))
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        categoryRepository.delete(categoryRepository.findById(name).get());
        return true;
    }

    @Override
    public List<Item> getItemByCategory(String name) {
        List<Item> items = itemRepository.findItemByCategories_Name(name);
        if (items.isEmpty()) throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);


        return items;
    }
}
