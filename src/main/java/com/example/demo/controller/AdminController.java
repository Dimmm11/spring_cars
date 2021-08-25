package com.example.demo.controller;

import com.example.demo.exception.NoCarsFoundException;
import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarStatus;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderStatus;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.service.CarService;
import com.example.demo.model.service.OrderService;
import com.example.demo.model.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/panel")
    public String panel() {
        return "admin/panel";
    }

    /******************************************************************
     *                       Exception handling                       *
     ******************************************************************/
    @ExceptionHandler({Exception.class})
    public String handleError(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return "error";
    }

    @ExceptionHandler({NoCarsFoundException.class})
    public String noCarsExceptionHandler(NoCarsFoundException ex){
        logger.error(ex.getMessage());
        return "error/noCarsFound";
    }

    /******************************************************************
     *                           user methods                         *
     ******************************************************************/

    @GetMapping("/users")
    public String allUsers() {
        return "redirect:/admin/users/page/1";
    }

    @GetMapping("/users/page/{pageNo}")
    public String allUsers(@PathVariable("pageNo") int pageNo,
                           Model model) {
        int pageSize = 3;
        Page<UserEntity> page = userService.findAll(pageNo, pageSize);
        List<UserEntity> users = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        return "admin/userList";
    }

    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") @Valid UserEntity user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/updateUser";
        }
        logger.info(String.format("update user: %s", user));
        userService.updateUser(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getId());
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
       userService.deleteById(id);
        logger.info(String.format("delete user: %d", id));
        return "redirect:/admin/users";
    }

    /******************************************************************
     *                            car methods                         *
     ******************************************************************/

    @GetMapping("/cars/all")
    public String allCars() {
        return "redirect:/admin/cars/page/1";
    }


    @GetMapping("/cars/page/{pageNo}")
    public String allCars(@PathVariable("pageNo") int pageNo,
                          Model model) {
        int pageSize=3;
        Page<Car> page = carService.findAllPaginated(pageNo, pageSize);
        List<Car> cars = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cars", cars);
        return "admin/cars";
    }

    @GetMapping("/cars/add")
    public String addCar(@ModelAttribute("car") Car car) {
        return "admin/addCar";
    }

    @PostMapping("/cars/add")
    public String addCar(@ModelAttribute("car") @Valid Car car,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addCar";
        }
        car.setCar_status(CarStatus.FREE);
        carService.addCar(car);
        logger.info(String.format("add car: %s", car));
        return "redirect:/admin/cars/all";
    }

    @GetMapping("/cars/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carService.findCarById(id));
        return "admin/updateCar";
    }

    @PostMapping("/cars/update")
    public String updateCar(@ModelAttribute("car") @Valid Car car,
                            BindingResult bindingResult,
                            @RequestParam("car_status") CarStatus carStatus) {
        car.setCar_status(carStatus);
        if (bindingResult.hasErrors()) {
            return "admin/updateCar";
        }
        logger.info(String.format("update car: %s", car));
        carService.updateCar(car.getMarque(),
                car.getModel(),
                car.getComfort(),
                car.getPrice(),
                car.getId());
        return "redirect:/admin/cars/all";
    }

    @PostMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable("id") long id) {
      int a =  carService.deleteCar(id);
        logger.info(String.format("delete car: %d", id));
        return "redirect:/admin/cars/all";
    }

    /******************************************************************
     *                          order methods                         *
     ******************************************************************/
    @GetMapping("/orders")
    public String orders() {
        return "redirect:/admin/orders/page/1";
    }

    @GetMapping("/orders/page/{pageNo}")
    public String orders(@PathVariable("pageNo") int pageNo,
                         Model model) {
        int pageSize = 3;
        Page<Order> page = orderService.findAllPaginated(pageNo, pageSize);
        List<Order> orders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/order/status")
    public String setOrderStatus(@RequestParam("order_status") String order_status,
                                 @RequestParam("order_id") Long order_id) {
        Order order = orderService.getById(order_id);
        orderService.setOrderStatus(order_status, order_id);
        if (order_status.equals(OrderStatus.REJECTED.name())) {
            carService.setCarFree(order.getCar().getId());
            logger.info(String.format("set Car FREE: %d", order.getCar().getId()));
        }
        logger.info(String.format("set status: %s, order: %d", order_status, order_id));
        return "redirect:/admin/orders";
    }

    @PostMapping("/order/finish")
    public String finishOrder(@RequestParam("orderId") Long orderId,
                              @RequestParam("carId") Long carId) {
        orderService.copyAndFinishOrder(orderId);
        carService.setCarFree(carId);
        logger.info(String.format("copied order to Finished: %d, set car FREE: %d", orderId, carId));
        return "redirect:/admin/orders";
    }
}
