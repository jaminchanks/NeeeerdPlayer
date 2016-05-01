package test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.jamin.neeeerdplayer.ui.user.login.UserLoginActivity;
import com.robotium.solo.Solo;

import java.util.Objects;

/**
 * Created by jamin on 16-4-7.
 */
public class LoginTest  extends ActivityInstrumentationTestCase2<UserLoginActivity> {

    private Solo solo;

     public LoginTest() {
        super(UserLoginActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }



    public void testLogin() {
        solo.unlockScreen();
    }

//    public void testInputChatPostMessage() {
//        enterTextById("et_user_login_account", "xiaoming", 100);
//        enterTextById("et_user_login__password", "123", 100);
//        clickCtrlById("bnt_user_login_submit", 100);
//    }




    private int clickCtrlById(String s, int t){
        int ctrl;
        View v;
        if(Objects.equals(s, "")){
            return -1;
        }
        ctrl = solo.getCurrentActivity().getResources().getIdentifier(s,"id",solo.getCurrentActivity().getPackageName());
        v = solo.getView(ctrl);
        solo.clickOnView(v);
        solo.sleep(t);
        return 0;
    }
    private int enterTextById(String id, String s, int t ){
        int ctrl;
        EditText v;
        if(Objects.equals(s, "")){
            return -1;
        }
        ctrl = solo.getCurrentActivity().getResources().getIdentifier(id,"id",solo.getCurrentActivity().getPackageName());
        v = (EditText) solo.getView(ctrl);
        solo.enterText(v, s) ;
        solo.sleep(t);
        return 0;
    }


    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
