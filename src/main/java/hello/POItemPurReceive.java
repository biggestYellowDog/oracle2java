/*
 *  $Revision: 1 $
 */
package hello;

//import com.redprairie.moca.MocaException;
//import com.redprairie.moca.MocaResults;
import java.text.SimpleDateFormat;

import com.redprairie.moca.MocaException;
import com.redprairie.moca.MocaResults;
import com.redprairie.mtf.MtfConstants;
import com.redprairie.mtf.MtfConstants.EFlow;
import com.redprairie.mtf.exceptions.XFailedRequest;
import com.redprairie.mtf.exceptions.XFormAlreadyOnStack;
import com.redprairie.mtf.exceptions.XInvalidArg;
import com.redprairie.mtf.exceptions.XInvalidRequest;
import com.redprairie.mtf.exceptions.XInvalidState;
import com.redprairie.mtf.exceptions.XMissingObject;
import com.redprairie.mtf.foundation.presentation.ACommand;
import com.redprairie.mtf.foundation.presentation.AFormLogic;
import com.redprairie.mtf.foundation.presentation.CWidgetActionAdapter;
import com.redprairie.mtf.foundation.presentation.ICommand;
import com.redprairie.mtf.foundation.presentation.IContainer;
import com.redprairie.mtf.foundation.presentation.IDisplay;
import com.redprairie.mtf.foundation.presentation.IEntryField;
import com.redprairie.mtf.foundation.presentation.IForm;
import com.redprairie.mtf.foundation.presentation.IFormSegment;
import com.redprairie.mtf.foundation.presentation.IInteractiveWidget;
import com.redprairie.mtf.foundation.presentation.IWidgetActionValidator;
import com.redprairie.wmd.WMDConstants;

/**
 * 
 * POItem 采购订单项次 送货单收货
 *  SRM送货单收货
 * @author liaofw
 * @version 1.0
 * @last modify date  2017.12.26
 */
public class POItemPurReceive extends AFormLogic {

	// 定义实例变量
	/**
	 * 组件验证器 一般不用动
	 */
	public IWidgetActionValidator actUsrReceiptPutaway;
	/**
	 * 定义表单组件 也一般不用动
	 */
	public IFormSegment segDef;

	/** 采购单录入框 */
	private IEntryField efPurOrder;
	/* 采购单录入验证器 */
	private IWidgetActionValidator actPurOrder;

	/** 采购单项次显示框 */
	private IEntryField efPOItem;
	/** 采购单项次验证器 */
	private IWidgetActionValidator actPOItem;

	/** 容器号录入框 */
	private IEntryField efLodnum;
	/* 容器号录入验证器 */
	private IWidgetActionValidator actLodnum;

	/** 料号 */
	private IEntryField efPrtnum;
	/* 料号录入验证器 */
	private IWidgetActionValidator actPrtnum;
		
	/** 商品描述 */
	private IEntryField efPrtDsc;
	/** 工厂代号 */
	private IEntryField efFactCode;
	/** 库存地点 */
	private IEntryField efAreaCode;
	/** 是否急料 */
	private IEntryField efUrgentFlag;
	/** ABC */
	private IEntryField efABC;
	/** Unit */
	private IEntryField efUnit;

	/** 特殊防护 */
	private IEntryField efSplDefend;
	/** 应收 */
	private IEntryField efExpQty;
	/** 已收 */
	private IEntryField efIdnQty;

	/** 实收 */
	private IEntryField efActRcvQty;
	/* 实收验证器 */
	private IWidgetActionValidator actActRcvQty;

	/** 已扫 */
	private IEntryField efScanQty;
	/* 已扫验证器 */
	// private IWidgetActionValidator actScanQty;

	/** 收货日期 */
	private IEntryField efRcvDate;
	/** 收货日期验证器 */
	private IWidgetActionValidator actRcvDate;

	/** 标贴张数 */
	private IEntryField efLabelNum;
	/** 标贴张数验证器 */
	private IWidgetActionValidator actLabelNum;


	/* 行号 有的需要invlin来 辅助定位一行 有的不需要 比如 SRM收货 */
	private String invlin;
	/* 是否序列号管理隐藏域 不用界面加隐藏域 直接定义个String 或int 即可 */
	private int ucSertyp;
	/* 货主 工厂代号 */
	private String prt_clientId;
	/* 交货日期 */
	private String deliveryDate;
	/* 库存地点 */
	private String invAttrStr1;
	/* 单位类型 */
	private String billtype;
	/* 批次号 */
	//private String lotnum;	
	
	/** 批次号 隐藏域  */
	private IEntryField eflotnum;
	
	/** 批次号 行序号  */
	private IEntryField efseqnum;
	
	/** 取消命令 */
	private ICommand cmdFkeyBack;
	
    /**F2*/
    private ICommand  cmdFkeyList;
    
    /**F3 可以快捷删除当前框的信息*/
    private ICommand  cmdFkeyClear;
	
	/* 单据类型固定值 加单据类型 是因为在 共用的MOCA 里面设定了需要输入单据类型参数 */
	private String po_Norm = "Po_Norm";

