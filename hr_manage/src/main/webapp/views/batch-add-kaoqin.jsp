<%@ page pageEncoding= "UTF-8" %>
<%@ page contentType= "text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>导入考勤数据</title>
	
	<style type="text/css">

.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover { font-size: 12px; }
.table th, .table td { vertical-align: middle; }
.page-title{ margin: 15px 0 10px 25px; }

blockquote { border-left: 8px solid #006dcc; }
blockquote { padding: 0 0 0 15px; margin: 0 0 20px; }
h3 { font-size: 22px; }
label, input, button, select, textarea { font-size: 12px; font-weight: normal; line-height: 20px; }
select, input[type="file"] { height: 26px; line-height: 30px; }
.form-horizontal .control-label { padding: 5px 0 5px 0; color: #777}
.form-horizontal .control-group { margin-bottom: 10px; border-bottom: 1px solid #eee; }

input[type="text"] { height: 18px; font-size: 12px;}
.required{color: #d80000;padding: 0 5px;}
.stockNum{}
.red{color: red;}
.green{color:green;}
input.span2{width: 135px;}
.item-img {vertical-align: middle; padding-bottom: 5px;}
</style>
</head>
<body>
	
	
	<div id="first" class="main-content" style="display:block;">
		<div class="pad-10">
			<div class="bk10"></div>
			<div class="col-tab">
				<ul class="tabBut cu-li">
					<li id="tab_setting_1" class="on">上传文件</li>
				</ul>
				<div id="div_setting_1" class="contentList pad-10">
					<form id="form" class="form-horizontal" enctype="multipart/form-data" action="/checkwork/importQtWlwExcel" method="post">
						<div class="control-group">
							<label class="control-label">请选择上传的文件：</label>
							<div class="controls">
							    <input class="" type="text" id="term" name="term" value="201807"/>
							
								<input class="" type="file" id="filedata" name="filedata" />
							</div>
						</div>
					</form>
				</div>
				<div class="saveInfo">
					<button data-action="submit" class="red-btn" type="button" onclick="saveProductItem()" id="frmSubmit" >导入</button>&nbsp;&nbsp;&nbsp;
				</div>
			</div>
		</div>
	</div>
	

	
<script src="/js/jquery.min.js"></script>
<script src="/js/jquery.form.min.js"></script>
<script src="/js/meiliwan.utils.js"></script>
<script type="text/javascript">

function saveProductItem() {
	
	var submit = 0;
	var filedata = $("#filedata").val();
    if(filedata=='') {
	    alert("请选择需上传的文件!");
	    return false;
	}
    if(filedata.indexOf('.xls')==-1&&filedata.indexOf('.xlsx')==-1){
	    alert("请选择正确的Excel文件(后缀名.xls或后缀名.xlsx)！");
	    return false;
	 }

    if (++submit>1)
    {
        alert('表单已经提交，需要几分钟请耐心等待,请勿刷新或再次提交表单!');
        return false;
    }

	$("#form").submit();
}

</script>

</body>
</html>