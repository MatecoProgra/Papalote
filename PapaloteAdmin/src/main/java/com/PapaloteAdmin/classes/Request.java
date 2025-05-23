package com.PapaloteAdmin.classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;


public class Request {
    static final String path = "http://localhost:8080/Papalote";

    private static void tryRequestAdd(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exitoso");
                alert.setHeaderText(null);
                alert.setContentText("Agregado con exito");
                alert.showAndWait();
            }
            else
                System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void addUser(User user){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path+"/addEmployee"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(user.toString()))
                .build();
        tryRequestAdd(request);
    }

    public static void addUserCategory(UserCategory category){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path+"/addEmployeeCategory"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(category.toString()))
                .build();
        tryRequestAdd(request);
    }

    public static void completeSell(int orderId){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/completeSell?id=" + orderId))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        tryRequestAdd(request);
    }

    public static void addOrder(Order order){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/newOrder"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(order.toString()))
                .build();
        tryRequestAdd(request);
    }
    public static void addToOrder(OrderInfo orderInfo){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/addToOrder"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(orderInfo.toString()))
                .build();
        tryRequestAdd(request);
    }
    public static void addProduct(Product product){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/newProduct"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(product.toString()))
                .build();
        tryRequestAdd(request);
    }
    public static void addProductCategory(ProductCategory productCategory){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/addProductCategory"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(productCategory.toString()))
                .build();
        tryRequestAdd(request);
    }
    public static void addTable(Tables table){
        HttpRequest request = HttpRequest.newBuilder(URI.create(path + "/newTable"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(table.toString()))
                .build();
        tryRequestAdd(request);
    }

    public static ObservableList<User> getUsers(){
        String apiUrl = path+"/getEmployees";
        ObservableList<User> users = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<User> temp = mapper.readValue(response.body(), new TypeReference<List<User>>() {});
            users.addAll(temp);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return users;
    }
    public static User getUser(int id){
        String apiUrl = path+"/getEmployee?id=" + id;
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return mapper.readValue(response.body(), User.class);
            }
            else {
                System.out.println(response.body());
                throw new IOException("No encontro el usuario: ");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Respuesta incorrecta");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return new User();
    }
    public static ProductCategory getProductCategory(int id){
        String apiUrl = path+"/getProductCat?id=" + id;
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return mapper.readValue(response.body(), ProductCategory.class);
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Respuesta incorrecta");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return null;
    }
    public static ObservableList<UserCategory> getUserCategories(){
        String apiUrl = path+"/getEmployeeCategories";
        ObservableList<UserCategory> cats = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<UserCategory> temp = mapper.readValue(response.body(), new TypeReference<>() {
                });
                cats.addAll(temp);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return cats;
    }
    public static ObservableList<Order> getOrders(){
        String apiUrl = path+"/getOrders";
        ObservableList<Order> orders = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Order> temp = mapper.readValue(response.body(), new TypeReference<>() {
                });
                orders.addAll(temp);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return orders;
    }
    public static ObservableList<OrderInfo> getOrderInfoById(int id){
        String apiUrl = path+"/getOrderInfoById?id=" + id;
        ObservableList<OrderInfo> orderInfos = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                System.out.println(response.body());
                List<OrderInfo> temp = mapper.readValue(response.body(), new TypeReference<>() {});
                orderInfos.addAll(temp);
            }
            else
                throw new IOException("Error al obtener la orden");
        } catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return orderInfos;
    }
    public static ObservableList<Inventory> getInventory(){
        String apiUrl = path+"/ShowInventory";
        ObservableList<Inventory> inventories = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Inventory> temp = mapper.readValue(response.body(), new TypeReference<>() {});
                inventories.addAll(temp);
            }
            else
                throw new IOException("Error al obtener el inventario");
        } catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return inventories;
    }

    public static ObservableList<Product> getProducts(){
        String apiUrl = path+"/getProducts";
        ObservableList<Product> products = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Product> temp = mapper.readValue(response.body(), new TypeReference<>() {});
                products.addAll(temp);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error al convertir" );
        }catch (IOException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return products;
    }
    public static ObservableList<Product> getProductsByCategory(int prodCatId){
        String apiUrl = path+"/getProductsById?id=" + prodCatId;
        ObservableList<Product> products = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Product> temp = mapper.readValue(response.body(), new TypeReference<>() {});
                products.addAll(temp);
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al solicitar un producto");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return products;
    }
    public static Product getProduct(int id){
        String apiUrl = path+"/getProduct?id="+id;
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Product resultado = mapper.readValue(response.body(), Product.class);
            return resultado;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al solicitar un producto");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return new Product();
    }
    public static ObservableList<ProductCategory> getProductCategories(){
        String apiUrl = path+"/getProductCategories";
        ObservableList<ProductCategory> cats = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<ProductCategory> temp = mapper.readValue(response.body(), new TypeReference<>() {});
            cats.addAll(temp);
        }catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return cats;
    }
    public static ObservableList<Sells> getSells(){
        String apiUrl = path+"/getSells";
        ObservableList<Sells> sells = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Sells> temp = mapper.readValue(response.body(), new TypeReference<>() {});
            sells.addAll(temp);
        }catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return sells;
    }
    public static ObservableList<SellDetail> getSellsDetails(){
        String apiUrl = path+"/getSellsDetail";
        ObservableList<SellDetail> sellDetails = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<SellDetail> temp = mapper.readValue(response.body(), new TypeReference<>() {});
            sellDetails.addAll(temp);
        }catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return sellDetails;
    }
    public static ObservableList<Tables> getTables(){
        String apiUrl = path+"/getTables";
        ObservableList<Tables> tables = FXCollections.observableArrayList();
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                List<Tables> temp = mapper.readValue(response.body(), new TypeReference<>() {
                });
                tables.addAll(temp);
            }
        }catch (IOException e) {
            System.out.println(e.getClass());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("No se puede conectar al servidor");
            alert.showAndWait();
        } catch (InterruptedException e) {
            System.out.println("Conexion interrumpida: " + e);
        }
        return tables;
    }

    private static void deleteObject(String apiUrl) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request;
        request = HttpRequest.newBuilder(URI.create(apiUrl))
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Objeto eliminado");
            alert.setHeaderText("Objeto eliminado");
            alert.setContentText(response.body());
        }catch (IOException e){
            System.out.println("Error: " + e);
        }catch (InterruptedException e){
            System.out.println("Conexion interrumpida: " + e);
        }
    }

    public static void deleteUser(int id){
        String apiUrl = path+"/deleteEmplyee?id=" + id;
        deleteObject(apiUrl);
    }
    public static void deleteProduct(int id){
        String apiUrl = path+"/deleteProduct?id=" + id;
        deleteObject(apiUrl);
    }
    public static void deleteProducCategory(int id){
        String apiUrl = path+"/deleteProductCategory?id=" + id;
        deleteObject(apiUrl);
    }
    public static void deleteOrder(int id){
        String apiUrl = path+"/deleteOrder?id=" + id;
        deleteObject(apiUrl);
    }

    public static void deleteOrderInfo(int id){
        String apiUrl = path+"/deleteOrderInfo?id=" + id;
        deleteObject(apiUrl);
    }

    public static void deleteTable(int id){
        String apiUrl = path+"/deleteTable?id=" + id;
        deleteObject(apiUrl);
    }
    private static void updateObject(String apiUrl, String string, Object objeto) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request;
        request = HttpRequest.newBuilder(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(string)).
                build();
        HttpResponse<String> response;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Objeto actualizado");
                alert.setHeaderText("Objeto actualizado");
                alert.setContentText(response.body());
                alert.showAndWait();
            }else {
                System.out.println(response.statusCode());
                System.out.println(response.body());
                throw new IOException("No se encontró el producto en inventario");
            }
        } catch (IOException e){
            System.out.println("Error: " + e);
        }catch (InterruptedException e){
            System.out.println("Conexion interrumpida: " + e);
        }
    }
    public static void editUser(@NotNull User user){
        String apiUrl = path+"/updateEmployee?id=" + user.getId();
        updateObject(apiUrl, user.toString(), user);
    }
    public static void editUserCategory(@NotNull UserCategory category){
        String apiUrl = path+"/updateEmployeeCat?id=" + category.getId();
    }
    public static void editProduct(@NotNull Product product){
        String apiUrl = path+"/updateProduct?id=" + product.getId();
        updateObject(apiUrl, product.toString(), product);
    }
    public static void editProductCategory(@NotNull ProductCategory category){
        String apiUrl = path+"/updateProductCat?id=" + category.getId();
        updateObject(apiUrl, category.toString(), category);
    }
    public static void editOrder(@NotNull Order order){
        String apiUrl = path+"/updateOrder?id=" + order.getId();
        updateObject(apiUrl, order.toString(), order);
    }
    public static void editOrderInfo(@NotNull OrderInfo order){
        String apiUrl = path+"/updateOrderInfo?id=" + order.getId();
        updateObject(apiUrl, order.toString(), order);
    }
    public static void editTable(@NotNull Tables table){
        String apiUrl = path+"/updateTable?id=" + table.getId();
        updateObject(apiUrl, table.toString(), table);
    }
    public static void editInventory(@NotNull Inventory inventory){
        System.out.println(inventory);
        String apiUrl = path+"/updateInventory?id=" + inventory.getId();
        updateObject(apiUrl, inventory.toString(), inventory);
    }

}
