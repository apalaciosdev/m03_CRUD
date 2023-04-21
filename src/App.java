import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class App {

    static boolean salir = false;
    // VARIABLES CREAR EDITAR
    
    static Integer contadorFinal = 1;
    static Connection con = null;
    static Statement stmt = null;
    static Statement stmt2 = null;
    static Statement stmt3 = null;
    static Statement stmt4 = null;
    static Statement stmt5 = null;

   

    public static void main(String[] args) throws SQLException {

        // CONEXION CON LA BBDD Y CREACION DE BBDD
        conexionbbdd();

        // EJECUTA LA FUNCIÓN QUE MUESTRA EL MENÚ
        iniciarSesion();
    }

    public static void menuoptions() throws SQLException {

        String[] botones = { "Buscar Cliente", "Nuevo Cliente", "Restaurar Base de Datos", "Salir" };
        // HACEMOS UN BUCLE QUE HASTA QUE NO LE DE A SALIR NO TERMINE
        while (!salir) {
            // CONEXION CON LA BBDD Y CREACION DE BBDD
            conexionbbdd();

            // EMPEZAMOS CON EL MENU
            int ventana = JOptionPane.showOptionDialog(null,
                    "¿QUE OPERACIÓN DESEA REALIZAR?",
                    "GESTOR DE LA BASE DE DATOS",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    botones, botones[0]);
            if (ventana == 0) {
                buscar();
            } else if (ventana == 1) {
                crear();
            } else if (ventana == 2) {
                restaurarBBDD();
            } else if (ventana == 3) {
                salir = true;
            }
        }

    }

     public static void iniciarSesion() {
        // Crear ventana y panel principal
        JFrame ventana = new JFrame("Iniciar Sesión");
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear panel de campos y añadirlos al panel principal
        JPanel panelCampos = new JPanel(new GridLayout(7, 2));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        // Crear etiquetas y campos de texto para cada campo
        JLabel email = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel password = new JLabel("Password:");
        JTextField txtPassword = new JTextField();

        // Añadir etiquetas y campos de texto al panel de campos
        panelCampos.add(txtEmail);
        panelCampos.add(txtPassword);

        // Crear botones y añadirlos al panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Iniciar Sesión");
        JButton btnSalir = new JButton("Salir");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnSalir);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Añadir el panel principal a la ventana y configurar la ventana
        ventana.add(panelPrincipal);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);

    }

    public void mostrarVentana() {
        // Crear ventana y panel principal
        JFrame ventana = new JFrame("Crear Cliente");
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear panel de campos y añadirlos al panel principal
        JPanel panelCampos = new JPanel(new GridLayout(7, 2));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        // Crear etiquetas y campos de texto para cada campo
        JLabel lblNIF = new JLabel("NIF:");
        JTextField txtNIF = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblApellido1 = new JLabel("Apellido 1:");
        JTextField txtApellido1 = new JTextField();
        JLabel lblApellido2 = new JLabel("Apellido 2:");
        JTextField txtApellido2 = new JTextField();
        JLabel lblEmpresa = new JLabel("Empresa:");
        JTextField txtEmpresa = new JTextField();
        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();
        JLabel lblEmail = new JLabel("Correo electrónico:");
        JTextField txtEmail = new JTextField();

        // Añadir etiquetas y campos de texto al panel de campos
        panelCampos.add(lblNIF);
        panelCampos.add(txtNIF);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblApellido1);
        panelCampos.add(txtApellido1);
        panelCampos.add(lblApellido2);
        panelCampos.add(txtApellido2);
        panelCampos.add(lblEmpresa);
        panelCampos.add(txtEmpresa);
        panelCampos.add(lblTelefono);
        panelCampos.add(txtTelefono);
        panelCampos.add(lblEmail);
        panelCampos.add(txtEmail);

        // Crear botones y añadirlos al panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnSalir = new JButton("Salir");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnLimpiar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Añadir el panel principal a la ventana y configurar la ventana
        ventana.add(panelPrincipal);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);

    }

    public static void buscar() throws SQLException {

        // Panel de los datos de cliente
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("DNI:"));
        JTextField dniField = new JTextField(10);
        panel.add(dniField);
        panel.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField(10);
        panel.add(nombreField);
        panel.add(new JLabel("Apellido:"));
        JTextField apellidoField = new JTextField(10);
        panel.add(apellidoField);
        panel.add(new JLabel("Segundo apellido:"));
        JTextField apellido2Field = new JTextField(10);
        panel.add(apellido2Field);
        panel.add(new JLabel("Empresa:"));
        JTextField empresaField = new JTextField(10);
        panel.add(empresaField);
        panel.add(new JLabel("Teléfono:"));
        JTextField telefonoField = new JTextField(10);
        panel.add(telefonoField);
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(10);
        panel.add(emailField);

        // Mostrar el JOptionPane
        int respuesta = JOptionPane.showConfirmDialog(null, panel, "Buscar cliente", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // COMPRUEBA SI LE HAS DADO AL OK
        if (respuesta == JOptionPane.OK_OPTION) {
            String dniSearch = dniField.getText();
            String nombreSearch = nombreField.getText();
            String apellidoSearch = apellidoField.getText();
            String apellido2Search = apellido2Field.getText();
            String empresaSearch = empresaField.getText();
            String telefonoSearch = telefonoField.getText();
            String emailSearch = emailField.getText();

            // AQUÍ LA CONSULTA
            String query = "select ID, DNI, NOMBRE, APELLIDO1, APELLIDO2, EMPRESA, TELEFONO, CORREO from mgallegopt1.CLIENTE WHERE DNI = '"
                    + dniSearch + "' OR NOMBRE = '"
                    + nombreSearch + "';";

            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String[] botones = { "Siguiente", "Editar", "Eliminar" };
                if (rs.next()) {
                    do {
                        Integer id = rs.getInt("ID");
                        String dniQuery = rs.getString("DNI");
                        String nombreQuery = rs.getString("NOMBRE");
                        String apellido1Query = rs.getString("APELLIDO1");
                        String apellido2Query = rs.getString("APELLIDO2");
                        String empresaQuery = rs.getString("EMPRESA");
                        String telefonoQuery = rs.getString("TELEFONO");
                        String correoQuery = rs.getString("CORREO");

                        // EMPEZAMOS CON EL MENU
                        int ventana = JOptionPane.showOptionDialog(null,
                                "\n DNI: " + dniQuery +
                                        "\n Nombre: " + nombreQuery + "\n 1r Apellido: " + apellido1Query
                                        + "\n 2º Apellido: "
                                        + apellido2Query + "\n Empresa: " + empresaQuery + "\n Teléfono: "
                                        + telefonoQuery + "\n Correo: "
                                        + correoQuery,
                                "GESTOR DE LA BASE DE DATOS",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null,
                                botones, botones[0]);
                        if (ventana == 0) {

                        } else if (ventana == 1) {
                            editar(id);
                        } else if (ventana == 2) {
                            eliminar(id);
                        }
                    } while (rs.next());

                } else {
                    JOptionPane.showMessageDialog(null, "NO EXISTE NINGÚN CLIENTE CON ESOS DATOS", "ERROR AL BUSCAR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stmt.close();
            }
            con.close();
        }

    }

    public static void crear() throws SQLException {

    }

    public static void eliminar(Integer id) throws SQLException {
        stmt3 = con.createStatement();
        String[] botones = { "Si", "No" };
        // MOSTRAMOS CON UN BUCLE TODO LO QUE HA DEVUELTO LA QUERY

        int ventana = JOptionPane.showOptionDialog(null,
                "¿Desea borrar a este cliente?",
                "ELIMINAR CLIENTE DE LA BASE DE DATOS",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                botones, botones[0]);
        if (ventana == 0) {
            String sql = "DELETE FROM mgallegopt1.CLIENTE WHERE id = " + id;
            stmt3.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SE HA ELIMINADO EL CLIENTE DE LA BASE DE DATOS",
                    "ELIMINADO CORRECTAMENTE",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO EL CLIENTE DE LA BASE DE DATOS",
                    "ERROR AL ELIMINAR",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void editar(Integer id) throws SQLException {
        
    }

    public static void conexionbbdd() throws SQLException {
        // conexion

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void restaurarBBDD() throws SQLException {
        String sqldb = "create database notasCRUD";

        try {
            stmt = con.createStatement();
            stmt.executeUpdate("drop database notasCRUD");
            stmt.executeUpdate(sqldb);
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            stmt.close();
        }
        insertarDatos();
        JOptionPane.showMessageDialog(null, "BASE DE DATOS RESTAURADA");

    }


    public static void insertarUsuarios() throws SQLException {
        String createString = "create table notasCRUD.USUARIOS " +
            "(ID integer NOT NULL," +
            "EMAIL varchar(70) NOT NULL UNIQUE," +
            "PASSWORD varchar(20) NOT NULL," +
            "NAME varchar(40) NOT NULL," +
            "PRIMARY KEY (ID))";

        // creacion de tabla
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        // insercion de elementos
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO notasCRUD.USUARIOS VALUES ("
                    + "1, 'marc@gmail.com', '12345', 'Marc')");
            stmt.executeUpdate("INSERT INTO mgallegopt1.CLIENTE VALUES ("
                    + "2, 'aaron@gmail.com', '12345', 'Aaron')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }


    public static void insertarNotas() throws SQLException {
        String createString = "create table notasCRUD.NOTAS " +
            "(ID integer NOT NULL," +
            "CONTENT varchar(200) NOT NULL," +
            "idUser integer NOT NULL," +
            "PRIMARY KEY (ID))";

        // creacion de tabla
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        // insercion de elementos
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO notasCRUD.NOTAS VALUES ("
                    + "1, 'marc@gmail.com', '12345', 'Marc')");
            stmt.executeUpdate("INSERT INTO mgallegopt1.CLIENTE VALUES ("
                    + "2, 'aaron@gmail.com', '12345', 'Aaron')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }


    public static void insertarDatos() throws SQLException {
       insertarNotas();
       insertarDatos();
    }
}