package com.lfl.yudao.server.pojo.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value="username")
    private String username;
    @TableField(value="password")
    private String password;
    @TableField(value="status")
    private Integer status;
}
