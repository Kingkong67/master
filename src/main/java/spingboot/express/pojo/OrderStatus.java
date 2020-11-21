package spingboot.express.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = -6620266253894242499L;

    private int id;

    private String name;

    private String desc;


    @Override
    public String toString(){
        return "id:"+id+"name:"+name+"desc:"+desc;
    }
}
