package org.matec.papalotedbmanager.classes;

import org.matec.papalotedbmanager.classes.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(path="/Papalote")
public class APIController {

    @Autowired
    private EmployeeRepostory emps;

    @Autowired
    private EmployeeCatRepostory empcats;

    @Autowired
    private SellRepostory sells;

    @Autowired
    private SellDetailRepostory selldetails;

    @Autowired
    private TableRepostory tables;

    @Autowired
    private OrderRepostory orders;

    @Autowired
    private OrderInfoRepostory infoRepostory;

    @Autowired
    private InventoryRepostory invs;

    @Autowired
    private ProductRepostory prods;

    @Autowired
    private ProductCatRepostory prodcats;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public APIController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping(path = "/addEmployeeCategory")
    public ResponseEntity<EmployeeCategory> addEmployeeCategory(@RequestBody EmployeeCategory employeeCategory) {
        EmployeeCategory category = empcats.save(employeeCategory);
        return ResponseEntity.ok(category);
    }

    @PostMapping(path = "/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee employee1 = emps.save(employee);
        return ResponseEntity.ok(employee1);
    }

    @PostMapping(path = "/newOrder")
    public ResponseEntity<Orders> newOrder(@RequestBody Orders orders1) {
        Orders order1 = orders.save(orders1);
        return ResponseEntity.ok(order1);
    }

    @PostMapping(path = "/addToOrder")
    public ResponseEntity<OrderInfo> newOrderInfo(@RequestBody OrderInfo orderInfo1) {
        OrderInfo oi = infoRepostory.save(orderInfo1);
        return ResponseEntity.ok(oi);
    }

    @PostMapping(path = "/newProduct")
    public ResponseEntity<Product> newProduct(@RequestBody Product product) {
        Product product1 = prods.save(product);
        Inventory inventory = new Inventory();
        inventory.setId_product(product.getId());
        inventory.setQuantity(1);
        inventory.setMin_stock(1);
        inventory.setMax_stock(1);
        inventory.setLast_update(LocalDate.now());

        invs.save(inventory);
        return ResponseEntity.ok(product1);
    }

