//
//  TVSAccountSDK.h
//  TVSAccountSDK
//
//  Created by RincLiu on 21/08/2017.
//  Copyright © 2017 tencent. All rights reserved.
//

/*!
 * @file TVSAccount.h
 */
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>



/*!
 * @brief TVS 账号类型
 */
typedef NS_ENUM(NSInteger,TVSAccountType) {
    /*!
     * @brief
     */
    TVSAccountTypeQQNumber,
    
    /*!
     * @brief
     */
    TVSAccountTypeQQOpenId,
    
    /*!
     * @brief
     */
    TVSAccountTypeWechatOpenId,
    
    /*!
     * @brief
     */
    TVSAccountTypePhoneNumber,
    
    /*!
     * @brief
     */
    TVSAccountTypeQbId,
    
    /*!
     * @brief
     */
    TVSAccountTypeIOSVistor,
    
    /*!
     * @brief
     */
    TVSAccountTypeUnknown
};



/*!
 * @brief 非法的 ClientId 常量
 */
extern NSString* const TVSInvalidClientId;

/*!
 * @brief 非法的 RefreshToken 常量
 */
extern NSString* const TVSInvalidRefreshToken;



/*!
 * @class TVSAccountInfo
 * @brief 账号信息
 */
@interface TVSAccountInfo : NSObject

/*!
 * @brief id类型
 */
@property(nonatomic,assign) TVSAccountType accountType;

/*!
 * @brief 微信/QQ登录的 openID
 */
@property(nonatomic,copy) NSString* appId;

/*!
 * @brief 微信/QQ登录的 openID
 */
@property(nonatomic,copy) NSString* openId;

/*!
 * @brief 微信/QQ登录的 accessToken
 */
@property(nonatomic,copy) NSString* accessToken;

/*!
 * @brief 微信登录的 refreshToken
 */
@property(nonatomic,copy) NSString* refreshToken;

/*!
 * @brief  微信登录的 unionId
 */
@property(nonatomic,assign) NSString *unionId;

/*!
 * @brief 微信/QQ token 的过期时间
 */
@property(nonatomic,assign) NSInteger expireTime;

/*!
 * @brief TVS 平台返回的 tvsId
 */
@property(nonatomic,copy) NSString* tvsId;

/*!
 * @brief userid
 */
@property(nonatomic,copy) NSString* userId;

/*!
 * @brief 获取 TVS ClientId
 * @warning 必须确保已登录，并且 dsn 和 productId 不为空
 * @param dsn 设备序列号
 * @param productId TVS平台申请的产品ID
 * @return TVS ClientId
 */
+(NSString*)clientIdWithDSN:(NSString*)dsn productId:(NSString*)productId;

@end



/*!
 * @class TVSGeoLocation
 * @brief 位置信息
 */
@interface TVSGeoLocation : NSObject <NSCoding>

/*!
 * @brief 名称
 */
@property (nonatomic,copy) NSString* name;

/*!
 * @brief 地址
 */
@property (nonatomic,copy) NSString* addr;

/*!
 * @brief 经度
 */
@property (nonatomic,copy) NSString* lng;

/*!
 * @brief 纬度
 */
@property (nonatomic,copy) NSString* lat;

/*!
 * @brief (打车点)名称
 */
@property (nonatomic,copy) NSString* cabName;

/*!
 * @brief (打车点)地址
 */
@property (nonatomic,copy) NSString* cabAddr;

/*!
 * @brief (打车点)经度
 */
@property (nonatomic,copy) NSString* cabLng;

/*!
 * @brief (打车点)纬度
 */
@property (nonatomic,copy) NSString* cabLat;

@end



/*!
 * @class TVSUserInfo
 * @brief 用户信息
 */
@interface TVSUserInfo : NSObject

/*!
 * @brief id类型
 */
@property(nonatomic,assign) TVSAccountType idType;

/*!
 * @brief 昵称
 */
@property(nonatomic,copy) NSString* nickName;

/*!
 * @brief 头像
 */
@property(nonatomic,copy) NSString* headImgUrl;

/*!
 * @brief 性别（0：女，1：男）
 */
@property(nonatomic,assign) NSInteger sex;

/*!
 * @brief 手机号
 */
@property(nonatomic,copy) NSString* phoneNumber;

/*!
 * @brief 家庭地址
 */
@property(nonatomic,strong) TVSGeoLocation*  homeLocation;

