package cn.abner.funchat.service;

import cn.abner.funchat.entity.ImGroup;
import cn.abner.funchat.entity.param.GroupEdit;
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
public interface ImGroupService {
    ImGroup saveGroup(GroupEdit group, MultipartFile avatarFile, MultipartFile avatarCover);

}
