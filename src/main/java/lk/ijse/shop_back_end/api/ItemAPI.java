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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        System.out.println(itemDTO);
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


}
