package com.susu.se.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.susu.se.model.users.*;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_permission")
@Proxy(lazy = false)
public class UserVSPermission {
    //这是主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer upID;

//    //角色id
//    private Integer userID;
//
//    //权限表对应的权限ID
//    private Integer permissionID;

    //此表-权限表 多-1
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    @JsonBackReference
    private Permission permission;

    //此表-用户表 多-1
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
