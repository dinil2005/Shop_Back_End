package lk.ijse.shop_back_end.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "filter",urlPatterns = "/*")
public class Filter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String origin = req.getHeader("Origin");
        System.out.println(origin);
        if (origin.contains(getServletContext().getInitParameter("origin"))){
            res.setHeader("Access-Control-Allow-Origin",origin);
            res.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,HEADER");
            res.setHeader("Access-Control-Allow-Headers","Content-Type");
            res.setHeader("Access-Control-Expose-Headers","Content-Type");


        }
        chain.doFilter(req,res);//meka dane  filter eka chain ekak wagy yana one handa dane natn methnin nawtino
    }
}
