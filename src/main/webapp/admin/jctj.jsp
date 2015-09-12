<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 头部 -->
<%@ include file="/admin/head.jsp"%>
<div class="easyui-layout" data-options="fit:true">

<!-- 导航 -->
<%@ include file="/admin/nav.jsp"%>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/jctj">首页</a>><span>检查统计</span></p>
    		</div>
    		<div>
				<table class="table1 table3" width="100%">
					<tr class="t1top">
						<td colspan="9" style="text-align: right;"><span></span><input type="text" value="输入执法人员姓名搜索" onfocus="if(this.value==this.defaultValue){this.value=''}" onblur="if(this.value==''){this.value=this.defaultValue}">
						</td>
					</tr>
					<tr>
						<th width="10%">企业名称</th>
						<th width="10%">最近检查日期</th>
						<th width="10%">检查项</th>
						<th width="10%">检查人员</th>
						<th width="7%">最近执法日期</th>
						<th width="10%">执法文书张片</th>
						<th width="10%">执法人员</th>
						<th width="10%">操作</th>
					</tr>
					<tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr>
					<tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr>
					<tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr><tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr>
					<tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr>
					<tr>
						<td>AAAAA</td>
						<td>刘德华</td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td><img src="#" /></td>
						<td>2015.09.04</td>
						<td>泸州市XXX厂</td>
						<td style="text-align: left;"><img src="#" /><img src="#" /></td>
						<td><a href="#">详情>></a></td>
					</tr>
				</table>
				<div class="page cb">
					<p>共有120条记录，当前第1/10 页</p>
					<ul>
						<li><a href="#">首页</a></li>
						<li><a href="#">上一页</a></li>
						<li><a href="#">下一页</a></li>
						<li><a href="#">尾页</a></li>
						<li>跳转到第<input type="text">页</li>
						<li><a href="#">确定</a></li>
					</ul>
				</div>

    		</div>
    </div>
 </div>

</body>
</html>