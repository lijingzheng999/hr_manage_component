var showAlert = function(msg,style,title,callback){
	var style=typeof(style)=='undefined'?1:style;
	var title=typeof(title)=='undefined'?'提示信息':title;
	var template;
	switch(style){
	case 0:
		template='<div id="alertTmp" class="alert fade in">' + 
					 '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
					 '<span>'+msg+'</span></div>';
		$(document.body).prepend(template);
		$("#alertTmp").alert();
		setTimeout(function(){$("#alertTmp").alert("close")}, 2000);
		break;
	case 1:
		template='<div id="alertTmp" class="modal hide fade">'+
				 '<div class="modal-header">'+
				 '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				 '<h3>'+title+'</h3>'+
				 '</div>'+
				 '<div class="modal-body">'+
				 '<p>'+msg+'</p>'+
				 '</div>'+
				 '<div class="modal-footer">'+
				 '<button class="btn red-btn" data-dismiss="modal" aria-hidden="true">关闭</button>'+
				 '</div></div>';
		$(document.body).prepend(template);
		$("#alertTmp").modal('show');
		$('#alertTmp').on('hidden', function () {
			$('#alertTmp').remove();
		})
		break;
	case 2:
		template='<div id="alertTmp" class="modal hide fade">'+
				 '<div class="modal-header">'+
				 '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				 '<h3>'+title+'</h3>'+
				 '</div>'+
				 '<div class="modal-body">'+
				 '<p>'+msg+'</p>'+
				 '</div>'+
				 '<div class="modal-footer">'+
				 '<button id="confirm" class="btn btn-success red-btn" >确认</button>'+
				 '<button class="btn red-btn" data-dismiss="modal" aria-hidden="true">关闭</button>'+
				 '</div></div>';
		$(document.body).prepend(template);
		$("#alertTmp").modal('show');
		$('#confirm').on('click',function(){
			if(callback){callback()}
			$("#alertTmp").modal('hide');
		});
		$('#alertTmp').on('hidden', function () {
			$('#alertTmp').remove();
		});
		break;
	}
};

var showConfirm = function(msg,title,callback,obj){
	var title=typeof(title)=='undefined'?'提示信息':title;
	var template ='<div id="alertTmp" class="modal hide fade">'+
				 '<div class="modal-header">'+
				 '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'+
				 '<h3>'+title+'</h3>'+
				 '</div>'+
				 '<div class="modal-body">'+
				 '<p>'+msg+'</p>'+
				 '</div>'+
				 '<div class="modal-footer">'+
				 '<button id="confirm" class="btn btn-success red-btn" >确认</button>'+
				 '<button class="btn red-btn" data-dismiss="modal" aria-hidden="true">关闭</button>'+
				 '</div></div>';
	$(document.body).prepend(template);
	$("#alertTmp").modal('show');
	$('#confirm').on('click',function(){
		if(callback){callback(obj)}
		$("#alertTmp").modal('hide');
	});
	$('#alertTmp').on('hidden', function () {
		$('#alertTmp').remove();
	});
};

//Rewrite window alert
window.alert=showAlert;

//Rewrite window alert
//window.confirm=showConfirm;