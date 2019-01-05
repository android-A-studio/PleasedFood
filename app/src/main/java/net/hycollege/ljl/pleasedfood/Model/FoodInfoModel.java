package net.hycollege.ljl.pleasedfood.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.greendao.db.DaoMaster;
import net.hycollege.ljl.pleasedfood.greendao.db.DaoSession;
import net.hycollege.ljl.pleasedfood.greendao.db.FoodBeanDao;

/**
 * 菜单数据库类
 * 用来将菜单信息写到本地数据库等idus(增删改查)各种操作
 */
public class FoodInfoModel implements FoodInfoModelImp {

    private FoodBeanDao foodBeanDao,foodBeanDao_secret;
    private SQLiteDatabase db,db_secret;
    private DaoSession daoSession,daoSession_secret;
    Context mcontext;
    public FoodInfoModel(Context context){
        mcontext=context;
    }

    void initGreendao(){//创建公开数据库
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(mcontext,"recluse-db",null);
        db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
        foodBeanDao=daoSession.getFoodBeanDao();
    }
    void initGreendao_serect(){//创建私密数据库
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(mcontext,"serect-db",null);
        db_secret=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db_secret);
        daoSession_secret=daoMaster.newSession();
        foodBeanDao_secret=daoSession_secret.getFoodBeanDao();
    }
    /**
     * 插入数据
     * @param foodBean
     */
    @Override
    public void InsertNotetoData(FoodBean foodBean) {
        foodBeanDao.insert(foodBean);
    }
    /**
     * 删除数据
     * @param foodBean
     */
    @Override
    public void DeleteNotefromData(FoodBean foodBean) {
        foodBeanDao.delete(foodBean);
    }
    /**
     * 更新数据
     * @param foodBean
     */
    @Override
    public void ChangeNotetoData(FoodBean foodBean) {
        foodBeanDao.update(foodBean);
    }
}
