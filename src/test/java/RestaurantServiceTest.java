import org.junit.jupiter.api.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    List<Item> order = new ArrayList<Item>();

    @BeforeEach
    public void restaurantCreation(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        assertEquals(restaurant.getName(), service.findRestaurantByName("Amelie's cafe").getName());
    }
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("New cafe"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ORDER VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_items_from_menu_should_increase_the_total_order_value() {
        order = restaurant.getMenu();
        assertEquals(388,service.totalOrderValue(order));
    }
    @Test
    public void removing_items_from_menu_should_decrease_the_total_order_value() {
        order = restaurant.getMenu();
        int total = service.totalOrderValue(order);
        int afterTotal = order.get(1).getPrice();
        order.remove(1);
        assertEquals(total-afterTotal,service.totalOrderValue(order));
    }
    //<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }
    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }
    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}