	/**
	 * 默认构造方法,初始化表单组件
	 * 
	 * @param IDisplay display
	 *            
	 */
	public POItemPurReceive(IDisplay _display) throws Exception {

		super(_display);

		// 创建表单 关联界面properties文件布局配置
		frmMain = display.createForm("PO_ITEM_PUR_RECEIVE");

		// 设置表单标题,需要在les_mls_cat表中翻译
		frmMain.setTitle("ttlPOitemPurReceive");

		// 为表单创建动作验证,此验证是表单进入和离开的逻辑验证 这个只是起到在表单进入和离开的时候的一个清屏的作用
		// 此外，其他EntryField的验证也是以这种形式添加
		actUsrReceiptPutaway = this.new UsrReceiptPutawayActions();
		frmMain.addWidgetAction(actUsrReceiptPutaway);

		// 创建表单界面组件
		segDef = frmMain.createSegment("segDef", false);
		createWidgets(segDef);

		// 表单绑定F1命令
		frmMain.unbind(frmMain.getCancelCommand());
		cmdFkeyBack = this.new FkeyBackCommand();
		cmdFkeyBack.setVisible(false);
		frmMain.bind(cmdFkeyBack);
		cmdFkeyBack.bind(MtfConstants.VK_FKEY_BACK);
		
		// 表单绑定F3命令    可以绑定在全局构造里  也可以考虑绑定在某一个录入框中
      /*  cmdFkeyClear = this.new F3keyBackCommand();
        cmdFkeyClear.setVisible(false);
		frmMain.bind(cmdFkeyClear);
		cmdFkeyClear.bind(MtfConstants.VK_FKEY_NEXTNUM); */
		/*不在表单构造中进行绑定  是因为只是在料号处才启动这个绑定才合理  而且efprtnum.bind()也走不通*/
	}

	/**
	 * Display and run the form
	 */
	public void run() throws XInvalidState, XInvalidRequest, XInvalidArg,
	XFailedRequest, XFormAlreadyOnStack {
		frmMain.interact(); // 重写run方法，启动表单
	}

	/**
	 * 创建表单组件的方法（内部私有的）
	 * 
	 * @param IFormSegment
	 *            formSegment
	 */
	private void createWidgets(IFormSegment segDef) throws Exception {

		efPurOrder = segDef.createEntryField("PurOrder", "lblPurOrder");
		efPurOrder.setEnabled(true);
		efPurOrder.setVisible(true);
		efPurOrder.setEntryRequired(true);

		actPurOrder = new LeaPurOrder();
		efPurOrder.addWidgetAction(actPurOrder);

		efPOItem = segDef.createEntryField("POItem", "lblPOItem");
		efPOItem.setEnabled(true);
		efPOItem.setVisible(true);
		efPOItem.setEntryRequired(true);

		actPOItem = new LeaPOItem();
		efPOItem.addWidgetAction(actPOItem);

		efLodnum = segDef.createEntryField("elodnum", "lblLodnum");
		efLodnum.setEnabled(true);
		efLodnum.setVisible(true);
		efLodnum.setEntryRequired(true);

		actLodnum = new LeaLodnumActions();
		efLodnum.addWidgetAction(actLodnum);

		efPrtnum = segDef.createEntryField("Prtnum", "lblPrtnumMR");
		efPrtnum.setEnabled(true);
		efPrtnum.setVisible(true);
		efPrtnum.setEntryRequired(true);
		
		actPrtnum = new LeaPrtnumActions();
		efPrtnum.addWidgetAction(actPrtnum);
		

		efPrtDsc = segDef.createEntryField("PrtDsc");
		efPrtDsc.setEnabled(false);
		efPrtDsc.setVisible(true);
		efPrtDsc.setEntryRequired(false);
		// 工厂代号
		efFactCode = segDef.createEntryField("FactCode");
		efFactCode.setEnabled(false);
		efFactCode.setVisible(true);
		efFactCode.setEntryRequired(false);
		// 库存地点
		efAreaCode = segDef.createEntryField("AreaCode");
		efAreaCode.setEnabled(false);
		efAreaCode.setVisible(true);
		efAreaCode.setEntryRequired(false);

		efUrgentFlag = segDef.createEntryField("UrgentFlag");
		efUrgentFlag.setEnabled(false);
		efUrgentFlag.setVisible(true);
		efUrgentFlag.setEntryRequired(false);

		efABC = segDef.createEntryField("efABC");
		efABC.setEnabled(false);
		efABC.setVisible(true);
		efABC.setEntryRequired(false);

		efUnit = segDef.createEntryField("Unit");
		efUnit.setEnabled(false);
		efUnit.setVisible(true);
		efUnit.setEntryRequired(false);

		efSplDefend = segDef.createEntryField("SplDefend", "lblSplDefend");
		efSplDefend.setEnabled(false);
		efSplDefend.setVisible(true);
		efSplDefend.setEntryRequired(false);

		efExpQty = segDef.createEntryField("ExpQty", "lblExpQty");
		efExpQty.setEnabled(false);
		efExpQty.setVisible(true);
		efExpQty.setEntryRequired(false);

		efIdnQty = segDef.createEntryField("IdnQty", "lblIdnQty");
		efIdnQty.setEnabled(false);
		efIdnQty.setVisible(true);
		efIdnQty.setEntryRequired(false);

		efActRcvQty = segDef.createEntryField("ActRcvQty", "lblActRcvQty");
		efActRcvQty.setEnabled(true);
		efActRcvQty.setVisible(true);
		efActRcvQty.setEntryRequired(false);

		actActRcvQty = new LeaActRcvQtyActions();
		efActRcvQty.addWidgetAction(actActRcvQty);

		efScanQty = segDef.createEntryField("ScanQty", "lblScanQty");
		efScanQty.setEnabled(false);
		efScanQty.setVisible(true);
		efScanQty.setEntryRequired(false);

		// actScanQty = new LeaScanQtyActions();
		// efScanQty.addWidgetAction(actScanQty);

		efRcvDate = segDef.createEntryField("RcvDate", "lblRcvDate");
		efRcvDate.setEnabled(true);
		efRcvDate.setVisible(true);
		efRcvDate.setEntryRequired(false);

		actRcvDate = new LeaRcvDateActions();
		efRcvDate.addWidgetAction(actRcvDate);

		efLabelNum = segDef.createEntryField("LabelNum", "lblLabelNum");
		efLabelNum.setEnabled(true);
		efLabelNum.setVisible(true);
		efLabelNum.setEntryRequired(true);

		/** 标贴张数验证器 */
		actLabelNum = new LeaLabelNumActions();
		efLabelNum.addWidgetAction(actLabelNum);
		
		eflotnum = segDef.createEntryField("lotnum");
		eflotnum.setVisible(false);
		
		efseqnum = segDef.createEntryField("seqnum");
		efseqnum.setVisible(false);
		
	}

