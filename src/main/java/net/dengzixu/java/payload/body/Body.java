package net.dengzixu.java.payload.body;

public class Body {
    private String type;
    private Class<?> bodyClass;
    private Object body;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class<?> getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(Class<?> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
