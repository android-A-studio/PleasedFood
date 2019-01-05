package net.hycollege.ljl.pleasedfood.Model;

import net.hycollege.ljl.pleasedfood.bean.User;

import java.util.List;

public interface UserInfoModelImp {
    List<User> QueryAllinfofromData();
    List<User> getSearchfromState(String search);

    List<User> getSearchfromId(String search);

    boolean readDataSizeisEmpty();

    void InsertUserData(User userBean);
    void DeleteNotefromData(User userBean);
    void ChangeNotetoData(User userBean);
    void DeleteNotefromDataByid(String id);
}