/*!
 * @brief 公司地址
 */
@property(nonatomic,strong) TVSGeoLocation* companyLocation;

@end



/*!
 * @class TVSAuth
 * @brief TVS 账号授权接口
 */
@interface TVSAccount : NSObject

/*!
 * @brief 获得 TVS 授权帮助类单例对象
 * @return TVS 授权帮助类实例
 */
+(instancetype)shared;

/*!
 * @brief 初始化 (自动从 Info.plist 读取相关参数)
 * @warning 必须在 AppDelegate 的 application:didFinishLaunchingWithOptions: 方法中调用
 */
-(void)registerApp;

/*!
 * @brief 处理 URL 跳转
 * @warning 必须在 AppDelegate 的 application:handleOpenURL: 和 application:openURL:sourceApplication:annotation: 两个方法中调用
 * @param url 待处理的 URL
 * @return 是否成功处理相关 URL 跳转
 */
-(BOOL)handleOpenUrl:(NSURL*)url;

/*!
 * @brief 检查微信 Token 是否存在
 * @return 是否存在
 */
-(BOOL)isWXTokenExist;

/*!
 * @brief 检查 QQ Token 是否存在
 * @return 是否存在
 */
-(BOOL)isQQTokenExist;

/*!
 * @brief 微信登录
 * @warning 如果微信 token 不存在，则必须调用此方法，以获得 TVS 后台返回的相关账户信息
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)wxLoginWithHandler:(void(^)(BOOL))handler;

/*!
 * @brief 微信登录(支持未安装微信的情况)
 * @warning 如果微信 token 不存在，则必须调用此方法，以获得 TVS 后台返回的相关账户信息
 * @@param viewController 发起微信登录的界面
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)wxLoginWithViewController:(UIViewController*)vc handler:(void(^)(BOOL))handler;

/*!
 * @brief 微信支付(NativeSDK方式)
 * @warning 注意后台生成的订单类型必须是app支付，不能是h5订单，否则微信会报错“支付场景非法”；并且必须保证后台订单的appId和客户端微信sdk的appid一致
 * @param appId (后台生成的微信支付订单)appid
 * @param partnerid (后台生成的微信支付订单)partnerid
 * @param prepayid (后台生成的微信支付订单)prepayid
 * @param package (后台生成的微信支付订单)package
 * @param noncestr (后台生成的微信支付订单)noncestr
 * @param sign (后台生成的微信支付订单)sign
 * @param timestamp (后台生成的微信支付订单)timestamp
 * @param handler 回调，BOOL 值表示是否成功，NSString 值为微信支付返回的 key
 */
-(void)wxPayWithAppId:(NSString*)appId partnerid:(NSString*)partnerid prepayid:(NSString*)prepayid package:(NSString*)package noncestr:(NSString*)noncestr sign:(NSString*)sign timestamp:(UInt32)timestamp handler:(void(^)(BOOL,NSString*))handler;

/*!
 * @brief 微信支付(openURL方式)
 * @warning 注意后台生成的订单类型必须是app支付，不能是h5订单，否则微信会报错“支付场景非法”
 * @param appId (后台生成的微信支付订单)appid
 * @param partnerid (后台生成的微信支付订单)partnerid
 * @param prepayid (后台生成的微信支付订单)prepayid
 * @param package (后台生成的微信支付订单)package
 * @param noncestr (后台生成的微信支付订单)noncestr
 * @param sign (后台生成的微信支付订单)sign
 * @param timestamp (后台生成的微信支付订单)timestamp
 */
-(void)wxPayWithAppId:(NSString*)appId partnerid:(NSString*)partnerid prepayid:(NSString*)prepayid package:(NSString*)package noncestr:(NSString*)noncestr sign:(NSString*)sign timestamp:(UInt32)timestamp;

/*!
 * @brief 检测微信是否安装，版本是否支持
 * @param alert 是否提示
 * @return 安装且版本支持返回 YES
 */
-(BOOL)checkWXAppWithAlert:(BOOL)alert;

