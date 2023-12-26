package app.seven.chew.features.category

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/meals")
class CategoryController {
    fun fetchCategories(){}

    fun createCategory(){}

    fun editCategory(){}

    fun deleteCategory(){}
}