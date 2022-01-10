package com.susu.se.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionIDList implements Serializable {
    private List<Integer> permissionIDList;
}


