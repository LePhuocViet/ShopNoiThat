package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Enity.Category;

import java.util.List;

public interface CategoryService {

    String createCategory(String name);

    List<Category> getCategory();

    boolean deletedCategory(String name);



}