	/**
	 * Defines extended ACommand for FkeyBack F1命令定义 返回动作
	 */
	private class FkeyBackCommand extends ACommand {

		private static final long serialVersionUID = 1L;

		/**
		 * Prime constructor. 构造方法 在如下中出现了F1 FkeyBack 1
		 */
		public FkeyBackCommand() {
			super("cmdFkeyBack", "FkeyBack", MtfConstants.FKEY_BACK_CAPTION,
					'1');
		}

		// 如下是这个FkeyBackCommand内部私有类定义的execut方法，这个方法里就是用内置对象frmMain调用了个formBack的方法而已
		public void execute(IContainer _container) throws XFormAlreadyOnStack {
			// efCarrierSetting.setEnabled(true); 如果按F1返回上级菜单，那么承运商设定将会重新生效
			frmMain.formBack();
		}	
		
	}
	
	
	
    /**
     * 定义一个键进行F3 快捷删除当前框信息
     */
    private class F3keyBackCommand extends ACommand {
		private static final long serialVersionUID = 1L;
		/**
         * Prime constructor.
         */
        public F3keyBackCommand() {
            super("cmdFkeyBack", "FkeyBack",  "cmdClearCurrentText", '3');   
        }
              
		@Override
		public void execute(IContainer arg0) throws XFormAlreadyOnStack,
				NullPointerException, ClassNotFoundException, XInvalidState,
				XInvalidRequest, XInvalidArg, XFailedRequest, MocaException,
				XMissingObject {
			// TODO Auto-generated method stub
			try {
				efPrtnum.clear();
			} catch (Exception e) {				
			}			
		}
    }

	
	
	   /**
     * Defines extended ACommand for FkeyBack cmdFkeyList
     */
    private class FkeyListCommand extends ACommand {

		private static final long serialVersionUID = 1L;

		/**
         * Prime constructor.
         */
        public FkeyListCommand() {
            super("cmdFkeyList", "FkeyList",  WMDConstants.FKEY_LIST_CAPTION, '2');
        }
        
        public void execute(IContainer _container) {
				try {
					jumpSnCollection();
				} catch (Exception e) {
					frmMain.promptMessageAnyKey(e.getMessage());
				}
        }
    }
    
    
	/**
	 * This class contains the entry/exit actions for the form (frmMain) 验证 ！
	 */
	private class UsrReceiptPutawayActions extends CWidgetActionAdapter {

		public boolean onFormEntry(IForm _frm) throws Exception {
			// Reset form
			frmMain.clearForm();
			return true;
		}

		public boolean onFormExit(IForm _frm) throws Exception {
			frmMain.clearForm();				
			frmMain.formBack();
			return false;
		}
	}

	/**
	 * 批次号录入框 继承CWidgetActionAdapter动作控件 重写回车事件
	 */
	private class LeaPurOrder extends CWidgetActionAdapter {
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {
			int retStatus = 0;
			String message = "";
			String cmdStr = "";
			MocaResults rs = session.getGlobalMocaResults();
			String BillNo = efPurOrder.getText().trim();
			
			if(1 == checkStr(BillNo,efPurOrder)){    //单引号验证
				  return false;
			}	
			
			if("".equals(BillNo)){
				frmMain.promptMessageAnyKey("订单号请输入值！");
				return false;
			}	
									
			try {
				cmdStr = "validate info by invnum and invtyp sum"
						+ " where wh_id = '"+ display.getVariable("global.wh_id") + "'"
						+ " and invnum ='" + BillNo + "'" 
						+ " and invtyp = '"+ po_Norm + "'"
						;
				rs = session.executeDSQL(cmdStr);
			} catch (MocaException e) {
				retStatus = e.getErrorCode();
				message = e.getMessage();
			}

			if (retStatus != 0 && retStatus != 90032 && retStatus != 90033) {  //其他异常
				display.beep();
				frmMain.displayErrorMessage();
				frmMain.clearForm();
				return false;
			} else if (retStatus == 90032 || retStatus == 90033) {    //32单号未查询到    33单号类型不匹配
				frmMain.promptMessageAnyKey(message);
				frmMain.clearForm();
				return false;
			} else {          //有返回值 flag有值
				if (rs.hasNext()) {
					rs.next();
					String rcvd_flag = rs.getString("rcvd_flag"); // 如果rcvd_flag为1则提示：'此单已收货完毕，请检查！'
						if("1".equals(rcvd_flag)){
							frmMain.promptMessageAnyKey("此单已收货完毕，请检查！");						
						}			
					String invlin2 = rs.getString("invlin");
					String prtnum2 = null;         //第一次只是用单号和项次去带  料号不传
					int sq = 1 ;
					return lookforBillno(ef,invlin2,prtnum2,sq);     //调用赋值方法赋值    收货完成也可以查看
				}else{
					return false;   //极小可能走此
				}
			}	
         }  
	}


