package com.tencent.ai.dobbydemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.ai.tvs.AuthorizeListener;
import com.tencent.ai.tvs.BindingListener;
import com.tencent.ai.tvs.ConstantValues;
import com.tencent.ai.tvs.LoginProxy;
import com.tencent.ai.tvs.business.AlarmBusiness;
import com.tencent.ai.tvs.business.AlarmBusinessDeviceInfo;
import com.tencent.ai.tvs.business.AlarmBusinessInfo;
import com.tencent.ai.tvs.business.EAlarmOpResultType;
import com.tencent.ai.tvs.business.EAlarmOper;
import com.tencent.ai.tvs.business.EAlarmRepeatType;
import com.tencent.ai.tvs.business.UniAccessInfo;
import com.tencent.ai.tvs.comm.CommOpInfo;
import com.tencent.ai.tvs.env.ELocationType;
import com.tencent.ai.tvs.env.ELoginEnv;
import com.tencent.ai.tvs.env.ELoginPlatform;
import com.tencent.ai.tvs.env.EUserAttrType;
import com.tencent.ai.tvs.info.CPMemberManager;
import com.tencent.ai.tvs.info.DeviceCPMemberManager;
import com.tencent.ai.tvs.info.DeviceManager;
import com.tencent.ai.tvs.info.LocManager;
import com.tencent.ai.tvs.info.LocationInfo;
import com.tencent.ai.tvs.info.ProductManager;
import com.tencent.ai.tvs.info.PushInfoManager;
import com.tencent.ai.tvs.info.QQOpenInfoManager;
import com.tencent.ai.tvs.info.UserInfoManager;
import com.tencent.ai.tvs.info.WxInfoManager;
import com.tencent.ai.tvs.miniprogram.EMiniProgType;
import com.tencent.ai.tvs.miniprogram.MiniProgCallback;
import com.tencent.ai.tvs.miniprogram.MiniProgManager;
import com.tencent.ai.tvs.qrauth.QRAuthInfo;
import com.tencent.ai.tvs.qrcode.QRCodeFontUICallback;
import com.tencent.ai.tvs.qrcode.QRCustomViewListener;
import com.tencent.ai.tvs.qrcode.QRStateListener;
import com.tencent.ai.tvs.qrcodesdk.WxQRCodeInfoManager;
import com.tencent.ai.tvs.ui.MotionEventListener;
import com.tencent.ai.tvs.ui.ProxyDataListener;
import com.tencent.ai.tvs.ui.UserCenterStateListener;
import com.tencent.ai.tvs.zxing.util.DDQRCodeFontUICallback;
import com.tencent.ai.tvs.zxing.util.QRKeyEventCallback;
import com.tencent.ai.tvs.zxing.util.DevRelationQRFontUICallback;
import com.tencent.ai.tvs.zxing.util.DingDangQRInfoManager;
import com.tencent.ai.tvs.zxing.util.QRScanResultListener;
import com.tencent.connect.common.Constants;

import org.json.JSONObject;

import java.util.ArrayList;

import SmartService.EAIPushIdType;
import SmartService.EQRCodeState;

