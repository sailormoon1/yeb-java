package com.hang.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hang.pojo.Employee;
import com.hang.pojo.MailConstants;
import com.hang.pojo.MailLog;
import com.hang.service.IEmployeeService;
import com.hang.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @USER: zzh-
 * @DATE: 2022/4/28
 * @TIME: 12:59
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            if (3 <= mailLog.getCount()) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("count", mailLog.getCount() + 1).set("updateTime", LocalDateTime.now())
                    .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq("msgId",mailLog.getMsgId()));
            Employee emp = employeeService.getEmployee(mailLog.getEid()).get(0);
            //重新发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,
                    emp,new CorrelationData(mailLog.getMsgId()));

        });
    }


}
