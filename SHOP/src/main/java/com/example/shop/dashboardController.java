package com.example.shop;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashboardController implements Initializable{

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button availableProducts_btn;

    @FXML
    private Button purchase_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_AB;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TC;

    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;

    @FXML
    private BarChart<?, ?> dashboard_customerChart;

    @FXML
    private AnchorPane availableProducts_form;

    @FXML
    private ImageView availableProducts_imageView;

    @FXML
    private Button availableProducts_importBtn;

    @FXML
    private TextField availableProducts_productID;

    @FXML
    private TextField availableProducts_productBrand;

    @FXML
    private TextField availableProducts_productType;

    @FXML
    private TextField availableProducts_country;

    @FXML
    private DatePicker availableProducts_date;

    @FXML
    private TextField availableProducts_price;

    @FXML
    private Button availableProducts_addBtn;

    @FXML
    private Button availableProducts_updateBtn;

    @FXML
    private Button availableProducts_clearBtn;

    @FXML
    private Button availableProducts_deleteBtn;

    @FXML
    private TextField availableProducts_search;

    @FXML
    private TableView<productData> availableProducts_tableView;

    @FXML
    private TableColumn<productData, String> availableProducts_col_productID;

    @FXML
    private TableColumn<productData, String> availableProducts_col_productBrand;

    @FXML
    private TableColumn<productData, String> availableProducts_col_productType;

    @FXML
    private TableColumn<productData, String> availableProducts_col_country;

    @FXML
    private TableColumn<productData, String> availableProducts_col_date;

    @FXML
    private TableColumn<productData, String> availableProducts_col_price;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<?> purchase_productID;

    @FXML
    private ComboBox<?> purchase_productBrand;

    @FXML
    private Label purchase_total;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private Label purchase_info_productID;

    @FXML
    private Label purchase_info_productBrand;

    @FXML
    private Label purchase_info_productType;

    @FXML
    private Label purchase_info_country;

    @FXML
    private Label purchase_info_date;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private TableColumn<customerData, String> purchase_col_productID;

    @FXML
    private TableColumn<customerData, String> purchase_col_productBrand;

    @FXML
    private TableColumn<customerData, String> purchase_col_productType;

    @FXML
    private TableColumn<customerData, String> purchase_col_country;

    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    @FXML
    private TableColumn<customerData, String> purchase_col_price;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    public void dashboardAB(){

        String sql = "SELECT COUNT(id) FROM product";

        connect = database.connectDb();
        int countAB = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countAB = result.getInt("COUNT(id)");
            }

            dashboard_AB.setText(String.valueOf(countAB));

        }catch(Exception e){e.printStackTrace();}
    }

    public void dashboardTI(){

        String sql = "SELECT SUM(total) FROM customer_info";

        connect = database.connectDb();
        double sumTotal = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                sumTotal = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + String.valueOf(sumTotal));

        }catch(Exception e){e.printStackTrace();}
    }

    public void dashboardTC(){
        String sql = "SELECT COUNT(id) FROM customer_info";

        connect = database.connectDb();
        int countTC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countTC = result.getInt("COUNT(id)");
            }

            dashboard_TC.setText(String.valueOf(countTC));

        }catch(Exception e){e.printStackTrace();}

    }

    public void dashboardIncomeChart(){

        dashboard_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_incomeChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }


    public void dashboardCustomerChart(){

        dashboard_customerChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 4";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_customerChart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }

    public void availableProductsAdd(){

        String sql = "INSERT INTO product (product_id, Brand, productType, country, date, price, image) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if(availableProducts_productID.getText().isEmpty()
                    || availableProducts_productBrand.getText().isEmpty()
                    || availableProducts_productType.getText().isEmpty()
                    || availableProducts_country.getText().isEmpty()
                    || availableProducts_date.getValue() == null
                    || availableProducts_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                // CHECK IF product ID IS ALREADY EXIST
                String checkData = "SELECT product_id FROM product WHERE product_id = '"
                        +availableProducts_productID.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("product ID: " + availableProducts_productID.getText() + " was already exist!");
                    alert.showAndWait();
                }else{

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, availableProducts_productID.getText());
                    prepare.setString(2, availableProducts_productBrand.getText());
                    prepare.setString(3, availableProducts_productType.getText());
                    prepare.setString(4, availableProducts_country.getText());
                    prepare.setString(5, String.valueOf(availableProducts_date.getValue()));
                    prepare.setString(6, availableProducts_price.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BE UPDATED THE TABLEVIEW
                    availableProductsShowListData();
                    // CLEAR FIELDS
                    availableProductsClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void availableProductsUpdate(){

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE product SET Brand = '"
                +availableProducts_productBrand.getText()+"', productType = '"
                +availableProducts_productType.getText()+"', country = '"
                +availableProducts_country.getText()+"', date = '"
                +availableProducts_date.getValue()+"', price = '"
                +availableProducts_price.getText()+"', image = '"
                +uri+"' WHERE product_id = '"+availableProducts_productID.getText()+"'";

        connect = database.connectDb();

        try{
            Alert alert;

            if(availableProducts_productID.getText().isEmpty()
                    || availableProducts_productBrand.getText().isEmpty()
                    || availableProducts_productType.getText().isEmpty()
                    || availableProducts_country.getText().isEmpty()
                    || availableProducts_date.getValue() == null
                    || availableProducts_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE product ID: " + availableProducts_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Updated!");
                    alert.showAndWait();

                    // TO BE UPDATED THE TABLEVIEW
                    availableProductsShowListData();
                    // CLEAR FIELDS
                    availableProductsClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void availableProductsDelete(){

        String sql = "DELETE FROM product WHERE product_id = '"
                +availableProducts_productID.getText()+"'";

        connect = database.connectDb();

        try{
            Alert alert;

            if(availableProducts_productID.getText().isEmpty()
                    || availableProducts_productBrand.getText().isEmpty()
                    || availableProducts_productType.getText().isEmpty()
                    || availableProducts_country.getText().isEmpty()
                    || availableProducts_date.getValue() == null
                    || availableProducts_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE product ID: " + availableProducts_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Delete!");
                    alert.showAndWait();

                    // TO BE UPDATED THE TABLEVIEW
                    availableProductsShowListData();
                    // CLEAR FIELDS
                    availableProductsClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void availableProductsClear(){
        availableProducts_productID.setText("");
        availableProducts_productBrand.setText("");
        availableProducts_productType.setText("");
        availableProducts_country.setText("");
        availableProducts_date.setValue(null);
        availableProducts_price.setText("");

        getData.path = "";

        availableProducts_imageView.setImage(null);
    }

    public void avaialableProductsInsertImage(){

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("File Image", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if(file != null){
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 112, 137, false, true);
            availableProducts_imageView.setImage(image);
        }

    }

    public ObservableList<productData> availableProductsListData(){

        ObservableList<productData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData productD;

            while(result.next()){
                productD = new productData(result.getInt("product_id"), result.getString("Brand")
                        , result.getString("productType"), result.getString("country")
                        , result.getDate("pub_date"), result.getDouble("price")
                        , result.getString("image"));

                listData.add(productD);
            }
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<productData> availableProductsList;
    public void availableProductsShowListData(){
        availableProductsList = availableProductsListData();

        availableProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        availableProducts_col_productBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        availableProducts_col_productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        availableProducts_col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        availableProducts_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        availableProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableProducts_tableView.setItems(availableProductsList);
    }

    public void availableProductsSelect(){
        productData productD = availableProducts_tableView.getSelectionModel().getSelectedItem();
        int num = availableProducts_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1){ return; }

        availableProducts_productID.setText(String.valueOf(productD.getproductId()));
        availableProducts_productBrand.setText(productD.getBrand());
        availableProducts_productType.setText(productD.getproductType());
        availableProducts_country.setText(productD.getcountry());
        availableProducts_date.setValue(LocalDate.parse(String.valueOf(productD.getDate())));
        availableProducts_price.setText(String.valueOf(productD.getPrice()));

        getData.path = productD.getImage();

        String uri = "file:" + productD.getImage();

        image = new Image(uri, 112, 137, false, true);

        availableProducts_imageView.setImage(image);
    }

    public void availableProductsSeach(){

        FilteredList<productData> filter = new FilteredList<>(availableProductsList, e -> true);

        availableProducts_search.textProperty().addListener((Observable, oldValue, newValue) ->{

            filter.setPredicate(predicateproductData -> {

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateproductData.getproductId().toString().contains(searchKey)){
                    return true;
                }else if(predicateproductData.getBrand().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateproductData.getproductType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateproductData.getcountry().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateproductData.getDate().toString().contains(searchKey)){
                    return true;
                }else if(predicateproductData.getPrice().toString().contains(searchKey)){
                    return true;
                }else return false;
            });
        });

        SortedList<productData> sortList = new SortedList(filter);
        sortList.comparatorProperty().bind(availableProducts_tableView.comparatorProperty());
        availableProducts_tableView.setItems(sortList);

    }

    private double totalP;
    public void purchaseAdd(){
        purchasecustomerId();

        String sql = "INSERT INTO customer (customer_id, product_id, Brand, productType, country, quantity, price, date) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if(purchase_productBrand.getSelectionModel().getSelectedItem() == null
                    || purchase_productID.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product first");
                alert.showAndWait();
            }else{

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, purchase_info_productID.getText());
                prepare.setString(3, purchase_info_productBrand.getText());
                prepare.setString(4, purchase_info_productType.getText());
                prepare.setString(5, purchase_info_country.getText());
                prepare.setString(6, String.valueOf(qty));

                String checkData = "SELECT Brand, price FROM product WHERE Brand = '"
                        +purchase_productBrand.getSelectionModel().getSelectedItem()+"'";

                double priceD = 0;

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    priceD = result.getDouble("price");
                }

                totalP = (qty * priceD);

                prepare.setString(7, String.valueOf(totalP));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();

                purchaseDisplayTotal();
                purchaseShowCustomerListData();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public void purchasePay(){

        String sql = "INSERT INTO customer_info (customer_id, total, date) "
                + "VALUES(?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;
            if(displayTotal == 0){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :3");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(displayTotal));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    private double displayTotal;
    public void purchaseDisplayTotal(){
        purchasecustomerId();

        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"+customerId+"'";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                displayTotal = result.getDouble("SUM(price)");
            }

            purchase_total.setText("$" + String.valueOf(displayTotal));

        }catch(Exception e){e.printStackTrace();}

    }

    public void purchaseproductId(){

        String sql = "SELECT product_id FROM product";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("product_id"));
            }

            purchase_productID.setItems(listData);
            purchaseproductBrand();
        }catch(Exception e){e.printStackTrace();}

    }

    public void purchaseproductBrand(){

        String sql = "SELECT product_id, Brand FROM product WHERE product_id = '"
                +purchase_productID.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("Brand"));
            }

            purchase_productBrand.setItems(listData);

            purchaseproductInfo();

        }catch(Exception e){e.printStackTrace();}

    }

    public void purchaseproductInfo(){

        String sql = "SELECT * FROM product WHERE Brand = '"
                +purchase_productBrand.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();

        String productId = "";
        String Brand = "";
        String productType = "";
        String country = "";
        String date = "";

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                productId = result.getString("product_id");
                Brand = result.getString("Brand");
                productType = result.getString("productType");
                country = result.getString("country");
                date = result.getString("pub_date");
            }

            purchase_info_productID.setText(productId);
            purchase_info_productBrand.setText(Brand);
            purchase_info_productType.setText(productType);
            purchase_info_country.setText(country);
            purchase_info_date.setText(date);

        }catch(Exception e){e.printStackTrace();}

    }

    public ObservableList<customerData> purchaseListData(){
        purchasecustomerId();
        String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";

        ObservableList<customerData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try{
            prepare  = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData customerD;

            while(result.next()){
                customerD = new customerData(result.getInt("customer_id")
                        , result.getInt("product_id")
                        , result.getString("Brand")
                        , result.getString("productType")
                        , result.getString("country")
                        , result.getInt("quantity")
                        , result.getDouble("price")
                        , result.getDate("date"));

                listData.add(customerD);
            }

        }catch(Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<customerData> purchaseCustomerList;
    public void purchaseShowCustomerListData(){
        purchaseCustomerList = purchaseListData();

        purchase_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        purchase_col_productBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        purchase_col_productType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        purchase_col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseCustomerList);

    }

    private SpinnerValueFactory<Integer> spinner;

    public void purchaseDisplayQTY(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }
    private int qty;
    public void purhcaseQty(){
        qty = purchase_quantity.getValue();
    }

    private int customerId;
    public void purchasecustomerId(){

        String sql = "SELECT MAX(customer_id) FROM customer";
        int checkCID = 0 ;
        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                customerId = result.getInt("MAX(customer_id)");
            }

            String checkData = "SELECT MAX(customer_id) FROM customer_info";

            prepare = connect.prepareStatement(checkData);
            result = prepare.executeQuery();

            if(result.next()){
                checkCID = result.getInt("MAX(customer_id)");
            }

            if(customerId == 0){
                customerId += 1;
            }else if(checkCID == customerId){
                customerId = checkCID + 1;
            }

        }catch(Exception e){e.printStackTrace();}

    }

    public void displayUsername(){
        String user = getData.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            availableProducts_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            availableProducts_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            dashboardAB();
            dashboardTI();
            dashboardTC();
            dashboardIncomeChart();
            dashboardCustomerChart();

        }else if(event.getSource() == availableProducts_btn){
            dashboard_form.setVisible(false);
            availableProducts_form.setVisible(true);
            purchase_form.setVisible(false);

            availableProducts_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            availableProductsShowListData();
            availableProductsSeach();

        }else if(event.getSource() == purchase_btn){
            dashboard_form.setVisible(false);
            availableProducts_form.setVisible(false);
            purchase_form.setVisible(true);

            purchase_btn.setStyle("-fx-background-color:linear-gradient(to top right, #72513c, #ab853e);");
            availableProducts_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");

            purchaseproductBrand();
            purchaseproductId();
            purchaseShowCustomerListData();
            purchaseDisplayQTY();
            purchaseDisplayTotal();

        }
    }

    private double x = 0;
    private double y = 0;
    public void logout(){
        try{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                // HIDE YOUR DASHBOARD
                logout.getScene().getWindow().hide();
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        }catch(Exception e){e.printStackTrace();}
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        dashboardAB();
        dashboardTI();
        dashboardTC();
        dashboardIncomeChart();
        dashboardCustomerChart();

        // TO SHOW THE DATA ON TABLEVIEW (AVAILABLE Products)
        availableProductsShowListData();

        purchaseproductId();
        purchaseproductBrand();
        purchaseShowCustomerListData();
        purchaseDisplayQTY();
        purchaseDisplayTotal();

    }

}
