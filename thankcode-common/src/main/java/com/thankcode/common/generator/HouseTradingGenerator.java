/**
 * All rights Reserved, Designed By ysusoft.
 *
 * @author: ${jiang_qian}
 * @date: 2019/8/6 9:23
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.common.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义主键生成策略
 *
 * @author: jiang_qian
 * @date: 2019/8/6 9:23
 * @version: V1.0
 */
public class HouseTradingGenerator implements Configurable, IdentifierGenerator {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private String tableName;


    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        tableName = properties.getProperty("tableName");
        if(!StringUtils.isEmpty(tableName)){
            tableName=tableName.toUpperCase();
        }
    }

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        String lock = getLock();

        //加字符串锁
        synchronized (lock) {
            try {
                Statement statement = sessionImplementor.connection().createStatement();
                Connection connection = sessionImplementor.connection();
                return getId(statement, connection);
            } catch (Exception e) {
                logger.error("获取Id异常 ：{}", e);
                return null;
            }
        }
    }

    private String getLock() {
        String lock = tableName.intern();
        return lock;
    }

    private Long getId(Statement statement, Connection connection) throws Exception {
        long idvalue = 0L;
        ResultSet resultSet = null;
        ResultSet rs = null;
        try {

            String sql = "select * from ibsidgen where tablename = '" + tableName + "'";
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                String queryIdgenSql = "select * from IBSIDGEN where Tablename='IBSIDGEN'";
                rs = statement.executeQuery(queryIdgenSql);
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    Long idval = rs.getLong("idvalue") + 1;
                    String insertIdgenSql = " Insert into IBSIDGEN (ID,TABLENAME,SEED,IDVALUE,MEMO) values (" + idval + ",'" + tableName + "',1,1,null)";
                    String updateIdgenSql = "update ibsidgen set idvalue= " + idval + "where id= " + id;
                    statement.executeUpdate(updateIdgenSql);
                    statement.executeUpdate(insertIdgenSql);
                    connection.commit();
                    this.getId(statement, connection);
                } else {
                    throw new RuntimeException("请先在IBSIDGEN里初始化一条   Tablename='IBSIDGEN' 祖数据！！！");
                }

            } else {
                idvalue = resultSet.getLong("idvalue");
                idvalue = idvalue + 1;
                String updateSql = "update ibsidgen i set i.idvalue=" + idvalue + " where i.tablename='" + tableName + "'";
                statement.executeUpdate(updateSql);
                connection.commit();
            }
        } catch (Exception e) {
            logger.error("<<<<<<<获取Id失败>>> 异常为:{}>>", e);
            throw new RuntimeException("获取Id失败", e);
        } finally {
            statement.close();
            if (resultSet != null) {
                resultSet.close();
            }
            if (rs != null) {
                rs.close();
            }
            logger.info("<<<<<<<<<<<<获取到的id:{} ，表名为：{}>>>>>>>>>>", idvalue,tableName);
            return idvalue;
        }


    }

}
