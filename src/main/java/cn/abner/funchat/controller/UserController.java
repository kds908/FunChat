package cn.abner.funchat.controller;

import cn.abner.funchat.common.Paged;
import cn.abner.funchat.common.ResponseResult;
import cn.abner.funchat.entity.ImUser;
import cn.abner.funchat.entity.param.UserParam;
import cn.abner.funchat.service.ImUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Controller
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ImUserService userService;

    /**
     * 用户列表查询
     * @param param UserParam
     * @return List
     */
    @GetMapping("/list")
    public ResponseResult<List<ImUser>> list(UserParam param) {
        return ResponseResult.success(userService.listByCondition(param));
    }

    /**
     * 用户分页查询
     * @param param param
     * @param pageNum Integer
     * @param pageSize Integer
     * @return Paged
     */
    @GetMapping("/page")
    public ResponseResult<Paged<ImUser>> page(UserParam param, @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseResult.success(userService.pageByCondition(param, pageNum, pageSize));
    }

    @GetMapping("findById/{uid}")
    public ResponseResult<ImUser> findById(@PathVariable("uid") String uid) {
        return ResponseResult.success(userService.findById(uid));
    }

    /**
     * 新增用户
     * @param user {@link ImUser}
     * @return {@link ImUser}
     */
    @PostMapping("/add")
    public ResponseResult<ImUser> add(@RequestBody ImUser user) {
        return ResponseResult.success(userService.add(user));
    }

    /**
     * 用户编辑
     * @param user {@link ImUser}
     * @return {@link ImUser}
     */
    @PutMapping("/edit")
    public ResponseResult<ImUser> edit(@RequestBody ImUser user) {
        return ResponseResult.success(userService.update(user));
    }

    /**
     * 主键删除用户
     * @param uid {@link String}
     * @return {@link Object}
     */
    @DeleteMapping("/remove/{uid}")
    public ResponseResult<Object> remove(@PathVariable String uid) {
        userService.deleteById(uid);
        return ResponseResult.success(null);
    }
}
