package com.easymeeting.controller;

import com.easymeeting.entity.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController extends ABaseController{
    @RequestMapping("/checkVersion")
    public ResponseVO checkVersion() {
        return getSuccessResponseVO(null);
    }


}
