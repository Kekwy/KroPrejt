package com.kekwy.se.interpreter;

import com.kekwy.se.data.DataStruct;

import java.util.List;

/**
 * -d   指定目录嵌套的层数（默认为1）
 * -dbs 使用 MySql 保存对比结果/查询历史记录
 * -o   指定输出路径（默认为工具根目录res_output文件夹下）
 * -i   指定输入路径（必选），应为待测程序的目录文件（即将待测程序放置在同一个文件夹下，输入该文件夹路径）
 * -f   从文件中读取待测程序输入类型并指定文件
 * -s   从用户输入读取待测程序输入类型
 */
public class DefaultInterpreter implements Interpreter {

    @Override
    public List<DataStruct> interpret(String command) {
        String[] parameters = command.split(" ");
        for (int i = 0; i < parameters.length; i++) {
            switch (parameters[i]) {
                case ""
            }
        }
        return null;
    }
}
