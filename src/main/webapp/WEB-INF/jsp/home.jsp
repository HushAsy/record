<%--
  Created by IntelliJ IDEA.
  User: hewater
  Date: 2017/12/16
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>流水记录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
</head>
<body>
<h2>流水记录</h2>
<div class="easyui-layout" style="width:1024px;height:600px;">
    <div data-options="region:'north'" style="height:50px">
        <div style="margin-top:10px;">
            酒类型:<input id="jiuCombo" name="jiu" style="width:60px;"/>&nbsp;&nbsp;人员姓名:<input id="userCombo" name="user" style="width:60px;"/>&nbsp;<input class="easyui-filebox" name="file" data-options="prompt:'Choose a file...'"/><a href="#" class="easyui-linkbutton" style="margin-right:40px;margin-left: 10px" data-options="iconCls:'icon-save'">保存</a><input class="easyui-textbox" data-options="buttonText:'SEARCH',prompt:'根据条形码搜索...'" style="width:400px;height:32px;font-size: 20px;">
        </div>
    </div>
    <div data-options="region:'south',split:true" style="height:50px;">
        <a href="#" class="easyui-linkbutton" style="margin-right:20px;margin-left: 10px" data-options="iconCls:'icon-add'">新增酒类型</a>
        <a href="#" class="easyui-linkbutton" style="margin-right:40px;margin-left: 10px" data-options="iconCls:'icon-add'">新增人员</a>
    </div>

    <%--<div data-options="region:'east',split:true" title="East" style="width:100px;"></div>--%>
    <%--<div data-options="region:'west',split:true" title="West" style="width:100px;"></div>--%>

    <div data-options="region:'center',title:'数据查询',iconCls:'icon-ok'">
        <table class="easyui-datagrid"
               data-options="url:'datagrid_data1.json',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true">
            <thead>
            <tr>
                <th data-options="field:'itemid'" width="80">Item ID</th>
                <th data-options="field:'productid'" width="100">Product ID</th>
                <th data-options="field:'listprice',align:'right'" width="80">List Price</th>
                <th data-options="field:'unitcost',align:'right'" width="80">Unit Cost</th>
                <th data-options="field:'attr1'" width="150">Attribute</th>
                <th data-options="field:'status',align:'center'" width="60">Status</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script>
    $('#jiuCombo').combobox({

    });
    $('#userCombo').combobox({

    });
</script>
</body>
</html>