  	/*17.12.06和易工确认的顺序 是单号  物料号  项次 
	   扫描单据后  带出一行默认的 单号 料号  项次     
	   料号可能变化   所以料号离开事件查询更新是不带 项次条件的   如果有多行  自动带出最小的行号
	   项次也可能认为变化   所以项次离开查询更新同样不带 物料条件  单号加项次再进行分组是可以确定返回一行的*/
		
	/* 物料号验证器 */
	private class LeaPrtnumActions extends CWidgetActionAdapter {
		/*进入绑定F3 键   离开就解绑 */
	    public boolean onFieldEntry(IInteractiveWidget _ef) throws Exception {	    		    	
	        //绑定F3	    	
	        cmdFkeyClear = new F3keyBackCommand();   //追加了这个 是否之前为创建cmdFkeyList对象，导致方法错误，方法不能及时触发！
	        frmMain.bind(cmdFkeyClear);
	        cmdFkeyClear.bind(MtfConstants.VK_FKEY_NEXTNUM);   
	    	//lookSnCnt();
			return true;
	    }	
	    
		// 表单绑定F3命令    可以绑定在全局构造里  也可以考虑绑定在某一个录入框中
	      /*  cmdFkeyClear = this.new F3keyBackCommand();
	        cmdFkeyClear.setVisible(false);
			frmMain.bind(cmdFkeyClear);
			cmdFkeyClear.bind(MtfConstants.VK_FKEY_NEXTNUM); */
			/*不在表单构造中进行绑定  是因为只是在料号处才启动这个绑定才合理  而且efprtnum.bind()也走不通*/
		
		/*进入一下可以更新     出来也可以更新  出来更新主要是可能人工改了料号！*/
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {			
	        try {
	        	//cmdFkeyClear = new F3keyBackCommand();
		        frmMain.unbind(cmdFkeyClear);                        //菜单解绑定功能   
		        //frmMain.promptMessageAnyKey("测试：有执行解绑定1");
	        	cmdFkeyClear.unbind(MtfConstants.VK_FKEY_NEXTNUM);   //功能类解绑定 物理的键 
	        	//frmMain.promptMessageAnyKey("测试：有执行解绑定2");
			} catch (Exception e) {
				// TODO: 这里可能没有绑定  这种情况下解绑会报异常   所以捕获下异常
			}	
			
			String PurOrderStr = efPurOrder.getText().trim();
			String PrtnumStr = efPrtnum.getText().trim();
			if(checkStr(PrtnumStr,efPrtnum)==1){               //单引号检查
				return false;
				}				
			if("".equals(PurOrderStr)){
				frmMain.promptMessageAnyKey("单据号不能为空！");   // 每次离开的时候用单号  料号去查下 (不用项次  因为料号可能变)
				return false;
			}			
			int sq = 2 ;
			String invlin2 = null;    //料号这里只是用单号和项次去带  项次参数不传 
			return lookforBillno(ef,invlin2,PrtnumStr,sq);     //调用赋值方法赋值    收货完成也可以查看            
		}
	}
	

	
    /**
   	 * 单据 项次行号验证器
     */		
	private class LeaPOItem extends CWidgetActionAdapter {
		/*进入一下可以更新     出来也可以更新  出来更新主要是可能人工改了项次 */
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {
			String PurOrder = efPurOrder.getText().trim();
			invlin = efPOItem.getText().trim();
			if(checkStr(invlin,efPOItem)==1){   //单引号检查
				return false;
				}				
			if("".equals(PurOrder)){
				frmMain.promptMessageAnyKey("单据号不能为空！");
				return false;
			}			
			int sq = 3;
			String prtnum2 = null;         //项次这里只是用单号和项次去带  料号参数不传
			return lookforBillno(ef,invlin,prtnum2,sq);     //调用赋值方法赋值    收货完成也可以查看
		}
	}

