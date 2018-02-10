package org.apache.ibatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception{
        final Reader reader = Resources.getResourceAsReader("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        reader.close();

        // populate in-memory database
        final SqlSession session = sqlSessionFactory.openSession();
        final Connection conn = session.getConnection();
        final Reader dbReader = Resources.getResourceAsReader("init.sql");
        final ScriptRunner runner = new ScriptRunner(conn);
        runner.setLogWriter(null);
        runner.runScript(dbReader);

        UserMapper mapper = session.getMapper(UserMapper.class);
        UserPo lance = mapper.select("lance");
        System.out.println(lance);
        conn.close();
        dbReader.close();
        session.close();
    }
}
