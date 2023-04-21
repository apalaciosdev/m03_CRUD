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
    static boolean dniBoolean = false;
    static boolean dniBoolean2 = false;
    static boolean nombreBoolean = false;
    static boolean apellido1Boolean = false;
    static boolean apellido2Boolean = false;
    static boolean empresaBoolean = false;
    static boolean telBoolean = false;
    static boolean mailBoolean = false;

    static Integer contadorFinal = 1;
    static Connection con = null;
    static Statement stmt = null;
    static Statement stmt2 = null;
    static Statement stmt3 = null;
    static Statement stmt4 = null;
    static Statement stmt5 = null;

    // VALORES
    static String dni = "";
    static String nombre = "";
    static String nombreNew = "";
    static String apellido1 = "";
    static String apellido1New = "";
    static String apellido2 = "";
    static String apellido2New = "";
    static String empresa = "";
    static String empresaNew = "";
    static String telefono = "";
    static String telefonoNew = "";
    static String correo = "";
    static String correoNew = "";

    public static void main(String[] args) throws SQLException {

        // CONEXION CON LA BBDD Y CREACION DE BBDD
        conexionbbdd();

        // EJECUTA LA FUNCIÓN QUE MUESTRA EL MENÚ
        menuoptions();
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
        // PONEMOS VARIABLES A FALSE
        dniBoolean = false;
        dniBoolean2 = false;
        nombreBoolean = false;
        apellido1Boolean = false;
        apellido2Boolean = false;
        empresaBoolean = false;
        telBoolean = false;
        mailBoolean = false;

        // HACEMOS LAS PREGUNTAS Y LA GUARDAMOS EN VARIABLES
        do {
            dni = JOptionPane.showInputDialog(null, "INTRODUCE EL DNI ");
            String query4 = "select COUNT(DNI) from mgallegopt1.CLIENTE WHERE DNI = '"
                    + dni + "'";
            stmt4 = con.createStatement();
            ResultSet rs = stmt4.executeQuery(query4);

            if (dni.isEmpty() || dni.length() != 9) {
                JOptionPane.showMessageDialog(null, "DNI NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);

            } else {

                dniBoolean = true;
            }

            while (rs.next()) {
                Integer count = rs.getInt("COUNT(DNI)");
                if (count == 0) {
                    dniBoolean2 = true;
                } else {
                    JOptionPane.showMessageDialog(null, "DNI YA REGISTRADO",
                            "ALGO HA SALIDO MAL",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        } while (dni.isEmpty() || dni.length() != 9 || !dniBoolean || !dniBoolean2);

        do {
            nombre = JOptionPane.showInputDialog(null, "INTRODUCE EL NOMBRE ");

            if (nombre.isEmpty() || nombre.length() >= 40) {
                JOptionPane.showMessageDialog(null, "NOMBRE NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                nombreBoolean = true;
            }

        } while (nombre.isEmpty() || nombre.length() >= 40 || !nombreBoolean);

        do {
            apellido1 = JOptionPane.showInputDialog("INTRODUCE EL PRIMER APELLIDO ");

            if (apellido1.isEmpty() || apellido1.length() >= 50) {
                JOptionPane.showMessageDialog(null, "PRIMER APELLIDO NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                apellido1Boolean = true;
            }

        } while (apellido1.isEmpty() || apellido1.length() >= 50 || !apellido1Boolean);

        do {
            apellido2 = JOptionPane.showInputDialog("INTRODUCE EL SEGUNDO APELLIDO ");

            if (apellido2.isEmpty() || apellido2.length() >= 50) {
                JOptionPane.showMessageDialog(null, "SEGUNDO APELLIDO NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                apellido2Boolean = true;
            }

        } while (apellido2.isEmpty() || apellido2.length() >= 50 || !apellido2Boolean);

        do {
            empresa = JOptionPane.showInputDialog("INTRODUCE EL NOMBRE DE LA EMPRESA ");

            if (empresa.isEmpty() || empresa.length() >= 20) {
                JOptionPane.showMessageDialog(null, "NOMBRE DE EMPRESA NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                empresaBoolean = true;
            }

        } while (empresa.isEmpty() || empresa.length() >= 20 || !empresaBoolean);

        do {
            telefono = JOptionPane.showInputDialog("INTRODUCE EL NÚMERO DE TELÉFONO ");
            for (int i = 0; i < telefono.length(); i++) {
                char c = telefono.charAt(i);
                if (c <= '0' || c >= '9') {
                    JOptionPane.showMessageDialog(null, "LETRA DETECTADA, SOLO NÚMEROS",
                            "ALGO HA SALIDO MAL",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    if (telefono.isEmpty() || telefono.length() != 9) {
                        JOptionPane.showMessageDialog(null, "TELÉFONO NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                        i = telefono.length();
                    } else {
                        telBoolean = true;
                        i = telefono.length();
                    }
                }
            }

        } while (telefono.isEmpty() || telefono.length() != 9 || !telBoolean);

        do {
            correo = JOptionPane.showInputDialog("INTRODUCE EL CORREO ELECTRÓNICO ");

            // Patrón para validar el email
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

            java.util.regex.Matcher mather = pattern.matcher(correo);

            if (mather.find() == true) {
                if (correo.isEmpty() || correo.length() >= 70) {
                    JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO",
                            "ALGO HA SALIDO MAL",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    mailBoolean = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO",
                        "ALGO HA SALIDO MAL",
                        JOptionPane.ERROR_MESSAGE);
            }

        } while (correo.isEmpty() || correo.length() >= 70 || !mailBoolean);

        if (dniBoolean && nombreBoolean && apellido1Boolean && apellido2Boolean && empresaBoolean && telBoolean
                && mailBoolean) {
            try {
                String[] botones = { "Crear", "Limpiar", "Salir" };
                // EMPEZAMOS CON EL MENU
                int ventana = JOptionPane.showOptionDialog(null,
                        "\n Nombre: " + nombre + "\n 1r Apellido: " + apellido1
                                + "\n 2º Apellido: "
                                + apellido2 + "\n Empresa: " + empresa + "\n Teléfono: " + telefono
                                + "\n Correo: "
                                + correo,
                        "EDICIÓN DE UN CLIENTE",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null,
                        botones, botones[0]);
                if (ventana == 0) {
                    stmt = con.createStatement();

                    // BUSCAMOS EL ID Y LE SUMAMOS UNO
                    String query4 = "select DNI from mgallegopt1.CLIENTE";
                    stmt4 = con.createStatement();
                    ResultSet rs = stmt4.executeQuery(query4);

                    while (rs.next()) {
                        contadorFinal = contadorFinal + 1;
                    }

                    // HACEMOS LA QUERY QUE EJECUTAMOS PARA INSERTAR LOS DATOS
                    stmt.executeUpdate("INSERT INTO mgallegopt1.CLIENTE VALUES ("
                            + "" + contadorFinal + ", '" + dni + "', '"
                            + nombre + "', '"
                            + apellido1 + "', '"
                            + apellido2 + "', '" + empresa + "', '"
                            + telefono + "', '" + correo + "')");
                    JOptionPane.showMessageDialog(null, "CREADO CORRECTAMENTE",
                            "CREADO CORRECTAMENTE",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (ventana == 1) {
                    crear();
                } else if (ventana == 1) {
                    JOptionPane.showMessageDialog(null, "CLIENTE NO CREADO",
                            "ERROR EN LA CREACIÓN",
                            JOptionPane.ERROR_MESSAGE);
                    menuoptions();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stmt.close();
            }
        } else {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN ALGÚN CAMPO",
                    "ALGO HA SALIDO MAL",
                    JOptionPane.ERROR_MESSAGE);
            menuoptions();
        }

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
        // PONEMOS VARIABLES A FALSE
        dniBoolean = false;
        dniBoolean2 = false;
        nombreBoolean = false;
        apellido1Boolean = false;
        apellido2Boolean = false;
        empresaBoolean = false;
        telBoolean = false;
        mailBoolean = false;

        // HACEMOS LA QUERY
        String query2 = "select NOMBRE, APELLIDO1, APELLIDO2, EMPRESA, TELEFONO, CORREO from mgallegopt1.CLIENTE where ID = "
                + id;

        try {
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            ResultSet rs = stmt.executeQuery(query2);
            String[] botones = { "Guardar", "Salir" };

            // MOSTRAMOS CON UN BUCLE TODO LO QUE HA DEVUELTO LA QUERY
            while (rs.next()) {
                String nombre = rs.getString("NOMBRE");
                String apellido1 = rs.getString("APELLIDO1");
                String apellido2 = rs.getString("APELLIDO2");
                String empresa = rs.getString("EMPRESA");
                String telefono = rs.getString("TELEFONO");
                String correo = rs.getString("CORREO");

                // HACEMOS LAS PREGUNTAS Y LA GUARDAMOS EN VARIABLES
                do {
                    nombreNew = JOptionPane.showInputDialog(null, "INTRODUCE EL NUEVO NOMBRE ", nombre);

                    if (nombreNew.isEmpty() || nombreNew.length() >= 40) {
                        JOptionPane.showMessageDialog(null, "NOMBRE NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        nombreBoolean = true;
                    }

                } while (nombreNew.isEmpty() || nombreNew.length() >= 40 || !nombreBoolean);

                // EDITAMOS APELLIDOS
                do {
                    apellido1New = JOptionPane.showInputDialog("INTRODUCE EL NUEVO PRIMER APELLIDO ", apellido1);

                    if (apellido1New.isEmpty() || apellido1New.length() >= 50) {
                        JOptionPane.showMessageDialog(null, "PRIMER APELLIDO NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        apellido1Boolean = true;
                    }

                } while (apellido1New.isEmpty() || apellido1New.length() >= 50 || !apellido1Boolean);

                do {
                    apellido2New = JOptionPane.showInputDialog("INTRODUCE EL NUEVO SEGUNDO APELLIDO ", apellido2);

                    if (apellido2New.isEmpty() || apellido2New.length() >= 50) {
                        JOptionPane.showMessageDialog(null, "SEGUNDO APELLIDO NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        apellido2Boolean = true;
                    }

                } while (apellido2New.isEmpty() || apellido2New.length() >= 50 || !apellido2Boolean);

                // EDITAMOS EMPRESA
                do {
                    empresaNew = JOptionPane.showInputDialog("INTRODUCE EL NUEVO NOMBRE DE LA EMPRESA ", empresa);

                    if (empresaNew.isEmpty() || empresaNew.length() >= 20) {
                        JOptionPane.showMessageDialog(null, "NOMBRE DE EMPRESA NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        empresaBoolean = true;
                    }

                } while (empresaNew.isEmpty() || empresaNew.length() >= 20 || !empresaBoolean);

                // EDITAMOS TELEFONO
                do {
                    telefonoNew = JOptionPane.showInputDialog("INTRODUCE EL NUEVO NÚMERO DE TELÉFONO ", telefono);
                    for (int i = 0; i < telefonoNew.length(); i++) {
                        char c = telefonoNew.charAt(i);
                        if (c <= '0' || c >= '9') {
                            JOptionPane.showMessageDialog(null, "LETRA DETECTADA, SOLO NÚMEROS",
                                    "ALGO HA SALIDO MAL",
                                    JOptionPane.ERROR_MESSAGE);

                        } else {

                            if (telefonoNew.isEmpty() || telefonoNew.length() != 9) {
                                JOptionPane.showMessageDialog(null, "TELÉFONO NO VÁLIDO",
                                        "ALGO HA SALIDO MAL",
                                        JOptionPane.ERROR_MESSAGE);
                                i = telefonoNew.length();
                            } else {
                                telBoolean = true;
                                i = telefonoNew.length();
                            }
                        }
                    }

                } while (telefonoNew.isEmpty() || telefonoNew.length() != 9 || !telBoolean);

                // EDITAMOS CORREO
                do {
                    correoNew = JOptionPane.showInputDialog("INTRODUCE EL NUEVO CORREO ELECTRÓNICO ", correo);

                    // Patrón para validar el email
                    Pattern pattern = Pattern
                            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                    java.util.regex.Matcher mather = pattern.matcher(correoNew);

                    if (mather.find() == true) {
                        if (correoNew.isEmpty() || correoNew.length() >= 70) {
                            JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO",
                                    "ALGO HA SALIDO MAL",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            mailBoolean = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO",
                                "ALGO HA SALIDO MAL",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } while (correoNew.isEmpty() || correoNew.length() >= 70 || !mailBoolean);

                // UNA VEZ PASADA LAS VALIDACIONES
                if (nombreBoolean && apellido1Boolean && apellido2Boolean && empresaBoolean && telBoolean
                        && mailBoolean) {
                    try {
                        String[] botones2 = { "Editar", "Salir" };
                        // EMPEZAMOS CON EL MENU
                        int ventana = JOptionPane.showOptionDialog(null,
                                "\n Nuevo Nombre: " + nombreNew + "\n Nuevo 1r Apellido: " + apellido1New
                                        + "\n Nuevo 2º Apellido: "
                                        + apellido2New + "\n Nuevo Empresa: " + empresaNew + "\n Nuevo Teléfono: "
                                        + telefonoNew
                                        + "\n Nuevo Correo: "
                                        + correoNew,
                                "EDICIÓN DE UN CLIENTE",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null,
                                botones2, botones2[0]);
                        if (ventana == 0) {
                            stmt5 = con.createStatement();

                            // ACTUALIZAMOS
                            String query3 = "UPDATE mgallegopt1.CLIENTE SET NOMBRE = '" + nombreNew
                                    + "', APELLIDO1 = '" + apellido1New
                                    + "', APELLIDO2 = '" + apellido2New
                                    + "', EMPRESA = '" + empresaNew
                                    + "', TELEFONO = '" + telefonoNew
                                    + "', CORREO = '" + correoNew
                                    + "'  WHERE id = "
                                    + id + "";

                            stmt5.executeUpdate(query3);
                            JOptionPane.showMessageDialog(null, "ACTUALIZADO CORRECTAMENTE",
                                    "ACTUALIZACIÓN DE LA BASE DE DATOS",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "CLIENTE NO ACTUALIZADO",
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
        String sqldb = "create database mgallegopt1";

        try {
            stmt = con.createStatement();
            stmt.executeUpdate("drop database mgallegopt1");
            stmt.executeUpdate(sqldb);
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            stmt.close();
        }
        insertarDatos();
        JOptionPane.showMessageDialog(null, "BASE DE DATOS RESTAURADA");

    }

    public static void insertarDatos() throws SQLException {
        String createString = "create table mgallegopt1.CLIENTE " +
                "(ID integer NOT NULL," +
                "DNI varchar(9) NOT NULL," +
                "NOMBRE varchar(40) NOT NULL," +
                "APELLIDO1 varchar(50) NOT NULL," +
                "APELLIDO2 varchar(50) NOT NULL," +
                "EMPRESA varchar(20) NOT NULL," +
                "TELEFONO char(9)," +
                "CORREO varchar(70) NOT NULL," +
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
            stmt.executeUpdate("INSERT INTO mgallegopt1.CLIENTE VALUES ("
                    + "1, '47954695V', 'Marc', 'Gallego', 'Pozo', 'Clicko', '656565656', 'marc.gallego@clicko.es')");
            stmt.executeUpdate("INSERT INTO mgallegopt1.CLIENTE VALUES ("
                    + "2, '12345678A', 'Al-h', 'Mahma', 'Hrla', 'NASA', '678123456', 'alhmahmahrla@nasa.net')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }
}