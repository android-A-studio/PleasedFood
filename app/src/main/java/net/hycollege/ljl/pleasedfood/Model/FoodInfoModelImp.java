package net.hycollege.ljl.pleasedfood.Model;

import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.bean.User;

public interface FoodInfoModelImp {
    void InsertNotetoData(FoodBean foodBean);
    void DeleteNotefromData(FoodBean foodBean);
    void ChangeNotetoData(FoodBean foodBean);
}
