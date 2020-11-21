package spingboot.express.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 907296044907184664L;

    private int messageId;

    private int fromId;

    private String fromName;

    private int toId;

    private String messageText;

    private Timestamp messageDate;

    public Message() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", fromId=" + fromId +
                ", fromName='" + fromName + '\'' +
                ", toId=" + toId +
                ", messageText='" + messageText + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }

}
