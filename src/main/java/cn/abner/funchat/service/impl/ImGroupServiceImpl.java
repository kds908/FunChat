package cn.abner.funchat.service.impl;

import cn.abner.funchat.common.constants.Constants;
import cn.abner.funchat.config.AppConfig;
import cn.abner.funchat.config.SystemSettings;
import cn.abner.funchat.entity.ImContact;
import cn.abner.funchat.entity.ImGroup;
import cn.abner.funchat.entity.ImUser;
import cn.abner.funchat.entity.enums.ContactStatusEnum;
import cn.abner.funchat.entity.enums.ContactTypeEnum;
import cn.abner.funchat.entity.param.GroupEdit;
import cn.abner.funchat.exception.BizException;
import cn.abner.funchat.repository.ImContactRepository;
import cn.abner.funchat.repository.ImGroupRepository;
import cn.abner.funchat.service.ImGroupService;
import cn.abner.funchat.service.ImUserService;
import cn.hutool.core.util.RandomUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
@Service
public class ImGroupServiceImpl implements ImGroupService {
    @Autowired
    private ImUserService userService;
    @Autowired
    private ImGroupRepository groupRepository;
    @Autowired
    private ImContactRepository contactRepository;
    @Autowired
    private AppConfig appConfig;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImGroup saveGroup(GroupEdit groupEdit, MultipartFile avatarFile, MultipartFile avatarCover) {
        ImUser currentUser = userService.getCurrentUser();
        LocalDateTime now = LocalDateTime.now();
        long groupCount = groupRepository.countByGroupOwnerId(currentUser.getUid());
        // TODO system settings
        SystemSettings settings = new SystemSettings();
        if (groupCount >= settings.getMaxGroupCount()) {
            throw new BizException("最多可创建" + settings.getMaxGroupCount() + "个群聊");
        }
        ImGroup group;
        if (StringUtils.isEmpty(groupEdit.getGroupId())) {
            groupEdit.setGroupId(Constants.GROUP_PREFIX + RandomUtil.randomNumbers(12));
            group = ImGroup.builder()
                    .groupId(groupEdit.getGroupId())
                    .groupName(groupEdit.getGroupName())
                    .createTime(now)
                    .groupOwnerId(currentUser.getUid())
                    .groupNotice(groupEdit.getGroupNotice())
                    .joinType(groupEdit.getJoinType())
                    .build();
        } else {
            Optional<ImGroup> groupOptional = groupRepository.findById(groupEdit.getGroupId());
            if (groupOptional.isPresent()) {
                group = groupOptional.get();
                if (!group.getGroupOwnerId().equals(currentUser.getUid())) {
                    throw new BizException("无权操作不属于自己的群组");
                }
                group.setGroupName(groupEdit.getGroupName());
                group.setGroupNotice(groupEdit.getGroupNotice());
                group.setJoinType(groupEdit.getJoinType());
            } else {
                throw new BizException("无效的群组ID" + groupEdit.getGroupId());
            }
        }
        groupRepository.save(group);

        ImContact contact = ImContact.builder()
                .status(ContactStatusEnum.FRIEND.getStatus())
                .contactType(ContactTypeEnum.GROUP.getType())
                .contactId(group.getGroupId())
                .userId(group.getGroupOwnerId())
                .createTime(now)
                .build();
        contactRepository.save(contact);

        saveFiles(group.getGroupId(), avatarFile, avatarCover);
        return group;
    }

    private void saveFiles(String groupId, MultipartFile avatarFile, MultipartFile avatarCover) {
        if (avatarFile != null) {
            String baseFolder = appConfig.getProjectFolder();
            File targetFileFolder = new File(baseFolder + Constants.FILE_FOLDER_AVATAR);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePathPrefix = targetFileFolder.getPath() + "/" + groupId;
            try {
                avatarFile.transferTo(new File(filePathPrefix + Constants.IMAGE_SUFFIX));
                avatarCover.transferTo(new File(filePathPrefix + Constants.COVER_IMAGE_SUFFIX));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
