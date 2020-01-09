package com.caldremch.parse;



public final class HttpCode {

    private HttpCode() {
    }

    public static final int OK = 200; //请求已成功，请求所希望的响应头或数据体将随此响应返回
    public static final int CREATED = 201; //请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，且其 URI 已经随Location 头信息返回
    public static final int NO_CONTENT = 204; //服务器成功处理了请求，但不需要返回任何实体内容，并且希望返回更新了的元信息
    public static final int BAD_REQUEST = 400; //语义有误 or 参数有误
    public static final int UNAUTHORIZED = 401; //当前请求需要用户验证  比如需要包含一个请求头信息 Authenticate
    public static final int INTERNAL_SERVER_ERROR = 500; //服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。
    public static final int BAD_GATEWAY = 502; //服务器不支持当前请求所需要的某个功能


}
