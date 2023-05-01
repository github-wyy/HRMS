package com.wyy.endpoint.action;

import com.wyy.common.http.ResponseData;
import com.wyy.endpoint.action.abs.AbstractEndpointAction;
import com.wyy.endpoint.service.IRecordService;
import com.wyy.jwt.annotation.JWTCheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hr/endpoint/record/*")
public class RecordAction extends AbstractEndpointAction {
    @Autowired
    private IRecordService recordService; // 日志记录业务层
    @JWTCheckToken(role = "record", action = "record:list")
    @GetMapping("list")
    public Object list(Long currentPage, Long lineSize, String column, String keyword) { // 查询全部工资等级
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh());
        // long currentPage, int lineSize, String column, String keyword
        data.setResult(this.recordService.split(currentPage, lineSize, column, keyword)); // 调用业务处理方法
        return data; // 响应数据
    }
}
