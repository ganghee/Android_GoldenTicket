package com.dazzi.goldenticket.activity

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.post.PostSignupResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

@Suppress("CAST_NEVER_SUCCEEDS")
class SignUpActivity : AppCompatActivity() {

    //이메일 형식 정규화
    val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    // 비밀번호 4자리 ~ 16자리까지 가능
    val VALID_PASSWOLD_REGEX_ALPHA_NUM: Pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$")

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        //포커스가 되거나 문자가 있을 때 밑줄이 활성화
        isDataVaild()

        //회원가입 버튼 이벤트
        //데이터가 옳바로 입력되었는지 확인한다.( 이메일형식, 비밀번호 형식)
        //2개의 비밀번호 입력이 일치하는지 확인한다.
        //true이면 서버에 회원정보가 저장이 된다.
        //저장이 완료되면 로그인 창으로 넘어간다.
        btn_signactivity_sign.setOnClickListener {
            val signupUEmail: String = et_signupactivity_email.text.toString()
            val signupUPw: String = et_signupactivity_pw.text.toString()
            val signupUPw2: String = et_signupactivity_pw2.text.toString()
            val signupUName: String = et_signupactivity_name.text.toString()
            val signupPhone: String = et_signupactivity_phone.text.toString()

            val signupAlarmEvent: Boolean = cb_event.isChecked
            val signupAlarmLike: Boolean = cb_like.isChecked
            val signupAlarmConfirm: Boolean = cb_confirm.isChecked

            //true인 경우 서버에 회원정보를 저장한다.
            if (isUserInfoValid(signupUName, signupPhone, signupUEmail, signupUPw, signupUPw2)) {
                //서버와의 연결
                postSignupResponse(
                    signupUEmail,
                    signupUPw,
                    signupUName,
                    signupPhone,
                    signupAlarmEvent,
                    signupAlarmLike,
                    signupAlarmConfirm
                )
            }


        }

        //뒤로가기 이미지를 입력하였을 때 로그인 창으로 넘어감
        iv_signactivity_back.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }


        //이메일로 로그인 버튼을 눌렀을 때 로그인 화면으로 넘어감
        tv_signactivity_login.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val signupUEmail: String = et_signupactivity_email.text.toString()
        val signupUPw: String = et_signupactivity_pw.text.toString()
        val signupUPw2: String = et_signupactivity_pw2.text.toString()
        val signupUName: String = et_signupactivity_name.text.toString()
        val signupPhone: String = et_signupactivity_phone.text.toString()
        setContentView(R.layout.activity_sign_up)

        et_signupactivity_email.text = signupUEmail as Editable
        et_signupactivity_pw.text = signupUPw as Editable
        et_signupactivity_pw2.text = signupUPw2 as Editable
        et_signupactivity_name.text = signupUName as Editable
        et_signupactivity_phone.text = signupPhone as Editable


    }

    //빈 문자열인지 확인 -> 형식이 맞는지 확인
    private fun isUserInfoValid(
        u_name: String,
        u_phone: String,
        u_email: String,
        u_pw: String,
        u_pw2: String
    ): Boolean {
        if (u_name == "") {
            toast("이름을 입력하세요")
            et_signupactivity_name.requestFocus()
        } else if (u_email == "") {
            toast("이메일을 입력하세요")
            et_signupactivity_email.requestFocus()
        } else if (u_phone == "") {
            toast("핸드폰을 입력하세요")
            et_signupactivity_phone.requestFocus()
        } else if (u_pw == "") {
            toast("비밀번호를 입력하세요")
            et_signupactivity_pw.requestFocus()
        } else if (u_pw2 == "") {
            toast("비밀번호 확인을 입력하세요")
            et_signupactivity_pw2.requestFocus()
        } else if (!validateEmail(u_email)) {
            toast("이메일 형식이 아닙니다")
            et_signupactivity_email.requestFocus()
        } else if (!validatePassword(u_pw)) {
            toast("패스워드 형식이 아닙니다")
            et_signupactivity_pw.requestFocus()
        } else if (u_pw != u_pw2) {
            toast("두 패스워드가 다릅니다")
            et_signupactivity_pw2.requestFocus()
        } else
            return true
        return false
    }

    //아이디와 패스워드가 포커스가 되면 밑줄
    //아이디와 패스워드에 문자가 있으면 밑줄
    private fun isDataVaild() {
        et_signupactivity_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_name.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_email.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_email.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_phone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_phone.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_pw.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_pw.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
        et_signupactivity_pw2.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || et_signupactivity_pw2.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_white)
        }
    }

    //패스워드 형식인지 유효성 검사
    private fun validatePassword(pwStr: String): Boolean {
        val matcher: Matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr)
        return matcher.matches()
    }

    //이메일 형식인지 유효성 검사
    private fun validateEmail(emailStr: String): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }


    //서버에 회원 가입 정보를 저장한다.
    private fun postSignupResponse(
        u_id: String,
        u_pw: String,
        u_name: String,
        u_phone: String,
        alarm_event: Boolean,
        alarm_like: Boolean,
        alarm_confirm: Boolean
    ) {

        //id,password,name 데이터를 받아서 JSON 객체로 만든다.
        val jsonObject = JSONObject()
        jsonObject.put("email", u_id)
        jsonObject.put("password", u_pw)
        jsonObject.put("name", u_name)
        jsonObject.put("phone", u_phone)
        jsonObject.put("alarm_event", alarm_event)
        jsonObject.put("alarm_like", alarm_like)
        jsonObject.put("alarm_confirm", alarm_confirm)

        //gsonObject는 body로 들어간다.
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignupResponse: Call<PostSignupResponse> =
            networkService.postSignupResponse("application/json", gsonObject)
        postSignupResponse.enqueue(object : Callback<PostSignupResponse> {
            override fun onFailure(call: Call<PostSignupResponse>, t: Throwable) {
                Log.e("Login failed", t.toString())
            }

            override fun onResponse(call: Call<PostSignupResponse>, response: Response<PostSignupResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 200) {
                        //Resquest Signup
                        startActivity<LoginActivity>()
                        finish()
                    }
                }
            }

        })
    }
}