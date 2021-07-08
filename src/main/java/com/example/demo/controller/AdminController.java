package com.example.demo.controller;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarStatus;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderStatus;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.service.CarService;
import com.example.demo.model.service.OrderService;
import com.example.demo.model.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public String allUsers() {
        return "redirect:/admin/users/page/1";
    }

    @GetMapping("/users/page/{pageNo}")
    @Transactional(readOnly = true)
    public String allUsers(@PathVariable("pageNo")int pageNo,
                           Model model){
        int pageSize=3;
        Page<UserEntity> page = userService.findAll(pageNo, pageSize);
        List<UserEntity> users = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
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
    public String allCars() {
        return "redirect:/admin/cars/page/1";
    }

    @GetMapping("/cars/page/{pageNo}")
    @Transactional(readOnly = true)
    public String allCars(@PathVariable("pageNo")int pageNo,
                          Model model){
        ///////////////////////// Hibernate test /////////////////////////////
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession();){
            // hibernate pagination
            int pageSize = 3;
            Query rowCountQuery = session.createQuery("SELECT count (c.id) FROM Car c");
            Long totalItems =(Long) rowCountQuery.uniqueResult();
            int totalPages = (int) (Math.ceil(totalItems/pageSize));
            Query selectQuery = session.createQuery("FROM Car");
            selectQuery.setFirstResult((pageNo-1)*pageSize);
            selectQuery.setMaxResults(pageSize);
            List<Car> cars = selectQuery.list(); // Do we need to cast (List<Car>) ???
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("cars", cars);
        }

        //////////////////////////////////////////////////////////////////////////////
//        int pageSize=3;
//        Page<Car> page = carService.findAllPaginated(pageNo, pageSize);
//        List<Car> cars = page.getContent();
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("cars", cars);
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
        car.setCar_status(CarStatus.FREE);
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
    public String orders() {
        return "redirect:/admin/orders/page/1";
    }
    @GetMapping("/orders/page/{pageNo}")
    @Transactional(readOnly = true)
    public String orders(@PathVariable("pageNo")int pageNo,
                         Model model){
        int pageSize=3;
        Page<Order> page = orderService.findAllPaginated(pageNo, pageSize);
        List<Order> orders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/order/status")
    @Transactional
    public String setOrderStatus(@RequestParam("order_status") String order_status,
                                 @RequestParam("order_id") Long order_id) {
        Order order = orderService.getById(order_id);
        orderService.setOrderStatus(order_status, order_id);
        if (order_status.equals(OrderStatus.REJECTED.name())) {
            carService.setCarFree(order.getCar().getId());
        }
        return "redirect:/admin/orders";
    }

    @PostMapping("/order/finish")
    @Transactional
    public String finishOrder(@RequestParam("orderId") Long orderId,
                              @RequestParam("carId") Long carId) {
        orderService.copyAndFinishOrder(orderId);
        carService.setCarFree(carId);
        return "redirect:/admin/orders";
    }
}
