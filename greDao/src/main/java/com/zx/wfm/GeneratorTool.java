package com.zx.wfm;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 *   作者：周学 on 2017/1/15 18:55
 *   功能：
 *   邮箱：194093798@qq.com
 *  
 */
public class GeneratorTool {
    private static final int SQL_GREAD = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(SQL_GREAD, "com.zx.wfm.bean");
        schema.enableKeepSectionsByDefault();
        schema.setDefaultJavaPackageDao("com.zx.wfm.dao");
        Baseuserbean(schema);
        new DaoGenerator().generateAll(schema, "../wfm/app/src/main/java");
    }

    private static void Baseuserbean(Schema schema){
        Entity user = schema.addEntity("BaseUser");
        user.setSuperclass("com.zx.wfm.bean.Basebean");
        user.implementsSerializable();
        user.addStringProperty("objectId");
        user.setHasKeepSections(true);
        user.addStringProperty("uid").primaryKey();
        user.addStringProperty("sex");
        user.addLongProperty("age");
        user.addStringProperty("headUrl");
        user.addStringProperty("nickName");
        user.addStringProperty("deviceId");
        user.addStringProperty("userType");
        user.addBooleanProperty("isVip");
        user.addStringProperty("rechargeTime");

        // 身份证
        Entity idCard = schema.addEntity("IDCard");
        idCard.setHasKeepSections(true);
        idCard.addStringProperty("objectId");
        idCard.setSuperclass("com.zx.wfm.bean.Basebean");
        idCard.implementsSerializable();
        idCard.addLongProperty("cardId").primaryKey();
        idCard.addLongProperty("validity");

        // 两个实体类通过 Property 来建立关联，此操作会在 user 表中增加一个外键，此外键是 idCard 表中的主键
        Property personProperty = user.addLongProperty("cardId").getProperty();
        // 第一个参数为目标实体类，第二个参数为此表中的外键属性
        user.addToOne(idCard, personProperty);

        Property idCardProperty = idCard.addStringProperty("uid").getProperty();
        idCard.addToOne(user, idCardProperty);
        // 电视剧
        Entity tele = schema.addEntity("Televisionbean");
        tele.setHasKeepSections(true);
        tele.setSuperclass("com.zx.wfm.bean.Basebean");
        tele.implementsSerializable();
        tele.addStringProperty("objectId");
        tele.addStringProperty("TelevisionId").primaryKey();// 电视/电影得ID
        tele.addStringProperty("addressUrl");// 电影解析地址
        tele.addStringProperty("desc");// 电视/电影描述
        tele.addStringProperty("videoName");// 电视电影 名字
        tele.addStringProperty("headUrl");// 封面
        tele.addStringProperty("rating");// 评分
        tele.addStringProperty("from");// 来源
        tele.addLongProperty("time");// 保存时间
        tele.addStringProperty("netPage");// 网页上页数


        // 电影或电视剧单集
        Entity movie = schema.addEntity("Moviebean");
        movie.setHasKeepSections(true);
        movie.setSuperclass("com.zx.wfm.bean.Basebean");
        movie.implementsSerializable();
        movie.addStringProperty("objectId");
        movie.addStringProperty("movieId").primaryKey();// 电视/电影得ID
        movie.addStringProperty("itemUrl");// 电影解析地址
        movie.addStringProperty("desc");// 电视/电影描述
        movie.addStringProperty("videoName");// 电视电影 名字
        movie.addStringProperty("videoHeadUrl");// 电影截图
        movie.addLongProperty("time");
        movie.addStringProperty("movieDetail");
        // 电视剧 与电影得关系是 1对1 或者是1对多  一个电视剧有多个 一个或多个 集
        Property teleproperty = movie.addStringProperty("TelevisionId").getProperty();
        tele.addToMany(movie,teleproperty,"moviebeans");
        movie.addToOne(tele, teleproperty);
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // 中间表有其它两个表的主键作为外键 一个用户 可以分享N个电视  一个电视也可以 由N个用去去分享
        Entity personCourse = schema.addEntity("MovieCourse");
        personCourse.setHasKeepSections(true);
        personCourse.setSuperclass("com.zx.wfm.bean.Basebean");
        personCourse.implementsSerializable();
        personCourse.addStringProperty("objectId");
        personCourse.addLongProperty("time");
        Property personName = personCourse.addStringProperty("uid").getProperty();
        Property courseId = personCourse.addStringProperty("TelevisionId").getProperty();
        // 分别构建一对多的关联
        user.addToMany(personCourse, personName);
        tele.addToMany(personCourse, courseId);
        // 建立一对一关系
        personCourse.addToOne(user, personName);
        personCourse.addToOne(tele, courseId);

    }

}

