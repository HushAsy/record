<%--
  Created by IntelliJ IDEA.
  User: hewater
  Date: 2017/12/18
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form style="margin-left: 50px;margin-top: 5px" id="f_user" method="post">
    <table cellpadding="5">
        <tr>
            <td>姓名:</td>
            <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'"></input></td>
        </tr>
        <tr>
            <td>电话:</td>
            <td><input class="easyui-textbox" type="text" name="tel" data-options="validType:'telNum'"></input></td>
        </tr>
        <tr>
            <td>地址:</td>
            <td><input class="easyui-textbox" type="text" name="address"></input></td>
        </tr>
    </table>
</form>
<div style="text-align:center;padding:5px">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitFormUser()">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearFormUser()">重置</a>
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
    function submitFormUser(){
        $('#f_user').form('submit',{
            url:getContextPath()+"/home/insertUser.do",
            onSubmit:function(){
                return $(this).form('enableValidation').form('validate');
            },
            success:function(data){
                $('#addUserDlg').dialog('close');
                $.messager.alert('我的消息',data,'info');
//                $('#userCombo').combobox('reload',getContextPath()+"/home/getUserListApi.do");
                window.location.href=getContextPath();
            }
        });
    }
    function clearFormUser(){
        $('#f_user').form('clear');
    }
</script>
</body>
</html>