    @PostMapping(path = "/addProductCategory")
    public ResponseEntity<ProductCategory> addProductCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory category = prodcats.save(productCategory);
        return ResponseEntity.ok(category);
    }

    @PostMapping(path = "/newTable")
    public ResponseEntity<TableEntity> newTable(@RequestBody TableEntity tableEntity) {
        TableEntity tableEntity1 = tables.save(tableEntity);
        return ResponseEntity.ok(tableEntity1);
    }

    @PostMapping(path = "/completeSell")
    public ResponseEntity<Sell> completeSell(@RequestParam Integer id) {
        Optional<Orders> order = orders.findById(id);
        if(order.isPresent()) {
            Orders or = order.get();
            Sell sell = new Sell();
            sell.setDate_order(LocalDateTime.now());
            sell.setId_employee(or.getEmployeeId());
            Sell res = sells.save(sell);

            ArrayList<OrderInfo> list = (ArrayList<OrderInfo>) infoRepostory.findAll();
            list.removeIf(p -> p.getOrderId() != id);
            double subtotal = 0;
            for(OrderInfo oi : list) {
                Optional<Product> p = prods.findById(oi.getProductId());
                if(p.isPresent()) {
                    Product p1 = p.get();
                    SellDetail sellDetail = new SellDetail();
                    sellDetail.setUnit_price(p1.getPrice());
                    sellDetail.setId_sell(res.getId());
                    sellDetail.setQuantity(oi.getQuantity());
                    subtotal += oi.getQuantity() * sellDetail.getUnit_price();
                    selldetails.save(sellDetail);
                    infoRepostory.delete(oi);
                }
            }
            sell.setSubtotal(subtotal);
            res = sells.save(sell);
            orders.deleteById(id);

            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/getEmployees")
    public @ResponseBody Iterable<Employee> getEmployees() {
        return emps.findAll();
    }

    @GetMapping(path = "/getEmployee")
    public @ResponseBody Employee getEmployeeById(@RequestParam Integer id) {
        var result = emps.findById(id);
        return result.orElseGet(Employee::new);
    }

    @GetMapping(path = "/getEmployeeCategories")
    public @ResponseBody Iterable<EmployeeCategory> getEmployeeCategories() {
        return empcats.findAll();
    }

    @GetMapping(path = "/ShowInventory")
    public @ResponseBody Iterable<Inventory> getInventory() {
        return invs.findAll();
    }

    @GetMapping(path = "/getOrders")
    public @ResponseBody Iterable<Orders> getOrders() {
        return orders.findAll();
    }

    @GetMapping(path="/getOrderInfoById")
    public @ResponseBody Iterable<OrderInfo> getOrderInfoById(@RequestParam Integer id) {
        ArrayList<OrderInfo> list = (ArrayList<OrderInfo>) infoRepostory.findAll();
        list.removeIf(p -> p.getOrderId() != id);
        System.out.println(list.size());
        return list;
    }

    @GetMapping(path = "/getProducts")
    public @ResponseBody Iterable<Product> getProducts() {
        return prods.findAll();
    }

    @GetMapping(path="/getProductsById")
    public @ResponseBody Iterable<Product> getProductsById(@RequestParam Integer id) {
        ArrayList<Product> list = (ArrayList<Product>) prods.findAll();
        list.removeIf(p -> p.getCategory() != id);
        return list;
    }

    @GetMapping(path = "/getProductCategories")
    public @ResponseBody Iterable<ProductCategory> getProductCategories() {
        return prodcats.findAll();
    }

    @GetMapping(path = "/getSells")
    public @ResponseBody Iterable<Sell> getSells() {
        System.out.println("User soliciting sells");
        return sells.findAll();
    }

    @GetMapping(path = "/getSellsDetail")
    public @ResponseBody Iterable<SellDetail> getSellsDetail(){
        return selldetails.findAll();
    }

    @GetMapping(path = "/getTables")
    public @ResponseBody Iterable<TableEntity> getTables() {
        System.out.println("User soliciting tables");
        return tables.findAll();
    }


    @GetMapping(path = "/getProduct")
    public @ResponseBody Product getProductById(@RequestParam Integer id) {
        var result = prods.findById(id);
        return result.orElseGet(Product::new);
    }

    @GetMapping(path = "/getProductCat")
    public @ResponseBody ProductCategory getProductCategoryById(@RequestParam Integer id) {
        var result = prodcats.findById(id);
        return result.orElseGet(ProductCategory::new);
    }

    @DeleteMapping(path = "/deleteEmplyee")
    public @ResponseBody ResponseEntity<String> deleteEmployee(@RequestParam Integer id) {
        emps.deleteById(id);
        if(emps.count()==0) {
            jdbcTemplate.execute("ALTER TABLE employee AUTO_INCREMENT = 1;");
            jdbcTemplate.execute("update employee_seq set next_val = 1;");
        }
        return ResponseEntity.ok("Employee deleted");
    }

    @DeleteMapping(path = "/deleteEmpCategory")
    public @ResponseBody ResponseEntity<String> deleteEmployeeCategory(@RequestParam Integer id) {
        empcats.deleteById(id);
        if(empcats.count()==0) {
            jdbcTemplate.execute("ALTER TABLE employee_category AUTO_INCREMENT = 1;");
            jdbcTemplate.execute("update employee_category_seq set next_val = 1;");
        }
        return ResponseEntity.ok("Employee category deleted");
    }

    @DeleteMapping(path = "/deleteProduct")
    public @ResponseBody ResponseEntity<String> deleteProduct(@RequestParam Integer id) {
        for(Inventory inv: invs.findAll()){
            if(inv.getId_product() == id){
                invs.deleteById(inv.getId());
            }
        }
        prods.deleteById(id);
        if(prods.count()==0) {
            jdbcTemplate.execute("ALTER TABLE product AUTO_INCREMENT = 1;");
            jdbcTemplate.execute("update product_seq set next_val = 1;");
        }
        return ResponseEntity.ok("Product deleted");
    }
    @DeleteMapping(path = "/deleteProductCategory")
    public @ResponseBody ResponseEntity<String> deleteProductCategory(@RequestParam Integer id) {
        prodcats.deleteById(id);
        if(prodcats.count()==0) {
            jdbcTemplate.execute("ALTER TABLE product_category AUTO_INCREMENT = 1;");
            jdbcTemplate.execute("update product_category_seq set next_val = 1;");
        }
        return ResponseEntity.ok("Product deleted");
    }

    @DeleteMapping(path = "/deleteOrder")
    public @ResponseBody ResponseEntity<String> deleteOrder(@RequestParam Integer id) {
        orders.deleteById(id);
        for(OrderInfo info: infoRepostory.findAll()){
            if(info.getOrderId() == id){
                infoRepostory.deleteById(info.getId());
            }
        }
        return ResponseEntity.ok("Order deleted");
    }
    @DeleteMapping(path = "/deleteOrderInfo")
    public @ResponseBody ResponseEntity<String> deleteOrderInfo(@RequestParam Integer id) {
        infoRepostory.deleteById(id);
        return ResponseEntity.ok("Item deleted");
    }

    @DeleteMapping(path = "/deleteTable")
    public @ResponseBody ResponseEntity<String> deleteTable(@RequestParam Integer id) {
        tables.deleteById(id);
        if(tables.count()==0) {
            jdbcTemplate.execute("ALTER TABLE table_entity AUTO_INCREMENT = 1;");
            jdbcTemplate.execute("update table_entity_seq set next_val = 1;");
        }
        return ResponseEntity.ok("Table deleted");
    }
    @PutMapping(path = "/updateEmployee")
    public @ResponseBody ResponseEntity<Employee> updateEmployee(@RequestParam Integer id, @RequestBody Employee employee) {
        return emps.findById(id).map(employee1 -> {
            employee1.setName(employee.getName());
            employee1.setEmail(employee.getEmail());
            employee1.setBirthday(employee.getBirthday());
            employee1.setPassword(employee.getPassword());
            employee1.setCategory(employee.getCategory());
            return emps.save(employee1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

    @PutMapping(path = "/updateProduct")
    public @ResponseBody ResponseEntity<Product> updateProduct(@RequestParam Integer id, @RequestBody Product product) {
        return prods.findById(id).map(product1 -> {
            product1.setName(product.getName());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setCategory(product.getCategory());
            return prods.save(product1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(path ="/updateOrder")
    public @ResponseBody ResponseEntity<Orders> updateOrder(@RequestParam Integer id, @RequestBody Orders order) {
        return orders.findById(id).map(order1 -> {
            order1.setEmployeeId(order.getEmployeeId());
            order1.setTableId(order.getTableId());
            order1.setOrderStatus(order.getOrderStatus());
            return orders.save(order1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(path ="/updateTable")
    public @ResponseBody ResponseEntity<TableEntity> updateTable(@RequestParam Integer id, @RequestBody TableEntity table) {
        return tables.findById(id).map(table1->{
            table1.setCapacity(table.getCapacity());
            table1.setState(table.getState());
            return tables.save(table1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/updateEmployeeCat")
    public @ResponseBody ResponseEntity<EmployeeCategory> updateEmployeeCategory(@RequestParam Integer id, @RequestBody EmployeeCategory employeeCategory) {
        return empcats.findById(id).map(employeeCategory1 -> {
            employeeCategory1.setName(employeeCategory.getName());
            employeeCategory1.setAdmin(employeeCategory.isAdmin());
            return empcats.save(employeeCategory1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/updateProductCat")
    public @ResponseBody ResponseEntity<ProductCategory> updateProductCategory(@RequestParam Integer id, @RequestBody ProductCategory productCategory) {
        return prodcats.findById(id).map(productCategory1 -> {
            productCategory1.setName(productCategory.getName());
            productCategory1.setDescription(productCategory.getDescription());
            return prodcats.save(productCategory1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/updateInventory")
    public @ResponseBody ResponseEntity<Inventory> updateInventory(@RequestParam Integer id, @RequestBody Inventory inventory) {
        return invs.findById(id).map(inventory1 -> {
            inventory1.setQuantity(inventory.getQuantity());
            inventory1.setLast_update(inventory.getLast_update());
            inventory1.setMax_stock(inventory.getMax_stock());
            inventory1.setMin_stock(inventory.getMin_stock());
            inventory1.setId_product(inventory.getId_product());
            return invs.save(inventory1);
        }).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