public class MainActivity extends AppCompatActivity implements AuthorizeListener, BindingListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String TEST_APPID_WX = "wxdbd76c1af795f58e";
    private static final String TEST_APPSECRET_WX = "723f0aca78996958212e2fdd41ebfc68";
    private static final String TEST_APPID_QQOPEN = "101470979";

    private static final String TEST_QRSDK_APPID_WX = ConstantValues.QRSDK_APPID_WX;

    private static final String TEST_MINIPROG_USERNAME = "gh_8cf389396e29";
    private static final String TEST_MINIPROG_MAIN_PATH = "pages/index/index_poem";
    private static final String TEST_MINIPROG_GRADE_PATH =  "pages/grade/grade";

    private static final long TIME_MILLIS_DELTA = 1000 * 60 * 60;


    private static final String TEST_APPKEY = "7e8ab486-c6f6-4ecc-b52e-7ea8da82c9da";
    private static final String TEST_APPACCEETOKEN = "9cb1fbf4c54442cc80c9aed8cb3c25b6";
    private static final String TEST_GUID = "9cb1fbf4c54442cc80c9aed8cb3c25b6";
    private static final String TEST_PRODUCTID = "2b82efec-a77c-46cd-a2c2-8df9bbd1d1c3:b50fd5a7baa547188516fec1fb5e3348";
    private static final String TEST_DSN = "AC:35:EE:22:DE:28";

    private static final String TEST_DEVOEM = "GGMM";
    private static final String TEST_DEVTYPE = "SPEAKER";

    private static final String TEST_WX_AUTH_RET = "{\"access_token\":\"8_SIkvkPRb_YzKpfLNal5aANjPkZri_eZiOUh7pYuN2dF8liBhVyL9lawYb3Em-S6R1bzw_uPi6W8PMSFHK-DVP5SMR5GE6HsCRHdzuJcTz6s\",\"expires_in\":7200,\"refresh_token\":\"8_umoSNLEEY_u-a48x39m-Ti41sVNe4actHJHYBu07i4e9yv3bS9Fakq0xOlLnhwOSCng3ZnjuRu7v5cXa68Wv9AtiHF-_zOBXjF32dmIVJqI\",\"openid\":\"olW1HwuZs4zRIiTs8xN_5i65DU4Q\",\"scope\":\"snsapi_userinfo\",\"unionid\":\"o9GiTuAkK5sryCobPgdS_iDo1W8A\"}";
    private static final String TEST_WX_REFRESH_RET = "{\"openid\":\"olW1HwuZs4zRIiTs8xN_5i65DU4Q\",\"access_token\":\"8_SIkvkPRb_YzKpfLNal5aANjPkZri_eZiOUh7pYuN2dF8liBhVyL9lawYb3Em-S6R1bzw_uPi6W8PMSFHK-DVP5SMR5GE6HsCRHdzuJcTz6s\",\"expires_in\":7200,\"refresh_token\":\"8_umoSNLEEY_u-a48x39m-Ti41sVNe4actHJHYBu07i4e9yv3bS9Fakq0xOlLnhwOSCng3ZnjuRu7v5cXa68Wv9AtiHF-_zOBXjF32dmIVJqI\",\"scope\":\"snsapi_base,snsapi_userinfo,\"}";
    private static final String TEST_QQOPEN_AUTH_RET = "{\"ret\":0,\"openid\":\"24361BBA12837FFA742C79EC810A6DA8\",\"access_token\":\"B8EC8C0ADD6DEF7F2E380C8DC8CD6451\",\"pay_token\":\"399D4E199447C1D2B51376E6692CC55C\",\"expires_in\":7776000,\"pf\":\"desktop_m_qq-10000144-android-2002-\",\"pfkey\":\"7195cbb183f325e51c93a09d441f9243\",\"msg\":\"\",\"login_cost\":98,\"query_authority_cost\":352,\"authority_cost\":0,\"expires_time\":1530603609881}";
    private static final String TEST_WX_USER_RET = " {\"openid\":\"olW1HwuZs4zRIiTs8xN_5i65DU4Q\",\"nickname\":\"时间段hddjd大喊大叫觉得\",\"sex\":0,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/eM2qvBP8HYxrXdCTlAib2ibmeJw4LYZfJOYsbDShNocXuIUWEnQ1Nwh5Nk5lBjb3LJhOt1r2dt5lHtIAdcUGx7RA\\/132\",\"privilege\":[],\"unionid\":\"o9GiTuAkK5sryCobPgdS_iDo1W8A\"}";
    private static final String TEST_QQOPEN_USER_RET = "{\"ret\":0,\"msg\":\"\",\"is_lost\":0,\"nickname\":\"\",\"gender\":\"男\",\"province\":\"\",\"city\":\"\",\"year\":\"0\",\"figureurl\":\"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/1105886239\\/24361BBA12837FFA742C79EC810A6DA8\\/30\",\"figureurl_1\":\"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/1105886239\\/24361BBA12837FFA742C79EC810A6DA8\\/50\",\"figureurl_2\":\"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/1105886239\\/24361BBA12837FFA742C79EC810A6DA8\\/100\",\"figureurl_qq_1\":\"http:\\/\\/thirdqq.qlogo.cn\\/qqapp\\/1105886239\\/24361BBA12837FFA742C79EC810A6DA8\\/40\",\"figureurl_qq_2\":\"http:\\/\\/thirdqq.qlogo.cn\\/qqapp\\/1105886239\\/24361BBA12837FFA742C79EC810A6DA8\\/100\",\"is_yellow_vip\":\"0\",\"vip\":\"0\",\"yellow_vip_level\":\"0\",\"level\":\"0\",\"is_yellow_year_vip\":\"0\"}";

    private static final String DD_AUTH_ALADE_URL = "dingdang://add_device?oem=alavening";
    private static final String DD_DOWNLOAD_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tencent.ai.dobby";

    private static final String UNIACCESS_DOMAIN_EXAMPLE = "alarm";
    private static final String UNIACCESS_INTENT_EXAMPLE = "cloud_manager";
    private static final String UNIACCESS_JSON_REQ_EXAMPLE = "{\"eType\":0,\"stCloudAlarmReq\":{\"stAccountBaseInfo\":{\"eAcctType\":3,\"strAcctId\":\"o05it0dAhgPPVfQXgXiNveqiLHAA\"}," +
            "\"eCloud_type\":1,\"sPushInfo\":\"\",\"vCloudAlarmData\":[{\"stAIDeviceBaseInfo\":{\"strGuid\":\"9cb1fbf4c54442cc80c9aed8cb3c25b6\",\"strAppKey\":\"2b82efec-a77c-46cd-a2c2-8df9bbd1d1c3\"}," +
            "\"eRepeatType\":1,\"lAlarmId\":1536027722795,\"lStartTimeStamp\":1536069600,\"vRingId\":[\"resource.search$10003\"]}]}}";

    private LoginProxy proxy;

    private WxInfoManager wxInfoManager;
    private QQOpenInfoManager qqOpenInfoManager;

    private RadioGroup netEnvRG;
    private RadioButton testEnvRB, formalEnvRB, exEnvRB;

    private RadioGroup loginfuncRG;
    private RadioButton normalRB, qrRB;

    private Button wxLoginBtn, wxLogoutBtn;
    private Button qqOpenLoginBtn, qqOpenLogoutBtn;
    private Button reportEndStateBtn, toUserCenterWithCallbackBtn, toGetClientIdBtn;
    private Button alarmCreate, alarmDel, alarmUpdate, alarmQuery;
    private long addedAlarmId;

    private TextView reportRelationTextView;

    private EditText getCaptchaEditText;
    private Button getCaptchaButton;
    private TextView getCaptchaTextView;

    private EditText bindPhoneNumberEditText;
    private Button bindPhoneNumberButton;
    private TextView bindPhoneNumberTextView;

    private EditText tvsOpenUrlEditText;
    private Button tvsOpenUrlButton;

    private Button bindLocationButton, queryLocationButton;
    private TextView bindLocationTextView;

    private LinearLayout queryLocationLayout;
    private TextView queryLocationTextView;

    private Button devicebindButton;
    private TextView devicebindTextView;

    private Button deviceunbindButton;
    private TextView deviceunbindTextView;

    private Button getBindDevicesButton;
    private TextView getBindDevicesTextView;

    private Button getBindAccountButton;
    private TextView getBindAccountTextView;

    private Button getMemberStatusButton;
    private TextView getMemberStatusTextView;

    private Button getDeviceStatusButton;
    private TextView getDeviceStatusTextView;

    private Button toSmartLinkButton, toSoftAPButton, toQRLoginButton, toQRSDKLoginButton, toMiniProgramButton;
    private Button ddQRShowButton, ddQRScanButton, ddQRUnbindAndClearButton;
    private Button drQRShowButton, drQRScanButton;
    private Button getBotAISpeechOptionButton, setDeviceAISpeechButton, getDeviceAISpeechButton;
    private Button uniAccessButton;

    private Button qrAuthButton;

    private LinearLayout wxTokenLayout, qqopenTokenLayout;
    private TextView wxATTextView, wxRTTextView, qqopenATTextView;

    private DeviceManager deviceManager;

    private static ELoginPlatform TEST_PLATFORM = ELoginPlatform.WX;

    private boolean isSimpleInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsById();

        registerProxy();

        requestProxyOp();

        showTokenInfo();

        netEnvRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (testEnvRB.getId() == checkedId) {
                    proxy.setLoginEnv(ELoginEnv.TEST);
                    proxy.setUserCenterEnv(ELoginEnv.TEST);
                    proxy.setDDQREnv(ELoginEnv.TEST);
                }
                else if (formalEnvRB.getId() == checkedId) {
                    proxy.setLoginEnv(ELoginEnv.FORMAL);
                    proxy.setUserCenterEnv(ELoginEnv.FORMAL);
                    proxy.setDDQREnv(ELoginEnv.FORMAL);
                }
                else if (exEnvRB.getId() == checkedId) {
                    proxy.setLoginEnv(ELoginEnv.EX);
                    proxy.setUserCenterEnv(ELoginEnv.EX);
                    proxy.setDDQREnv(ELoginEnv.EX);
                }
            }
        });

        loginfuncRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (normalRB.getId() == checkedId) {
                    proxy = LoginProxy.getInstance(TEST_APPID_WX, TEST_APPID_QQOPEN, MainActivity.this);
                }
                else if (qrRB.getId() == checkedId) {
                    proxy = LoginProxy.getInstance(TEST_QRSDK_APPID_WX, TEST_APPID_QQOPEN, MainActivity.this);
                }
            }
        });

        wxLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!proxy.isWXAppInstalled()) {
                    Toast.makeText(MainActivity.this, "WX Not Installed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!proxy.isWXAppSupportAPI()) {
                    Toast.makeText(MainActivity.this, "WX Not SupportAPI", Toast.LENGTH_SHORT).show();
                    return;
                }
                TEST_PLATFORM = ELoginPlatform.WX;
                if (isSimpleInterface) {
                    proxy.tvsAuth(TEST_PLATFORM, TEST_WX_AUTH_RET);
                }
                else {
                    proxy.requestLogin(ELoginPlatform.WX, TEST_PRODUCTID, TEST_DSN, MainActivity.this);
                }
            }
        });

        qqOpenLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TEST_PLATFORM = ELoginPlatform.QQOpen;
                if (isSimpleInterface) {
                    proxy.tvsAuth(TEST_PLATFORM, TEST_QQOPEN_AUTH_RET);
                }
                else {
                    proxy.requestLogin(ELoginPlatform.QQOpen, TEST_PRODUCTID, TEST_DSN, MainActivity.this);
                }
            }
        });

        wxLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.clearToken(ELoginPlatform.WX, MainActivity.this);
            }
        });

        qqOpenLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.clearToken(ELoginPlatform.QQOpen, MainActivity.this);
            }
        });

        toGetClientIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "ClientId = " + proxy.getClientId(TEST_PLATFORM));
            }
        });

        toGetClientIdBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse(DD_AUTH_ALADE_URL));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    intent.setData(Uri.parse(DD_DOWNLOAD_URL));
                    startActivity(intent);
                }
                return true;
            }
        });

        alarmCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmBusiness business = new AlarmBusiness();

                ArrayList<AlarmBusinessInfo> alarmBusinessInfos = new ArrayList<>();
                AlarmBusinessInfo alarmBusinessInfo = getNewAlarmBusinessInfo();
                alarmBusinessInfos.add(alarmBusinessInfo);

                business.alarmBusinessInfos = alarmBusinessInfos;
                business.alarmOper = EAlarmOper.CREATE;

                proxy.requestAlarmManagement(TEST_PLATFORM, business);
            }
        });

        alarmDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmBusiness business = new AlarmBusiness();

                ArrayList<AlarmBusinessInfo> alarmBusinessInfos = new ArrayList<>();
                AlarmBusinessInfo alarmBusinessInfo = new AlarmBusinessInfo();
                alarmBusinessInfo.deviceInfo = getAlarmBusinessDeviceInfo();
                alarmBusinessInfo.alarmId = addedAlarmId;
                alarmBusinessInfos.add(alarmBusinessInfo);

                business.alarmBusinessInfos = alarmBusinessInfos;
                business.alarmOper = EAlarmOper.DELETE;

                proxy.requestAlarmManagement(TEST_PLATFORM, business);
            }
        });

        alarmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmBusiness business = new AlarmBusiness();

                ArrayList<AlarmBusinessInfo> alarmBusinessInfos = new ArrayList<>();
                AlarmBusinessInfo alarmBusinessInfo = new AlarmBusinessInfo();
                alarmBusinessInfo.deviceInfo = getAlarmBusinessDeviceInfo();
                alarmBusinessInfo.alarmId = addedAlarmId;
                alarmBusinessInfo.alarmTime = System.currentTimeMillis() + TIME_MILLIS_DELTA * 2;
                alarmBusinessInfo.note = "New Alarm note";
                alarmBusinessInfo.repeatType = EAlarmRepeatType.DAY;
                alarmBusinessInfos.add(alarmBusinessInfo);

                business.alarmBusinessInfos = alarmBusinessInfos;
                business.alarmOper = EAlarmOper.UPDATE;

                proxy.requestAlarmManagement(TEST_PLATFORM, business);
            }
        });

        alarmQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmBusiness business = new AlarmBusiness();

                ArrayList<AlarmBusinessInfo> alarmBusinessInfos = new ArrayList<>();
                AlarmBusinessInfo alarmBusinessInfo = new AlarmBusinessInfo();
                alarmBusinessInfo.deviceInfo = getAlarmBusinessDeviceInfo();
                alarmBusinessInfos.add(alarmBusinessInfo);

                business.alarmBusinessInfos = alarmBusinessInfos;
                business.alarmOper = EAlarmOper.QUERY;

                proxy.requestAlarmManagement(TEST_PLATFORM, business);
            }
        });

        reportEndStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                deviceManager.deviceOEM = TEST_DEVOEM;
                deviceManager.deviceType = TEST_DEVTYPE;
                deviceManager.guid = "";
                proxy.reportEndState(TEST_PLATFORM, deviceManager);
            }
        });

        toUserCenterWithCallbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                deviceManager.deviceOEM = TEST_DEVOEM;
                deviceManager.deviceType = TEST_DEVTYPE;
                proxy.toUserCenter(EUserAttrType.HOMEPAGE, deviceManager, new UserCenterStateListener() {
                    @Override
                    public void onSuccess(ELoginPlatform platform, int type, CommOpInfo commOpInfo) {
                        switch (type) {
                            case UserCenterStateListener.LOGIN_TYPE:
                                break;
                            case UserCenterStateListener.LOGOUT_TYPE:
                                break;
                        }
                    }

                    @Override
                    public void onError(int type, CommOpInfo commOpInfo) {
                        switch (type) {
                            case UserCenterStateListener.LOGIN_TYPE:
                                break;
                        }
                    }

                    @Override
                    public void onCancel(int type, CommOpInfo commOpInfo) {

                    }
                });
            }
        });

        getCaptchaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.requestGetCaptcha(TEST_PLATFORM, getCaptchaEditText.getText().toString());
            }
        });

        bindPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.requestBindPhoneNumber(TEST_PLATFORM, getCaptchaEditText.getText().toString(), bindPhoneNumberEditText.getText().toString());
            }
        });

        tvsOpenUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvsOpenUrlEditText.getText().toString().equals("")) {
                    tvsOpenUrlEditText.setText("https://ddsdk.html5.qq.com/ugc");
                }
                proxy.tvsOpenUrlWithCallback(tvsOpenUrlEditText.getText().toString(), new UserCenterStateListener() {
                    @Override
                    public void onSuccess(ELoginPlatform platform, int type, CommOpInfo commOpInfo) {

                    }

                    @Override
                    public void onError(int type, CommOpInfo commOpInfo) {

                    }

                    @Override
                    public void onCancel(int type, CommOpInfo commOpInfo) {

                    }
                }, new ProxyDataListener() {
                    @Override
                    public boolean onDataRecv(JSONObject data) {
                        return true;
                    }
                });
            }
        });

        bindLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocManager locManager = LocManager.getInstance();
                LocationInfo homeLocationInfo = locManager.getLocation(ELocationType.HOME);
                homeLocationInfo.addr = "h_addr";
                homeLocationInfo.name = "h_name";
                homeLocationInfo.longitube = "h_longitube";
                homeLocationInfo.latitube = "h_latitube";
                homeLocationInfo.cabAddr = "h_caddr";
                homeLocationInfo.cabName = "h_cname";
                homeLocationInfo.cabLongitube = "h_clongitube";
                homeLocationInfo.cabLatitube = "h_clatitube";
                LocationInfo companyLocationInfo = locManager.getLocation(ELocationType.COMPANY);
                companyLocationInfo.addr = "c_addr";
                companyLocationInfo.name = "c_name";
                companyLocationInfo.longitube = "c_longitube";
                companyLocationInfo.latitube = "c_latitube";
                companyLocationInfo.cabAddr = "c_caddr";
                companyLocationInfo.cabName = "c_cname";
                companyLocationInfo.cabLongitube = "c_clongitube";
                companyLocationInfo.cabLatitube = "c_clatitube";
                if (proxy.isLocationOpValid(TEST_PLATFORM)) {
                    proxy.requestBindLocation(TEST_PLATFORM, homeLocationInfo, companyLocationInfo);
                }
            }
        });

        queryLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proxy.isLocationOpValid(TEST_PLATFORM)) {
                    proxy.requestQueryLocation(TEST_PLATFORM);
                }
            }
        });

        devicebindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushInfoManager pushManager = PushInfoManager.getInstance();
                DeviceManager devManager = new DeviceManager();
                pushManager.idType = EAIPushIdType._ETVSSpeakerIdentifier;
                pushManager.idExtra = ConstantValues.PUSHMGR_IDEXTRA;
                devManager.productId = TEST_PRODUCTID;
                devManager.dsn = TEST_DSN;

                proxy.requestSetPushMapInfoEx(TEST_PLATFORM, pushManager, devManager);
            }
        });

        deviceunbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushInfoManager pushManager = PushInfoManager.getInstance();
                DeviceManager devManager = new DeviceManager();
                pushManager.idType = EAIPushIdType._ETVSSpeakerIdentifier;
                pushManager.idExtra = ConstantValues.PUSHMGR_IDEXTRA;
                devManager.productId = TEST_PRODUCTID;
                devManager.dsn = TEST_DSN;

                proxy.requestDelPushMapInfo(TEST_PLATFORM, pushManager, devManager);
            }
        });

        getBindDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.requestGetPushDeviceInfo(TEST_PLATFORM);
            }
        });

        getBindAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushInfoManager pushManager = PushInfoManager.getInstance();
                DeviceManager devManager = new DeviceManager();
                pushManager.idType = EAIPushIdType._ETVSSpeakerIdentifier;
                pushManager.idExtra = ConstantValues.PUSHMGR_IDEXTRA;
                devManager.productId = TEST_PRODUCTID;
                devManager.dsn = TEST_DSN;
                proxy.requestGetBoundAcctByPushInfo(TEST_PLATFORM, pushManager, devManager);
            }
        });

        getMemberStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                deviceManager.deviceOEM = TEST_DEVOEM;
                deviceManager.deviceType = TEST_DEVTYPE;

                proxy.getMemberStatus(TEST_PLATFORM, deviceManager);
            }
        });

        getDeviceStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                deviceManager.deviceOEM = TEST_DEVOEM;
                deviceManager.deviceType = TEST_DEVTYPE;

                proxy.getDeviceStatus(TEST_PLATFORM, deviceManager);
            }
        });

        toSmartLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SmartLinkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("loginplatform", TEST_PLATFORM.ordinal());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        toSoftAPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoftAPActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("loginplatform", TEST_PLATFORM.ordinal());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        toQRLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginProxy loginProxy = LoginProxy.getWebInstance(null, null, MainActivity.this);
                loginProxy.requestQRLogin(MainActivity.this, qrStateListener, qrCustomViewListener, true, true);
            }
        });

        toQRSDKLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.requestQRSDKLogin(MainActivity.this, qrStateListener, qrCustomViewListener, qrCodeFontUICallback, qrKeyEventCallback);
            }
        });

        toMiniProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiniProgManager mgr = new MiniProgManager();
                mgr.userName = TEST_MINIPROG_USERNAME;
                mgr.path = TEST_MINIPROG_MAIN_PATH;
                mgr.progType = EMiniProgType.PREVIEW.ordinal();
                proxy.tvsOpenMiniProgram(mgr, new MiniProgCallback() {
                    @Override
                    public void onReceiveExtMsg(String msg) {
                        Log.v(LOG_TAG, "onReceiveExtMsg msg = " + msg);
                    }
                });
            }
        });

        ddQRShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                String jsonData = "";
                proxy.requestDingDangQRShow(MainActivity.this, deviceManager, qrStateListener, qrCustomViewListener, ddqrCodeFontUICallback, qrKeyEventCallback, jsonData);
            }
        });

        ddQRScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proxy.requestDingDangQRScan(MainActivity.this, new QRScanResultListener() {
                    @Override
                    public void onSuccess(Object msg) {

                    }

                    @Override
                    public void onCancel(Object msg) {

                    }
                });
            }
        });

        ddQRUnbindAndClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushInfoManager pushManager = PushInfoManager.getInstance();
                DeviceManager devManager = new DeviceManager();
                pushManager.idType = EAIPushIdType._ETVSSpeakerIdentifier;
                pushManager.idExtra = ConstantValues.PUSHMGR_IDEXTRA;
                devManager.productId = TEST_PRODUCTID;
                devManager.dsn = TEST_DSN;

                proxy.requestDelPushMapInfo(TEST_PLATFORM, pushManager, devManager);

                proxy.requestSetQRCodeState(devManager, DingDangQRInfoManager.QRTYPE_BINDING, EQRCodeState._EHASNOTSCAN);
            }
        });

        drQRShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                proxy.requestDevRelationQRShow(MainActivity.this, deviceManager, qrStateListener, qrCustomViewListener, devRelationQRFontUICallback, qrKeyEventCallback);
            }
        });

        drQRScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getBotAISpeechOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                proxy.requestGetBotAISpeechOption(deviceManager);
            }
        });

        setDeviceAISpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                String speechID = "";
                proxy.requestSetDeviceAISpeech(ELoginPlatform.WX, deviceManager, speechID);
            }
        });

        getDeviceAISpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                proxy.requestGetDeviceAISpeech(ELoginPlatform.WX, deviceManager);
            }
        });

        uniAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager deviceManager = new DeviceManager();
                deviceManager.productId = TEST_PRODUCTID;
                deviceManager.dsn = TEST_DSN;
                UniAccessInfo info = new UniAccessInfo();
                info.domain = UNIACCESS_DOMAIN_EXAMPLE;
                info.intent = UNIACCESS_INTENT_EXAMPLE;
                info.jsonBlobInfo = UNIACCESS_JSON_REQ_EXAMPLE;
                proxy.requestTskmUniAccess(ELoginPlatform.WX, deviceManager, info);
            }
        });

        qrAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRAuthInfo info = new QRAuthInfo();
                info.platform = ELoginPlatform.WX;
                info.appId = "wxdbd76c1af795f58e";
                info.openId = "o05it0dAhgPPVfQXgXiNveqiLHAA";
                info.accessToken = "13_EW06jB7kRqfS1fL959cKw0n5zhRhArSoBEKNIzthFAxFmJme8RbcMUFZp0x75Od445yksuSAVpgNgc8_5IOQbZulnjWflHnD74Bbx-ZEzco";
                info.refreshToken = "13_YGiL-Az8WWTU5avwrDXidhU1VDyf7rcpHbCeN4DKqlSKXS1ZNM953yib7STWgMGsKalCeKogCiZpyxtX1d7vBJVWbcY_VuD-WVnoYLXRtJw,21e9289200050a15";
                info.tvsId = "21e9289200050a15";
                info.expireTime = 7200L;
                proxy.requestQRAuth(info,
                        new UserCenterStateListener() {
                            @Override
                            public void onSuccess(ELoginPlatform platform, int type, CommOpInfo commOpInfo) {

                            }

                            @Override
                            public void onError(int type, CommOpInfo commOpInfo) {

                            }

                            @Override
                            public void onCancel(int type, CommOpInfo commOpInfo) {

                            }
                        }, new ProxyDataListener() {
                            @Override
                            public boolean onDataRecv(JSONObject data) {
                                return true;
                            }
                        }, new MotionEventListener() {
                            @Override
                            public void onMotionDown() {

                            }
                        }, true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        proxy.handleCheckQQOpenTokenValid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (RESULT_OK == resultCode) {
                proxy.handleQQOpenIntent(requestCode, resultCode, data);
            }
            else if (RESULT_CANCELED == resultCode){
            }
        }
    }

    @Override
    public void onSuccess(int type, CommOpInfo commOpInfo) {
        Log.v(LOG_TAG, "onSuccess type = " + type + ", commOpInfo = " + commOpInfo);
        switch (type)
        {
            case AuthorizeListener.AUTH_TYPE:
                break;
            case AuthorizeListener.REFRESH_TYPE:
                wxLoginBtn.setEnabled(false);
                break;
            case AuthorizeListener.WX_TVSIDRECV_TYPE:
                wxLoginBtn.setEnabled(false);
                if (isSimpleInterface) {
                    proxy.tvsSetUser(ELoginPlatform.WX, TEST_WX_USER_RET);
                }
                break;
            case AuthorizeListener.QQOPEN_TVSIDRECV_TYPE:
                qqOpenLoginBtn.setEnabled(false);
                if (isSimpleInterface) {
                    proxy.tvsSetUser(ELoginPlatform.QQOpen, TEST_QQOPEN_USER_RET);
                }
                break;
            case AuthorizeListener.TOKENVERIFY_TYPE:
                qqOpenLoginBtn.setEnabled(false);
                break;
            case AuthorizeListener.USERINFORECV_TYPE:
                UserInfoManager mgr = UserInfoManager.getInstance();
                break;
            case AuthorizeListener.WX_VALID_LOGIN_TYPE:
                break;
            case AuthorizeListener.QQOPEN_VALID_LOGIN_TYPE:
                break;
            case BindingListener.GET_CAPTCHA_TYPE:
                getCaptchaTextView.setText("Captcha Send Success");
                break;
            case BindingListener.BIND_PHONENUMBER_TYPE:
                bindPhoneNumberTextView.setText("Bind Success");
                break;
            case BindingListener.BIND_LOCATION_TYPE:
                bindLocationTextView.setText("Bind Success");
                break;
            case BindingListener.QUERY_LOCATION_TYPE:
                LocationInfo homeInfo = LocManager.getInstance().getLocation(ELocationType.HOME);
                LocationInfo companyInfo = LocManager.getInstance().getLocation(ELocationType.COMPANY);
                queryLocationLayout.setVisibility(View.VISIBLE);
                queryLocationTextView.setText("HomeInfo:"+homeInfo.addr+"|"+homeInfo.name+"|"+homeInfo.latitube+"|"+homeInfo.longitube
                        + "|" +homeInfo.cabAddr+"|"+homeInfo.cabName+"|"+homeInfo.cabLatitube+"|"+homeInfo.cabLongitube
                        +"\nCompanyInfo:"+companyInfo.addr+"|"+companyInfo.name+"|"+companyInfo.latitube+"|"+companyInfo.longitube
                        + "|" +companyInfo.cabAddr+"|"+companyInfo.cabName+"|"+companyInfo.cabLatitube+"|"+companyInfo.cabLongitube);
                break;
            case BindingListener.SET_PUSH_MAP_INFOEX_TYPE:
                devicebindTextView.setText("Bind Success Ex");
                break;
            case BindingListener.DEL_PUSH_MAP_INFO_TYPE:
                deviceunbindTextView.setText("Unbind Success");
                break;
            case BindingListener.GET_PUSH_DEVICE_INFO_TYPE:
                getBindDevicesTextView.setText("Devices count = " + ProductManager.getInstance().pushDeviceInfos.size());
                break;
            case BindingListener.GET_BOUND_ACCT_BY_PUSH_INFO_TYPE:
                getBindAccountTextView.setText("OpenId :" + ProductManager.getInstance().aiAcctInfo.strAcctId);
                break;
            case BindingListener.BIND_GET_MEMBER_STATUS_TYPE:
                getMemberStatusTextView.setText("vip=" + CPMemberManager.getInstance().vip + ",sT=" + CPMemberManager.getInstance().startTime +",eT=" + CPMemberManager.getInstance().expireTime);
                break;
            case BindingListener.BIND_GET_DEVICE_STATUS_TYPE:
                getDeviceStatusTextView.setText("sta=" + DeviceCPMemberManager.getInstance().deviceStatus
                        + ",amt=" + DeviceCPMemberManager.getInstance().deviceAmt
                        +",amtU=" + DeviceCPMemberManager.getInstance().deviceAmtUnit);
                break;
            case AuthorizeListener.REPORTENDSTATE_TYPE:
                reportRelationTextView.setText("Succ");
                break;
            case AuthorizeListener.MANAGEACCT_TYPE:
                break;
            case AuthorizeListener.ALARMMANAGEMENT_TYPE:
                AlarmBusiness alarmBusiness = ProductManager.getInstance().alarmBusiness;
                ArrayList<AlarmBusinessInfo> effectBusinessInfos = alarmBusiness.alarmBusinessInfos;
                EAlarmOper alarmOper = alarmBusiness.alarmOper;
                Toast.makeText(MainActivity.this, alarmOper + " Alarms Size = " + effectBusinessInfos.size(), Toast.LENGTH_SHORT).show();
                if (effectBusinessInfos != null && effectBusinessInfos.size() > 0) {
                    AlarmBusinessInfo alarmBusinessInfo = effectBusinessInfos.get(0);
                    Log.v(LOG_TAG, alarmOper + " --- " + alarmBusinessInfo.toString());
                    if (EAlarmOper.CREATE == alarmOper) {
                        addedAlarmId = alarmBusinessInfo.alarmId;
                    }
                }
                Log.v(LOG_TAG, "retCode = " + commOpInfo.retCode);
                if (commOpInfo.retCode == EAlarmOpResultType.NO_ALARM_DATA.getOpResult()) {
                    Log.v(LOG_TAG, "No Alarm Data");
                }
                break;
            case AuthorizeListener.GETQRCODESIG_TYPE:
                Toast.makeText(MainActivity.this, "QRSIG = " + WxQRCodeInfoManager.getInstance().qrcodeSig, Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.SETQRCODE_STATE_TYPE:
                Toast.makeText(MainActivity.this, "setQRCode state success", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.GET_BOT_AI_SPEECH_OPTION_TYPE:
                Toast.makeText(MainActivity.this, "getBotAISpeechOption success", Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, "support size = " + ProductManager.getInstance().supportAISpeechItems.size());
                break;
            case BindingListener.SET_DEVICE_AI_SPEECH_TYPE:
                Toast.makeText(MainActivity.this, "setDeviceAISpeech success", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.GET_DEVICE_AI_SPEECH_TYPE:
                Toast.makeText(MainActivity.this, "getDeviceAISpeech success", Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, "currItem = " + ProductManager.getInstance().currAISpeechItem);
                break;
            case AuthorizeListener.UNIACCESS_TYPE:
                Toast.makeText(MainActivity.this, "getDeviceAISpeech success", Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, "uniAccess Resp = " + commOpInfo.errMsg);
                break;
        }
    }

    @Override
    public void onError(int type, CommOpInfo commOpInfo) {
        Log.v(LOG_TAG, "onError type = " + type + ", commOpInfo = " + commOpInfo);
        switch (type)
        {
            case AuthorizeListener.AUTH_TYPE:
                break;
            case AuthorizeListener.REFRESH_TYPE:
                wxLoginBtn.setEnabled(true);
                break;
            case AuthorizeListener.WX_TVSIDRECV_TYPE:
                wxLoginBtn.setEnabled(true);
                break;
            case AuthorizeListener.QQOPEN_TVSIDRECV_TYPE:
                qqOpenLoginBtn.setEnabled(true);
                break;
            case AuthorizeListener.TOKENVERIFY_TYPE:
                qqOpenLoginBtn.setEnabled(true);
                break;
            case AuthorizeListener.WX_VALID_LOGIN_TYPE:
                Toast.makeText(MainActivity.this, "WX Login InValid", Toast.LENGTH_SHORT).show();
                break;
            case AuthorizeListener.QQOPEN_VALID_LOGIN_TYPE:
                Toast.makeText(MainActivity.this, "QQOpen Login InValid", Toast.LENGTH_SHORT).show();
                break;
            case AuthorizeListener.USERINFORECV_TYPE:
                break;
            case BindingListener.GET_CAPTCHA_TYPE:
                getCaptchaTextView.setText("Captcha Send Error");
                break;
            case BindingListener.BIND_PHONENUMBER_TYPE:
                bindPhoneNumberTextView.setText("Bind Error: wrong captcha");
                break;
            case BindingListener.BIND_LOCATION_TYPE:
                bindLocationTextView.setText("Bind Error");
                break;
            case BindingListener.QUERY_LOCATION_TYPE:
                queryLocationLayout.setVisibility(View.VISIBLE);
                queryLocationTextView.setText("Query Error");
                break;
            case BindingListener.SET_PUSH_MAP_INFOEX_TYPE:
                devicebindTextView.setText("Bind Error Ex");
                break;
            case BindingListener.DEL_PUSH_MAP_INFO_TYPE:
                deviceunbindTextView.setText("Unbind Error");
                break;
            case BindingListener.GET_PUSH_DEVICE_INFO_TYPE:
                getBindDevicesTextView.setText("Get BindDevices Error");
                break;
            case BindingListener.GET_BOUND_ACCT_BY_PUSH_INFO_TYPE:
                getBindAccountTextView.setText("GetBindACCT Error");
                break;
            case BindingListener.BIND_GET_MEMBER_STATUS_TYPE:
                getMemberStatusTextView.setText("Get Member Status Error");
                break;
            case BindingListener.BIND_GET_DEVICE_STATUS_TYPE:
                getDeviceStatusTextView.setText("Get Device Status Error");
                break;
            case AuthorizeListener.REPORTENDSTATE_TYPE:
                reportRelationTextView.setText("Error");
                break;
            case AuthorizeListener.MANAGEACCT_TYPE:
                break;
            case AuthorizeListener.ALARMMANAGEMENT_TYPE:
                Toast.makeText(MainActivity.this, "alarmManagement onError", Toast.LENGTH_SHORT).show();
                break;
            case AuthorizeListener.GETQRCODESIG_TYPE:
                Toast.makeText(MainActivity.this, "getQRCodeSig Error", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.SETQRCODE_STATE_TYPE:
                Toast.makeText(MainActivity.this, "setQRCode state error", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.GET_BOT_AI_SPEECH_OPTION_TYPE:
                Toast.makeText(MainActivity.this, "getBotAISpeechOption onError", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.SET_DEVICE_AI_SPEECH_TYPE:
                Toast.makeText(MainActivity.this, "setDeviceAISpeech onError", Toast.LENGTH_SHORT).show();
                break;
            case BindingListener.GET_DEVICE_AI_SPEECH_TYPE:
                Toast.makeText(MainActivity.this, "getDeviceAISpeech onError", Toast.LENGTH_SHORT).show();
                break;
            case AuthorizeListener.UNIACCESS_TYPE:
                Toast.makeText(MainActivity.this, "requestUniAccess onError", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCancel(int type) {
        switch (type) {
            case AuthorizeListener.AUTH_TYPE:
                break;
        }
    }

    private void findViewsById() {
        netEnvRG = (RadioGroup) findViewById(R.id.netenvrg);
        testEnvRB = (RadioButton) findViewById(R.id.testenvrb);
        formalEnvRB = (RadioButton) findViewById(R.id.formalenvrb);
        exEnvRB = (RadioButton) findViewById(R.id.exenvrb);

        loginfuncRG = (RadioGroup) findViewById(R.id.loginfuncrg);
        normalRB = (RadioButton) findViewById(R.id.normalrb);
        qrRB = (RadioButton) findViewById(R.id.qrrb);

        wxLoginBtn = (Button)findViewById(R.id.wxlogin);
        wxLogoutBtn = (Button)findViewById(R.id.wxlogout);
        reportEndStateBtn = (Button) findViewById(R.id.reportendstatebtn);
        toUserCenterWithCallbackBtn = (Button) findViewById(R.id.tousercenterwithcbbtn);

        reportRelationTextView = (TextView) findViewById(R.id.reportrelationtextview);

        qqOpenLoginBtn = (Button)findViewById(R.id.qqopenlogin);
        qqOpenLogoutBtn = (Button)findViewById(R.id.qqopenlogout);

        toGetClientIdBtn = (Button) findViewById(R.id.getclientidbtn);

        alarmCreate = (Button) findViewById(R.id.alarmcreate);
        alarmDel = (Button) findViewById(R.id.alarmdel);
        alarmUpdate = (Button) findViewById(R.id.alarmupdate);
        alarmQuery = (Button) findViewById(R.id.alarmquery);

        getCaptchaEditText = (EditText) findViewById(R.id.getcaptchaedittext);
        getCaptchaButton = (Button) findViewById(R.id.getcaptchabutton);
        getCaptchaTextView = (TextView) findViewById(R.id.getcaptchatextview);

        bindPhoneNumberEditText = (EditText) findViewById(R.id.bindnumberedittext);
        bindPhoneNumberButton = (Button) findViewById(R.id.bindnumberbutton);
        bindPhoneNumberTextView = (TextView) findViewById(R.id.bindnumbertextview);

        tvsOpenUrlEditText = (EditText) findViewById(R.id.tvsopenurledittext);
        tvsOpenUrlButton = (Button) findViewById(R.id.tvsopenurlbutton);

        bindLocationButton = (Button) findViewById(R.id.bindlocation);
        queryLocationButton = (Button) findViewById(R.id.querylocation);
        bindLocationTextView = (TextView) findViewById(R.id.bindlocationtextview);

        queryLocationLayout = (LinearLayout) findViewById(R.id.querylocationlayout);
        queryLocationTextView = (TextView) findViewById(R.id.querylocationtextview);

        devicebindButton = (Button) findViewById(R.id.devicebindbtn);
        devicebindTextView = (TextView) findViewById(R.id.devicebindtextview);

        deviceunbindButton = (Button) findViewById(R.id.deviceunbindbtn);
        deviceunbindTextView = (TextView) findViewById(R.id.deviceunbindtextview);

        getBindDevicesButton = (Button) findViewById(R.id.getbinddevicesbtn);
        getBindDevicesTextView = (TextView) findViewById(R.id.getbinddevicestext);

        getBindAccountButton = (Button) findViewById(R.id.getbindaccountbtn);
        getBindAccountTextView = (TextView) findViewById(R.id.getbindaccounttext);

        getMemberStatusButton = (Button) findViewById(R.id.getmemberstatusbtn);
        getMemberStatusTextView = (TextView) findViewById(R.id.getmemberstatustext);

        getDeviceStatusButton = (Button) findViewById(R.id.getdevicestatusbtn);
        getDeviceStatusTextView = (TextView) findViewById(R.id.getdevicestatustext);

        toSmartLinkButton = (Button) findViewById(R.id.tosmartlinkbtn);
        toSoftAPButton = (Button) findViewById(R.id.tosoftapbtn);
        toQRLoginButton = (Button) findViewById(R.id.toqrloginbtn);
        toQRSDKLoginButton = (Button) findViewById(R.id.toqrsdkloginbtn);
        toMiniProgramButton = (Button) findViewById(R.id.reqminiprogram);
        ddQRShowButton = (Button) findViewById(R.id.ddqrshowbtn);
        ddQRScanButton = (Button) findViewById(R.id.ddqrscanbtn);
        ddQRUnbindAndClearButton = (Button) findViewById(R.id.ddqrunbindandclear);

        drQRShowButton = (Button) findViewById(R.id.drshowbutton);
        drQRScanButton = (Button) findViewById(R.id.drscanbutton);

        getBotAISpeechOptionButton = (Button) findViewById(R.id.getBotAISpeechOptionButton);
        getDeviceAISpeechButton = (Button) findViewById(R.id.getDeviceAISpeechButton);
        setDeviceAISpeechButton = (Button) findViewById(R.id.setDeviceAISpeechButton);

        uniAccessButton = (Button) findViewById(R.id.uniAccessButton);
        qrAuthButton = (Button) findViewById(R.id.qrauthbtn);

        wxTokenLayout = (LinearLayout) findViewById(R.id.wxtokenlayout);
        wxATTextView = (TextView)findViewById(R.id.accesstokenid);
        wxRTTextView = (TextView)findViewById(R.id.refreshtokenid);

        qqopenTokenLayout = (LinearLayout) findViewById(R.id.qqopentokenlayout);
        qqopenATTextView = (TextView)findViewById(R.id.accesstokenidqqopen);
    }

    private void registerProxy() {
        proxy = LoginProxy.getInstance(TEST_APPID_WX, TEST_APPID_QQOPEN, MainActivity.this);
        wxInfoManager = (WxInfoManager) proxy.getInfoManager(ELoginPlatform.WX);
        qqOpenInfoManager = (QQOpenInfoManager) proxy.getInfoManager(ELoginPlatform.QQOpen);

        proxy.initNetEnv();
        proxy.setOwnActivity(this);


        proxy.setAuthorizeListener(this);
        proxy.setBindingListener(this);

        isSimpleInterface = false;
    }

    private void requestProxyOp() {
        if (proxy.isTokenExist(ELoginPlatform.WX, this)) {
            if (isSimpleInterface) {
                proxy.tvsAuth(ELoginPlatform.WX, TEST_WX_REFRESH_RET);
            }
            else {
                proxy.requestTokenVerify(ELoginPlatform.WX, "productId", "dsn");
            }
        }
        else {
            wxLoginBtn.setEnabled(true);
        }

        if (proxy.isTokenExist(ELoginPlatform.QQOpen, this)) {
            if (isSimpleInterface) {
                proxy.tvsQQOpenVerify(qqOpenInfoManager.appId, qqOpenInfoManager.openID, qqOpenInfoManager.accessToken);
            }
            else {
                proxy.requestTokenVerify(ELoginPlatform.QQOpen, "productId", "dsn");
            }
        }
        else {
            qqOpenLoginBtn.setEnabled(true);
        }
    }

    private void showTokenInfo() {
        if (!"".equals(wxInfoManager.accessToken)) {
            wxTokenLayout.setVisibility(View.VISIBLE);
            wxATTextView.setText("AccessToken:" + wxInfoManager.accessToken);
        }

        if (!"".equals(wxInfoManager.refreshToken)) {
            wxTokenLayout.setVisibility(View.VISIBLE);
            wxRTTextView.setText("RefreshToken:" + wxInfoManager.refreshToken);
        }

        if (!"".equals(qqOpenInfoManager.accessToken)) {
            qqopenTokenLayout.setVisibility(View.VISIBLE);
            qqopenATTextView.setText("AccessToken:" + qqOpenInfoManager.accessToken);
        }
    }

    private AlarmBusinessDeviceInfo getAlarmBusinessDeviceInfo() {
        AlarmBusinessDeviceInfo alarmBusinessDeviceInfo = new AlarmBusinessDeviceInfo();
        alarmBusinessDeviceInfo.productId = TEST_PRODUCTID;
        alarmBusinessDeviceInfo.guid = TEST_GUID;
        return alarmBusinessDeviceInfo;
    }

    private AlarmBusinessInfo getNewAlarmBusinessInfo() {
        AlarmBusinessInfo alarmBusinessInfo = new AlarmBusinessInfo();
        alarmBusinessInfo.deviceInfo = getAlarmBusinessDeviceInfo();
        alarmBusinessInfo.alarmTime = System.currentTimeMillis() + TIME_MILLIS_DELTA;
        alarmBusinessInfo.note = "Alarm note";
        alarmBusinessInfo.repeatType = EAlarmRepeatType.ONCE;
        return alarmBusinessInfo;
    }

    QRStateListener qrStateListener = new QRStateListener() {
        @Override
        public void onSuccess(ELoginPlatform platform, int type, CommOpInfo commOpInfo) {
            if (QRStateListener.LOGIN_TYPE == type) {
                Toast.makeText(MainActivity.this, "QRLogin valid token", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(int type, CommOpInfo commOpInfo) {
            Toast.makeText(MainActivity.this, "QRLogin invalid token", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(int type, CommOpInfo commOpInfo) {

        }
    };

    QRCustomViewListener qrCustomViewListener = new QRCustomViewListener() {

        @Override
        public int customViewVisibility() {
            return View.VISIBLE;
        }

        @Override
        public boolean customViewEnabled() {
            return true;
        }

        @Override
        public String customViewText() {
            return "网络设置";
        }

        @Override
        public void customViewListener() {
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        }

        @Override
        public void customViewLongClickListener() {

        }
    };

    DDQRCodeFontUICallback ddqrCodeFontUICallback = new DDQRCodeFontUICallback() {
        @Override
        public void onFontChange(TextView number1TextView, TextView titleTextView, TextView hintTextView,
                                 TextView number2TextView, TextView title2TextView, TextView hint2TextView,
                                 TextView qrStateTextView, TextView customTextView, TextView refreshTextView) {

        }
    };

    QRKeyEventCallback qrKeyEventCallback = new QRKeyEventCallback() {
        @Override
        public void dispatchKeyEvent(KeyEvent event) {

        }
    };

    DevRelationQRFontUICallback devRelationQRFontUICallback = new DevRelationQRFontUICallback() {
        @Override
        public void onFontChange(TextView titleView, TextView hintTextView, TextView customTextView, TextView refreshTextView) {

        }
    };

    QRCodeFontUICallback qrCodeFontUICallback = new QRCodeFontUICallback() {
        @Override
        public void onFontChange(TextView titleTextView, TextView hintTextView, TextView customTextView, TextView refreshTextView) {

        }
    };
}
