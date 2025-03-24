package cn.abner.funchat.controller;

import cn.abner.funchat.aop.annotation.GlobalInterceptor;
import cn.abner.funchat.common.ResponseResult;
import cn.abner.funchat.entity.param.GroupEdit;
import cn.abner.funchat.service.ImGroupService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
@RestController
@RequestMapping("/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private ImGroupService groupService;

    @PostMapping("/saveGroup")
    @GlobalInterceptor
    public ResponseResult<Object> saveGroup(@RequestBody GroupEdit group, MultipartFile avatarFile, MultipartFile avatarCover) {
        logger.info("saveGroup");
        return ResponseResult.success(groupService.saveGroup(group, avatarFile, avatarCover));
    }
}
