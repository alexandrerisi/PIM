package nl.zamro.pim.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "category")
@EqualsAndHashCode
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private short minOrderQuantity;
    private String unitOfMeasure;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Category category;
    private float price;
    private boolean isAvailable;
}
