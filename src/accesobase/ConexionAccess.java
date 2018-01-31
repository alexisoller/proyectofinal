/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package accesobase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ConexionAccess
{
    private String NombreBD = "bases/proyecto.mdb";
    private String ConexionBD = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + this.NombreBD;
    private String SentenciaSQL;
    private Connection CanalBD;
    private Statement Instruccion;
    private ResultSet Resultado;

    public ConexionAccess()
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            this.CanalBD = DriverManager.getConnection(this.ConexionBD);
            this.Instruccion = this.CanalBD.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        }
        catch(SQLException SQLE)
        {
            JOptionPane.showMessageDialog(null,"ERROR EN LA CONEXION CON BD\nERROR : " + SQLE.getMessage());
        }
        catch(ClassNotFoundException CNFE)
        {
            JOptionPane.showMessageDialog(null,"ERROR DRIVER BD JAVA\nERROR : " + CNFE.getMessage());
        }
    }
    public int numeroRegistros(){
         int cuantos=0;
        this.SentenciaSQL="SELECT * FROM Trabajadores";
        try{          
            this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);
            Resultado.last();
            cuantos=Resultado.getRow();
            JOptionPane.showMessageDialog(null,Resultado.getRow());
        }
        catch (SQLException SQLE){
            JOptionPane.showMessageDialog(null,"ERROR AL CARGAR LOS DATOS DE LA BD2 \n ERROR : " + SQLE.getMessage());
        }
        return cuantos;
    }
    public String[] ConsultarRegistro(String SentenciaSQL,int puntero,int clientesoseguros){
        System.out.println("entra en consultar registro");
        if(clientesoseguros==0){
        String registro[]=new String[10];
        this.SentenciaSQL=SentenciaSQL;
        System.out.println(SentenciaSQL);
        try{
            this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);
            //Resultado.next();
            Resultado.absolute(puntero);
            JOptionPane.showMessageDialog(null,Resultado.getRow());
            registro[0]= Resultado.getString("dni");
            registro[1]=Resultado.getString("nombre");
            registro[2]=Resultado.getString("apellidos");
            registro[3]=Resultado.getString("telefono");
            registro[4]=Resultado.getString("edad");
            registro[5]=Resultado.getString("calle");
            registro[6]=Resultado.getString("ciudad");
            registro[7]=Resultado.getString("email");
            registro[8]=Resultado.getString("sexo");
            registro[9]=String.valueOf(Resultado.getRow());
        }
        catch (SQLException SQLE){
            JOptionPane.showMessageDialog(null,"ERROR AL CARGAR LOS DATOS DE LA BD3 \n ERROR : " + SQLE.getMessage());
        }
        return registro;
        }else{
        String registro[]=new String[2];
        this.SentenciaSQL=SentenciaSQL;
        System.out.println(SentenciaSQL);
        try{
            this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);
            //Resultado.next();
            Resultado.absolute(puntero);
            JOptionPane.showMessageDialog(null,Resultado.getRow());
            registro[0]= Resultado.getString("euros");
            registro[1]=String.valueOf(Resultado.getRow());
        }
        catch (SQLException SQLE){
            JOptionPane.showMessageDialog(null,"ERROR AL CARGAR LOS DATOS DE LA BD euros \n ERROR : " + SQLE.getMessage());
        }
        return registro;
        }
       
    }
    
    public void InsertInsertar(String SentenciaSQL)
    {
        this.SentenciaSQL = SentenciaSQL;

        try
        {
            this.Instruccion.executeUpdate(this.SentenciaSQL);
            JOptionPane.showMessageDialog(null,"El registro se agregó con éxito");
        }
        catch (SQLException SQLE)
        {
            JOptionPane.showMessageDialog(null,"ERROR AL INSERTAR REGISTRO \n ERROR : " + SQLE.getMessage());
        }
    }

    public void UpdateModificar(String SentenciaSQL)
    {
        this.SentenciaSQL = SentenciaSQL;

        try
        {
            this.Instruccion.executeUpdate(this.SentenciaSQL);
            JOptionPane.showMessageDialog(null,"Modificación con éxito");
        }
        catch (SQLException SQLE)
        {
            JOptionPane.showMessageDialog(null,"ERROR AL MODIFICAR LA BD \n ERROR : " + SQLE.getMessage());
        }
    }

    public void DeleteEliminar(String SentenciaSQL)
    {
        this.SentenciaSQL = SentenciaSQL;

        try
        {
            this.Instruccion.executeUpdate(this.SentenciaSQL);
            JOptionPane.showMessageDialog(null,"Registro eliminado");
        }
        catch (SQLException SQLE)
        {
            JOptionPane.showMessageDialog(null,"ERROR AL ELIMINAR REGISTRO DE LA BD \n ERROR : " + SQLE.getMessage());
        }
    }

    
    public DefaultTableModel SelectConsultar(String SentenciaSQL)
    {
        this.SentenciaSQL = SentenciaSQL;

        String[] TITULOS = {"matricula","marca","modelo","cv","clientedni","total"};
        String[] REGISTRO = new String[6];

        DefaultTableModel TABLA = new DefaultTableModel(null,TITULOS);

        try
        {
            this.Resultado = Instruccion.executeQuery(this.SentenciaSQL);

            while(Resultado.next())
            {
                REGISTRO[0] = Resultado.getString("matricula");
                REGISTRO[1] = Resultado.getString("marca");
                REGISTRO[2] = Resultado.getString("modelo");
                REGISTRO[3] = Resultado.getString("cv");
                REGISTRO[4] = Resultado.getString("clientedni");
                REGISTRO[5] = Resultado.getString("total");
               
                TABLA.addRow(REGISTRO);
            }
        }
        catch (SQLException SQLE)
        {
            JOptionPane.showMessageDialog(null,"ERROR AL CARGAR LOS DATOS DE LA BD4 \n ERROR : " + SQLE.getMessage());
        }

        return TABLA;
}
    
}