	/**
	 * 容器号录入框 继承CWidgetActionAdapter动作控件 重写回车事件
	 */
	private class LeaLodnumActions extends CWidgetActionAdapter {
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {
			int retStatus = 0;
			String cmdStr = "";

			String lodnum_s = efLodnum.getText().trim() ;
			String Prtnum_s = efPrtnum.getText().trim() ;

			if(checkStr(lodnum_s,efLodnum)==1){  //单引号检查 					
				return false;
			}
			if("".equals(lodnum_s)){
				frmMain.promptMessageAnyKey("请输入容器号！");
				return false;
			}
			int flag = 1 ;  //是否进行楼层验证的标志							

			//if (deliveryDate != null && !deliveryDate.equals(""))			
			if("".equals(prt_clientId)|| null == prt_clientId){
				frmMain.promptMessageAnyKey("商品对应货主为空！无法进行楼层验证！");
				flag = 0;
			}				
			if("".equals(invAttrStr1)|| null == invAttrStr1){
				frmMain.promptMessageAnyKey("商品对应库存地点为空！无法进行楼层验证！");
				flag = 0;
			}
			if("".equals(Prtnum_s)|| null == Prtnum_s){
				frmMain.promptMessageAnyKey("商品对应库存地点为空！无法进行楼层验证！");
				flag = 0;
			}

			if(flag ==1){
				try{
					cmdStr = "validate usr lodnum for prtnum floor"
							+ " where wh_id = '"+display.getVariable("global.wh_id")+"'"
							+ " and usr_id = '"+display.getVariable("global.usr_id")+"'"
							+ " and prt_client_id = '"+prt_clientId+"'"
							+"  and inv_attr_str1 = '"+invAttrStr1+"'"
							+ " and prtnum = '"+efPrtnum.getText()+"'"
							+ " and lodnum = '"+efLodnum.getText()+"'";
					session.executeDSQL(cmdStr);
				}catch(MocaException e){
					retStatus = e.getErrorCode();
					//message = e.getMessage();
				}
				if(retStatus != 0 && retStatus != 90021){//90021当前容器无库存，可用
					frmMain.displayErrorMessage();
					display.beep();
					efLodnum.clear();
					return false;
				}else{
					//frmMain.displayMessage(message);
				}
			}
			return true;
		}
	}


    /**
	  * 实收数量验证 
	  */
	private class LeaActRcvQtyActions extends CWidgetActionAdapter {
	    public boolean onFieldEntry(IInteractiveWidget _ef) throws Exception {
	        if(ucSertyp==1){                     //只有在是序列号管理的才绑定F2	    	
	    	cmdFkeyList = new FkeyListCommand();   //追加了这个 是否之前为创建cmdFkeyList对象，导致方法错误，方法不能及时触发！
	        frmMain.bind(cmdFkeyList);
	        cmdFkeyList.bind(WMDConstants.VK_FKEY_LIST);   
	    	lookSnCnt();
	        }else if(ucSertyp==0){             //如果不是序列号管理的 解绑F2
		        try {
	                cmdFkeyList.unbind(WMDConstants.VK_FKEY_LIST);
			        frmMain.unbind(cmdFkeyList);
				} catch (Exception e) {
					// TODO: 这里可能没有绑定  这种情况下解绑会报异常   所以捕获下异常
				}	        	
	        }
			return true;
	    }   
		
		
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {
			
	        //离开的时候进行解除绑定
			try {
                cmdFkeyList.unbind(WMDConstants.VK_FKEY_LIST);
		        frmMain.unbind(cmdFkeyList);
			} catch (Exception e) {
				// TODO: 这里可能没有绑定  这种情况下解绑会报异常   所以捕获下异常
			}	
			
			int actRcvQty = 0;
			try {
				if (Integer.parseInt(efActRcvQty.getText()) <= 0) {
					frmMain.promptMessageAnyKey("收货数量需大于0!");
					efActRcvQty.clear();
					return false;
				}
			} catch (Exception e) {
				frmMain.promptMessageAnyKey("请输入有效数值!");
				efActRcvQty.clear();
				return false;
			}
			actRcvQty = Integer.parseInt(efActRcvQty.getText());
			// 为了防止出现015这样的数字
			efActRcvQty.setText(String.valueOf(actRcvQty));
						
			int expQty = Integer.parseInt(efExpQty.getText());
			int idnQty = Integer.parseInt(efIdnQty.getText());
			int unQty = expQty - idnQty;

			if (actRcvQty > unQty) {
				display.beep();
				frmMain.promptMessageAnyKey("实收数量不能大于未收数量!");
				efActRcvQty.clear();
				return false;
			}
			
			if (ucSertyp == 1) { // 如果需要采集我才走判断程序 否则直接true
				int scanQty = 0; // 扫描数量变量 如果不为空 即转为int
				if (!"".equals(efScanQty.getText())) { // 如果已扫能取到值 则修改已扫变量的值
					scanQty = Integer.parseInt(efScanQty.getText());
				}

				// 校验当前物料是否为SN管理的物料
				if (actRcvQty + idnQty == scanQty) { // 如果相等光标下跳 不采集 如果小于
														// 提示数量错误return false
														// 如果大于强跳入扫描采集
					return true;
				} else if (actRcvQty + idnQty < scanQty) {
					frmMain.promptMessageAnyKey("实扫数量大于实收数量,请检查！");
					return false;
				} else {
					return jumpSnCollection();
				}
			} else {
				return true;
			}
		}
	}

	
	
	private class LeaRcvDateActions extends CWidgetActionAdapter {

