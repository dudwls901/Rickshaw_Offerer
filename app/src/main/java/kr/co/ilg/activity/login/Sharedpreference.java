package kr.co.ilg.activity.login;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference {
    //public static final String PREFERENCES_NAME = "managerinfo";

    private static SharedPreferences getPreferences(String name, Context context) {

        return context.getSharedPreferences(name, Context.MODE_PRIVATE);

    }
    public static void set_state(Context context, String key, Boolean value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }
    public static Boolean get_state(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        Boolean value = prefs.getBoolean(key, false);
        return value;
    }
    public static void clear(Context context,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }
    public static void set_id(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_id(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 회원번호 이메일 저장
    public static void set_pw(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_pw(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 회원번호 이메일 저장

    public static void set_business_reg_num(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_business_reg_num(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 회원번호 이메일 저장

    public static void set_manager_pw(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String get_manager_pw(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 일반이메일 저장
    public static void set_manager_represent_name(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_manager_represent_name(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 비밀번호 이메일 저장

    public static void set_manager_office_name(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_office_name(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 닉네임 저장
    public static void set_manager_office_telnum(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_office_telnum(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 생년월일 저장

    public static void set_manager_office_address(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_manager_office_address(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 성별 저장

    public static void set_manager_name(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_name(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 휴대전화번호 저장

    public static void set_local_code(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_local_code(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 은행계좌 저장
    public static void set_manager_phonenum(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_phonenum(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 은행이름 저장
    public static void set_local_sido(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_local_sido(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 한줄소개 저장
    public static void set_local_sigugun(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_local_sigugun(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 한줄소개 저장


    public static void set_manager_bankname(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_bankname(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 한줄소개 저장
    public static void set_kakaoemail(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_kakaoemail(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 한줄소개 저장
    public static void set_manager_bankaccount(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_bankaccount(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 한줄소개 저장
    public static void set_manager_office_info(Context context, String key, String value,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_manager_office_info(Context context, String key,String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "managerinfo");
        return value;
    } // 한줄소개 저장


}
