package com.hr.manage.web.constant;
/**
 * 权限ID
 * @author Administrator
 *
 */
public class FunctionIds {
	
	/**
	 * 已作废权限，用于废弃的controller方法，限制访问。数据库中不保存此权限id。
	 */
	public static final int FUNCTION_INVALID = 9999;
	
	public static final int FUNCTION_1 = 1;				//系统管理0
	public static final int FUNCTION_2 = 2;				//招聘管理0
	public static final int FUNCTION_3 = 3;				//入离职管理0
	public static final int FUNCTION_4 = 4;				//职场管理0
	public static final int FUNCTION_5 = 5;				//薪酬管理  0
	public static final int FUNCTION_6 = 6;				//项目经管  0
	public static final int FUNCTION_7 = 7;				//账号管理  1-1预留
	public static final int FUNCTION_8 = 8;				//修改密码  1-1
	public static final int FUNCTION_9 = 9;				//招聘需求  1-2
	public static final int FUNCTION_10 = 10;			//简历筛选  1-2
	public static final int FUNCTION_11= 11;			//个人信息(登陆者本人) 1-1
	public static final int FUNCTION_12 = 12;			//入职员工管理   1-3
	public static final int FUNCTION_13 = 13;			//合同管理  0
	public static final int FUNCTION_14 = 14;			//全通考勤明细  1-4
	public static final int FUNCTION_15 = 15;			//北京物联网考勤明细  1-4
	public static final int FUNCTION_16 = 16;			//成都物联网考勤明细  1-4
	public static final int FUNCTION_17= 17;			//百度考勤明细  1-4
	public static final int FUNCTION_18 = 18;			//考勤管理  1-4
	public static final int FUNCTION_19= 19;			//工资管理  1-5
	public static final int FUNCTION_20 = 20;			//奖惩管理  1-5
	public static final int FUNCTION_21 = 21;			//查询管理  1-5
	public static final int FUNCTION_22 = 22;			//利润测算表  1-6
	public static final int FUNCTION_23 = 23;			//合同管理  1-13
	public static final int FUNCTION_24 = 24;			//转正员工查询   1-3
	public static final int FUNCTION_25 = 25;			//离职员工查询   1-3
	
