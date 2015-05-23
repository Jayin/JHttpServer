package jhttpserver.entity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * HTTP Cookie
 *
 * 详细定义 :http://tools.ietf.org/html/rfc6265#section-4.1.1
 *
 * NOTE:
 * 1. 虽然提供expires(Date)来设置过期时间，但是我只提供设置max-age的借口来设置
 *    原因：https://github.com/Jayin/JHttpServer/issues/9
 */
public class Cookie {

    private String field;
    private String value;
    private String domain;
    private String path;
    private boolean secure;
    private Date expires;
    private long maxAge;//非0,单位秒
    private boolean httpOnly;

    public Cookie(String field,String value){
        this(field,value,"","/",null,0,false,false);
    }

    public Cookie(String field,String value,Date expires){
        this(field,value,"","/",expires,0,false,false);
    }

    public Cookie(String field,String value,Date expires,boolean secure,boolean httpOnly){
        this(field,value,"","/",null,0,secure,httpOnly);
    }

    public Cookie(String field,String value,long maxAge){
        this(field,value,"","/",null,maxAge,false,false);
    }

    public Cookie(String field,String value,long maxAge,boolean secure,boolean httpOnly){
        this(field,value,"","/",null,0,secure,httpOnly);
    }


    private Cookie(String field,String value,String domain,String path,Date expires,long maxAge,boolean secure,boolean httpOnly){
        this.field  = field;
        this.value = value;
        this.domain = domain;
        this.path = path;
        if(expires !=null) this.expires = expires;
//        if(maxAge >= 0){
            this.maxAge = maxAge;
//        }else{
//            this.maxAge = 0;
//        }
        this.secure = secure;
        this.httpOnly = httpOnly;
    }



    public Header toHeader(){
        return new Header("Set-Cookie",this.toString());
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        return field + "=" + value
                + (domain.isEmpty() ? "" : "; Domain=" + domain)
                + (path.isEmpty() ?   "" : "; path=" + path)
                + (expires==null ?  "" : "; Expires=" + sdf.format(expires))
                + (maxAge ==0 ? "" : "; Max-Age=" + maxAge)
                + (!secure ? "" : ";Secure")
                + (!httpOnly ? "" : ";HttpOnly");
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
