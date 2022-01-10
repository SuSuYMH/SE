package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "permission")
@Proxy(lazy = false)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionId;

    //权限控制字符串
    private String permissionString;

    private String description;

    //权限所应该属于哪个身份
    //1.administrator
    //2.teacher
    //3.assistant
    //4.student
    private Integer shouldBelongRoleID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private List<UserVSPermission> userVSPermissionList;

}
