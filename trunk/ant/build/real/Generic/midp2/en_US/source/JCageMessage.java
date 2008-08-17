// package dev.di.data.dao;

/**
 * Created by IntelliJ IDEA.
 * User: Sumithi
 * Date: Dec 5, 2007
 * Time: 7:48:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class JCageMessage extends AbstractDao{

    String id="";
    String target="";
    String subject="";
    String type="";
    String source="";
    String text="";

    public JCageMessage(String id, String target, String subject, String type, String source, String text){
        this.id = id;
        this.target = target;
        this.subject = subject;
        this.type = type;
        this.source = source;
        this.text = text;
    }

    public JCageMessage(String fullStr){

        numOfAttribs=6;
        String[] s=getAttributes(fullStr);
        id = s[0];
        target = s[1];
        subject = s[2];
        type = s[3];
        source = s[4];
        text = s[5];

    }

    public String toString(){
        return id + "|" +target + "|" + subject + "|" + type + "|" +source + "|" + text + "|";
    }

    public JCageMessage(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
