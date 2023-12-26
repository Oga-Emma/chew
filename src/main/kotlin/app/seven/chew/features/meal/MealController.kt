package app.seven.chew.features.meal

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/meals")
class MealController {

    //Admin Endpoints
    fun createMeal(){}

    fun editMeal(){}

    fun deleteMeal(){}

    // General endpoints
    fun queryMeals(){}

    fun getMeal(){}
}