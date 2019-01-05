package net.hycollege.ljl.pleasedfood.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import net.hycollege.ljl.pleasedfood.bean.User;
import net.hycollege.ljl.pleasedfood.greendao.db.DaoMaster;
import net.hycollege.ljl.pleasedfood.greendao.db.DaoSession;
import net.hycollege.ljl.pleasedfood.greendao.db.UserDao;
import org.greenrobot.greendao.query.QueryBuilder;
import java.util.Collections;
import java.util.List;

/**
 * 使用数据库
 */
public class UserInfoModel implements UserInfoModelImp {
    private UserDao userBeanDao;
    private SQLiteDatabase db,db_secret;
    private DaoSession daoSession,daoSession_secret;
    Context mcontext;
    public UserInfoModel(Context context){
        mcontext=context;
        initUserdao();
    }
    void initUserdao(){//创建公开数据库
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(mcontext,"user-db",null);
        db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
        userBeanDao=daoSession.getUserDao();
    }
    /**
     * 查询所有信息
     * @return
     */
    @Override
    public List<User> QueryAllinfofromData() {
        List mlist=userBeanDao.loadAll();
        Collections.reverse(mlist);//倒序
        return mlist;
    }
    /**
     * 根据Userinfobean 搜索数据
     * @param search
     * @return
     */
    @Override
    public List<User> getSearchfromState(String search) {
        QueryBuilder<User> queryBuilder=userBeanDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Userinfobean.like("%"+search+"%"))
                .orderAsc(UserDao.Properties.Userinfobean);
        return queryBuilder.list();
    }
    /**
     * 根据Userinfobean 搜索数据
     * @param search
     * @return
     */
    @Override
    public List<User> getSearchfromId(String search) {
        QueryBuilder<User> queryBuilder=userBeanDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Id.like("%"+search+"%"))
                .orderAsc(UserDao.Properties.Id);
        return queryBuilder.list();
    }
    /**
     * 判断数据表是否为空
     * @return
     */
    @Override
    public boolean readDataSizeisEmpty() {
        if (QueryAllinfofromData().size()==0){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 插入数据
     * @param noteBean
     */
    @Override
    public void InsertUserData(User noteBean) {
        userBeanDao.insert(noteBean);
    }
    /**
     * 删除某条数据
     * @param noteBean
     */
    @Override
    public void DeleteNotefromData(User noteBean) {
        userBeanDao.delete(noteBean);
    }
    /**
     * 更新数据
     * @param noteBean
     */
    @Override
    public void ChangeNotetoData(User noteBean) {
        userBeanDao.update(noteBean);
    }
    /**
     * 通过id删除
     * @param id
     */
    @Override
    public void DeleteNotefromDataByid(String id) {
        userBeanDao.deleteByKey(id);
    }
}
