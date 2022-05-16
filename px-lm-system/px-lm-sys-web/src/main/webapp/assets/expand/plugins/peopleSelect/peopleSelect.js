

// 子函数封装数据
function child(noId, no, nameId, name) {
	$('#noId').val(noId);
	$('#no').val(no);
	$('#nameId').val(nameId);
	$('#name').val(name);

	var ids = no.split(',');
	var names = name.split(',');
	if (no != '') {
		for (var i = 0; i < ids.length; i++) {
			$('.sp_select_popup_right_list_box').append('<p id="list' + i + '" data-index="' + ids[i] + '" >' +
				'<a class="tablename">' + names[i] + '</a><span class="tableid">' + ids[i] + '</span>' +
				'<i class="layui-icon layui-icon-add-circle"></i></p>')
		}
	}
}

layui.use(['form', 'table'], function() {
	var table = layui.table;
	var form = layui.form;


	//点击左侧树的事件
	var setting = {
		view: {
			showLine: false,
		},
		data: {
			key: {
				title: "t"
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function(a, b, node,index,searchUsers) { //node是代表zNodes里面的urltree属性
				console.log(node);
				if(node==null){
					return;
				}
				PeopleSelect.nowObj=node;//通过转换实现对象克隆
				if(node.role==null){
					PeopleSelect.nowObj.type='dept';
				}else{
					PeopleSelect.nowObj.type="role";
				}
				var users=node.users;
				if(searchUsers!=null){
					users=searchUsers;
				}
				table.render({
					id: 'idTest',
					elem: '#ztreedata',
					data: users,
					height: 'full-95',
					cols: [
						[{
							type: 'checkbox',
							fixed: 'left',
						}, {
							field: 'avatar',
							title: '头像',
							templet: '#imgAvatar',
							width: '15%',
							align: 'center',
						}, {
							field: 'name',
							title: '姓名',
							width: '40%',
							align: 'center',
						}, {
							field: 'phone',
							title: '联系电话',
							width: '35%',
							align: 'center',
						}, {
							field: 'userId',
							title: 'ID',
							hide: true
						}]
					],
					page: true,
					limit: 10,
					done: function(res, curr, count) {
						// var idCard = $('#no').val();
						// var idCards = $('#no').val().split(',');
						// $(res.data).each(function(index, r) {
						// 	if (idCard.search(r.id) != -1) {
						// 		// 默认渲染表格
						// 		$('.layui-table-fixed').find('tr[data-index="' + r.LAY_TABLE_INDEX + '"]').find(
						// 			'input[name="layTableCheckbox"]+').click();
						// 	}
						// });
					}
				});
			}
		}
	};
	
	/* 搜索 */
	form.on('submit(skeySubmit)', function(data) {
		var keyword = data.field.name;
		console.log(keyword)
		var users=PeopleSelect.users;
		if(PeopleSelect.nowObj!=null){
			users=PeopleSelect.nowObj.users;
		}
		if(users!=null){
			var searchUsers=[];
			for(var i=0;i<users.length;i++){
				var user=users[i];
				if(user.name.indexOf(keyword)!=-1){
					searchUsers.push(user);
				}
			}
			setting.callback.onClick(null,null,PeopleSelect.nowObj,0,searchUsers);
		}else{
			setting.callback.onClick(null,null,PeopleSelect.nowObj,0);
		}
		return false;
	})

	/* 选择事件 */
	table.on('checkbox(test)', function(obj) {
		var checkStatus = table.checkStatus('idTest');
		var tablecheck = (obj.checked); //当前当前复选框状态
		var tablename = (obj.data.name); //获取选中行的name
		var tableid = (obj.data.id); //获取选中行的身份证号
		var checktype = (obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
		var uid = $(this).parent().parent().parent().data('index') //获取选中行的data-index
		var listid = 'list' + uid //给选中行所对应的右侧p获取相应的id
		var checkOn = false;
		if (checktype == 'one') {
			var tableid2 = $('.sp_select_popup_right_list_box').find('.tableid').text(); //判断是否存在该身份证号,如果存在则不添加
			if (tablecheck == true && tableid2.indexOf(tableid) < 0) { //如果tablecheck是选中状态,且已选择列表没有当前信息
				$('.sp_select_popup_right_list_box').append('<p id="' + listid + '" data-index="' + tableid +
					'" ><a class="tablename">' + tablename + '</a><span class="tableid">' + tableid +
					'</span><i class="layui-icon layui-icon-add-circle"></i></p>') //添加右侧列表信息
			} else if (tablecheck == false && tableid2.indexOf(tableid) > -1 && checkOn == false) {
				$('.sp_select_popup_right_list_box').find('p[data-index="' + tableid + '"]').remove();
			}
		} else if (checktype == 'all') {
			var ids = $('#no').val(); //已经存在的身份证串
			var tableng = (checkStatus.data.length) //获取选中行数量
			if (tablecheck == true) { //当点全选时
				for (var i = 0; i < tableng; i++) {
					var tablename_box = $('tr[data-index=' + i + ']').find('td[data-field=name]').text() //循环获取每行的姓名
					var tableid_box = $('tr[data-index=' + i + ']').find('td[data-field=id]').text() //循环获取每行的身份证号
					console.log(tablename_box+'-------------------'+tableid_box)
					if (ids.search(tableid_box) == -1) {
						$('.sp_select_popup_right_list_box').append('<p id="list' + i + '" data-index="' + tableid_box +
							'" ><a class="tablename">' + tablename_box + '</a><span class="tableid">' + tableid_box +
							'</span><i class="layui-icon layui-icon-add-circle"></i></p>') //添加右侧列表信息
					}
				}
			} else {
				// 获取当前页表格数据
				var len = $('tr').find('td[data-field=id]').length;
				for (var i = 0; i < len; i++) {
					var tableid_box = $('tr[data-index=' + i + ']').find('td[data-field=id]').text() //循环获取每行的身份证号
					console.log(ids.search(tableid_box));
					if (ids.search(tableid_box) != -1) {
						$('.sp_select_popup_right_list_box').find('p[data-index="' + tableid_box + '"]').remove();
					}
				}
			};
		};

		// 获取数据
		getData();
	});

	/**
	 * 初始化数据，在用户使用时加载一次
	 */
	var initUserData=function(callBack){
		$.ajax({
			type: 'PUT',
			url: '/mgr/read/userTree'
		}).then(function(result) {
			console.log(result);
			if(result.code==200){
				PeopleSelect.users=Warpper.allUsers(result.data.deptUser);
				var data={
					users: PeopleSelect.users,
					deptTree:Warpper.deptTree(result.data.deptUser),
					roleTree:Warpper.roleTree(result.data.roleUser)
				};

				callBack(data);
			}
		});
	}
	var PeopleSelect={
		users:[],
		deptTree:[],
		roleTree:[],
		nowObj: null
	};

	/**
	 * 数据处理
	 * @type {{deptTree: deptTree, roleTree: roleTree}}
	 */
	var Warpper={
		allUsers:function(data){
			var users=[];
			Warpper.deptTreePack(data,0,function(data){
				if(data==null){
					return;
				}
				for(var i=0;i<data.length;i++){
					var user=data[i];
					users.push(user);
				}
			});
			return users;
		},
		deptTree:function(data){
			var tree=Warpper.deptTreePack(data,0);
			tree.unshift({
				id: 1,
				pId: 0,
				name: "全部",
				t: "",
				users: PeopleSelect.users,
				open: true,
			});
			return tree;
		},
		deptTreePack:function(data,pid,callBack){
			var tree=[];
			if(data!=null){
				var item= {
					id: data.dept.deptId,
					pId: pid,
					name: data.dept.fullName,
					t: data.dept.simpleName,
					open: true,
					users: data.users
				};
				if(callBack!=null){
					callBack(data.users);
				}
				tree.push(item);
				if(data.depts!=null && data.depts.length>0){
					for(let i=0;i<data.depts.length;i++){
						let dept=data.depts[i];
						let dt=Warpper.deptTreePack(dept,item.id,callBack);
						for(let j=0;j<dt.length;j++){
							tree.push(dt[j]);
						}
					}
				}
				return tree;
			}
		},
		roleTree:function(data){
			var tree=[];
			tree.unshift({
				id: 1,
				pId: 0,
				name: "全部",
				t: "",
				open: true,
				users: PeopleSelect.users
			});
			if(data!=null){
				for(var i=0;i<data.length;i++){
					var item= {
						id: data[i].role.roleId,
						pId: 0,
						name: data[i].role.name,
						t: data[i].role.description,
						open: true,
						users: data[i].users
					};
					tree.push(item);
				}
			}
			return tree;
		}
	};

var initTable=function(users){
	//默认显示的 表格
	table.render({
		id: 'idTest',
		elem: '#ztreedata',
		height: 'full-95',
		data: users,
		cols: [
			[{
				type: 'checkbox',
				fixed: 'left',
			}, {
				field: 'avatar',
				title: '头像',
				templet: '#imgAvatar',
				width: '15%',
				align: 'center',
			}, {
				field: 'name',
				title: '姓名',
				width: '40%',
				align: 'center',
			}, {
				field: 'phone',
				title: '联系电话',
				width: '35%',
				align: 'center',
			}, {
				field: 'userId',
				title: 'ID',
				hide: true
			}]
		],
		page: true,
		limit: 10,
		done: function(res, curr, count) {
			var idCard = $('#no').val();
			var idCards = $('#no').val().split(',');
			// $(res.data).each(function(index, r) {
			// 	if (idCard.search(r.id) != -1) {
			// 		// 默认渲染表格
			// 		$('.layui-table-fixed').find('tr[data-index="' + r.LAY_TABLE_INDEX + '"]').find(
			// 			'input[name="layTableCheckbox"]+').click();
			// 	}
			// });

		}
	});
}

	$(document).ready(function() {
		// 初始化数据
		initUserData(function(data){

			console.log(data);
			initTable(data.users);
			var deptTreeData=data.deptTree;
			var roleTreeData=data.roleTree;
			//初始化树
			$.fn.zTree.init($("#deptTree"), setting, deptTreeData);
			$.fn.zTree.init($("#roleTree"), setting, roleTreeData);

		});
	});

	$('.sp_select_popup_right_list_box').on('click', '.layui-icon-add-circle', function() { //删除某一行
		$(this).parent().remove();

		// 获取数据
		getData();
		table.reload('idTest');
	});
});

$('.sp_select_popup_right_list_bot a').click(function() { //点击删除全部
	$('.sp_select_popup_right_list_box p').remove();
	for (var i = 0; i < 2; i++) {
		$('div.laytable-cell-checkbox').find('div.layui-form-checked').click(); //取消checked的全部选中状态
	}
	$("#name").val('');
	$("#no").val('');
});

$('.sp_select_popup_sub').click(function() { //将已选择列表中的姓名给添加到文本框中
	$(".sp_select_popup_input").val('');
	$(".sp_select_popup_list").val('');
	parent.$('#' + $('#noId').val()).val($("#no").val());
	parent.$('#' + $('#nameId').val()).val($("#name").val());
	parent.$('#' + $('#nameId').val()).attr("title", $("#name").val());

	//关闭选人弹窗
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
});

$(".sp_select_popup_left_tab a").click(function() {
	var _index = $(this).index();
	$(".sp_select_popup_left_tree .ztree-demo").eq(_index).show().siblings().hide();
	$(this).addClass("active").siblings().removeClass("active");
});

// 获取数据
function getData() {
	var idcardno = '';
	var idcardnoname = '';
	$('.sp_select_popup_right_list_box p').each(function() {
		var pickname = $(this).find('.tablename').text();
		var pickid = $(this).find('.tableid').text();

		idcardno = idcardno + pickid + ',';
		idcardnoname = idcardnoname + pickname + ',';
	});

	$('#no').val(idcardno.substring(0, idcardno.lastIndexOf(",")));
	$('#name').val(idcardnoname.substring(0, idcardnoname.lastIndexOf(",")));
}
