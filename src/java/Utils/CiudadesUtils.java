package Utils;

import Entities.CmGenerado;
import Entities.DetalleCm;
import Entities.Estudiantes;
import Entities.FacturaHistorico;
import Entities.ReciboDeCaja;
import Entities.SaldosEmpleado;
import Entities.TblRegistroContravias;
import Entities.TblusuarioRegistro;
import Entities.TiquetesAutorizados;
import Modelo.Conexion;
import Modelo.ConexionPool;
import Modelo.ConsultaGeneral;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mauricio Herrera - Juan Castrillon
 * @version 1.0 de octubre de 2016
 */
public class CiudadesUtils {

    static ConexionPool pool = new ConexionPool();
    static Connection cn;
    static Connection con3 = null;
    static PreparedStatement pstm;
    static ResultSet rs;
    static CallableStatement cstmt;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static List<ConsultaGeneral> getSelectSql(String vista, int opc, String params) throws SQLException {
        ArrayList<ConsultaGeneral> l = new ArrayList<>();
        System.out.println("parametros : " + params);
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call ConsultaGeneral (?,?,?)}");
            cstmt.setString(1, vista);
            cstmt.setInt(2, opc);
            cstmt.setString(3, params);
            ResultSet rs = cstmt.executeQuery();
            int result = 0;
            while (rs.next()) {
                if (vista.equalsIgnoreCase("ciudadUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("empresaUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("empresaById")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1)));
                    }
                } else if (vista.equalsIgnoreCase("servicioUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("estadosUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("origenDestinoUtil")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilCodRuta")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilOrigen")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilDestino")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("oDestUtilServicio")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("empleadoAutocomplete")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("autocompleteEmpl")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getString(1), rs.getString(2)));
                    }
                } else if (vista.equalsIgnoreCase("idConvenioBydetalle")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1)));
                    }
                } else if (vista.equalsIgnoreCase("valueRuta")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getInt(2)));
                    }
                } else if (vista.equalsIgnoreCase("taquillas")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                } else if (vista.equalsIgnoreCase("listCiudades")) {
                    if (opc == 1) {
                        l.add(new ConsultaGeneral(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                    }
                }
                //result = rs.getInt(1);
                // System.out.println("valor " + result);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }

        return l;
    }

    public static int getValorRuta(String origen, String destino) throws SQLException {
        int result = 0;
        try {
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement("select case when b.Temporada='Baja' then cast(precio as numeric) "
                    + "when b.Temporada='Alta' then cast(precio2 as numeric) end precio "
                    + "from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) "
                    + "AND origen = '" + origen + "' AND destino = '" + destino + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    public static List<String> getRutasWeb(String origen, String destino) throws SQLException {
        System.out.println(origen + "" + destino);
        ArrayList<String> result = new ArrayList();
        try {
            cn = Conexion.conectar2();
//            String sql = "select a.fecha as fecha, "
//                    + "origen,destino,"
//                    + "case when b.Temporada='Baja' then cast(precio as numeric) "
//                    + "when b.Temporada='Alta' then cast(precio2 as numeric) end precio,"
//                    + "servicio,SUBSTRING(hora,0,3)+':'+ SUBSTRING(hora,3,5) horario, "
//                    + "observaciones from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
//                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) and origen = '" + origen + "' and destino = '" + destino + "'  order by precio desc";

            String sql = "select distinct servicio from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + " a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) and origen = '" + origen + "' and destino = '" + destino + "'"
                    + " order by 1 desc";

//            System.out.println("query = " + sql);
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
//                String cadena = rs.getString(1).trim() + "," + rs.getString(2).trim() + "," + rs.getString(3).trim() + "," + rs.getInt(4) + "," + rs.getString(5).trim() + " " + rs.getString(7).trim().replace(",", " -") + "," + rs.getString(5).trim() + " -- " + rs.getString(6);
                String cadena = rs.getString(1).trim();
                if (!result.contains(cadena)) {
                    result.add(cadena);
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
//        HashSet<String> hs = new HashSet();
//        hs.addAll(result);
//        result.clear();
//        result.addAll(hs);
        return result;
    }

    public static String getValorGlobal(int id_empresa) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select tipo_contrato from tbl_convenio where id_empresa = " + id_empresa + " AND estado = 'A' ");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    /*
    * Método que compara el número de documento del estudiante ingresado al sistema
    * con el número de documento que se encuentra registrado en la base de datos
    * @param numero_documento
     */
    public static boolean getExistEstudiante(String numero_documento) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select * from estudiante_convenios where documento_estudiante = '" + numero_documento + "' AND estado = 'A'";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error  camilo n" + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static ArrayList<Estudiantes> listarUniversidades() throws SQLException {
        ArrayList<Estudiantes> lista = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select distinct universidad from estudiante_convenios");
            rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(new Estudiantes(rs.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return lista;
    }

    public static int getDetalleConvPk(int id_viaje_tiquete) throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select detalle_conv_pk from tbl_viajes_tiquetes where id_viaje_tiquete = " + id_viaje_tiquete);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<String> getServicios(String origen, String destino) throws SQLException {
        List<String> result = new ArrayList<>();
        try {
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement("select servicio "
                    + "from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) "
                    + "AND origen = '" + origen + "' AND destino = '" + destino + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    //historico de facturas old app
    public static List<FacturaHistorico> getHistoricoFacturas(Date fecha_ini, Date fecha_fin, String empresa) throws SQLException {
        List<FacturaHistorico> result = new ArrayList<>();
        try {
            int emp = getCodSucurEmpOldApp(empresa);
            if (emp > 0) {
                con3 = Conexion.getConexionOldApp();
                String sql = "SELECT tbl_msucursales.nombre,"
                        + "nombres,"
                        + "cedula,origen,"
                        + "destino,valor,cantidad,total,"
                        + "DATE(fecha_venta),ot,ida_regreso,"
                        + "case when orden is null then 'N/A' else orden end "
                        + "FROM factura,"
                        + "tbl_msucursales "
                        + "WHERE  DATE(fecha_venta) BETWEEN '" + format.format(fecha_ini) + "' AND '" + format.format(fecha_fin) + "' and  factura.cod_sucuremp = tbl_msucursales.cod_sucuremp "
                        + "AND tbl_msucursales.cod_sucuremp IN (" + emp + ")";
                pstm = con3.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
//                    System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+ rs.getString(3)+" "+ 
//                                       rs.getString(4)+" "+  rs.getString(5)+" "+  rs.getString(6)+" "+ 
//                                       rs.getString(7)+" "+  rs.getString(8)+" "+  rs.getDate(9)+" "+  rs.getString(10)+" "+ 
//                                       rs.getString(11)+" "+  rs.getString(12));
                    result.add(new FacturaHistorico(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10),
                            rs.getString(11), rs.getString(12)));
                }
                String sql2 = "SELECT cliente,nombre,cedula,origen,destino,"
                        + "CONCAT('$',cast(valor as char(15))),cantidad,CONCAT('$',cast(total as char(15))),"
                        + "DATE(fecha),'-',idRegreso,'-' FROM tbl_usuarioRegistro where "
                        + "DATE(fecha)  BETWEEN '" + format.format(fecha_ini) + "' AND '" + format.format(fecha_fin) + "' and cliente<>'prueba' and cod_cliente IN (" + emp + ")";
                pstm = con3.prepareStatement(sql2);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    result.add(new FacturaHistorico(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10),
                            rs.getString(11), rs.getString(12)));
                }
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            con3.close();
            pstm.close();
            Conexion.cerrarConexion3();
        }
        return result;
    }

    public static int getCodSucurEmpOldApp(String emp) throws SQLException {
        int cod = 0;
        try {
            con3 = Conexion.getConexionOldApp();
            pstm = con3.prepareStatement("SELECT cod_sucuremp FROM tbl_msucursales WHERE nombre LIKE '%" + emp + "%'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                cod = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            con3.close();
            pstm.close();
            Conexion.cerrarConexion3();
        }
        return cod;
    }

    public static String getValidateNodum(String user, String clave) throws SQLException {
        String result = "";
        try {
            String sql = "select top 1 Nombre from WF_PALM.dbo.Usuarios where Codigo='" + user + "' and Password ='" + clave + "' and Estado=0";
//            + "union "
//                    + "select Nombre from gl_usuarios where usuario='" + user + "' and estado_usuario='A' "
//                    + ") Y
            System.out.println("sql = " + sql);
            cn = Conexion.conectar2();
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
            System.out.println("result " + result);
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            cn.close();
            pstm.close();
        }
        return result;
    }

    public static boolean editCm(String consec, int val) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update relacion_recibos set verificado = " + val + " where id_trans = '" + consec + "'");
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    /*
    * Método que valida el número de tiquete ingresado en el sistema
    * con el número de tiquete que esta en la base de datos
    * @param numtiquet
     */
    public static boolean validarTiqueteExist(String numtiquet) throws SQLException {

        boolean resp = false;
        try {
            String query = "select numero_tiquete from tiquete_estudiante where numero_tiquete = '" + numtiquet + "'";
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    /*
    * Método que valida el número de documento del estudiante ingresado en el sistema
    * con el número de documento que esta en la base de datos
     */
    public static boolean validarDocumentoExist(String documento) throws SQLException {

        boolean resp = false;
        try {
            String query = "select documento_estudiante from estudiante_convenios where documento_estudiante = '" + documento + "'";
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static boolean editValTrans(int id, int val, String user) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "insert into Log_Transacciones values ('" + user + "', '" + format2.format(new Date()) + "',"
                    + " 'tbl_usuarioRegistro', (select 'id_registro='+convert(varchar(10),id_registro)+',valor='+convert(varchar(20),valor) from tbl_usuarioRegistro where id_registro = " + id + "), '" + val + "')";
            pstm = pool.con.prepareStatement(query);
            pstm.execute();
            System.out.println(query);
            query = "update tbl_usuarioRegistro set valor = " + val + ", total = " + val + " * cantidad where id_registro = " + id + "";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static boolean editEmpTrans(int id, String val, String user) throws SQLException {
        boolean resp = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "insert into Log_Transacciones values ('" + user + "', '" + format2.format(new Date()) + "',"
                    + " 'tbl_usuarioRegistro', (select cliente from tbl_usuarioRegistro where id_registro = " + id + "), '" + val + "')";
            pstm = pool.con.prepareStatement(sql);
            pstm.execute();
            System.out.println(sql);
            sql = "update tbl_usuarioRegistro set cliente = '" + val + "',"
                    + "id_empresa = (select top 1 id_empresa from tbl_empresas where nombre like '%" + val + "%') "
                    + "where id_registro = " + id + "";
            System.out.println(sql);
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();
            resp = true;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static int getIdUserByEmpresa(int id_empresa) throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select id_usuario from usuarios where id_empresa = " + id_empresa + "");
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    // mas adelante cambiar estos 2 metodos y dejarlos en uno solo
    public static String getConsecutivoAgencia(String cod_agencia, String tabla) throws SQLException {
        String code = "";
        String newCode = "";
        int sum = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select consecutivo from " + tabla + " where cod_agencia = '" + cod_agencia + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                code = rs.getString(1);
            }
            if (code.equals("000000")) {
                newCode = "000001";
            } else {
                sum = Integer.parseInt(code) + 1;
                if (Integer.toString(sum).length() == 1) {
                    newCode = "00000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 2) {
                    newCode = "0000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 3) {
                    newCode = "000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 4) {
                    newCode = "00" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 5) {
                    newCode = "0" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 6) {
                    newCode = Integer.toString(sum);
                }

            }
            pstm = pool.con.prepareStatement("update " + tabla
                    + " set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'");
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return code;
    }

    public static void rollBackConsecutivoAgencias(String cod_agencia, String tabla, String newCode) throws SQLException {
        int res = Integer.parseInt(newCode);

        if (Integer.toString(res).length() == 1) {
            newCode = "00000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 2) {
            newCode = "0000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 3) {
            newCode = "000" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 4) {
            newCode = "00" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 5) {
            newCode = "0" + Integer.toString(res);
        }
        if (Integer.toString(res).length() == 6) {
            newCode = Integer.toString(res);
        }
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("update " + tabla
                    + " set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'");
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }

    }
    //CONSECUTIVO CONTRAVIAS

    public static String getConsecutivoContravias(String cod_agencia) throws SQLException {
        String code = "";
        String newCode = "";
        int sum = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select consecutivo from consecutivo_contravias where cod_agencia = '" + cod_agencia + "'");
            rs = pstm.executeQuery();
            if (rs.next()) {
                code = rs.getString(1);
            }

            if (code.equals("000000")) {
                newCode = "000001";
            } else {
                sum = Integer.parseInt(code) + 1;
                if (Integer.toString(sum).length() == 1) {
                    newCode = "00000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 2) {
                    newCode = "0000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 3) {
                    newCode = "000" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 4) {
                    newCode = "00" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 5) {
                    newCode = "0" + Integer.toString(sum);
                }
                if (Integer.toString(sum).length() == 6) {
                    newCode = Integer.toString(sum);
                }

            }

            String sqlu = "update consecutivo_contravias "
                    + "set consecutivo = '" + newCode + "' where cod_agencia = '" + cod_agencia + "'";
            pstm = pool.con.prepareStatement(sqlu);
            System.out.println(sqlu);
            pstm.executeUpdate();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return code;
    }

    //historico de contravias old app
    public static List<TblRegistroContravias> getHistoricoContravias(Date fecha_ini, Date fecha_fin, String agencia) throws SQLException {
        List<TblRegistroContravias> result = new ArrayList<>();
        try {
            System.out.println("agencia = " + agencia);
            cn = Conexion.getConexionOldApp();
            agencia = agencia.replace('-', ' ');
            System.out.println("");
            String and = "";
            if (!agencia.equals("")) {
                and = " AND usuario_mod LIKE '%" + agencia + "%'";
            }
            String sql = "SELECT * FROM tbl_registroContravias "
                    + "WHERE fecha BETWEEN '" + format.format(fecha_ini) + " 00:00:00' "
                    + "AND '" + format.format(fecha_fin) + " 23:59:59'" + and;
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                result.add(new TblRegistroContravias(rs.getString("secuencia"), 60, rs.getString("nom_comprador"),
                        rs.getString("cc_comprador"), rs.getString("nom_viajero"), rs.getString("cc_viajero"),
                        rs.getString("origen"), rs.getString("destino"), rs.getInt("valor"),
                        rs.getInt("total"), rs.getString("idRegreso"), rs.getString("cod_bus"),
                        rs.getString("observacion"), rs.getDate("fecha"), rs.getString("usuario_mod"),
                        "No asignado", rs.getString("UsuarioTaquilla"), rs.getString("servicio"),
                        rs.getString("piso"), rs.getString("estado"), rs.getString("user_entrega"),
                        "No asignado", rs.getString("nomb_user_entrega"), rs.getString("telefono_comprador"),
                        rs.getString("no_reserva"), 0, rs.getInt("dinero_en_casa"),
                        rs.getInt("dinero_en_casa") == 1, rs.getDate("fecha_entrega"), "No asignado",
                        rs.getString("tiquete"), rs.getInt("cantidad")));
            }

        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pstm.close();
            rs.close();
//            Conexion.cerrarConexion3();
        }
        return result;
    }

    //recaudarMoney contravias old app
    public static int recaudarMoneyold(TblRegistroContravias c) throws SQLException {
        int result = 0;
        try {
//            System.out.println("trns = " + c.getTransaccion());
            con3 = Conexion.getConexionOldApp();
            String sql = "UPDATE tbl_registroContravias set dinero_en_casa = " + (c.getDineroEnCasa() == 1 ? 0 : 1)
                    + " WHERE secuencia = '" + c.getTransaccion() + "'";
            pstm = con3.prepareStatement(sql);
            result = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            Conexion.cerrarConexion3();
        }
        return result;
    }

    public static int countAlertas() throws SQLException {
        int result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select COUNT(*) from alertas where fecha_creacion = '" + format.format(new Date()) + "'";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static String getEmpresaById(int idEmpresa) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select nombre from tbl_empresas where id_empresa = " + idEmpresa;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static String getCiudadByCode(int codCiudad) throws SQLException {
        String result = "";
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select ciudad from tbl_ciudades where cod_ciudad = " + codCiudad;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateTotalTransbyId(int idTrans) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select SUM(pagar) total from total_transaccion where trans = " + idTrans;
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String query = "update transaccion set total = " + rs.getInt(1) + " "
                        + "where id_transaccion = " + idTrans;
//                System.out.println("query " +query);
                pstm = pool.con.prepareStatement(query);
                pstm.executeUpdate();
                result = true;
            }
            System.out.println("exito");
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateTotalTransTiq(int idTrans) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select [dbo].[getTotalTiquetesTrans](" + idTrans + ")";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                String query = "update transaccion set cant_tiquetes = " + rs.getFloat(1) + " "
                        + "where id_transaccion = " + idTrans;
//                System.out.println("query " +query);
                pstm = pool.con.prepareStatement(query);
                pstm.executeUpdate();
                result = true;
            }
            System.out.println("exito");
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<String> cargarUsers() throws SQLException {
        List<String> users = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("Select nombres+' '+apellidos from usuarios where nombres like '%VALORES%'");
            rs = pstm.executeQuery();
            while (rs.next()) {
                users.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return users;
    }

    public static List<ReciboDeCaja> reciboDeCaja(int id_trans) throws SQLException {
        List<ReciboDeCaja> recibo = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select  t.id_transaccion, "
                    + "CONVERT(datetime, t.fecha_mod) as fechaEntrega, "
                    + "t.cant_tiquetes, "
                    + "t.descripcion_tiquetes as desc_tiquetes, "
                    + "t.total, "
                    + "e.nombre empresa, "
                    + "t.userNodum, "
                    + "t.usuario_taquilla userTaquilla, "
                    + "t.taquilla, "
                    + "t.tiquetes_entregados, "
                    + "tv.documento, "
                    + "em.nombre, "
                    + "c1.ciudad as origen, "
                    + "c2.ciudad as destino, "
                    + "tv.valor_expal, "
                    + "tv.cant_tiquetes unidad_tiquete, "
                    + "vt.ida_regreso, "
                    + "s.servicio, "
                    + "case when vt.ida_regreso = 'Si' then  (tv.cant_tiquetes * tv.valor_expal)*2 "
                    + "else (tv.cant_tiquetes * tv.valor_expal) end as total_unidad "
                    + "from transaccion_viajero tv, transaccion t, tbl_viajes_tiquetes vt, "
                    + "tbl_ciudades c1, tbl_ciudades c2, empresa_empleado ee, tbl_empleados em, tbl_servicios s, "
                    + "detalle_convenio dc, tbl_empresas e, usuarios u "
                    + "where t.id_transaccion = tv.id_transaccion and tv.id_transaccion = " + id_trans + " "
                    + "and tv.id_viaje_tiquete = vt.id_viaje_tiquete "
                    + "and tv.cod_ciudad_origen = c1.cod_ciudad and tv.cod_ciudad_destino = c2.cod_ciudad "
                    + "and ee.documento = tv.documento and em.documento = tv.documento "
                    + "and tv.detalle_conv_pk = dc.detalle_conv_pk "
                    + "and dc.id_servicio = s.id_servicio "
                    + "and u.id_empresa = e.id_empresa and t.id_usuario = u.id_usuario";
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                recibo.add(new ReciboDeCaja(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16),
                        rs.getString(17), rs.getString(18), rs.getInt(19)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return recibo;
    }

    public static List<TiquetesAutorizados> reciboDeCajaAutorizados(String arrayIdTrans) throws SQLException {
        List<TiquetesAutorizados> recibo = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "select t.*, u.nombres+''+u.apellidos from tiquetes_autorizados t , usuarios u where u.documento = t.usuario_solicita and t.id_carga in (" + arrayIdTrans.substring(0, arrayIdTrans.length() - 1) + ")";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                recibo.add(new TiquetesAutorizados(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getDate(8),
                        rs.getString(9), rs.getDate(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16), rs.getString(19), rs.getDate(17), rs.getString(18)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return recibo;
    }

    public static boolean existEmpresaEmpleado(String documento, int id_empresa) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement("select * from empresa_empleado where documento = '" + documento + "' and id_empresa = " + id_empresa);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean anularTransaccion(String idTransAnular, String documentoUserLog) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "update tbl_usuarioRegistro set cliente = 'anulado', "
                    + "nombre = 'anulado', documento = 'anulado', valor = 0, cantidad = 0, total = 0, cod_bus = '', "
                    + "tiquete = '', taquilla = '', user_mod = '" + documentoUserLog + "' where transaccion = '" + idTransAnular + "'";
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean updateNumberTiquete(TiquetesAutorizados t) throws SQLException {
        boolean result = false;
        try {

            pool.con = pool.dataSource.getConnection();
            String query = "update tiquetes_autorizados set tiquete = '" + t.getTiquete() + "', tiquete_regreso = '" + t.getTiquete_regreso() + "' where id_carga = " + t.getId_carga() + "";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static TiquetesAutorizados getUserExist(String documento) throws SQLException {
        TiquetesAutorizados object = null;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "select top 1 documento, nombre_completo, telefono from tiquetes_autorizados\n"
                    + "where documento = '" + documento + "'";
//            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                object = new TiquetesAutorizados(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return object;
    }

    public static boolean cargarAutorizadosExcel(ArrayList<String> lista, String Autoriza) throws SQLException {
        boolean respuesta = false;
        Date fechaSolicitud = new Date();
        String estado = "Pendiente";
        try {
            pool.con = pool.dataSource.getConnection();
            lista.forEach((String next) -> {
                String datos[] = next.split(",");
                String query = "insert into tiquetes_autorizados values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try {
//                System.out.println("query " + query);

                    pstm = pool.con.prepareStatement(query);
                    pstm.setString(1, datos[0].replace(".", "").trim());
                    pstm.setString(2, datos[1] + " " + datos[2]);
                    pstm.setString(3, datos[3]);
                    pstm.setString(4, datos[4]);
                    pstm.setString(5, datos[5]);
                    pstm.setString(6, datos[6]);
                    pstm.setString(7, format2.format(fechaSolicitud));
                    pstm.setString(8, estado);
                    pstm.setString(9, null);
                    pstm.setString(10, datos[7]);
                    pstm.setString(11, null);
                    pstm.setString(12, null);
                    pstm.setString(13, Autoriza);
                    pstm.setString(14, null);
                    pstm.setString(15, datos[8]);
                    pstm.setString(16, ((datos[9].equals("NA")) ? "1900-01-01" : datos[9]));
                    pstm.setString(17, null);
                    pstm.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("error " + ex);
                }
            });
            respuesta = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return respuesta;
    }

    public static boolean existEstudinte(String doc, String universidad) throws SQLException {
        boolean respuesta = false;
        String queryS = "select * from estudiante_convenios "
                + "where documento_estudiante like '%" + doc.replace(".", "").replace(",", "") + "%' "
                + "and universidad like '%" + universidad + "%'";
        try {
            pool.con = pool.dataSource.getConnection();
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        } finally {
            pool.con.close();
        }
        return respuesta;

    }

    public static boolean cargarEstudiantesExcel(ArrayList<String> lista, int usuario) throws SQLException {
        boolean respuesta = false;
//        System.out.println("lista tam = " + lista.size());
        try {
            pool.con = pool.dataSource.getConnection();
            lista.forEach((String next) -> {
                String datos[] = next.split(",");
                String query = "insert into estudiante_convenios values(?,?,?,?,?,?)";
//                String queryS = "insert into estudiante_convenios values('"+datos[0].replace(".", "").replace(",", "")+"'"
//                        + ",'"+datos[1]+"','"+datos[2]+"','"+datos[3]+"',"+usuario+",'A')";

                try {
//                    System.out.println("query " + queryS);
                    pstm = pool.con.prepareStatement(query);
                    pstm.setString(1, datos[0].replace(".", "").replace(",", ""));
                    pstm.setString(2, datos[1]);
                    pstm.setString(3, datos[2]);
                    pstm.setString(4, datos[3]);
                    pstm.setInt(5, usuario);
                    pstm.setString(6, "A");
                    pstm.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("error insert" + ex);
                }
            });
            respuesta = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return respuesta;
    }

    public static boolean updateNumeroFacturaCms(CmGenerado cms) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String query = "update relacion_recibos set no_factura = '" + cms.getNo_factura() + "' where id_trans = '" + cms.getId_trans() + "'";
            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static boolean chekRecibido(CmGenerado cms, int opc) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            int val = 0;
            String query = "";
            if (opc == 1) {
                if (cms.isRecibido()) {
                    val = 1;
                }
                query = "update relacion_recibos set recibido = " + val + " where id_trans = '" + cms.getId_trans() + "'";
            } else {
                if (cms.isEn_contabilidad()) {
                    val = 1;
                }
                query = "update relacion_recibos set en_contabilidad = " + val + " where id_trans = '" + cms.getId_trans() + "'";
            }

            System.out.println("query " + query);
            pstm = pool.con.prepareStatement(query);
            pstm.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static float getCantTiqEntregados(int id_Viaje_tiquete) throws SQLException {
        float result = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select tiquetes_entregados from tbl_viajes_tiquetes where id_viaje_tiquete = " + id_Viaje_tiquete;
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getFloat(1);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<CmGenerado> returnCmsAsocDescrip(String numeroCm) throws SQLException {
        List<CmGenerado> CmgeneradoList = new ArrayList();
        int countDescripcion = 0;
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select id_trans, agencia, cm_asoc, fecha_creacion, verificado,  valor, no_factura, en_contabilidad, recibido,  case when descripcion IS NULL then '' else descripcion end "
                    + " from ReturnListCms(" + Integer.parseInt(numeroCm) + ")";
//            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (!rs.getString(10).equals("")) {
                    countDescripcion++;
                }
//                System.out.println("rs.getString(10) " + rs.getString(10));
                CmgeneradoList.add(new CmGenerado(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getBoolean(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getString(10)));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        if (countDescripcion == 0) {
            CmgeneradoList.clear();
        }
        return CmgeneradoList;
    }

    public static boolean verificarCmExist(String cm_asoc) throws SQLException {
        boolean result = false;
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select cm_asoc from detalle_relacion where cm_asoc = '" + cm_asoc + "' "
                    + "union "
                    + "select cm_asoc from recibos_manuales where cm_asoc = '" + cm_asoc + "'";

//            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return result;
    }

    public static List<TblusuarioRegistro> returnTrans(String idTrans) throws SQLException {
        List<TblusuarioRegistro> list = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select * from tbl_usuarioRegistro where transaccion = '" + idTrans + "'";
            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                TblusuarioRegistro objeto = new TblusuarioRegistro();
                objeto.setIdRegistro(rs.getString(1));
                objeto.setTransaccion(rs.getString(26));
                objeto.setCliente(rs.getString(2));
                objeto.setNombre(rs.getString(3));
                objeto.setDocumento(rs.getString(4));
                objeto.setValor(rs.getInt(8));
                objeto.setTotal(rs.getInt(11));
                objeto.setCmgenerado(rs.getInt(27));
                list.add(objeto);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return list;
    }

    public static List<TblRegistroContravias> returnTransContravia(String idTrans) throws SQLException {
        List<TblRegistroContravias> list = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select * from tbl_registroContravias where transaccion = '" + idTrans + "'";
            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                TblRegistroContravias objeto = new TblRegistroContravias();
                objeto.setTransaccion(rs.getString(1));
                objeto.setNombre_comprador(rs.getString(3));
                objeto.setCc_comprador(rs.getString(4));
                objeto.setOrigen(rs.getString(7));
                objeto.setDestino(rs.getString(8));
                objeto.setTotal(rs.getInt(11));
                list.add(objeto);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return list;
    }

    public static int anularTransaccion(TblusuarioRegistro currenTrans, String usermod) throws SQLException {
        int resp = -1;
        try {
            pool.con = pool.dataSource.getConnection();
            String sql = "insert into Log_Transacciones values ('" + usermod + "', '" + format2.format(new Date()) + "',"
                    + " 'tbl_usuarioRegistro', (select 'Registro='+convert(varchar(10),id_registro)+','+ "
                    + "cliente+','+ "
                    + "nombre+','+ "
                    + "documento+','+ "
                    + "ot+','+ "
                    + "convert(varchar(10),cod_ciudad_origen)+','+ "
                    + "convert(varchar(10),cod_ciudad_destino)+','+"
                    + "convert(varchar(10),valor)+','+ "
                    + "convert(varchar(10),cantidad)+','+"
                    + "ida_regreso+','+"
                    + "convert(varchar(10),total)+','+"
                    + "cod_bus+','+"
                    + "observacion+','+"
                    + "tiquete+','+"
                    + "convert(varchar(10),id_empresa)+','+"
                    + "user_mod+','+"
                    + "taquilla+','+"
                    + "convert(varchar(10),valor_sin)+','+"
                    + "convert(varchar(10),Fecha_Creacion)+','+"
                    + "convert(varchar(10),Total_sin)+','+"
                    + "convert(varchar(10),fechaviaje)+','+"
                    + "userNodum+','+"
                    + "claveNodum+','+"
                    + "confirm_registro+','+"
                    + "usuario_taquilla+','+"
                    + "transaccion+','+"
                    + "convert(varchar(10),cmgenerado) from tbl_usuarioRegistro where transaccion = " + currenTrans.getTransaccion() + "), 'Anulado')";
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();
            sql = "update tbl_usuarioRegistro set cliente = 'anulado', "
                    + "nombre = 'anulado',"
                    + "documento = 'anulado',"
                    + "valor = 0, cantidad = 0,"
                    + "total = 0,"
                    + "tiquete = 'anulado',"
                    + "observacion = 'anulado' "
                    + "where transaccion = '" + currenTrans.getTransaccion() + "'";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();
            resp = 1;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static int anularTransaccionContravia(TblRegistroContravias currenTrans, String usermod) throws SQLException {
        int resp = -1;
        try {
            pool.con = pool.dataSource.getConnection();

            String sql = "insert into Log_Transacciones values ('" + usermod + "', '" + format2.format(new Date()) + "',"
                    + " 'tbl_registroContravias', (select 'Registro='+transaccion+','+\n"
                    + "convert(varchar(20),id_empresa)+','+\n"
                    + "nombre_comprador+','+\n"
                    + "cc_comprador+','+\n"
                    + "nombre_viajero+','+\n"
                    + "cc_viajero+','+\n"
                    + "origen+','+\n"
                    + "destino+','+\n"
                    + "convert(varchar(20),valor)+','+\n"
                    + "convert(varchar(20),cant_tiquetes)+','+\n"
                    + "convert(varchar(20),total)+','+\n"
                    + "ida_regreso+','+\n"
                    + "cod_bus+','+\n"
                    + "observacion+','+\n"
                    + "convert(varchar(20),fecha_creacion)+','+\n"
                    + "no_tiquete+','+\n"
                    + "taquilla_vende+','+\n"
                    + "userNodum_vende+','+\n"
                    + "usuarioTaquilla_vende+','+\n"
                    + "servicio+','+\n"
                    + "convert(varchar(20),piso)+','+\n"
                    + "convert(varchar(20),estado)+','+\n"
                    + "taquilla_entrega+','+\n"
                    + "userNodum_entrega+','+\n"
                    + "usuarioTaquilla_entrega+','+\n"
                    + "telefono_comprador+','+\n"
                    + "no_reserva+','+\n"
                    + "convert(varchar(20),reimprimir)+','+\n"
                    + "convert(varchar(20),dinero_en_casa)+','+\n"
                    + "convert(varchar(20),fecha_entrega)+','+\n"
                    + "user_mod from tbl_registroContravias where transaccion = " + currenTrans.getTransaccion() + "), 'Anulado')";
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();

            sql = "update tbl_registroContravias set nombre_comprador = 'anulado',"
                    + "cc_comprador = 'anulado',"
                    + "nombre_viajero = 'anulado'"
                    + ",cc_viajero = 'anulado'"
                    + ",valor = 0, cant_tiquetes = 0,"
                    + "total = 0, estado = 'Anulado'"
                    + "where transaccion = '" + currenTrans.getTransaccion() + "'";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();
            resp = 1;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static List<CmGenerado> returnCMSUpdate(String idTrans) throws SQLException {
        List<CmGenerado> list = new ArrayList(9);

        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select top 1 r.*, d.cm_asoc from relacion_recibos r, detalle_relacion d "
                    + "where r.id_trans = d.id_trans and d.cm_asoc = '" + idTrans + "'";
            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            if (rs.next()) {
                CmGenerado c = new CmGenerado();
                c.setId_trans(rs.getString(1));
                c.setAgencia(rs.getString(2));
                c.setFecha_creacion(rs.getDate(3));
                c.setVerificado(rs.getBoolean(4));
                c.setNo_factura(rs.getString(5));
                c.setRecibido(rs.getBoolean(6));
                c.setEn_contabilidad(rs.getBoolean(7));
                c.setCm_asoc(rs.getString(8));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return list;
    }

    public static List<DetalleCm> returnDetalleCM(String idTrans) throws SQLException {
        List<DetalleCm> list = new ArrayList();
        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select d.* from relacion_recibos r, detalle_relacion d "
                    + "where r.id_trans = d.id_trans and d.cm_asoc = '" + idTrans + "'";
            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DetalleCm detalle = new DetalleCm();
                detalle.setId(rs.getInt(1));
                detalle.setId_trans_conv(rs.getInt(2));
                detalle.setStrIdtrans(rs.getString(3));
                detalle.setCm_asoc(rs.getString(4));
                detalle.setTabla(rs.getString(5));
                list.add(detalle);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return list;
    }

    public static int updateCmGen(String nuevocm, CmGenerado obj, String usermod) throws SQLException {
        System.out.println("nuevocm " + nuevocm);
        int resp = -1;
        try {
            pool.con = pool.dataSource.getConnection();

            String sql = "insert into Log_Transacciones values ('" + usermod + "', '" + format2.format(new Date()) + "',"
                    + " 'detalle_relacion', (select top 1 'id_trans='+id_trans+','+'cm_asoc='+cm_asoc from detalle_relacion  where id_trans = '" + obj.getId_trans() + "'), '" + nuevocm + "')";
            System.out.println("sql = " + sql);
            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();

            sql = "Update detalle_relacion "
                    + "set cm_asoc = '" + nuevocm + "' "
                    + "from relacion_recibos r, detalle_relacion d "
                    + "where d.id_trans = r.id_trans and d.id_trans = '" + obj.getId_trans() + "'";
            System.out.println("sql = " + sql);

            pstm = pool.con.prepareStatement(sql);
            pstm.executeUpdate();
            resp = 1;
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } finally {
            pool.con.close();
        }
        return resp;
    }

    public static List<SaldosEmpleado> returnViajesPendientes(String doc, String fecha_ini, String fecha_fin) throws SQLException {
        List<SaldosEmpleado> list = new ArrayList();

        try {
            pool.con = pool.dataSource.getConnection();
            String queryS = "select empre.nombre, e.documento, e.nombre, convert(varchar(10),vt.fecha_final,120) fecha_final, vt.ida_regreso, "
                    + "vt.tiquetes_asignados, vt.tiquetes_entregados , "
                    + "case when vt.estado = 0 then 'Anulado' else 'Activo' end anulado, "
                    + "case  when vt.fecha_final < convert(date,GETDATE()) then 'Vencido' else 'Vigente' end Vigencia, "
                    + "case when (vt.tiquetes_asignados-vt.tiquetes_entregados) = 0 then 'Entregados Totalmente' else 'Faltan Tiquetes Por Entregar' end entregados "
                    + "from tbl_viajes_tiquetes vt, "
                    + "tbl_empleados e, tbl_empresas empre "
                    + "where vt.documento = e.documento and "
                    + "empre.id_empresa = vt.id_empresa and "
                    + "vt.documento = '" + doc + "' "
                    + "and (fecha_inicial between '" + fecha_ini + "' and '" + fecha_fin + "' or  fecha_final between '" + fecha_ini + "' and '" + fecha_fin + "')";
            System.out.println("queryS " + queryS);
            pstm = pool.con.prepareStatement(queryS);
            rs = pstm.executeQuery();
            while (rs.next()) {
                int count = 0;
                SaldosEmpleado s = new SaldosEmpleado();
                s.setEmpresa(rs.getString(1));
                s.setDocumento(rs.getString(2));
                s.setNombre(rs.getString(3));
                s.setFecha_final(rs.getString(4));
                s.setIda_regreso(rs.getString(5));
                s.setAsignados(rs.getInt(6));
                s.setEntregados(rs.getInt(7));
                s.setAnulado(rs.getString(8));
                s.setVigencia(rs.getString(9));
                s.setEntrega(rs.getString(10));
                if (rs.getString(8).equals("Activo")) {
                    count += 1;
                }
                if (rs.getString(9).equals("Vigente")) {
                    count += 1;
                }
                if (rs.getString(10).equals("Faltan Tiquetes Por Entregar")) {
                    count += 1;
                }
                if (count == 3) {
                    s.setSeVeEnAgencia("Si");
                } else {
                    s.setSeVeEnAgencia("No");
                }
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            pool.con.close();
        }
        return list;
    }

}