/*!
 * @brief QQ 登录
 * @warning 如果 QQ token 不存在，则必须调用此方法，以获得 TVS 后台返回的相关账户信息
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)qqLoginWithHandler:(void(^)(BOOL))handler;

/*!
 * @brief 刷新微信 Token
 * @warning 如果微信 token 存在，则必须调用此方法，以获得(更新) TVS 后台返回的相关账户信息
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)wxTokenRefreshWithHandler:(void(^)(BOOL))handler;

/*!
 * @brief 验证 QQ Token
 * @warning 如果 QQ token 存在，则必须调用此方法，以获得(更新) TVS 后台返回的相关账户信息
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)qqTokenVerifyWithHandler:(void(^)(BOOL))handler;

/*!
 * @brief 通过微信/QQ 账号信息直接到 TVS 后台授权（换取tvsId）
 * @warning 仅针对之前已经独自接入 QQ/微信 SDK，且自己维护 token 过期刷新的场景！!
 * @param openId QQ/微信登录后返回的 openId
 * @param accessToken QQ/微信登录后返回的 accessToken
 * @param isWX 是否微信登录
 * @param handler 回调，BOOL 值表示是否成功，成功后即可通过 TVSAccountInfo 读取 tvsId
 */
-(void)tvsAuthWithOpenId:(NSString*)openId accessToken:(NSString*)accessToken isWX:(BOOL)isWX handler:(void(^)(BOOL))handler;

/*!
 * @brief 验证 QQ token
 * @warning 仅针对之前已经独自接入 QQ/微信 SDK，且自己维护 token 过期刷新的场景！!
 * @param openId QQ/微信登录后返回的 openId
 * @param accessToken QQ/微信登录后返回的 accessToken
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)verifyQQTokenWithOpenId:(NSString*)openId accessToken:(NSString*)accessToken handler:(void(^)(BOOL))handler;

/*!
 * @brief 保存微信/QQ登录返回的用户资料信息
 * @warning 仅针对之前已经独自接入 QQ/微信 SDK，且自己维护 token 过期刷新的场景！！
 * @param json 微信/QQ登录返回的用户资料信息JSON
 * @return 是否保存成功
 */
-(BOOL)setUserProfileJson:(NSString*)json;

/*!
 * @brief 开始微信二维码登录
 * @param imageHandler 获取到二维码回调
 * @param scannedHandler 二维码被扫描回调
 * @param finishHandler 完成授权回调，BOOL参数表示是否成功
 * @return 是否请求成功
 */
-(BOOL)startWXQRCodeAuthWithImageHandler:(void(^)(UIImage*))imageHandler scannedHandler:(void(^)(void))scannedHandler finishHandler:(void(^)(BOOL))finishHandler;

/*!
 * @brief 停止微信二维码登录
 * @return 是否成功
 */
-(BOOL)stopWXQRCodeAuth;

/*!
 * @brief 获取账号信息
 * @warning 必须在已登录状态调用
 * @return accountInfo
 */
-(TVSAccountInfo*)accountInfo;

/*!
 * @brief 获取用户信息
 * @warning 必须在已登录状态调用
 * @return userInfo
 */
-(TVSUserInfo*)userInfo;

/*!
 * @brief 注销登录
 */
-(void)logout;

/*!
 * @brief 获取用于绑定手机号的验证码
 * @warning 必须确保已登录
 * @param phoneNumber 手机号
 * @param handler 回调
 */
-(void)getCaptchaWithPhoneNumber:(NSString*)phoneNumber handler:(void(^)(BOOL))handler;

/*!
 * @brief 绑定手机号
 * @warning 必须确保已登录
 * @param phoneNumber 手机号
 * @param captcha 验证码
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)bindPhoneNumber:(NSString*)phoneNumber captcha:(NSString*)captcha handler:(void(^)(BOOL))handler;

/*!
 * @brief 绑定地址
 * @warning 必须确保已登录
 * @param homeLoc 家庭地址
 * @param companyLoc 公司地址
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)bindHomeLocation:(TVSGeoLocation*)homeLoc companyLocation:(TVSGeoLocation*)companyLoc handler:(void(^)(BOOL))handler;

/*!
 * @brief 查询地址
 * @warning 必须确保已登录
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)queryLocationWithHandler:(void(^)(NSArray*,NSArray*))handler;

/*!
 * @brief 设置用户自定义信息
 * @warning 必须确保已登录
 * @param nickName 昵称
 * @param avatarUrl 头像url
 * @param handler 回调，BOOL 值表示是否成功
 */
-(void)setCustomUserInfoWithNickName:(NSString*)nickName avatarUrl:(NSString*)avatarUrl handler:(void(^)(BOOL))handler;

@end
