package nl.zamro.pim.domain;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@NoArgsConstructor
@Data
@ToString(exclude = "products")
@EqualsAndHashCode
public class Category {

    @Id
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.REMOVE) // lazy default
    private Collection<Product> products;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
