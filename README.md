### 本Demo基于Spring Boot构建，实现微信公众号后端开发功能。
### 本项目为WxJava的Demo演示程序，更多Demo请[查阅此处](https://github.com/Wechat-Group/WxJava/blob/master/demo.md)。


## 使用步骤：
1. 请注意，本demo为简化代码编译时加入了lombok支持，如果不了解lombok的话，请先学习下相关知识，比如可以阅读[此文章](https://mp.weixin.qq.com/s/cUc-bUcprycADfNepnSwZQ)；
1. 另外，新手遇到问题，请务必先阅读[【开发文档首页】](https://github.com/Wechat-Group/WxJava/wiki)的常见问题部分，可以少走很多弯路，节省不少时间。
1. 配置：复制 `/src/main/resources/application.yml.template` 或修改其扩展名生成 `application.yml` 文件，根据自己需要填写相关配置（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；
2. 主要配置说明如下：
```
wx:
  mp:
    useRedis: false
    redisConfig:
      host: 127.0.0.1
      port: 6379
    configs:
      - appId: 1111 # 第一个公众号的appid
        secret: 1111 # 公众号的appsecret
        token: 111 # 接口配置里的Token值
        aesKey: 111 # 接口配置里的EncodingAESKey值
      - appId: 2222 # 第二个公众号的appid，以下同上
        secret: 1111
        token: 111
        aesKey: 111
```
3. 运行Java程序：`WxMpDemoApplication`；
4. 配置微信公众号中的接口地址：http://公网可访问域名/wx/portal/xxxxx （注意，xxxxx为对应公众号的appid值）；
5. 根据自己需要修改各个handler的实现，加入自己的业务逻辑。


## 流程

授权登录：

1. 构造网页授权url（WxMenuController -> 创建自定义菜单是跳转授权URL）
   首先构造网页授权url，然后构成超链接让用户点击
   ```
   https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
   ```
   
2. 获得access token（WxRedirectController -> greetUser()）
   当用户同意授权后，会回调所设置的url并把authorization code传过来，然后用这个code获得access token，
   其中也包含用户的openid等信息
   
3. 获得用户基本信息（WxRedirectController -> greetUser()）


创建自定义菜单：

WxMenuController -> menuCreateSample() 设置菜单。


## 说明	

错误代码：
个人公众号暂时不支持认证，对应的接口权限也较少，比如不能创建自定义菜单、不能自动回复等。
个人测试开发可以申请测试号，可使用所有功能；企业账号需要认证。
微信公众平台测试号申请：
https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index

授权登录：
提示：redirect_uri 参数错误
容易产生误区，一直检查redirect_uri参数，很容易忽略一点就是微信后台的配置，
修改[网页授权获取用户基本信息]的回调域名，保持与redirect_uri参数的域名一直，并去掉http://。
