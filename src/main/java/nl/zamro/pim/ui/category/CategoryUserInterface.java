package nl.zamro.pim.ui.category;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI(path = "/categories")
public class CategoryUserInterface extends UI {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new CategoryLayout());
        //setContent(new ProductLayout());
    }
}