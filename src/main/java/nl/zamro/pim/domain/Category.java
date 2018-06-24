package nl.zamro.pim.domain;

import java.util.Collection;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "products")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
