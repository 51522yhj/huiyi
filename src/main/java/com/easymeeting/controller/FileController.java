package com.easymeeting.controller;

import com.easymeeting.annotation.GlobalInterceptor;
import com.easymeeting.config.AppConfig;
import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.DateTimePatternEnum;
import com.easymeeting.entity.enums.FileTypeEnum;
import com.easymeeting.entity.enums.ResponseCodeEnum;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.utils.DateUtil;
import com.easymeeting.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Date;

@Slf4j
@Validated
@RequestMapping("/file")
@RestController
public class FileController extends ABaseController{
        @Resource
        private AppConfig appConfig;

        @RequestMapping("/getResource")
        @GlobalInterceptor(checkLogin = false)
        public void getResource(HttpServletResponse response,
                                  @RequestHeader(required = false,name = "range") String range,@NotNull Long messageId,
                                  @NotNull Long sendTime , @NotNull Integer fileType,
                                  @NotEmpty String token,Boolean thumbnail){
                TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
                if (null == tokenUserInfo){
                        throw  new BusinessException(ResponseCodeEnum.CODE_901);
                }
                FileTypeEnum fileTypeEnum = FileTypeEnum.getByType(fileType);
                thumbnail = thumbnail == null ? false : thumbnail;
                String month = DateUtil.format(new Date(sendTime), DateTimePatternEnum.YYYY_MM.getPattern());
                String filePath = appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE+month+"/"+messageId+fileType;
                if(null == fileTypeEnum){
                        throw new BusinessException(ResponseCodeEnum.CODE_600);
                }
                switch (fileTypeEnum){
                        case IMAGE:
                                response.setHeader("Content-Type","max-age=" + 30 * 24 * 60 * 60);
                                response.setContentType("image/jpeg");
                                break;
                }
                readFile(response,range,filePath,thumbnail);
          //  return month;
        }

        private void readFile(HttpServletResponse response, String range, String filePath, Boolean thumbnail) {
            filePath = thumbnail ? StringTools.getImageThumbnail(filePath) : filePath;
            File file = new File(filePath);

        }

}
