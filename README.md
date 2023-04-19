# SHOPManagement
Introduction:
SHOPManagement project written with java and css make condition for users (which their username and password were added to admin table in myshop database(mysql)) to add, update, delete any product they want (information of product like id, brand, country, date, type and price are in product table) and buy products from available once (information of purchases are in customer and customer-info table).
-----------------------------------------------------------------------------------------------------------------------------------------------
Objects:
Product and customer are two objects of shop program,
Product attribute are in productData.java: id, brand, country, date, type and price
and customer attribute are in customerData.java: id, brand, country, date, type, quantity and price
------------------------------------------------------------------------------------------------------------------------------------------------
Two pages:
1. login page
2. dashboard (which is available for successfully logged in users)

Login:
Controller: FXMLDocumentController.java for controlling actions (contains functions).
Fxml file: FXMLDocument.fxml for Designing buttons, text fields and … with scene builder.(UX)
Design: loginDesign.css for colors and styles.(UI)
How it work:
If user hasn’t entered username and password then click on login, an error will appear that fill all blanks.
If user entered wrong username and password, an error will appear again.
And if right username and password were entered, user will successfully login and dashboard page will be open.

Dashboard:
3 parts:
1.Dashboard:
Contains charts that comparing daily activities. Showing total purchases and income and available products.(using data from custumer-info table)
2.Available products:
User can add, delete and update product from this part. It also shows list of available products and user can search to find any product.(using data from product table)
3.Purchase:
User can add product to walt and pay to buy any product.(using data from customer table)

Controller: dashboardController.java for controlling actions (contains functions).
Fxml file: dashboard.fxml for Designing buttons, text fields and … with scene builder.(UX)
Design: dashboardDesign.css for colors and styles.(UI)
-----------------------------------------------------------------------------------------------------------------------------------------------------
getData.java and database.java are for handeling sql database.
