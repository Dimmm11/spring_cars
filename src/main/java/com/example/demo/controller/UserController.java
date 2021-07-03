package com.example.demo.controller;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.order.OrderStatus;
import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.service.CarService;
import com.example.demo.model.service.OrderService;
import com.example.demo.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private OrderService orderService;

    /******************************************************************
     *                             order                              *
     ******************************************************************/
    @GetMapping("/cars")
    @Transactional(readOnly = true)
    public String allCars(Model model) {
        model.addAttribute("cars", carService.findFreeCars());
        return "cars/allCars";
    }

    @GetMapping("/orders")
    @Transactional(readOnly = true)
    public String userOrders(Model model,
                             Principal principal){
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("orders", orderService.findAllByUserAndStatus(OrderStatus.FINISHED.name(), user.getId()));
        return "user/order";
    }

    @PostMapping("/make_order/{id}")
    @Transactional
    public String carOrder(@PathVariable("id") Long id,
                           Principal principal,
                           Model model,
                           @RequestParam("driver") Boolean driver,
                           @RequestParam("term") BigDecimal term) {
        UserEntity userEntity = userService.findByUsername(principal.getName());
        Long userId = userEntity.getId();

        Car car = carService.findCarById(id);
        orderService.carOrder(userId, id, driver, term, car.getPrice().multiply(term), LocalDateTime.now().withSecond(0));
        carService.orderCar(id);
        model.addAttribute("orders", orderService.findByUser_Id(userId));
        return "redirect:/user/orders";
    }

    @PostMapping("/cancel")
    @Transactional
    public String cancelOrder(@RequestParam("orderId")Long orderId,
                              @RequestParam("carId")Long carId){
        carService.setCarFree(carId);
        orderService.deleteById(orderId);
        return "redirect:/user/orders";
    }

    /******************************************************************
     *                           registration                         *
     ******************************************************************/
    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserEntity user) {
        return "registration";
    }

    @PostMapping("/registration")
    @Transactional
    public String addUser(@ModelAttribute("user") @Valid UserEntity user,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        UserEntity userFromDB = userService.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("message", "such user exists");
            return "registration";
        }
        user.setActive(true);
        user.setRole(Role.USER);
        userService.save(user);
        return "redirect:/login";
    }
}
