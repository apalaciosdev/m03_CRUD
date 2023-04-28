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
import java.awt.event.ActionEvent;
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
    static Integer idUser = 0;
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

        String[] botones = { "Buscar Nota", "Nueva Nota", "Restaurar Base de Datos", "Salir" };
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
        panelCampos.add(email);
        panelCampos.add(txtEmail);
        panelCampos.add(password);
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

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textValue = txtEmail.getText();
                String passValue = txtPassword.getText();
                // AQUÍ LA CONSULTA
                String query = "select ID, EMAIL, PASSWORD, NAME from notascrud.USUARIOS WHERE EMAIL = '"
                        + textValue + "' AND PASSWORD = '"
                        + passValue + "';";

                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        ventana.dispose(); // Cerrar la ventana actual
                        idUser = rs.getInt("ID");
                        menuoptions();
                    } else {
                        JOptionPane.showMessageDialog(null, "NO EXISTE NINGÚN CLIENTE CON ESOS DATOS",
                                "ERROR AL BUSCAR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {

                } finally {
                }

            }
        });

        btnSalir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose(); // Cerrar la ventana actual
            }

        });

    }

    public static void buscar() throws SQLException {

        int idUser = 1;


        // AQUÍ LA CONSULTA
        String query = "SELECT * FROM notasCRUD.NOTAS where sharedUsers LIKE '%"+idUser+"%';";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String[] botones = { "Siguiente", "Editar", "Eliminar" };
            if (rs.next()) {
                do {
                    Integer id = rs.getInt("ID");
                    String contentQuery = rs.getString("CONTENT");
                    String idUserQuery = rs.getString("idUser");
                    String sharedUsersQuery = rs.getString("sharedUsers");

                    // EMPEZAMOS CON EL MENU
                    int ventana = JOptionPane.showOptionDialog(null,
                            "\n ID: " + id +
                                    "\n Contenido: " + contentQuery,
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

    public static void crear() throws SQLException {

    }

    public static void eliminar(Integer id) throws SQLException {
        stmt3 = con.createStatement();
        String[] botones = { "Si", "No" };
        // MOSTRAMOS CON UN BUCLE TODO LO QUE HA DEVUELTO LA QUERY

        int ventana = JOptionPane.showOptionDialog(null,
                "¿Desea borrar a esta nota?",
                "ELIMINAR NOTA DE LA BASE DE DATOS",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                botones, botones[0]);
        if (ventana == 0) {
            String sql = "DELETE FROM notasCRUD.NOTAS WHERE id = " + id;
            stmt3.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SE HA ELIMINADO LA NOTA DE LA BASE DE DATOS",
                    "ELIMINADO CORRECTAMENTE",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA ELIMINADO LA NOTA DE LA BASE DE DATOS",
                    "ERROR AL ELIMINAR",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void editar(Integer id) throws SQLException {
        // PONEMOS VARIABLES A FALSE
        boolean contentBoolean = false;
        String newContent = "";

        // HACEMOS LA QUERY
        String query2 = "select * from notascrud.NOTAS where ID = " + id;

        try {
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery(query2);
            String[] botones = { "Guardar", "Salir" };

            // MOSTRAMOS CON UN BUCLE TODO LO QUE HA DEVUELTO LA QUERY
            while (rs.next()) {
                String content = rs.getString("CONTENT");

                // HACEMOS LAS PREGUNTAS Y LA GUARDAMOS EN VARIABLES
                do {
                    newContent = JOptionPane.showInputDialog(null, "INTRODUCE EL NUEVO CONTENIDO DE LA NOTA: ", content);

                    if (newContent.isEmpty() || newContent.length() >= 200) {
                        JOptionPane.showMessageDialog(null, "CONTENIDO NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        contentBoolean = true;
                    }

                } while (newContent.isEmpty() || newContent.length() >= 200 || !contentBoolean);


                // UNA VEZ PASADA LAS VALIDACIONES
                if (contentBoolean) {
                    try {
                        String[] botones2 = { "Editar", "Salir" };
                        // EMPEZAMOS CON EL MENU
                        int ventana = JOptionPane.showOptionDialog(null,
                                "\n Nuevo Contenido: " + newContent,
                                "EDICIÓN DE UNA NOTA",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null,
                                botones2, botones2[0]);
                        if (ventana == 0) {
                            stmt5 = con.createStatement();

                            // ACTUALIZAMOS
                            String query3 = "UPDATE notasCRUD.NOTAS SET CONTENT = '" + newContent
                                    + "'  WHERE id = "
                                    + id + "";

                            stmt5.executeUpdate(query3);
                            JOptionPane.showMessageDialog(null, "ACTUALIZADO CORRECTAMENTE",
                                    "ACTUALIZACIÓN DE LA BASE DE DATOS",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "NOTA NO ACTUALIZADA",
                                    "ERROR EN LA EDICIÓN",
                                    JOptionPane.ERROR_MESSAGE);
                            menuoptions();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        stmt5.close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN ALGÚN CAMPO",
                            "ALGO HA SALIDO MAL",
                            JOptionPane.ERROR_MESSAGE);
                    menuoptions();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }

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
                "sharedUsers SET() NOT NULL," +
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
                    + "1, 'Lista de la compra', '1', '1', '2')");
            stmt.executeUpdate("INSERT INTO notasCRUD.NOTAS VALUES ("
                    + "2, 'Notas ocultas', '1', '1', '2')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    public static void insertarDatos() throws SQLException {
        insertarNotas();
        insertarUsuarios();
    }
}