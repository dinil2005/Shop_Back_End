package lk.ijse.shop_back_end.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.shop_back_end.dto.ItemDTO;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "item",urlPatterns = "/item",
        initParams = {
                @WebInitParam(name = "db-user",value = "root"),
                @WebInitParam(name = "db-pw",value = "1234"),
                @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/shop"),
                @WebInitParam(name = "db-class",value= "com.mysql.cj.jdbc.Driver")
        }
        ,loadOnStartup = 3
)
public class ItemAPI extends HttpServlet {

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

    String SAVE_DATA = "INSERT INTO Items(itemId,itemName,itemPrice,itemDesc) VALUES (?,?,?,?)";
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);

        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_DATA);
        preparedStatement.setString(1,itemDTO.getItemId());
        preparedStatement.setString(2,itemDTO.getItemName());
        preparedStatement.setDouble(3,itemDTO.getItemPrice());
        preparedStatement.setString(4,itemDTO.getItemDesc());

        if (preparedStatement.executeUpdate() !=0){
            System.out.println("Save");
        }else{
            System.out.println("Not Save");
        }



    }

    String UPDATE_ITEM = "UPDATE Items SET items.itemName = ?,items.itemPrice = ?,items.itemDesc = ? WHERE items.itemId =?";
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);
        System.out.println(itemDTO);
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM);
        preparedStatement.setString(1,itemDTO.getItemName());
        preparedStatement.setDouble(2,itemDTO.getItemPrice());
        preparedStatement.setString(3,itemDTO.getItemDesc());
        preparedStatement.setString(4,itemDTO.getItemId());

        if (preparedStatement.executeUpdate() !=0){
            System.out.println("Item Update ");
        }else {
            System.out.println("Not Update ");
        }
    }


    //    String SEARCH_DATA = "SELECT * FROM Items WHERE itemId = ? ";
//    @SneakyThrows
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Jsonb jsonb = JsonbBuilder.create();
//        ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);
//        PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_DATA);
//        preparedStatement.setString(1,itemDTO.getItemId());
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            System.out.println(resultSet.getString(1));
//            System.out.println(resultSet.getString(2));
//            System.out.println(resultSet.getString(3));
//            System.out.println(resultSet.getString(4));
//        }
//    }
}
