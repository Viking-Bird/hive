package org.apache.hadoop.hive.metastore.mine;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hive.shims.Hadoop23Shims;
import org.apache.hadoop.security.UserGroupInformation;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

/**
 * @author pengwang
 * @date 2020/03/18
 */
public class TestMetaStore {

    @org.junit.Test
    public void test1() {
        org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory objectInspectorFactory;

        org.apache.hadoop.io.Text text;

        org.apache.hadoop.hive.serde2.objectinspector.primitive.WritableStringObjectInspector writableStringObjectInspector;

        Hadoop23Shims hadoop23Shims;

        FileSystem fileSystem;

        UserGroupInformation userGroupInformation;

        JDOPersistenceManagerFactory jdoPersistenceManagerFactory;

        JDOPersistenceManager jdoPersistenceManager;
    }
}