		public boolean onFieldExit(IInteractiveWidget _ef) throws Exception {
			String rcvDate = efRcvDate.getText();
			if(!rcvDate.startsWith("2")){
				frmMain.promptMessageAnyKey("日期格式不符合要求！");
				return false;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			try{
				format.parse(rcvDate);
			}catch(Exception e){
				display.beep();
				frmMain.promptMessageAnyKey("日期格式不符合要求！");
				return false;
			}
			return true;
		}	
	}

	
	private class LeaLabelNumActions extends CWidgetActionAdapter {
		public boolean onFieldExit(IInteractiveWidget ef) throws Exception {
		
			try {
				if (Integer.parseInt(efLabelNum.getText()) < 0) {
					frmMain.promptMessageAnyKey("标贴张数不能小于0！");
					efLabelNum.clear();
						return false;
				}else if(Integer.parseInt(efLabelNum.getText()) > 10000){
					frmMain.promptMessageAnyKey("标贴张数不能大于10000！");
					efLabelNum.clear();
					   return false;					
				}
			} catch (Exception e) {
				frmMain.promptMessageAnyKey("请输入有效数值!");
				efLabelNum.clear();
				return false;
			}
					
		
			// 创建库存！！   定制的创建库存 ，这个创建库存首先去查看是否要生成新的批次号！
			int retStatus = 0;
			String cmdStr = "";
			try {
				cmdStr = "create usr batch inventory and print sum "
						+ " where wh_id = '"+display.getVariable("global.wh_id")+"'"
						+ " and client_id = '"+ prt_clientId +"'"
						+ " and realQty = '"+ efActRcvQty.getText()+"'"
						+ " and mandte1 = '"+ efRcvDate.getText()+"'"
						+ " and dstlodnum = '"+ efLodnum.getText()+"'"
						+ " and invnum = '"+ efPurOrder.getText()+"'"
						+ " and invlin = '"+ efPOItem.getText()+"'"
						+ " and labelingCnt = '"+ efLabelNum.getText()+"'";
				session.executeDSQL(cmdStr);
			} catch (MocaException e) {
				retStatus = e.getErrorCode();
			}
			if (retStatus != 0) {
				display.beep();
				frmMain.displayErrorMessage();
				return false;
			}
			
			
   			int retStatus2 = 0;
   			String cmdStr2 = "";
   			MocaResults rs2  = null;  
			try{
   				cmdStr2 = "validate info by invnum and invtyp sum "
						+ " where wh_id = '"+display.getVariable("global.wh_id")+"'"
						+ " and invnum = '"+ efPurOrder.getText().trim() +"'"
						+"  and invtyp = '" + po_Norm +"'";
			    rs2 =session.executeDSQL(cmdStr2);
   			}catch(MocaException e){
   				retStatus2 = e.getErrorCode();
   			}
   			if(retStatus2 != 0){            //不可能走这里  因为之前已经验证过  单据是可以查到的  只是验证是否收完
   				frmMain.displayErrorMessage();
   				display.beep();
   				return false;
   			}else if(rs2.hasNext()){
   				rs2.next();
   				int rcvd_flag = rs2.getInt("rcvd_flag");  //对于进口单子  返回的行号是没有用的  因为不知道用户输入什么箱号
   				if(rcvd_flag==1){
   					 frmMain.promptMessageAnyKey("此单已收货完毕！");
   					     frmMain.clearForm();
   					     efPurOrder.setFocus();
						 return false;          //如果整单收货完成  此处即 return false  进行了拦截！
   				}
   			}
			
			frmMain.displayMessage("usrDlgOKToReceipt");  //收货成功！
			clearExceptBillNo();          //除去单据号不清  其他情况 
			efPurOrder.setFocus();
			return false; 	//最后一个值要 return false 否则会跳出框去

		}				
	}
	
	
	
	
    /**
     * 查找
     * @throws Exception
     */
	//ord_innerWkoNo   ord_innerWkoLin  
	private boolean lookforBillno(IInteractiveWidget ef, String invlin2, String prtnum2, int sq) throws Exception {
        int retStatus = 0;
        String cmdStr = "";	
        MocaResults rs = null;   
        
        try{        	
           cmdStr = "list bill info by invnum invlin invtyp sum " + 
                    "  where  wh_id = '" + display.getVariable("global.wh_id") + 
                    "' and invtyp ='" + po_Norm +
                    "' and invnum ='"+ efPurOrder.getText().trim() +
                    "' and invlin ='"+ invlin2 +
                    "' and prtnum ='"+ prtnum2 +
                    "' and sq ='"+ sq +
                    "'";                   
           rs = session.executeDSQL(cmdStr);
        }
        catch(MocaException e){       	
        	retStatus = e.getErrorCode();
        }
      	if (retStatus != 0) {    		
      		frmMain.displayErrorMessage();
            display.beep();
            if(sq==1){                      //如果是单号录入 全屏清除   如果是料号和项次录入 清除的时候保留单号
            	frmMain.clearForm();
            }else if(sq==2||sq==3){                	
            	clearExceptBillNo();	
            }
        	return false;
        }else{         	
        	if(retStatus==0 && rs!=null && rs.hasNext()){
    			rs.next();
				int int_expqty = rs.getInt("expqty");
				//如果rcvqty是空值 那么int取出的值会是0
				int int_rcvqty = rs.getInt("rcvqty");				
				int int_ActRcvQty = int_expqty - int_rcvqty;                    
				String String_ActRcvQty = String.valueOf(int_ActRcvQty);
				efPurOrder.setText(rs.getString("invnum"));   //单据号 交货单号
				efPOItem.setText(rs.getString("invlin"));               //交货单号项次
				efPrtnum.setText(rs.getString("prtnum"));           //物料号
				efPrtDsc.setText(rs.getString("lngdsc"));           //物料描述
				efFactCode.setText(rs.getString("client_id"));     //工厂代号 货主代号
				efAreaCode.setText(rs.getString("inv_attr_str1"));   //库存地点
				efUrgentFlag.setText(rs.getString("uc_prioritylevel"));  //紧急标识
				efABC.setText(rs.getString("abccod"));                   //ABC标识
				efUnit.setText(rs.getString("stkuom"));                   //单位
				efSplDefend.setText(rs.getString("uc_Special_protection"));  //特殊防护
				efExpQty.setText(String.valueOf(rs.getInt("expqty")));                     //应收
				efIdnQty.setText(String.valueOf(rs.getInt("rcvqty")));                   //已收
				efActRcvQty.setText(String_ActRcvQty);           //实收是相减获得的			
				efRcvDate.setText(rs.getString("mandte"));    //制造日期
				efseqnum.setText(rs.getString("seqnum"));    //行序号
				ucSertyp = rs.getInt("uc_ser_typ");        //是否序列管理
				prt_clientId = rs.getString("prt_client_id");        //货主
				deliveryDate = rs.getString("uc_delivery_date");  //交货日期
				invAttrStr1 = rs.getString("inv_attr_str1");  //库存地点
				billtype =  rs.getString("invtyp");            //单据类型				
				efLabelNum.setText("1");
    			if(ucSertyp ==1){
    			lookSnCnt();                      //查询SN数量
				reNewActRcv();            //调用下这个方法如果有必要更新 应收（在已扫大于已收的极端情况）
				//judgeRcvdOff();          // 调用这个方法判断当前行是否需要禁用或者开启部分录入功能
    			}
    			if(judgeRcvdOff()){ // 调用这个方法判断当前行是否需要禁用或者开启部分录入功能
					if(!validateDeliveryDate()){
						return false;
					}
				}
    			if(sq==1||sq==2){		 //如果是单号录入和料号录入  直接下跳  如果是项次录入  是否下跳要做判断  可能不能下跳了		
    			   	return true;
    			}else{
        			if(efLodnum.isEnabled()){				
        				           return true;	  
        			}else{
        				    	  return true;  	//最后一个输入框   因为标签的输入框总是可用的  这里从false 改为 true			
        			}      				    				
    			}	  			
        	}        	
        	return false;
        }
	}
	
