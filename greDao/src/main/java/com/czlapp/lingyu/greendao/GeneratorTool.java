package com.czlapp.lingyu.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GeneratorTool {
    private static final int SQL_GREAD = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(SQL_GREAD, "com.zx.wfm.bean");
        schema.setDefaultJavaPackageDao("com.zx.wfm.dao");
        test(schema);
        new DaoGenerator().generateAll(schema, "../wfm/app/src/main/java");
    }



    private static void test(Schema schema) {
        Entity notify = schema.addEntity("test");//表名
        notify.implementsSerializable();
        notify.addIdProperty();
        notify.addStringProperty("userId");                 //我的Id

    }

}
