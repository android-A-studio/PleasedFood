package net.hycollege.ljl.pleasedfood.utils;

import android.content.Context;
import android.widget.Toast;

import net.hycollege.ljl.pleasedfood.MainActivity;
import net.hycollege.ljl.pleasedfood.view.RegistTransition.AActivityTwo;

/**
 * 这是一个用户注册界面
 * 是否正确输入信息工具类,
 */
public class PasswordTool {
    /**
     * 判断用户注册界面是否正确输入信息
     * @param user        用户名
     * @param pass        密码1，不能全部是数字2，不能全部是字母3，必须是数字或字母
     *                    正则表达式:^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$
     * @param phnum       电话
     * @param userIsExist 用户是否存在标识符,view包下RegistActivity类有定义
     * @return
     */
    public static boolean isSetUserInfo(Context context,String user, String pass, String phnum, String userIsExist) {
        if (user.equals("") || pass.equals("") || phnum.equals("") || userIsExist.equals("true")) {
            return false;
        }
        //正则表达式
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!pass.matches(regex)) {
            Toast.makeText(context,"六位密码数字+字母",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 判断用户注册界面是否正确输入信息
     *
     * @param user 用户名
     * @param pass 密码
     * @return
     */
    public static boolean isSetUserInfo(String user, String pass) {
        if (user.equals("") || pass.equals("")) {
            return false;
        }
        if (pass.length() < 6 && pass.length() > 18) {
            return false;
        }
        return true;
    }
}
