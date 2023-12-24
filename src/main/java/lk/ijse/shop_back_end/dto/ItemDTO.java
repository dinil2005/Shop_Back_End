package lk.ijse.shop_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemDTO {
    private String itemId;
    private String itemName;
    private Double itemPrice;
    private String itemDesc;
}
