package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.IdRequest;
import NoiThatGroup.Home.Dto.request.ItemRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enity.Item;
import NoiThatGroup.Home.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;


    @GetMapping
    ApiResponses<List<Item>> getItem(){
        return ApiResponses.<List<Item>>builder()
                .result(itemService.getItem())
                .build();
    }

    @GetMapping("/find")
    ApiResponses<Item> findItemById(@RequestParam("id") String id){
        return ApiResponses.<Item>builder()
                .result(itemService.findItemById(id))
                .build();

    }

    @PostMapping
    ApiResponses<Item> createItem(@RequestParam("name") String name,
                                           @RequestParam("img") MultipartFile img,
                                           @RequestParam("detail") String detail,
                                           @RequestParam("material") String material,
                                           @RequestParam("weight") double weight,
                                           @RequestParam("status") String status,
                                           @RequestParam("inventory") int inventory,
                                           @RequestParam("price") int price,
                                           @RequestParam("category") String category) throws IOException {
        byte[] imgByte = img.getBytes();
        Item item = itemService.createItem(new ItemRequest().builder()
                .name(name)
                .img(imgByte)
                .detail(detail)
                .material(material)
                .weight(weight)
                .status(status)
                .inventory(inventory)
                .price(price)
                .category(category)
                .build());

        return ApiResponses.<Item>builder()
                .result(item)
                .build();
    }

    @PutMapping
    ApiResponses<Item> updateItem(@RequestParam("id") String id,
                                     @RequestParam("name") String name,
                                  @RequestParam("img") MultipartFile img,
                                  @RequestParam("detail") String detail,
                                  @RequestParam("material") String material,
                                  @RequestParam("weight") double weight,
                                  @RequestParam("status") String status,
                                  @RequestParam("inventory") int inventory,
                                  @RequestParam("price") int price,
                                  @RequestParam("category") String category) throws IOException {
        byte[] imgByte = img.getBytes();
        Item item = itemService.updateItem(new ItemRequest().builder()
                        .id(id)
                .name(name)
                .img(imgByte)
                .detail(detail)
                .material(material)
                .weight(weight)
                .status(status)
                .inventory(inventory)
                .price(price)
                .category(category)
                .build());

        return ApiResponses.<Item>builder()
                .result(item)
                .build();
    }
    @DeleteMapping
    ApiResponses<Boolean> deletedItem(@RequestBody IdRequest idRequest){
        itemService.deletedItem(idRequest.getId());
        return ApiResponses.<Boolean>builder()
                .result(true)
                .build();

    }


}
