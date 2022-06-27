from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys

#linux
from docx2pdf import convert
import os
import platform

from mailmerge import MailMerge  # 引用邮件处理模块

# datafile_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js002\\js002.xlsx'  # 表格位置
# data = xlrd.open_workbook(datafile_path)
# table = data.sheet_by_name('这是一个合同')  # excel表名
#template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS002\\NST－04－JS002－2011－软件项目委托测试申请表-空白表.docx'  # 模版位置
# nrows = table.nrows

# C:/Users/Yongp/AppData/Local/Programs/Python/Python310/python.exe c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/JS002/test.py -t 'c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/JS002/NST－04－JS002－2011－软件项目委托测试申请表-空白表.docx' -o 'c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/JS002/output' -i 'c:/Users/Yongp/Desktop/Classified/NJUTesting/yudao-server/src/main/resources/tool/JS002/data.json'
# -t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS002\NST－04－JS002－2011－软件项目委托测试申请表-空白表.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS002\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS002\\output"

def main(argv):
      
      o_path = ""
      i_path = ""
      template = ""

      try:
            opts, args = getopt.getopt(argv,'o:i:t:',["ofile=","ifile=","template="])
            
      except getopt.GetoptError:
            sys.exit(2)

      for opt, arg in opts:
            if opt in ("-o", "--ofile"):
                  o_path = arg
            elif opt in ("-i", "--ifile"):
                  i_path = arg 
            elif opt in ("-t", "--template"):
                  template = arg
      with open(i_path, 'r', encoding='utf8') as fp:
            jdata = json.load(fp)
            
            document = MailMerge(template)
            
            #测试类型
            _TestType1 = "□"
            _TestType2 = "□"
            _TestType3 = "□"
            _TestTypeOther = ""
            for i in jdata["测试类型"]:
                  if i == "软件确认测试":
                        _TestType1 = "✓"
                  if i == "成果/技术鉴定测试":
                        _TestType2 = "✓"
                  if i == "专项资金验收测试":
                        _TestType3 = "✓"
                  if "测试类型其他" in jdata.keys():
                        _TestTypeOther = jdata["测试类型其他"]
            
            #单位性质
            _Department1 = "○"
            _Department2 = "○"
            _Department3 = "○"
            _Department4 = "○"
            _Department5 = "○"
            _DepartmentOther = "○"
            if(jdata["单位性质"] == "内资企业"):
                  _Department1 = "✓" 
            elif(jdata["单位性质"] == "外合资企业"):
                  _Department2 = "✓"  
            elif(jdata["单位性质"] == "港澳台合资企业"):
                  _Department3 = "✓" 
            elif(jdata["单位性质"] == "科研院校"):
                  _Department4 = "✓"  
            elif(jdata["单位性质"] == "政府事业团体"):
                  _Department5 = "✓" 
            elif(jdata["单位性质"] == "其它"):
                  _DepartmentOther = "✓"   
            
            #测试依据
            _TestDep1 = "□"
            _TestDep2 = "□"
            _TestDep3 = "□"
            _TestDep4 = "□"
            _TestDepOther = ""
            if "测试依据其他" in jdata.keys():
                  _TestDepOther = jdata["测试依据其他"]
            for i in jdata["测试依据"]:
                  if i == "GB/T 25000.51-2010":
                        _TestDep1 = "✓"
                  if i == "GB/T 16260.1-2006":
                        _TestDep1 = "✓"
                  if i == "NST-03-WI12-2011":
                        _TestDep1 = "✓"
                  if i == "NST-03-WI13-2011":
                        _TestDep1 = "✓"
            
            #需要测试的技术指标
            _TechTar1 = "□"
            _TechTar2 = "□"
            _TechTar3 = "□"
            _TechTar4 = "□"
            _TechTar5 = "□"
            _TechTar6 = "□"
            _TechTar7 = "□"
            _TechTar8 = "□"
            _TechTar9 = "□"
            _TechTar10 = "□"
            _TechTar11 = "□"
            _TechTar12 = "□"
            _TechTarOther = ""
            if "需要测试的技术指标其他" in jdata.keys():
                  _TechTarOther = jdata["需要测试的技术指标其他"]
            for i in jdata["需要测试的技术指标"]:
                  if i == "功能性":
                        _TechTar1 = "✓"
                  if i == "可靠性":
                        _TechTar2 = "✓"
                  if i == "易用性":
                        _TechTar3 = "✓"
                  if i == "效率":
                        _TechTar4 = "✓"
                  if i == "可维护性":
                        _TechTar5 = "✓"
                  if i == "可移植性":
                        _TechTar6 = "✓"    
                  if i == "代码覆盖度":
                        _TechTar7 = "✓"    
                  if i == "缺陷检测率":
                        _TechTar8 = "✓"    
                  if i == "代码风格符合度":
                        _TechTar9 = "✓"    
                  if i == "代码不符合项检测率":
                        _TechTar10 = "✓"  
                  if i == "产品说明要求":
                        _TechTar11 = "✓"        
                  if i == "用户文档集要求":
                        _TechTar12 = "✓"        

            #软件类型
            _Syssoft1 = "○"
            _Syssoft2 = "○"
            _Syssoft3 = "○"
            _Syssoft4 = "○"
            _Syssoft5 = "○"
            if "软件类型" in jdata.keys():
                  if(jdata["软件类型"] == "操作系统"):
                        _Syssoft1 = "✓" 
                  elif(jdata["软件类型"] == "中文处理系统"):
                        _Syssoft2 = "✓" 
                  elif(jdata["软件类型"] == "网络系统"):
                        _Syssoft3 = "✓" 
                  elif(jdata["软件类型"] == "嵌入式操作系统"):
                        _Syssoft4 = "✓" 
                  elif(jdata["软件类型"] == "其他"):
                        _Syssoft5 = "✓"     
                  
            #软件类型
            _SptSoft1 = "○"
            _SptSoft2 = "○"
            _SptSoft3 = "○"
            _SptSoft4 = "○"
            _SptSoft5 = "○"
            _SptSoft6 = "○"
            if "软件类型" in jdata.keys():
                  if(jdata["软件类型"] == "程序设计语言"):
                        _SptSoft1 = "✓" 
                  elif(jdata["软件类型"] == "数据库系统设计"):
                        _SptSoft2 = "✓" 
                  elif(jdata["软件类型"] == "工具软件"):
                        _SptSoft3 = "✓"       
                  elif(jdata["软件类型"] == "网络通信软件"):
                        _SptSoft4 = "✓" 
                  elif(jdata["软件类型"] == "中间件"):
                        _SptSoft5 = "✓" 
                  elif(jdata["软件类型"] == "其他"):
                        _SptSoft6 = "✓" 
                  
            #应用软件
            _AppSoft1 = "○"
            _AppSoft2 = "○"
            _AppSoft3 = "○"
            _AppSoft4 = "○"
            _AppSoft5 = "○"
            _AppSoft6 = "○"
            _AppSoft7 = "○"
            _AppSoft8 = "○"
            _AppSoft9 = "○"
            _AppSoft10 = "○"
            _AppSoft11 = "○"
            _AppSoft12 = "○"
            _AppSoft13 = "○"
            if "软件类型" in jdata.keys():
                  if jdata["软件类型"] == "行业管理软件":
                        _AppSoft1 = "✓" 
                  elif jdata["软件类型"] == "办公软件":
                        _AppSoft2 = "✓"
                  elif jdata["软件类型"] == "模式识别软件":
                        _AppSoft3 = "✓"
                  elif jdata["软件类型"] == "图形图像软件":
                        _AppSoft4 = "✓"      
                  elif jdata["软件类型"] == "控制软件":
                        _AppSoft5 = "✓"
                  elif jdata["软件类型"] == "网络应用软件":
                        _AppSoft6 = "✓"
                  elif jdata["软件类型"] == "信息管理软件":
                        _AppSoft7 = "✓"
                  elif jdata["软件类型"] == "数据库管理应用软件":
                        _AppSoft8 = "✓"
                  elif jdata["软件类型"] == "安全与保密软件":
                        _AppSoft9 = "✓"
                  elif jdata["软件类型"] == "嵌入式应用软件":
                        _AppSoft10 = "✓"
                  elif jdata["软件类型"] == "教育软件":
                        _AppSoft11 = "✓"
                  elif jdata["软件类型"] == "游戏软件":
                        _AppSoft12 = "✓"
                  elif jdata["软件类型"] == "其他":
                        _AppSoft13 = "✓"
                  
            #客户端操作系统
            _OtherOS = ""
            _OtherOSVer = ""
            _Windows = "○"
            _Linux = "○"
            _WindowsVer = ""
            _LinuxVer = ""
            if "WindowsVersion" in jdata.keys():
                  _WindowsVer = jdata["WindowsVersion"]
            if "_LinuxVer" in jdata.keys():
                  _WindowsVer = jdata["_LinuxVer"]
            if "Windows" in jdata.keys() and jdata["Windows"] == True:
                  _Windows = "✓"
            if "Linux" in jdata.keys() and jdata["Linux"] == True:
                  _Linux = "✓"

            #软件介质
            _SoftwareMedDisk = "□"
            _SoftwareMedU = "□"
            _SoftWareMedOtherContent = ""
            if "软件介质其他" in jdata.keys():
                  _SoftWareMedOtherContent = jdata["软件介质其他"]
            for i in jdata["软件介质"]:
                  if i == "光盘":
                        _SoftwareMedDisk = "✓"
                  if i == "U盘":
                        _SoftwareMedU = "✓"
                        
            #处理方式
            _SampleManagement1 = "○"
            _SampleManagement2 = "○"
            if "提交的样品" in jdata.keys():
                  if jdata["提交的样品"] == "由本实验室销毁":
                        _SampleManagement1 = "✓"
                  elif jdata["提交的样品"] == "退还给我们":
                        _SampleManagement2 = "✓"
            
            #密级
            _Secret1 = "○"
            _Secret2 = "○"
            _Secret3 = "○"
            if jdata["密级"] == "无密级":
                  _Secret1 = "✓"
            elif jdata["密级"] == "秘密":
                  _Secret2 = "✓"
            elif jdata["密级"] == "机密":
                  _Secret3 = "✓"
            
            #查杀病毒
            _Virus1 = "○"
            _Virus2 = "○"
            _Virus3 = ""
            if jdata["查杀病毒"] == "已完成":
                  _Virus1 = "✓"
            elif jdata["查杀病毒"] == "无法完成":
                  _Virus2 = "✓"
            
            #材料检查
            #测试样品
            _TestSample1 = "□"
            _TestSample2 = "□"
            for i in jdata["测试样品"]:
                  if i == "源代码":
                        _TestSample1 = "✓"
                  if i == "可执行文件":
                        _TestSample2 = "✓"
            
            #构架
            _CS = "□"
            _BS = "□"
            _goujiaOther = "□"
            for i in jdata["构架"]:
                  if i == "C/S":
                        _CS = "✓"
                  if i == "B/S":
                        _BS = "✓"
                  if i == "其它":
                        _goujiaOther = "✓"
            
            #需求文档
            _ReqDoc1 = "□"
            _ReqDoc2 = "□"
            _ReqDoc3 = "□"
            if "需求文档" in jdata.keys():
                  for i in jdata["需求文档"]:
                        if i == "项目计划任务书":
                              _ReqDoc1 = "✓"
                        if i == "需求分析报告":
                              _ReqDoc2 = "✓"
                        if i == "合同":
                              _ReqDoc3 = "✓"
                        
            #用户文档
            _UserDoc1 = "□"
            _UserDoc2 = "□"
            for i in jdata["用户文档"]:
                  if i == "用户手册":
                        _UserDoc1 = "✓"    
                  if i == "用户指南":
                        _UserDoc2 = "✓"     
                  
            #操作文档
            _OpDoc1 = "□"
            _OpDoc2 = "□"
            _OpDoc3 = "□"
            _OpDoc4 = "□"
            for i in jdata["操作文档"]:
                  if i == "操作员手册":
                        _OpDoc1 = "✓"
                  if i == "安装手册":
                        _OpDoc2 = "✓"
                  if i == "诊断手册":
                        _OpDoc3 = "✓"
                  if i == "支持手册":
                        _OpDoc4 = "✓"
                  
            #确认意见
            _ConfirmAdvice1 = "○"
            _ConfirmAdvice2 = "○"
            _ConfirmAdvice3 = "○"
            _ConfirmAdvice4 = "○"
            if jdata["确认意见"] == "确认意见1":
                  _ConfirmAdvice1 = "✓"
            elif jdata["确认意见"] == "确认意见2":
                  _ConfirmAdvice2 = "✓"
            elif jdata["确认意见"] == "确认意见3":
                  _ConfirmAdvice3 = "✓"
            elif jdata["确认意见"] == "确认意见4":
                  _ConfirmAdvice4 = "✓"
            
            #受理意见
            _DealAdvice1 =  "○"
            _DealAdvice2 =  "○"
            _DealAdvice3 =  "○"
            if jdata["受理意见"] == "受理意见1":
                  _DealAdvice1 = "✓"
            if jdata["受理意见"] == "受理意见2":
                  _DealAdvice2 = "✓"
            if jdata["受理意见"] == "受理意见3":
                  _DealAdvice3 = "✓"      


            #客户端其他要求
            _ClientOtherRequest = ""
            if "客户端其他要求" in jdata.keys():
                  _ClientOtherRequest = jdata["客户端其他要求"]

            #服务器端其他要求
            _ServerOtherRequest = ""
            if "服务器端其他要求" in jdata.keys():
                  _ServerOtherRequest = jdata["服务器端其他要求"]
                  
            #希望测试完成时间
            _ExpectedFinTime = ""
            if "希望测试完成时间" in jdata.keys():
                  _ExpectedFinTime = jdata["希望测试完成时间"]

            #
            _MatCheckOther = ""
            if "材料检查_其他" in jdata.keys():
                  _MatCheckOther = jdata["材料检查_其他"]
            document.merge(
            Edition = jdata["版本"],
            SoftwareName = jdata["软件名称"],
            DelegationPartyCn = jdata["委托单位Ch"],
            DelegationPartyEn = jdata["委托单位En"],
            Developer = jdata["开发单位"],
            DescriptionOfUser = jdata["软件用户对象描述"],
            FunctionBrief = jdata["主要功能及用途简介（限200字）"],
            FunctionNum = jdata["功能数"],
            FunctionPnt = jdata["功能点数"],
            Lines = jdata["代码行数"],
            ClientMemory = jdata["客户端内存要求"],
            ClientOtherRequest = _ClientOtherRequest,
            ServerMemory = jdata["服务器端内存要求"],
            DiskMemory = jdata["服务器端硬盘要求"],
            ServerOtherRequest = _ServerOtherRequest,
            OS = jdata["操作系统"],
            ServerEdition = jdata["版本"],
            Language = jdata["编程语言"],
            Database = jdata["数据库"],
            Midware = jdata["中间件"],
            OtherSupport = jdata["其他支撑软件"],
            NetEnv = jdata["网络环境"],
            DocumentData = jdata["文档资料"],
            ExpectedFinTime = _ExpectedFinTime,
            Tel = jdata["委托单位_电话"],
            Fax = jdata["委托单位_传真"],
            Addr = jdata["委托单位_地址"],
            PastalCode = jdata["委托单位_邮编"],
            Linkman = jdata["委托单位_联系人"],
            Mobile = jdata["委托单位_手机"],
            Email = jdata["委托单位_Email"],
            URL = jdata["委托单位_网址"],
            ProjectNum = jdata["测试项目编号"],
            Remark = jdata["备注"],
            SigB = jdata["受理人（签字）"],
            SigA = jdata["委托人（签字）"],
            DateB = jdata["受理人_日期"],
            DateA = jdata["委托人_日期"],
            
            #单位性质
            Department1 = _Department1,
            Department2 = _Department2,
            Department3 = _Department3,
            Department4 = _Department4,
            Department5 = _Department5,
            DepartmentOther = _DepartmentOther,
            
            #测试类型
            TestType1 = _TestType1,
            TestType2 = _TestType2,
            TestType3 = _TestType3,
            TestTypeOther = _TestTypeOther,
            
            #测试依据
            TestDep1 = _TestDep1,
            TestDep2 = _TestDep2,
            TestDep3 = _TestDep3,
            TestDep4 = _TestDep4,
            TestDepOther = _TestDepOther,
            
            #需要测试的技术指标
            TechTar1 = _TechTar1,
            TechTar2 = _TechTar2,
            TechTar3 = _TechTar3,
            TechTar4 = _TechTar4,
            TechTar5 = _TechTar5,
            TechTar6 = _TechTar6,
            TechTar7 = _TechTar7,
            TechTar8 = _TechTar8,
            TechTar9 = _TechTar9,
            TechTar10 = _TechTar10,
            TechTar11 = _TechTar11,
            TechTar12 = _TechTar12,
            TechTarOther = _TechTarOther,
            
            #软件类型
            SysSoft1 = _Syssoft1,
            SysSoft2 = _Syssoft2,
            SysSoft3 = _Syssoft3,
            SysSoft4 = _Syssoft4,
            SysSoft5 = _Syssoft5,
            
            #软件类型
            SptSoft1 = _SptSoft1,
            SptSoft2 = _SptSoft2,
            SptSoft3 = _SptSoft3,
            SptSoft4 = _SptSoft4,
            SptSoft5 = _SptSoft5,
            SptSoft6 = _SptSoft6,
            
            #应用软件
            AppSoft1 = _AppSoft1,
            AppSoft2 = _AppSoft2,
            AppSoft3 = _AppSoft3,
            AppSoft4 = _AppSoft4,
            AppSoft5 = _AppSoft5,
            AppSoft6 = _AppSoft6,
            AppSoft7 = _AppSoft7,
            AppSoft8 = _AppSoft8,
            AppSoft9 = _AppSoft9,
            AppSoft10 = _AppSoft10,
            AppSoft11 = _AppSoft11,
            AppSoft12 = _AppSoft12,
            AppSoft13 = _AppSoft13,
            
            #客户端操作系统
            Windows = _Windows,
            Linux = _Linux,
            WindowsVer = _WindowsVer,
            LinuxVer = _LinuxVer,
            OtherOS = _OtherOS,
            OtherOSVer = _OtherOSVer,
            
            #软件介质
            SoftwareMedDisk = _SoftwareMedDisk,
            SoftwareMedU = _SoftwareMedU,
            SoftWareMedOtherContent = _SoftWareMedOtherContent,
            
            #提交的样品
            SampleManagement1 = _SampleManagement1,
            SampleManagement2 = _SampleManagement2,
            
            #密级
            Secret1 = _Secret1,
            Secret2 = _Secret2,
            Secret3 = _Secret3,
            
            #查杀病毒
            Virus1 = _Virus1,
            Virus2 = _Virus2,
            Virus3 = _Virus3,
            
            #材料检查
            TestSample1 = _TestSample1,
            TestSample2 = _TestSample2,
            
            #需求文档
            ReqDoc1 = _ReqDoc1,
            ReqDoc2 = _ReqDoc2,
            ReqDoc3 = _ReqDoc3,
            
            #用户文档
            UserDoc1 = _UserDoc1,
            UserDoc2 = _UserDoc2,
            
            #操作文档
            OpDoc1 = _OpDoc1,
            OpDoc2 = _OpDoc2,
            OpDoc3 = _OpDoc3,
            OpDoc4 = _OpDoc4,
            
            #构架
            CS = _CS,
            BS = _BS,
            goujiaOther = _goujiaOther,
            
            #其他材料检查
            MatCheckOther = _MatCheckOther,
            
            #确认意见
            ConfirmAdvice1 = _ConfirmAdvice1,
            ConfirmAdvice2 = _ConfirmAdvice2,
            ConfirmAdvice3 = _ConfirmAdvice3,
            ConfirmAdvice4 = _ConfirmAdvice4,
            
            #受理意见
            DealAdvice1 = _DealAdvice1,
            DealAdvice2 = _DealAdvice2,
            DealAdvice3 = _DealAdvice3,
            
            
            )
            wordname = o_path + '.docx' 
            document.write(wordname)  # 创建新文件
            if platform.system() == "Windows":
                  convert(wordname, o_path + '.pdf')
            else:
                  os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
            
      


if __name__ == '__main__':
    main(sys.argv[1:])