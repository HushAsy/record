<%--
  Created by IntelliJ IDEA.
  User: hewater
  Date: 2017/12/18
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
    <form style="margin-left: 50px;margin-top: 30px" id="f_jiu" method="post">
        <table cellpadding="5">
            <tr>
                <td>酒名:</td>
                <td><input class="easyui-textbox" type="text" name="j_name" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'"></input></td>
            </tr>
            <tr>
                <td>生产日期:</td>
                <td><input id="j_time" class="easyui-datebox" type="text" name="j_time"></input></td>
            </tr>
        </table>
    </form>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
    </div>
    <script>
        $.extend($.fn.validatebox.defaults.rules, {
            phoneNum: { //验证手机号
                validator: function(value, param){
                    return /^1[3-8]+\d{9}$/.test(value);
                },
                message: '请输入正确的手机号码。'
            },

            telNum:{ //既验证手机号，又验证座机号
                validator: function(value, param){
                    return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
                },
                message: '请输入正确的电话号码。'
            }
        });
        function submitForm(){
            $('#f_jiu').form('submit',{
                url:getContextPath()+"/home/insertJiu.do",
                onSubmit:function(){
                    return $(this).form('enableValidation').form('validate');
                },
                success:function(data){
                    $.messager.show({
                        title:'我的消息',
                        msg:'添加成功',
                        timeout:3000,
                        showType:'slide'
                    });
                    $('#addJiuDlg').dialog('close');
                    $('#jiuCombo').combobox('reload',getContextPath()+"/home/getJiuListApi.do");
                }
            });
        }
        function clearForm(){
            $('#f_jiu').form('clear');
        }
        $.fn.datebox.defaults.formatter = function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            var hh = date.getHours();
            var mm = date.getMinutes();
            return m+'-'+d+'-'+y+' '+hh+':'+mm;
        }
    </script>
</body>
</html>
