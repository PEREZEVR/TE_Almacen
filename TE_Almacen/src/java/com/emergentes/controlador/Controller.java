
package com.emergentes.controlador;

import com.emergentes.modelo.GestorProductos;
import com.emergentes.modelo.Productos;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Productos objTarea=new Productos();
       int id;
       int pos;
       String op = request.getParameter("op");
       
       if(op.equals("nuevo")){
           HttpSession ses=request.getSession();
           GestorProductos agenda= (GestorProductos) ses.getAttribute("agenda");
           objTarea.setId(agenda.obtenerId());
           request.setAttribute("op", op);
           request.setAttribute("miTarea", objTarea);
           request.getRequestDispatcher("editar.jsp").forward(request, response);
       }
       if(op.equals("modificar")){
           id=Integer.parseInt(request.getParameter("id"));
           HttpSession ses = request.getSession();
           GestorProductos agenda=(GestorProductos) ses.getAttribute("agenda");
           pos=agenda.ubicarProducto(id);
           objTarea=agenda.getLista().get(pos);
           
           request.setAttribute("op", op);
           request.setAttribute("miTarea", objTarea);
           request.getRequestDispatcher("editar.jsp").forward(request, response);;
           
               
       }
       if(op.equals("eliminar")){
           id=Integer.parseInt(request.getParameter("id"));
           HttpSession ses = request.getSession();
           GestorProductos agenda=(GestorProductos) ses.getAttribute("agenda");
           pos= agenda.ubicarProducto(id);
           agenda.eliminarProducto(pos);
           ses.setAttribute("agenda", agenda);
           response.sendRedirect("index.jsp");
       }
       
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Productos objTarea = new Productos();
       int pos;
       String op=request.getParameter("op");
       
       if(op.equals("grabar")){
           objTarea.setId(Integer.parseInt(request.getParameter("id")));
           objTarea.setProducto(request.getParameter("producto"));
           objTarea.setPrecio(request.getParameter("precio"));
           objTarea.setCantidad(request.getParameter("cantidad"));
           
           HttpSession ses = request.getSession();
           GestorProductos agenda=(GestorProductos) ses.getAttribute("agenda");
           String opg=request.getParameter("opg");
           if(opg.equals("nuevo")){
               agenda.insertProducto(objTarea);
           }else{
               pos=agenda.ubicarProducto(objTarea.getId());
               agenda.modificarProducto(pos, objTarea);
           }
           ses.setAttribute("agenda", agenda);
           response.sendRedirect("index.jsp");
           
       }
    }




}
