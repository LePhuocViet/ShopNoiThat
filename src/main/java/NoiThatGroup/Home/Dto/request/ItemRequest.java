package NoiThatGroup.Home.Dto.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {
    String id;
    private String name;

    @Lob
    private byte[] img;

    private String detail;

    private String material;

    private double weight;

    private String status;

    private int inventory;

    private int price;

    private String category;
}
