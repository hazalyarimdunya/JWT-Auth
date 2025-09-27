package com.hazalyarimdunya.auth_jwt_app.bean;

abstract public class BeanAllMethod {

    /** Bean oluşturulduğunda çalışacak metod*/
    abstract  public void onInit();

    /**Bean yok edilmeden önce çalışacak metod*/
    abstract  public void onDestroy();
}