	// 验证交期  只拦截
	public boolean validateDeliveryDate() throws Exception {		
		int retStatus = 0;
		String cmdStr = "";
		if (deliveryDate != null && !deliveryDate.equals("")) {
			try {		
				cmdStr = "validate usr recving delivery date"
						+ " where wh_id = '"+ display.getVariable("global.wh_id") + "'"
						+ " and prt_client_id = '" + prt_clientId + "'"
						+ " and delivery_date = '" + deliveryDate + "'";
				session.executeDSQL(cmdStr);
			} catch (MocaException e) {
				retStatus = e.getErrorCode();
			}
			if (retStatus != 0) {
				frmMain.displayErrorMessage();
				display.beep();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 跳转进行SN扫描
	 */
	
	private boolean jumpSnCollection() throws Exception{
		//校验当前物料是否为SN管理的物料
		if(ucSertyp==1){
			frmMain.displayMessage("该物料需采集序列！");
			int expQty = Integer.parseInt(efExpQty.getText());
			int scanQty = Integer.parseInt(efScanQty.getText());
  			String prtDsc = efPrtDsc.getText();
  			String prtDscr = "";
  			if(prtDsc.length()>12){
  				prtDsc = efPrtDsc.getText().substring(0, 12);
  				prtDscr = efPrtDsc.getText().substring(12);
  			}
  			AFormLogic  newFrm  = display.createFormLogic("USR_SN_COLLECTION",EFlow.SHOW_FORM);
  			newFrm.getForm().clearForm();
  			display.setVariable("USR_SN_COLLECTION.Billno", efPurOrder.getText().trim());
  			display.setVariable("USR_SN_COLLECTION.Lineno", efPOItem.getText().trim());
			display.setVariable("USR_SN_COLLECTION.Lotnum", eflotnum.getText().trim());
			display.setVariable("USR_SN_COLLECTION.PrtNum", efPrtnum.getText().trim());
			display.setVariable("USR_SN_COLLECTION.Seqnum", efseqnum.getText().trim());
			display.setVariable("USR_SN_COLLECTION.PrtDsc", prtDsc);
			display.setVariable("USR_SN_COLLECTION.PrtDscr", prtDscr);
			display.setVariable("USR_SN_COLLECTION.Lodnum",efLodnum.getText().trim());
			display.setVariable("USR_SN_COLLECTION.ExpScanQty",scanQty+"/"+expQty);
			display.setVariable("USR_SN_COLLECTION.client_id",prt_clientId);
			display.setVariable("USR_SN_COLLECTION.inv_attr_str1",invAttrStr1);
			display.setVariable("USR_SN_COLLECTION.billtype",billtype);
			newFrm.run();
		}   
			return false;
	}
	
	
	
	/**
	 * 查找已扫描sn个数
	 * 
	 */
	private void lookSnCnt() throws Exception{
		int retStatus = 0;
		String cmdStr = "";
		MocaResults mr = null;
		try {
			cmdStr = "list usr sn count by bill and item"
					+ " where wh_id = '"+display.getVariable("global.wh_id")+"'"
					+ " and client_id = '"+ prt_clientId +"'"
					+ " and billno = '"+ efPurOrder.getText().trim()+"'"
					+ " and uc_lineid = '"+ efPOItem.getText().trim() +"'"	  //单据rcvlin中的invlin				
					+ " and billtype = '"+ billtype +"'";
			mr = session.executeDSQL(cmdStr);
		} catch (MocaException e) {
			retStatus = e.getErrorCode();
		}
		if (retStatus != 0) {
			display.beep();
			frmMain.displayErrorMessage();
			return;
		}else{
			 if(retStatus==0 && mr!=null && mr.hasNext()){
                mr.next();
                //setText(mr.getInt("cnt"), false);
                int scanQty = mr.getInt("cnt");
                efScanQty.setText(String.valueOf(scanQty));           //进入后更新扫描数量
                
                String scanQty_s = efScanQty.getText().trim();
                String idnQty_s = efIdnQty.getText().trim();
                int idnQty = 0 ;
                if(!"".equals(scanQty_s)){
              	  try {
                  	   idnQty = Integer.parseInt(idnQty_s);
  				  } catch (Exception e) {
  				  }
                }
                int ActRcvQty = scanQty - idnQty;  //实收数量 等于已扫 减去已收   如果大于0 才去更新实收的值  否则不更新
                if(ActRcvQty>0){
              	  efActRcvQty.setText(String.valueOf(ActRcvQty));;
                } 	                     
                frmMain.resetMessageLine(); //清空提示 ！
                return;
			 }
		}
	}
	
	
	//更新实收数量，如果已扫数量大于已收数量（这种情况比较特殊），会更新实收数量！
	private void reNewActRcv() {
	    String scanQty_s = efScanQty.getText().trim();
	    String idnQty_s = efIdnQty.getText().trim();
   
	    int idnQty_i = 0 ;
	    int scanQty_i = 0 ;
	    if(!"".equals(scanQty_s)){
	  	  try {
	  		   idnQty_i = Integer.parseInt(idnQty_s);
	  		 scanQty_i = Integer.parseInt(scanQty_s);
			  } catch (Exception e) {
			  }
	    }
	
	    int ActRcvQty = scanQty_i - idnQty_i;  //实收数量 等于已扫 减去已收   如果大于0 才去更新实收的值  否则不更新
	    if(ActRcvQty>0){
	  	  efActRcvQty.setText(String.valueOf(ActRcvQty));;
	    }
	}
	
	
	
	//判断如果此单某项收货数已经等于预期 ExpQty 则禁用某些输入框    此功能是根据行来判断   只要当前行全部收完   即禁用部分输入框
	private boolean judgeRcvdOff(){
	    String ExpQty_s = efExpQty.getText().trim();
	    String idnQty_s = efIdnQty.getText().trim();		
	    int ExpQty_i = 1 ;                    //初始赋值为1 ，除非是两个显示处都有值，而且相等才锁定一些输入框功能
	    int idnQty_i = 0 ;	    
	    if(!"".equals(ExpQty_s)&&!"".equals(idnQty_s)){
		  	  try {
			    	ExpQty_i = Integer.parseInt(ExpQty_s);
		  		    idnQty_i = Integer.parseInt(idnQty_s);
				  } catch (Exception e) {
				  }
		    }
	    int judge  = ExpQty_i - idnQty_i ;
	    if(judge == 0){                      // 这地方如果是相等的就直接返回false   
	    	efLodnum.setEnabled(false);
	    	efActRcvQty.setEnabled(false);
	    	efRcvDate.setEnabled(false);	  //收货日期这里是否考虑值清空  因为这里的数据可能被修改过 存在了invdtl里
	    	//efLabelNum.setEnabled(false);
	    	return false;
	    }else{
	    	efLodnum.setEnabled(true);
	    	efActRcvQty.setEnabled(true);
	    	efRcvDate.setEnabled(true);	  //如果是大于0的情况，需要均是可以编辑的！
	    	//efLabelNum.setEnabled(true);
	    	return true;
	    }		
	}
	
   	
	/*因为单引号对于MOCA是敏感字符，如果输入单引号系统是会报错的，因为参数是用单引号包裹的  所以如果外部输入的字符串含单引号 需要被替换掉*/
	private int checkStr(String s, IEntryField ef){	
		int Flag_dyh = 0;
		if(s.contains("'")){
			frmMain.promptMessageAnyKey("单引号为保留字符，输入无效！");
			ef.clear();
			 Flag_dyh = 1;
		}
		return Flag_dyh;
		//return s;
	}
	
	
	
	/**
	 * 清除表单中的值，单号不清除
	 */
	   
		private void clearExceptBillNo() {
			efPOItem.clear();
			efLodnum.clear();
			efPrtnum.clear();
			efPrtDsc.clear();
			efFactCode.clear();
			efAreaCode.clear();
			efUrgentFlag.clear();
			efABC.clear();
			efUnit.clear();
			efSplDefend.clear();
			efExpQty.clear();
			efIdnQty.clear();
			efActRcvQty.clear();
			efScanQty.clear();
			efRcvDate.clear();
			efLabelNum.clear();
		}
		

}