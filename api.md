
@@请求路径 : http://devg.bjpygh.com/Amazon/




错误码列表：


SUCCESS ：0  
SQL_ERROR ： 100   
ILLEGAL_ERROR ： 201  
INVOCATION_TARGET_ERROR ： 202  
HAS_REGISTERED ： 300  
NOT_REGISTERED ： 301  
NO_DATA ： 400;  
SERVER_ERROR ： 203  
PASSWORD_ERROR ： 401  



#### 1.获取短信验证码接口


* path : sendSms  
* 请求方式 : POST  
* 请求参数 :  
  * uPhone : 手机号  
  * isReset : 是否为忘记密码时发送验证码  
* 返回参数 :  
  * {"status":0,"msg":"处理成功","data":"2616"}  

* 失败事例 :  
  * {"status":205401,"msg":"错误的手机号码","data":{}}  

* 错误码  
  * 100 ：查询异常  
  * 205401 : 错误的手机号码  
  * 300 : 该手机号已被注册  
  * 301 ：该手机号没有注册  




#### 2.存入新用户信息接口（注册接口）


* path : insertUser  
* 请求方式 : POST  
* 请求参数 :  
  * uPhone : 用户手机号  
  * uPassword : 用户密码  
  * uName : 用户真实姓名  

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 失败事例 :
```
{"status":100,"msg":"注册失败","data":{}}
```

* 错误码
  * 100 : 注册失败




#### 3.登录接口

* path : login
* 请求方式 : POST
* 请求参数 :
  * uPhone : 用户手机号
  * uPassword : 用户密码

* 返回参数 :
```
{
"status": 0,
"msg": "处理成功",
"data": {
    "uId": 23,                                      //用户ID
    "uRegister": "流水行云",                           //昵称
    "uName": "蒋圆",                                  //真实姓名
    "uSex": "未知",                                   //性别
    "uPhone": "18813069517",                         //绑定手机号
    "uQQ": "1345802063",                          //用户QQ
    "carts": 0                                  //购物车里的物品数量
}
}
```

* 失败事例 :
```
{"status":401,"msg":"密码错误","data":{}}
```

* 错误码
  * 100 : 查询异常
  * 400 : 账户不存在
  * 401 : 密码错误

#### 4.获取商品书籍列表

* path : getBookList
* 请求方式 : GET
* 请求参数 : 无

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":[{"bId":"B0053UHO2E","bName":"教育信息处理(第2版)","bStar":4.5,"bUnitprice":30.1,"bPicture":""},....]}
```

* 错误码
  * 100 : 查询异常
  * 400 : 数据出错

#### 5.通过ID获取单个书籍详细信息

* path : getBook
* 请求方式 : POST
* 请求参数 :
  * bId : 书籍ID标识
* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{"bId":"B0055OQTEC","bIsbn":"9787121135811","bPublish":"电子工业出版社","bName":"3G智能手机创意设计:首届北京市大学生计算机应用大赛获奖作品精选","bAuthorOne":"柳贡慧","bAuthorTwo":"","bAuthorThree":"","bAuthorFour":"","bAuthorFive":"","bLanguage":"简体中文","bFormat":16,"bSize":"23.4 x 18.2 x 1 cm","bWeight":"299 g","bStar":0.0,"bRank":1963684,"bUnitprice":41.0,"bDiscription":"本书是北京市首届大学生计算机应用大赛3G智能手机创意设计实录与获奖作品汇编。书中精选了23个优秀获奖作品，从作品概述到作品功能、特色进行了全面分析。对后续举办此类大赛有现实指导意义。本书是北京市首届大学生计算机应用大赛3G智能手机创意设计实录与获奖作品汇编。书中精选了23个优秀获奖作品，从作品概述到作品功能、特色进行了全面分析。","bStatus":"无货","bType":"计算机与互联网","bPicture":""}}
```

* 失败示例 :
```
{"status":400,"msg":"没有数据","data":{}}
```

* 错误码
  * 100 : 查询异常
  * 400 : 没有数据

#### 6.重置密码接口

* path : resetPass
* 请求方式 : POST
* 请求参数 :
  * uPhone : 手机账号
  * uPassword : 密码

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 重置失败

#### 7.修改用户信息接口

