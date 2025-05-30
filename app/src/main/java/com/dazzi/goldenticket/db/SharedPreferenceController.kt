
package com.dazzi.goldenticket.db

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController {

    private const val ACCOUNT = "unique_string"

    //유저 정보를 저장한다.
    fun setUserInfo(ctx: Context, userData: com.dazzi.goldenticket.data.UserData) {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_name", userData.name)
        editor.putString("u_idx", userData.user_idx)
        editor.putString("u_email", userData.email)
        editor.putString("u_token", userData.token)
        editor.putString("u_phone",userData.phone)
        editor.apply()
    }

    //유저 정보를 보내준다.


    fun getUserName(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_name","")
    }
    fun getUserEmail(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_email","")
    }
    fun getUserToken(ctx: Context): String {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_token","")!!
    }
    fun getUserPhone(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_phone","")
    }


    //유저 정보를 해제한다. (로그아웃)
    fun clearUserToken(ctx: Context) {
        val preference: SharedPreferences = ctx.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.clear()
        editor.apply()
    }

}