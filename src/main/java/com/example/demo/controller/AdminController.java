package com.example.demo.controller;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderStatus;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {
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
     *                           user methods                         *
     ******************************************************************/

    @GetMapping("/users")
    @Transactional(readOnly = true)
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/userList";
    }

    @GetMapping("/users/update/{id}")
    @Transactional(readOnly = true)
    public String updateUser(@PathVariable("id") long id,
                             Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/users/update")
    @Transactional
    public String userEdit(@ModelAttribute("user") @Valid UserEntity user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/updateUser";
        }
        userService.updateUser(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getId());
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    @Transactional
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    /******************************************************************
     *                            car methods                         *
     ******************************************************************/

    @GetMapping("/cars/all")
    @Transactional(readOnly = true)
    public String allCars(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/cars";
    }

    @GetMapping("/cars/add")
    public String addCar(@ModelAttribute("car") Car car) {
        return "admin/addCar";
    }

    @PostMapping("/cars/add")
    @Transactional
    public String addCar(@ModelAttribute("car") @Valid Car car,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addCar";
        }
        carService.addCar(car);
        return "redirect:/admin/cars/all";
    }

    @GetMapping("/cars/update/{id}")
    @Transactional(readOnly = true)
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carService.findCarById(id));
        return "admin/updateCar";
    }

    @PostMapping("/cars/update")
    @Transactional
    public String updateCar(@ModelAttribute("car") @Valid Car car,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/updateCar";
        }
        carService.updateCar(car.getMarque(),
                car.getModel(),
                car.getComfort(),
                car.getPrice(),
                car.getId());
        return "redirect:/admin/cars/all";
    }

    @PostMapping("/cars/delete/{id}")
    @Transactional
    public String deleteCar(@PathVariable("id") long id) {
        carService.deleteCar(id);
        return "redirect:/admin/cars/all";
    }

    /******************************************************************
     *                          order methods                         *
     ******************************************************************/
    @GetMapping("/orders")
    @Transactional(readOnly = true)
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "manager/orders";
    }

    @PostMapping("/order/status")
    @Transactional
    public String setOrderStatus(@RequestParam("order_status") String order_status,
                                 @RequestParam("order_id") Long order_id) {
        Order order = orderService.getById(order_id);
        orderService.setOrderStatus(order_status, order_id);
        if (order_status.equals(OrderStatus.FINISHED.name()) || order_status.equals(OrderStatus.REJECTED.name())) {
            carService.setCarFree(order.getCar().getId());
        }


        return "redirect:/admin/orders";
    }
}