* path : updateUser
* 请求方式 : POST
* 请求参数 :
  * uPhone : 手机账号  不能为空
  * uRegister : 用户昵称
  * uName : 用户真实姓名
  * uQQ : 用户QQ号
  * uSex : 用户性别 String类型 男；女
* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 修改失败

#### 8.添加到购物车

* path : insertCart
* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空
  * bId : 商品ID  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 添加失败

#### 9.添加地址

* path : insertAddress
* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空
  * bIdzipCode : 邮编  不能为空
  * province : 省  不能为空
  * country : 国家  不能为空
  * township : 市区  不能为空
  * street : 路、街  不能为空
  * tNumber : 门牌号  不能为空
  * remarks : 备注地址  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 添加失败

#### 10.下单购买接口

* path : insertOrder
* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空
  * bId : 商品ID 数组 例:bId=B0053UHO2E&bId=B0055OQTEC  不能为空 
  * quantity : 商品对应的数量，顺序要和商品一致 数组  不能为空
  * quantity : 支付方式  不能为空
  * invoiceTitle : 发票抬头  不能为空
  * invoiceType : 发票类型  不能为空
  * deliverFee : 邮费  不能为空
  * aId : 地址ID  不能为空
  * isCart : 是否为购物者内付款：1：是；0：否  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 创建订单失败  /  存入失败
  * 400 : 商品信息出错

#### 11.删除购物车内容

* path : deleteCart
* 请求方式 : POST
* 请求参数 :
  * cId : 购物车ID  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 移除失败

#### 12.删除地址

* path : deleteAddress
* 请求方式 : POST
* 请求参数 :
  * aId : 地址ID  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 删除失败

#### 13.获取订单列表

* path : getOrderList
* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空

* 返回参数 :
```
{
"status": 0,
"msg": "处理成功",
"data": [
    {
        "bussinessId": "201803300025",    //单号
        "oCount": 101.2,                  //订单价格
        "oDate": "2018-03-30",            //订单日期
        "oStatus": "未发货",               //订单状态
        "oDeliver": "快递送货上门",         //运输方式
        "oDeliverFee": 5,                 //运费
        "uPay": "支付宝",                  //支付方式
        "uInvoiceType": "纸质发票",        //发票类型
        "uInvoiceTitle": "蒋圆"           //发票抬头
    }
]
}
```

* 错误码
  * 100 : 查询异常
  * 400 : 没有数据

#### 14.获取订单详细信息

* path : getOrderDetails
* 请求方式 : POST
* 请求参数 :
  * oId : 订单ID  不能为空

* 返回参数 :
```
{
"status": 0,
"msg": "处理成功",
"data": {
    "bussinessId": "201803300025",
    "oCount": 101.2,
    "oDate": "2018-03-30",
    "oStatus": "未发货",
    "oDeliver": "快递送货上门",
    "oDeliverFee": 5,
    "uPay": "支付宝",
    "uInvoiceType": "纸质发票",
    "uInvoiceTitle": "蒋圆",
    "books": [
        {
            "bId": "B0053UHO2E",
            "bName": "教育信息处理(第2版)",   //书籍名称
            "bStar": 4.5,                  //评价指数
            "bUnitprice": 30.1,            //商品价格
            "bPicture": "",                //图片地址
            "quantity": 2                  //购买数量
        }
    ]
}
}
```

* 错误码
  * 100 : 查询异常

#### 15.获取地址信息

* path : getAddress

* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空

* 返回参数 :
```
{
"status": 0,
"msg": "处理成功",
"data": {
    "aId": 14,
    "zipcode": "100101",
    "province": "北京市",
    "country": "中国",
    "township": "北京市海淀区",
    "street": "西四环北路137号院",
    "tNumber": "4号楼1门2层202",
    "remarks": "西木小区4号楼1门2层202"
}
}
```

* 错误码
* 100 : 获取失败
* 400 : 没有地址信息

#### 16.更新地址信息

* path : updateAddress
* 请求方式 : POST
* 请求参数 :
  * uId : 用户ID  不能为空
  * bIdzipCode : 邮编  不能为空
  * province : 省  不能为空
  * country : 国家  不能为空
  * township : 市区  不能为空
  * street : 路、街  不能为空
  * tNumber : 门牌号  不能为空
  * remarks : 备注地址  不能为空

* 返回参数 :
```
{"status":0,"msg":"处理成功","data":{}}
```

* 错误码
  * 100 : 更新失败