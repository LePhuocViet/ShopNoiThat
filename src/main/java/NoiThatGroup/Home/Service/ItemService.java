package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.ItemRequest;
import NoiThatGroup.Home.Enity.Item;
import org.hibernate.sql.Update;

import java.util.List;

public interface ItemService {

Item createItem(ItemRequest itemRequest);

List<Item> getItem();

Item findItemById(String id);

boolean deletedItem(String id);

}
