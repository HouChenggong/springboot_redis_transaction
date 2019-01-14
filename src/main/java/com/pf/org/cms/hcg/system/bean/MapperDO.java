package com.pf.org.cms.hcg.system.bean;


import com.pf.org.cms.annotation.Column;
import com.pf.org.cms.annotation.Id;
import com.pf.org.cms.annotation.Table;


@Table(value = "mapper")
//表的名称
public class MapperDO {

//    使用注解编写对象类@Table为表名，@Id为主键，@Column为列名，
//    注意@Id和@Column需要定义在get方法上。
    private Integer id ;

    private  String name ;

    private Integer sex ;

    @Id(value = "ID")
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }
    @Column(value = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(value = "sex")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "MapperDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