	///////////////////后续添加
	
//	public static final int FUNCTION_23 = 23;			//通类属性
//	public static final int FUNCTION_24 = 24;			//订单管理
//	public static final int FUNCTION_25 = 25;			//订单查询
//	public static final int FUNCTION_26 = 26;			//RMA查询
//	public static final int FUNCTION_27 = 27;			//申请RMA
//	public static final int FUNCTION_28 = 28;			//销售管理
//	public static final int FUNCTION_29 = 29;			//积分渠道管理
//	public static final int FUNCTION_30 = 30;			//手机渠道管理
//	public static final int FUNCTION_31 = 31;			//商品SKU按钮
//	public static final int FUNCTION_32 = 32;			//销售模式按钮
//	public static final int FUNCTION_33 = 33;			//渠道状态按钮
//	public static final int FUNCTION_34 = 34;			//库存设置按钮
//	public static final int FUNCTION_35 = 35;			//展示价格按钮
//	public static final int FUNCTION_36 = 36;			//跳转ID按钮
//	public static final int FUNCTION_37 = 37;			//销售状态按钮
//	public static final int FUNCTION_38 = 38;			//商家管理
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_39 = 39;			//商户查询
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_40 = 40;			//商户分配
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_41 = 41;			//基本信息
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_42 = 42;			//联系人
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_43 = 43;			//特殊资质
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_44 = 44;			//财务信息
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_45 = 45;			//账户信息
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_46 = 46;			//品牌信息		
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_47 = 47;			//合作信息
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_48 = 48;			//编辑和保存(合作信息子级)
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_49 = 49;			//商户审核
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_50 = 50;			//品牌授权
//	
//	public static final int FUNCTION_51 = 51;			//类目分配
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_52 = 52;			//审核商家展示（商户审核子级）
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_53 = 53;			//品牌展示（品牌授权子级）
//	public static final int FUNCTION_54 = 54;			//类目分配列表展示
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_55 = 55;			//审核商家通过按钮（商户审核子级，与审核商家展示同级）
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_56 = 56;			//品牌审核通过按钮（品牌授权子级）
//	
//	public static final int FUNCTION_57 = 57;			//类目操作按钮
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_58 = 58;			//合作信息展示（合作信息与编辑保存同级）
//	
//	/**
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_59 = 59;			//商家管理TAB标签
//	public static final int FUNCTION_60 = 60;			//订单查询列表，只显示订单信息，不能申请RMA（与申请RMA同级，）
//	public static final int FUNCTION_61 = 61;			//商家考核
//	public static final int FUNCTION_62 = 62;			//审核标准维护（左侧TAB标签）
//	public static final int FUNCTION_63 = 63;			//标准查看
//	public static final int FUNCTION_64 = 64;			//查看当前标准
//	public static final int FUNCTION_65 = 65;			//编辑保存（允许操作当前标准）
//	public static final int FUNCTION_66 = 66;			//标准审核
//	public static final int FUNCTION_67 = 67;			//标准审核状态查看
//	public static final int FUNCTION_68 = 68;			//标准审核操作（允许审核标准）
//	/*     功能变化，以下权限id已做逻辑删除，对应代码已注释
//	public static final int FUNCTION_69 = 69;			//考核管理
//	public static final int FUNCTION_70 = 70;			//查询结果展示（商家考核结果展示，即查看权限）
//	public static final int FUNCTION_71 = 71;			//操作权限（下载模板，导入评分，编辑三项权限）
//	public static final int FUNCTION_72 = 72;			//操作历史查看权限
//	public static final int FUNCTION_73 = 73;			//坑位设置权限
//	*/
//	public static final int FUNCTION_74 = 74;			//商户提报管理
//	public static final int FUNCTION_75 = 75;			//提报管理TAB
//	public static final int FUNCTION_76 = 76;			//商户提报管理
//	public static final int FUNCTION_77 = 77;			//招募公告管理
//	
//	/**
//	 * 财务管理权限
//	 */
//	public static final int FUNCTION_78= 78;
//	
//	/**
//	 * 结算配置TAB
//	 */
//	public static final int FUNCTION_79= 79;
//	
//	/**
//	 *时间结点配置 
//	 */
//	public static final int FUNCTION_80= 80;
//	
//	/**
//	 * 映射关系维护
//	 */
//	public static final int FUNCTION_81= 81;
//	
//	/**
//	 * 品牌映射维护TAB
//	 */
//	public static final int FUNCTION_82= 82;
//	
//	/**
//	 *品牌映射关系维护
//	 */
//	public static final int FUNCTION_83= 83;
//	
//	/**
//	 *商户映射关系维护
//	 */
//	public static final int FUNCTION_84= 84;
//	
//	/**
//	 * 品牌管理
//	 */
//	public static final int FUNCTION_85=85;
//	
//	/**
//	 * 品牌管理TAB
//	 */
//	public static final int FUNCTION_86=86;
//	
//	/**
//	 *  品牌管理（子类）        -------20170814 品牌审核模块新做，删除 88-91 相关功能。 87保留，用于展示权限。另添加品牌审核相关权限
//	 */
//	public static final int FUNCTION_87=87;
//	
//	/**
//	 * 创建新品牌（按钮）               页面功能已删除，权限进行逻辑删除
//	 */
//	public static final int FUNCTION_88=88;
//	
//	/**
//	 * 取消授权（按钮）		页面功能已删除，权限进行逻辑删除
//	 */
//	public static final int FUNCTION_89=89;
//	
//	/**
//	 * 关联（按钮）			页面功能已删除，权限进行逻辑删除
//	 */
//	public static final int FUNCTION_90=90;
//	
//	/**
//	 * 品牌合并			页面功能已删除，权限进行逻辑删除
//	 */
//	public static final int FUNCTION_91=91;
//	
//	/**
//	 * admin权限，只分配给admin系统管理员，使其权限最大
//	 */
//	public static final int FUNCTION_92=92;
//	/**
//	 * 虚拟订单查询
//	 */
//	public static final int FUNCTION_93=93;
//	/**
//	 * 消费记录查询
//	 */
//	public static final int FUNCTION_94=94;
//	
//	/**
//	 * 手机站管理
//	 */
//	public static final int FUNCTION_95=95;
//	
//	/**
//	 * 手机站管理TAB
//	 */
//	public static final int FUNCTION_96=96;
//
//	/**
//	 * 其他操作
//	 */
//	public static final int FUNCTION_97=97;
//	/**
//	 *安全中心
//	 */
//	public static final int FUNCTION_98=98;
//	/**
//	 *积分品台TAB
//	 */
//	public static final int FUNCTION_99=99;
//	/**
//	 * sp账户解锁
//	 */
//	public static final int FUNCTION_100=100;
//	/**
//	 * sp-ip解锁
//	 */
//	public static final int FUNCTION_101=101;
//	/**
//	 * ipp账户解锁
//	 */
//	public static final int FUNCTION_102=102;
//	/**
//	 * ipp-ip解锁
//	 */
//	public static final int FUNCTION_103=103;
//	/**
//	 * 团购TAB
//	 */
//	public static final int FUNCTION_104=104;
//	/**
//	 * 商户账户解锁
//	 */
//	public static final int FUNCTION_105=105;
//	/**
//	 * 商户ip解锁
//	 */
//	public static final int FUNCTION_106=106;
//	/**
//	 * 超时记录查询
//	 */
//	public static final int FUNCTION_107=107;
//	/**
//	 * 超时记录处理
//	 */
//	public static final int FUNCTION_108=108;
//	/**
//	 * 导入（超时）订单
//	 */
//	public static final int FUNCTION_109=109;
//	/**
//	 *（超时订单）审核
//	 */
//	public static final int FUNCTION_110=110;
//	/**
//	 *考核结果查询
//	 */
//	public static final int FUNCTION_111=111;
//	/**
//	 *考核文件管理
//	 */
//	public static final int FUNCTION_112=112;
//	/**
//	 *考核商户管理
//	 */
//	public static final int FUNCTION_113=113;
//	/**
//	 *考核订单下载
//	 */
//	public static final int FUNCTION_114=114;
//	/**
//	 *导入考核结果按钮
//	 */
//	public static final int FUNCTION_115=115;
//	/**
//	 *考核文件审核按钮
//	 */
//	public static final int FUNCTION_116=116;
//	/**
//	 *更新考核文件按钮
//	 */
//	public static final int FUNCTION_117=117;
//	/**
//	 *  商品审核TAB
//	 */
//	public static final int FUNCTION_118=118;
//	/**
//	 *审核结果查询
//	 */
//	public static final int FUNCTION_119=119;
//	/**
//	 *结果查询展示
//	 */
//	public static final int FUNCTION_120=120;
//	/**
//	 *批量复审权限
//	 */
//	public static final int FUNCTION_121=121;
//	/**
//	 *通用积分管理
//	 */
//	public static final int FUNCTION_122=122;
//	/**
//	 *用户管理TAB
//	 */
//	public static final int FUNCTION_123=123;
//	/**
//	 *用户信息
//	 */
//	public static final int FUNCTION_124=124;
//	/**
//	 *用户基本信息查看
//	 */
//	public static final int FUNCTION_125=125;
//	/**
//	 *用户信息页面展示
//	 */
//	public static final int FUNCTION_126=126;
//	/**
//	 *类目信息
//	 *作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_127=127;
//	/**
//	 *结算关系管理TAB
//	 */
//	public static final int FUNCTION_128=128;
//	/**
//	 *创建结算单位
//	 */
//	public static final int FUNCTION_129=129;
//	/**
//	 * 设置关联商户
//	 */
//	public static final int FUNCTION_130=130;
//	/**
//	 * 删除结算单位
//	 */
//	public static final int FUNCTION_131=131;
//	
//	/**
//	 * 展位费结算
//	 */
//	public static final int FUNCTION_132=132;
//	
//	/**
//	 * 文件导入（展位费）
//	 */
//	public static final int FUNCTION_133=133;
//	
//	/**
//	 * 文件审核（展位费）
//	 */
//	public static final int FUNCTION_134=134;
//	
//	/**
//	 * 通过审核（展位费上传文件）
//	 */
//	public static final int FUNCTION_135=135;
//	
//	/**
//	 * 不通过审核（展位费上传文件）
//	 */
//	public static final int FUNCTION_136=136;
//	
//	/**
//	 * 调账管理TAB
//	 */
//	public static final int FUNCTION_137=137;
//	
//	/**
//	 * 调账申请
//	 */
//	public static final int FUNCTION_138=138;
//	
//	/**
//	 * 调账审核
//	 */
//	public static final int FUNCTION_139=139;
//	
//	/**
//	 * 调账申请审核权限
//	 */
//	public static final int FUNCTION_140=140;
//	
//	/**
//	 * 审核列表展示
//	 */
//	public static final int FUNCTION_141=141;
//	
//	/**
//	 * 收款管理TAB
//	 */
//	public static final int FUNCTION_142=142;
//	
//	/**
//	 * 返佣费收款
//	 */
//	public static final int FUNCTION_143=143;
//	
//	/**
//	 * 展位费收款
//	 */
//	public static final int FUNCTION_144=144;
//	
//	/**
//	 * 财务报表导出（返佣费）
//	 */
//	public static final int FUNCTION_145=145;
//	
//	/**
//	 * 发布数据（返佣费）
//	 */
//	public static final int FUNCTION_146=146;
//	
//	/**
//	 * 提交发票申请（返佣费）
//	 */
//	public static final int FUNCTION_147=147;
//	
//	/**
//	 * 批量确认收款（返佣费）
//	 */
//	public static final int FUNCTION_148=148;
//	
//	/**
//	 * 财务报表导出（展位费）
//	 */
//	public static final int FUNCTION_149=149;
//	
//	/**
//	 * 提交发票申请（展位费）
//	 */
//	public static final int FUNCTION_150=150;
//	
//	/**
//	 * 批量确认收款（展位费）
//	 */
//	public static final int FUNCTION_151=151;
//	
//	/**
//	 * 结算单查询TAB
//	 * 只在页面限制，controller方法多处调用，不能限制。
//	 */
//	public static final int FUNCTION_152=152;
//	
//	/**
//	 * 付款管理TAB
//	 */
//	public static final int FUNCTION_153=153;
//	
//	/**
//	 * 财务报表导出（积分付款）
//	 */
//	public static final int FUNCTION_154=154;
//	
//	/**
//	 * 发布数据（积分付款）
//	 */
//	public static final int FUNCTION_155=155;
//	
//	/**
//	 * 发布暂停或解除（积分付款）
//	 */
//	public static final int FUNCTION_156=156;
//
//	/**
//	 * 通知商户开票（积分付款）
//	 */
//	public static final int FUNCTION_157=157;
//
//	/**
//	 * 编辑通知开票模板（积分付款）
//	 */
//	public static final int FUNCTION_158=158;
//
//	/**
//	 * 暂不通知或解除（积分付款）
//	 */
//	public static final int FUNCTION_159=159;
//
//	/**
//	 * 不结算（积分付款）
//	 */
//	public static final int FUNCTION_160=160;
//
//	/**
//	 * 审核发票（积分付款）
//	 */
//	public static final int FUNCTION_161=161;
//
//	/**
//	 * 导出发票汇总（积分付款）
//	 */
//	public static final int FUNCTION_162=162;
//
//	/**
//	 * 确认付款（积分付款）
//	 */
//	public static final int FUNCTION_163=163;
//	
//	/**
//	 * 通知发布管理TAB
//	 */
//	public static final int FUNCTION_164=164;
//	
//	/**
//	 * 通知发布管理
//	 */
//	public static final int FUNCTION_165=165;
//	
//	/**
//	 * 新建公告
//	 */
//	public static final int FUNCTION_166=166;
//	
//	/**
//	 * 编辑公告
//	 */
//	public static final int FUNCTION_167=167;
//	
//	/**
//	 * 仅查看公告权限
//	 */
//	public static final int FUNCTION_168=168;
//	
//	
//	/**
//	 * 品牌审核  父权限 id = 86
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_169=169;
//	
//	/**
//	 * 品牌审核仅查看权限，只做页面显示用，代码实际为其父权限
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_170=170;
//	
//	/**
//	 * 品牌审核按钮权限
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_171=171;
//	
//	/**
//	 * 商户年审
//	 */
//	public static final int FUNCTION_172=172;
//	
//	/**
//	 * 年审考核成绩
//	 */
//	public static final int FUNCTION_173=173;
//	
//	/**
//	 * 年度结算金额
//	 */
//	public static final int FUNCTION_174=174;
//	
//	/**
//	 * 年度签约品类
//	 */
//	public static final int FUNCTION_175=175;
//	
//	/**
//	 * 严重违规记录
//	 */
//	public static final int FUNCTION_176=176;
//	
//	/**
//	 * 未上线时间统计
//	 */
//	public static final int FUNCTION_177=177;
//	
//	/**
//	 * 年审资料下载
//	 */
//	public static final int FUNCTION_178=178;
//	
//	/**
//	 * 年审结果发布
//	 */
//	public static final int FUNCTION_179=179;
//	
//	/**
//	 * 年审结果查询
//	 */
//	public static final int FUNCTION_180=180;
//	
//	/**
//	 * 签约品类数据导入
//	 */
//	public static final int FUNCTION_181=181;
//	
//	/**
//	 * 违规记录操作
//	 */
//	public static final int FUNCTION_182=182;
//	
//	/**
//	 * 未上线编辑保存
//	 */
//	public static final int FUNCTION_183=183;
//	
//	/**
//	 * 下载文件权限
//	 */
//	public static final int FUNCTION_184=184;
//	
//	/**
//	 * 导出年审结果
//	 */
//	public static final int FUNCTION_185=185;
//	
//	/**
//	 * 导入年审结果
//	 */
//	public static final int FUNCTION_186=186;
//	
//	/**
//	 * 操作年审结果
//	 */
//	public static final int FUNCTION_187=187;
//	
//	/**
//	 * 签约品类查看（增加查看节点，在没有按钮级权限时可以查看页面）
//	 */
//	public static final int FUNCTION_188=188;
//	
//	/**
//	 * 违规记录查看（增加查看节点，在没有按钮级权限时可以查看页面）
//	 */
//	public static final int FUNCTION_189=189;
//	
//	/**
//	 * 未上线统计查看（增加查看节点，在没有按钮级权限时可以查看页面）
//	 */
//	public static final int FUNCTION_190=190;
//	
//	/**
//	 * 年审资料下载查看（增加查看节点，在没有按钮级权限时可以查看页面）
//	 */
//	public static final int FUNCTION_191=191;
//	
//	/**
//	 * 年审结果发布查看（增加查看节点，在没有按钮级权限时可以查看页面）
//	 */
//	public static final int FUNCTION_192=192;
//	
//	/**
//	 * 和优选管理
//	 */
//	public static final int FUNCTION_193=193;
//	
//	
//	/**
//	 * 轮播图管理
//	 */
//	public static final int FUNCTION_194=194;
//	
//	/**
//	 *轮播图操作
//	 */
//	public static final int FUNCTION_195=195;
//	
//	/**
//	 * 高端精品管理
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_196=196;
//	
//	/**
//	 * 高端精品操作
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_197=197;
//	
//	
//	/**
//	 * 品牌旗舰店管理
//	 */
//	public static final int FUNCTION_198=198;
//	
//	
//	/**
//	 * 品牌旗舰店操作
//	 */
//	public static final int FUNCTION_199=199;
//	
//	/**
//	 * 楼层及通栏管理
//	 */
//	public static final int FUNCTION_200=200;
//	
//	
//	/**
//	 * 楼层及通栏操作
//	 */
//	public static final int FUNCTION_201=201;
//	
//	/**
//	 *标签管理 
//	 */
//	public static final int FUNCTION_202=202;
//	
//	/**
//	 * 标签操作
//	 */
//	public static final int FUNCTION_203=203;
//	
//	/**
//	 * 轮播查看
//	 */
//	public static final int FUNCTION_204=204;
//	
//	/**
//	 * 高端精品查看
//	 * 作废，页面controller暂时保留
//	 */
//	public static final int FUNCTION_205=205;
//	
//	/**
//	 * 品牌旗舰店查看
//	 */
//	public static final int FUNCTION_206=206;
//	
//	/**
//	 * 楼层通栏查看
//	 */
//	public static final int FUNCTION_207=207;
//	
//	
//	/**
//	 * 标签查看
//	 */
//	public static final int FUNCTION_208=208;
//	
//	/**
//	 * 商家管理TAB（2018-05-22）
//	 */
//	public static final int FUNCTION_209=209;
//	
//	/**
//	 * 商家查询（2018-05-22）
//	 */
//	public static final int FUNCTION_210=210;
//	
//	/**
//	 * 入驻查询（2018-05-22）
//	 */
//	public static final int FUNCTION_211=211;
//	
//	/**
//	 * 基础信息审核（2018-05-22）
//	 */
//	public static final int FUNCTION_212=212;
//	
//	/**
//	 * 品牌信息审核（2018-05-22）
//	 */
//	public static final int FUNCTION_213=213;
//	
//	/**
//	 * 入驻评审（2018-05-22）
//	 */
//	public static final int FUNCTION_214=214;
//	
//	/**
//	 * 合同签约（2018-05-22）
//	 */
//	public static final int FUNCTION_215=215;
//	
//	/**
//	 * 保障金缴纳（2018-05-22）
//	 */
//	public static final int FUNCTION_216=216;
//	
//	/**
//	 * 商家审核（2018-05-22）
//	 */
//	public static final int FUNCTION_217=217;
//	
//	/**
//	 * 品牌审核新
//	 */
//	public static final int FUNCTION_218=218;
//	
//	/**
//	 * 品牌审核结果查询
//	 */
//	public static final int FUNCTION_219=219;
//	
//	/**
//	 * 新增品类审核
//	 */
//	public static final int FUNCTION_220=220;
//	
//	
//	/**
//	 * 品类审核结果查询
//	 */
//	public static final int FUNCTION_221=221;
	
}
