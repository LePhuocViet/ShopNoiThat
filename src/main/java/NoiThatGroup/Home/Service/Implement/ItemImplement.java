package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.ItemRequest;
import NoiThatGroup.Home.Enity.Category;
import NoiThatGroup.Home.Enity.Item;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.CategoryRepository;
import NoiThatGroup.Home.Repository.ItemRepository;
import NoiThatGroup.Home.Service.ItemService;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ItemImplement implements ItemService {

    ItemRepository itemRepository;
    CategoryRepository categoryRepository;

    @Override
    public Item createItem(ItemRequest itemRequest) {
        Set<Category> categories = new HashSet<>();
        Optional<Category> category = categoryRepository.findById(itemRequest.getCategory());
        if (category.isEmpty()) throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        categories.add(category.get());
        Item item = Item.builder()
                .name(itemRequest.getName())
                .img(itemRequest.getImg())
                .detail(itemRequest.getDetail())
                .material(itemRequest.getMaterial())
                .weight(itemRequest.getWeight())
                .status(itemRequest.getStatus())
                .inventory(itemRequest.getInventory())
                .price(itemRequest.getPrice())
                .categories(categories)
                .build();
        itemRepository.save(item);
        return item;
    }

    @Override
    public List<Item> getItem() {
        return itemRepository.findAll();
    }

    @Override
    public Item findItemById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        return item.get();
    }

    @Override
    public boolean deletedItem(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        itemRepository.deleteById(id);
        return true;
    }


}
