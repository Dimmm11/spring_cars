package com.example.demo.controller;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.hibernateLearn.service.HibernateCarService;
import com.example.demo.model.hibernateLearn.service.HibernateOrderService;
import com.example.demo.model.hibernateLearn.service.HibernateUserService;
import com.example.demo.model.service.CarService;
import com.example.demo.model.service.OrderService;
import com.example.demo.model.service.UserService;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private OrderService orderService;

    /**
     * testing Hibernate Service layer
     */
    @Autowired
    private HibernateCarService hibernateCarService;
    @Autowired
    private HibernateUserService hibernateUserService;
    @Autowired
    private HibernateOrderService hibernateOrderService;


    /******************************************************************
     *                       Exception handling                       *
     ******************************************************************/
    @ExceptionHandler({Exception.class})
    public String handleError(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return "error";
    }

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
                                    Model model) {
        // hibernate pagination
        int pageSize = 3;
        Long totalRows = (long) hibernateCarService.findAll().size();
        int totalPages = (int) (Math.ceil((double) totalRows / pageSize));
        List<Car> freeCars = hibernateCarService.findAll((pageNo - 1) * pageSize, pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalRows);
        model.addAttribute("cars", freeCars);
        // jpa pagination
//        int pageSize = 3;
//        Page<Car> page = carService.findFreeCarsPaginated(pageNo, pageSize);
//        List<Car> freeCars = page.getContent();
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("cars", freeCars);
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
    public String findOrdersPaginated(@PathVariable("pageNo") int pageNo,
                                      Model model,
                                      Principal principal
    ) {

        UserEntity user = hibernateUserService.findByUsername(principal.getName());
        int pageSize = 3;
        Long totalRows = (long) (hibernateOrderService.findByUserId(user.getId()).size());
        int totalPages = (int) (Math.ceil((double) totalRows / pageSize));
        List<Order> userOrders = hibernateOrderService.findByUserId(user.getId(), (pageNo - 1) * pageSize, pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalRows);
        model.addAttribute("orders", userOrders);
//        Page<Order> page = orderService.findAllByUserPaginated(user.getId(), pageNo, pageSize);
//        List<Order> userOrders = page.getContent();
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("orders", userOrders);
        return "user/order";
    }

    @PostMapping("/make_order/{id}")
    @Transactional
    public String carOrder(@PathVariable("id") Long id,
                           Principal principal,
                           Model model,
                           @RequestParam("driver") Boolean driver,
                           @RequestParam("term") BigDecimal term) {
        UserEntity userEntity = hibernateUserService.findByUsername(principal.getName());
        Long userId = userEntity.getId();
        Car car = hibernateCarService.findById(id);
        int orderId = hibernateOrderService.makeOrder(userId, id, driver, term, car.getPrice().multiply(term),
                LocalDateTime.now().withSecond(0));
        hibernateCarService.orderCar(id);
        // last inserted row in multithreading ???
        logger.info(MessageFormat.format("user: {0} ordered car: {1} order id: {2}",
                userEntity.getId(), car.getId(), orderId));
        model.addAttribute("orders", hibernateOrderService.findByUserId(userId));
        return "redirect:/user/orders";
    }

    @PostMapping("/cancel")
    @Transactional
    public String cancelOrder(@RequestParam("orderId") Long orderId,
                              @RequestParam("carId") Long carId) {
        hibernateOrderService.cancelOrder(orderId, carId);
        logger.info(MessageFormat.format("order canceled:{0}, car set FREE:{1}", orderId, carId));
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
        UserEntity userFromDB = hibernateUserService.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("message", "such user exists");
            return "registration";
        }
        user.setActive(true);
        user.setRole(Role.USER);
        hibernateUserService.save(user);
        logger.info("registered user: " + user);
        return "redirect:/login";
    }
}
