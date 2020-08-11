package com.example.springbootrest.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.springbootrest.pojo.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberListener11 extends AnalysisEventListener<Member> {

    List<Member> list = new ArrayList<Member>();

    public List<Member> getList() {
        return list;
    }

    public void setList(List<Member> list) {
        this.list = list;
    }

    @Override
    public void invoke(Member data, AnalysisContext context) {
        Integer num = context.getCurrentRowNum();
        System.out.println("第" + num + "行");
        System.out.println("解析到一条数据:{ "+ data.getName() +" }");
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成！");
    }


}
