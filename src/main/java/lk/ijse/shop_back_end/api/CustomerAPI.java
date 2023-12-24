package lk.ijse.shop_back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.shop_back_end.dto.CustomerDTO;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "customer",urlPatterns = "/customer",
    initParams = {
            @WebInitParam(name = "db-user",value = "root"),
            @WebInitParam(name = "db-pw",value = "1234"),
            @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/shop"),
            @WebInitParam(name = "db-class",value= "com.mysql.cj.jdbc.Driver")
    }
    ,loadOnStartup = 3
)
public class CustomerAPI extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        String userName = getServletConfig().getInitParameter("db-user");
        String password = getServletConfig().getInitParameter("db-pw");
        String url = getServletConfig().getInitParameter("db-url");



        try {
            Class.forName(getServletConfig().getInitParameter("db-class"));
            this.connection = DriverManager.getConnection(url,userName,password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String SAVE_DATA = "INSERT INTO CUSTOMER (custId,custName,custForm) VALUES (?,?,?)";
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA);
        preparedStatement.setString(1,customerDTO.getCustId());
        preparedStatement.setString(2,customerDTO.getCustName());
        preparedStatement.setString(3,customerDTO.getCustForm());

        if (preparedStatement.executeUpdate() !=0){
            System.out.println("Save");
        }else {
            System.out.println("Not Save");
        }
    }

    String UPDATE_DATA = "UPDATE customer SET  customer.custName =?,customer.custForm=? WHERE customer.custId = ?";
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);

        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA);
        preparedStatement.setString(1, customerDTO.getCustName());
        preparedStatement.setString(2, customerDTO.getCustForm());
        preparedStatement.setString(3, customerDTO.getCustId());

        if (preparedStatement.executeUpdate() !=0){
            System.out.println("Update");
        }else{
            System.out.println("Not Update");
        }
    }



    String DELETE_DATA = "DELETE FROM  customer WHERE custId = ?";
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DATA);
            preparedStatement.setString(1, customerDTO.getCustId());

            if (preparedStatement.executeUpdate() !=0){
                System.out.println("Delete");
            }else{
                System.out.println("Not Delete");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    String SEARCH_CUSTOMER = "SELECT * FROM CUSTOMER WHERE custId = ?";
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//            Jsonb jsonb = JsonbBuilder.create();
//            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(),CustomerDTO.class);
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CUSTOMER);
//            preparedStatement.setString(1, customerDTO.getCustId());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                System.out.println(resultSet.getString(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getString(3));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}
