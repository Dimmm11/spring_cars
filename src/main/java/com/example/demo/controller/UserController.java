package com.example.demo.controller;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarStatus;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.service.CarService;
import com.example.demo.model.service.OrderService;
import com.example.demo.model.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     *                             cars                               *
     ******************************************************************/
    @GetMapping("/cars")
    @Transactional(readOnly = true)
    public String allCars() {
        return "redirect:/user/cars/page/1";
    }

    @GetMapping("/cars/page/{pageNo}")
    @Transactional(readOnly = true)
    public String findCarsPaginated(@PathVariable("pageNo") int pageNo,
                                    Model model){
        int pageSize=3;
        Page<Car> page = carService.findFreeCarsPaginated(pageNo, pageSize);
        List<Car> freeCars = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cars", freeCars);
        return "cars/allCars";
    }
    /******************************************************************
     *                             order                              *
     ******************************************************************/

    @GetMapping("/orders")
    @Transactional(readOnly = true)
    public String userOrders() {

        return "redirect:/user/orders/page/1";
    }
    @GetMapping("/orders/page/{pageNo}")
    @Transactional(readOnly = true)
    public String findOrdersPaginated(@PathVariable("pageNo")int pageNo,
                                      Principal principal,
                                      Model model){
        UserEntity user = userService.findByUsername(principal.getName());
        int pageSize=3;
        Page<Order> page = orderService.findAllByUserPaginated(user.getId() , pageNo, pageSize);
        List<Order> userOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orders", userOrders);
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
        orderService.carOrder(userId, id, driver, term, car.getPrice().multiply(term),
                LocalDateTime.now().withSecond(0));
        carService.orderCar(id);
        model.addAttribute("orders", orderService.findByUser_Id(userId));
        return "redirect:/user/orders";
    }

    @PostMapping("/cancel")
    @Transactional
    public String cancelOrder(@RequestParam("orderId") Long orderId,
                              @RequestParam("carId") Long carId) {
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
